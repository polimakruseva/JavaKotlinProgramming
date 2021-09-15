package com.solution;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ExpressionParseException {
        System.out.println("Enter expression:");

        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextLine()) {
            String string = scanner.nextLine();
//            System.out.println("Your expression is: ");
//            System.out.print(string);

            ExpressionTree tree = new ExpressionTree(string);
//            System.out.println("did parse sth!");
            System.out.println("representation");
            System.out.println(tree.getTreeRepresentation());

            System.out.print("Tree depth: ");
            System.out.println(tree.getTreeDepth());

            System.out.println("result:");
            System.out.println(tree.computeResult());
        }
    }
}
