package agh.ics.poproject.model.map;

import agh.ics.poproject.model.Vector2d;

public record Boundary(
        Vector2d LowerBound,
        Vector2d UpperBound
) {
}
