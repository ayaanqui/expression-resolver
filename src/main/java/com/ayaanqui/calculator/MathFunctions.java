package com.ayaanqui.calculator;

import java.util.ArrayList;
import java.lang.Math;

public class MathFunctions {
    final private String[] advOperatorList = { "sqrt", "sin", "cos", "tan", "ln", "abs", "exp", "fact", "!", "arcsin",
            "arccos", "arctan" };
    private ArrayList<String> formattedUserInput;

    public MathFunctions(ArrayList<String> formattedUserInput) {
        this.formattedUserInput = formattedUserInput;
    }

    public double factorialOf(double x) {
        double factorial = 1;

        for (int i = (int) x; i > 1; i--) {
            factorial *= i;
        }
        return factorial;
    }

    public void formatFunctions() {
        /**
         * "2+sin(x)" is represented as ["2", "+", "sin(", "x", ")"] The loop replaces
         * "sin(" with "sin" or "ln(" with "ln" etc.. Then adds a ( after sin. eg. ["2",
         * "+", "sin", "(", "x", ")"]
         */
        for (String operator : advOperatorList) {
            for (int i = 0; i < formattedUserInput.size(); i++) {
                if (formattedUserInput.get(i).equals(operator + "(")) {
                    formattedUserInput.set(i, operator);
                    formattedUserInput.add(i + 1, "(");
                }

                // Negative functions
                if (formattedUserInput.get(i).equals("-" + operator + "(")) {
                    formattedUserInput.set(i, operator);
                    formattedUserInput.add(i, "-1");
                    formattedUserInput.add(i + 1, "*");
                    formattedUserInput.add(i + 3, "(");
                }
            }
        }
    }

    public ArrayList<String> evaluateFunctions() {
        formatFunctions();
        double x = 0.0;

        EvaluateParentheses evalPrenths;

        for (String operator : advOperatorList) {
            for (int i = 0; i < formattedUserInput.size(); i++) {

                if (formattedUserInput.get(i).equals(operator)) {
                    // Evaluates [(, x, )] from [sin, (, x, )], leaving us with [sin, x]
                    evalPrenths = new EvaluateParentheses(formattedUserInput);
                    evalPrenths.condense(i + 1);
                    x = Double.parseDouble(evalPrenths.getSolvedInnerExpression());

                    switch (operator) {
                        case "sqrt":
                            // Square Root
                            formattedUserInput.set(i, Math.sqrt(x) + "");
                            break;
                        case "sin":
                            // Sine function
                            formattedUserInput.set(i, Math.sin(x) + "");
                            break;
                        case "cos":
                            // Cosine function
                            formattedUserInput.set(i, Math.cos(x) + "");
                            break;
                        case "tan":
                            // Tangent function
                            formattedUserInput.set(i, Math.tan(x) + "");
                            break;
                        case "ln":
                            // Natural Log (base e) function
                            formattedUserInput.set(i, Math.log(x) + "");
                            break;
                        case "abs":
                            // Absolute
                            formattedUserInput.set(i, Math.abs(x) + "");
                            break;
                        case "exp":
                            // Exponential e
                            formattedUserInput.set(i, Math.exp(x) + "");
                            break;
                        case "fact":
                            // Factorial
                            formattedUserInput.set(i, factorialOf(x) + "");
                            break;
                        case "arcsin":
                            // Inverse Sine
                            formattedUserInput.set(i, Math.asin(x) + "");
                            break;
                        case "arccos":
                            // Inverse Cosine
                            formattedUserInput.set(i, Math.acos(x) + "");
                            break;
                        case "arctan":
                            // Inverse Tangent value
                            formattedUserInput.set(i, Math.atan(x) + "");
                            break;
                    }

                    formattedUserInput.remove(i + 1); // Remove x from formattedUserInput
                }

            }
        }

        return formattedUserInput;
    }
}