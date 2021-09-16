package com.solution;

public interface Expression {
    void setUnaryMinus();
    boolean isNegative();
    void setUnaryPlus();
    String getUnarySigns();

    Object accept(ExpressionVisitor visitor) throws ExpressionParseException;
}
