package agh.ics.poproject.model;

public class IncorrectPositionException extends Exception {
    public IncorrectPositionException(Vector2d position) {
        super(position + " is not correct.");
    }
}
