package server.commands;

import server.managers.CollectionManager;
import common.Response;
import common.Request;

import java.util.stream.Collectors;

public class Help extends Command {
    private final CollectionManager collectionManager;

    public Help(CollectionManager collectionManager) {
        super("help", "вывести справку по доступным командам");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response apply(Request request) {
        if (request.getArguments().length > 0 && !request.getArguments()[0].isEmpty()) {
            return new Response(false, "Неправильное количество аргументов!");
        }
        String message = collectionManager.getCollection().isEmpty() ? "Коллекция пуста!" :
                collectionManager.getCollection().stream()
                        .map(h -> String.format(" %-35s%-1s", getName(), getDescription()))
                        .collect(Collectors.joining("\n"));
        return new Response(true, message);
    }
}