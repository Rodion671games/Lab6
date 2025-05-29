package client.models;

import client.utility.Console;
import common.models.*;
import java.util.NoSuchElementException;

public class Ask {
    public static class AskBreak extends Exception {}

    public static HumanBeing askHumanBeing(Console console, int id) throws AskBreak {
        try {
            console.print("name: ");
            String name;
            while (true) {
                name = console.readln().trim();
                if (name.equals("exit")) throw new AskBreak();
                if (!name.isEmpty()) break;
                console.print("name (cannot be empty): ");
            }

            console.print("coordinates.x: ");
            Integer x;
            while (true) {
                String line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                try {
                    x = Integer.parseInt(line);
                    break;
                } catch (NumberFormatException e) {
                    console.print("coordinates.x (must be an integer): ");
                }
            }

            console.print("coordinates.y: ");
            Double y;
            while (true) {
                String line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                try {
                    y = Double.parseDouble(line);
                    break;
                } catch (NumberFormatException e) {
                    console.print("coordinates.y (must be a double): ");
                }
            }
            Coordinates coordinates = new Coordinates(x, y);

            console.print("realHero (true/false): ");
            Boolean realHero;
            while (true) {
                String line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (line.equals("true") || line.equals("false")) {
                    realHero = Boolean.parseBoolean(line);
                    break;
                }
                console.print("realHero (true/false): ");
            }

            console.print("hasToothpick (true/false): ");
            Boolean hasToothpick;
            while (true) {
                String line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (line.equals("true") || line.equals("false")) {
                    hasToothpick = Boolean.parseBoolean(line);
                    break;
                }
                console.print("hasToothpick (true/false): ");
            }

            console.print("impactSpeed: ");
            Double impactSpeed;
            while (true) {
                String line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                try {
                    impactSpeed = Double.parseDouble(line);
                    break;
                } catch (NumberFormatException e) {
                    console.print("impactSpeed (must be a double): ");
                }
            }

            console.print("minutesOfWaiting: ");
            Double minutesOfWaiting;
            while (true) {
                String line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                try {
                    minutesOfWaiting = Double.parseDouble(line);
                    break;
                } catch (NumberFormatException e) {
                    console.print("minutesOfWaiting (must be a double): ");
                }
            }

            console.print("weaponType (" + WeaponType.names() + "): ");
            WeaponType weaponType;
            while (true) {
                String line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                try {
                    weaponType = WeaponType.valueOf(line);
                    break;
                } catch (IllegalArgumentException e) {
                    console.print("weaponType (" + WeaponType.names() + "): ");
                }
            }

            console.print("mood (" + Mood.names() + "): ");
            Mood mood;
            while (true) {
                String line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                try {
                    mood = Mood.valueOf(line);
                    break;
                } catch (IllegalArgumentException e) {
                    console.print("mood (" + Mood.names() + "): ");
                }
            }

            console.print("car.name: ");
            String carName;
            while (true) {
                carName = console.readln().trim();
                if (carName.equals("exit")) throw new AskBreak();
                if (!carName.isEmpty()) break;
                console.print("car.name (cannot be empty): ");
            }
            Car car = new Car(carName);

            return new HumanBeing(id, name, coordinates, realHero, hasToothpick, impactSpeed, minutesOfWaiting,
                    weaponType, mood, car);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения: " + e.getMessage());
            return null;
        }
    }
}