package com.solution;

import java.util.HashMap;

public class ComputeExpressionVisitor implements ExpressionVisitor<Double> {
    public ComputeExpressionVisitor(HashMap<String, String> variables) {
        mVariables = variables;
    }

    @Override
    public Double visitBinaryExpression(BinaryExpression expr) throws ExpressionParseException {
        switch (expr.getOperation()) {
            case ADD: {
                return expr.getLeft().accept(this) + expr.getRight().accept(this);
            }
            case SUBTRACT: {
                return expr.getLeft().accept(this) - expr.getRight().accept(this);
            }
            case MULTIPLY: {
                return expr.getLeft().accept(this) * expr.getRight().accept(this);
            }
            case DIVIDE: {
                if (expr.getRight().accept(this) == 0) {
                    ExceptionHandler handler = new ExceptionHandler();
                    handler.handleException(TypeOfException.DIVISIONBYZERO);
                }
                return expr.getLeft().accept(this) / expr.getRight().accept(this);
            }
            default: {
                return 0.;
            }
        }
    }

    @Override
    public Double visitLiteral(Literal expr) throws ExpressionParseException {
        double result = expr.getValue();
        if (expr.isNegative()) {
            return -result;
        }
        return result;
    }

    @Override
    public Double visitParenthesisExpression(ParenthesisExpression expr) throws ExpressionParseException {
        if (expr.isNegative()) {
            return -(Double) expr.getExpr().accept(this);
        }
        return expr.getExpr().accept(this);
    }

    @Override
    public Double visitVariable(Variable expr) {
        Double result = Double.parseDouble(mVariables.get(expr.getVariable()));
        if (expr.isNegative()) {
            return -result;
        }
        return result;
    }

    private final HashMap<String, String> mVariables;
}
