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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;

public class HomeController {

    NavigateService navigateService;
    DatabaseConnection dbConnection;
    ClientCRUD clientCRUD;
    AppointmentCRUD appointmentCRUD;


    public HomeController() {
        this.dbConnection = new DatabaseConnection();
        this.navigateService = new NavigateService();
        this.clientCRUD = new ClientCRUD(dbConnection);
        this.appointmentCRUD = new AppointmentCRUD(dbConnection);
    }

    @FXML
    Button button;

    @FXML
    TableView<Client> clientTable;
    @FXML
    TableColumn<Client, String> columnId;
    @FXML
    TableColumn<Client, String> columnLastname;
    @FXML
    TableColumn<Client, String> columnFirstname;
    @FXML
    TableColumn<Client, LocalDate> columnDate;
    @FXML
    TableColumn<Client, String> columnNation;
    @FXML
    TableColumn<Client, String> columnGender;
    @FXML
    TableColumn<Client, String> columnRelationship;

    @FXML
    TableView<Appointment> appointmentTable;

    @FXML
    TableColumn<Appointment, String> clientAppointmentIdColumn;
    @FXML
    TableColumn<Appointment, String> appointmentLastnameColumn;
    @FXML
    TableColumn<Appointment, String> appointmentFirstnameColumn;
    @FXML
    TableColumn<Appointment, String> institutionColumn;
    @FXML
    TableColumn<Appointment, LocalDate> appointmentDateColumn;
    @FXML
    TableColumn<Appointment, LocalTime> appointmentTimeColumn;
    @FXML
    TableColumn<Appointment, String> statusColumn;

    @FXML
    public void initialize() {

        ObservableList<Client> clients = clientCRUD.getAllClients();
        ObservableList<Appointment> appointments = appointmentCRUD.getAllAppointments();
        clientTable.setRowFactory(tv -> {
            var row = new TableRow<Client>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Client clickedClient = row.getItem();

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    navigateService.navigateClientDetails(stage,clickedClient);
                }
            });
            return row;
        });

       appointmentTable.setRowFactory(tv -> {
            var row = new TableRow<Appointment>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Appointment clickedAppointment = row.getItem();

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    navigateService.navigateAppointmentDetails(stage,clickedAppointment);
                }
            });
            return row;
        });

        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnLastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        columnFirstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        columnGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        columnNation.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        columnRelationship.setCellValueFactory(new PropertyValueFactory<>("relationship"));

        clientTable.setItems(clients);

        clientAppointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        appointmentLastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        appointmentFirstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        institutionColumn.setCellValueFactory(new PropertyValueFactory<>("institution"));
        appointmentDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        appointmentTimeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        appointmentTable.setItems(appointments);

    }

    @FXML
    public void onClientButtonClick(ActionEvent event) {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        navigateService.navigate(stage, "client");
    }

    }


