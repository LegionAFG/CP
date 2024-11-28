package com.example.projekt.controller;

import com.example.projekt.service.NavigateService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HomeController {

    NavigateService navigateService;

    public HomeController() {
        this.navigateService = new NavigateService();
    }

    @FXML
    Button button;

    @FXML
    public void onClientButtonClick(ActionEvent event){

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        navigateService.navigate(stage, "client");
    }
}