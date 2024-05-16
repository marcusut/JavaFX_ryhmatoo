package org.example.javafx_ryhmatoo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file and set the controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/game_ui.fxml"));
            loader.setController(new GameUIController());
            Parent root = loader.load();

            // Create the scene and show the stage
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}