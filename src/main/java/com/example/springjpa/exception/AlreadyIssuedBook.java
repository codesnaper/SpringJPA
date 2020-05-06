package com.example.springjpa.exception;

public class AlreadyIssuedBook extends Exception {
    public AlreadyIssuedBook() {
        super();
    }

    public AlreadyIssuedBook(String message) {
        super(message);
    }

    public AlreadyIssuedBook(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyIssuedBook(Throwable cause) {
        super(cause);
    }

    protected AlreadyIssuedBook(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
