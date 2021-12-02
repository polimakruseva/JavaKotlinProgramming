package com.solution;

public class VariableImpl implements Variable {
    public VariableImpl(String name) {
        mName = name;
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

    private final String mName;
    private boolean mUnaryMinus = false;
    private String mUnarySigns = "";

    @Override
    public String getVariable() {
        return mName;
    }
}
