package com.testapi.testapi.repository;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * The Account entity used as a table in the database.
 */
@Setter
@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"iban"})})
@ToString
@Getter
public class Account {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column
    private String iban;

    @Column
    private String owner;

    @Column
    private String currency;

    @Column
    private Double balance;

    @Column
    private Long lastUpdateDate;

}
