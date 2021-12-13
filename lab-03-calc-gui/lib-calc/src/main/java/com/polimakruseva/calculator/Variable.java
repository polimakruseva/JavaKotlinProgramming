package com.polimakruseva.calculator;

public interface Variable extends Expression {
    String getVariable();

    @Override
    default <T> T accept(ExpressionVisitor<T> visitor) throws ExpressionParseException {
        return visitor.visitVariable(this);
    }
}
