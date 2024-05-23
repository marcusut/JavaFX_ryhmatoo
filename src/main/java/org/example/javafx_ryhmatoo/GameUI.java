package org.example.javafx_ryhmatoo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameUI extends Application {

    private GameUIController gameUIController = new GameUIController();
    private Mäng mäng =  gameUIController.getMäng();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Wördel");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/mang.fxml"));

            Scene scene = new Scene(root, 800, 600);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }


        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            gameUIController.writeToLog("Mäng lõplikult suletud.\n \n ");
        }));


        primaryStage.setOnCloseRequest(event -> {
            gameUIController.writeToLog("Mäng kasutaja poolt suletud.\n/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\");
            Platform.exit();
        });
    }
}
