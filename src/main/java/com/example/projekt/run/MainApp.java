package com.example.projekt.run;

import com.example.projekt.sql.AddClient;
import com.example.projekt.sql.DatabaseConnection;
import com.example.projekt.service.NavigateService;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    NavigateService navigateService;
    DatabaseConnection databaseConnection = new DatabaseConnection();
    AddClient addClient;

    public MainApp() {
        this.navigateService = new NavigateService();
        this.addClient = new AddClient(databaseConnection);
    }

    @Override
    public void start(Stage stage) {
        navigateService.navigate(stage, "home");
        databaseConnection.connectToDatabase();
        //Insert Test
        addClient.insertUser("2025", "Dmitry","Pogoreliy","1990-12-04","Male","Ukraine","Married");
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