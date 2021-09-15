package com.solution;

public class ParenthesisExpressionImpl implements ParenthesisExpression {
    ParenthesisExpressionImpl(Expression expression) {
        innerExpr_ = expression;
    }

    @Override
    public Expression getExpr() {
        return innerExpr_;
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

    private Expression innerExpr_;
    private boolean unaryMinus_ = false;
    private int numberOfMinuses_ = 0;
}
