package com.github.ayaanqui.expressionresolver;

import org.junit.Test;
import static org.junit.Assert.*;

public class ErrorTest {
    @Test
    public void emptyOperatorsTest() {
        Resolver resolver = new Resolver();

        assertFalse(resolver.setExpression("-").solveExpression().success);
        assertFalse(resolver.setExpression("+").solveExpression().success);
        assertFalse(resolver.setExpression("*").solveExpression().success);
        assertFalse(resolver.setExpression("/").solveExpression().success);
    }

    @Test
    public void partialOperatorsTest() {
        Resolver resolver = new Resolver();

        assertFalse(resolver.setExpression("1-").solveExpression().success);
        assertFalse(resolver.setExpression("1+").solveExpression().success);
        assertFalse(resolver.setExpression("1*").solveExpression().success);
        assertFalse(resolver.setExpression("*1").solveExpression().success);
        assertFalse(resolver.setExpression("/1").solveExpression().success);
        assertFalse(resolver.setExpression("1/").solveExpression().success);
    }

    @Test
    public void parenthesesMismatchTest() {
        Resolver resolver = new Resolver();

        assertFalse(resolver.setExpression("(").solveExpression().success);
        assertFalse(resolver.setExpression("((1+1)").solveExpression().success);
        assertFalse(resolver.setExpression("(1-2)/sin((e*2^pi)/tau").solveExpression().success);
        assertFalse(resolver.setExpression("1+(((((((((((1-1))))+2+2))))))))").solveExpression().success);
        assertFalse(resolver.setExpression("))").solveExpression().success);
    }

    @Test
    public void emptyParenthesesTest() {
        Resolver resolver = new Resolver();

        assertFalse(resolver.setExpression("()").solveExpression().success);
        assertFalse(resolver.setExpression("()()()").solveExpression().success);
    }

    @Test
    public void incorrectFunctionParameterTest() {
        Resolver resolver = new Resolver();

        assertFalse(resolver.setExpression("sin()").solveExpression().success);
        // log requires 2 parameters log(n, base)
        assertFalse(resolver.setExpression("log(10)").solveExpression().success);
    }

    @Test
    public void incorrectVariableAssignmentTest() {
        Resolver resolver = new Resolver();

        assertFalse(resolver.setExpression("1=2").solveExpression().success);
        assertFalse(resolver.setExpression("pi = 3.142").solveExpression().success);
        assertFalse(resolver.setExpression("name = ayaan").solveExpression().success);

        // Set variable...
        assertTrue(resolver.setExpression("time_diff = 10").solveExpression().success);
        assertFalse(resolver.setExpression("time_diff = 9").solveExpression().success);
    }

    @Test
    public void miscTest() {
        Resolver resolver = new Resolver();

        assertFalse(resolver.setExpression("hello").solveExpression().success);
        assertFalse(resolver.setExpression("The quick brown fox jumps over the lazy boy").solveExpression().success);
        assertFalse(resolver.setExpression(",").solveExpression().success);
    }
}
