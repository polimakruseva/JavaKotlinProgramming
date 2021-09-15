package com.solution;

public interface Expression {
    void setUnaryMinus();
    boolean isNegative();
    int getUnaryMinuses();

    Object accept(ExpressionVisitor visitor) throws ExpressionParseException;
}
