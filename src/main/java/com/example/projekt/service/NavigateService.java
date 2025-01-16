package com.example.projekt.service;

import com.example.projekt.controller.AppointmentController;
import com.example.projekt.controller.ClientController;
import com.example.projekt.model.Appointment;
import com.example.projekt.model.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
            stage.setResizable(false);

        } catch (IllegalArgumentException e) {

            logger.severe("Navigation fehlgeschlagen: " + e.getMessage());
            showErrorDialog("Navigation fehlgeschlagen", e.getMessage());
        } catch (IOException e) {

            logger.severe("Fehler beim Laden der Seite: " + e.getMessage());
            showErrorDialog("Seitenladefehler", "Die Seite konnte nicht geladen werden.");
        }

    }

    public void navigateClientDetails(Stage stage, String page, Client client ){

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
            Parent root = fxmlLoader.load();

            ClientController clientController = fxmlLoader.getController();
            clientController.setClientDetails(client);

            Scene scene = new Scene(root, 800, 600);
            stage.setTitle("Case Pilot");
            stage.setScene(scene);
            stage.show();
            stage.setResizable(false);

        } catch (IllegalArgumentException e) {

            logger.severe("Navigation fehlgeschlagen: " + e.getMessage());
            showErrorDialog("Navigation fehlgeschlagen", e.getMessage());
        } catch (IOException e) {

            logger.severe("Fehler beim Laden der Seite: " + e.getMessage());
            showErrorDialog("Seitenladefehler", "Die Seite konnte nicht geladen werden.");
        }

    }

    public void navigateAppointmentDetails(Stage stage, String page, Appointment appointment ){

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
            Parent root = fxmlLoader.load();

            AppointmentController appointmentController = fxmlLoader.getController();
            appointmentController.setAppointmentDetails(appointment);

            Scene scene = new Scene(root, 800, 600);
            stage.setTitle("Case Pilot");
            stage.setScene(scene);
            stage.show();
            stage.setResizable(false);

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