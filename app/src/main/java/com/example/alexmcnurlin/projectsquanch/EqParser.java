package com.example.alexmcnurlin.projectsquanch;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by alexmcnurlin on 2/27/17.
 */

public class EqParser {
    public String equation;
    public Node root;
    private ArrayList<String> tokens;
    private ArrayList<Integer> tokensp;


    public class Node {
        private String data;
        private int value;
        private int precedence;
        private int index;
        private Node parent;
        private boolean isRightChild;
        private Node right;
        private Node left;

        Node() {
            right = null;
            left = null;
            parent = null;
        }
    }


    EqParser(String newEquation) {
        tokens = new ArrayList<>();
        tokensp = new ArrayList<>();
        equation = newEquation;
        tokenize();
        root = parseString();
    }


    // Takes in an array of operators/functions, and a corresponding array containing the
    // precedence of all the operators/functions. Then it creates a tree with lowest precedence
    // on top.
    private Node parseString() {return parseString(tokens, tokensp, 0);}
    private Node parseString(ArrayList<String> s_arr, ArrayList<Integer> p_arr, int offset) {
        if (s_arr.size() == 0) {
            return null;
        }
        // temp variables to store operator with highest precedence
        String temps = "";      // temp String
        int tempp = 2147483647; // temp precedence of character
        int tempi = -1;         // temp index of character
        // Find the character with the lowest precedence, store it in tempc
        String s;
        int p = 2147483647;
        for (int i = 0; i < s_arr.size(); i++){
            s = s_arr.get(i);
            p = p_arr.get(i);
            if (p < tempp) {
                tempp = p;
                temps = s;
                tempi = i;
            }
        }

        Node newNode = new Node();
        newNode.data = temps;
        newNode.precedence = tempp;
        newNode.index = offset+tempi;

        // Check if the given character is the first in the string
        if (tempi == 0) {
            newNode.left = null;
        } else {
            ArrayList<String> s_list = new ArrayList<>(s_arr.subList(0, tempi));
            ArrayList<Integer> p_list = new ArrayList<>(p_arr.subList(0, tempi));
            newNode.left = parseString(s_list, p_list, offset);
            newNode.left.parent = newNode;
            newNode.left.isRightChild = false;
        }
        // Check if the given character is the last in the string
        if (tempi+1 == s_arr.size()) {
            newNode.right = null;
        } else {
            ArrayList<String> s_list = new ArrayList<>(s_arr.subList(tempi+1, s_arr.size()));
            ArrayList<Integer> p_list = new ArrayList<>(p_arr.subList(tempi+1, p_arr.size()));
            newNode.right = parseString(s_list, p_list, offset+tempi+1);
            newNode.right.parent = newNode;
            newNode.right.isRightChild = true;
        }


        return newNode;
    }


    private void addNode(String s) {addNode(tokens.size(), s);}
    private void addNode(int i, String s) {
        // TODO: handle case where i=0 (prepending s to the equation)
        Node n = new Node();
        n.index = i;
        n.data = s;
        n.precedence = getPrecedence(s);
        Node tempNode = root;
        while (true) {
            if (i-1 < tempNode.index) {
                tempNode = tempNode.left;
            } else if (i-1 > tempNode.index) {
                tempNode = tempNode.right;
            } else {
                while (n.precedence < tempNode.parent.precedence) {
                    tempNode = tempNode.parent;
                    if (tempNode.parent == null) {
                        break;
                    }
                }
                if (tempNode.parent.index < n.index) {
                    n.parent = tempNode.parent;
                    tempNode.parent.right = n;
                    n.isRightChild = true;
                    tempNode.parent = n;
                    tempNode.isRightChild = true;
                    n.right = tempNode;
                } else {
                    n.parent = tempNode.parent;
                    tempNode.parent.left = n;
                    n.left = tempNode;
                    n.isRightChild = false;
                    tempNode.parent = n;
                    tempNode.isRightChild = false;
                    //TODO: check to see if this actually works....
                }
            }
        }

    }


    public int getPrecedence(String s) {
        switch(s) {
            case "^":
                return 3;
            case "*":
            case "/":
                return 2;
            case "+":
            case "-":
                return 1;
            default:
                return 2147483647-1;
        }
    }


    private void tokenize() {
        for (int i = 0; i < equation.length(); i++) {
            String s = Character.toString(equation.charAt(i));
            tokens.add(i, s);
            tokensp.add(i, getPrecedence(s));
        }
    }


    public String printEquation() {return printEquation(root);}
    public String printEquation(Node myNode) {
        if (myNode == null) {
            return "";
        }
        String myString = "(";
        myString+=printEquation(myNode.left);
        myString+=" "+myNode.index+" ";
        myString+=printEquation(myNode.right);
        myString+=")";
        return myString;
    }
}