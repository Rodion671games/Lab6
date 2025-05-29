package server;

import common.Request;
import common.Response;
import server.managers.CommandManager;
import server.commands.*;

public class CommandProcessor {
    private final CommandManager commandManager;

    public CommandProcessor(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public Response process(Request request) {
        Command command = commandManager.getCommands().get(request.getCommandName());
        if (command == null) {
            return new Response(false, "Команда '" + request.getCommandName() + "' не найдена");
        }
        return command.apply(request);
    }
}
