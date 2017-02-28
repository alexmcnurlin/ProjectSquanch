package com.example.alexmcnurlin.projectsquanch;

/**
 * Created by alexmcnurlin on 2/27/17.
 */

public class EqParser {
    public String equation;
    public Node<String> root;

    public class Node<T> {
        private char data;
        private int value;
        private int precedence;
        private Node<T> parent;
        private Node<T> right;
        private Node<T> left;
    }

    EqParser(String newEquation) {
        equation = newEquation;
    }

    private Node parseString(String s) {
        int temp = -1;
        char tempc = 0;
        int tempi = -1; // temp variables to store operator with highest precedence
        // Find the operator with the highest precedence
        char c;
        int p;
        for (int i = 0; i < s.length(); i++){
            c = s.charAt(i);
            p = getPrecedence(c);
            if (p > temp) {
                temp = p;
                tempc = c;
                tempi = i;
            }
        }

        Node<String> newNode = new Node<String>();
        newNode.data = tempc;
        // Check if the given character is the first in the string
        if (tempi == 0) {
            newNode.right = null;
        } else {
            newNode.right = parseString(s.substring(0, tempi));
        }
        // Check if the given character is the last in the string
        if (tempi+1 == s.length()) {
            newNode.left = parseString(s.substring(tempi));
        }

        root = null;
        //root = new Node<String>();
        //root.parent = null;
        //root.right = null;
        //root.left = null;
        return newNode;
    }

    public int getPrecedence(char c) {
        switch(c) {
            case '^':
                return 2;
                break; // The return is redundant here
            case '*':
            case '/':
                return 1;
                break;
            case '+':
            case '-':
                return 0;
                break;
        }
    }
}
