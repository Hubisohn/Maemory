package com.example.maemory;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import java.util.*;
import static java.lang.Thread.sleep;

public class MainAppController {
    public ImageView front;
    public ImageView back;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() throws Exception {
        ArrayList<Spielkarte> list = ImageConverter.convertWithDialog(100,100);
    
        for (Spielkarte spielkarte: list) {
            
            front.setImage(spielkarte.getFront());
            back.setImage(spielkarte.getBack());
            
            wait(1000);
            
        }
        
        
    }
}