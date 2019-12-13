package com.smec.users.accounts;

import java.util.List;

import com.smec.users.DatabaseCleanupRule;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AccountControllerTest {

	@Autowired
	public DatabaseCleanupRule rule;

	@BeforeEach
	public void cleanDb() {
		rule.execute();
	}

	@Autowired
	private AccountController target;

	@Test
	public void canPersistAndLoad() {
		Assertions.assertThat(target.list()).isEmpty();

		for (int i = 0; i < 3; i++) {
			String name = "name " + i;
			AccountEntity result = target.store(new AccountEntity(name));
			Assertions.assertThat(result.getName()).isEqualTo(name);
			Assertions.assertThat(result.getId()).isNotNull();
		}

		List<AccountEntity> listResult = target.list();
		Assertions.assertThat(listResult).hasSize(3);
	}

}
