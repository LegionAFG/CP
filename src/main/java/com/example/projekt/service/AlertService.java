package com.example.projekt.service;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

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
    public boolean showConfirmation(String contentText,String headerText) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("WARNING");
        confirmationAlert.setHeaderText(headerText);
        confirmationAlert.setContentText(contentText);

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;

        }
    }

