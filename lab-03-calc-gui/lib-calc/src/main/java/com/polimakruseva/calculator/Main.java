package com.polimakruseva.calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ExpressionParseException {
        ArrayList<String> enteredExpressions = new ArrayList<>(Arrays.asList("(2 + x)   * x - y",
                "((78 / 56 - 6) +  mem ) * foo", "--6", "-(   - 7)", "+++ - 9", "2 *   3 +7-1 - 10/2"));
        ArrayList<String> toStringExpr = new ArrayList<>(Arrays.asList("(2 + x) * x - y", "((78 / 56 - 6) + mem) * foo",
                "--6", "-(-7)", "+++-9"));
        ArrayList<ExpressionTree> trees = new ArrayList<>();
        for (String expr : enteredExpressions) {
            trees.add(new ExpressionTree(expr));
        }
        ArrayList<String> treeRepresentations = new ArrayList<>(Arrays.asList("sub(mul(paren-expr(add('2', var[x])), " +
                        "var[x]), var[y])", "mul(paren-expr(add(paren-expr(sub(div('78', '56'), '6')), var[mem])), " +
                        "var[foo])", "unary-min unary-min '6'", "unary-min paren-expr(unary-min '7')", "unary-plus " +
                "unary-plus unary-plus unary-min '9'"));
        ArrayList<Integer> treeDepths = new ArrayList<>(Arrays.asList(5, 7, 1, 2, 1));

        // toString tests

        boolean isWorking = true;

        for (int i = 0; i < toStringExpr.size(); ++i) {
            if (!toStringExpr.get(i).equals(trees.get(i).toString())) {
                isWorking = false;
                System.out.println("toString is not working, expected: " + toStringExpr.get(i) + ", actual: " +
                        trees.get(i));
            }
        }

        if (isWorking) {
            System.out.println("All toString tests passed");
        }

        // Tree representation tests

        isWorking = true;
        for (int i = 0; i < treeRepresentations.size(); ++i) {
            if (!treeRepresentations.get(i).equals(trees.get(i).getTreeRepresentation())) {
                isWorking = false;
                System.out.println("Tree representation is not working, expected: " + treeRepresentations.get(i) +
                        ", actual: " + trees.get(i).getTreeRepresentation());
            }
        }
        if (isWorking) {
            System.out.println("All tree representation tests passed");
        }

        // Tree depth tests

        isWorking = true;
        for (int i = 0; i < treeDepths.size(); ++i) {
            if (treeDepths.get(i) != trees.get(i).getTreeDepth()) {
                isWorking = false;
                System.out.println("Tree depth is not working, expected: " + treeDepths.get(i) + ", actual: "
                        + trees.get(i).getTreeDepth());
            }
        }
        if (isWorking) {
            System.out.println("All tree depth tests passed");
        }

        // Computing tests

        isWorking = true;

        //82 - (62 / 2)
        ExpressionTree computeTree = new ExpressionTree(new BinaryExpressionImpl(new LiteralImpl("82"),
                new ParenthesisExpressionImpl(new BinaryExpressionImpl(new LiteralImpl("62"), new LiteralImpl("2"),
                        BinOpKind.DIVIDE)), BinOpKind.SUBTRACT));
        if (!computeTree.toString().equals("82 - (62 / 2)")) {
            System.out.println("Wrong expression structure");
        }
        if (computeTree.computeResult() != 51.) {
            isWorking = false;
            System.out.println("Wrong result, expected: 51, actual: " + computeTree.computeResult());
        }
        // (2 + 3) * 6
        ExpressionTree computeTree2 = new ExpressionTree(new BinaryExpressionImpl(new ParenthesisExpressionImpl(
                new BinaryExpressionImpl(new LiteralImpl("2"), new LiteralImpl("3"), BinOpKind.ADD)), new
                LiteralImpl("6"), BinOpKind.MULTIPLY));
        if (!computeTree2.toString().equals("(2 + 3) * 6")) {
            System.out.println("Wrong expression structure");
        }
        if (computeTree2.computeResult() != 30.) {
            isWorking = false;
            System.out.println("Wrong result, expected: 30, actual: " + computeTree.computeResult());
        }

        //2 * 3 + 7 - 1 - 10 / 2
        ExpressionTree computeTree3 = new ExpressionTree(new BinaryExpressionImpl(new
                BinaryExpressionImpl(new LiteralImpl("2"), new LiteralImpl("3"), BinOpKind.MULTIPLY), new
                BinaryExpressionImpl(new BinaryExpressionImpl(new LiteralImpl("7"), new LiteralImpl("1"),
                BinOpKind.SUBTRACT), new BinaryExpressionImpl(new LiteralImpl("10"), new LiteralImpl("2"),
                BinOpKind.DIVIDE), BinOpKind.SUBTRACT), BinOpKind.ADD));
        if (!computeTree3.toString().equals("2 * 3 + 7 - 1 - 10 / 2")) {
            System.out.println("Wrong expression structure");
        }
        if (computeTree3.computeResult() != 7.) {
            isWorking = false;
            System.out.println("Wrong result, expected: 7, actual: " + computeTree.computeResult());
        }

        ArrayList<String> computeExpr = new ArrayList<>(Arrays.asList("(((27 - 8) + (-7) / 2) - 71) * 2 * (-1)", "--9",
                "- (- (- 7))", "71 - (100 * 8 / 10 / 73 * 146) - 3.333", "2.7893 - 0.8989 + 0.3287 + 2 + 1.9090 - 0"));
        ArrayList<Double> results = new ArrayList<>(Arrays.asList(111., 9., -7., -92.333, 6.1281));

        ArrayList<ExpressionTree> treesToCompute = new ArrayList<>();
        for (String expr : computeExpr) {
            treesToCompute.add(new ExpressionTree(expr));
        }

        for (int i = 0; i < results.size(); ++i) {
            if (treesToCompute.get(i).computeResult() != results.get(i)) {
                isWorking = false;
                System.out.println("Wrong result, expected: " + results.get(i) + ", actual: " +
                        treesToCompute.get(i).computeResult());
            }
        }

        if (isWorking) {
            System.out.println("All computing tests passed");
        }

        // Part of code to enter your own expression and test program

        System.out.println("Enter expression:");
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextLine()) {
            String string = scanner.nextLine();

            ExpressionTree tree = new ExpressionTree(string);
            System.out.println("Representation:");
            System.out.println(tree.getTreeRepresentation());

            System.out.print("Tree depth: ");
            System.out.println(tree.getTreeDepth());

            System.out.print("Result: ");
            System.out.println(tree.computeResult());

            System.out.println("ToString representation:");
            System.out.println(tree);
        }
    }
}
