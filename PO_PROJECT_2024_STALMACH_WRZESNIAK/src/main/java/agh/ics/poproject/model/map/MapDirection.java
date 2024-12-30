package agh.ics.poproject.model.map;

import agh.ics.poproject.model.Vector2d;

import java.util.Random;

public enum MapDirection {
    NORTH("N"), NORTHEAST("NE"), EAST("E"), SOUTHEAST("SE"), SOUTH("S"), SOUTHWEST("SW"), WEST("W"), NORTHWEST("NW");

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
            case NORTH -> "North";
            case NORTHEAST -> "Northeast";
            case EAST -> "East";
            case SOUTHEAST -> "Southeast";
            case SOUTH -> "South";
            case SOUTHWEST -> "Southwest";
            case WEST -> "West";
            case NORTHWEST -> "Northwest";
        };
    }

    public static MapDirection getRandomDirection() {
        return values()[new Random().nextInt(values().length)];
    }

    public MapDirection opposite() {
        return switch (this) {
            case NORTH -> SOUTH;
            case NORTHEAST -> SOUTHWEST;
            case EAST -> WEST;
            case SOUTHEAST -> NORTHWEST;
            case SOUTH -> NORTH;
            case SOUTHWEST -> NORTHEAST;
            case WEST -> EAST;
            case NORTHWEST -> SOUTHEAST;
        };
    }

    public MapDirection rotate(int steps) {
        return values()[(ordinal() + steps) % values().length];
    }

    public Vector2d toUnitVector() {
        return switch (this) {
            case NORTH -> new Vector2d(0, 1);
            case SOUTH -> new Vector2d(0, -1);
            case NORTHEAST -> new Vector2d(1, 1);
            case EAST -> new Vector2d(1,0);
            case WEST -> new Vector2d(-1, 0);
            case SOUTHEAST -> new Vector2d(1, -1);
            case SOUTHWEST -> new Vector2d(-1, -1);
            case NORTHWEST -> new Vector2d(-1, 1);
        };
    }


}
