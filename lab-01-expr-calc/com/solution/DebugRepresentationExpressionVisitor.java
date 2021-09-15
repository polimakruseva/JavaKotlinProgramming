package com.solution;

public class DebugRepresentationExpressionVisitor implements ExpressionVisitor {
    private DebugRepresentationExpressionVisitor() {}

    public static final ExpressionVisitor INSTANCE = new DebugRepresentationExpressionVisitor();

    @Override
    public Object visitBinaryExpression(BinaryExpression expr) throws ExpressionParseException {
        String result = "";
        BinOpKind op = expr.getOperation();
        switch(op) {
            case ADD: {
                result += "add(";
                break;
            }
            case SUBTRACT: {
                result += "sub(";
                break;
            }
            case MULTIPLY: {
                result += "mul(";
                break;
            }
            case DIVIDE: {
                result += "div";
                break;
            }
            default: {
                break;
            }
        }
        result += expr.getLeft().accept(this) + ", " + expr.getRight().accept(this) +
                ")";
        return result;
    }

    @Override
    public Object visitLiteral(Literal expr) {
        String result = "";
        if (expr.isVariable()) {
            result += "var[";
        } else {
            result += "'";
        }
        if (expr.getUnaryMinuses() != 0) {
            result += getStringWithMinuses(expr);
        }
        result += expr.getLiteral();
        if (expr.isVariable()) {
            result += "]";
        } else {
            result += "'";
        }
        return result;
    }

    @Override
    public Object visitParenthesisExpression(ParenthesisExpression expr) throws ExpressionParseException {
        String result = "";
        if (expr.getUnaryMinuses() != 0) {
            result += getStringWithMinuses(expr);
        }
        return result + "paren-expr(" + expr.getExpr().accept(this) + ")";
    }

    private String getStringWithMinuses(Expression expr) {
        String result = "";
        for (int i = 0; i < expr.getUnaryMinuses(); ++i) {
            result += "unary-min ";
        }
        return result;
    }
}
