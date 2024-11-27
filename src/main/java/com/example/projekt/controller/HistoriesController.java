package com.example.projekt.controller;

import com.example.projekt.service.NavigateService;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HistoriesController {

    private final Logger logger = Logger.getLogger(HistoriesController.class.getName());

    NavigateService navigateService;

    public HistoriesController(){
        this.navigateService = new NavigateService();
    }

    @FXML
    Button backButton;
    @FXML
    Button homeButton;

    @FXML
    public void onBackButtonClick(ActionEvent event)throws IOException{
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        navigateService.navigate(stage,"client");
        logger.log(Level.INFO, "Client seite geladen");
    }

    @FXML
    public void onHomeButtonClick(ActionEvent event)throws IOException{
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        navigateService.navigate(stage,"home");
        logger.log(Level.INFO, "Client seite geladen");
    }

}
