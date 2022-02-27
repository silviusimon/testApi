package com.testapi.testapi.service;

import com.testapi.testapi.dto.RatesWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.testapi.testapi.service.ServiceConfig.DEFAULT_EUR_RATE;

/**
 * The type Exchange rate service.
 */
@Service
@RequiredArgsConstructor
public class ExchangeRateService {

    private final CircuitBreaker circuitBreaker;
    private final RestTemplate restTemplate;
    private final ServiceConfig serviceConfig;

    /**
     * Get rates rates wrapper.
     *
     * @return the rates wrapper
     */
    @Cacheable("getRates")
    public RatesWrapper getRates(){
        return circuitBreaker.run(() -> restTemplate.getForObject(serviceConfig.getGetExchangeRatesApi(),
                RatesWrapper.class), throwable -> getDefaultRates() );
    }

    private RatesWrapper getDefaultRates() {
        Map<String, Double> eurRate = new HashMap<>();
        eurRate.put(Currency.EUR.name(), DEFAULT_EUR_RATE);
        RatesWrapper ratesWrapper = new RatesWrapper();
        ratesWrapper.setRates(eurRate);
        return ratesWrapper;
    }
}
