package org.example.javafx_ryhmatoo;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;


public class GameUIController {

    private static final int tähtedeArv = 7;


    private Mäng mäng;

    private List<String> arvatudSõnad = new ArrayList<>();
    private Lemmad lemmad;


    @FXML
    private FlowPane lettersPane;

    @FXML
    private FlowPane wordBox;

    @FXML
    private VBox mainVBox;

    @FXML
    private Label hoiatus;


    @FXML
    public void initialize() {
        mäng = new Mäng();
        lemmad = new Lemmad();
        mainVBox.setPadding(new Insets(15, 15, 15, 15));
        initLettersPane();
        initWordBox();

    }

    @FXML
    private void initLettersPane() {
        lettersPane.setPadding(new Insets(15, 15, 15, 15));
        mäng = new Mäng(); // Create a new game

        generateLetters();
    }


    @FXML
    private void addLetter() {
        String newLetter = mäng.genereeriÜksTäht();
        Button letterButton = createLetterButton(newLetter);
        lettersPane.getChildren().add(letterButton);
    }

    private void generateLetters() {
        String letters = "";

        for (int i = 0; i < tähtedeArv; i++) {
            String letter = mäng.genereeriÜksTäht();
            letters += letter;
        }
        while (!mäng.kontroll.saabTehaSõna(letters)) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 7; i++) {
                sb.append(mäng.genereeriÜksTäht());
            }
            letters = sb.toString();
        }
        char[] letterChars = letters.toCharArray();  // Convert String to character array

        for (char letterChar : letterChars) {
            String letterString = String.valueOf(letterChar); // Convert char to String
            Button letterButton = createLetterButton(letterString);
            lettersPane.getChildren().add(letterButton);
        }

    }


    public Mäng getMäng() {
        return this.mäng;
    }

    private void initWordBox() {
        wordBox.setPadding(new Insets(15, 15, 15, 15));
        wordBox.setHgap(3);
        wordBox.setMinHeight(50);
        wordBox.setOnDragOver(event -> {
            if (event.getGestureSource() != wordBox && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });
        wordBox.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                Button letterButton = createLetterButton(db.getString());
                wordBox.getChildren().add(letterButton);
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }

    private Button createLetterButton(String letter) {
        Button button = new Button(letter);
        button.setFont(Font.font(20));
        button.setOnDragDetected(event -> {
            Dragboard db = button.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(button.getText());
            db.setContent(content);
            event.consume();
        });

        button.setOnDragOver(event -> {
            if (event.getGestureSource() != button && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        button.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                if (wordBox.getChildren().contains(button)) {
                    int droppedIdx = wordBox.getChildren().indexOf(button);
                    Button droppedButton = createLetterButton(db.getString());
                    wordBox.getChildren().add(droppedIdx, droppedButton);
                    success = true;
                } else if (lettersPane.getChildren().contains(button)) {
                    int droppedIdx = lettersPane.getChildren().indexOf(button);
                    Button droppedButton = createLetterButton(db.getString());
                    lettersPane.getChildren().add(droppedIdx, droppedButton);
                    success = true;
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });

        button.setOnDragDone(event -> {
            if (event.getTransferMode() == TransferMode.MOVE) {
                ((Pane) button.getParent()).getChildren().remove(button);
            }
        });

        button.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Button letterButton = createLetterButton(button.getText());
                wordBox.getChildren().add(letterButton);
                ((Pane) button.getParent()).getChildren().remove(button);
            }
        });

        return button;
    }

    @FXML
    private void submitWord() {
        StringBuilder word = new StringBuilder();
        for (Node node : wordBox.getChildren()) {
            if (node instanceof Button) {
                word.append(((Button) node).getText());
            }
        }

        String wordString = word.toString();
        if (mäng.arvaSõna(wordString)) {
            System.out.println("Õige sõna: " + wordString);
            arvatudSõnad.add(lemmad.getSõnaOriginaalVorm(wordString));
            lemmad.eemaldaSõna(lemmad.getSõnaOriginaalVorm(wordString));
            showCorrect();
            writeToLog("Õieti arvatud sõna: " + lemmad.getSõnaOriginaalVorm(wordString));
            wordBox.getChildren().clear();

        } else {
            System.out.println("Vale sõna: " + wordString);
            writeToLog("Vale sõna: " + wordString);
            showWarning();
        }
        while (lettersPane.getChildren().size() + wordBox.getChildren().size() < tähtedeArv) {
            addLetter();
        }
    }

    private void showCorrect() {
        hoiatus.setFont(Font.font(20));
        hoiatus.setText("✅");
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event -> hoiatus.setText(""));
        pause.play();
    }

    private void showWarning() {
        hoiatus.setFont(Font.font(20));
        hoiatus.setText("⛔");
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event -> hoiatus.setText(""));
        pause.play();
    }

    public void writeToLog(String sõna) {
        FileWriter writer = null;
        try {
            writer = new FileWriter("logi.txt", true);
            String timestamp = Instant.now().toString();
            writer.write(timestamp + " - " + sõna + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
