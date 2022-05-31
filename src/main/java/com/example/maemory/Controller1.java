package com.example.maemory;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller1 implements Initializable {
    @FXML
    private TextField nickname;

    @FXML
    private BorderPane borderPane;


    Stage stageOld;

    public void setStageOld(Stage stage){
        stageOld = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nickname.setFont(new Font(22));
        nickname.setAlignment(Pos.BOTTOM_CENTER);
        borderPane.setBackground(new Background(new BackgroundImage(new Image("startPicture.jpg"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1,1, true, true, false, false))));

        Platform.runLater(() -> {
            borderPane.requestFocus();
        });
        borderPane.setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.ENTER && nickname.getLength() > 0){
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("File2.fxml"));
                Stage stage = new Stage();
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load(), 320, 240);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                stage.setTitle("Mämory");
                stage.setScene(scene);
                stage.setFullScreen(true);
                stage.show();
                stageOld.close();

                Controller2 controller2;
                controller2 = fxmlLoader.getController();
                controller2.setOldStage(stage);
                controller2.playerName(this);
            }
        });
    }

    public TextField getNickname() {
        return nickname;
    }
}
