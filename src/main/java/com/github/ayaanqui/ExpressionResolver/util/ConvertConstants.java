package com.github.ayaanqui.ExpressionResolver.util;

import java.util.LinkedList;
import java.util.Map;

public class ConvertConstants {
    private Map<String, Double> constantMap;
    private LinkedList<String> userInpList;

    public ConvertConstants(LinkedList<String> userInpList, Map<String, Double> variableMap) {
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