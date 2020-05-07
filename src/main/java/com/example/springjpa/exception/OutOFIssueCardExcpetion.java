package com.example.springjpa.exception;

public class OutOFIssueCardExcpetion extends Exception {

    public OutOFIssueCardExcpetion() {
    }

    public OutOFIssueCardExcpetion(String message) {
        super(message);
    }

    public OutOFIssueCardExcpetion(String message, Throwable cause) {
        super(message, cause);
    }

    public OutOFIssueCardExcpetion(Throwable cause) {
        super(cause);
    }

    public OutOFIssueCardExcpetion(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
