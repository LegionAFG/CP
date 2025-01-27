package com.example.projekt.controller;


import com.example.projekt.model.Appointment;
import com.example.projekt.model.Client;
import com.example.projekt.service.AlertService;
import com.example.projekt.service.IdService;
import com.example.projekt.service.NavigateService;
import com.example.projekt.sql.AppointmentCRUD;
import com.example.projekt.sql.ClientCRUD;
import com.example.projekt.sql.DatabaseConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;

public class ClientController {

    NavigateService navigateService;
    ClientCRUD clientCRUD;
    DatabaseConnection dbConnection;
    AppointmentCRUD appointmentCRUD;
    IdService idService;
    AlertService alertService;

    private static final String ALPHABETIC_PATTERN = "[a-zA-Z]+";

    private boolean isEditMode = false;

    private void clearField() {
        lastname.clear();
        firstname.clear();
        date.setValue(null);
        gender.setValue(null);
        nationality.clear();
        relationship.setValue(null);
    }

    private String capitalizeFirstLetter(String input) {
        if (input != null && !input.isEmpty()) {
            return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
        }
        return input;
    }

    public ClientController() {
        this.dbConnection = new DatabaseConnection();
        this.navigateService = new NavigateService();
        this.alertService = new AlertService();
        this.clientCRUD = new ClientCRUD(dbConnection);
        this.appointmentCRUD = new AppointmentCRUD(dbConnection);
        this.idService = new IdService(clientCRUD);

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
    Button deleteButton;

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
    TableView<Appointment> appointmentTableClient;

    @FXML
    TableColumn<Appointment, String> appointmentStreetColumn;
    @FXML
    TableColumn<Appointment, String> appointmentCityColumn;
    @FXML
    TableColumn<Appointment, String> institutionClientColumn;
    @FXML
    TableColumn<Appointment, LocalDate> appointmentDateClientColumn;
    @FXML
    TableColumn<Appointment, LocalTime> appointmentTimeClientColumn;
    @FXML
    TableColumn<Appointment, String> statusClientColumn;

    @FXML
    public void initialize() {

        gender.getItems().addAll("Male", "Female", "Other");
        gender.setValue("Pleas choose");

        relationship.getItems().addAll("Married", "Single", "Complicated", "Divorced", "Widowed");
        relationship.setValue("Pleas choose");

        IdService idService = new IdService(clientCRUD);
        clientID.setText(idService.generateUnique6DigitId());
        clientID.setDisable(true);

        appointmentTableClient.setRowFactory(tv -> {
            var row = new TableRow<Appointment>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Appointment clickedAppointment = row.getItem();

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    navigateService.navigateAppointmentDetails(stage, clickedAppointment);
                }
            });
            return row;
        });
    }

    public void setClientDetails(Client client) {
        if (client != null) {
            clientID.setText(client.getId());
            firstname.setText(client.getFirstname());
            lastname.setText(client.getLastname());
            nationality.setText(client.getNationality());
            date.setValue(client.getBirthdate());
            gender.setValue(client.getGender());
            relationship.setValue(client.getRelationship());

            setEditMode(true);

            ObservableList<Appointment> appointmentList = appointmentCRUD.getAppointmentsByClientId(clientID.getText());

            appointmentDateClientColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            appointmentTimeClientColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
            institutionClientColumn.setCellValueFactory((new PropertyValueFactory<>("institution")));
            appointmentCityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
            appointmentStreetColumn.setCellValueFactory(new PropertyValueFactory<>("street"));
            statusClientColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

            appointmentTableClient.setItems(appointmentList);
        }

    }

    public void setEditMode(boolean editMode) {
        this.isEditMode = editMode;

        if(editMode){
            saveButton.setText("UPDATE");
            saveButton.setStyle("-fx-background-color: #008689; -fx-text-fill: #FFFFFF;");
        }else{
            saveButton.setText("SAVE");
        }
    }

    @FXML
    public void onHomeButtonClick(ActionEvent event) {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        navigateService.navigate(stage, "home");

    }

    @FXML
    public void onAppointmentButtonClick(ActionEvent event) {
        String clientId = clientID.getText();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        ObservableList<Appointment> appointments = appointmentCRUD.getAppointmentsByClientId(clientId);

        if (appointments == null || appointments.isEmpty()) {

           navigateService.navigateClientIdAppointment(stage,clientId);
           return;
        }

        navigateService.navigateAppointmentDetails(stage, appointments);
    }


    @FXML
    public void onFileButtonClick(ActionEvent event) {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        navigateService.navigate(stage, "file");

    }

    @FXML
    public void onHistoriesButtonClick(ActionEvent event) {
        String clientId = clientID.getText();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        navigateService.navigateClientIdHistoy(stage,clientId);

    }
    @FXML
    public void onSaveButtonClick(ActionEvent ignoredEvent) {
        String id = clientID.getText().trim();
        String last = lastname.getText().trim().toUpperCase();
        String first = capitalizeFirstLetter(firstname.getText().trim());
        LocalDate birthdate = date.getValue();
        String genderValue = gender.getValue();
        String nation = capitalizeFirstLetter(nationality.getText().trim());
        String relation = relationship.getValue();

        if (last.isEmpty() || first.isEmpty() || birthdate == null ||
                genderValue == null || nation.isEmpty() || relation == null) {
            alertService.showErrorAlert("Please fill out all required fields.");
            return;
        }

        if (!last.matches(ALPHABETIC_PATTERN) ||
                !first.matches(ALPHABETIC_PATTERN) ||
                !nation.matches(ALPHABETIC_PATTERN)) {
            alertService.showErrorAlert("The fields may only contain letters.");
            return;
        }

        if (!isEditMode) {

            boolean insertSuccess = clientCRUD.insertClient(
                    id, last, first, birthdate, genderValue, nation, relation
            );
            if (insertSuccess) {
                alertService.showInfoAlert("New client was added successfully. ID: " + id);
                clearField();

            } else {
                alertService.showErrorAlert("There was a problem adding the new client.");
            }
        } else {

            boolean updateSuccess = clientCRUD.updateClient(
                    id, last, first, birthdate, genderValue, nation, relation
            );
            if (updateSuccess) {
                alertService.showInfoAlert("Client with ID " + id + " was updated successfully.");
            } else {
                alertService.showErrorAlert("Error updating the client with ID " + id + ".");
            }
        }
    }

    @FXML
    public void onDeleteButtonClick(ActionEvent event) {

        String id = clientID.getText().trim();

        boolean deleted = clientCRUD.deleteClient(id);

        if (id.isEmpty()) {
            alertService.showWarningAlert("Please enter a valid client ID!");
            return;
        }
        if (deleted) {
            alertService.showInfoAlert("Client was successfully deleted");
        } else {
            alertService.showErrorAlert("No client found or deletion error");
            return;
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        navigateService.navigate(stage, "home");
    }

}