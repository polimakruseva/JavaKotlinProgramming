package com.solution;

public class ToStringVisitor implements ExpressionVisitor<String> {
    private ToStringVisitor() {}

    public static final ExpressionVisitor<String> INSTANCE = new ToStringVisitor();

    @Override
    public String visitBinaryExpression(BinaryExpression expr) throws ExpressionParseException {
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
    public String visitLiteral(Literal expr) {
        return expr.getUnarySigns() + expr.getLiteral();
    }

    @Override
    public String visitParenthesisExpression(ParenthesisExpression expr) throws ExpressionParseException {
        return expr.getUnarySigns() + "(" + expr.getExpr().accept(this) + ")";
    }

    @Override
    public String visitVariable(Variable expr) {
        return expr.getUnarySigns() + expr.getVariable();
    }
}
