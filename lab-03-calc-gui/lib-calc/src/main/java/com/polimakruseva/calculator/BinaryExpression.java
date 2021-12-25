package com.polimakruseva.calculator;

public interface BinaryExpression extends Expression {
    Expression getLeft();
    Expression getRight();
    BinOpKind getOperation();

    @Override
    default <T> T accept(ExpressionVisitor<T> visitor) throws ExpressionParseException {
        return visitor.visitBinaryExpression(this);
    }
}
