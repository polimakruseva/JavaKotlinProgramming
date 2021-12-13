package com.polimakruseva.calculator;

public interface ParenthesisExpression extends Expression {
    Expression getExpr();

    @Override
    default <T> T accept(ExpressionVisitor<T> visitor) throws ExpressionParseException {
        return visitor.visitParenthesisExpression(this);
    }
}
