package com.github.ayaanqui.expressionresolver;

import org.junit.Test;
import static org.junit.Assert.*;

import com.github.ayaanqui.expressionresolver.objects.Response;

public class FunctionTest {
    /**
     *
     */
    private static final double DIFF = 0.0000000000000001;

    @Test
    public void basicFunctionTest() {
        Resolver resolver = new Resolver();
        Response res;

        res = resolver.setExpression("sin(pi)").solveExpression();
        assertTrue(res.success);
        assertEquals(Math.sin(Math.PI), res.result, DIFF);

        res = resolver.setExpression("cos(pi)").solveExpression();
        assertTrue(res.success);
        assertEquals(Math.cos(Math.PI), res.result, DIFF);

        res = resolver.setExpression("tan(pi)").solveExpression();
        assertTrue(res.success);
        assertEquals(Math.tan(Math.PI), res.result, DIFF);

        res = resolver.setExpression("ln(67)").solveExpression();
        assertTrue(res.success);
        assertEquals(Math.log(67), res.result, DIFF);

        res = resolver.setExpression("sqrt(4748)").solveExpression();
        assertTrue(res.success);
        assertEquals(Math.sqrt(4748), res.result, DIFF);

        res = resolver.setExpression("deg(2*pi)").solveExpression();
        assertTrue(res.success);
        assertEquals((double) 360, res.result, DIFF);

        res = resolver.setExpression("log(67, e)").solveExpression();
        assertTrue(res.success);
        assertEquals(Math.log(67) / Math.log(Math.E), res.result, DIFF);

        res = resolver.setExpression("log(34758755, 67.34)").solveExpression();
        assertTrue(res.success);
        assertEquals(Math.log(34758755) / Math.log(67.34), res.result, DIFF);

        res = resolver.setExpression("abs(-tau)").solveExpression();
        assertTrue(res.success);
        assertEquals(2 * Math.PI, res.result, DIFF);

        res = resolver.setExpression("exp(100)").solveExpression();
        assertTrue(res.success);
        assertEquals(Math.exp(100), res.result, DIFF);

        res = resolver.setExpression("avg(1,2,3,4)").solveExpression();
        assertTrue(res.success);
        assertEquals(2.5, res.result, DIFF);

        res = resolver.setExpression("sum(1,2,3,4)").solveExpression();
        assertTrue(res.success);
        assertEquals(10, res.result, DIFF);
    }

    @Test
    public void multiParamTest() {
        Resolver resolver = new Resolver();
        Response res;

        res = resolver.setExpression("avg(1,2,3").solveExpression();
        assertFalse(res.success);

        res = resolver.setExpression("sum,1,2,3").solveExpression();
        assertFalse(res.success);

        res = resolver.setExpression("log(1)").solveExpression();
        assertFalse(res.success);
    }

    @Test
    public void nestedMultiParamFunctionTest() {
        Resolver resolver = new Resolver();
        Response res;

        res = resolver.setExpression("sum(sin(574)^2+cos(574)^2, 2, 4563+26444/exp(12), 3, -99, 2050, pi)")
                .solveExpression();
        assertTrue(res.success);
        assertEquals(1.0 + 2 + (4563 + 26444 / Math.exp(12)) + 3 + (-99) + 2050 + Math.PI, res.result, DIFF);

        // No support for nested multi-param functions due to commas
        /*
         * res = resolver.
         * setExpression("sum(sin(574)^2+cos(574)^2, log(2,4), 4563+26444/exp(12), 3, -99, 2050, pi)"
         * ) .solveExpression(); assertTrue(res.success); assertEquals(1 + 2 + (4563 +
         * 26444 / Math.exp(12)+3-99+2050+Math.PI), res);
         */
    }

    @Test
    public void setFunctionTest() {
        Resolver resolver = new Resolver();
        Response res;

        res = resolver.setExpression("decrement(1,2,3,4,5)").setFunction("decrement", args -> {
            double output = 0.0;
            for (double arg : args)
                output -= arg;
            return output;
        }).solveExpression();
        assertTrue(res.success);
        assertEquals(-1 - 2 - 3 - 4 - 5, res.result, DIFF);

        res = resolver.setExpression("min(193,42,3.2,45,4,5,pi)").setFunction("min", args -> {
            double min = args[0];
            for (double arg : args) {
                if (arg < min)
                    min = arg;
            }
            return min;
        }).solveExpression();
        assertTrue(res.success);
        assertEquals(Math.PI, res.result, DIFF);
    }

    @Test
    public void degRadConversionFunctionTest() {
        Resolver res = new Resolver();

        res.setExpression("deg(pi)");
        assertEquals(180, res.solveExpression().result, DIFF);

        res.setExpression("rad(180)");
        assertEquals(Math.PI, res.solveExpression().result, DIFF);

        res.setExpression("cos(rad(11))");
        assertEquals(0.981627183447664, res.solveExpression().result, DIFF);
    }
}
