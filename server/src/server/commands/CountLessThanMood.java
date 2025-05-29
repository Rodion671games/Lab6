package server.commands;

import common.models.Mood;
import server.managers.CollectionManager;
import common.Response;
import common.Request;

public class CountLessThanMood extends Command {
    private final CollectionManager collectionManager;

    public CountLessThanMood(CollectionManager collectionManager) {
        super("count_less_than_mood", "вывести количество элементов с mood меньше заданного");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response apply(Request request) {
        try {
            if (request.getArguments().length == 0 || request.getArguments()[0].isEmpty()) {
                return new Response(false, "Не указан аргумент mood!");
            }
            Mood mood = Mood.valueOf(request.getArguments()[0]);
            int count = collectionManager.countLessThanMood(mood);
            return new Response(true, "Количество элементов с mood меньше " + mood + ": " + count);
        } catch (IllegalArgumentException e) {
            return new Response(false, "Некорректное значение mood!");
        }
    }
}