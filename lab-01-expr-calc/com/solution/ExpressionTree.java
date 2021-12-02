package com.solution;

import java.util.HashMap;
import java.util.Scanner;

public class ExpressionTree {
    ExpressionTree(String expression) throws ExpressionParseException {
        ParserImpl parser = new ParserImpl();
        try {
            mTreeTop = parser.parseExpression(expression);
        } catch (ExpressionParseException exception) {
            if (exception.toString().equals("Syntax error") || exception.toString().equals("No closing parenthesis")
            || exception.toString().equals("No expression was entered")) {
                System.out.println(exception + ". Please try again");
                Scanner scanner = new Scanner(System.in);
                if (scanner.hasNextLine()) {
                    String newExpression = scanner.nextLine();
                    mTreeTop = parser.parseExpression(newExpression);
                } else {
                    throw new ExpressionParseException("No expression was entered");
                }
            } else {
                throw exception;
            }
        }
    }

    ExpressionTree(Expression treeTop) {
        mTreeTop = treeTop;
    }

    String getTreeRepresentation() throws ExpressionParseException {
        return mTreeTop.accept(DebugRepresentationExpressionVisitor.INSTANCE);
    }

    double computeResult() throws ExpressionParseException {
        HashMap<String, String> res = mTreeTop.accept(new InitializeVariablesVisitor());
        return mTreeTop.accept(new ComputeExpressionVisitor(res));
    }

    int getTreeDepth() throws ExpressionParseException {
        return mTreeTop.accept(GetTreeDepthVisitor.INSTANCE);
    }

    @Override
    public String toString() {
        try {
            return mTreeTop.accept(ToStringVisitor.INSTANCE);
        } catch (ExpressionParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    private Expression mTreeTop;
}
