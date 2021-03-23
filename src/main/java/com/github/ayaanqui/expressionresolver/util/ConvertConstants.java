package com.github.ayaanqui.expressionresolver.util;

import java.util.List;
import java.util.Map;

public class ConvertConstants {
    private Map<String, Double> constantMap;
    private List<String> userInpList;

    public ConvertConstants(List<String> userInpList, Map<String, Double> variableMap) {
        this.userInpList = userInpList;
        this.constantMap = variableMap;
    }

    public void convert() {
        for (int i = 0; i < userInpList.size(); i++) {
            final int iC = i;
            constantMap.forEach((key, value) -> {
                if (userInpList.get(iC).equals(key))
                    userInpList.set(iC, value.toString());
            });
        }
    }
}