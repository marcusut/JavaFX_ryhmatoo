package org.example.javafx_ryhmatoo;

import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.text.Font;

public class LetterCell extends ListCell<String> {
    private Button täheNupp;

    public LetterCell() {
        super();
        täheNupp = new Button();
        täheNupp.setFont(Font.font(20));
        setGraphic(täheNupp);

        // Set up the drag event for this button
        täheNupp.setOnDragDetected(event -> {
            Dragboard db = täheNupp.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(täheNupp.getText());
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
            täheNupp.setText(item);
            setText(null);
        }
    }
}