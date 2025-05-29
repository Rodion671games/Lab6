package server.commands;

import server.managers.CollectionManager;
import common.Response;
import common.Request;

public class Save extends Command {
    private final CollectionManager collectionManager;

    public Save(CollectionManager collectionManager) {
        super("save", "сохранить коллекцию в файл");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response apply(Request request) {
        if (request.getArguments().length > 0 && !request.getArguments()[0].isEmpty()) {
            return new Response(false, "Неправильное количество аргументов!");
        }
        collectionManager.saveCollection();
        return new Response(true, "Коллекция сохранена!");
    }
}