package com.example.maemory;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;

import java.net.*;
import java.util.*;

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
    
    
    private String pfad;
    private int size;
    private double grad;
    
    public int vier(ActionEvent actionEvent) {
        if (Objects.equals(pfad = CardSetFunctions.showCardSetSelectionDialog(4), "abort")) {
            
            return 0;
            
        }
        size = 4;
        return 4;
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
    
    public int sechs(ActionEvent actionEvent) {
        if (Objects.equals(pfad = CardSetFunctions.showCardSetSelectionDialog(6), "abort")) {
            
            return 0;
            
        }
        size = 6;
        return 6;
    }
    
    public int acht(ActionEvent actionEvent) {
        
        if (Objects.equals(pfad = CardSetFunctions.showCardSetSelectionDialog(8), "abort")) {
            
            return 0;
            
        }
        size = 8;
        return 8;
    }
    
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
