package com.smec.users.stats;

import java.util.*;
import java.util.stream.Collectors;

import com.smec.users.base.DateUtils;
import com.smec.users.events.EventEntity;
import com.smec.users.events.IEventDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StatsService implements IStatsService {

    @Autowired
    private IStatsDao statsDao;

    @Autowired
    private IEventDao eventDao;

    public List<StatsEntry> fetchAll(int accountId) {
        List<StatsEntry> result = statsDao.fetchAll(accountId);
        result.addAll(eventDao.getStats(accountId));
        return merge(result).stream().collect(Collectors.toList()); // todo sort
    }

    static Set<StatsEntry> merge(List<StatsEntry> list) {
        Set<StatsEntry> result = new HashSet<>();

        list.forEach(e -> {
            StatsEntry rse = result.stream().filter(r ->
                    r.getAccount() == e.getAccount()  &&
                    r.getYear() == e.getYear() &&
                    r.getMonth() == e.getMonth() &&
                    r.getDay() == e.getDay() &&
                    r.getType().equals(e.getType())
            ).findFirst().orElse(new StatsEntry(e.getType(), e.getYear(), e.getMonth(), e.getDay(), e.getAccount(), e.getCount()));

                result.add(rse);

                rse.setCount(rse.getCount() + e.getCount());

        });

        return result;
    }

    private void mergeStore(List<EventEntity> list) {
        for (EventEntity ee : list) {
            StatsEntry stats = this.getByGroup(ee.getTime(), ee.getType(), ee.getAccount().getId());
            if (stats == null) {
                DateUtils du = new DateUtils(ee.getTime());
                stats = new StatsEntry(ee.getType(), du.year(), du.month(),
                        du.day(), ee.getAccount(), 1);
            } else {
                stats.setCount(stats.getCount() +1);
            }
            this.statsDao.store(stats);
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE) // TODO: check isolation level
    public void archivateOldEvents(Date time) {
        List<EventEntity> newStats = this.eventDao.getEventsBefore(time);
        this.eventDao.deleteStatsYoungerThan(time);
        this.mergeStore(newStats);
    }

    @Override
    public StatsEntry getByGroup(Date time, String type, int accountId) {
        DateUtils du = new DateUtils(time);
        return this.statsDao.fetchByGroup(du.year(), du.month(), du.day(), type, accountId);
    }
}
