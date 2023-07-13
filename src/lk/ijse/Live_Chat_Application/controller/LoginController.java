package lk.ijse.Live_Chat_Application.controller; // Define the package where this class is located

import javafx.event.ActionEvent; // Import the class for handling events
import javafx.fxml.FXMLLoader; // Import the class for loading FXML documents
import javafx.scene.Parent; // Import the class that is the base class for all nodes that have children in the scene graph
import javafx.scene.control.Button; // Import the class for creating a button
import javafx.scene.control.TextField; // Import the class for creating a text field
import javafx.scene.layout.AnchorPane; // Import the class for creating an anchor pane

import java.io.IOException; // Import the class for handling Input/Output exceptions
import java.net.URL; // Import the class for representing a Uniform Resource Locator

public class LoginController { // Define the LoginController class
    public AnchorPane loginPane; // Define a public anchor pane object
    public TextField txtUserName; // Define a public text field object for the username input
    public Button btnSend; // Define a public button object for sending the form
    public static String clientName; // Define a public static string to hold the client's name

    // This method loads the client page when an action event (e.g., a button click) occurs
    public void loadClientPageOnAction(ActionEvent actionEvent) throws IOException {
        clientName = txtUserName.getText(); // Get the text from the username input field and assign it to the clientName variable

        // Get the resource URL of the client FXML file
        URL resource = getClass().getResource("/lk/ijse/Live_Chat_Application/view/Client.fxml");

        // Load the client FXML file into a Parent object
        Parent load = FXMLLoader.load(resource);

        // Clear all children from the loginPane object
        loginPane.getChildren().clear();

        // Add the loaded FXML document (as a Parent object) to the loginPane object
        loginPane.getChildren().add(load);
    }
}
