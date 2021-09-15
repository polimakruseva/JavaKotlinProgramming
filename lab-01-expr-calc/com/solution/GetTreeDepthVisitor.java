package com.solution;

public class GetTreeDepthVisitor implements ExpressionVisitor {
    private GetTreeDepthVisitor() {}

    public static final ExpressionVisitor INSTANCE = new GetTreeDepthVisitor();

    @Override
    public Object visitBinaryExpression(BinaryExpression expr) throws ExpressionParseException {
        return 1 + Math.max((Integer) expr.getLeft().accept(this), (Integer) expr.getRight().accept(this)) ;
    }

    @Override
    public Object visitLiteral(Literal expr) {
        return 1;
    }

    @Override
    public Object visitParenthesisExpression(ParenthesisExpression expr) throws ExpressionParseException {
        return 1 + (Integer) expr.getExpr().accept(this);
    }
}
