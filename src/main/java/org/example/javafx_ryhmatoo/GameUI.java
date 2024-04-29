package org.example.javafx_ryhmatoo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create buttons for each game type
        Button endlessModeButton = new Button("Endless Mode");
        endlessModeButton.setOnAction(event -> startEndlessMode());

        Button timeAttackButton = new Button("Time Attack");
        timeAttackButton.setOnAction(event -> startTimeAttack());

        // Add the buttons to a container
        VBox menu = new VBox(10, endlessModeButton, timeAttackButton);

        // Create the scene and show the stage
        Scene scene = new Scene(menu, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startEndlessMode() {
        // Start the Endless Mode game
        System.out.println("Starting Endless Mode...");
        // Replace with your actual game start code
    }

    private void startTimeAttack() {
        // Start the Time Attack game
        System.out.println("Starting Time Attack...");
        // Replace with your actual game start code
    }

    public static void main(String[] args) {
        launch(args);
    }
}
