package com.smec.users.events;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    public int deleteOld(long l) {
        return entityManager.createQuery("DELETE FROM EventEntity WHERE time < :time").setParameter("time", new Date(l))
                .executeUpdate();
    }

}
