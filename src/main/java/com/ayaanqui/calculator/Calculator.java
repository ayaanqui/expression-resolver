package com.ayaanqui.calculator;

import java.lang.Math;
import java.util.ArrayList;
import java.util.LinkedList;

import com.ayaanqui.calculator.util.ConvertConstants;
import com.ayaanqui.calculator.util.MathFunctions;
import com.ayaanqui.calculator.util.EvaluateParentheses;
import com.ayaanqui.calculator.objects.Response;

public class Calculator {
    private final char[] operatorList = { '+', '-', '*', '/', '^', '(', ')', '<' };

    private String userInput;
    private LinkedList<String> formattedUserInput;
    private ArrayList<String> userHistory;

    public Calculator() {
        formattedUserInput = new LinkedList<>();
        userHistory = new ArrayList<>();
    }

    public void expression(String uInp) {
        this.userInput = uInp;
        formattedUserInput = new LinkedList<>();
    }

    public void expressionList(LinkedList<String> subList) {
        this.formattedUserInput = subList;
    }

    public String getUserInput() {
        return userInput;
    }

    public ArrayList<String> getUserHistory() {
        return userHistory;
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
    private boolean handleNegative(LinkedList<String> formattedList, int pre, int op, int post) {
        if (op >= formattedList.size() && post >= formattedList.size())
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
    private void operatorFormatting(LinkedList<String> formattedList) {
        for (int i = 0; i < formattedList.size(); i++) {
            String item = formattedList.get(i);

            if (item.equals("-")) {
                handleNegative(formattedList, i - 1, i, i + 1);
            } else if (item.equals("<")) { // get the the previous answer
                if (this.userHistory.size() > 0)
                    formattedList.set(i, userHistory.get(userHistory.size() - 1));
                else
                    formattedList.set(i, "0");
            }
        }
    }

    public LinkedList<String> formatUserInput() {
        // Trim whitespaces and $ signs
        userInput = userInput.replaceAll("\\s", "");
        userInput = userInput.replace("$", "");

        LinkedList<String> formattedList = new LinkedList<>();
        int start = 0;
        for (int i = 0; i < userInput.length(); i++) {
            for (char operator : operatorList) {
                if (operator == userInput.charAt(i)) {
                    // Content before operator
                    String prefix = userInput.substring(start, i);
                    if (prefix.length() > 0)
                        formattedList.add(prefix);
                    formattedList.add(Character.toString(operator));
                    start = i + 1;
                    break;
                }
            }
        }

        if (userInput.equals(""))
            return formattedList;

        String remainder = userInput.substring(start);
        if (!remainder.equals(""))
            formattedList.add(remainder);

        // Remove "+" if it is at the begining of the list
        if (formattedList.get(0).equals("+"))
            formattedList.remove(0);

        new ConvertConstants(formattedList).convert();
        operatorFormatting(formattedList);

        return formattedList;
    }

    private Response catchNumberException(String elem) {
        double result;
        Response res = new Response();

        try {
            result = Double.parseDouble(elem); // value of i-1
        } catch (NumberFormatException e) {
            res.success = false;
            res.errors = new String[] { "Operator requires two numbers" };
            return res;
        }
        res.success = true;
        res.result = result;
        return res;
    }

    public Response condenseExpression(char operator, int i) {
        // Check to see if (i-1) and (i-1) are within the bounds of formattedUserInput
        if (i - 1 < 0 || i + 1 >= formattedUserInput.size()) {
            Response res = new Response();
            res.success = false;
            res.errors = new String[] { "Operator requires two numbers" };
            return res;
        }

        Response x = catchNumberException(formattedUserInput.get(i - 1));
        Response y = catchNumberException(formattedUserInput.get(i + 1));

        if (!x.success)
            return x;
        if (!y.success)
            return y;

        double output = 0.0;
        if (operator == '^')
            output = Math.pow(x.result, y.result);
        else if (operator == '/')
            output = x.result / y.result;
        else if (operator == '*')
            output = x.result * y.result;
        else if (operator == '-')
            output = x.result - y.result;
        else if (operator == '+')
            output = x.result + y.result;
        Response res = new Response();
        res.success = true;
        res.result = output;
        return res;
    }

    public Response solveExpression() {
        if (formattedUserInput.isEmpty()) {
            this.formattedUserInput = formatUserInput();
        }

        if (formattedUserInput.isEmpty())
            return Response.getError(new String[] { "Input cannot be left blank" });

        Response evalFunctionsResponse = new MathFunctions(formattedUserInput).evaluateFunctions();
        if (evalFunctionsResponse != null) {
            // If Response is not null then Response.success is set to false
            // So just return the errors...
            return evalFunctionsResponse;
        }

        Response res = new Response();

        // Perform parentheses before everything
        for (int i = 0; i < formattedUserInput.size() - 1; i++) {
            if (formattedUserInput.get(i).equals("(")) {
                res = EvaluateParentheses.condense(formattedUserInput, i);
                if (!res.success)
                    return res;
                i--;
            }
        }

        // Perform Exponents only after all parentheses have been evaluated
        for (int i = 1; i < formattedUserInput.size(); i++) {
            if (formattedUserInput.get(i).equals("^")) {
                res = condenseExpression('^', i);
                if (!res.success)
                    return res;

                formattedUserInput.remove(i + 1); // Remove number before operation
                formattedUserInput.remove(i); // Remove operation
                formattedUserInput.set(i - 1, Double.toString(res.result));
                i--;
            }
        }

        // Perform multiplication/division before addition/subtraction
        for (int i = 1; i < formattedUserInput.size(); i++) {
            if (formattedUserInput.get(i).equals("*") || formattedUserInput.get(i).equals("/")) {
                if (formattedUserInput.get(i).equals("*")) {
                    res = condenseExpression('*', i);
                    if (!res.success)
                        return res;
                }

                if (formattedUserInput.get(i).equals("/")) {
                    res = condenseExpression('/', i);
                    if (!res.success)
                        return res;

                    if (Double.isInfinite(res.result)) {
                        res = Response.getError(new String[] { "Could not divide by zero" });
                        return res;
                    }
                }

                formattedUserInput.remove(i + 1); // remove number before operation
                formattedUserInput.remove(i); // remove operation
                formattedUserInput.set(i - 1, Double.toString(res.result));
                i--;
            }
        }

        // Perform addition/subtraction after everything else
        for (int i = 1; i < formattedUserInput.size(); i++) {
            if (formattedUserInput.get(i).equals("+") || formattedUserInput.get(i).equals("-")) {
                if (formattedUserInput.get(i).equals("+")) {
                    res = condenseExpression('+', i);
                    if (!res.success)
                        return res;
                }

                if (formattedUserInput.get(i).equals("-")) {
                    res = condenseExpression('-', i);
                    if (!res.success)
                        return res;
                }

                formattedUserInput.remove(i + 1); // remove number before operation
                formattedUserInput.remove(i); // remove operation
                formattedUserInput.set(i - 1, Double.toString(res.result));
                i--;
            }
        }

        if (formattedUserInput.size() == 1) {
            double expression;
            try {
                expression = Double.parseDouble(formattedUserInput.get(0));
            } catch (Exception e) {
                return Response.getError(new String[] { "Not a number", "Error parsing input" });
            }
            userHistory.add(Double.toString(expression));
            return Response.getSuccess(expression);
        } else {
            return Response.getError(new String[] { "Could not resolve expression" });
        }
    }

    public String toString() {
        return solveExpression() + "";
    }
}