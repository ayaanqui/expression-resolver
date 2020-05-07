package com.ayaanqui.calculator.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class RelatedParentheses {
    private ArrayList<String> userInpList = new ArrayList<String>();

    public RelatedParentheses() {
    }

    public RelatedParentheses(ArrayList<String> userInpList) {
        this.userInpList = userInpList;
    }

    public HashMap<Integer, Integer> evaluateRelations() {
        Stack<Integer> openingParenthesis = new Stack<>();
        HashMap<Integer, Integer> relationships = new HashMap<>();

        for (int i = 0; i < userInpList.size(); i++) {
            if (userInpList.get(i).equals("(")) {
                openingParenthesis.push(i);
            } else if (userInpList.get(i).equals(")") && openingParenthesis.size() > 0) {
                relationships.put(openingParenthesis.pop(), i);
            }
        }
        return relationships;
    }
}