package com.ayaanqui.calculator.util;

import java.util.HashMap;
import java.util.LinkedList;

public class ConvertConstants {
    private final String[] constants = { "pi", "e", "tau" };
    private HashMap<String, Double> constantMap;
    private LinkedList<String> userInpList;

    public ConvertConstants(LinkedList<String> userInpList) {
        this.userInpList = userInpList;
        this.constantMap = new HashMap<>();

        // Define constants
        constantMap.put("pi", Math.PI);
        constantMap.put("e", Math.E);
        constantMap.put("tau", 2 * Math.PI);
    }

    public void convert() {
        for (int i = 0; i < userInpList.size(); i++) {
            for (String constant : constants) {
                if (userInpList.get(i).equals(constant))
                    userInpList.set(i, constantMap.get(constant) + "");
                else if (userInpList.get(i).equals("-" + constant)) // Deals with negative constants
                    userInpList.set(i, "-" + constantMap.get(constant));
            }
        }
    }
}