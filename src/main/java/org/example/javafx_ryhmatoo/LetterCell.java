package org.example.javafx_ryhmatoo;

import javafx.scene.control.ListCell;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LetterCell extends ListCell<String> {
    private Text letterText;

    public LetterCell() {
        super();
        letterText = new Text();
        letterText.setFont(Font.font(20));
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(letterText);
        stackPane.setOnDragDetected(event -> {
            Dragboard db = stackPane.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(letterText.getText());
            db.setContent(content);
            event.consume();
        });
        setGraphic(stackPane);
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            letterText.setText(item);
            setText(null);
        }
    }
}