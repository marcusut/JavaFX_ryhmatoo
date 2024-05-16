package org.example.javafx_ryhmatoo;

import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class LetterCell extends ListCell<String> {
    private Button letterButton;

    public LetterCell() {
        super();
        letterButton = new Button();
        letterButton.setFont(Font.font(20));
        setGraphic(letterButton);

        // Set up the drag event for this button
        letterButton.setOnDragDetected(event -> {
            Dragboard db = letterButton.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(letterButton.getText());
            db.setContent(content);
            event.consume();
        });
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            letterButton.setText(item);
            setText(null);
        }
    }
}
