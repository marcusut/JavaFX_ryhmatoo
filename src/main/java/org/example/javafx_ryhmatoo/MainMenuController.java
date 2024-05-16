package org.example.javafx_ryhmatoo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuController {

    @FXML
    private Button playButton;

    @FXML
    private Button closeButton;

    @FXML
    private void startGameUI() {
        GameUI gameUI = new GameUI();
        Stage stage = new Stage();
        gameUI.start(stage);
    }

    @FXML
    public void closeGame() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        if (stage != null) {
            stage.close();
        } else {
            System.out.println("Error: Stage is null");
        }
    }

}
