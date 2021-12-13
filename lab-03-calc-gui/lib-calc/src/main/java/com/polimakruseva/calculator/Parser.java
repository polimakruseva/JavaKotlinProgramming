package com.polimakruseva.calculator;

public interface Parser {
    Expression parseExpression(String input) throws ExpressionParseException;
}
