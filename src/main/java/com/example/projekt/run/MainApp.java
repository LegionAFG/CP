package com.example.projekt.run;

import com.example.projekt.mysql.DatabaseConnection;
import com.example.projekt.service.NavigateService;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    private final NavigateService navigateService;
    private final DatabaseConnection databaseConnection;

    public MainApp() {
        this.databaseConnection = new DatabaseConnection();
        this.navigateService = new NavigateService();
    }

    @Override
    public void start(Stage stage) {
        try {

            databaseConnection.connectToDatabase();

            navigateService.navigate(stage, "home");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        databaseConnection.close();
    }

    public static void main(String[] args) {
        launch();
    }
}
