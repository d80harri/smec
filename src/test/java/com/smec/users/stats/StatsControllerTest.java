package com.smec.users.stats;

import java.util.Date;
import java.util.List;

import com.smec.users.DatabaseCleanupRule;
import com.smec.users.accounts.AccountController;
import com.smec.users.accounts.AccountEntity;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javassist.tools.web.BadHttpRequest;

@SpringBootTest
public class StatsControllerTest {

	@Autowired
	@Rule
	public DatabaseCleanupRule rule;
	/*
	 * @AfterEach public void after() { rule.execute(); }
	 */

	@Autowired
	private StatsController target;

	@Autowired
	private AccountController accountController;

	@Test
	public void canPersistAndLoad() throws Throwable {
		AccountEntity account = accountController.store(new AccountEntity("someAccount"));
		Assertions.assertThat(target.list(account.getId())).isEmpty();

		for (int i = 0; i < 3; i++) {
			String name = "name " + i;
			Date date = new Date(i);
			StatsDto result = target.store(account.getId(), new StatsDto(name, date));
			Assertions.assertThat(result.getType()).isEqualTo(name);
			Assertions.assertThat(result.getTime()).isEqualTo(date);
			Assertions.assertThat(result.getId()).isNotNull();
		}

		List<StatsDto> listResult = target.list(account.getId());
		Assertions.assertThat(listResult).hasSize(3);
	}

	@Test
	public void persistForUnknownAccount() throws Throwable {
		Assertions.assertThatThrownBy(() -> target.store(-1, new StatsDto("some", new Date())))
				.isInstanceOf(BadHttpRequest.class);
	}

}
