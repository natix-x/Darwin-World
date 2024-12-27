package agh.ics.poproject.model;



public class RectangularMap extends AbstractWorldMap {
    private static final Vector2d LOWER_BOUND = new Vector2d(0, 0);
    private final Vector2d upperBound;

    public RectangularMap(int width, int height) {
        this.upperBound = new Vector2d(width, height);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(LOWER_BOUND) && position.precedes(upperBound) && !isOccupied(position);
    }

    @Override
    public Boundary getCurrentBounds() {
        return new Boundary(LOWER_BOUND, upperBound);
    }
}
