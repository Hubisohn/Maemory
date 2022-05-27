package com.example.maemory;

import javafx.fxml.*;
import javafx.scene.image.*;
public class MainAppController {
    public ImageView front;
    public ImageView back;
    
    @FXML
    protected void onHelloButtonClick() {
    
        CardSetFunctions.showCardSetSelectionDialog(4);
        //CardSetFunctions.convertToCardSetWithDialog(100,100,4);
    }
}