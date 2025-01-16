package com.example.projekt.controller;


import com.example.projekt.model.Appointment;
import com.example.projekt.model.Client;
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

    public ClientController() {
        this.dbConnection = new DatabaseConnection();
        this.navigateService = new NavigateService();
        this.clientCRUD = new ClientCRUD(dbConnection);
        this.appointmentCRUD = new AppointmentCRUD(dbConnection);
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

        relationship.getItems().addAll("Married", "Single","Complicated","Divorced","Widowed");
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
                    navigateService.navigateAppointmentDetails(stage, "appointment", clickedAppointment);
                }
            });
            return row;
        });


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

        String last = lastname.getText();
        String first = firstname.getText();
        LocalDate birthDate = date.getValue();
        String genderValue = gender.getValue();
        String nation = nationality.getText();
        String relation = relationship.getValue();

        clientCRUD.insertClient(last, first, birthDate, genderValue, nation, relation);
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
}