package com.solution;

public interface ExpressionVisitor<T> {
    T visitBinaryExpression(BinaryExpression expr) throws ExpressionParseException;
    T visitLiteral(Literal expr) throws ExpressionParseException;
    T visitParenthesisExpression(ParenthesisExpression expr) throws ExpressionParseException;
    T visitVariable(Variable expr) throws ExpressionParseException;
}
