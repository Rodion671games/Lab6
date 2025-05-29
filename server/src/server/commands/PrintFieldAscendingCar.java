package server.commands;

import server.managers.CollectionManager;
import common.Response;
import common.Request;

public class PrintFieldAscendingCar extends Command {
    private final CollectionManager collectionManager;

    public PrintFieldAscendingCar(CollectionManager collectionManager) {
        super("print_field_ascending_car", "вывести значения поля car в порядке возрастания");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response apply(Request request) {
        if (request.getArguments().length > 0 && !request.getArguments()[0].isEmpty()) {
            return new Response(false, "Неправильное количество аргументов!");
        }
        if (collectionManager.getCollection().isEmpty()) {
            return new Response(true, "Коллекция пуста!");
        }
        return new Response(true, collectionManager.printFieldAscendingCar());
    }
}