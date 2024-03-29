package com.smec.users.accounts;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class AccountDao implements IAccountDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<AccountEntity> fetchAllAccounts() {
        return entityManager.createQuery("FROM AccountEntity").getResultList();
    }

    @Override
    public AccountEntity store(AccountEntity entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public AccountEntity getById(int accountId) {
        return entityManager.find(AccountEntity.class, accountId);
    }

    @Override
    public void merge(AccountEntity result) {
        entityManager.merge(result);
    }

}
