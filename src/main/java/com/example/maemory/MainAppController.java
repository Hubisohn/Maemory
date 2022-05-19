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
        
        front.setImage(list.get(0).getFront());
        back.setImage(list.get(0).getBack());
        
    }
}