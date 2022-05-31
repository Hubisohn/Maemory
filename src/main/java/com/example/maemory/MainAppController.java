package com.example.maemory;

import javafx.fxml.*;
import javafx.scene.image.*;

import java.io.*;

public class MainAppController {
    public ImageView front;
    public ImageView back;
    
    @FXML
    protected void onHelloButtonClick() {
    
        String s = CardSetFunctions.showCardSetSelectionDialog(4);
    
        try {
            front.setImage(new Image(new FileInputStream(s+"/1.jpg")));
            back.setImage(new Image(new FileInputStream(s+"/background.jpg")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        
        
    }
}