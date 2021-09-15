package com.solution;

public class LiteralImpl implements Literal {
    LiteralImpl(String literal) {
        value_ = literal;
        isVariable_ = !Character.isDigit(value_.charAt(0));
    }

    @Override
    public double getValue() throws ExpressionParseException {
        double result = 0;
        try {
            result = Double.parseDouble(value_);
        } catch (NumberFormatException exception) {
            ExceptionHandler handler = new ExceptionHandler();
            handler.handleException(TypeOfException.SYNTAXERROR);
        }
        return result;
    }

    @Override
    public String getLiteral() {
        return value_;
    }

    @Override
    public boolean isVariable() {
        return isVariable_;
    }

    @Override
    public void initializeVariable(String number) {
        value_ = number;
        isVariable_ = false;
    }

    @Override
    public void setUnaryMinus() {
        unaryMinus_ = !unaryMinus_;
        ++numberOfMinuses_;
    }

    @Override
    public boolean isNegative() {
        return unaryMinus_;
    }

    @Override
    public int getUnaryMinuses() {
        return numberOfMinuses_;
    }

    private String value_;
    private boolean isVariable_;
    private boolean unaryMinus_ = false;
    private int numberOfMinuses_ = 0;
}
