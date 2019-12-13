package com.smec.users.events;

import java.util.Date;
import java.util.List;

import com.smec.users.stats.StatsEntry;

public interface IEventDao {
    List<EventEntity> fetchAllAccounts();

    EventEntity store(EventEntity entity);

    List<StatsEntry> getStats(int accountId);

    void deleteStatsYoungerThan(Date time);

    List<EventEntity> getEventsBefore(Date time);
}
