import java.net.ServerSocket; // Import the class for creating a server socket
import java.net.Socket; // Import the class for creating a socket
import java.util.ArrayList; // Import the class for using array lists

public class Server { // Define the Server class

    private static ArrayList<ClientHandler> clients = new ArrayList<>(); // Define a private static list that will store each connected client's handler object

    // The main method to run the server
    public static void main(String[] args) {
        ServerSocket serverSocket; // Declare a server socket
        Socket socket; // Declare a socket for accepting client connections

        try {
            serverSocket= new ServerSocket(3000); // Initialize the server socket and set it to listen on port 3000

            while (true){ // Keep the server running indefinitely
                System.out.println("Waiting For Clients..."); // Output a message indicating that the server is waiting for clients
                socket = serverSocket.accept(); // Accept a client connection and store it in the socket
                System.out.println("New Client Connected..."); // Output a message indicating that a new client has connected
                ClientHandler handler = new ClientHandler(socket,clients); // Create a new client handler for the connected client
                clients.add(handler); // Add the new client handler to the clients list
                handler.start(); // Start the client handler (This assumes that ClientHandler extends Thread or implements Runnable)
            }
        }catch (Exception e){ // Catch any exceptions that might occur
            e.printStackTrace(); // Print the exception's stack trace
        }
    }
}
