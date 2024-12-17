module com.example.projekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.sql;



    opens com.example.projekt to javafx.fxml;
    exports com.example.projekt.service;
    exports com.example.projekt.run;
    opens com.example.projekt.run to javafx.fxml;
    exports com.example.projekt.controller;
    opens com.example.projekt.controller to javafx.fxml;
}
