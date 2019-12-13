package com.smec.users.stats;

import com.smec.users.DatabaseCleanupRule;
import com.smec.users.accounts.AccountEntity;
import com.smec.users.accounts.AccountService;
import com.smec.users.base.DateUtils;
import com.smec.users.base.IClock;
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

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class StatsServiceTest {

	@Autowired
	@Rule
	public DatabaseCleanupRule rule;

	 @BeforeEach
	 public void cleanDB() { rule.execute(); }


	@Autowired
	private StatsService target;

	@Autowired
	private EventService eventService;

	@Autowired
	private AccountService accountService;

	@MockBean
	private IClock mockedClock;

	@Test
	public void canArchivateAndRead() throws Throwable {
		AccountEntity account = accountService.store(new AccountEntity("some"));
		int accountId = account.getId();

		Assertions.assertThat(target.fetchAll(accountId)).hasSize(0);

		Mockito.when(mockedClock.now()).thenReturn(DateUtils.yesterday());
		eventService.store(new EventEntity("some event"), accountId);
		eventService.store(new EventEntity("some event"), accountId);
		eventService.store(new EventEntity("some other event"), accountId);
		eventService.store(new EventEntity("some other event"), accountId);

		Mockito.when(mockedClock.now()).thenReturn(DateUtils.tomorrow());
		eventService.store(new EventEntity("some event"), accountId);
		eventService.store(new EventEntity("some event"), accountId);
		eventService.store(new EventEntity("some other event"), accountId);

		Assertions.assertThat(eventService.fetchAllEvents()).hasSize(7);
		Assertions.assertThat(target.fetchAll(accountId)).hasSize(4);

		target.archivateOldEvents(DateUtils.today());

		Assertions.assertThat(eventService.fetchAllEvents()).hasSize(3);
		Assertions.assertThat(target.fetchAll(accountId)).hasSize(4);

		target.archivateOldEvents(DateUtils.today(2));

		Assertions.assertThat(eventService.fetchAllEvents()).hasSize(0);
		Assertions.assertThat(target.fetchAll(accountId)).hasSize(4);
	}
	
	@Test
	public void canMerge() {
		AccountEntity account1 = new AccountEntity();
		AccountEntity account2 = new AccountEntity();

		List<StatsEntry> listA = Arrays.asList(
				new StatsEntry("typeA", 2000, 1, 1, account1, 1),
				new StatsEntry("typeA", 2000, 1, 2, account1, 1),
				new StatsEntry("typeA", 2000, 1, 2, account2, 1),
				new StatsEntry("typeA", 2000, 1, 1, account2, 1),
				new StatsEntry("typeB", 2000, 1, 1, account1, 1),
				new StatsEntry("typeB", 2000, 1, 1, account2, 1),
				new StatsEntry("typeB", 2000, 1, 2, account1, 1),
				new StatsEntry("typeB", 2000, 1, 2, account2, 1),
				new StatsEntry("typeA", 2000, 1, 2, account1, 1),
				new StatsEntry("typeA", 2000, 1, 2, account2, 1),
				new StatsEntry("typeA", 2000, 1, 3, account1, 1),
				new StatsEntry("typeA", 2000, 1, 3, account2, 1),
				new StatsEntry("typeB", 2000, 1, 2, account1, 1),
				new StatsEntry("typeB", 2000, 1, 2, account2, 1),
				new StatsEntry("typeB", 2000, 1, 3, account1, 1),
				new StatsEntry("typeB", 2000, 1, 3, account2, 1)
		);

		Set<StatsEntry> result = StatsService.merge(listA);

		Assertions.assertThat(result).hasSize(12);
		Assertions.assertThat(result.stream().filter(s -> s.getAccount() == account1)).hasSize(6);
		Assertions.assertThat(result.stream().filter(s -> s.getAccount() == account2)).hasSize(6);
	}
}
