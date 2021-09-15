package com.solution;

import java.util.Objects;

enum TokenType {
    NONE,
    DELIMITER,
    VARIABLE,
    NUMBER
}

public class ParserImpl implements Parser {
    @Override
    public Expression parseExpression(String input) throws ExpressionParseException {
        input_ = input;
        currentIndex_ = 0;
        getToken();

        if (token_.equals(endOfExpression)) {
            handler.handleException(TypeOfException.NOEXPRESSION);
        }

        Expression result = addOrSubtract();

        if (!token_.equals(endOfExpression)) {
            handler.handleException(TypeOfException.SYNTAXERROR);
        }

        return result;
    }

    private Expression addOrSubtract() throws ExpressionParseException {
        Expression left = multiplyOrDivide();
        char operation = token_.charAt(0);
        while (operation == '-' || operation == '+') {
            getToken();
            Expression right = multiplyOrDivide();
            switch (operation) {
                case '+': {
                    return new BinaryExpressionImpl(left, right, BinOpKind.ADD);
                }
                case '-': {
                    return new BinaryExpressionImpl(left, right, BinOpKind.SUBTRACT);
                }
            }
            operation = token_.charAt(0);
        }
        return left;
    }

    private Expression multiplyOrDivide() throws ExpressionParseException {
        Expression left = unaryOperators();
        char operation = token_.charAt(0);
        while (operation == '*' || operation == '/') {
            getToken();
            Expression right = unaryOperators();
            switch (operation) {
                case '*': {
                    return new BinaryExpressionImpl(left, right, BinOpKind.MULTIPLY);
                }
                case '/': {
                    return new BinaryExpressionImpl(left, right, BinOpKind.DIVIDE);
                }
            }
            operation = token_.charAt(0);
        }
        return left;
    }

    private Expression unaryOperators() throws ExpressionParseException {
        String operation = "";
        if (tokenType_ == TokenType.DELIMITER && (Objects.equals(token_, "-")
                || Objects.equals(token_, "+"))) {
            operation = token_;
            getToken();
        }
        Expression result = parenthesisExpression();
        if (operation.equals("-")) {
            result.setUnaryMinus();
        }
        return result;
    }

    private Expression parenthesisExpression() throws ExpressionParseException {
        if (token_.equals("(")) {
            getToken();
            Expression result = addOrSubtract();
            if (!token_.equals(")")) {
                handler.handleException(TypeOfException.UNBALANCEDPARENS);
            }
            getToken();
            return new ParenthesisExpressionImpl(result);
        } else {
            return getLiteral();
        }
    }

    private Expression getLiteral() {
        LiteralImpl result = new LiteralImpl(token_);
        getToken();
        return result;
    }

    private void getToken() {
        tokenType_ = TokenType.NONE;
        token_ = "";

        if(currentIndex_ == input_.length()) {
            token_ = endOfExpression;
            return;
        }

        while(currentIndex_ < input_.length() && Character.isWhitespace(input_.charAt(currentIndex_))) {
            ++currentIndex_;
        }

        if(currentIndex_ == input_.length()) {
            token_ = endOfExpression;
            return;
        }

        if(isDelim(input_.charAt(currentIndex_))) {
            token_ += input_.charAt(currentIndex_);
            ++currentIndex_;
            tokenType_ = TokenType.DELIMITER;
        } else if(Character.isLetter(input_.charAt(currentIndex_))) {
            while(!isDelim(input_.charAt(currentIndex_))) {
                token_ += input_.charAt(currentIndex_);
                ++currentIndex_;
                if(currentIndex_ >= input_.length()) {
                    break;
                }
            }
            tokenType_ = TokenType.VARIABLE;
        } else if (Character.isDigit(input_.charAt(currentIndex_))) {
            while(!isDelim(input_.charAt(currentIndex_))){
                token_ += input_.charAt(currentIndex_);
                ++currentIndex_;
                if(currentIndex_ >= input_.length()) {
                    break;
                }
            }
            tokenType_ = TokenType.NUMBER;
        } else {
            token_ = endOfExpression;
        }
    }

    private boolean isDelim(char charAt) {
        return (" +-/*()".indexOf(charAt)) != -1;
    }

    private String input_;
    private int currentIndex_;
    private String token_;
    TokenType tokenType_;
    private final ExceptionHandler handler = new ExceptionHandler();

    final String endOfExpression = "\0";
}
