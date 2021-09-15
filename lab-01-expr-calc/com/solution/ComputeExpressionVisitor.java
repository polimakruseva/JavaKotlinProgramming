package com.solution;

import java.util.HashMap;
import java.util.Scanner;

public class ComputeExpressionVisitor implements ExpressionVisitor {
    @Override
    public Object visitBinaryExpression(BinaryExpression expr) throws ExpressionParseException {
        switch (expr.getOperation()) {
            case ADD: {
                return (Double) expr.getLeft().accept(this) + (Double) expr.getRight().accept(this);
            }
            case SUBTRACT: {
                return (Double) expr.getLeft().accept(this) - (Double) expr.getRight().accept(this);
            }
            case MULTIPLY: {
                return (Double) expr.getLeft().accept(this) * (Double) expr.getRight().accept(this);
            }
            case DIVIDE: {
                if ((Double) expr.getRight().accept(this) == 0) {
                    ExceptionHandler handler = new ExceptionHandler();
                    handler.handleException(TypeOfException.DIVISIONBYZERO);
                }
                return (Double) expr.getLeft().accept(this) / (Double) expr.getRight().accept(this);
            }
            default: {
                return 0;
            }
        }
    }

    @Override
    public Object visitLiteral(Literal expr) throws ExpressionParseException {
        if (expr.isVariable()) {
            if (!variables_.containsKey(expr.getLiteral())) {
                System.out.println("Enter value for '" + expr.getLiteral() + "'");
                Scanner scanner =  new Scanner(System.in);
                if (scanner.hasNextLine()) {
                    String number = scanner.next();
                    variables_.put(expr.getLiteral(), number);
                    expr.initializeVariable(number);
                }
            } else {
                expr.initializeVariable(variables_.get(expr.getLiteral()));
            }
        }
        if (expr.isNegative()) {
            return -expr.getValue();
        }
        return expr.getValue();
    }

    @Override
    public Object visitParenthesisExpression(ParenthesisExpression expr) throws ExpressionParseException {
        if (expr.isNegative()) {
            return -(Double) expr.getExpr().accept(this);
        }
        return expr.getExpr().accept(this);
    }

    private HashMap<String, String> variables_ = new HashMap<>();
}
