package com.example.maemory;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;

import java.util.*;

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
            
            synchronized (this) {
                wait(1000);
            }
            
        }
        
        
    }
}