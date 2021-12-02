package com.solution;

import java.util.HashMap;
import java.util.Scanner;

public class InitializeVariablesVisitor implements ExpressionVisitor<HashMap<String, String>> {
    @Override
    public HashMap<String, String> visitBinaryExpression(BinaryExpression expr) throws ExpressionParseException {
        mVariables = expr.getLeft().accept(this);
        return expr.getRight().accept(this);
    }

    @Override
    public HashMap<String, String> visitLiteral(Literal expr) {
        return mVariables;
    }

    @Override
    public HashMap<String, String> visitParenthesisExpression(ParenthesisExpression expr) throws
            ExpressionParseException {
        return expr.getExpr().accept(this);
    }

    @Override
    public HashMap<String, String> visitVariable(Variable expr) throws ExpressionParseException {
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
