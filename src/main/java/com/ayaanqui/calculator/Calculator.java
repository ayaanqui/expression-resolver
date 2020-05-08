package com.ayaanqui.calculator;

import java.lang.Math;
import java.util.ArrayList;

import com.ayaanqui.calculator.util.ConvertConstants;
import com.ayaanqui.calculator.util.MathFunctions;
import com.ayaanqui.calculator.util.EvaluateParentheses;

public class Calculator {
    final private char[] operatorList = { '+', '-', '*', '/', '^', '(', ')', '<' };

    private String userInput;
    private ArrayList<String> formattedUserInput = new ArrayList<>();
    private ArrayList<String> userHistory = new ArrayList<>(); // records all the solved expressions

    public static class Response {
        public boolean success;
        public double result;
        public String[] errors;

        public Response() {
        }
    }

    /**
     * Given positions for prefix, operator, and postfix, format negatives. Given
     * formattedList [.., "x", "-", "num", ..] returns [.., "x", "+", "-num", ..],
     * where x = ")" or a number. For everything else removes "-" and concatinates
     * "-" to post.
     * 
     * @param formattedList
     * @param pre
     * @param op
     * @param post
     * @return
     */
    private boolean handleNegative(ArrayList<String> formattedList, int pre, int op, int post) {
        // When pre >= 0 and pre == ")" or pre is a digit
        if (!(pre < 0) && (formattedList.get(pre).equals(")")
                || Character.isDigit(formattedList.get(pre).charAt(formattedList.get(pre).length() - 1)))) {
            if (op < formattedList.size() - 1 && post < formattedList.size()) {
                formattedList.set(post, '-' + formattedList.get(post));
                formattedList.set(op, "+");
                return true;
            }
            return false;
        }

        // Concatinates op with post, then removes op
        if (op >= 0 && post >= 1 && post < formattedList.size()) {
            formattedList.set(post, '-' + formattedList.get(post));
            formattedList.remove(op);
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
    private void operatorFormatting(ArrayList<String> formattedList) {
        for (int i = 0; i < formattedList.size(); i++) {
            String item = formattedList.get(i);

            if (item.equals("-")) {
                if (!handleNegative(formattedList, i - 1, i, i + 1)) {
                    System.out.println("There was an error!");
                }
            } else if (item.equals("<")) { // get the the previous answer
                if (this.userHistory.size() > 0)
                    formattedList.set(i, userHistory.get(userHistory.size() - 1));
                else
                    formattedList.set(i, "0");
            }
        }
    }

    public Calculator() {
    }

    public void expression(String uInp) {
        this.userInput = uInp;
    }

    public String getUserInput() {
        return userInput;
    }

    public ArrayList<String> getUserHistory() {
        return userHistory;
    }

    public ArrayList<String> formatUserInput() {
        // Trim whitespaces and $ signs
        userInput = userInput.replaceAll("\\s", "");
        userInput = userInput.replace("$", "");

        StringBuilder tempInput = new StringBuilder(userInput);
        ArrayList<String> formattedList = new ArrayList<>();
        boolean found = false; // Prevents skipping next char

        for (int i = 0; i < tempInput.length(); i++) {
            // If found is true set i = 0 this prevents skipping over the next character
            if (found) {
                i = 0;
                found = false;
            }

            for (char operator : operatorList) {
                if (operator == tempInput.charAt(i)) {
                    // Content before operator
                    String prefix = tempInput.substring(0, i);
                    if (prefix.length() > 0)
                        formattedList.add(prefix);
                    formattedList.add(Character.toString(operator));

                    tempInput.delete(0, i + 1); // Delete the rest of the input
                    i = 0; // Reset i to begining of input
                    found = true;
                    break;
                }
            }
        }
        formattedList.add(tempInput.toString());

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
        this.formattedUserInput = formatUserInput();
        Response evalFunctionsResponse = new MathFunctions(formattedUserInput).evaluateFunctions();
        if (evalFunctionsResponse != null) {
            // If Response is not null then Response.success is set to false
            // So just return the errors...
            return evalFunctionsResponse;
        }

        /**
         * Set condense to first element of formattedUserInput, in case the user enters
         * a number, constant, etc. This avoids returning a 0 if the user enters, e.g.
         * sqrt(2)
         */
        double condense;
        try {
            condense = Double.parseDouble(formattedUserInput.get(0));
        } catch (NumberFormatException e) {
            condense = 0.0;
        }

        // Perform parentheses before everything
        for (int i = 0; i < formattedUserInput.size() - 1; i++) {
            if (formattedUserInput.get(i).equals("-(")) {
                formattedUserInput.add(i, "-1.0");
                formattedUserInput.add(i + 1, "*");
                formattedUserInput.set(i + 2, "(");

                i = i + 2;
            }

            if (formattedUserInput.get(i).equals("(")) {
                Response res = EvaluateParentheses.condense(formattedUserInput, i);
                if (!res.success)
                    return res;
                i = 0;
            }
        }

        // Perform Exponents only after all parentheses have been evaluated
        for (int i = 1; i < formattedUserInput.size(); i++) {
            if (formattedUserInput.get(i).equals("^")) {
                Response res = condenseExpression('^', i);
                if (!res.success)
                    return res;
                condense = res.result;

                formattedUserInput.remove(i + 1); // Remove number before operation
                formattedUserInput.remove(i); // Remove operation
                formattedUserInput.set(i - 1, condense + ""); // Change number after operation to condensed form

                i = 0;
            }
        }

        // Perform multiplication/division before addition/subtraction
        for (int i = 1; i < formattedUserInput.size(); i++) {
            if (formattedUserInput.get(i).equals("*") || formattedUserInput.get(i).equals("/")) {
                if (formattedUserInput.get(i).equals("*")) {
                    Response res = condenseExpression('*', i);
                    if (!res.success)
                        return res;
                    condense = res.result;
                }

                if (formattedUserInput.get(i).equals("/")) {
                    Response res = condenseExpression('/', i);
                    if (!res.success)
                        return res;
                    condense = res.result;

                    if (Double.isInfinite(condense)) {
                        res = new Response();
                        res.success = false;
                        res.errors = new String[] { "Could not divide by zero" };
                        return res;
                    }
                }

                formattedUserInput.remove(i + 1); // remove number before operation
                formattedUserInput.remove(i); // remove operation
                formattedUserInput.set(i - 1, condense + ""); // change number after operation to condensed form

                i = 0;
            }
        }

        // Perform addition/subtraction after everything else
        for (int i = 1; i < formattedUserInput.size(); i++) {
            if (formattedUserInput.get(i).equals("+") || formattedUserInput.get(i).equals("-")) {
                if (formattedUserInput.get(i).equals("+")) {
                    Response res = condenseExpression('+', i);
                    if (!res.success)
                        return res;
                    condense = res.result;
                }

                if (formattedUserInput.get(i).equals("-")) {
                    Response res = condenseExpression('-', i);
                    if (!res.success)
                        return res;
                    condense = res.result;
                }

                formattedUserInput.remove(i + 1); // remove number before operation
                formattedUserInput.remove(i); // remove operation
                formattedUserInput.set(i - 1, condense + ""); // change number after operation to condensed form

                i = 0;
            }
        }

        Response res = new Response();
        if (formattedUserInput.size() >= 1) {
            try {
                userHistory.add(formattedUserInput.get(0));

                res.result = Double.parseDouble(formattedUserInput.get(0));
                res.success = true;
                return res;
            } catch (Exception e) {
                res.success = false;
                res.errors = new String[] { "Not a number", "Error parsing input" };
                return res;
            }
        } else {
            System.err.println(formattedUserInput);

            res.success = false;
            res.errors = new String[] { "Could not resolve expression" };
            return res;
        }
    }

    public String toString() {
        return solveExpression() + "";
    }
}