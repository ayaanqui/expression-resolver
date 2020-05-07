package com.ayaanqui.calculator;

import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {
    /**
     * Basic testing with just addition
     */
    @Test
    public void testBasicAddition() {
        Calculator calculator = new Calculator();

        // Addition
        calculator.expression("2+2");
        assertEquals("4.0", calculator.solveExpression());

        calculator.expression("12+10");
        assertEquals("22.0", calculator.solveExpression());

        calculator.expression("345+1393");
        assertEquals("1738.0", calculator.solveExpression());

        calculator.expression("0+0");
        assertEquals("0.0", calculator.solveExpression());

        calculator.expression("5891+42411");
        assertEquals("48302.0", calculator.solveExpression());

        calculator.expression("233.890455+1");
        assertEquals("234.890455", calculator.solveExpression());

        calculator.expression("3.14159786663+1564.9923");
        assertEquals("1568.13389786663", calculator.solveExpression());

        calculator.expression("+1");
        assertEquals("1", calculator.solveExpression());
    }

    /**
     * Basic testing with just subtraction
     */
    @Test
    public void testBasicSubtraction() {
        Calculator calculator = new Calculator();

        // Subtraction
        calculator.expression("4-2");
        assertEquals("2.0", calculator.solveExpression());

        calculator.expression("34905-1112");
        assertEquals("33793.0", calculator.solveExpression());

        calculator.expression("0-0");
        assertEquals("0.0", calculator.solveExpression());

        calculator.expression("1-1");
        assertEquals("0.0", calculator.solveExpression());

        calculator.expression("-1");
        assertEquals("-1", calculator.solveExpression());

        calculator.expression("15-34");
        assertEquals("-19.0", calculator.solveExpression());

        calculator.expression("233.890455-1");
        assertEquals("232.890455", calculator.solveExpression());

        calculator.expression("234.983494942754-15969.1085015998");
        assertEquals("-15734.125006657046", calculator.solveExpression());

        // calculator.setUp("--1");
        // assertEquals("1", calculator.solveExpression());
    }

    /**
     * Basic testing with addition and subtraction
     */
    public void testAdditionAndSubtraction() {
        Calculator calculator = new Calculator();

        calculator.expression("1+1-1-1");
        assertEquals("0.0", calculator.solveExpression());

        calculator.expression("21344-8822-82+123-12-3485.123");
        assertEquals("9065.877", calculator.solveExpression());

        calculator.expression("-1+-1");
        assertEquals("-2.0", calculator.solveExpression());

        calculator.expression("28--25+056.200+-14");
        assertEquals("95.2", calculator.solveExpression());
    }

    /**
     * Basic testing with multiplication and division
     */
    @Test
    public void testMultiplicationDivision() {
        Calculator calculator = new Calculator();

        calculator.expression("0*0");
        assertEquals("0.0", calculator.solveExpression());

        calculator.expression("0*1");
        assertEquals("0.0", calculator.solveExpression());

        calculator.expression("4*0");
        assertEquals("0.0", calculator.solveExpression());

        calculator.expression("2349*133*1249");
        assertEquals("3.90208833E8", calculator.solveExpression());

        // Division
        calculator.expression("0/198");
        assertEquals("0.0", calculator.solveExpression());

        calculator.expression("4/2");
        assertEquals("2.0", calculator.solveExpression());

        calculator.expression("384/13/1933");
        assertEquals("0.015281149269767999", calculator.solveExpression());
    }

    /**
     * Basic testing with constants
     */
    @Test
    public void testConstants() {
        Calculator calculator = new Calculator();

        calculator.expression("pi");
        assertEquals("3.141592653589793", calculator.solveExpression());

        calculator.expression("e");
        assertEquals("2.718281828459045", calculator.solveExpression());

        calculator.expression("pi+e");
        assertEquals("5.859874482048838", calculator.solveExpression());

        calculator.expression("pi+e-1");
        assertEquals("4.859874482048838", calculator.solveExpression());

        calculator.expression("1+e");
        assertEquals("3.718281828459045", calculator.solveExpression());

        calculator.expression("pi*pi");
        assertEquals("9.869604401089358", calculator.solveExpression());

        calculator.expression("pi/pi");
        assertEquals("1.0", calculator.solveExpression());
    }

    /**
     * Basic testing with exponents
     */
    public void testExponents() {
        Calculator calculator = new Calculator();

        calculator.expression("0^1393");
        assertEquals("1.0", calculator.solveExpression());

        calculator.expression("pi^2");
        assertEquals("9.869604401089358", calculator.solveExpression());

        calculator.expression("2^2^2");
        assertEquals("16.0", calculator.solveExpression());

        calculator.expression("0^0");
        assertEquals("1.0", calculator.solveExpression());
    }

    /**
     * Basic testing with parentheses
     */
    @Test
    public void testParentheses() {
        Calculator calculator = new Calculator();

        // Expected...
        // calculator.setUp("(1)");
        // assertEquals("1.0", calculator.solveExpression());
        calculator.expression("(1)");
        assertEquals("1", calculator.solveExpression());

        calculator.expression("((((((1-1))))))+1-(1)");
        assertEquals("0.0", calculator.solveExpression());

        calculator.expression("-(1)");
        assertEquals("-1.0", calculator.solveExpression());
    }

    /**
     * Mixed operations
     */
    @Test
    public void testMixedOperatons() {
        Calculator calculator = new Calculator();

        calculator.expression("95-10+2^(3+3)*10");
        assertEquals("725.0", calculator.solveExpression());

        calculator.expression("235^(2-1)*54-(174722/2)-pi");
        assertEquals("-74674.14159265358", calculator.solveExpression());

        calculator.expression("(pi^2+pi*e)/pi-pi");
        assertEquals("2.7182818284590446", calculator.solveExpression());

        calculator.expression("7+7/7+7*7-7");
        assertEquals("50.0", calculator.solveExpression());
    }

    /**
     * Basic testing with functions
     */
    @Test
    public void testMathFunctions() {
        Calculator calculator = new Calculator();

        calculator.expression("sin(0)");
        assertEquals("0.0", calculator.solveExpression());

        calculator.expression("sin(pi)");
        assertEquals("1.2246467991473532E-16", calculator.solveExpression());

        calculator.expression("cos(0)");
        assertEquals("1.0", calculator.solveExpression());

        calculator.expression("cos(pi)");
        assertEquals("-1.0", calculator.solveExpression());

        calculator.expression("cos(4958.111+35-e)");
        assertEquals("0.02720869756712837", calculator.solveExpression());

        calculator.expression("sin(-pi/2)");
        assertEquals("-1.0", calculator.solveExpression());

        calculator.expression("tan(0.000134566+cos(pi+1)-1*2)");
        assertEquals("0.6862306858063052", calculator.solveExpression());

        calculator.expression("sqrt(4)");
        assertEquals("2.0", calculator.solveExpression());

        calculator.expression("sqrt(4858.001)");
        assertEquals("69.69936154657373", calculator.solveExpression());

        calculator.expression("ln(e)");
        assertEquals("1.0", calculator.solveExpression());

        calculator.expression("sin(pi)^2+cos(pi)^2");
        assertEquals("1.0", calculator.solveExpression());
    }

    /**
     * This is the advanced test.
     * 
     * This test gives the program extremely tricky expressions to solve. As a
     * result, most of the errors will be caught here.
     */
    @Test
    public void testAdvanced() {
        Calculator calculator = new Calculator();

        calculator.expression("ln(1+(1+1))");
        assertEquals("1.0986122886681098", calculator.solveExpression());

        calculator.expression("ln(746+23413.303+(-3855/12)-3+3)");
        assertEquals("10.079038448381066", calculator.solveExpression());

        // Will not pass the test...
        // calculator.setUp("ln(746+23413.303-3855/12-((3+3)-1/2))");
        // assertEquals("10.078807698219242", calculator.solveExpression());

        calculator.expression("54/2-(((29+sqrt(2))+sin(pi/3)-1.89*2)/2)/ln(abs(-90))");
        assertEquals("23.94428864986426", calculator.solveExpression());

        calculator.expression("sin(20)+pi*2");
        assertEquals("7.196130557907214", calculator.solveExpression());
    }

    @Test
    public void testMisc() {
        Calculator calculator = new Calculator();

        calculator.expression("-1");
        assertEquals("-1", calculator.solveExpression());

        calculator.expression("<+1");
        assertEquals("0.0", calculator.solveExpression());

        calculator.expression("123-1");
        assertEquals("122.0", calculator.solveExpression());

        calculator.expression("(123)-1");
        assertEquals("122.0", calculator.solveExpression());
    }
}
