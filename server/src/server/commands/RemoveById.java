package server.commands;

import server.managers.CollectionManager;
import common.Response;
import common.Request;

public class RemoveById extends Command {
    private final CollectionManager collectionManager;

    public RemoveById(CollectionManager collectionManager) {
        super("remove_by_id", "удалить элемент из коллекции по ID");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response apply(Request request) {
        try {
            if (request.getArguments().length == 0 || request.getArguments()[0].isEmpty()) {
                return new Response(false, "Не указан ID!");
            }
            int id = Integer.parseInt(request.getArguments()[0]);
            if (collectionManager.byId(id) == null) {
                return new Response(false, "Не существующий ID!");
            }
            collectionManager.remove(id);
            return new Response(true, "HumanBeing успешно удален!");
        } catch (NumberFormatException e) {
            return new Response(false, "ID не распознан!");
        }
    }
}