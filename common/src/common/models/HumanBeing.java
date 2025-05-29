package common.models;

import common.utility.Element;
import common.utility.Validatable;
import java.time.ZonedDateTime;
import java.io.Serializable;

public class HumanBeing extends Element implements Validatable, Serializable {
    private Integer id;
    private String name;
    private Coordinates coordinates;
    private ZonedDateTime creationDate;
    private Boolean realHero;
    private Boolean hasToothpick;
    private Double impactSpeed;
    private Double minutesOfWaiting;
    private WeaponType weaponType;
    private Mood mood;
    private Car car;

    public HumanBeing(Integer id, String name, Coordinates coordinates, Boolean realHero, Boolean hasToothpick,
                      Double impactSpeed, Double minutesOfWaiting, WeaponType weaponType, Mood mood, Car car) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = ZonedDateTime.now();
        this.realHero = realHero;
        this.hasToothpick = hasToothpick;
        this.impactSpeed = impactSpeed;
        this.minutesOfWaiting = minutesOfWaiting;
        this.weaponType = weaponType;
        this.mood = mood;
        this.car = car;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Boolean getRealHero() {
        return realHero;
    }

    public Boolean getHasToothpick() {
        return hasToothpick;
    }

    public Double getImpactSpeed() {
        return impactSpeed;
    }

    public Double getMinutesOfWaiting() {
        return minutesOfWaiting;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public Mood getMood() {
        return mood;
    }

    public Car getCar() {
        return car;
    }

    @Override
    public boolean validate() {
        return id != null && id > 0 &&
                name != null && !name.isEmpty() &&
                coordinates != null && coordinates.validate() &&
                creationDate != null &&
                realHero != null && hasToothpick != null &&
                impactSpeed != null && minutesOfWaiting != null &&
                weaponType != null && mood != null &&
                car != null && car.validate();
    }

    public static HumanBeing fromArray(String[] a) {
        try {
            Integer id = Integer.parseInt(a[0]);
            String name = a[1];
            Coordinates coordinates = new Coordinates(Integer.parseInt(a[2].split(";")[0]), Double.parseDouble(a[2].split(";")[1]));
            Boolean realHero = Boolean.parseBoolean(a[3]);
            Boolean hasToothpick = Boolean.parseBoolean(a[4]);
            Double impactSpeed = Double.parseDouble(a[5]);
            Double minutesOfWaiting = Double.parseDouble(a[6]);
            WeaponType weaponType = WeaponType.valueOf(a[7]);
            Mood mood = Mood.valueOf(a[8]);
            Car car = new Car(a[9]);
            return new HumanBeing(id, name, coordinates, realHero, hasToothpick, impactSpeed, minutesOfWaiting, weaponType, mood, car);
        } catch (Exception e) {
            return null;
        }
    }

    public static String[] toArray(HumanBeing e) {
        return new String[] {
                e.getId().toString(),
                e.getName(),
                e.getCoordinates().toString(),
                e.getRealHero().toString(),
                e.getHasToothpick().toString(),
                e.getImpactSpeed().toString(),
                e.getMinutesOfWaiting().toString(),
                e.getWeaponType().toString(),
                e.getMood().toString(),
                e.getCar().toString()
        };
    }

    @Override
    public String toString() {
        return "HumanBeing{\"id\": " + id + ", \"name\": \"" + name + "\", \"coordinates\": \"" + coordinates +
                "\", \"realHero\": " + realHero + ", \"hasToothpick\": " + hasToothpick +
                ", \"impactSpeed\": " + impactSpeed + ", \"minutesOfWaiting\": " + minutesOfWaiting +
                ", \"weaponType\": \"" + weaponType + "\", \"mood\": \"" + mood + "\", \"car\": \"" + car + "\"}";
    }

    @Override
    public int compareTo(Element o) {
        return this.getId().compareTo(o.getId());
    }
}