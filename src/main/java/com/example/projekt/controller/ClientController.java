package com.example.projekt.controller;

import com.example.projekt.mysql.ClientData;
import com.example.projekt.service.NavigateService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.logging.Logger;

public class ClientController {

    private static final Logger logger = Logger.getLogger(ClientController.class.getName());

    NavigateService navigateService;
    ClientData clientData;

    public ClientController() {
        this.navigateService = new NavigateService();
        this.clientData = new ClientData();
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
    TextField clientID = new TextField();
    @FXML
    TextField firstname = new TextField();
    @FXML
    TextField lastname = new TextField();
    @FXML
    DatePicker dateOfBirth = new DatePicker();
    @FXML
    TextField gender = new TextField();
    @FXML
    TextField nationality = new TextField();
    @FXML
    TextField relationship = new TextField();


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
    public void addClientButton(ActionEvent event) {
        String clientIDText = clientID.getText();
        String lastnameText = lastname.getText();
        String firstnameText = firstname.getText();
        LocalDate dateOfBirthValue = dateOfBirth.getValue();
        String genderText = gender.getText();
        String nationalityText = nationality.getText();
        String relationshipText = relationship.getText();


        if (clientIDText.isEmpty() || lastnameText.isEmpty() || firstnameText.isEmpty() || dateOfBirthValue == null) {
            if (clientIDText.isEmpty()) {
                logger.info("Die Client-ID darf nicht leer sein.");
            }
            if (lastnameText.isEmpty()) {
                logger.info("Der Nachname darf nicht leer sein.");
            }
            if (firstnameText.isEmpty()) {
                logger.info("Der Vorname darf nicht leer sein.");
            }
            if (dateOfBirthValue == null) {
                logger.info("Das Geburtsdatum darf nicht leer sein.");
            }
            return;
        }

        clientData.addClient(clientIDText, lastnameText, firstnameText, dateOfBirthValue, genderText, nationalityText, relationshipText);
        logger.info("Klient erfolgreich hinzugefügt!");
    }


}
