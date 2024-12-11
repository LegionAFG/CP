package com.example.projekt.run;

import com.example.projekt.mysql.DatabaseConnection;
import com.example.projekt.service.NavigateService;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class MainApp extends Application {

    Logger logger = Logger.getLogger(MainApp.class.getName());

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
            logger.severe("Home seite konnte nicht geladen werden");
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
