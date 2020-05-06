package com.example.springjpa.exception;

public class IssueCardExpiration extends Exception {
    public IssueCardExpiration() {
    }

    public IssueCardExpiration(String message) {
        super(message);
    }

    public IssueCardExpiration(String message, Throwable cause) {
        super(message, cause);
    }

    public IssueCardExpiration(Throwable cause) {
        super(cause);
    }

    public IssueCardExpiration(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
