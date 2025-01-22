package com.example.projekt.controller;

import com.example.projekt.model.Appointment;
import com.example.projekt.model.Client;
import com.example.projekt.service.AlertService;
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
import java.util.logging.Logger;

public class AppointmentController {

    NavigateService navigateService;
    ClientCRUD clientCRUD;
    DatabaseConnection dbConnection;
    AppointmentCRUD appointmentCRUD;
    AlertService alertService;

    String clientId;
    private static final String TIME_PATTERN = "^([01]\\d|2[0-3]):([0-5]\\d)$";
    private static final Logger logger = Logger.getLogger(AppointmentController.class.getName());
    private boolean isEditMode = false;


    public void setClientId(String clientId) {
        this.clientId = clientId;
        clientID.setText(clientId);
    }

    public AppointmentController() {

        this.dbConnection = new DatabaseConnection();
        this.navigateService = new NavigateService();
        this.alertService = new AlertService();
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

    public void onSaveButtonClick(ActionEvent ignoredEvent) {

        String idAppointment = clientID.getText().trim();
        String institutAppointment = institution.getText().trim();
        String cityAppointment = city.getText().trim();
        String streetAppointment = street.getText().trim();
        LocalDate localDate = date.getValue();
        String timeAppointment = time.getText().trim();
        String statusAppointment = status.getValue();

        if (idAppointment.isEmpty() || institutAppointment.isEmpty() || cityAppointment.isEmpty() ||
                streetAppointment.isEmpty() || localDate == null || timeAppointment.isEmpty() || statusAppointment == null) {
            alertService.showErrorAlert("Please fill out all required fields.");
            return;
        }

        if (!timeAppointment.matches(TIME_PATTERN)) {
            alertService.showErrorAlert("Time format invalid. It has to be HH:MM.");
            return;
        }

        if (!isEditMode) {

            boolean insertSuccess = appointmentCRUD.insertAppointment(idAppointment, localDate, timeAppointment, institutAppointment,
                    cityAppointment, streetAppointment, statusAppointment);
            if (insertSuccess) {
                alertService.showInfoAlert("New appointment was added successfully.");
            } else {
                alertService.showErrorAlert("Error adding the appointment.");
            }
        } else {

            Integer appointmentId = appointmentCRUD.getAppointmentId(idAppointment, localDate, timeAppointment);
            if (appointmentId == null) {
                alertService.showErrorAlert("Error: AppointmentID could not be retrieved from the database.");
                return;
            }

            boolean updateSuccess = appointmentCRUD.updateAppointment(appointmentId, idAppointment, localDate, timeAppointment,
                    institutAppointment, cityAppointment, streetAppointment, statusAppointment);
            if (updateSuccess) {
                alertService.showInfoAlert("Appointment updated successfully.");
            } else {
                alertService.showErrorAlert("Error updating the Appointment.");
            }
        }
    }

    @FXML
    public void onBackButtonClick(ActionEvent event) {


        ObservableList<Client> clients = clientCRUD.getClientByClientId(clientID.getText());

        if (clients == null || clients.isEmpty()) {
            logger.info("No client found with the specified ID.");
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
            logger.info("No valid appointment given.");
            return;
        }

        clientID.setText(appointment.getId());
        institution.setText(appointment.getInstitution());
        city.setText(appointment.getCity());
        street.setText(appointment.getStreet());
        date.setValue(appointment.getDate());
        time.setText(String.valueOf(appointment.getTime()));

        setEditMode(true);

        ObservableList<Appointment> appointmentList = appointmentCRUD.getAppointmentsByClientId(clientID.getText());

        if (appointmentList == null || appointmentList.isEmpty()) {
            logger.info("No appointments found for client ID: " + clientID.getText());
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

    public void setEditMode(boolean editMode) {
        this.isEditMode = editMode;

        if(editMode){

            saveButton.setText("UPDATE");
            saveButton.setStyle("-fx-background-color: #008689; -fx-text-fill: #FFFFFF;");
        }else{
            saveButton.setText("SAVE");
        }
    }

    public void setAppointments(ObservableList<Appointment> appointmentList) {
        if (appointmentList == null || appointmentList.isEmpty()) {
            logger.info("The appointment list passed is empty or null.");
            return;
        }

        Appointment firstAppointment = appointmentList.getFirst();
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
