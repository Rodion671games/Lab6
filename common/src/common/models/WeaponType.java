package common.models;

import java.io.Serializable;

public enum WeaponType {
    PISTOL, SHOTGUN, RIFLE;

    public static String names() {
        StringBuilder nameList = new StringBuilder();
        for (var weaponType : values()) {
            nameList.append(weaponType.name()).append(", ");
        }
        return nameList.substring(0, nameList.length() - 2);
    }
}