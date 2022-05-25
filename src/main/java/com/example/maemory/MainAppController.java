package com.example.maemory;

import javafx.fxml.*;
import javafx.scene.image.*;

import java.util.*;

public class MainAppController {
    public ImageView front;
    public ImageView back;
    
    @FXML
    protected void onHelloButtonClick() throws Exception {
        CardSetFunctions.convertToCardSetWithDialog(100,100,3);
        CardSetFunctions.showCarSetSelectionDialog();
        
    }
}