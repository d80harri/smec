package com.smec.users.stats;

import com.smec.users.DatabaseCleanupRule;
import com.smec.users.accounts.AccountController;

import com.smec.users.accounts.AccountEntity;
import com.smec.users.accounts.AccountService;
import com.smec.users.base.DateUtils;
import com.smec.users.base.IClock;
import com.smec.users.events.EventController;
import com.smec.users.events.EventDto;
import com.smec.users.events.EventEntity;
import com.smec.users.events.EventService;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class StatsControllerTest {

	@Autowired
	@Rule
	public DatabaseCleanupRule rule;

	 @BeforeEach
	 public void cleanDb() { rule.execute(); }


	@Autowired
	private StatsController target;

	@Autowired
	private EventController eventService;

	@Autowired
	private AccountController accountService;

	@MockBean
	private IClock mockedClock;
	@Test
	public void canArchivateAndRead() throws Throwable {
		AccountEntity account = accountService.store(new AccountEntity("some"));
		int accountId = account.getId();

		Assertions.assertThat(target.list(accountId)).hasSize(0);

		Mockito.when(mockedClock.now()).thenReturn(DateUtils.yesterday());
		eventService.store(new EventDto("some event", accountId));
		eventService.store(new EventDto("some event", accountId));
		eventService.store(new EventDto("some other event", accountId));
		eventService.store(new EventDto("some other event", accountId));

		Mockito.when(mockedClock.now()).thenReturn(DateUtils.tomorrow());
		eventService.store(new EventDto("some event", accountId));
		eventService.store(new EventDto("some event", accountId));
		eventService.store(new EventDto("some other event", accountId));

		Assertions.assertThat(eventService.list()).hasSize(7);
		Assertions.assertThat(target.list(accountId)).hasSize(4);


	}
}
