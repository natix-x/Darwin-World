package agh.ics.poproject.presenters;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class NewConfigurationPresenter {

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
}
