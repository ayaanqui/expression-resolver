package com.ayaanqui.calculator.util;

import java.util.LinkedList;
import java.util.HashMap;

import com.ayaanqui.calculator.algorithms.RelatedParentheses;
import com.ayaanqui.calculator.objects.Response;
import com.ayaanqui.calculator.Calculator;

public class EvaluateParentheses {
    public EvaluateParentheses() {
    }

    public static Response condense(LinkedList<String> formattedList, int start) {
        HashMap<Integer, Integer> relatedParentheses = RelatedParentheses.evaluateRelations(formattedList);

        if (relatedParentheses.isEmpty()) {
            Response res = new Response();
            res.success = false;
            res.errors = new String[] { "Parentheses mismatch" };
            return res;
        }

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
        Response res = newExpression.solveExpression();

        if (res.success)
            formattedList.set(start, Double.toString(res.result));
        return res;
    }
}