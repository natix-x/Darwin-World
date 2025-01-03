package agh.ics.poproject.model.elements;

import agh.ics.poproject.model.Vector2d;

public class Plant implements WorldElement {
    private final Vector2d position;

    public Plant(Vector2d plantPosition) {
        this.position = plantPosition;
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
