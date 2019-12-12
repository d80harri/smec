package com.smec.users.accounts;

import java.util.List;

public interface IAccountDao {
    List<AccountEntity> fetchAllAccounts();

    AccountEntity store(AccountEntity entity);

    AccountEntity getById(int accountId);
}
