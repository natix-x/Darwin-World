package agh.ics.poproject;

import agh.ics.poproject.model.*;

import java.util.ArrayList;
import java.util.List;

public class World {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("System started.");

        String[] moves = {"f", "l", "r", "b", "f", "l", "r", "b", "f", "l", "r", "b", "f", "l", "r", "b"};
        List<MoveDirection> directions = OptionsParser.parseMoveDirection(moves);
        List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,4));
        List<Simulation> simulationsList = new ArrayList<>();
        ConsoleMapDisplay consoleMapDisplay = new ConsoleMapDisplay();

        for(int i = 0; i < 1000; i++) {
            RectangularMap rectangularMap = new RectangularMap(10,10);
            rectangularMap.subscribe(consoleMapDisplay);
            simulationsList.add(new Simulation(positions, directions, rectangularMap));
        }

        SimulationEngine simulations = new SimulationEngine(simulationsList);
        simulations.runAsyncInThreadPool();

        System.out.println("System ended.");
    }

    public static void run(List<MoveDirection> directions) {
        for (MoveDirection direction : directions) {
            String message = switch (direction) {
                case FORWARD -> "Animal is going forward.";
                case BACKWARD -> "Animal is going backward.";
                case RIGHT -> "Animal is turning right.";
                case LEFT -> "Animal is turning left.";
            };
            System.out.println(message);
        }
    }

}