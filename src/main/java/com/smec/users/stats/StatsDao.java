package com.smec.users.stats;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class StatsDao implements IStatsDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<StatsEntry> fetchAllAccounts() {
        return entityManager.createQuery("FROM StatsEntry").getResultList();
    }

    @Override
    public StatsEntry store(StatsEntry entity) {
        entityManager.persist(entity);
        return entity;
    }

}
