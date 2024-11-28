package com.example.projekt.service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;

public class NavigateService {

    private static final Logger logger = Logger.getLogger(NavigateService.class.getName());

    private static final String HOME = "/com/example/projekt/Home.fxml";
    private static final String FILE = "/com/example/projekt/File.fxml";
    private static final String APPOINTMENT = "/com/example/projekt/Appointment.fxml";
    private static final String CLIENT = "/com/example/projekt/Client.fxml";
    private static final String HISTORIES = "/com/example/projekt/Histories.fxml";

    public void navigate(Stage stage, String page) {
        try {

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

        } catch (IllegalArgumentException e) {

            logger.severe("Navigation fehlgeschlagen: " + e.getMessage());
            showErrorDialog("Navigation fehlgeschlagen", e.getMessage());
        } catch (IOException e) {

            logger.severe("Fehler beim Laden der Seite: " + e.getMessage());
            showErrorDialog("Seitenladefehler", "Die Seite konnte nicht geladen werden.");
        }

    }

    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}