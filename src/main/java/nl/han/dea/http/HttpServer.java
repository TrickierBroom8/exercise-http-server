package nl.han.dea.http;

import java.io.IOException;
import java.net.ServerSocket;

public class HttpServer {

    private int tcpPort;

    public HttpServer(int tcpPort) {
        this.tcpPort = tcpPort;
    }

    public static void main(String[] args) {
        new HttpServer(8383).startServer();
    }

    public void startServer() {
        try (var serverSocket = new ServerSocket(this.tcpPort)) {
            System.out.println("Server accepting requests on port " + tcpPort);

            while (true) {
                var acceptedSocket = serverSocket.accept();
                var connectionHandler = new ConnectionHandler(acceptedSocket);

                // Maak een nieuwe Thread voor elk HTTP-verzoek en start het
                Thread requestHandlerThread = new Thread(connectionHandler::handle);
                requestHandlerThread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
