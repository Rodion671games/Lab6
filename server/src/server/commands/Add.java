package server.commands;

import common.models.HumanBeing;
import server.managers.CollectionManager;
import common.Response;
import common.Request;

public class Add extends Command {
    private final CollectionManager collectionManager;

    public Add(CollectionManager collectionManager) {
        super("add", "добавить новый элемент в коллекцию");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response apply(Request request) {
        HumanBeing h = request.getElement();
        if (h != null && h.validate()) {
            h = new HumanBeing(collectionManager.getFreeId(), h.getName(), h.getCoordinates(), h.getRealHero(),
                    h.getHasToothpick(), h.getImpactSpeed(), h.getMinutesOfWaiting(), h.getWeaponType(),
                    h.getMood(), h.getCar());
            if (collectionManager.add(h)) {
                return new Response(true, "HumanBeing успешно добавлен!");
            }
        }
        return new Response(false, "Поля HumanBeing не валидны!");
    }
}