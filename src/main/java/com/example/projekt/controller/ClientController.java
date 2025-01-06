package com.example.projekt.controller;


import com.example.projekt.service.NavigateService;
import com.example.projekt.sql.ClientCRUD;
import com.example.projekt.sql.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

import javafx.stage.Stage;
import java.time.LocalDate;

public class ClientController {

    NavigateService navigateService;
    ClientCRUD clientCRUD;
    DatabaseConnection dbConnection;

    public ClientController() {
        this.dbConnection = new DatabaseConnection();
        this.navigateService = new NavigateService();
        this.clientCRUD = new ClientCRUD(dbConnection);
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
    Button saveButton;

    @FXML
    TextField clientID;
    @FXML
    TextField lastname;
    @FXML
    TextField firstname;
    @FXML
    TextField nationality;
    @FXML
    DatePicker date;
    @FXML
    ChoiceBox<String> gender;
    @FXML
    ChoiceBox<String> relationship;



    @FXML
    public void initialize() {

        gender.getItems().addAll("Male", "Female", "Divers");
        gender.setValue("Pleas choose");

        relationship.getItems().addAll("Married", "Single");
        relationship.setValue("Pleas choose");
    }


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

    @FXML
    public void onSaveButtonClick(ActionEvent event) {

        String id = clientID.getText();
        String last = lastname.getText();
        String first = firstname.getText();
        LocalDate birthDate = date.getValue();
        String genderValue = gender.getValue();
        String nation = nationality.getText();
        String relation = relationship.getValue();

        clientCRUD.insertClient(id, last, first, birthDate, genderValue, nation, relation);
    }
}

