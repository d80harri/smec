package com.smec.users;

import com.smec.users.accounts.AccountDao;
import com.smec.users.accounts.AccountService;
import com.smec.users.events.EventService;
import com.smec.users.events.IEventService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class AccountsApplication {
	private static final Logger logger = LoggerFactory.getLogger(AccountsApplication.class);

	@Autowired
	private IEventService eventService;

	private int accountDeletionInterval = 1000 * 60 * 60 * 30; // TODO: configurable

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

	@Scheduled(fixedDelayString = "${accounts.deleteOldJob.delay}")
	public void deleteOldJob() {
		logger.info("Timed job " + this.getClass() + " running.");
		int deleted = eventService.deleteOld(System.currentTimeMillis() - accountDeletionInterval);
		logger.info(deleted + " old events removed");
	}

}
