module com.example.maemory {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.maemory to javafx.fxml;
    exports com.example.maemory;
    exports com.example.maemory.multiplayer;
    opens com.example.maemory.multiplayer to javafx.fxml;
}