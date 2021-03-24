package com.github.ayaanqui.expressionresolver;

import java.lang.Math;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Function;

import com.github.ayaanqui.expressionresolver.objects.Response;
import com.github.ayaanqui.expressionresolver.util.ConvertConstants;
import com.github.ayaanqui.expressionresolver.util.EvaluateParentheses;
import com.github.ayaanqui.expressionresolver.util.MathFunctions;

public class Resolver {
    private final char[] operatorList = { '+', '-', '*', '/', '^', '(', ')', ',', '<', '=' };

    private String expression;
    private List<String> parsedExpression;
    private Double lastResult = null;
    private Map<String, Double> variableMap;
    private Map<String, Function<Double[], Double>> functionList;

    public Resolver() {
        parsedExpression = new LinkedList<>();
        variableMap = new HashMap<>(30);
        functionList = new HashMap<>(30);

        // Define constants
        variableMap.put("pi", Math.PI);
        variableMap.put("e", Math.E);
        variableMap.put("tau", 2 * Math.PI);

        functionList.put("sqrt", args -> Math.sqrt(args[0]));
        functionList.put("sin", args -> Math.sin(args[0]));
        functionList.put("cos", args -> Math.cos(args[0]));
        functionList.put("tan", args -> Math.tan(args[0]));
        functionList.put("ln", args -> Math.log(args[0]));
        functionList.put("log", args -> Math.log(args[0]) / Math.log(args[1]));
        functionList.put("deg", args -> args[0] * (180 / Math.PI));
        functionList.put("rad", args -> args[0] * (Math.PI / 180));
        functionList.put("abs", args -> Math.abs(args[0]));
        functionList.put("exp", args -> Math.exp(args[0]));
        functionList.put("arcsin", args -> Math.asin(args[0]));
        functionList.put("arccos", args -> Math.acos(args[0]));
        functionList.put("arctan", args -> Math.atan(args[0]));
        functionList.put("avg", args -> {
            double sum = Arrays.stream(args).reduce(0.0, (total, arg) -> total += arg);
            return sum / args.length;
        });
        functionList.put("sum", args -> {
            return Arrays.stream(args).reduce(0.0, (total, arg) -> total += arg);
        });
        functionList.put("fact", args -> {
            double factorial = 1;
            for (int i = args[0].intValue(); i > 1; i--)
                factorial *= i;
            return factorial;
        });
    }

    /**
     * <p>
     * Sets the string expression. Ex: "1+1"
     * </p>
     * <p>
     * Usage:
     * </p>
     * 
     * <pre>
     * <code>Resolver res = new Resolver();</code>
     * <code>double value = res.setExpression("sin(pi+1)/log(39.5, 10)").solveExpression().result;</code>
     * </pre>
     * 
     * @param expression String-based expression
     * @return Resolver object
     */
    public Resolver setExpression(String expression) {
        this.expression = expression;
        parsedExpression = new LinkedList<>();
        return this;
    }

    public Resolver setFunction(String name, Function<Double[], Double> function) {
        functionList.put(name, function);
        return this;
    }

    /**
     * <p>
     * This method can be used when an expression list has already been parsed with
     * the correct format to skip the parsing step.
     * </p>
     * <p>
     * Example subList:
     * </p>
     * 
     * <pre>
     * <code>List<String>: ["1", "+", "sin" + "(" + "pi" + ")"]</code>
     * </pre>
     * 
     * @param subList List<String> with a pre formatted input
     * @return Returns a Resolver object
     */
    public Resolver expressionList(List<String> subList) {
        this.parsedExpression = subList;
        return this;
    }

    public String getExpression() {
        return expression;
    }

    public double getLastResult() {
        return lastResult;
    }

    /**
     * Given positions for prefix, operator, and postfix, format negatives. Given
     * formattedList [.., "x", "-", "num", ..] returns [.., "x", "+", "-num", ..],
     * where x = ")" or a number. For everything else removes "-" and concatinates
     * "-" to post.
     *
     * @param formattedList
     * @param pre           Left hand side opperand
     * @param op            Operator. Assumes the value at index is "-"
     * @param post          Right hand side opperand
     * @return if processed correctly true, else false
     */
    private boolean handleNegative(List<String> formattedList, int pre, int op, int post) {
        if (op >= formattedList.size() || post >= formattedList.size())
            return false;

        if (pre >= 0 && Character.isDigit(formattedList.get(pre).charAt(0))
                && Character.isDigit(formattedList.get(post).charAt(0)))
            return true;

        // When pre >= 0 and the value at pre == ")"
        if (pre >= 0 && formattedList.get(pre).equals(")")) {
            formattedList.set(op, "+");
            formattedList.add(op + 1, "-1");
            formattedList.add(op + 2, "*");
            return true;
        }

        // Concatinates op with post, then removes op
        if (op >= 0 && post >= 1 && post < formattedList.size()) {
            if (Character.isDigit(formattedList.get(post).charAt(0))) {
                formattedList.set(post, '-' + formattedList.get(post));
                formattedList.remove(op);
                return true;
            }
            // If post is not a number it could be an opening parethesis or a function
            // In this case add [.., "-1", "*", ..] in front
            if (pre >= 0) {
                formattedList.set(op, "+");
                formattedList.add(op + 1, "-1");
                formattedList.add(op + 2, "*");
            } else {
                formattedList.set(op, "-1");
                formattedList.add(op + 1, "*");
            }
            return true;
        }
        return false;
    }

