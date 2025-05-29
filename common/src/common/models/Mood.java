package common.models;

import java.io.Serializable;

public enum Mood {
    SAD, HAPPY, ANGRY;

    public static String names() {
        StringBuilder nameList = new StringBuilder();
        for (var mood : values()) {
            nameList.append(mood.name()).append(", ");
        }
        return nameList.substring(0, nameList.length() - 2);
    }
}