package com.smec.users.stats;

import java.util.Date;
import java.util.List;

import com.smec.users.accounts.AccountEntity;
import com.smec.users.accounts.IAccountDao;
import com.smec.users.exceptions.IllegalReferenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StatsService implements IStatsService {

    @Autowired
    private IStatsDao eventDao;

    @Autowired
    private IAccountDao accountDao;

    public List<StatsEntry> fetchAllEvents() {
        return eventDao.fetchAllAccounts();
    }

    @Override
    public StatsEntry store(StatsEntry entity, int accountId) throws IllegalReferenceException {
        AccountEntity account = accountDao.getById(accountId);
        if (account == null) {
            throw new IllegalReferenceException("Account with id " + accountId + " does not exist.");
        }
        entity.setAccount(account);
        return eventDao.store(entity);
    }

}
