package com.github.ayaanqui.ExpressionResolver.util;

import java.util.TreeMap;
import java.util.LinkedList;
import java.util.Map;

public class ConvertConstants {
    private TreeMap<String, Double> constantMap;
    private LinkedList<String> userInpList;

    public ConvertConstants(LinkedList<String> userInpList, TreeMap<String, Double> variableMap) {
        this.userInpList = userInpList;
        this.constantMap = variableMap;
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