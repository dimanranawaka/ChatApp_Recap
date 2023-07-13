import java.io.BufferedReader; // Import the class for reading text from an input stream
import java.io.IOException; // Import the class for handling Input/Output exceptions
import java.io.InputStreamReader; // Import the class for converting an input stream into a reader
import java.io.PrintWriter; // Import the class for writing characters to an output stream
import java.net.Socket; // Import the class for creating a socket
import java.util.ArrayList; // Import the class for using array lists

// Define the ClientHandler class which extends Thread, allowing it to run in its own thread
public class ClientHandler extends Thread{
    private ArrayList<ClientHandler> clients; // A private list that will store each connected client's handler object
    private Socket socket; // A private socket for handling the client's connection
    public BufferedReader bufferedReader; // A public reader for reading text from the client's input stream
    public PrintWriter printWriter; // A public writer for writing text to the client's output stream

    // Constructor for the ClientHandler class
    public ClientHandler(Socket socket, ArrayList<ClientHandler> clientHandlers) throws IOException {
        this.socket=socket; // Assign the provided socket to the class's socket variable

        this.clients=clientHandlers; // Assign the provided list of client handlers to the class's list

        // Initialize the reader and assign it to the class's reader variable
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Initialize the writer, set it to auto-flush after print calls, and assign it to the class's writer variable
        this.printWriter= new PrintWriter(socket.getOutputStream(),true);
    }

    // This method will run when the thread starts
    public void run() {
        try {
            String msg; // Declare a string to store the client's messages

            // Read lines from the client's input stream until a null value is encountered
            while((msg = bufferedReader.readLine()) != null){
                // If the client's message is "exit", break the loop
                if(msg.equalsIgnoreCase("exit")){
                    break;
                }
                // For each client handler in the list of client handlers
                for (ClientHandler cl: clients) {
                    // Write the client's message to the output stream of the client handler
                    cl.printWriter.println(msg);
                }
            }

        }catch (Exception e){ // Catch any exceptions that might occur
            e.printStackTrace(); // Print the exception's stack trace
        }
        finally { // This block will execute whether an exception occurs or not
            try {
                bufferedReader.close(); // Close the reader
                printWriter.close(); // Close the writer
                socket.close(); // Close the socket
            }catch (IOException e){ // Catch any IO exceptions that might occur
                e.printStackTrace(); // Print the exception's stack trace
            }
        }
    }
}
