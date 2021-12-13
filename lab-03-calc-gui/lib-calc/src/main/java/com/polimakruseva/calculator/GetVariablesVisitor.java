package com.polimakruseva.calculator;

import java.util.ArrayList;
import java.util.HashMap;

public class GetVariablesVisitor implements ExpressionVisitor<ArrayList<String>> {
    @Override
    public ArrayList<String> visitBinaryExpression(BinaryExpression expr) throws ExpressionParseException {
        mVariables = expr.getLeft().accept(this);
        return expr.getRight().accept(this);
    }

    @Override
    public ArrayList<String> visitLiteral(Literal expr) {
        return mVariables;
    }

    @Override
    public ArrayList<String> visitParenthesisExpression(ParenthesisExpression expr) throws ExpressionParseException {
        return expr.getExpr().accept(this);
    }

    @Override
    public ArrayList<String> visitVariable(Variable expr) {
        if (!mVariables.contains(expr.getVariable())) {
            mVariables.add(expr.getVariable());
        }
        return mVariables;
    }

    private ArrayList<String> mVariables = new ArrayList<>();
}
