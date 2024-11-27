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

public class HomeController {

    private final Logger logger = Logger.getLogger(HomeController.class.getName());

    NavigateService navigateService;

    public HomeController() {
        this.navigateService = new NavigateService();
    }

    @FXML
    Button button;

    @FXML
    public void onClientButtonClick(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        navigateService.navigate(stage, "client");
        logger.log(Level.INFO, "Klienten seite geladen");
    }
}