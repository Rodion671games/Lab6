package server;

import common.Response;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.channels.SocketChannel;

public class ResponseSender {
    public void sendResponse(SocketChannel client, Response response) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(client.socket().getOutputStream());
            oos.writeObject(response);
            oos.flush();
        } catch (IOException e) {
            System.out.println("Ошибка отправки ответа: " + e.getMessage());
        }
    }
}