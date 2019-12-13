package com.smec.users.events;

import com.smec.users.DatabaseCleanupRule;
import com.smec.users.accounts.AccountDao;
import com.smec.users.accounts.AccountEntity;
import com.smec.users.base.DateUtils;
import com.smec.users.stats.StatsDao;
import com.smec.users.stats.StatsEntry;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class EventDaoTest {

    @Autowired
    @Rule
    public DatabaseCleanupRule rule;

    @BeforeEach
    public void cleanDB() { rule.execute(); }

    @Autowired
    private EventDao target;

    @Autowired
    private AccountDao accountDao;

    @Test
    @Transactional
    void fetchByGroup() {
        AccountEntity account = accountDao.store(new AccountEntity("me"));
        target.store(new EventEntity("asdf", DateUtils.today(), account));
        target.store(new EventEntity("asdf", DateUtils.today(), account));
        target.store(new EventEntity("asdf", DateUtils.today(), account));

        Assertions.assertThat(target.getStats(account.getId())).hasSize(1);
    }
}