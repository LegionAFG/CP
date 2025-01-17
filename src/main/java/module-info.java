module com.example.projekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.sql;

    exports com.example.projekt.service;
    exports com.example.projekt.run;
    exports com.example.projekt.controller;
    exports com.example.projekt.model;

    opens com.example.projekt.run to javafx.fxml, javafx.graphics;
    opens com.example.projekt.controller to javafx.fxml;
    opens com.example.projekt.model to javafx.base;

}
