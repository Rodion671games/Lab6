package client.utility;

import common.Request;
import common.Response;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class RequestSender {
    private final String host;
    private final int port;
    private static final int MAX_RETRIES = 5;
    private static final int RETRY_DELAY = 2000;

    public RequestSender(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Response sendRequest(Request request) {
        int attempts = 0;
        while (attempts < MAX_RETRIES) {
            SocketChannel socket = null;
            try {
                socket = SocketChannel.open();
                socket.configureBlocking(true); // Блокирующий режим
                socket.connect(new InetSocketAddress(host, port));
                System.out.println("Подключение установлено, отправка запроса: " + request.getCommandName());
                ObjectOutputStream oos = new ObjectOutputStream(socket.socket().getOutputStream());
                oos.writeObject(request);
                oos.flush();
                System.out.println("Запрос отправлен, ожидание ответа");
                ObjectInputStream ois = new ObjectInputStream(socket.socket().getInputStream());
                Response response = (Response) ois.readObject();
                System.out.println("Ответ получен: " + (response != null ? response.getMessage() : "null"));
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Ошибка при закрытии соединения: " + e.getMessage());
                }
                return response;
            } catch (IOException e) {
                attempts++;
                System.out.println("Попытка " + attempts + " из " + MAX_RETRIES + " не удалась: " + e.getMessage());
                if (attempts == MAX_RETRIES) {
                    try {
                        if (socket != null) socket.close();
                    } catch (IOException e2) {
                        System.out.println("Ошибка при закрытии соединения: " + e2.getMessage());
                    }
                    return new Response(false, "Сервер недоступен после " + MAX_RETRIES + " попыток: " + e.getMessage());
                }
                try {
                    Thread.sleep(RETRY_DELAY);
                } catch (InterruptedException ie) {
                    try {
                        if (socket != null) socket.close();
                    } catch (IOException e2) {
                        System.out.println("Ошибка при закрытии соединения: " + e2.getMessage());
                    }
                    return new Response(false, "Ошибка при ожидании: " + ie.getMessage());
                }
            } catch (ClassNotFoundException e) {
                attempts++;
                System.out.println("Ошибка десериализации ответа: " + e.getMessage());
                if (attempts == MAX_RETRIES) {
                    try {
                        if (socket != null) socket.close();
                    } catch (IOException e2) {
                        System.out.println("Ошибка при закрытии соединения: " + e2.getMessage());
                    }
                    return new Response(false, "Ошибка десериализации после " + MAX_RETRIES + " попыток: " + e.getMessage());
                }
                try {
                    Thread.sleep(RETRY_DELAY);
                } catch (InterruptedException ie) {
                    try {
                        if (socket != null) socket.close();
                    } catch (IOException e2) {
                        System.out.println("Ошибка при закрытии соединения: " + e2.getMessage());
                    }
                    return new Response(false, "Ошибка при ожидании: " + ie.getMessage());
                }
            } finally {
                if (socket != null && socket.isOpen()) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        System.out.println("Ошибка при закрытии соединения в finally: " + e.getMessage());
                    }
                }
            }
        }
        return new Response(false, "Не удалось отправить запрос");
    }
}