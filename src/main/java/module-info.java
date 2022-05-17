module com.example.maemory {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.maemory to javafx.fxml;
    exports com.example.maemory;
}