package com.github.ayaanqui.expressionresolver.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.github.ayaanqui.expressionresolver.Resolver;
import com.github.ayaanqui.expressionresolver.algorithms.RelatedParentheses;
import com.github.ayaanqui.expressionresolver.objects.Response;

public class MathFunctions {
    private List<String> formattedUserInput;
    private Map<String, Function<Double[], Double>> functionList;

    public MathFunctions(List<String> formattedUserInput, Map<String, Function<Double[], Double>> functionList) {
        this.formattedUserInput = formattedUserInput;
        this.functionList = functionList;
    }

    private void removeElements(int from, int to) {
        for (int i = to; i >= from; i--)
            formattedUserInput.remove(i);
    }

    private ArrayList<Response> evalParams(int from, int to) {
        ArrayList<Response> list = new ArrayList<>(10);

        if (from + 1 == to)
            return null;

        Resolver resolver = new Resolver();
        int i = from + 1;
        while (i + 1 != to) {
            String elem = formattedUserInput.get(i);
            if (elem.charAt(0) == ',' && (i - 1) != from) {
                List<String> subList = new LinkedList<>(formattedUserInput.subList(from + 1, i));
                list.add(resolver.expressionList(subList).solveExpression());
                removeElements(from + 1, i);

                to = RelatedParentheses.evaluateRelations(formattedUserInput).get(from);
                i = from + 1;
            } else
                i++;
        }

        if (from + 1 == to)
            return null;

        List<String> subList = new LinkedList<>(formattedUserInput.subList(from + 1, to));
        list.add(resolver.expressionList(subList).solveExpression());
        removeElements(from + 1, to);
        return list;
    }

    private Double[] parseParams(int from, int to) {
        ArrayList<Response> evalList = evalParams(from, to);
        if (evalList == null)
            return null;

        ArrayList<Double> list = new ArrayList<>();
        evalList.forEach(res -> list.add(res.result));
        return list.toArray(new Double[0]);
    }

    public Response evaluateFunctions() {
        for (int i = 0; i < formattedUserInput.size(); i++) {
            Function<Double[], Double> functionMethod = functionList.get(formattedUserInput.get(i));
            if (functionMethod == null)
                continue;

            int from = i + 1;

            HashMap<Integer, Integer> map = RelatedParentheses.evaluateRelations(formattedUserInput);
            if (map.isEmpty())
                return Response.getError("Function requires a closing parenthsis");
            int to = map.get(from);

            Double[] parsedParamList = parseParams(from, to);
            if (parsedParamList == null)
                return Response.getError("Must provide at least one parameter");

            double output;
            try {
                output = functionMethod.apply(parsedParamList);
            } catch (Exception e) {
                return Response.getError("Insufficient function parameters");
            }

            formattedUserInput.set(i, Double.toString(output));
            formattedUserInput.remove(i + 1); // Remove x from formattedUserInput
        }
        return Response.getSuccess(0);
    }
}