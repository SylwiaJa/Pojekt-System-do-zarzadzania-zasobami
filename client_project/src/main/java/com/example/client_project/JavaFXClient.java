package com.example.client_project;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class JavaFXClient extends Application {
    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    private Label questionLabel;
    private TextField answerTextField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Client");

        questionLabel = new Label();
        answerTextField = new TextField();

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(questionLabel, answerTextField);

        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest(event -> {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Platform.exit();
        });

        connectToServer();

        primaryStage.show();
    }

    private void connectToServer() {
        try {
            socket = new Socket("127.0.0.1", 12345);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);

            String question = in.nextLine();
            Platform.runLater(() -> questionLabel.setText(question));

            answerTextField.setOnAction(event -> {
                String answer = answerTextField.getText();
                out.println(answer);
                answerTextField.clear();
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
