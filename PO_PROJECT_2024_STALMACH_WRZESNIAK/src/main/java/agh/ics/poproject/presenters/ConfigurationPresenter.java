package agh.ics.poproject.presenters;

import agh.ics.poproject.presenters.SimulationPresenter;
import agh.ics.poproject.util.Configuration;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ConfigurationPresenter {

    @FXML
    public Button startSimulationButton;

    @FXML
    private Spinner<Integer> lengthOfGenome;

    @FXML
    private RadioButton fullPredestinationButton;

    @FXML
    private RadioButton globeMapButton;

    @FXML
    private RadioButton noSaveConfigButton;

    @FXML
    private RadioButton yesSaveConfigButton;

    @FXML
    private RadioButton slightCorrectionButton;

    @FXML
    private RadioButton fullRandomButton;

    @FXML
    private Spinner<Integer> maxNumberOfMutationsSpinner;

    @FXML
    private Spinner<Integer> minNumberOfMutationsSpinner;

    @FXML
    private Spinner<Integer> reproductionEnergyLostSpinner;

    @FXML
    private Spinner<Integer> neededEnergyForReproductionSpinner;

    @FXML
    private Spinner<Integer> initialEnergyOfAnimalsSpinner;

    @FXML
    private Spinner<Integer> initialNumberOfAnimalsSpinner;

    @FXML
    private Spinner<Integer> initialNumberOfPlantsSpinner;

    @FXML
    private Spinner<Integer> energyPerPlantsSpinner;

    @FXML
    private Spinner<Integer> dailyPlantGrowthSpinner;

    @FXML
    private RadioButton forestedEquatorButton;

    @FXML
    private RadioButton zyciodajneTruchlaButton;

    @FXML
    private Spinner<Integer> mapWidthSpinner;

    @FXML
    private Spinner<Integer> mapHeightSpinner;

    @FXML
    public void initialize() {
        // TODO: jakie ograniczenia nałożyć na poszczególne spinnery? Jakie mogą przyjmować wartości?
        mapHeightSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(3, Integer.MAX_VALUE, 20));
        mapWidthSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, Integer.MAX_VALUE, 30));
        initialNumberOfPlantsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(3, Integer.MAX_VALUE, 10));
        energyPerPlantsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, Integer.MAX_VALUE, 15));
        dailyPlantGrowthSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(3, Integer.MAX_VALUE, 4));
        initialNumberOfAnimalsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(8, Integer.MAX_VALUE, 10));
        initialEnergyOfAnimalsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(30, Integer.MAX_VALUE, 40));
        neededEnergyForReproductionSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(30, Integer.MAX_VALUE, 40));
        reproductionEnergyLostSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, Integer.MAX_VALUE, 20));
        minNumberOfMutationsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 1));
        maxNumberOfMutationsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 2));
        // TODO: min musi być mniejsze od max i max musi byc tyle co liczba genow
        lengthOfGenome.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, Integer.MAX_VALUE, 7));

        ToggleGroup mapVariant = new ToggleGroup();  // in case any other variant adds
        globeMapButton.setToggleGroup(mapVariant);

        ToggleGroup plantGrowthVariant = new ToggleGroup();
        forestedEquatorButton.setToggleGroup(plantGrowthVariant);
        zyciodajneTruchlaButton.setToggleGroup(plantGrowthVariant);

        ToggleGroup mutationVariant = new ToggleGroup();
        fullRandomButton.setToggleGroup(mutationVariant);
        slightCorrectionButton.setToggleGroup(mutationVariant);

        ToggleGroup animalBehaviourVariant = new ToggleGroup();  // in case any other variant adds
        fullPredestinationButton.setToggleGroup(animalBehaviourVariant);

        ToggleGroup saveConfig = new ToggleGroup();
        yesSaveConfigButton.setToggleGroup(saveConfig);
        noSaveConfigButton.setToggleGroup(saveConfig);
    }


    @FXML
    public void startSimulationOnClick() throws IOException {
        // Fetch values from the UI
        int mapHeight = mapHeightSpinner.getValue();
        int mapWidth = mapWidthSpinner.getValue();
        int initialPlants = initialNumberOfPlantsSpinner.getValue();
        int energyPerPlant = energyPerPlantsSpinner.getValue();
        int dailyPlantGrowth = dailyPlantGrowthSpinner.getValue();
        int initialAnimals = initialNumberOfAnimalsSpinner.getValue();
        int initialEnergy = initialEnergyOfAnimalsSpinner.getValue();
        int neededEnergyForReproduction = neededEnergyForReproductionSpinner.getValue();
        int reproductionEnergyLost = reproductionEnergyLostSpinner.getValue();
        int minMutations = minNumberOfMutationsSpinner.getValue();
        int maxMutations = maxNumberOfMutationsSpinner.getValue();
        int genomeLength = lengthOfGenome.getValue();

        boolean isGlobeMap = globeMapButton.isSelected();
        boolean isForestedEquator = forestedEquatorButton.isSelected();
        boolean isZyciodajneTruchla = zyciodajneTruchlaButton.isSelected();
        boolean isFullRandomMutation = fullRandomButton.isSelected();
        boolean isSlightCorrectionMutation = slightCorrectionButton.isSelected();
        boolean isFullPredestination = fullPredestinationButton.isSelected();
        boolean saveConfig = yesSaveConfigButton.isSelected();

        Configuration configuration = new Configuration(
                mapHeight, mapWidth, initialPlants, energyPerPlant, dailyPlantGrowth,
                initialAnimals, initialEnergy, neededEnergyForReproduction, reproductionEnergyLost,
                minMutations, maxMutations, genomeLength, isGlobeMap, isForestedEquator, isZyciodajneTruchla,
                isFullRandomMutation, isSlightCorrectionMutation, isFullPredestination, saveConfig
        );

        showSimulationWindow(configuration);

        System.out.println("Simulation config:");
        System.out.println(configuration);
    }

    private void showSimulationWindow(Configuration configuration) throws IOException {
        Stage configStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = loader.load();

        SimulationPresenter presenter = new SimulationPresenter();
        presenter.setConfiguration(configuration);

        configureStage(configStage, viewRoot);
        configStage.show();
    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }



}
