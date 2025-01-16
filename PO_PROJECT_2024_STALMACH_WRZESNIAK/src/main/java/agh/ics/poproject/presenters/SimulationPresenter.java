package agh.ics.poproject.presenters;

import agh.ics.poproject.model.elements.Animal;
import agh.ics.poproject.model.elements.Plant;
import agh.ics.poproject.simulation.Simulation;
import agh.ics.poproject.model.MapChangeListener;
import agh.ics.poproject.model.Vector2d;
import agh.ics.poproject.model.map.WorldMap;
import agh.ics.poproject.statistics.Stats;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class SimulationPresenter implements MapChangeListener {

    // TODO: rozmiary komórek dostosowane do rozmiaru okna
    private static final double CELL_HEIGHT = 25.0;
    private static final double CELL_WIDTH = 25.0;

    @FXML
    private GridPane mapGrid;

    @FXML
    private Label numberOfAnimalsLabel;

    @FXML
    private Label numberOfPlantsLabel;

    @FXML
    private Label numberOfUnoccupiedPositions;

    @FXML
    private Label mostPopularGenomesLabel;

    @FXML
    private Label averageAnimalEnergyLabel;

    @FXML
    private Label averageAnimalLifespanLabel;

    @FXML
    private Label averageChildrenNumberLabel;

    @FXML
    private Button resumeButton;

    @FXML
    private Button stopButton;

    private Simulation simulation;
    private WorldMap worldMap;

    @FXML
    public void initialize() {
        resumeButton.setDisable(true);
    }

    public void setSimulationParameters(Simulation simulation) {
        this.simulation = simulation;

        System.out.println("GlobeMap");
        this.worldMap = simulation.getWorldMap();
        this.worldMap.subscribe(this);
        drawMap();
    }

    private void drawMap() {
        clearMapGrid();

        Vector2d lowerBound = worldMap.getCurrentBounds().LowerBound();
        Vector2d upperBound = worldMap.getCurrentBounds().UpperBound();

        createNewMapGrid(lowerBound, upperBound);
        fillTheMap(lowerBound, upperBound);

    }

    /**
    Clears space in the simulation window for printing the map
     */
    private void clearMapGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().getFirst());
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    private void createNewMapGrid(Vector2d lowerBound, Vector2d upperBound) {
        clearMapGrid();
        mapGrid.alignmentProperty();
        int width = Math.abs(lowerBound.x() - upperBound.x())-1;
        int height = Math.abs(lowerBound.y() - upperBound.y())-1;

        for (int column = 0; column < width; column++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        }
        for (int row = 0; row < height; row++) {
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
        }
    }

    private void fillTheMap(Vector2d lowerBound, Vector2d upperBound) {
        int width = Math.abs(lowerBound.x() - upperBound.x());
        int height = Math.abs(lowerBound.y() - upperBound.y());

        for (int column = 0; column <= width; column++) {
            for (int row = 0; row <= height; row++) {
                Vector2d position = new Vector2d(column, row);
                Label cellLabel = createCellLabel(position);
                mapGrid.add(cellLabel, column, row);
            }
        }
    }
    private Label createCellLabel(Vector2d position) {
        Label cellLabel = new Label();
        cellLabel.setMinSize(CELL_WIDTH, CELL_HEIGHT);
        cellLabel.setAlignment(Pos.CENTER);

        Object objectAtPosition = worldMap.objectAt(position);
        if (objectAtPosition != null) {
            if (objectAtPosition instanceof Animal) {
                cellLabel.getStyleClass().add("cell-animal");
            }
            else if (objectAtPosition instanceof Plant) {
                cellLabel.getStyleClass().add("cell-plant");
            }
            cellLabel.setText(objectAtPosition.toString());
        } else {
            cellLabel.getStyleClass().add("cell");
        }
        return cellLabel;
    }

    private void getCurrentStats() {
        Stats stats = simulation.getStats();
        numberOfAnimalsLabel.setText(String.valueOf(stats.countAnimalsNumber()));
        numberOfPlantsLabel.setText(String.valueOf(stats.countPlantsNumber()));
        numberOfUnoccupiedPositions.setText(String.valueOf(stats.countNumberOfPositionsUnoccupiedByAnyAnimal()));
        mostPopularGenomesLabel.setText(String.valueOf(stats.getMostPopularGenotype()));
        averageAnimalEnergyLabel.setText(String.valueOf(stats.countAverageEnergyOfAliveAnimals()));
        averageAnimalLifespanLabel.setText(String.valueOf(stats.countAverageAnimalLifeSpan()));
        averageChildrenNumberLabel.setText(String.valueOf(stats.countAverageNumberOfChildrenForAliveAnimals()));
    }


    @Override
    public void mapChange(WorldMap worldMap, String message) {
        Platform.runLater(() -> {
            drawMap();
            getCurrentStats();
        });
    }

    public void onStoppedClicked() {
        simulation.stop();
        stopButton.setDisable(true);
        resumeButton.setDisable(false);
    }

    public void onResumeClicked() {
        simulation.resume();
        resumeButton.setDisable(true);
        stopButton.setDisable(false);
    }
}