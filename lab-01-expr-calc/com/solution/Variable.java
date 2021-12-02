package com.solution;

public interface Variable extends Expression {
    String getVariable();

    @Override
    default Object accept(ExpressionVisitor visitor) throws ExpressionParseException {
        return visitor.visitVariable(this);
    }
}
