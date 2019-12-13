package com.smec.users.accounts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@Transactional
public class AccountService implements IAccountService {

    @Autowired
    private IAccountDao accountDao;

    public List<AccountEntity> fetchAllAccounts() {
        return accountDao.fetchAllAccounts();
    }

    @Override
    public AccountEntity store(AccountEntity entity) {
        return accountDao.store(entity);
    }

    @Override
    public AccountEntity get(int id) {
        return accountDao.getById(id);
    }

    @Override
    public void update(AccountEntity result) {
        accountDao.merge(result);
    }

}
