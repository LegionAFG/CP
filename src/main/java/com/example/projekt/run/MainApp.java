package com.example.projekt.run;

import com.example.projekt.sql.DatabaseConnection;
import com.example.projekt.service.NavigateService;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    NavigateService navigateService;
    DatabaseConnection databaseConnection = new DatabaseConnection();



    public MainApp() {
        this.navigateService = new NavigateService();
    }

    @Override
    public void start(Stage stage) {
        databaseConnection.connectToDatabase();
        navigateService.navigate(stage, "home");

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