package com.github.ayaanqui.expressionresolver.objects;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Class for handling errors, and holding the correct value if there are no
 * errors.
 * 
 * Usage: set success = false if there was an error trying process the
 * expression and set error = new String[] {"Example error...", "More errors"}.
 * If the expression was valid and processed successfully then set success =
 * true and set result to the processed value.
 */
public class Response implements Serializable {
    private static final long serialVersionUID = 1L;

    public boolean success;
    public double result;
    public String[] errors;

    public static Response getSuccess(double result) {
        Response res = new Response();
        res.success = true;
        res.result = result;
        return res;
    }

    public static Response getError(String... errors) {
        Response res = new Response();
        res.success = false;
        res.errors = errors;
        return res;
    }

    public String toString() {
        return (success) ? Double.toString(this.result) : Arrays.toString(this.errors);
    }
}