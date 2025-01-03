package com.example.projekt.controller;

import com.example.projekt.service.NavigateService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;



public class ClientController {

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
    public void onHomeButtonClick(ActionEvent event) {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        navigateService.navigate(stage, "home");

    }

    @FXML
    public void onAppointmentButtonClick(ActionEvent event) {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        navigateService.navigate(stage, "appointment");

    }

    @FXML
    public void onFileButtonClick(ActionEvent event) {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        navigateService.navigate(stage, "file");

    }

    @FXML
    public void onHistoriesButtonClick(ActionEvent event) {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        navigateService.navigate(stage, "histories");

    }
}

