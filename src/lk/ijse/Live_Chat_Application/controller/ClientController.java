package lk.ijse.Live_Chat_Application.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.net.URL;

public class ClientController extends Thread {
    public TextField txtMessage;
    public ScrollPane txtArea;
    public VBox messageArea;
    public Label txtClientName;
    public AnchorPane emojiPane;
    public ImageView emoji1;
    public ImageView emoji6;
    public ImageView emoji2;
    public ImageView emoji7;
    public ImageView emoji3;
    public ImageView emoji8;
    public ImageView emoji4;
    public ImageView emoji9;
    public ImageView emoji5;
    public ImageView emoji10;

    private Socket socket;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private FileChooser fileChooser;
    private URL url;

    public void initialize(){
        this.txtClientName.setText(LoginController.clientName);
        connectSocket();
        emojiPane.setVisible(false);
    }

    private void connectSocket() {
    }

    public void sendOnAction(ActionEvent actionEvent) {
    }

    public void emojiOnAction(MouseEvent mouseEvent) {
    }

    public void openFileChoser(MouseEvent mouseEvent) {
    }

    public void sendEmojiOnAction(MouseEvent mouseEvent) {
    }
}
