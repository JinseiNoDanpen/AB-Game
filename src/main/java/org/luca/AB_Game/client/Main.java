package org.luca.AB_Game.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application {

    public static final int PORT = 49654;

    public Main() throws FileNotFoundException {
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Main.class.getClassLoader().getResource("client_panel.fxml"));
        primaryStage.setTitle("AB Game");

        primaryStage.setResizable(false);
        Scene scene = new Scene(root);
        primaryStage.getIcons().add(new Image("https://i.imgur.com/50XPa3m.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
