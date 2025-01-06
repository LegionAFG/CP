package com.example.projekt.controller;

import com.example.projekt.model.Client;
import com.example.projekt.service.NavigateService;
import com.example.projekt.sql.ClientCRUD;
import com.example.projekt.sql.DatabaseConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDate;

public class HomeController {

    NavigateService navigateService;
    ClientCRUD clientCRUD;
    DatabaseConnection dbConnection;

    public HomeController() {
        this.dbConnection = new DatabaseConnection();
        this.navigateService = new NavigateService();
        this.clientCRUD = new ClientCRUD(dbConnection);
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
    public void initialize() {
        DatabaseConnection dbConnection = new DatabaseConnection();
        ClientCRUD clientCRUD = new ClientCRUD(dbConnection);
        ObservableList<Client> clients = clientCRUD.getAllClients();

        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnLastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        columnFirstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        columnGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        columnNation.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        columnRelationship.setCellValueFactory(new PropertyValueFactory<>("relationship"));

        clientTable.setItems(clients);

    }

    @FXML
    public void onClientButtonClick(ActionEvent event){

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        navigateService.navigate(stage, "client");
    }
}