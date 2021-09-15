package com.solution;

import java.util.concurrent.LinkedBlockingDeque;

public interface Parser {
    Expression parseExpression(String input) throws ExpressionParseException;
}
