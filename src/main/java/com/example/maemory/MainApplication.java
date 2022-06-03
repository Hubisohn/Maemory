package com.example.maemory;

import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;

import java.io.*;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("File1.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Player name");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();

        Controller1 controller1;
        controller1 = fxmlLoader.getController();
        controller1.setStageOld(stage);
    }

    public static void main(String[] args) {
        launch();
    }

}