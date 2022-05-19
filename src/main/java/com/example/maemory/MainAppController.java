package com.example.maemory;

import javafx.fxml.*;
import javafx.scene.image.*;

import java.util.*;

public class MainAppController {
    public ImageView front;
    public ImageView back;
    
    @FXML
    protected void onHelloButtonClick() throws Exception {
        ArrayList<Spielkarte> list = ImageConverter.convertWithDialog(100,100);
    
        for (Spielkarte spielkarte: list) {
            
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    front.setImage(spielkarte.getFront());
                }
            },1000);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    back.setImage(spielkarte.getBack());
                }
            },1000);
            
        }
        
        
    }
}