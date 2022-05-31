package com.example.maemory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller3 implements Initializable {

    @FXML
    private Button viererButton;

    @FXML
    private Button sechserButton;

    @FXML
    private Button achterButton;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Label textSize;

    @FXML
    MenuButton menuButton;


    Stage stageOld;


    public int vier(ActionEvent actionEvent) {
        return 4;
    }

    public int sechs(ActionEvent actionEvent) {
        return 6;
    }

    public int acht(ActionEvent actionEvent) {
        return 8;
    }

    public void setStageOld(Stage stage) {
        stageOld = stage;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textSize.setFont(new Font(32));
        viererButton.setDisable(true);
        sechserButton.setDisable(true);
        achterButton.setDisable(true);
    }


    double grad;

    public void leicht_Func(ActionEvent actionEvent) {
        menuButton.setText("Leicht");
        grad = 12.5;
        viererButton.setDisable(false);
        sechserButton.setDisable(false);
        achterButton.setDisable(false);
    }

    public void mittel_Func(ActionEvent actionEvent) {
        menuButton.setText("Mittel");
        grad = 25.0;
        viererButton.setDisable(false);
        sechserButton.setDisable(false);
        achterButton.setDisable(false);
    }

    public void schwer_Func(ActionEvent actionEvent) {
        menuButton.setText("Schwer");
        grad = 50.0;
        viererButton.setDisable(false);
        sechserButton.setDisable(false);
        achterButton.setDisable(false);
    }

    public void legendaer_Func(ActionEvent actionEvent) {
        menuButton.setText("Legendär");
        grad = 100.0;
        viererButton.setDisable(false);
        sechserButton.setDisable(false);
        achterButton.setDisable(false);
    }
}
