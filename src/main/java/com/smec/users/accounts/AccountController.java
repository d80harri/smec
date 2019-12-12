package com.smec.users.accounts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/accounts")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @GetMapping()
    public List<AccountEntity> list() {
        return accountService.fetchAllAccounts();
    }

    @PostMapping
    public AccountEntity store(@RequestBody AccountEntity entity) {
        return accountService.store(entity);
    }
}