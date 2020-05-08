package com.ayaanqui.calculator.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class RelatedParentheses {
    public RelatedParentheses() {
    }

    public static HashMap<Integer, Integer> evaluateRelations(ArrayList<String> formattedList) {
        Stack<Integer> openingParenthesis = new Stack<>();
        HashMap<Integer, Integer> relationships = new HashMap<>();

        for (int i = 0; i < formattedList.size(); i++) {
            if (formattedList.get(i).equals("(")) {
                openingParenthesis.push(i);
            } else if (formattedList.get(i).equals(")") && openingParenthesis.size() > 0) {
                relationships.put(openingParenthesis.pop(), i);
            }
        }

        if (openingParenthesis.empty())
            return relationships;
        else {
            // If the stack is not empty then we have opening parentheses
            // that have no pair, so return empty map
            return new HashMap<Integer, Integer>();
        }
    }
}