package com.solution;

public class ParenthesisExpressionImpl implements ParenthesisExpression {
    ParenthesisExpressionImpl(Expression expression) {
        mInnerExpr = expression;
    }

    @Override
    public Expression getExpr() {
        return mInnerExpr;
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
        return mUnarySigns;
    }

    private Expression mInnerExpr;
    private boolean mUnaryMinus = false;
    private String mUnarySigns = "";
}
