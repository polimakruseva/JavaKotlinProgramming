package com.solution;

public class BinaryExpressionImpl implements BinaryExpression {
    BinaryExpressionImpl(Expression left, Expression right, BinOpKind operation) {
        mLeft = left;
        mRight = right;
        mOperation = operation;
    }

    @Override
    public Expression getLeft() {
        return mLeft;
    }

    @Override
    public Expression getRight() {
        return mRight;
    }

    @Override
    public BinOpKind getOperation() {
        return mOperation;
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

    private Expression mLeft;
    private Expression mRight;
    private BinOpKind mOperation;
    private boolean mUnaryMinus = false;
    private String mUnarySigns = "";
}
