package com.github.ayaanqui.expressionresolver.algorithms;

import java.util.List;
import java.util.HashMap;
import java.util.Stack;

/**
 * Returns a HashMap<Integer, Integer> relating all opening parenthesis to their
 * corresponding closing parenthsis, with the opening parethesis index as the
 * key and the value as the index of the closing parenthesis. If a corresponding
 * closing parenthesis is not provided then return an empty HashMap.
 */
public class RelatedParentheses {
    /**
     * Finds the corresponding closing parenthesis of every opening parenthesis
     * 
     * @param formattedList List of all operators and opperands
     * @return HashMap of indexex from formattedList relating each opening
     *         parenthesis with it's corresponding closing parenthesis. Key = index
     *         of opening parenthesis, Value = index of closing parenthesis. If a
     *         matching pair was not found an empty HashMap is returned
     */
    public static HashMap<Integer, Integer> evaluateRelations(List<String> formattedList) {
        Stack<Integer> openingParenthesis = new Stack<>();
        HashMap<Integer, Integer> relationships = new HashMap<>();

        for (int i = 0; i < formattedList.size(); i++) {
            char c = formattedList.get(i).charAt(0);
            if (c == '(') {
                openingParenthesis.push(i);
            } else if (c == ')' && openingParenthesis.size() > 0) {
                relationships.put(openingParenthesis.pop(), i);
            }
        }

        if (openingParenthesis.empty())
            return relationships;
        // If the stack is not empty then we have still have unmatched parentheses
        // So return empty HashMap
        return new HashMap<Integer, Integer>();
    }
}