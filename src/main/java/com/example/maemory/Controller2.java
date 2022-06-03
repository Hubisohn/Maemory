package com.example.maemory;

import javafx.application.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public class Controller2 implements Initializable {
    public Button SaveResults;
    @FXML
    private Label welcomeText;

    @FXML
    private Button buttonSingle;

    @FXML
    private Button buttonMulti;

    @FXML
    private TableView<User> tableView;

    @FXML
    private ColorPicker colorpicker;

    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField textfield;

    @FXML
    private TableColumn<String, String> username;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    Stage stageOld;
    Controller1 controller1;
    ObservableList<User> users = FXCollections.observableArrayList(new ArrayList<User>());


    public void setOldStage(Stage stage) {
        stageOld = stage;
    }

    public void gameSingleplayer(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("File3.fxml"));
        Stage stage = new Stage();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 320, 240);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        stage.setTitle("Size Gamefield");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();

        Controller3 controller3;
        controller3 = fxmlLoader.getController();
        controller3.setStageOld(stage);

        stageOld.close();
    }

    public void gameMultiplayer(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("File3.fxml"));
        Stage stage = new Stage();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 320, 240);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        stage.setTitle("Size Gamefield");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();

        Controller3 controller3;
        controller3 = fxmlLoader.getController();
        controller3.setStageOld(stage);

        stageOld.close();
    }

    public void ProgrammSchliesen(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void backgroundcolour(ActionEvent actionEvent) {
        borderPane.setBackground(new Background(new BackgroundFill(colorpicker.getValue(), new CornerRadii(0), new Insets(0))));
        tableView.setBackground(new Background(new BackgroundFill(colorpicker.getValue(), new CornerRadii(0), new Insets(0))));
        Matcher matcher = Pattern.compile("(?<=0x).+").matcher(colorpicker.getValue().toString());
        String string;
        if(matcher.find()) {
            string = matcher.group();
        }else{
            return;
        }
        tableView.setRowFactory((s) -> {
            final TableRow<User> tableRow = new TableRow<>();
            tableRow.setStyle("-fx-background-color: #" + string);
            return tableRow;
        });
        tableView.refresh();
    }

    public void regelwerk(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("File4.fxml"));
        Stage stage = new Stage();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 300);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Rules for the game");
        stage.setScene(scene);
        stage.show();
    }

    public void playerName(Controller1 controller) {
        controller1 = controller;
        textfield.setText(controller1.getNickname().getText());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (tableView != null) {
            tableView.setItems(users);
        }
    }

    public void saveDatei(ActionEvent actionEvent) {
        ObjectOutputStream outputStream = null;
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(new File("src/main/resources/com/example/maemory/Results.txt")));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        for (User user:tableView.getItems()) {
            try {
                outputStream.writeObject(user);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void loadDatei(ActionEvent actionEvent) {
        tableView.getItems().clear();
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File("src/main/resources/com/example/maemory/Results.txt")));
            Object o = null;
            try{
                while (true){
                    users.add((User) objectInputStream.readObject());
                }
            }catch (EOFException e){} catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}