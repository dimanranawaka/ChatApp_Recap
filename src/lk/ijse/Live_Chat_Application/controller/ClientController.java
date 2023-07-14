package lk.ijse.Live_Chat_Application.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

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


    // Initializes your chat application
    public void initialize(){
        this.txtClientName.setText(LoginController.clientName);
        connectSocket();
        emojiPane.setVisible(false);
    }

    // Connects to the server
    private void connectSocket() {
        try {

            socket = new Socket("localhost",3000); // Create a new Socket to connect to the server

            System.out.println("Connected to Server...!");

            // Create a new BufferedReader to read messages from the server
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

            // Create a new PrintWriter to send messages to the server
            printWriter = new PrintWriter(socket.getOutputStream(),true);

            // Start this thread
            this.start();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // Continuously reads messages from the server and updates the chat box accordingly
    public void run(){

        try {

            while (true){

                String msg = bufferedReader.readLine();

                System.out.println("Message : "+msg);

                String[] array = msg.split(" :");

                String name = array[0];

                System.out.println("Client name: "+name);

                StringBuilder message = new StringBuilder();

                for (int i = 1; i < array.length; i++) {

                        message.append(array[i]);

                }

                System.out.println("New Msg :"+message);
                System.out.println();
                if(name.equalsIgnoreCase(LoginController.clientName)){
                    continue;
                }else if (message.toString().equalsIgnoreCase("bye")){
                    break;
                }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        HBox hBox = new HBox();

                        if(message.toString().endsWith(".png") || message.toString().endsWith(".jpg") || message.toString()
                                .endsWith(".jpeg") || message.toString().endsWith(".gif")){
                            System.out.println(message);
                            hBox.setAlignment(Pos.CENTER_LEFT);
                            hBox.setPadding(new Insets(5,10,5,5));
                            Text text = new Text(name + " : ");
                            text.setStyle("-fx-font-size: 15px");
                            ImageView imageView = new ImageView();
                            Image image = new Image(String.valueOf(message));
                            imageView.setImage(image);
                            imageView.setFitHeight(100);
                            imageView.setFitWidth(100);
                            TextFlow textFlow = new TextFlow(text,imageView);
                            textFlow.setStyle("-fx-color:rgb(230,235,250);"+ "-fx-background-color: rgb(180,180,180);"+
                                    "-fx-background-radius:10px");
                            textFlow.setPadding(new Insets(5,5,5,5));
                            hBox.getChildren().add(textFlow);
                            messageArea.getChildren().add(hBox);

                        }else{

                            hBox.setAlignment(Pos.CENTER_LEFT);
                            hBox.setPadding(new Insets(5,10,5,5));
                            Text text = new Text(msg);
                            text.setStyle("-fx-font-size: 15px");
                            TextFlow textFlow = new TextFlow(text);
                            textFlow.setStyle("-fx-color:rgb(239,242,255);"+"-fx-background-color: rgb(134,188,227);"+
                                    "-fx-background-radius: 20px");
                            textFlow.setPadding(new Insets(5,10,5,10));
                            text.setFill(Color.color(0,0,0));
                            hBox.getChildren().add(textFlow);
                            messageArea.getChildren().add(hBox);

                        }
                    }
                });
            }

            bufferedReader.close();
            printWriter.close();
            socket.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // Sends the message in txtMessage to the server when triggered by an ActionEvent
    public void sendOnAction(ActionEvent actionEvent) {
        send();
        emojiPane.setVisible(false);
    }

    // Sends the text in txtMessage to the server and updates the chat box accordingly
    private void send(){
        String msg = txtMessage.getText();

        printWriter.println(LoginController.clientName+" : "+msg);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5,5,5,10));
        Text text = new Text("Me : "+msg);
        text.setStyle("-fx-font-size: 15px");

        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-color:rgb(239,242,255);"+"-fx-background-color: rgb(62,155,224);"+
                "-fx-background-radius: 20px");
        textFlow.setPadding(new Insets(5,10,5,10));
        text.setFill(Color.color(0,0,0));
        hBox.getChildren().add(textFlow);
        messageArea.getChildren().add(hBox);
        printWriter.flush();
        txtMessage.setText(null);
        if(msg.equalsIgnoreCase("bye")){
            System.exit(0);
        }
    }

    // Toggles the visibility of the emojiPane when triggered by a MouseEvent
    public void emojiOnAction(MouseEvent mouseEvent) {

        // Toggle the visibility of the emoji pane
        emojiPane.setVisible(!emojiPane.isVisible());


    }

    // This method opens a file chooser and sends an image file selected by the user to the server

    public void openFileChoser(MouseEvent mouseEvent) throws MalformedURLException {

        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Select a Image");
        File file = fileChooser.showOpenDialog(stage);

        if(file!=null){

            printWriter.println("File Selected...");
            url = file.toURI().toURL();
            System.out.println(url);
            ImageView imageView = new ImageView();
            Image image = new Image(String.valueOf(url));
            imageView.setImage(image);
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            HBox hBox = new HBox(imageView);
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setPadding(new Insets(5,10,5,5));
            messageArea.getChildren().add(hBox);
        }
    }
    // This method updates the txtMessage with the selected emoji when an emoji is clicked
    public void sendEmojiOnAction(MouseEvent mouseEvent) {
        if(mouseEvent.getSource() instanceof ImageView){
            ImageView icon = (ImageView) mouseEvent.getSource();
            switch (icon.getId()){

                case "emoji1":
                    byte[] byte1 = new byte[]{(byte) 0xE2,(byte) 0x9D,(byte) 0xA4};
                    String emoji1AsString = new String(byte1,StandardCharsets.UTF_8);
                    txtMessage.appendText(emoji1AsString);
                    break;

                case "emoji2":
                    byte [] byte2 = new byte[]{(byte) 0xF0,(byte) 0x9F ,(byte) 0x98,(byte) 0x8D};
                    String emoji2AsString = new String(byte2,StandardCharsets.UTF_8);
                    txtMessage.appendText(emoji2AsString);
                    break;

                case "emoji3":
                    byte [] byte3 = new byte[]{(byte) 0xF0,(byte) 0x9F ,(byte) 0x98,(byte) 0xA1};
                    String emoji3AsString = new String(byte3,StandardCharsets.UTF_8);
                    txtMessage.appendText(emoji3AsString);
                    break;

                case "emoji4":
                    byte [] byte4 = new byte[]{(byte) 0xF0,(byte) 0x9F ,(byte) 0x98,(byte) 0xBB};
                    String emoji4AsString = new String(byte4,StandardCharsets.UTF_8);
                    txtMessage.appendText(emoji4AsString);
                    break;

                case "emoji5":
                    byte [] byte5 = new byte[]{(byte) 0xF0,(byte) 0x9F ,(byte) 0x98,(byte) 0x82};
                    String emoji5AsString = new String(byte5,StandardCharsets.UTF_8);
                    txtMessage.appendText(emoji5AsString);
                    break;

                case "emoji6":
                    byte [] byte6 = new byte[]{(byte) 0xF0,(byte) 0x9F ,(byte) 0x98,(byte) 0x98};
                    String emoji6AsString = new String(byte6,StandardCharsets.UTF_8);
                    txtMessage.appendText(emoji6AsString);
                    break;

                case "emoji7":
                    byte [] byte7 = new byte[]{(byte) 0xF0,(byte) 0x9F ,(byte) 0x98,(byte) 0xB7};
                    String emoji7AsString = new String(byte7,StandardCharsets.UTF_8);
                    txtMessage.appendText(emoji7AsString);
                    break;

                case "emoji8":
                    byte [] byte8 = new byte[]{(byte) 0xF0,(byte) 0x9F ,(byte) 0x98,(byte) 0x93};
                    String emoji8AsString = new String(byte8,StandardCharsets.UTF_8);
                    txtMessage.appendText(emoji8AsString);
                    break;

                case "emoji9":
                    byte [] byte9 = new byte[]{(byte) 0xF0,(byte) 0x9F ,(byte) 0x98,(byte) 0xB3};
                    String emoji9AsString = new String(byte9,StandardCharsets.UTF_8);
                    txtMessage.appendText(emoji9AsString);
                    break;

                case "emoji10":
                    byte [] byte10 = new byte[]{(byte) 0xF0,(byte) 0x9F ,(byte) 0x98,(byte) 0xB1};
                    String emoji10AsString = new String(byte10,StandardCharsets.UTF_8);
                    txtMessage.appendText(emoji10AsString);
                    break;
            }
        }
    }
}
