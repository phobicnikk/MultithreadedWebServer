import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {

    public Runnable getRunnable() throws UnknownHostException, IOException {
        return () -> {
            int port = 8010;
            try {
                InetAddress address = InetAddress.getByName("localhost");
                Socket socket = new Socket(address, port);
                try (
                    PrintWriter toSocket = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader fromSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()))
                ) {
                    // Send request to server
                    toSocket.println("Hello from Client " + socket.getLocalSocketAddress());

                    // Read server response
                    String line = fromSocket.readLine();
                    System.out.println("Response from Server: " + line);

                } catch (IOException e) {
                    System.out.println("Error in communication: " + e.getMessage());
                } finally {
                    socket.close(); // Ensure socket is closed
                }
            } catch (IOException e) {
                System.out.println("Error connecting to server: " + e.getMessage());
            }
        };
    }

    public static void main(String[] args) {
        Client client = new Client();
        int numThreads = 100;

        // Use an ExecutorService to manage the threads
        ExecutorService executorService = Executors.newFixedThreadPool(10); // 10 concurrent threads

        for (int i = 0; i < numThreads; i++) {
            try {
                // Submit each client request as a separate task
                executorService.submit(client.getRunnable());

                // Delay between client thread creation to avoid overwhelming the server
                Thread.sleep(100); // 100 milliseconds delay

            } catch (Exception ex) {
                System.out.println("Error in thread creation: " + ex.getMessage());
            }
        }

        // Shutdown the executor service once all tasks are complete
        executorService.shutdown();
    }
}
