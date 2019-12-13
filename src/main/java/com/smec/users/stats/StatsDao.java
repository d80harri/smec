package com.smec.users.stats;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class StatsDao implements IStatsDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<StatsEntry> fetchAll(int accountId) {
        return entityManager.createQuery("FROM StatsEntry WHERE account.id=:accountId")
                .setParameter("accountId", accountId).getResultList();
    }

    @Override
    public StatsEntry store(StatsEntry entity) {
        entityManager.persist(entity);
        return entity;
    }

    public StatsEntry fetchByGroup(int year, int month, int day, String type, int accountId){
        List<StatsEntry> result = entityManager.createQuery("FROM StatsEntry " +
                "WHERE year=:year and month=:month and " +
                "day=:day and type=:type and account.id=:accountId")
                .setParameter("year", year)
                .setParameter("month", month)
                .setParameter("day", day)
                .setParameter("type", type)
                .setParameter("accountId", accountId)
                .getResultList();

        switch (result.size()){
            case 0: return null;
            case 1:return result.get(0);
            default: throw new IllegalStateException();
        }
    }

}
