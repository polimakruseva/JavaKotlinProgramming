package com.solution;

enum BinOpKind {
    ADD,
    SUBTRACT,
    MULTIPLY,
    DIVIDE
}

public interface BinaryExpression extends Expression {
    Expression getLeft();
    Expression getRight();
    BinOpKind getOperation();

    @Override
    default Object accept(ExpressionVisitor visitor) throws ExpressionParseException {
        return visitor.visitBinaryExpression(this);
    }
}
