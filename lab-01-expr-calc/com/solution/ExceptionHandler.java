package com.solution;

enum TypeOfException {
    SYNTAXERROR,
    UNBALANCEDPARENS,
    DIVISIONBYZERO,
    NOEXPRESSION
}

public class ExceptionHandler {
    public void handleException(TypeOfException exception) throws ExpressionParseException {
        String message;
        switch (exception) {
            case SYNTAXERROR: {
                message = "Syntax error";
                break;
            }
            case NOEXPRESSION: {
                message = "No expression was entered";
                break;
            }
            case DIVISIONBYZERO: {
                message = "Division by zero";
                break;
            }
            case UNBALANCEDPARENS: {
                message = "No closing parenthesis";
                break;
            }
            default: {
                message = "Something went wrong. Please check entered expression";
                break;
            }
        }
        throw new ExpressionParseException(message);
    }
}
