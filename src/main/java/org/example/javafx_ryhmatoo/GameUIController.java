package org.example.javafx_ryhmatoo;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

public class GameUIController {

    @FXML
    private ListView<String> lettersList;

    @FXML
    private HBox wordBox;

    @FXML
    private Button submitButton;

    // Initialize the controller
    @FXML
    public void initialize() {
        // Initialize the letters list with available letters
        String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        for (String letter : letters) {
            StackPane letterStack = new StackPane();
            Text letterText = new Text(letter);
            letterText.setFont(Font.font(20));
            letterStack.getChildren().add(letterText);
            letterStack.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Dragboard db = letterStack.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    content.putString(letter);
                    db.setContent(content);
                    event.consume();
                }
            });
            lettersList.setCellFactory(param -> new LetterCell());
        }

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
        wordBox.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    Text letterText = new Text(db.getString());
                    wordBox.getChildren().add(letterText);
                    success = true;
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });
    }


    @FXML
    private void submitWord() {
        String word = "";
        for (Node node : wordBox.getChildren()) {
            if (node instanceof Text) {
                word += ((Text) node).getText();
            }
        }

        // Check if the word is valid
        if (isValidWord(word)) {
            // Update the game state
            System.out.println("Valid word: " + word);
        } else {
            System.out.println("Invalid word: " + word);
        }
    }

    public boolean isValidWord(String word) {
        // Check if the word exists in the dictionary
        // You can use the Kontroll class from your original code to check if the word is valid
        return true; // Replace this with the actual validation logic
    }
}