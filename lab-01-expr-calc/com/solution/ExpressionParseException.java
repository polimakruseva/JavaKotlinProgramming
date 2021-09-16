package com.solution;

public class ExpressionParseException extends Exception {
    public ExpressionParseException(String errorString) {
        super();
        mErrorString = errorString;
    }

    @Override
    public String toString() {
        return mErrorString;
    }

    private String mErrorString;
}
