package com.example.projekt.controller;


import com.example.projekt.service.NavigateService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class FileController {

    NavigateService navigateService;

    public FileController(){
        this.navigateService = new NavigateService();
    }

    @FXML
    Button backButton;
    @FXML
    Button homeButton;

    @FXML
    public void onBackButtonClick(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        navigateService.navigate(stage,"client");
    }

    @FXML
    public void onHomeButtonClick(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        navigateService.navigate(stage,"home");
    }
}
