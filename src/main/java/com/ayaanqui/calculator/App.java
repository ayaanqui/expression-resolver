package com.ayaanqui.calculator;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        Scanner keyboard = new Scanner(System.in);
        Calculator calc = new Calculator();
        String userInput;

        while (true) {

            System.out.print(">> ");
            userInput = keyboard.nextLine();
            userInput = userInput.toLowerCase();

            if (userInput.equals("exit") || userInput.equals("quit"))
                break;

            if (userInput.toLowerCase().equals("history")) {
                ArrayList<String> history = calc.getUserHistory();
                if (!history.isEmpty()) {
                    for (String item : history)
                        System.out.println(" - " + item);
                } else {
                    System.out.println("Your history is empty :(");
                }
            } else {
                calc.expression(userInput);
                Calculator.Response res = calc.solveExpression();

                if (res.success)
                    System.out.println(res.result);
                else {
                    for (String error : res.errors)
                        System.err.println(" *" + error);
                }
            }

            System.out.println();

        }
        keyboard.close();
    }
}