package com.example.projekt.controller;

import com.example.projekt.model.History;
import com.example.projekt.service.AlertService;
import com.example.projekt.service.NavigateService;
import com.example.projekt.sql.DatabaseConnection;
import com.example.projekt.sql.HistoryCRUD;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;

public class HistoryController {

    NavigateService navigateService;
    HistoryCRUD historiesCRUD;
    DatabaseConnection dbConnection;
    AlertService alertService;

    String clientId;
    String ALPHABETIC_PATTERN = "[a-zA-Z]+";
    String TIME_PATTERN = "^([01]\\d|2[0-3]):([0-5]\\d)$";
    public HistoryController() {
        this.dbConnection = new DatabaseConnection();
        this.navigateService = new NavigateService();
        this.alertService = new AlertService();
        this.historiesCRUD = new HistoryCRUD(dbConnection);
    }


    public void setClientId(String clientId) {
        this.clientId = clientId;
        clintIdTextFiled.setText(clientId);
    }

    @FXML
    Button backButton;
    @FXML
    Button homeButton;
    @FXML
    Button saveButton;
    @FXML
    TextField titleField;
    @FXML
    TextArea contentArea;
    @FXML
    DatePicker historyDate;
    @FXML
    TextField historyTime;
    @FXML
    TextField clintIdTextFiled;




    @FXML
    public void onBackButtonClick(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        navigateService.navigate(stage, "client");
    }

    @FXML
    public void onHomeButtonClick(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        navigateService.navigate(stage, "home");
    }

    @FXML
    public void initialize() {
        clintIdTextFiled.setDisable(true);
    }

    @FXML
    public void onSaveButtonClick(ActionEvent event) {

        String title = titleField.getText().trim();
        String content = contentArea.getText().trim();
        LocalDate date = historyDate.getValue();
        String time = historyTime.getText().trim();
        String id = clintIdTextFiled.getText().trim();

        if (title.isEmpty() || content.isEmpty() || date == null || time.isEmpty() || id.isEmpty()) {
            alertService.showErrorAlert("Please fill out all required fields.");
            return;
        }
        if(!time.matches(TIME_PATTERN)){
            alertService.showErrorAlert("Time format invalid. Use HH:MM.");
            return;
        }
        if(!title.matches(ALPHABETIC_PATTERN)){
            alertService.showErrorAlert("Title must contain only letters.");
            return;
        }
        historiesCRUD.insertHistories(id, date, LocalTime.parse(time), title, content);
    }

    public void setHistoryDetails(History history){

       clintIdTextFiled.setText(String.valueOf(history.getClientId()));
        historyDate.setValue(history.getHistoryDate());
        historyTime.setText(String.valueOf(history.getHistoryTime()));
        titleField.setText(history.getTitle());

        ObservableList<History> histories = historiesCRUD.getHistoryByClientId(clintIdTextFiled.getText());


        }
    }


