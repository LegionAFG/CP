package com.example.projekt.controller;

import com.example.projekt.model.Appointment;
import com.example.projekt.model.Client;
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

public class AppointmentController {

    NavigateService navigateService;
    ClientCRUD clientCRUD;
    DatabaseConnection dbConnection;
    AppointmentCRUD appointmentCRUD;

    private String clientId;

    public void setClientId(String clientId) {
        this.clientId = clientId;
        clientID.setText(clientId);
    }

    public AppointmentController() {

        this.dbConnection = new DatabaseConnection();
        this.navigateService = new NavigateService();
        this.clientCRUD = new ClientCRUD(dbConnection);
        this.appointmentCRUD = new AppointmentCRUD(dbConnection);

    }

    @FXML
    Button backButton;
    @FXML
    Button homeButton;
    @FXML
    Button saveButton;

    @FXML
    TextField clientID;
    @FXML
    TextField institution;
    @FXML
    TextField city;
    @FXML
    TextField street;
    @FXML
    ChoiceBox<String> status;
    @FXML
    TextField time;
    @FXML
    DatePicker date;

    @FXML
    TableView<Appointment> appointmentTableClient;

    @FXML
    TableColumn<Appointment, LocalDate> appointmentDateClientColumn;
    @FXML
    TableColumn<Appointment, LocalTime> appointmentTimeClientColumn;
    @FXML
    TableColumn<Appointment, String> institutionClientColumn;
    @FXML
    TableColumn<Appointment, String> appointmentCityColumn;
    @FXML
    TableColumn<Appointment, String> appointmentStreetColumn;
    @FXML
    TableColumn<Appointment, String> appointmentStatusColumn;


    @FXML
    public void initialize() {

        clientID.setDisable(true);

        status.getItems().addAll("Cancelled", "Scheduled", "Completed");
        status.setValue("Pleas choose");


    }

    public void onSaveButtonClick(ActionEvent event) {

        String idAppointment = clientID.getText();
        String institutAppointment = institution.getText();
        String cityAppointment = city.getText();
        String streetAppointment = street.getText();
        LocalDate localDate = date.getValue();
        String timeAppointment = time.getText();
        String statusAppointment = status.getValue();


        appointmentCRUD.insertAppointment(idAppointment, localDate, timeAppointment, institutAppointment, cityAppointment, streetAppointment, statusAppointment);


    }

    @FXML
    public void onBackButtonClick(ActionEvent event) {


        ObservableList<Client> clients = clientCRUD.getClientByClientId(clientID.getText());

        if (clients == null || clients.isEmpty()) {
            System.out.println("Kein Client mit der angegebenen ID gefunden.");
            return;
        }

        Client selectedClient = clients.getFirst();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        navigateService.navigateClientDetails(stage, selectedClient);
    }

    @FXML
    public void onHomeButtonClick(ActionEvent event) {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        navigateService.navigate(stage, "home");
    }

    public void setAppointmentDetails(Appointment appointment) {
        if (appointment == null) {
            System.out.println("Kein gültiger Termin übergeben.");
            return;
        }

        clientID.setText(appointment.getId());
        institution.setText(appointment.getInstitution());
        city.setText(appointment.getCity());
        street.setText(appointment.getStreet());
        date.setValue(appointment.getDate());
        time.setText(String.valueOf(appointment.getTime()));


        ObservableList<Appointment> appointmentList = appointmentCRUD.getAppointmentsByClientId(clientID.getText());

        if (appointmentList == null || appointmentList.isEmpty()) {
            System.out.println("Keine Termine für Client-ID gefunden: " + clientID.getText());
            return;
        }


        appointmentDateClientColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        appointmentTimeClientColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        institutionClientColumn.setCellValueFactory(new PropertyValueFactory<>("institution"));
        appointmentCityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        appointmentStreetColumn.setCellValueFactory(new PropertyValueFactory<>("street"));
        appointmentStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));


        appointmentTableClient.setItems(appointmentList);
    }


    public void setAppointments(ObservableList<Appointment> appointmentList) {
        if (appointmentList == null || appointmentList.isEmpty()) {
            System.out.println("Die übergebene Terminliste ist leer oder null.");
            return;
        }

        Appointment firstAppointment = appointmentList.get(0);
        clientID.setText(firstAppointment.getId());

        appointmentDateClientColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        appointmentTimeClientColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        institutionClientColumn.setCellValueFactory(new PropertyValueFactory<>("institution"));
        appointmentCityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        appointmentStreetColumn.setCellValueFactory(new PropertyValueFactory<>("street"));
        appointmentStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        appointmentTableClient.setItems(appointmentList);
    }

}