    /**
     * Converts "<" to the last item from userHistory, and handles elements with
     * "-".
     *
     * @param formattedList
     */
    private void operatorFormatting(List<String> formattedList) {
        for (int i = 0; i < formattedList.size(); i++) {
            String item = formattedList.get(i);

            if (item.equals("-")) {
                handleNegative(formattedList, i - 1, i, i + 1);
            } else if (item.equals("<")) { // get the the previous answer
                if (lastResult != null)
                    formattedList.set(i, lastResult.toString());
                else
                    formattedList.set(i, "0");
            }
        }
    }

    public List<String> formatUserInput() {
        // Trim whitespaces and $ signs
        expression = expression.replaceAll("\\s", "");
        expression = expression.replace("$", "");

        List<String> formattedList = new LinkedList<>();
        int start = 0;
        for (int i = 0; i < expression.length(); i++) {
            for (char operator : operatorList) {
                if (operator == expression.charAt(i)) {
                    // Content before operator
                    String prefix = expression.substring(start, i);
                    if (prefix.length() > 0)
                        formattedList.add(prefix);
                    formattedList.add(Character.toString(operator));
                    start = i + 1;
                    break;
                }
            }
        }

        if (expression.equals(""))
            return formattedList;

        String remainder = expression.substring(start);
        if (!remainder.equals(""))
            formattedList.add(remainder);

        // Remove "+" if it is at the begining of the list
        if (formattedList.get(0).equals("+"))
            formattedList.remove(0);

        ConvertConstants cOb = new ConvertConstants(formattedList, variableMap);
        cOb.convert();

        operatorFormatting(formattedList);

        return formattedList;
    }

    private Response catchNumberException(String elem) {
        double result;
        try {
            result = Double.parseDouble(elem); // value of i-1
        } catch (NumberFormatException e) {
            return Response.getError("Operator requires two numbers");
        }
        return Response.getSuccess(result);
    }

    public Response condenseExpression(char operator, int i) {
        // Check to see if (i-1) and (i-1) are within the bounds of formattedUserInput
        if (i - 1 < 0 || i + 1 >= parsedExpression.size()) {
            return Response.getError("Operator requires two numbers");
        }

        String lhs = parsedExpression.get(i - 1);
        Response x = catchNumberException(lhs);
        Response y = catchNumberException(parsedExpression.get(i + 1));

        // Handle variables
        if (operator == '=') {
            // x cannot start with a number
            if (!x.success) {
                x.success = true;
                x.errors = new String[0];
            } else {
                x = Response.getError("Variable names cannot start with a number", "Variables cannot be reassigned");
            }

            // y must be a number
            if (!y.success) {
                y = Response.getError("Variable value must be a number");
            }
        }

        if (!x.success)
            return x;
        if (!y.success)
            return y;

        switch (operator) {
        case '^':
            return Response.getSuccess(Math.pow(x.result, y.result));
        case '/':
            return Response.getSuccess(x.result / y.result);
        case '*':
            return Response.getSuccess(x.result * y.result);
        case '-':
            return Response.getSuccess(x.result - y.result);
        case '+':
            return Response.getSuccess(x.result + y.result);
        case '=':
            this.variableMap.put(lhs, y.result);
            return Response.getSuccess(y.result);
        }
        return Response.getError("Invalid operator");
    }

    public Response solveExpression() {
        if (parsedExpression.isEmpty()) {
            this.parsedExpression = formatUserInput();
        }

        if (parsedExpression.isEmpty())
            return Response.getError("Input cannot be left blank");

        Response evalFunctionsResponse = new MathFunctions(parsedExpression, functionList).evaluateFunctions();
        if (!evalFunctionsResponse.success)
            return evalFunctionsResponse;

        Response res = new Response();
        // Perform parentheses before everything
        for (int i = 0; i < parsedExpression.size() - 1; i++) {
            if (parsedExpression.get(i).equals("(")) {
                res = EvaluateParentheses.condense(parsedExpression, i);
                if (!res.success)
                    return res;
                i--;
            }
        }

        final char[][] orderOfOperations = new char[][] { { '^' }, { '*', '/' }, { '+', '-' }, { '=' } };
        for (char[] operators : orderOfOperations) {
            for (int i = 1; i < parsedExpression.size(); i++) {
                char inputOp = parsedExpression.get(i).charAt(0);

                for (char op : operators) {
                    if (op == inputOp) {
                        res = condenseExpression(op, i);
                        if (!res.success)
                            return res;

                        if (op == '/' && Double.isInfinite(res.result))
                            return Response.getError("Could not divide by zero");

                        parsedExpression.remove(i + 1); // Remove rhs operand
                        parsedExpression.remove(i); // Remove operator
                        parsedExpression.set(i - 1, Double.toString(res.result));
                        i--;
                    }
                }
            }
        }

        if (parsedExpression.size() == 1) {
            double expression;
            try {
                expression = Double.parseDouble(parsedExpression.get(0));
            } catch (Exception e) {
                return Response.getError("Not a number", "Error parsing input");
            }
            lastResult = expression;
            return Response.getSuccess(expression);
        } else {
            return Response.getError("Could not resolve expression");
        }
    }
}