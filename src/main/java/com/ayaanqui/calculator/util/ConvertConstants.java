package com.ayaanqui.calculator.util;

import java.util.HashMap;
import java.util.ArrayList;

public class ConvertConstants {
    final private String[] constants = { "pi", "e", "tau" };
    private HashMap<String, Double> constantMap = new HashMap<>();
    private ArrayList<String> userInpList;

    public ConvertConstants(ArrayList<String> userInpList) {
        this.userInpList = userInpList;

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