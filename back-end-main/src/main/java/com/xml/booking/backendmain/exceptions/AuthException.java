package com.xml.booking.backendmain.exceptions;

public class AuthException extends RuntimeException {
    public AuthException() {
    }

    public AuthException(String s) {
        super(s);
    }
}
