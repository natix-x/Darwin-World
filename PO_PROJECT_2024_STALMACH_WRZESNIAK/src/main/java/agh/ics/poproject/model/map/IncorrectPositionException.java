package agh.ics.poproject.model.map;

import agh.ics.poproject.model.Vector2d;

public class IncorrectPositionException extends Exception {
    public IncorrectPositionException(Vector2d position) {
        super(position + " is not correct.");
    }
}
