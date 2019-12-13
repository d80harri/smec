package com.smec.users.events;

import java.util.Date;
import java.util.List;

import com.smec.users.accounts.AccountEntity;
import com.smec.users.accounts.IAccountDao;
import com.smec.users.base.IClock;
import com.smec.users.exceptions.IllegalReferenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EventService implements IEventService {

    @Autowired
    private IEventDao eventDao;

    @Autowired
    private IAccountDao accountDao;

    @Autowired
    private IClock clock;

    public List<EventEntity> fetchAllEvents() {
        return eventDao.fetchAllAccounts();
    }

    @Override
    public EventEntity store(EventEntity entity, int accountId) throws IllegalReferenceException {
        entity.setTime(clock.now());
        AccountEntity account = accountDao.getById(accountId);
        if (account == null) {
            throw new IllegalReferenceException("Account with id " + accountId + " does not exist.");
        }
        entity.setAccount(account);
        return eventDao.store(entity);
    }

}
