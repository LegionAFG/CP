package com.example.projekt.run;

import com.example.projekt.mysql.ClientData;
import com.example.projekt.mysql.DatabaseConnection;
import com.example.projekt.service.NavigateService;
import javafx.application.Application;
import javafx.stage.Stage;

import java.time.LocalDate;

public class MainApp extends Application {

    private ClientData clientData;
    private NavigateService navigateService;
    private DatabaseConnection databaseConnection;

    public MainApp() {
        this.databaseConnection = new DatabaseConnection();
        this.navigateService = new NavigateService();
        this.clientData = new ClientData();
    }

    @Override
    public void start(Stage stage) {
        try {

            databaseConnection.connectToDatabase();


            clientData.addClient("123457", "Badr", "Said", LocalDate.of(1990,5,17), "Male", "Afghanistan", "Married");


            navigateService.navigate(stage, "home");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        if (databaseConnection != null) {
            databaseConnection.close();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
