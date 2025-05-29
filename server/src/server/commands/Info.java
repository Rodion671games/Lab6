package server.commands;

import server.managers.CollectionManager;
import common.Response;
import common.Request;

import java.time.ZonedDateTime;

public class Info extends Command {
    private final CollectionManager collectionManager;

    public Info(CollectionManager collectionManager) {
        super("info", "вывести информацию о коллекции");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response apply(Request request) {
        if (request.getArguments().length > 0 && !request.getArguments()[0].isEmpty()) {
            return new Response(false, "Неправильное количество аргументов!");
        }
        ZonedDateTime lastInitTime = collectionManager.getLastInitTime();
        String lastInitTimeString = (lastInitTime == null) ? "в данной сессии инициализации еще не происходило" :
                lastInitTime.toString();
        ZonedDateTime lastSaveTime = collectionManager.getLastSaveTime();
        String lastSaveTimeString = (lastSaveTime == null) ? "в данной сессии сохранения еще не происходило" :
                lastSaveTime.toString();
        String s = "Сведения о коллекции:\n" +
                " Тип: " + collectionManager.getCollection().getClass().toString() + "\n" +
                " Количество элементов: " + collectionManager.getCollection().size() + "\n" +
                " Дата последнего сохранения: " + lastSaveTimeString + "\n" +
                " Дата последней инициализации: " + lastInitTimeString;
        return new Response(true, s);
    }
}