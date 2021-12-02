package com.solution;

public class GetTreeDepthVisitor implements ExpressionVisitor<Integer> {
    private GetTreeDepthVisitor() {}

    public static final ExpressionVisitor<Integer> INSTANCE = new GetTreeDepthVisitor();

    @Override
    public Integer visitBinaryExpression(BinaryExpression expr) throws ExpressionParseException {
        return 1 + Math.max(expr.getLeft().accept(this), expr.getRight().accept(this)) ;
    }

    @Override
    public Integer visitLiteral(Literal expr) {
        return 1;
    }

    @Override
    public Integer visitParenthesisExpression(ParenthesisExpression expr) throws ExpressionParseException {
        return 1 + expr.getExpr().accept(this);
    }

    @Override
    public Integer visitVariable(Variable expr) {
        return 1;
    }
}
