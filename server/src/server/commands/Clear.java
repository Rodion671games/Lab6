package server.commands;

import server.managers.CollectionManager;
import common.Response;
import common.Request;

public class Clear extends Command {
    private final CollectionManager collectionManager;

    public Clear(CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response apply(Request request) {
        if (request.getArguments().length > 0 && !request.getArguments()[0].isEmpty()) {
            return new Response(false, "Неправильное количество аргументов!");
        }
        collectionManager.getCollection().clear();
        return new Response(true, "Коллекция успешно очищена!");
    }
}