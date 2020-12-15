package com.github.ayaanqui.ExpressionResolver.util;

import java.util.TreeMap;
import java.util.LinkedList;
import java.util.Map;

public class ConvertConstants {
    private TreeMap<String, Double> constantMap;
    private LinkedList<String> userInpList;

    public ConvertConstants(LinkedList<String> userInpList) {
        this.userInpList = userInpList;
        this.constantMap = new TreeMap<>();

        // Define constants
        constantMap.put("pi", Math.PI);
        constantMap.put("e", Math.E);
        constantMap.put("tau", 2 * Math.PI);
    }

    public void addConstantMap(TreeMap<String, Double> map) {
        constantMap.putAll(map);
    }

    public void convert() {
        for (int i = 0; i < userInpList.size(); i++) {
            for (Map.Entry<String, Double> constant : constantMap.entrySet()) {
                if (userInpList.get(i).equals(constant.getKey()))
                    userInpList.set(i, constant.getValue().toString());
                else if (userInpList.get(i).equals("-" + constant.getKey())) // Deals with negative constants
                    userInpList.set(i, "-" + constant.getValue());
            }
        }
    }
}