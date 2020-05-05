package com.ayaanqui.calculator;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;

import com.ayaanqui.calculator.util.ConvertConstants;
import com.ayaanqui.calculator.util.MathFunctions;
import com.ayaanqui.calculator.util.EvaluateParentheses;

public class Calculator {
    final private String[] operatorList = { "+", "-", "*", "/", "^", "(", ")", "<" };

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

    public ArrayList<String> formatUserInput() {
        // Remove all spaces, dollar signs, and commas...
        userInput = userInput.replace(" ", "");
        userInput = userInput.replace(",", "");
        userInput = userInput.replace("$", "");

        /**
         * Gets user input Adds spaces in between the operation and the operatorList
         * Splits each item by spaces, into formattedUserInput
         */
        for (String j : this.operatorList) {
            switch (j) {
                case "<":
                    // get the the previous answer
                    if (userHistory.size() - 1 >= 0)
                        userInput = userInput.replace(j, userHistory.get(userHistory.size() - 1) + "");
                    else
                        userInput = userInput.replace(j, "");
                    break;
                case "(":
                    userInput = userInput.replace(j, j + " ");
                    break;
                case ")":
                    userInput = userInput.replace(j, " " + j);
                    break;
                default:
                    userInput = userInput.replace(j, " " + j + " ");
                    break;
            }
        }

        formattedUserInput = new ArrayList<String>(Arrays.asList(userInput.split(" ")));

        for (int i = 0; i < formattedUserInput.size(); i++) {
            if (formattedUserInput.get(i).equals("-")) {
                formattedUserInput.set(i, "+");
                formattedUserInput.set(i + 1, "-" + formattedUserInput.get(i + 1));
            }
        }

        return formattedUserInput;
    }

    public double condenseExpression(String operator, int indexVal) {
        double output = 0; // final output
        int i = indexVal; // index of the current value inside inpList

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
                    System.out.println("Error: Can't divide by a zero");
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
                System.out.println("Error: operation does not exist");
                return 0;
        }
        return output;
    }

    public String solveExpression() {
        formatUserInput();
        formattedUserInput = new ConvertConstants(formattedUserInput).convert();
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
                formattedUserInput.add(i, "-1");
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

        userHistory.add(formattedUserInput.get(0) + "");

        if (formattedUserInput.size() == 1)
            return formattedUserInput.get(0) + "";
        else
            return "Error";
    }

    public String toString() {
        return solveExpression() + "";
    }
}