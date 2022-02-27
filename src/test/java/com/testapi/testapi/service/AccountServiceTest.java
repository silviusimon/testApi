package com.testapi.testapi.service;

import com.testapi.testapi.dto.AccountDto;
import com.testapi.testapi.exceptions.AccountNotFoundException;
import com.testapi.testapi.repository.Account;
import com.testapi.testapi.repository.AccountRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.sql.Date;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private ExchangeRateService exchangeRateService;

    private ModelMapper modelMapper = new ModelMapper();

    private AccountService accountService;

    @Before
    public void setUp(){
        accountService = new AccountService(accountRepository, exchangeRateService, modelMapper);
    }

    @Test
    public void testAccountNotFound(){
        String iban = "testIban";

        when(accountRepository.findAll()).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> accountService.findOneByIban(iban)).isInstanceOf(AccountNotFoundException.class);
    }

    @Test
    public void testAccountFound(){
        String iban = "testIban";
        Account account = new Account();
        account.setBalance(200.0);
        account.setIban(iban);
        account.setOwner("test owner");

        AccountDto accountDto = modelMapper.map(account, AccountDto.class);

        List<Account> accounts = Lists.newArrayList(account);
        when(accountRepository.findAll()).thenReturn(accounts);

        assertThat(accountService.findOneByIban(iban)).isEqualTo(accountDto);
    }

    @Test
    public void shouldMapObject(){
        long timeStamp = Instant.now().toEpochMilli();
        Account account = new Account();
        account.setId(2);
        account.setBalance(200.0);
        account.setIban("testIban");
        account.setOwner("test owner");
        account.setLastUpdateDate(timeStamp);

        AccountDto accountDto = modelMapper.map(account, AccountDto.class);

        assertThat(account.getId()).isEqualTo(accountDto.getId());
        assertThat(account.getIban()).isEqualTo(accountDto.getIban());
        assertThat(account.getBalance()).isEqualTo(accountDto.getBalance());
        assertThat(new Date(account.getLastUpdateDate())).isEqualTo(accountDto.getLastUpdateDate());
    }
}