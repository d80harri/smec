package com.smec.users.accounts;

import java.util.List;

public interface IAccountService {
    List<AccountEntity> fetchAllAccounts();

    AccountEntity store(AccountEntity entity);

    AccountEntity get(int id);

    void update(AccountEntity entity);
}
