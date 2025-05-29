package server;

import common.Request;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.channels.SocketChannel;

public class RequestHandler {
    public Request readRequest(SocketChannel client) {
        try {
            System.out.println("Начало чтения запроса от " + client.getRemoteAddress());
            ObjectInputStream ois = new ObjectInputStream(client.socket().getInputStream());
            Request request = (Request) ois.readObject();
            System.out.println("Запрос успешно прочитан: " + (request != null ? request.getCommandName() : "null"));
            return request;
        } catch (IOException e) {
            System.out.println("Ошибка чтения запроса (IOException): " + (e.getMessage() != null ? e.getMessage() : "No message"));
            return null;
        } catch (ClassNotFoundException e) {
            System.out.println("Ошибка десериализации запроса (ClassNotFoundException): " + e.getMessage());
            return null;
        }
    }
}