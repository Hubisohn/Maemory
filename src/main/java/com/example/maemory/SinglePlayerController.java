package com.example.maemory;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
    public Label pointsPlayer;
    public Label pointsComputer;
    public GridPane spielfeld;
    HashMap<Integer, Spielkarte> feld = new HashMap<>();
    ArrayList<Spielkarte> cardsOpened = new ArrayList<>();
    Image background = null;
    private SimpleIntegerProperty[] points = new SimpleIntegerProperty[2];
    private int player = 0;
    private ArrayList<Spielkarte> knownCards = new ArrayList<>();
    private int level;


    //4x4 6x6 8x8
    //Leicht = 12,5%
    //Mittel = 25%
    //Schwer = 50%
    //Legendär = Merkt sich alle Karten




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        points[0] = new SimpleIntegerProperty(0);
        points[1] = new SimpleIntegerProperty(0);
        pointsPlayer.textProperty().bind(points[0].asString());
        pointsComputer.textProperty().bind(points[1].asString());
        start(6, 12.5);
        spielfeld.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (player == 0) {
                if (cardsOpened.size() < 2) {
                    ImageView imageView = (ImageView) mouseEvent.getTarget();
                    karteAufdecken(imageView);
                    if (cardsOpened.size() == 2) {
                        if (cardsOpened.get(0).getIndex() == cardsOpened.get(1).getIndex()) {
                            cardsOpened.remove(1);
                        } else {
                            timeline.play();
                        }
                    }
                } else {
                    check();
                }
            }
        });
    }


    private void karteAufdecken(ImageView imageView) {
        Spielkarte x = (Spielkarte) feld.get(spielfeld.getChildren().indexOf(imageView));
        //Test fehler
        System.out.println(x.getIndex() + "  " + x.getVorderseiteID());
        imageView.setImage(x.getVorderseite());
        cardsOpened.add(x);
        if (!knownCards.contains(x)) {
            knownCards.add(x);
            if(knownCards.size() > level){
                knownCards.remove(0);
            }
        }
        System.out.println(knownCards);
    }

    public void autoPlay() {
        for (int i = 0; i < knownCards.size(); i++) {
            for (int j = i + 1; j < knownCards.size(); j++) {
                if (knownCards.get(i).getVorderseiteID() == knownCards.get(j).getVorderseiteID()) {
                    karteAufdecken((ImageView) spielfeld.getChildren().get(knownCards.get(i).getIndex()));
                    karteAufdecken((ImageView) spielfeld.getChildren().get(knownCards.get(j).getIndex()));
                    Platform.runLater(() -> {
                        timeline.play();
                    });
                    return;
                }
            }
        }
        Random random = new Random();
        int randomIndex;
        do {
            randomIndex = random.nextInt(feld.size());
        } while (feld.get(randomIndex) == null);
        karteAufdecken((ImageView) spielfeld.getChildren().get(randomIndex));
        for (int i = 0; i < knownCards.size(); i++) {
            if (knownCards.get(i).getIndex() != randomIndex && feld.get(randomIndex).getVorderseiteID() == knownCards.get(i).getVorderseiteID()) {
                karteAufdecken((ImageView) spielfeld.getChildren().get(knownCards.get(i).getIndex()));
                Platform.runLater(() -> {
                    timeline.play();
                });
                return;
            }
        }
        do {
            randomIndex = random.nextInt(feld.size());
        } while (feld.get(randomIndex) == null || cardsOpened.get(0).getIndex() == randomIndex);
        karteAufdecken((ImageView) spielfeld.getChildren().get(randomIndex));
        Platform.runLater(() -> {
            timeline.play();
        });
    }


    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2.4), new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent actionEvent) {
            if (cardsOpened.size() >= 2) {
                check();
                timeline.stop();
            }
        }
    }));


    private synchronized void check() {
        if (cardsOpened.get(0).getVorderseiteID() == cardsOpened.get(1).getVorderseiteID()) {
            spielfeld.getChildren().get(cardsOpened.get(0).getIndex()).setVisible(false);
            spielfeld.getChildren().get(cardsOpened.get(1).getIndex()).setVisible(false);
            feld.put(cardsOpened.get(0).getIndex(), null);
            feld.put(cardsOpened.get(1).getIndex(), null);
            knownCards.remove(cardsOpened.get(0));
            knownCards.remove(cardsOpened.get(1));
            points[player].setValue(points[player].getValue() + 10);
            for (int i = 0; i < feld.size(); i++) {
                if (feld.get(i) != null) {
                    break;
                }
                if(i == feld.size() - 1){
                    gameOver();
                    return;
                }
            }
            if (player == 1) {

                cardsOpened.clear();
                autoPlay();
            }
        } else {
            ((ImageView) spielfeld.getChildren().get(cardsOpened.get(1).getIndex())).setImage(background);
            ((ImageView) spielfeld.getChildren().get(cardsOpened.get(0).getIndex())).setImage(background);
            if (player== 0) {
                player = 1;
                cardsOpened.clear();
                autoPlay();
            } else {
                player = 0;
            }
        }
        if(player == 0) cardsOpened.clear();
    }

    private void gameOver() {
        box.setLeft(null);
        box.setRight(null);
        box.setCenter(null);
        Label label = new Label("");
        label.setStyle("-fx-font-size: 50px; -fx-text-fill: #ff0000;");
        if (points[0].getValue() > points[1].getValue()) {
            label.setText("Spieler hat gewonnen!");
        } else if (points[0].getValue() < points[1].getValue()) {
            label.setText("Computer hat gewonnen!");
        }
        else {
            label.setText("Unentschieden!");
        }
        box.setCenter(label);
    }



    void start(int size, double difficulty) {
        level = (int) ((Math.pow(size,2) / 100) * difficulty);
        System.out.println(level);
        //Hintergrund
        try {
            background = new Image(new FileInputStream("src/main/resources/Images/Hintergrund.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        int anz[] = new int[(int) Math.pow(size, 2) / 2];
        Random random = new Random();
        Arrays.fill(anz, 2);
        HashMap<Integer, String> motive = new HashMap<>();
        for (int i = 0; i < anz.length; i++) {
            String x = "src/main/resources/Images/" + i + ".jpg";
            motive.put(i, x);
        }
        int index = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                ImageView imageView = new ImageView(background);
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                spielfeld.add(imageView, j, i);
                int y;
                do {
                    y = random.nextInt(anz.length);
                } while (anz[y] <= 0);
                feld.put(index, new Spielkarte(spielfeld.getChildren().indexOf(imageView), motive.get(y), y));
                anz[y]--;
                index++;
            }
        }
        box.setCenter(spielfeld);
    }

}
