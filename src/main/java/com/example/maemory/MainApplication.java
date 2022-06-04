package com.example.maemory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("SinglePlayer.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        stage.setFullScreen(true);

        //Aso wert is Spiel gstartn
        //Ba start muss man die größe fan spielfeld (4, 6, odo 8) ingebm,
        // ba do schwierigkeit a prozentwert(wenn 1vs1 noa konn man irgendepas ingebm sel isch noa gleich)
        //und singlePlayer af true isch gegn Computer und false 1vs1
        SinglePlayerController controller = fxmlLoader.getController();
        controller.start(4, 25, false);

    }

    public static void main(String[] args) {
        launch();
    }
}