package server.commands;

import server.managers.CollectionManager;
import common.Response;
import common.Request;

public class Show extends Command {
    private final CollectionManager collectionManager;

    public Show(CollectionManager collectionManager) {
        super("show", "вывести все элементы коллекции");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response apply(Request request) {
        if (request.getArguments().length > 0 && !request.getArguments()[0].isEmpty()) {
            return new Response(false, "Неправильное количество аргументов!");
        }
        return new Response(true, collectionManager.toString());
    }
}