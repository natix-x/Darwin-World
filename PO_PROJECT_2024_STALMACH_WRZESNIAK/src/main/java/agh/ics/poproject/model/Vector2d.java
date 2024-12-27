package agh.ics.poproject.model;

import java.util.Objects;

public class Vector2d {
    private final int x;  // totally unchangeable
    private final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("(%d,%d)", x, y);
    }

    public boolean precedes(Vector2d other) {
        return x <= other.x && y <= other.y;
    }
    public boolean follows(Vector2d other) {
        return x >= other.x && y >= other.y;
    }
    public Vector2d add(Vector2d other) {
        return new Vector2d(x + other.x, y + other.y);
    }
    public Vector2d subtract(Vector2d other) {
        return new Vector2d(x - other.x, y - other.y);
    }
    public Vector2d upperRight(Vector2d other) {
        int newX = Math.max(this.x, other.x);
        int newY = Math.max(this.y, other.y);

        return new Vector2d(newX, newY);

    }
    public Vector2d lowerLeft(Vector2d other) {
        int newX = Math.min(this.x, other.x);
        int newY = Math.min(this.y, other.y);

        return new Vector2d(newX, newY);
    }
    public Vector2d opposite() {
        return new Vector2d(-x, -y);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2d vector2d = (Vector2d) o;
        return x == vector2d.x && y == vector2d.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
