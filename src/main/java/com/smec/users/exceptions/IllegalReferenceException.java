package com.smec.users.exceptions;

public class IllegalReferenceException extends Exception {
    private static final long serialVersionUID = 1L;

    public IllegalReferenceException(String msg) {
        super(msg);
    }
}