package com.solution;

public interface ExpressionVisitor {
    Object visitBinaryExpression(BinaryExpression expr) throws ExpressionParseException;
    Object visitLiteral(Literal expr) throws ExpressionParseException;
    Object visitParenthesisExpression(ParenthesisExpression expr) throws ExpressionParseException;
}
