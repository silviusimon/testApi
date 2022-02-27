package com.testapi.testapi.service;

import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Getter
@Configuration
public class ServiceConfig {

    public final static Double DEFAULT_EUR_RATE = 4.9;

    @Value("http://api.exchangeratesapi.io/latest?access_key=${com.testapi.exchangerate.api.key}")
    private String getExchangeRatesApi;


    @Bean
    public RestTemplate getRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public CircuitBreaker circuitBreaker(final CircuitBreakerFactory circuitBreakerFactory) {
            return circuitBreakerFactory.create("circuitbreaker");
    }
}
