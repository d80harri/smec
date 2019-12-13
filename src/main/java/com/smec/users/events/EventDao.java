package com.smec.users.events;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.smec.users.stats.StatsEntry;

import org.springframework.stereotype.Repository;

@Repository
public class EventDao implements IEventDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<EventEntity> fetchAllAccounts() {
        return entityManager.createQuery("FROM EventEntity").getResultList();
    }

    @Override
    public EventEntity store(EventEntity entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public List<StatsEntry> getStats(int accountId) {
        return entityManager.createQuery("SELECT new StatsEntry(type, YEAR(ee.time), MONTH(ee.time), DAY(ee.time), account, COUNT(ee.id)) " +
                " FROM EventEntity ee WHERE ee.account.id=:accountId GROUP BY ee.type, YEAR(ee.time), MONTH(ee.time), DAY(ee.time), ee.account")
                .setParameter("accountId", accountId).getResultList();
    }

    @Override
    public void deleteStatsYoungerThan(Date time) {
        entityManager.createQuery("DELETE FROM EventEntity WHERE time < :time").setParameter("time", time)
                .executeUpdate();
    }

    @Override
    public List<EventEntity> getEventsBefore(Date time) {
        return entityManager.createQuery("FROM EventEntity WHERE time<:time")
                .setParameter("time", time).getResultList();
    }

}
