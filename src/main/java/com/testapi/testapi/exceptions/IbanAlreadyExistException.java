package com.testapi.testapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Iban already exist exception.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Account already exists")
public class IbanAlreadyExistException extends RuntimeException {
}
