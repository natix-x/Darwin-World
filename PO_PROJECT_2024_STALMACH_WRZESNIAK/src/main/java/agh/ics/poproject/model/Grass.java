package agh.ics.poproject.model;

public class Grass implements WorldElement {
    private final Vector2d position;

    public Grass(Vector2d grassPosition) {
        this.position = grassPosition;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "*";
    }
}
