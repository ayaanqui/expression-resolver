package com.github.ayaanqui.expressionresolver.util;

import java.util.LinkedList;
import java.util.List;

import com.github.ayaanqui.expressionresolver.Resolver;
import com.github.ayaanqui.expressionresolver.algorithms.RelatedParentheses;
import com.github.ayaanqui.expressionresolver.objects.Response;

import java.util.HashMap;

public class EvaluateParentheses {
    public static Response condense(List<String> formattedList, int start) {
        HashMap<Integer, Integer> relatedParentheses = RelatedParentheses.evaluateRelations(formattedList);

        if (relatedParentheses.isEmpty())
            return Response.getError("Parentheses mismatch");

        int end = relatedParentheses.get(start);

        // Check if the pair contains a subexpression
        if (start + 1 == end)
            return Response.getError("Input cannot be left blank");

        Resolver newExpression = new Resolver();
        newExpression.expressionList(new LinkedList<String>(formattedList.subList(start + 1, end)));
        Response res = newExpression.solveExpression();

        // Removing elements from the back, to avoid IndexOutOfBounds Errors
        for (int t = end; t > start; t--)
            formattedList.remove(t);

        if (res.success)
            formattedList.set(start, Double.toString(res.result));
        return res;
    }
}