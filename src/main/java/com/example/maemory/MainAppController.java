package com.example.maemory;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
        
        list.forEach((e) -> {
    
            front.setImage(e.getFront());
            back.setImage(e.getBack());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
    
        });
        
        
    }
}