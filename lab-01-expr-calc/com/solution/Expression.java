package com.solution;

public interface Expression {
    void setUnaryMinus();
    boolean isNegative();
    void setUnaryPlus();
    String getUnarySigns();

    <T> T accept(ExpressionVisitor<T> visitor) throws ExpressionParseException;
}
