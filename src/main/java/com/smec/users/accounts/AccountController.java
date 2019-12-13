package com.smec.users.accounts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;

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

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public AccountEntity get(@PathVariable("id") int id) {
        return accountService.get(id);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") int id, @RequestBody AccountEntity result) {
        result.setId(id);
        accountService.update(result);
    }
}