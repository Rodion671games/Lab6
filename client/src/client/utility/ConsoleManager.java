package client.utility;

import common.Request;
import common.Response;
import common.models.HumanBeing;
import client.models.Ask;
import java.io.File;
import java.util.List;
import java.util.Scanner;

public class ConsoleManager {
    private final Console console;
    private Scanner fileScanner;

    public ConsoleManager(Console console) {
        if (console == null) {
            throw new IllegalArgumentException("Console cannot be null");
        }
        this.console = console;
        this.fileScanner = null;
    }

    public Console getConsole() {
        if (console == null) {
            throw new IllegalStateException("Console is not initialized");
        }
        return console;
    }

    public void prompt() {
        getConsole().prompt();
    }

    public Request readCommand(List<String> scriptStack) {
        try {
            String[] input = (getConsole().readln().trim() + " ").split(" ", 2);
            String commandName = input[0].trim();
            String[] arguments = input[1].trim().isEmpty() ? new String[0] : input[1].trim().split(" ");
            HumanBeing element = null;

            System.out.println("Обработка команды: " + commandName + " с аргументами: " + String.join(",", arguments));
            if (commandName.equals("add") || commandName.equals("add_if_max") || commandName.equals("update") || commandName.equals("insert_at")) {
                if ((commandName.equals("update") || commandName.equals("insert_at")) && arguments.length == 0) {
                    getConsole().println("Требуется указать ID или индекс!");
                    return null;
                }
                getConsole().println("* Создание нового HumanBeing:");
                element = Ask.askHumanBeing(getConsole(), commandName.equals("update") ? Integer.parseInt(arguments[0]) : 0);
                if (element == null) {
                    getConsole().println("Отмена создания элемента");
                    return null;
                }
            } else if (commandName.equals("count_less_than_mood")) {
                if (arguments.length == 0) {
                    getConsole().println("Требуется указать mood!");
                    return null;
                }
            }

            Request request = new Request(commandName, arguments, element);
            System.out.println("Сформирован запрос: " + request.getCommandName() + ", аргументы: " + String.join(",", request.getArguments()) + ", элемент: " + (element != null ? "присутствует" : "отсутствует"));
            return request;
        } catch (Ask.AskBreak e) {
            getConsole().println("Отмена...");
            return null;
        } catch (Exception e) {
            getConsole().println("Ошибка ввода: " + e.getMessage());
            return null;
        }
    }

    public void executeScript(String fileName, List<String> scriptStack) {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            fileScanner = scanner;
            getConsole().selectFileScanner(fileScanner);
            while (fileScanner.hasNextLine()) {
                Request request = readCommand(scriptStack);
                if (request == null) continue;
                if (request.getCommandName().equals("execute_script")) {
                    String newFile = request.getArguments()[0];
                    if (!scriptStack.contains(newFile)) {
                        scriptStack.add(newFile);
                        executeScript(newFile, scriptStack);
                        scriptStack.remove(newFile);
                    } else {
                        getConsole().println("Рекурсия обнаружена, пропуск скрипта: " + newFile);
                    }
                } else if (request.getCommandName().equals("exit")) {
                    break;
                } else {
                    Response response = new RequestSender("localhost", 12345).sendRequest(request);
                    new ResponseHandler(getConsole()).handle(response);
                }
            }
        } catch (Exception e) {
            getConsole().println("Ошибка выполнения скрипта: " + e.getMessage());
        } finally {
            getConsole().selectConsoleScanner();
        }
    }
}