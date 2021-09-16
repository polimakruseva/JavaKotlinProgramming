package com.solution;

import java.util.HashMap;

public class ComputeExpressionVisitor implements ExpressionVisitor {
    public ComputeExpressionVisitor(HashMap<String, String> variables) {
        mVariables = variables;
    }

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
        double result;
        if (expr.isVariable()) {
            result = Double.parseDouble(mVariables.get(expr.getLiteral()));
        } else {
            result = expr.getValue();
        }
        if (expr.isNegative()) {
            return -result;
        }
        return result;
    }

    @Override
    public Object visitParenthesisExpression(ParenthesisExpression expr) throws ExpressionParseException {
        if (expr.isNegative()) {
            return -(Double) expr.getExpr().accept(this);
        }
        return expr.getExpr().accept(this);
    }

    private HashMap<String, String> mVariables;
}
