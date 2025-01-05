package agh.ics.poproject.presenters;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeScreenPresenter {

    @FXML
    private Button startSimulationButton;

    @FXML
    public void onAddNewConfigClicked(ActionEvent actionEvent) throws IOException {
        Stage configStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("configuration.fxml"));
        BorderPane viewRoot = loader.load();
        ConfigurationPresenter presenter = new ConfigurationPresenter();

        configureStage(configStage, viewRoot);
        configStage.show();
    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("New configuration");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
}