package agh.ics.poproject;

import agh.ics.poproject.presenters.SimulationPresenter;
import agh.ics.poproject.simulation.Simulation;
import agh.ics.poproject.util.Configuration;
import agh.ics.poproject.util.LoadConfigurationFromFile;
import agh.ics.poproject.util.SimulationEngine;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SetApp extends Application {

    private final LoadConfigurationFromFile loadConfiguration = new LoadConfigurationFromFile();

    @FXML
    private Button startSimulationButton;
    ArrayList<Configuration> configurationList = new ArrayList<>();

    public void initialize() {
        startSimulationButton.setDisable(true);
    }

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

        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Shutting down the application.");
            System.exit(0);
        });
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

    @FXML
    public void startConfigStage(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SetApp.class.getClassLoader().getResource("configuration.fxml"));
        BorderPane viewRoot = loader.load();
        setNewStage(viewRoot, "New Configuration");
    }

    public static SimulationPresenter startSimulationStage(Simulation simulation, SimulationEngine engine) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SetApp.class.getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = loader.load();

        Stage stage = new Stage();
        var scene = new Scene(viewRoot);
        stage.setScene(scene);
        stage.setTitle("New Simulation");

        stage.setOnCloseRequest(k -> {
            System.out.println("Shutting down simulation, id: " + simulation.getWorldMap().getId());
            simulation.stop();
            stage.close();
        });

        stage.show();

        return loader.getController();

    }

    public void loadFileWithConfig(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter fileExtension = new FileChooser.ExtensionFilter("Select your file(*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(fileExtension);
        File configFile = fileChooser.showOpenDialog(new Stage());
        if (configFile != null) {
            Configuration configuration = loadConfiguration.loadToConfig(configFile.toPath());
            startSimulationButton.setDisable(false);
            this.configurationList.add(configuration);
        }
    }

    public void startSimulation() throws IOException {
        if (!configurationList.isEmpty()) {
            Configuration configuration = configurationList.getLast();
            Simulation simulation = new Simulation(configuration);

            SimulationEngine engine = new SimulationEngine(List.of(simulation)); //engine jest run
            engine.runAsync();

            SimulationPresenter presenter = startSimulationStage(simulation, engine);
            presenter.setSimulationParameters(simulation);
        }
    }

}


