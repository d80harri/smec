package com.smec.users.accounts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
