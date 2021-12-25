package com.polimakruseva.calculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ExpressionTree {
    public ExpressionTree(String expression) throws ExpressionParseException {
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

    public ExpressionTree() {}

    public void loadNewExpression(String expression) {
        ParserImpl parser = new ParserImpl();
        try {
            mTreeTop = parser.parseExpression(expression);
            mThrowsException = false;
        } catch (ExpressionParseException exception) {
            mThrowsException = true;
            exceptionText = exception.toString();
        }
    }

    public String getTreeRepresentation() throws ExpressionParseException {
        return mTreeTop.accept(DebugRepresentationExpressionVisitor.INSTANCE);
    }

    public double solveFromCalculator(HashMap<String, String> variables, int numOfVariables) {
        double result = 0;
        if (variables.size() < numOfVariables) {
            mThrowsException = true;
            exceptionText = "Initialize variables!";
            return result;
        }
        try {
            result = mTreeTop.accept(new ComputeExpressionVisitor(variables));
            mThrowsException = false;
        } catch (ExpressionParseException e) {
            mThrowsException = true;
            exceptionText = e.toString();
        }
        return result;

    }

    public double computeResult() throws ExpressionParseException {
        HashMap<String, String> res = mTreeTop.accept(new InitializeVariablesVisitor());
        double result = 0;
        try {
            result = mTreeTop.accept(new ComputeExpressionVisitor(res));
            mThrowsException = false;
        } catch (ExpressionParseException e) {
            mThrowsException = true;
            exceptionText = e.toString();
        }
        return result;
    }

    public int getTreeDepth() throws ExpressionParseException {
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

    public boolean throwsException() {
        return mThrowsException;
    }

    public String getExceptionText() {
        return exceptionText;
    }

    public ArrayList<String> getVariables() throws ExpressionParseException {
        return mTreeTop.accept(new GetVariablesVisitor());
    }

    private Expression mTreeTop;
    private boolean mThrowsException = false;
    private String exceptionText = "";
}
