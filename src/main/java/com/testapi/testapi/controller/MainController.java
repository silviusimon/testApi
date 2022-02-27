package com.testapi.testapi.controller;

import com.testapi.testapi.dto.AccountDto;
import com.testapi.testapi.exceptions.AccountNotFoundException;
import com.testapi.testapi.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Slf4j
@Service
@Controller
@RequestMapping(path="/accounts")
public class MainController {

    @Autowired
    private AccountService accountService;

    @ExceptionHandler(value = AccountNotFoundException.class)
    @GetMapping(path="/get/{iban}")
    @ResponseBody
    public AccountDto getAccount(final @PathVariable String iban) {
       return accountService.findOneByIban(iban);
    }

    @PostMapping(path = "/add", consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody String addAccount(@RequestBody AccountDto accountDto) {

        log.info("received add account accountDto={}", accountDto);
        accountService.saveAccount(accountDto);
        return "Account created.";
    }

    @GetMapping(path="/getBalanceAsEur/{iban}")
    @ResponseBody
    public AccountDto getBalanceAsEur(final @PathVariable String iban) {

        final AccountDto account = accountService.convertToEur(iban);
        return account;
    }
}
