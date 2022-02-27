package com.testapi.testapi.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.sql.Date;

@ToString
@Data
@EqualsAndHashCode
public class AccountDto {

    private Integer Id;

    private String iban;

    private String owner;

    private String currency;

    private Double balance;

    private Date lastUpdateDate;
}
