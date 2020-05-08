package com.ayaanqui.calculator;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        Scanner keyboard = new Scanner(System.in);
        Calculator calc = new Calculator();
        String userInput = "";
        int cnt = 0;

        System.out.print(">> ");
        userInput = keyboard.nextLine();
        userInput = userInput.toLowerCase();

        while (!userInput.equals("exit") && !userInput.equals("quit")) {
            // User help
            if (userInput.toLowerCase().equals("help") || userInput.equals("?")) {

                Scanner help = new Scanner(new File("help.txt"));
                String helpItem = "";
                System.out.print("\n");

                while (help.hasNext()) {
                    System.out.println(helpItem);
                    helpItem = help.nextLine();
                }
                System.out.print("\n");
                help.close();

            } else if (userInput.toLowerCase().equals("history")) {

                if (cnt > 0) {
                    System.out.println("\nHISTORY:");
                    for (int i = 0; i <= calc.getUserHistory().size() - 1; i++) {
                        System.out.println((i + 1) + " - " + calc.getUserHistory().get(i));
                    }
                    System.out.println();
                } else {
                    System.out.println("Your history is empty :(");
                }

            } else {
                calc.expression(userInput);
                Calculator.Response res = calc.solveExpression();

                if (res.success)
                    System.out.println(res.result + "\n");
                else {
                    for (String error : res.errors) {
                        System.err.println(" *" + error);
                    }
                    System.out.println();
                }
            }
            cnt++;

            System.out.print(">> ");
            userInput = keyboard.next();
        }
        keyboard.close();
    }
}