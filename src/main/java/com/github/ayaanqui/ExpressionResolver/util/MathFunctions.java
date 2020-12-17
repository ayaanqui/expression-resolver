package com.github.ayaanqui.ExpressionResolver.util;

import java.util.LinkedList;
import java.util.Map;
import java.util.function.Function;

import com.github.ayaanqui.ExpressionResolver.objects.Response;

public class MathFunctions {
    private LinkedList<String> formattedUserInput;
    private Map<String, Function<Double[], Double>> functionList;

    public MathFunctions(LinkedList<String> formattedUserInput, Map<String, Function<Double[], Double>> functionList) {
        this.formattedUserInput = formattedUserInput;
        this.functionList = functionList;
    }

    public double factorialOf(double x) {
        double factorial = 1;

        for (int i = (int) x; i > 1; i--) {
            factorial *= i;
        }
        return factorial;
    }

    public Response evaluateFunctions() {
        for (int i = 0; i < formattedUserInput.size(); i++) {
            Function<Double[], Double> functionMethod = functionList.get(formattedUserInput.get(i));

            if (functionMethod != null) {
                Response evalaluateParenthesesResponse = EvaluateParentheses.condense(formattedUserInput, i + 1);

                if (!evalaluateParenthesesResponse.success)
                    return evalaluateParenthesesResponse;
                double x = evalaluateParenthesesResponse.result;

                Double[] args = new Double[] { x };
                double output = functionMethod.apply(args);
                formattedUserInput.set(i, Double.toString(output));
                formattedUserInput.remove(i + 1); // Remove x from formattedUserInput
            }
        }
        return Response.getSuccess(0);
    }
}