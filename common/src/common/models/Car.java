package common.models;

import common.utility.Validatable;
import java.io.Serializable;

public class Car implements Validatable, Serializable {
    private String name;

    public Car(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean validate() {
        return name != null && !name.isEmpty();
    }

    @Override
    public String toString() {
        return name;
    }
}