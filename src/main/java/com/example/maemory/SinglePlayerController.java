package com.example.maemory;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

public class SinglePlayerController implements Initializable {
    public BorderPane box;
    HashMap<Integer, Spielkarte> feld = new HashMap<>();
    GridPane spielfeld;
    ArrayList<Spielkarte> cardsOpened = new ArrayList<>();
    Image background = null;
    private SimpleIntegerProperty points[] = new SimpleIntegerProperty[2];
    private int activePlayer = 1;
    private int remainingCards;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        points[0] = new SimpleIntegerProperty(0);
        points[1] = new SimpleIntegerProperty(0);
        start(8);
        spielfeld.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent ->{
            if(cardsOpened.size() < 2){
                ImageView imageView = (ImageView) mouseEvent.getTarget();
                karteAufdecken(imageView);
                if(cardsOpened.get(0).getIndex() == cardsOpened.get(1).getIndex()){
                    cardsOpened.remove(1);
                }
                if (cardsOpened.size() == 2){
                    timeline.play();
                }
            }
            else{
              check();
            }

    });
    }


    private void karteAufdecken(ImageView imageView){
        Spielkarte x = (Spielkarte)feld.get(spielfeld.getChildren().indexOf(imageView));
        imageView.setImage(x.getVorderseite());
        cardsOpened.add(x);
    }

    public void autoPlay(){

    }



    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent actionEvent) {
            if (cardsOpened.size() == 2){
                check();
            }
        }
    }));





    private void check() {
        if(cardsOpened.get(0).getVorderseiteID() == cardsOpened.get(1).getVorderseiteID()){
            spielfeld.getChildren().get(cardsOpened.get(0).getIndex()).setVisible(false);
            spielfeld.getChildren().get(cardsOpened.get(1).getIndex()).setVisible(false);
            points[activePlayer].setValue(points[activePlayer].getValue() + 10);
            remainingCards -= 2;
            if (remainingCards == 0){
                if (points[0].getValue() > points[1].getValue()){
                    box.setBottom(new Label("Player 1 wins!"));
                }
                else if (points[0].getValue() < points[1].getValue()){
                    box.setBottom(new Label("Player 2 wins!"));
                }
                else{
                    box.setBottom(new Label("Draw!"));
                }
            }
        }
        else{
            ((ImageView)spielfeld.getChildren().get(cardsOpened.get(1).getIndex())).setImage(background);
            ((ImageView)spielfeld.getChildren().get(cardsOpened.get(0).getIndex())).setImage(background);
            if(activePlayer == 0){
                activePlayer = 1;
            } else {
                activePlayer = 0;
            }
        }
        cardsOpened.clear();
    }

    //4x4 6x6 8x8
    void start(int size){
        remainingCards = size * size;
        //Hintergrund
        try {
            background = new Image(new FileInputStream("src/main/resources/Images/Hintergrund.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        int anz[] = new int[(int)Math.pow(size,2)/2];
        Random random = new Random();
        Arrays.fill(anz, 2);
        HashMap<Integer,String> motive = new HashMap<>();
        for (int i = 0; i < anz.length; i++) {
            String x = "src/main/resources/Images/" + i + ".jpg";
            motive.put(i,x);
        }
        int index = 0;
        spielfeld = new GridPane();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                ImageView imageView = new ImageView(background);
                imageView.setX(80);
                imageView.setY(80);
                imageView.setFitWidth(80);
                imageView.setFitHeight(80);
                spielfeld.add(imageView,j,i);
                int y;
                do{
                    y= random.nextInt(anz.length);
                } while(anz[y] <= 0);
                feld.put(index, new Spielkarte(spielfeld.getChildren().indexOf(imageView),motive.get(y),y));
                anz[y] --;
                index++;
            }
        }
        box.setCenter(spielfeld);

        //Counter
        Label counter1 = new Label("0");
        Label counter2 = new Label("0");
        counter1.textProperty().bind(points[0].asString());
        counter2.textProperty().bind(points[1].asString());
        counter1.setStyle("-fx-font-size: 30px; -fx-text-fill: #ff0000;");
        counter2.setStyle("-fx-font-size: 30px; -fx-text-fill: #ff0000;");
        box.setBottom(counter2);
        box.setTop(counter1);
    }

}
