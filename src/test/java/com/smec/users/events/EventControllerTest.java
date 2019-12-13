package com.smec.users.events;

import java.util.Date;
import java.util.List;

import com.smec.users.DatabaseCleanupRule;
import com.smec.users.accounts.AccountController;
import com.smec.users.accounts.AccountEntity;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javassist.tools.web.BadHttpRequest;

@SpringBootTest
public class EventControllerTest {

	@Autowired
	@Rule
	public DatabaseCleanupRule rule;

	 @BeforeEach
	 public void cleanDb() { rule.execute(); }


	@Autowired
	private EventController target;

	@Autowired
	private AccountController accountController;

	@Test
	public void canPersistAndLoad() throws Throwable {
		Assertions.assertThat(target.list()).isEmpty();

		AccountEntity account = accountController.store(new AccountEntity("someAccount"));

		for (int i = 0; i < 3; i++) {
			String name = "name " + i;
			EventEntity result = target.store(new EventDto(name, account.getId()));
			Assertions.assertThat(result.getType()).isEqualTo(name);
			Assertions.assertThat(result.getTime()).isNotNull().isBetween(new Date(System.currentTimeMillis() - 1000),
					new Date());
			Assertions.assertThat(result.getId()).isNotNull();
		}

		List<EventEntity> listResult = target.list();
		Assertions.assertThat(listResult).hasSize(3);
	}

	@Test
	public void persistForUnknownAccount() throws Throwable {
		Assertions.assertThatThrownBy(() -> target.store(new EventDto("some", -1))).isInstanceOf(BadHttpRequest.class);
	}

}
