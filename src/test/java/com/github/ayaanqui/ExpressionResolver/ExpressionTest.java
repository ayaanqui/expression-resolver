package com.github.ayaanqui.ExpressionResolver;

import org.junit.Test;
import static org.junit.Assert.*;

import com.github.ayaanqui.ExpressionResolver.Resolver;

public class ExpressionTest {
    /**
     * Basic testing with just addition
     */
    @Test
    public void testBasicAddition() {
        Resolver calculator = new Resolver();

        // Addition
        calculator.setExpression("2+2");
        assertEquals("4.0", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("12+10");
        assertEquals("22.0", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("345+1393");
        assertEquals("1738.0", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("0+0");
        assertEquals("0.0", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("5891+42411");
        assertEquals("48302.0", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("233.890455+1");
        assertEquals("234.890455", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("3.14159786663+1564.9923");
        assertEquals("1568.13389786663", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("+1");
        assertEquals("1.0", Double.toString(calculator.solveExpression().result));
    }

    /**
     * Basic testing with just subtraction
     */
    @Test
    public void testBasicSubtraction() {
        Resolver calculator = new Resolver();

        // Subtraction
        calculator.setExpression("4-2");
        assertEquals("2.0", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("34905-1112");
        assertEquals("33793.0", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("0-0");
        assertEquals("0.0", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("1-1");
        assertEquals("0.0", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("-1");
        assertEquals("-1.0", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("15-34");
        assertEquals("-19.0", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("233.890455-1");
        assertEquals("232.890455", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("234.983494942754-15969.1085015998");
        assertEquals("-15734.125006657046", Double.toString(calculator.solveExpression().result));

        // calculator.setUp("--1");
        // assertEquals("1", Double.toString(calculator.solveExpression().result));
    }

    /**
     * Basic testing with addition and subtraction
     */
    public void testAdditionAndSubtraction() {
        Resolver calculator = new Resolver();

        calculator.setExpression("1+1-1-1");
        assertEquals("0.0", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("21344-8822-82+123-12-3485.123");
        assertEquals("9065.877", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("-1+-1");
        assertEquals("-2.0", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("28--25+056.200+-14");
        assertEquals("95.2", Double.toString(calculator.solveExpression().result));
    }

    /**
     * Basic testing with multiplication and division
     */
    @Test
    public void testMultiplicationDivision() {
        Resolver calculator = new Resolver();

        calculator.setExpression("0*0");
        assertEquals("0.0", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("0*1");
        assertEquals("0.0", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("4*0");
        assertEquals("0.0", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("2349*133*1249");
        assertEquals("3.90208833E8", Double.toString(calculator.solveExpression().result));

        // Division
        calculator.setExpression("0/198");
        assertEquals("0.0", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("4/2");
        assertEquals("2.0", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("384/13/1933");
        assertEquals("0.015281149269767999", Double.toString(calculator.solveExpression().result));
    }

    /**
     * Basic testing with constants
     */
    @Test
    public void testConstants() {
        Resolver calculator = new Resolver();

        calculator.setExpression("pi");
        assertEquals("3.141592653589793", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("e");
        assertEquals("2.718281828459045", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("pi+e");
        assertEquals("5.859874482048838", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("pi+e-1");
        assertEquals("4.859874482048838", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("1+e");
        assertEquals("3.718281828459045", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("pi*pi");
        assertEquals("9.869604401089358", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("pi/pi");
        assertEquals("1.0", Double.toString(calculator.solveExpression().result));
    }

    /**
     * Basic testing with exponents
     */
    public void testExponents() {
        Resolver calculator = new Resolver();

        calculator.setExpression("0^1393");
        assertEquals("1.0", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("pi^2");
        assertEquals("9.869604401089358", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("2^2^2");
        assertEquals("16.0", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("0^0");
        assertEquals("1.0", Double.toString(calculator.solveExpression().result));
    }

    /**
     * Basic testing with parentheses
     */
    @Test
    public void testParentheses() {
        Resolver calculator = new Resolver();

        // Expected...
        // calculator.setUp("(1)");
        // assertEquals("1.0", Double.toString(calculator.solveExpression().result));
        calculator.setExpression("(1)");
        assertEquals("1.0", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("((((((1-1))))))+1-(1)");
        assertEquals("0.0", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("-(1)");
        assertEquals("-1.0", Double.toString(calculator.solveExpression().result));
    }

    /**
     * Mixed operations
     */
    @Test
    public void testMixedOperatons() {
        Resolver calculator = new Resolver();

        calculator.setExpression("95-10+2^(3+3)*10");
        assertEquals("725.0", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("235^(2-1)*54-(174722/2)-pi");
        assertEquals("-74674.14159265358", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("(pi^2+pi*e)/pi-pi");
        assertEquals("2.7182818284590446", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("7+7/7+7*7-7");
        assertEquals("50.0", Double.toString(calculator.solveExpression().result));
    }

    /**
     * Basic testing with functions
     */
    @Test
    public void testMathFunctions() {
        Resolver calculator = new Resolver();

        calculator.setExpression("sin(0)");
        assertEquals("0.0", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("sin(pi)");
        assertEquals("1.2246467991473532E-16", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("cos(0)");
        assertEquals("1.0", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("cos(pi)");
        assertEquals("-1.0", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("cos(4958.111+35-e)");
        assertEquals("0.02720869756712837", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("sin(-pi/2)");
        assertEquals("-1.0", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("tan(0.000134566+cos(pi+1)-1*2)");
        assertEquals("0.6862306858063052", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("sqrt(4)");
        assertEquals("2.0", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("sqrt(4858.001)");
        assertEquals("69.69936154657373", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("ln(e)");
        assertEquals("1.0", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("sin(pi)^2+cos(pi)^2");
        assertEquals("1.0", Double.toString(calculator.solveExpression().result));
    }

    /**
     * This is the advanced test.
     * 
     * This test gives the program extremely tricky expressions to solve. As a
     * result, most of the errors will be caught here.
     */
    @Test
    public void testAdvanced() {
        Resolver calculator = new Resolver();

        calculator.setExpression("ln(1+(1+1))");
        assertEquals("1.0986122886681098", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("ln(746+23413.303+(-3855/12)-3+3)");
        assertEquals("10.079038448381066", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("ln(746+23413.303-3855/12-((3+3)-1/2))");
        assertEquals("10.078807698219242", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("54/2-(((29+sqrt(2))+sin(pi/3)-1.89*2)/2)/ln(abs(-90))");
        assertEquals("23.94428864986426", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("sin(20)+pi*2");
        assertEquals("7.196130557907214", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("ln(abs(-90))/(sin(3*pi/56)+fact(6)-33*234.22113454-(sqrt(3)))");
        assertEquals("-6.418340113298676E-4", Double.toString(calculator.solveExpression().result));
    }

    @Test
    public void testMisc() {
        Resolver calculator = new Resolver();

        calculator.setExpression("-1");
        assertEquals("-1.0", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("<+1");
        assertEquals("0.0", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("123-1");
        assertEquals("122.0", Double.toString(calculator.solveExpression().result));

        calculator.setExpression("(123)-1");
        assertEquals("122.0", Double.toString(calculator.solveExpression().result));
    }
}
