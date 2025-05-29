package client;

import client.utility.ConsoleManager;
import client.utility.RequestSender;
import client.utility.StandardConsole;
import common.Response;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 12345;

    public static void main(String[] args) {
        ConsoleManager consoleManager = new ConsoleManager(new StandardConsole());
        RequestSender requestSender = new RequestSender(HOST, PORT);

        while (true) {
            consoleManager.prompt();
            common.Request request = consoleManager.readCommand(null);
            if (request == null) continue;
            if (request.getCommandName().equals("exit")) {
                System.out.println("Завершение работы клиента...");
                break;
            }
            Response response = requestSender.sendRequest(request);
            new client.utility.ResponseHandler(consoleManager.getConsole()).handle(response);
        }
    }
}