package com.solution;

public interface ParenthesisExpression extends Expression {
    Expression getExpr();

    @Override
    default Object accept(ExpressionVisitor visitor) throws ExpressionParseException {
        return visitor.visitParenthesisExpression(this);
    }
}
