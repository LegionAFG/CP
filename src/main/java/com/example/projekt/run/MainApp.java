package com.example.projekt.run;

import com.example.projekt.service.NavigateService;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    NavigateService navigateService;

    public MainApp(){
        this.navigateService = new NavigateService();
    }

    @Override
    public void start(Stage stage){
       navigateService.navigate(stage, "home");
    }

    public static void main(String[] args) {
        launch();
    }
}