package com.testapi.testapi.service;

import com.testapi.testapi.dto.AccountDto;
import com.testapi.testapi.dto.RatesWrapper;
import com.testapi.testapi.exceptions.AccountNotFoundException;
import com.testapi.testapi.exceptions.IbanAlreadyExistException;
import com.testapi.testapi.repository.Account;
import com.testapi.testapi.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

/**
 * The Account service.
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private final ExchangeRateService exchangeRateService;
    private final ModelMapper modelMapper;
    /**
     * Save account.
     *
     * @param accountDto the account dto
     */
    @ExceptionHandler(value = IbanAlreadyExistException.class)
    public void saveAccount(final AccountDto accountDto) {
        accountRepository.findAll().forEach(savedAccount ->{
            if(savedAccount.getIban().equals(accountDto.getIban())) {
                throw new IbanAlreadyExistException();
            }
        });

        Account account = modelMapper.map(accountDto, Account.class);
        account.setLastUpdateDate(Instant.now().toEpochMilli());
        accountRepository.save(account);
    }

    /**
     * Find account by Iban
     *
     * @param iban the iban
     * @return the account dto
     */
    @ExceptionHandler(value = AccountNotFoundException.class)
    public AccountDto findOneByIban(final String iban) {

        final Iterable<Account> accounts =  accountRepository.findAll();
        for (Account account : accounts) {
            if (account.getIban().equals(iban)) {
                return modelMapper.map(account, AccountDto.class);
            }
        }
       throw new AccountNotFoundException();
    }

    /**
     * Returns AccountDto with ammount converted to EUR.
     *
     * @param iban the iban
     * @return the account dto
     */
    public AccountDto convertToEur (final String iban){

        final AccountDto account = findOneByIban(iban);

        if(account.getCurrency().equals(Currency.EUR.name())){
            log.info("Account is already in EUR.");
            return account;
        }
        log.info("Converting account to EUR");
        RatesWrapper ratesWrapper = exchangeRateService.getRates();

        final double eurCoeff = ratesWrapper.getRates().get(account.getCurrency());

        account.setCurrency(Currency.EUR.name());
        account.setBalance(account.getBalance()/eurCoeff);

        return account;
    }

}
