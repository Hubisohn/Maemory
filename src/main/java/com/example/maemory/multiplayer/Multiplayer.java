package com.example.maemory.multiplayer;

import com.example.maemory.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Multiplayer {

    public static Stage hostStage;
    public static Stage joinStage;

    @FXML
    public void onHostServerClick(){
        try {
            hostStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MultiplayerFxmlFiles/hostServer.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            hostStage.setTitle("Host Server");
            hostStage.setScene(scene);
            hostStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //return window from JoinServer.fxml
    public static Stage getJoinServerScene(){return joinStage;}
    //return window from HostServer.fxml
    public static Stage getHostServerScene(){
        return hostStage;
    }

    @FXML
    public void onJoinServerClick(){
        try {
            joinStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MultiplayerFxmlFiles/JoinServer.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            joinStage.setTitle("Join Server");
            joinStage.setScene(scene);
            joinStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
