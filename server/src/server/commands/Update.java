package server.commands;

import common.models.HumanBeing;
import server.managers.CollectionManager;
import common.Response;
import common.Request;

public class Update extends Command {
    private final CollectionManager collectionManager;

    public Update(CollectionManager collectionManager) {
        super("update", "обновить значение элемента коллекции по ID");
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
            HumanBeing h = request.getElement();
            if (h != null && h.validate()) {
                collectionManager.remove(id);
                h = new HumanBeing(id, h.getName(), h.getCoordinates(), h.getRealHero(),
                        h.getHasToothpick(), h.getImpactSpeed(), h.getMinutesOfWaiting(), h.getWeaponType(),
                        h.getMood(), h.getCar());
                collectionManager.add(h);
                return new Response(true, "HumanBeing успешно обновлен!");
            }
            return new Response(false, "Поля HumanBeing не валидны!");
        } catch (NumberFormatException e) {
            return new Response(false, "ID не распознан!");
        }
    }
}