package com.solution;

public class ToStringVisitor implements ExpressionVisitor {
    private ToStringVisitor() {}

    public static final Object INSTANCE = new ToStringVisitor();

    @Override
    public Object visitBinaryExpression(BinaryExpression expr) throws ExpressionParseException {
        switch (expr.getOperation()) {
            case ADD: {
                return expr.getLeft().accept(this) + " + " + expr.getRight().accept(this);
            }
            case SUBTRACT: {
                return expr.getLeft().accept(this) + " - " + expr.getRight().accept(this);
            }
            case MULTIPLY: {
                return expr.getLeft().accept(this) + " * " + expr.getRight().accept(this);
            }
            case DIVIDE: {
                return expr.getLeft().accept(this) + " / " + expr.getRight().accept(this);
            }
            default: {
                break;
            }
        }
        return "";
    }

    @Override
    public Object visitLiteral(Literal expr) {
        return expr.getUnarySigns() + expr.getLiteral();
    }

    @Override
    public Object visitParenthesisExpression(ParenthesisExpression expr) throws ExpressionParseException {
        return expr.getUnarySigns() + "(" + expr.getExpr().accept(this) + ")";
    }

    @Override
    public Object visitVariable(Variable expr) {
        return expr.getUnarySigns() + expr.getVariable();
    }
}
