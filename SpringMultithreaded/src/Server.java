import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {
    public Consumer<Socket> getConsumer() {
        return (clientSocket) -> {
            try (PrintWriter toSocket = new PrintWriter(clientSocket.getOutputStream(), true)) {
                // Log when a new client connects
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                // Send response to the client
                toSocket.println("Hello from server " + clientSocket.getInetAddress());
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        };
    }

    public static void main(String[] args) {
        int port = 8010;
        Server server = new Server();

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server is listening on port " + port);

            // Accept clients and handle each one in a new thread
            while (true) {
                Socket clientSocket = serverSocket.accept();

                // Log when a new client connection is accepted
                System.out.println("Accepted new connection from: " + clientSocket.getInetAddress());

                // Create and start a new thread for each client
                Thread thread = new Thread(() -> server.getConsumer().accept(clientSocket));
                thread.start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
