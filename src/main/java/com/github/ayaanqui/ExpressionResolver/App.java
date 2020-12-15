package com.github.ayaanqui.ExpressionResolver;

import java.util.ArrayList;
import java.util.Scanner;

import com.github.ayaanqui.ExpressionResolver.objects.Response;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        Scanner keyboard = new Scanner(System.in);
        Expression calc = new Expression();
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
                calc.setExpression(userInput);
                Response res = calc.solveExpression();

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