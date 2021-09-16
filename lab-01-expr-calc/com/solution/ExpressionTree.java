package com.solution;

import java.util.HashMap;

public class ExpressionTree {
    ExpressionTree(String expression) throws ExpressionParseException {
        ParserImpl parser = new ParserImpl();
        mTreeTop = parser.parseExpression(expression);
    }

    String getTreeRepresentation() throws ExpressionParseException {
        return (String) mTreeTop.accept(DebugRepresentationExpressionVisitor.INSTANCE);
    }

    double computeResult() throws ExpressionParseException {
        InitializeVariablesVisitor varVisitor = new InitializeVariablesVisitor();
        HashMap<String, String> res = (HashMap<String, String>) mTreeTop.accept(varVisitor);
        ComputeExpressionVisitor visitor = new ComputeExpressionVisitor(res);
        return (double) mTreeTop.accept(visitor);
    }

    int getTreeDepth() throws ExpressionParseException {
        return (Integer) mTreeTop.accept(GetTreeDepthVisitor.INSTANCE);
    }

    @Override
    public String toString() {
        try {
            return (String) mTreeTop.accept((ExpressionVisitor) ToStringVisitor.INSTANCE);
        } catch (ExpressionParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    private Expression mTreeTop;
}
