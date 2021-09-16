package com.solution;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ExpressionParseException {
        System.out.println("Enter expression:");

        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextLine()) {
            String string = scanner.nextLine();

            ExpressionTree tree = new ExpressionTree(string);
            System.out.println("representation");
            System.out.println(tree.getTreeRepresentation());

            System.out.print("Tree depth: ");
            System.out.println(tree.getTreeDepth());

            System.out.println("result:");
            System.out.println(tree.computeResult());

            System.out.println("ToString representation:");
            System.out.println(tree);
        }
    }
}
