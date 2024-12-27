package agh.ics.poproject;

import agh.ics.poproject.model.MoveDirection;

import java.util.LinkedList;
import java.util.List;

public class OptionsParser {
    public static List<MoveDirection> parseMoveDirection(String[] args) {
        List<MoveDirection> moveDirections = new LinkedList<>();

        for (String arg : args) {
            MoveDirection direction = switch (arg) {
                case "f" -> MoveDirection.FORWARD;
                case "b" -> MoveDirection.BACKWARD;
                case "r" -> MoveDirection.RIGHT;
                case "l" -> MoveDirection.LEFT;
                default -> throw new IllegalArgumentException(arg + " is not a valid move specification");
            };
            moveDirections.add(direction);
        }

        return moveDirections;
    }
}