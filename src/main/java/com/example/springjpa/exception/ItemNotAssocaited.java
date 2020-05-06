package com.example.springjpa.exception;

public class ItemNotAssocaited extends Exception {
    public ItemNotAssocaited() {
        super();
    }

    public ItemNotAssocaited(String message) {
        super(message);
    }

    public ItemNotAssocaited(String message, Throwable cause) {
        super(message, cause);
    }

    public ItemNotAssocaited(Throwable cause) {
        super(cause);
    }

    protected ItemNotAssocaited(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
