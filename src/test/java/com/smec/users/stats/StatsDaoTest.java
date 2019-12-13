package com.smec.users.stats;

import com.smec.users.DatabaseCleanupRule;
import com.smec.users.accounts.AccountDao;
import com.smec.users.accounts.AccountEntity;
import org.apache.logging.log4j.status.StatusData;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StatsDaoTest {

    @Autowired
    @Rule
    public DatabaseCleanupRule rule;

    @BeforeEach
    public void cleanDB() { rule.execute(); }

    @Autowired
    private StatsDao target;

    @Autowired
    private AccountDao accountDao;

    @Test
    @Transactional
    void fetchByGroup() {
        AccountEntity account = accountDao.store(new AccountEntity("me"));
        target.store(new StatsEntry("a", 2000, 1, 1, account, 1));

        Assertions.assertThat(target.fetchByGroup(2000, 1, 1, "a", account.getId())).isNotNull();
        Assertions.assertThat(target.fetchByGroup(2001, 1, 1, "a", account.getId())).isNull();
        Assertions.assertThat(target.fetchByGroup(2000, 2, 1, "a", account.getId())).isNull();
        Assertions.assertThat(target.fetchByGroup(2000, 1, 2, "a", account.getId())).isNull();
        Assertions.assertThat(target.fetchByGroup(2000, 1, 1, "b", account.getId())).isNull();
    }
}