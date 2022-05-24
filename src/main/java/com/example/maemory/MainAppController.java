package com.example.maemory;

import javafx.fxml.*;
import javafx.scene.image.*;

import java.util.*;

public class MainAppController {
    public ImageView front;
    public ImageView back;
    
    @FXML
    protected void onHelloButtonClick() throws Exception {
        ArrayList<Spielkarte> list = ImageConverter.convertWithDialog(100,100,3);
        int delay = 500;
        
        for (Spielkarte spielkarte: list) {
            
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    front.setImage(spielkarte.getFront());
                }
            },delay);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    back.setImage(spielkarte.getBack());
                }
            },delay);
         
            delay = delay + 500;
            
        }
        
        
    }
}