package org.example.javafx_ryhmatoo;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

public class GameUIController {


    private Mäng mäng;

    @FXML
    private FlowPane lettersPane;

    @FXML
    private HBox wordBox;

    @FXML
    private Button submitButton;

    // Initialize the controller
    @FXML
    public void initialize() {
        lettersPane.setPadding(new Insets(15, 15, 15, 15));
        mäng = new Mäng(); // Create a new game

        // Generate the letters for the player
        String letters = "";
        do {
            letters = "";
            for (int i = 0; i < 7; i++) {
                letters += mäng.genereeriÜksTäht();
            }
        } while (!mäng.getKontroll().saabTehaSõna(letters));

        for (char letter : letters.toCharArray()) {
            Button letterButton = new Button(String.valueOf(letter));
            letterButton.setFont(Font.font(20));
            lettersPane.getChildren().add(letterButton);

            // Set up the drag event for this button
            letterButton.setOnDragDetected(event -> {
                Dragboard db = letterButton.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(letterButton.getText());
                db.setContent(content);
                event.consume();
            });
        }

        // Add "+" button
        Button plusButton = new Button("+");
        plusButton.setFont(Font.font(20));
        lettersPane.getChildren().add(plusButton);

        // Set up the action event for the "+" button
        plusButton.setOnAction(event -> {
            String newLetter = mäng.genereeriÜksTäht();
            Button letterButton = new Button(newLetter);
            letterButton.setFont(Font.font(20));
            lettersPane.getChildren().add(letterButton);
        });
        // Handle the drag over event on the wordBox
        wordBox.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != wordBox && event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });

        // Handle the drag dropped event on the wordBox
        wordBox.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                Label letterLabel = new Label(db.getString());
                wordBox.getChildren().add(letterLabel);
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }

    @FXML
    private void submitWord () {
        String word = "";
        for (Node node : wordBox.getChildren()) {
            if (node instanceof Label) {
                word += ((Label) node).getText();
            }
        }

        // Check if the word is valid
        if (mäng.arvaSõna(word)) { // Use the game's method to check the word
            // Update the game state
            System.out.println("Valid word: " + word);
        } else {
            System.out.println("Invalid word: " + word);
        }
    }

    public boolean isValidWord (String word){
        // Check if the word exists in the dictionary
        // You can use the Kontroll class from your original code to check if the word is valid
        return mäng.kontroll.saabTehaSõna(word); // Use the Kontroll instance to check the word
    }
}