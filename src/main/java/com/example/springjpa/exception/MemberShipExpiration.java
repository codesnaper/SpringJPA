package com.example.springjpa.exception;

public class MemberShipExpiration extends Exception {
    public MemberShipExpiration() {
    }

    public MemberShipExpiration(String message) {
        super(message);
    }

    public MemberShipExpiration(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberShipExpiration(Throwable cause) {
        super(cause);
    }

    public MemberShipExpiration(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
