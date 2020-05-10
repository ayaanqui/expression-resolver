package com.ayaanqui.calculator.objects;

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
public class Response {
    public boolean success;
    public double result;
    public String[] errors;

    public Response() {
    }

    public String toString() {
        return (success) ? Double.toString(this.result) : Arrays.toString(this.errors);
    }
}