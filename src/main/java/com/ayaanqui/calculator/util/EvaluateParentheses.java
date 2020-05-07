package com.ayaanqui.calculator.util;

import java.util.ArrayList;
import java.util.Map;

import com.ayaanqui.calculator.algorithms.RelatedParentheses;
import com.ayaanqui.calculator.Calculator;

public class EvaluateParentheses {
    public EvaluateParentheses() {
    }

    public static String condense(ArrayList<String> formattedList, int start) {
        Map<Integer, Integer> relatedParentheses = new RelatedParentheses(formattedList).evaluateRelations();

        int end = relatedParentheses.get(start);

        String innerExpression = "";

        // Populate innerExpression with all of the elemnts inside the parentheses
        for (int t = start + 1; t < end; t++)
            innerExpression += formattedList.get(t);

        // Removing elements from the back, to avoid IndexOutOfBounds Errors
        for (int t = end; t > start; t--)
            formattedList.remove(t);

        Calculator newExpression = new Calculator();
        newExpression.expression(innerExpression);
        String solvedInnerExpression = newExpression.solveExpression();

        formattedList.set(start, solvedInnerExpression);

        return solvedInnerExpression;
    }
}