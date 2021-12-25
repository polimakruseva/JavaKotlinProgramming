package com.polimakruseva.calculator;

public interface Literal extends Expression {
    double getValue() throws ExpressionParseException;
    String getLiteral();

    @Override
    default <T> T accept(ExpressionVisitor<T> visitor) throws ExpressionParseException {
        return visitor.visitLiteral(this);
    }
}
