package com.ayaanqui.calculator;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for some simple cases
 */
public class AppTest {
    /**
     * Basic testing for addition with two values, x+y
     */
    @Test
    public void testBasicAddition() {
        Calculator calculator = new Calculator();

        calculator.setUp("2+2");
        assertEquals("4.0", calculator.solveExpression());

        calculator.setUp("12+10");
        assertEquals("22.0", calculator.solveExpression());

        calculator.setUp("345+1393");
        assertEquals("1738.0", calculator.solveExpression());

        calculator.setUp("0+0");
        assertEquals("0.0", calculator.solveExpression());

        calculator.setUp("5891+42411");
        assertEquals("48302.0", calculator.solveExpression());

        calculator.setUp("233.890455+1");
        assertEquals("234.890455", calculator.solveExpression());

        calculator.setUp("3.14159786663+1564.9923");
        assertEquals("1568.13389786663", calculator.solveExpression());
    }

    @Test
    public void testBasicSubtraction() {
        Calculator calculator = new Calculator();

        calculator.setUp("4-2");
        assertEquals("2.0", calculator.solveExpression());

        calculator.setUp("34905-1112");
        assertEquals("33793.0", calculator.solveExpression());

        calculator.setUp("0-0");
        assertEquals("0.0", calculator.solveExpression());

        calculator.setUp("1-1");
        assertEquals("0.0", calculator.solveExpression());

        calculator.setUp("15-34");
        assertEquals("-19.0", calculator.solveExpression());

        calculator.setUp("233.890455-1");
        assertEquals("232.890455", calculator.solveExpression());

        calculator.setUp("234.983494942754-15969.1085015998");
        assertEquals("-15734.125006657046", calculator.solveExpression());
    }
}
