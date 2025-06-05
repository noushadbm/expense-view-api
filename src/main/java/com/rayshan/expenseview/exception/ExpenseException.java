package com.rayshan.expenseview.exception;

public class ExpenseException extends Exception {
    private static final long serialVersionUID = 1L;

    public ExpenseException(String message) {
        super(message);
    }

    public ExpenseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpenseException(Throwable cause) {
        super(cause);
    }
}
