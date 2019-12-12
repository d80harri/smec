package com.smec.users.events;

import java.util.Date;
import java.util.List;

import com.smec.users.accounts.AccountEntity;
import com.smec.users.accounts.IAccountDao;

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

    public List<EventEntity> fetchAllEvents() {
        return eventDao.fetchAllAccounts();
    }

    @Override
    public EventEntity store(EventEntity entity, int accountId) throws IllegalReferenceException {
        entity.setTime(new Date());
        // TODO: test unknown account
        AccountEntity account = accountDao.getById(accountId);
        if (account == null) {
            throw new IllegalReferenceException("Account with id " + accountId + " does not exist.");
        }
        entity.setAccount(account);
        return eventDao.store(entity);
    }

    public static class IllegalReferenceException extends Exception {
        private static final long serialVersionUID = 1L;

        public IllegalReferenceException(String msg) {
            super(msg);
        }
    }
}
