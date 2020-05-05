package com.ayaanqui.calculator;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class Tests {
    public Tests() {
    }

    public String[] parseLine(String equation) {
        String expression = "";
        String result = "";

        for (int i = 0; i < equation.length(); i++) {
            if (equation.charAt(i) == '=') {
                expression = equation.substring(0, i - 1);
                result = equation.substring(i + 2, equation.length());
                break;
            }
        }
        String[] output = { expression, result };
        return output;
    }

    public static void main(String[] args) throws IOException {
        Scanner testFile = new Scanner(new File("tests.dat"));
        String[] output = new String[1];
        Calculator calcObj = new Calculator();
        String calcObjResult = "";
        int errors = 0;

        while (testFile.hasNext()) {
            output = new Tests().parseLine(testFile.nextLine());
            calcObj.setUp(output[0]);
            calcObjResult = calcObj.toString();

            if (!calcObjResult.equals(output[1])) {
                System.out.println(output[0] + " = " + output[1] + " not " + calcObjResult);
                errors++;
            }
        }
        testFile.close();

        if (errors > 0)
            System.out.println("\n" + errors + " Errors found");
        else
            System.out.println("No errors found");
    }
}