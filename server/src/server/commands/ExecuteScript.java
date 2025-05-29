package server.commands;

import common.Response;
import common.Request;

public class ExecuteScript extends Command {
    public ExecuteScript() {
        super("execute_script", "выполнить скрипт (не поддерживается на сервере)");
    }

    @Override
    public Response apply(Request request) {
        return new Response(false, "Команда execute_script не поддерживается на сервере!");
    }
}