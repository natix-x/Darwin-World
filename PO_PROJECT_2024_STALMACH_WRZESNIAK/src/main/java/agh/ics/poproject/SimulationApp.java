package agh.ics.poproject;

import agh.ics.poproject.presenters.StartSimulationPresenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;


public class SimulationApp extends Application {
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("welcomeScreen.fxml"));
        BorderPane viewRoot = loader.load();
        StartSimulationPresenter presenter = new StartSimulationPresenter();

        configureStage(primaryStage, viewRoot);


        primaryStage.show();
    }
    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Welcome");
        primaryStage.setMaxWidth(900);
        primaryStage.setMaxHeight(600);
    }

}
