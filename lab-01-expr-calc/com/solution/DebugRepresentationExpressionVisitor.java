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
                result += "div(";
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
        return getStringWithUnarySigns(expr) + "'" + expr.getLiteral() + "'";
    }

    @Override
    public Object visitParenthesisExpression(ParenthesisExpression expr) throws ExpressionParseException {
        String result = "";
        result += getStringWithUnarySigns(expr);
        return result + "paren-expr(" + expr.getExpr().accept(this) + ")";
    }

    @Override
    public Object visitVariable(Variable expr) {
        return getStringWithUnarySigns(expr) + "var[" + expr.getVariable() + "]";
    }

    private String getStringWithUnarySigns(Expression expr) {
        String unarySigns = expr.getUnarySigns();
        String result = "";
        for (int i = 0; i < unarySigns.length(); ++i) {
            if (unarySigns.charAt(i) == '+') {
                result += "unary-plus ";
            } else {
                result += "unary-min ";
            }
        }
        return result;
    }
}
