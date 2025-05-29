package server.commands;

import common.models.HumanBeing;
import server.managers.CollectionManager;
import common.Response;
import common.Request;

public class AddIfMax extends Command {
    private final CollectionManager collectionManager;

    public AddIfMax(CollectionManager collectionManager) {
        super("add_if_max", "добавить новый элемент, если он превышает максимальный");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response apply(Request request) {
        HumanBeing h = request.getElement();
        if (h != null && h.validate()) {
            h = new HumanBeing(collectionManager.getFreeId(), h.getName(), h.getCoordinates(), h.getRealHero(),
                    h.getHasToothpick(), h.getImpactSpeed(), h.getMinutesOfWaiting(), h.getWeaponType(),
                    h.getMood(), h.getCar());
            if (collectionManager.addIfMax(h)) {
                return new Response(true, "HumanBeing успешно добавлен!");
            }
            return new Response(false, "HumanBeing не превышает максимальный элемент!");
        }
        return new Response(false, "Поля HumanBeing не валидны!");
    }
}