package server.managers;

import common.models.HumanBeing;
import java.io.*;
import java.util.Vector;
import java.util.stream.Collectors; // Добавлен импорт

public class DumpManager {
    private final String fileName;

    public DumpManager(String fileName) {
        this.fileName = fileName;
    }

    private String collectionToCSV(Vector<HumanBeing> collection) {
        return collection.stream()
                .map(h -> String.join(";", HumanBeing.toArray(h)))
                .collect(Collectors.joining("\n"));
    }

    public void writeCollection(Vector<HumanBeing> collection) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(collectionToCSV(collection));
            System.out.println("Коллекция сохранена в файл");
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл: " + e.getMessage());
        }
    }

    public boolean readCollection(Vector<HumanBeing> collection) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    HumanBeing human = HumanBeing.fromArray(line.split(";"));
                    if (human != null && human.validate()) {
                        collection.add(human);
                    }
                }
            }
            System.out.println("Коллекция загружена из файла");
            return true;
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
            return false;
        }
    }
}