package com.example.projekt.service;

import javafx.scene.control.Alert;

public class AlertService {

    public void showWarningAlert(String content){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("WARNING");
        alert.setContentText(content);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void showErrorAlert(String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setContentText(content);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void showInfoAlert(String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("INFORMATION");
        alert.setContentText(content);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

}
