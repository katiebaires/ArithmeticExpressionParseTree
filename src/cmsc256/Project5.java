/*
Katie Martinez
Project 5 - Arithmetic Expression Parse Tree Implementation
Project5.java
Building a binary expression tree for evaluation through inorder traversal
 */



package cmsc256;
import bridges.base.BinTreeElement;
import java.util.Stack;
public class Project5 {
    public static bridges.base.BinTreeElement<String> buildParseTree(String expression) {
        if (expression == null || expression.length() == 0) {
            throw new IllegalArgumentException();
        }

        String[] tokens = expression.split(" ");
        BinTreeElement<String> parseTree = new BinTreeElement<>("root", "");
        BinTreeElement<String> curr = parseTree;
        Stack<BinTreeElement<String>> treeStack = new Stack<>();

        for (String token : tokens) {
            switch (token) {
                case "(":
                    curr.setLeft(new BinTreeElement<String>("void", ""));
                    treeStack.push(curr);
                    curr = curr.getLeft();
                    break;
                case "+":
                case "-":
                case "*":
                case "/":
                    curr.setLabel(token);
                    curr.setRight(new BinTreeElement<String>("void", ""));
                    treeStack.push(curr);
                    curr = curr.getRight();
                    break;
                case ")":
                    if (!treeStack.isEmpty()) {
                        curr = treeStack.pop();
                    }
                    break;
                default:
                    if (isOperand(token)) {
                        curr.setLabel(token);
                        curr = treeStack.pop();
                    }
                    break;
            }
        }
        return parseTree;
    }
    public static double evaluate(bridges.base.BinTreeElement<String> tree) {
        if (tree == null) {
            return Double.NaN;
        }

        if (tree.getLeft() == null && tree.getRight() == null) {
            return Double.parseDouble(tree.getLabel());
        }

        double left = evaluate(tree.getLeft());
        double right = evaluate(tree.getRight());

        switch (tree.getLabel()) {
            case "+":
                return left + right;
            case "-":
                return left - right;
            case "*":
                return left * right;
            case "/":
                if (right == 0) {
                    throw new ArithmeticException("Cannot divide by 0");
                }
                return left / right;
            default:
                return 0;
        }

    }
    public static String getEquation(bridges.base.BinTreeElement<String> tree) {
        String equation = "";
        if (tree == null) {
            return "";
        }
        if (tree.getLeft() == null && tree.getRight() == null) {
            return tree.getLabel();
        }
        equation = equation + "( " + getEquation(tree.getLeft()) + " " + tree.getLabel() + " " + getEquation(tree.getRight()) + " )";
        return equation;
    }

    public static boolean isOperator(String string) {
        return (string.equals("+") || string.equals("-") || string.equals("*") || string.equals("/"));
    }

    public static boolean isOperand(String string) {
        try {
            Double.parseDouble(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
