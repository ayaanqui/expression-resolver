package com.ayaanqui.calculator;

import java.lang.Math;
import java.util.ArrayList;

import com.ayaanqui.calculator.util.ConvertConstants;
import com.ayaanqui.calculator.util.MathFunctions;
import com.ayaanqui.calculator.util.EvaluateParentheses;

public class Calculator {
    final private char[] operatorList = { '+', '-', '*', '/', '^', '(', ')', '<' };

    private String userInput;
    private ArrayList<String> formattedUserInput = new ArrayList<String>();
    private ArrayList<String> userHistory = new ArrayList<String>(); // records all the solved expressions

    public Calculator() {
    }

    public void setUp(String uInp) {
        this.userInput = uInp;
    }

    public String getUserInput() {
        return userInput;
    }

    public ArrayList<String> getUserHistory() {
        return userHistory;
    }

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
     * Converts "<" to the last item from userHistory. Replaces [..., "-", "x", ...]
     * with [..., "-x", ...], where x is a "digit"
     * 
     * @param formattedList
     */
    public void operatorFormatting(ArrayList<String> formattedList) {
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

        formattedList = new ConvertConstants(formattedList).convert();
        operatorFormatting(formattedList);

        return formattedList;
    }

    public double condenseExpression(String operator, int i) {
        double output = 0; // final output

        double x = 0.0;
        double y = 0.0;

        try {
            x = Double.parseDouble(formattedUserInput.get(i - 1)); // value of i-1
        } catch (NumberFormatException e) {
            x = 0.0;
        }

        try {
            y = Double.parseDouble(formattedUserInput.get(i + 1)); // value of i+1
        } catch (NumberFormatException e) {
            y = 0.0;
        }

        switch (operator) {
            case "^":
                output = Math.pow(x, y);
                break;
            case "/":
                if (y == 0) {
                    System.err.println("Error: Can't divide by a zero");
                    return 0;
                } else {
                    output = x / y;
                }
                break;
            case "*":
                output = x * y;
                break;
            case "-":
                output = x - y;
                break;
            case "+":
                output = x + y;
                break;
            default:
                System.err.println("Error: operation does not exist");
                return 0;
        }
        return output;
    }

    public String solveExpression() {
        this.formattedUserInput = formatUserInput();
        System.out.println(formattedUserInput);
        formattedUserInput = new MathFunctions(formattedUserInput).evaluateFunctions();

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
        EvaluateParentheses evalParenths;
        for (int i = 0; i < formattedUserInput.size() - 1; i++) {
            if (formattedUserInput.get(i).equals("-(")) {
                formattedUserInput.add(i, "-1.0");
                formattedUserInput.add(i + 1, "*");
                formattedUserInput.set(i + 2, "(");

                i = i + 2;
            }

            if (formattedUserInput.get(i).equals("(")) {
                evalParenths = new EvaluateParentheses(formattedUserInput);
                evalParenths.condense(i);
                formattedUserInput = evalParenths.getFormattedUserInput();

                i = 0;
            }
        }

        // Perform Exponents only after all parentheses have been evaluated
        for (int i = 1; i < formattedUserInput.size(); i++) {
            if (formattedUserInput.get(i).equals("^")) {
                condense = condenseExpression("^", i);

                formattedUserInput.remove(i + 1); // Remove number before operation
                formattedUserInput.remove(i); // Remove operation
                formattedUserInput.set(i - 1, condense + ""); // Change number after operation to condensed form

                i = 0;
            }
        }

        // Perform multiplication/division before addition/subtraction
        for (int i = 1; i < formattedUserInput.size(); i++) {
            if (formattedUserInput.get(i).equals("*") || formattedUserInput.get(i).equals("/")) {
                if (formattedUserInput.get(i).equals("*"))
                    condense = condenseExpression("*", i);

                if (formattedUserInput.get(i).equals("/"))
                    condense = condenseExpression("/", i);

                formattedUserInput.remove(i + 1); // remove number before operation
                formattedUserInput.remove(i); // remove operation
                formattedUserInput.set(i - 1, condense + ""); // change number after operation to condensed form

                i = 0;
            }
        }

        // Perform addition/subtraction after everything else
        for (int i = 1; i < formattedUserInput.size(); i++) {
            if (formattedUserInput.get(i).equals("+") || formattedUserInput.get(i).equals("-")) {
                if (formattedUserInput.get(i).equals("+"))
                    condense = condenseExpression("+", i);

                if (formattedUserInput.get(i).equals("-"))
                    condense = condenseExpression("-", i);

                formattedUserInput.remove(i + 1); // remove number before operation
                formattedUserInput.remove(i); // remove operation
                formattedUserInput.set(i - 1, condense + ""); // change number after operation to condensed form

                i = 0;
            }
        }

        userHistory.add(formattedUserInput.get(0));

        try {
            condense = Double.parseDouble(formattedUserInput.get(0));
        } catch (Exception e) {
            return "Error";
        }
        return formattedUserInput.get(0);
    }

    public String toString() {
        return solveExpression() + "";
    }
}