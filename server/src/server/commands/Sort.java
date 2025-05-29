package server.commands;

import server.managers.CollectionManager;
import common.Response;
import common.Request;

public class Sort extends Command {
    private final CollectionManager collectionManager;

    public Sort(CollectionManager collectionManager) {
        super("sort", "отсортировать коллекцию в естественном порядке");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response apply(Request request) {
        if (request.getArguments().length > 0 && !request.getArguments()[0].isEmpty()) {
            return new Response(false, "Неправильное количество аргументов!");
        }
        collectionManager.sort();
        return new Response(true, "Коллекция отсортирована!");
    }
}