package com.testapi.testapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * The type Account not found exception.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Account not found.")
public class AccountNotFoundException extends RuntimeException{
}
