package agh.ics.poproject;

import agh.ics.poproject.presenters.ConfigurationPresenter;
import agh.ics.poproject.presenters.SimulationPresenter;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;


public class SetApp extends Application {
    //he he SetApp(Up) :))
    public void start(Stage primaryStage) throws IOException {
        //initialise application
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("welcomeScreen.fxml"));
        BorderPane viewRoot = loader.load();

        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Welcome");
        primaryStage.setMaxWidth(900);
        primaryStage.setMaxHeight(600);

        primaryStage.show();
    }

    private static void setNewStage(BorderPane viewRoot, String title) {
        Stage stage = new Stage();
        var scene = new Scene(viewRoot);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.minWidthProperty().bind(viewRoot.minWidthProperty());
        stage.minHeightProperty().bind(viewRoot.minHeightProperty());

        stage.show();
    }

    // TODO: to comment xd
    @FXML
    public void startConfigStage(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SetApp.class.getClassLoader().getResource("configuration.fxml"));
        BorderPane viewRoot = loader.load();
        setNewStage(viewRoot, "New Configuration");
    }

    public static SimulationPresenter startSimulationStage() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SetApp.class.getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = loader.load();
        setNewStage(viewRoot, "New Simulation");
        return loader.getController();
    }
}


