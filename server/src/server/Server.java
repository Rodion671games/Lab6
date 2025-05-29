package server;

import server.managers.*;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Server {
    private final ConnectionManager connectionManager;
    private final RequestHandler requestHandler;
    private final CommandProcessor commandProcessor;
    private final ResponseSender responseSender;
    private final CollectionManager collectionManager;

    public Server(String fileName) {
        DumpManager dumpManager = new DumpManager(fileName);
        this.collectionManager = new CollectionManager(dumpManager);
        CommandManager commandManager = new CommandManager();
        registerCommands(commandManager, collectionManager);
        this.connectionManager = new ConnectionManager();
        this.requestHandler = new RequestHandler();
        this.commandProcessor = new CommandProcessor(commandManager);
        this.responseSender = new ResponseSender();
        System.out.println("Сервер запущен");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Завершение работы сервера, сохранение коллекции...");
            collectionManager.saveCollection();
        }));
    }

    private void registerCommands(CommandManager commandManager, CollectionManager collectionManager) {
        commandManager.register("help", new server.commands.Help(collectionManager));
        commandManager.register("info", new server.commands.Info(collectionManager));
        commandManager.register("show", new server.commands.Show(collectionManager));
        commandManager.register("add", new server.commands.Add(collectionManager));
        commandManager.register("update", new server.commands.Update(collectionManager));
        commandManager.register("remove_by_id", new server.commands.RemoveById(collectionManager));
        commandManager.register("clear", new server.commands.Clear(collectionManager));
        commandManager.register("save", new server.commands.Save(collectionManager));
        commandManager.register("execute_script", new server.commands.ExecuteScript());
        commandManager.register("insert_at", new server.commands.InsertAt(collectionManager));
        commandManager.register("add_if_max", new server.commands.AddIfMax(collectionManager));
        commandManager.register("sort", new server.commands.Sort(collectionManager));
        commandManager.register("count_less_than_mood", new server.commands.CountLessThanMood(collectionManager));
        commandManager.register("print_descending", new server.commands.PrintDescending(collectionManager));
        commandManager.register("print_field_ascending_car", new server.commands.PrintFieldAscendingCar(collectionManager));
    }

    public void run() {
        try (ServerSocketChannel serverSocket = connectionManager.start()) {
            while (true) {
                SocketChannel client = connectionManager.accept(serverSocket);
                if (client != null) {
                    System.out.println("Новое подключение: " + client.getRemoteAddress());
                    common.Request request = requestHandler.readRequest(client);
                    if (request != null) {
                        System.out.println("Получен запрос: " + request.getCommandName());
                        common.Response response = commandProcessor.process(request);
                        responseSender.sendResponse(client, response);
                        System.out.println("Ответ отправлен");
                    } else {
                        System.out.println("Запрос не получен, ожидание следующего подключения...");
                    }
                    try {
                        Thread.sleep(200); // Задержка для завершения обмена данными
                        client.close();
                    } catch (InterruptedException e) {
                        System.out.println("Ожидание прервано: " + e.getMessage());
                    } catch (IOException e) {
                        System.out.println("Ошибка при закрытии клиента: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка сервера: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Укажите имя файла как аргумент");
            System.exit(1);
        }
        Server server = new Server(args[0]);
        server.run();
    }
}