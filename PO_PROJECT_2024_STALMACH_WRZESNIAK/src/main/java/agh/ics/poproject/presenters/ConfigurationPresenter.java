package agh.ics.poproject.presenters;

import agh.ics.poproject.SetApp;
import agh.ics.poproject.simulation.Simulation;
import agh.ics.poproject.util.Configuration;
import agh.ics.poproject.util.SaveConfigurationToFile;
import agh.ics.poproject.util.SimulationEngine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigurationPresenter {

    @FXML
    public Spinner<Integer> corpseDecompositionSpinner;
    @FXML
    public Spinner<Integer> simulationSpeedSpinner;
    @FXML
    private RadioButton yesSaveStatsButton;
    @FXML
    public RadioButton noSaveStatsButton;
    @FXML
    public Button startSimulationButton;
    @FXML
    private Spinner<Integer> lengthOfGenome;
    @FXML
    private RadioButton fullPredestinationButton;
    @FXML
    private RadioButton globeMapButton;
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

    ArrayList<Configuration> configurationList = new ArrayList<>();

    @FXML
    public void initialize() {
        // TODO: jakie ograniczenia nałożyć na poszczególne spinnery? Jakie mogą przyjmować wartości?
        mapHeightSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(3, Integer.MAX_VALUE, 13));
        mapWidthSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, Integer.MAX_VALUE, 15));
        initialNumberOfPlantsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 10));
        energyPerPlantsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, Integer.MAX_VALUE, 15));
        dailyPlantGrowthSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(3, Integer.MAX_VALUE, 4));
        initialNumberOfAnimalsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 2));
        initialEnergyOfAnimalsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(30, Integer.MAX_VALUE, 40));
        neededEnergyForReproductionSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(30, Integer.MAX_VALUE, 40));
        reproductionEnergyLostSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, Integer.MAX_VALUE, 20));
        minNumberOfMutationsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 1));
        maxNumberOfMutationsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 2));
        // TODO: min musi być mniejsze od max i max musi byc tyle co liczba genow
        lengthOfGenome.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 7));

        ToggleGroup mapVariant = new ToggleGroup();  // in case any other variant adds
        globeMapButton.setToggleGroup(mapVariant);

        ToggleGroup plantGrowthVariant = new ToggleGroup();
        forestedEquatorButton.setToggleGroup(plantGrowthVariant);
        zyciodajneTruchlaButton.setToggleGroup(plantGrowthVariant);

        corpseDecompositionSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, Integer.MAX_VALUE, 15));

        ToggleGroup mutationVariant = new ToggleGroup();
        fullRandomButton.setToggleGroup(mutationVariant);
        slightCorrectionButton.setToggleGroup(mutationVariant);

        ToggleGroup animalBehaviourVariant = new ToggleGroup();  // in case any other variant adds
        fullPredestinationButton.setToggleGroup(animalBehaviourVariant);

        simulationSpeedSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(200, Integer.MAX_VALUE, 1500));

        ToggleGroup saveStats = new ToggleGroup();
        yesSaveStatsButton.setToggleGroup(saveStats);
        noSaveStatsButton.setToggleGroup(saveStats);

    }


    @FXML
    public void startSimulationOnClick() throws IOException { //save config params
        // Fetch values from the UI
        Configuration newConfiguration = getConfigurationFromUser();

        this.configurationList.add(newConfiguration);

        System.out.println("Simulation config:");
        System.out.println(newConfiguration);

        startSimulation();
    }

    private Configuration getConfigurationFromUser() {
        int mapHeight = mapHeightSpinner.getValue() - 1;
        int mapWidth = mapWidthSpinner.getValue() - 1;
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
        int corpseDecomposition = corpseDecompositionSpinner.getValue();

        boolean isGlobeMap = globeMapButton.isSelected();
        boolean isForestedEquator = forestedEquatorButton.isSelected();
        boolean isZyciodajneTruchla = zyciodajneTruchlaButton.isSelected();
        boolean isFullRandomMutation = fullRandomButton.isSelected();
        boolean isSlightCorrectionMutation = slightCorrectionButton.isSelected();
        boolean isFullPredestination = fullPredestinationButton.isSelected();
        boolean saveStats = yesSaveStatsButton.isSelected();

        int simulationSpeed = simulationSpeedSpinner.getValue();

        return new Configuration(
                mapHeight, mapWidth, initialPlants, energyPerPlant, dailyPlantGrowth,
                initialAnimals, initialEnergy, neededEnergyForReproduction, reproductionEnergyLost,
                minMutations, maxMutations, genomeLength, corpseDecomposition, isGlobeMap, isForestedEquator, isZyciodajneTruchla,
                isFullRandomMutation, isSlightCorrectionMutation, isFullPredestination, saveStats, simulationSpeed
        );
    }

    /**
    Sets up the UI and the engine.
    Gets the most recent config and concurrently runs the Simulation and the SimulationUI.
     */
    public void startSimulation() throws IOException {
        if (!configurationList.isEmpty()) {
            Configuration configuration = configurationList.getLast();
            Simulation simulation = new Simulation(configuration);

            SimulationEngine engine = new SimulationEngine(List.of(simulation)); //engine jest run
            engine.runAsync();

            SimulationPresenter presenter = SetApp.startSimulationStage(simulation, engine);
            presenter.setSimulationParameters(simulation);
        }
    }

    public void saveConfigOnClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save configuration");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Save your file (*.csv)", "*.csv*"));
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            SaveConfigurationToFile saveConfig = new SaveConfigurationToFile();
            saveConfig.saveConfig(getConfigurationFromUser(), file);
        }

    }
}
