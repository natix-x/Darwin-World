package agh.ics.poproject.presenters;

import agh.ics.poproject.simulation.Simulation;
import agh.ics.poproject.model.MapChangeListener;
import agh.ics.poproject.model.Vector2d;
import agh.ics.poproject.model.map.GlobeMap;
import agh.ics.poproject.model.map.WorldMap;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class SimulationPresenter implements MapChangeListener {

    private static final double CELL_HEIGHT = 10.0;
    private static final double CELL_WIDTH = 10.0;

    @FXML
    private Button startButton;

    public Simulation simulation;
    public GlobeMap worldMap;

    @FXML
    private GridPane mapGrid;

    public void setSimulationParameters(Simulation simulation) {
        this.simulation = simulation;

        System.out.println("GlobeMap");
        this.worldMap = new GlobeMap(simulation.getConfig().mapWidth(), simulation.getConfig().mapHeight());
        this.simulation.setWorldMap(worldMap);
        drawMap();
    }

    private void drawMap() {
        clearMapGrid();

        Vector2d lowerBound = worldMap.getCurrentBounds().LowerBound();
        Vector2d upperBound = worldMap.getCurrentBounds().UpperBound();

        createNewMapGrid(lowerBound, upperBound);
        //String mapRepresentation = worldMap.toString();
        //fillTheMap(mapRepresentation);

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
        int width = Math.abs(lowerBound.getX() - upperBound.getX());
        int height = Math.abs(lowerBound.getY() - upperBound.getY());
        for (int column = 0; column < width; column++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        }
        for (int row = 0; row < height; row++) {
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
        }

        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                Label cell = new Label();
                cell.getStyleClass().add("cell");
                mapGrid.add(cell, column, row);
            }
        }
    }

    //TODO: przekleić reprezentację jako string do kodu żeby śmigało
    //TODO: coś się papra, do poprawy print
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
        Platform.runLater(this::drawMap);
    }

    @FXML
    public void startSimulation() {
        System.out.println("TUTAJ OZNACZA ZE DZIALA POLACZENIE PREZENTERA");

        setSimulationParameters(simulation);
        System.out.println(worldMap.getCurrentBounds().LowerBound());
        System.out.println(worldMap.getCurrentBounds().UpperBound());
    }
}