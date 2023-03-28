package com.infogain.rewards.v1.exceptions;

public class TransactionNotFoundException extends RuntimeException {

    public TransactionNotFoundException(String exception) {
        super(exception);
    }
}
