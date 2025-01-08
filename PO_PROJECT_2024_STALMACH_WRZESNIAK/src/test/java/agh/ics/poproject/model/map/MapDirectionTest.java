package agh.ics.poproject.model.map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapDirectionTest {

    @Test
    void RotateCorrectly() {
        assertEquals(MapDirection.NORTH, MapDirection.NORTH.rotate(0));
        assertEquals(MapDirection.NORTHEAST, MapDirection.NORTH.rotate(1));
        assertEquals(MapDirection.EAST, MapDirection.NORTH.rotate(2));
        assertEquals(MapDirection.SOUTHEAST, MapDirection.NORTH.rotate(3));
        assertEquals(MapDirection.SOUTH, MapDirection.NORTH.rotate(4));
        assertEquals(MapDirection.SOUTHWEST, MapDirection.NORTH.rotate(5));
        assertEquals(MapDirection.WEST, MapDirection.NORTH.rotate(6));
        assertEquals(MapDirection.NORTHWEST, MapDirection.NORTH.rotate(7));
    }
}