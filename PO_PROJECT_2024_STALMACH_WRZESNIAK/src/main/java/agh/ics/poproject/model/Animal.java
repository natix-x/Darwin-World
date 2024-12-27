package agh.ics.poproject.model;

public class Animal implements WorldElement {
    private MapDirection direction;
    private Vector2d position;


    public Animal() {
        this(new Vector2d(2, 2));
    }

    public Animal(Vector2d position) {
        this.direction = MapDirection.NORTH;
        this.position = position;
    }

    public MapDirection getDirection() {
        return direction;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return direction.getDirection();
    }

    void move(MoveDirection direction, MoveValidator validator) {
        Vector2d newPosition = this.position;
        switch (direction) {
            case RIGHT -> this.direction = this.direction.next();
            case LEFT -> this.direction = this.direction.previous();
            case FORWARD -> newPosition = this.position.add(this.direction.toUnitVector());
            case BACKWARD -> newPosition = this.position.subtract(this.direction.toUnitVector());
        }
        if (validator.canMoveTo(newPosition)) {

            this.position = newPosition;
        }
    }
}
