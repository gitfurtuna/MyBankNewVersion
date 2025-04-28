
package com.mybank.authservice_my_bank.domain.exception;

public class PasswordIsIncorrectException extends RuntimeException{
    public PasswordIsIncorrectException(String message) {
        super(message);
    }
}
