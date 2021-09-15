package com.solution;

public class BinaryExpressionImpl implements BinaryExpression {
    BinaryExpressionImpl(Expression left, Expression right, BinOpKind operation) {
        left_ = left;
        right_ = right;
        operation_ = operation;
    }

    @Override
    public Expression getLeft() {
        return left_;
    }

    @Override
    public Expression getRight() {
        return right_;
    }

    @Override
    public BinOpKind getOperation() {
        return operation_;
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

    private Expression left_;
    private Expression right_;
    private BinOpKind operation_;
    private boolean unaryMinus_ = false;
    private int numberOfMinuses_ = 0;
}
