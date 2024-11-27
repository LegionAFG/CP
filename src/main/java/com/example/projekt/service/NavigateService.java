package com.example.projekt.service;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NavigateService {

    private static final String HOME = "/com/example/projekt/Home.fxml";
    private static final String FILE = "/com/example/projekt/File.fxml";
    private static final String APPOINTMENT = "/com/example/projekt/Appointment.fxml";
    private static final String CLIENT = "/com/example/projekt/Client.fxml";
    private static final String HISTORIES = "/com/example/projekt/Histories.fxml";

    public EventHandler<ActionEvent> navigate(Stage stage, String page) throws IOException {

        String fxmlPath = switch (page) {
            case "home" -> HOME;
            case "file" -> FILE;
            case "appointment" -> APPOINTMENT;
            case "client" -> CLIENT;
            case "histories" -> HISTORIES;
            default -> throw new IllegalArgumentException("Unbekannte Seite: " + page);
        };

        FXMLLoader fxmlLoader = new FXMLLoader(NavigateService.class.getResource(fxmlPath));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Case Pilot");
        stage.setScene(scene);
        stage.show();
        return null;
    }
}