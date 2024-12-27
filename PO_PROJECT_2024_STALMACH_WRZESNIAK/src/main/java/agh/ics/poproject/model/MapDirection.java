package agh.ics.poproject.model;

public enum MapDirection {
    NORTH("N"), EAST("E"), SOUTH("S"), WEST("W");

    private final String direction;

    MapDirection(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return switch (this) {
            case NORTH -> "North";  // English names preferred
            case EAST -> "East";
            case SOUTH -> "South";
            case WEST -> "West";
        };
    }
    public MapDirection next() {
        return values()[(this.ordinal() + 1) % values().length];
    }

    public MapDirection previous() {
        return values()[(ordinal() - 1  + values().length) % values().length];
    }

    public Vector2d toUnitVector() {
        return switch (this) {
            case NORTH -> new Vector2d(0, 1);
            case SOUTH -> new Vector2d(0, -1);
            case EAST -> new Vector2d(1,0);
            case WEST -> new Vector2d(-1, 0);
        };
    }


}
