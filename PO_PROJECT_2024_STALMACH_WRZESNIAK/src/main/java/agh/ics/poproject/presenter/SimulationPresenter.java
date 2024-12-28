package agh.ics.poproject.presenter;

import agh.ics.poproject.OptionsParser;
import agh.ics.poproject.Simulation;
import agh.ics.poproject.model.*;
import agh.ics.poproject.model.map.WorldMap;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;


import java.util.List;


public class SimulationPresenter implements MapChangeListener {

    private static final double CELL_WIDTH = 50.0;
    private static final double CELL_HEIGHT = 50.0;
    private static final List<Vector2d> POSITIONS = List.of(new Vector2d(2, 2), new Vector2d(3, 4));

    @FXML
    private GridPane mapGrid;

    @FXML
    private Label movesInformationLabel;

    @FXML
    private TextField movesListTestField;

    @FXML
    private Button startButton;


    private WorldMap worldMap;



    public void setWorldMap(WorldMap worldMap) {
        this.worldMap = worldMap;
        worldMap.subscribe(this);
        drawMap();
    }


    private void drawMap() {
        clearMapGrid();

        Vector2d lowerBound = worldMap.getCurrentBounds().LowerBound();
        Vector2d upperBound = worldMap.getCurrentBounds().UpperBound();

        createNewMapGrid(lowerBound, upperBound);
        String mapRepresentation = worldMap.toString();
        fillTheMap(mapRepresentation);

    }

    private void clearMapGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().getFirst());
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    private void createNewMapGrid(Vector2d lowerBound, Vector2d upperBound) {
        int width = Math.abs(lowerBound.getX() - upperBound.getX()) + 2;
        int height = Math.abs(lowerBound.getY() - upperBound.getY()) + 2;
        for (int column = 0; column < width; column++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        }
        for (int row = 0; row < height; row++) {
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
        }
    }

    private void fillTheMap(String mapRepresentation) {
        String[] lines = mapRepresentation.split("\n");

        int rowIndex = 0;


        for (String line : lines) {
            String[] cells;

            if (line.contains("y\\x")) {
                cells = line.split(" ");
            } else if ((line.contains("|"))) {
                cells = line.split("\\|");
            } else {
                continue;
            }

            int columnIndex = 0;

            for (String cell : cells) {
                if (!cell.isEmpty()) {
                    Label cellLabel = new Label(cell);
                    cellLabel.setMinSize(CELL_WIDTH, CELL_HEIGHT);
                    cellLabel.setAlignment(Pos.CENTER);
                    mapGrid.add(cellLabel, columnIndex, rowIndex);
                }
                columnIndex++;
            }
            rowIndex++;
        }
    }


    @Override
    public void mapChange(WorldMap worldMap, String message) {
        Platform.runLater(() -> {
            drawMap();
            movesInformationLabel.setText(message);
    });
    }

    @FXML
    public void onSimulationStartClicked(ActionEvent actionEvent) throws InterruptedException {
        List<MoveDirection> directions = OptionsParser.parseMoveDirection(getMovesInstructions());
        Simulation simulation = new Simulation(POSITIONS, directions, worldMap);
        SimulationEngine simulationEngine = new SimulationEngine(List.of(simulation));
        simulationEngine.runAsync();
    }


    private String[] getMovesInstructions() {
        return movesListTestField.getText().split(" ");
    }
}
