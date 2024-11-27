package com.example.projekt.controller;

import com.example.projekt.service.NavigateService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientController {


    private final Logger logger = Logger.getLogger(ClientController.class.getName());

    NavigateService navigateService;

    public ClientController() {
        this.navigateService = new NavigateService();
    }

    @FXML
    Button homeButton;
    @FXML
    Button appointmentButton;
    @FXML
    Button fileButton;
    @FXML
    Button historiesButton;

    @FXML
    public void onHomeButtonClick(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        navigateService.navigate(stage, "home");
        logger.log(Level.INFO, "Home seite geladen");
    }

    @FXML
    public void onAppointmentButtonClick(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        navigateService.navigate(stage, "appointment");
        logger.log(Level.INFO, "Home seite geladen");
    }

    @FXML
    public void onFileButtonClick(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        navigateService.navigate(stage, "home");
        logger.log(Level.INFO, "Home seite geladen");
    }

    @FXML
    public void onHistoriesButtonClick(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        navigateService.navigate(stage, "home");
        logger.log(Level.INFO, "Home seite geladen");
    }

}
