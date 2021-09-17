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
    public Object visitLiteral(Literal expr) {
        return mVariables;
    }

    @Override
    public Object visitParenthesisExpression(ParenthesisExpression expr) throws ExpressionParseException {
        return expr.getExpr().accept(this);
    }

    @Override
    public Object visitVariable(Variable expr) throws ExpressionParseException {
        if (!mVariables.containsKey(expr.getVariable())) {
            System.out.println("Enter value for '" + expr.getVariable() + "'");
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextLine()) {
                String number = scanner.next();
                mVariables.put(expr.getVariable(), number);
            } else {
                ExceptionHandler handler = new ExceptionHandler();
                handler.handleException(TypeOfException.NOEXPRESSION);
            }
        }
        return mVariables;
    }

    private HashMap<String, String> mVariables = new HashMap<>();
}
