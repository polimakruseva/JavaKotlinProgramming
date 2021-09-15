package com.solution;

public class ExpressionParseException extends Exception {
    public ExpressionParseException(String errorString) {
        super();
        errorString_ = errorString;
    }

    @Override
    public String toString() {
        return errorString_;
    }

    private String errorString_;
}
