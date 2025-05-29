package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ConnectionManager {
    private static final int PORT = 12345;

    public ServerSocketChannel start() throws IOException {
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress(PORT));
        serverSocket.configureBlocking(true);
        return serverSocket;
    }

    public SocketChannel accept(ServerSocketChannel serverSocket) throws IOException {
        return serverSocket.accept();
    }
}