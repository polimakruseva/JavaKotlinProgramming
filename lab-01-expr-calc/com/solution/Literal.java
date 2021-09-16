package com.solution;

public interface Literal extends Expression {
    double getValue() throws ExpressionParseException;
    String getLiteral();
    boolean isVariable();

    @Override
    default Object accept(ExpressionVisitor visitor) throws ExpressionParseException {
        return visitor.visitLiteral(this);
    }
}
