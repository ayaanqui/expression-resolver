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
            final int iC = i;
            constantMap.forEach((key, value) -> {
                if (userInpList.get(iC).equals(key))
                    userInpList.set(iC, value.toString());
                else if (userInpList.get(iC).equals("-" + key)) // Deals with negative constants
                    userInpList.set(iC, "-" + value);
            });
        }
    }
}