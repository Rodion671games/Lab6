package server.managers;

import java.time.ZonedDateTime;
import java.util.Vector;
import java.util.stream.Collectors;
import common.models.*;

public class CollectionManager {
    private int currentId = 1;
    private Vector<HumanBeing> collection = new Vector<>();
    private ZonedDateTime lastInitTime;
    private ZonedDateTime lastSaveTime;
    private final DumpManager dumpManager;

    public CollectionManager(DumpManager dumpManager) {
        this.dumpManager = dumpManager;
        this.lastInitTime = null;
        this.lastSaveTime = null;
        loadCollection();
    }

    public Vector<HumanBeing> getCollection() {
        return new Vector<>(collection.stream()
                .sorted((h1, h2) -> h1.getName().compareTo(h2.getName()))
                .collect(Collectors.toList()));
    }

    public ZonedDateTime getLastInitTime() {
        return lastInitTime;
    }

    public ZonedDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    public void saveCollection() {
        dumpManager.writeCollection(collection);
        lastSaveTime = ZonedDateTime.now();
    }

    public HumanBeing byId(Integer id) {
        return collection.stream()
                .filter(h -> h.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public int getFreeId() {
        while (byId(currentId) != null) {
            currentId++;
        }
        return currentId;
    }

    public boolean add(HumanBeing h) {
        if (h == null || !h.validate()) return false;
        collection.add(h);
        return true;
    }

    public boolean remove(Integer id) {
        return collection.removeIf(h -> h.getId().equals(id));
    }

    public boolean insertAt(int index, HumanBeing h) {
        if (index < 0 || index > collection.size() || h == null || !h.validate()) return false;
        collection.add(index, h);
        return true;
    }

    public boolean addIfMax(HumanBeing h) {
        if (h == null || !h.validate()) return false;
        if (collection.isEmpty() || h.compareTo(collection.stream().max(HumanBeing::compareTo).get()) > 0) {
            collection.add(h);
            return true;
        }
        return false;
    }

    public void sort() {
        collection = new Vector<>(collection.stream()
                .sorted((h1, h2) -> h1.getName().compareTo(h2.getName()))
                .collect(Collectors.toList()));
    }

    public int countLessThanMood(Mood mood) {
        return (int) collection.stream()
                .filter(h -> h.getMood().compareTo(mood) < 0)
                .count();
    }

    public String printDescending() {
        return collection.stream()
                .sorted((h1, h2) -> h2.compareTo(h1))
                .map(HumanBeing::toString)
                .collect(Collectors.joining("\n"));
    }

    public String printFieldAscendingCar() {
        return collection.stream()
                .map(HumanBeing::getCar)
                .sorted((c1, c2) -> c1.getName().compareTo(c2.getName()))
                .map(Car::toString)
                .collect(Collectors.joining("\n"));
    }

    public boolean loadCollection() {
        collection.clear();
        if (dumpManager.readCollection(collection)) {
            lastInitTime = ZonedDateTime.now();
            currentId = collection.stream()
                    .mapToInt(HumanBeing::getId)
                    .max()
                    .orElse(0) + 1;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        if (collection.isEmpty()) return "Коллекция пуста!";
        return collection.stream()
                .sorted((h1, h2) -> h1.getName().compareTo(h2.getName()))
                .map(HumanBeing::toString)
                .collect(Collectors.joining("\n"));
    }
}