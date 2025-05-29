package common.models;

import common.utility.Validatable;
import java.io.Serializable;

public class Coordinates implements Validatable, Serializable {
    private Integer x;
    private Double y;

    public Coordinates(Integer x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    @Override
    public boolean validate() {
        return x != null && y != null;
    }

    @Override
    public String toString() {
        return x + ";" + y;
    }
}