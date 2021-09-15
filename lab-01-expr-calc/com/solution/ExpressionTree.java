package com.solution;

public class ExpressionTree {
    ExpressionTree(String expression) throws ExpressionParseException {
        ParserImpl parser = new ParserImpl();
        treeTop_ = parser.parseExpression(expression);
    }

    String getTreeRepresentation() throws ExpressionParseException {
        return (String) treeTop_.accept(DebugRepresentationExpressionVisitor.INSTANCE);
    }

    double computeResult() throws ExpressionParseException {
        ComputeExpressionVisitor visitor = new ComputeExpressionVisitor();
        return (double) treeTop_.accept(visitor);
    }

    int getTreeDepth() throws ExpressionParseException {
        return (Integer) treeTop_.accept(GetTreeDepthVisitor.INSTANCE);
    }

    private Expression treeTop_;
}
