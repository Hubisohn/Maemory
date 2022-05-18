package com.example.maemory;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainAppController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() throws Exception {
        ImageConverter.convertWithDialog(100,100);
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}