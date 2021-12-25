package com.polimakruseva.calculator;

public class LiteralImpl implements Literal {
    LiteralImpl(String literal) {
        mValue = literal;
    }

    @Override
    public double getValue() throws ExpressionParseException {
        double result = 0;
        try {
            result = Double.parseDouble(mValue);
        } catch (NumberFormatException exception) {
            ExceptionHandler handler = new ExceptionHandler();
            handler.handleException(TypeOfException.SYNTAXERROR);
        }
        return result;
    }

    @Override
    public String getLiteral() {
        return mValue;
    }

    @Override
    public void setUnaryMinus() {
        mUnaryMinus = !mUnaryMinus;
        mUnarySigns += "-";
    }

    @Override
    public boolean isNegative() {
        return mUnaryMinus;
    }

    @Override
    public void setUnaryPlus() {
        mUnarySigns += "+";
    }

    @Override
    public String getUnarySigns() {
        return new StringBuilder(mUnarySigns).reverse().toString();
    }

    private final String mValue;
    private boolean mUnaryMinus = false;
    private String mUnarySigns = "";
}
