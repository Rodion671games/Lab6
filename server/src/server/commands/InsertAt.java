package server.commands;

import server.managers.CollectionManager;
import common.Response;
import common.Request;
import common.models.*;

public class InsertAt extends Command {
    private final CollectionManager collectionManager;

    public InsertAt(CollectionManager collectionManager) {
        super("insert_at", "добавить новый элемент в заданную позицию");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response apply(Request request) {
        try {
            if (request.getArguments().length == 0 || request.getArguments()[0].isEmpty()) {
                return new Response(false, "Не указан индекс!");
            }
            int index = Integer.parseInt(request.getArguments()[0]);
            HumanBeing h = request.getElement();
            if (h != null && h.validate()) {
                h = new HumanBeing(collectionManager.getFreeId(), h.getName(), h.getCoordinates(), h.getRealHero(),
                        h.getHasToothpick(), h.getImpactSpeed(), h.getMinutesOfWaiting(), h.getWeaponType(),
                        h.getMood(), h.getCar());
                if (collectionManager.insertAt(index, h)) {
                    return new Response(true, "HumanBeing успешно вставлен на позицию " + index + "!");
                }
                return new Response(false, "Индекс за пределами коллекции!");
            }
            return new Response(false, "Поля HumanBeing не валидны!");
        } catch (NumberFormatException e) {
            return new Response(false, "Индекс не распознан!");
        }
    }
}