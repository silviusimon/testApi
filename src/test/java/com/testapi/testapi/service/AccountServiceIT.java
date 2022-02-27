package com.testapi.testapi.service;


import com.testapi.testapi.configuration.AccountTestConfiguration;
import com.testapi.testapi.dto.AccountDto;
import com.testapi.testapi.dto.RatesWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@Import(AccountTestConfiguration.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceIT {


    @MockBean
    RestTemplate restTemplate;
    @Autowired
    AccountService accountService;

    @Test
    public void test() {

        long lastUpdateDate = Instant.now().toEpochMilli();
        String testIban = "testIban123412";
        double eurRate = 4.9;

        //rates mock
        final RatesWrapper ratesWrapperMock = Mockito.mock(RatesWrapper.class);
        Map<String, Double> rates = new HashMap<>();
        rates.put(Currency.RON.name(), eurRate);
        when(ratesWrapperMock.getRates()).thenReturn(rates);
        when(restTemplate.getForObject(anyString(), Mockito.eq(RatesWrapper.class))).thenReturn(ratesWrapperMock);

        //input account
        AccountDto accountDto = new AccountDto();
        accountDto.setId(1);
        accountDto.setBalance(2000.0);
        accountDto.setIban(testIban);
        accountDto.setOwner("Ion Popescu");
        accountDto.setCurrency(Currency.RON.name());
        accountDto.setLastUpdateDate(new Date(lastUpdateDate));

        accountService.saveAccount(accountDto);

        assertThat(accountService.findOneByIban(testIban).getId()).isEqualTo(accountDto.getId());


        //when
        AccountDto convertedDto1 = accountService.convertToEur(testIban);
        AccountDto convertedDto2 = accountService.convertToEur(testIban);


        //then
        verify(restTemplate, Mockito.atMostOnce()) // 2nd call was cached
        .getForObject(anyString(), Mockito.eq(RatesWrapper.class));
        assertThat(convertedDto1.getBalance()).isEqualTo(accountDto.getBalance()/eurRate);
        assertThat(convertedDto2.getBalance()).isEqualTo(accountDto.getBalance()/eurRate);
    }

}
