package com.solution;

public interface Parser {
    Expression parseExpression(String input) throws ExpressionParseException;
}
