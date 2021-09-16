package com.solution;

import java.util.HashMap;
import java.util.Scanner;

public class InitializeVariablesVisitor implements ExpressionVisitor {
    @Override
    public Object visitBinaryExpression(BinaryExpression expr) throws ExpressionParseException {
        mVariables = (HashMap<String, String>) expr.getLeft().accept(this);
        return expr.getRight().accept(this);
    }

    @Override
    public Object visitLiteral(Literal expr) throws ExpressionParseException {
        if (expr.isVariable() && !mVariables.containsKey(expr.getLiteral())) {
            System.out.println("Enter value for '" + expr.getLiteral() + "'");
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextLine()) {
                String number = scanner.next();
                mVariables.put(expr.getLiteral(), number);
            } else {
                ExceptionHandler handler = new ExceptionHandler();
                handler.handleException(TypeOfException.NOEXPRESSION);
            }
        }
        return mVariables;
    }

    @Override
    public Object visitParenthesisExpression(ParenthesisExpression expr) throws ExpressionParseException {
        return expr.getExpr().accept(this);
    }

    private HashMap<String, String> mVariables = new HashMap<>();
}
