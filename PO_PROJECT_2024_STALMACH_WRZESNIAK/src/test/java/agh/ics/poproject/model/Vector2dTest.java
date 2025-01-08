package agh.ics.poproject.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2dTest {


    @Test
    void ChangeToString() {
        // given
        Vector2d vector = new Vector2d(1, 2);
        // when
        String vectorStringRepresentation = vector.toString();
        // then
        assertEquals("(1,2)", vector.toString());
    }

    @Test
    void precedesIfXAndYOfOtherVectorAreGreater() {
        // given
        Vector2d vector1 = new Vector2d(1, 3);
        Vector2d vector2 = new Vector2d(2, 4);
        // when
        boolean precedes = vector1.precedes(vector2);
        // then
        assertTrue(precedes);
    }

    @Test
    void doNotPrecedesIfYOfOtherVectorAreSmaller() {
        // given
        Vector2d vector1 = new Vector2d(2, 4);
        Vector2d vector2 = new Vector2d(1, 3);
        // when
        boolean notPrecedes = vector1.precedes(vector2);
        // then
        assertFalse(notPrecedes);
    }

    @Test
    void doNotPrecedesIfOnlyOneCoordinateIsGrater() {
        // given
        Vector2d vector1 = new Vector2d(1, 3);
        Vector2d vector2 = new Vector2d(2, 2);
        // when
        boolean notPrecedes = vector1.precedes(vector2);
        // then
        assertFalse(notPrecedes);
    }

    @Test
    void followsIfXAndYOfOtherVectorAreSmaller() {
        // given
        Vector2d vector1 = new Vector2d(2, 4);
        Vector2d vector2 = new Vector2d(1, 3);
        // when
        boolean follows = vector1.follows(vector2);
        // then
        assertTrue(follows);
    }

    @Test
    void doNotFollowsIfYOfOtherVectorAreGrater() {
        // given
        Vector2d vector1 = new Vector2d(1, 3);
        Vector2d vector2 = new Vector2d(2, 4);
        // when
        boolean notFollows = vector1.follows(vector2);
        // then
        assertFalse(notFollows);
    }

    @Test
    void doNotFollowsIfOnlyOneCoordinateIsSmaller() {
        // given
        Vector2d vector1 = new Vector2d(1, 4);
        Vector2d vector2 = new Vector2d(2, 3);
        // when
        boolean notFollows = vector1.follows(vector2);
        // then
        assertFalse(notFollows);
    }

    @Test
    void addTwoVectors() {
        // given
        Vector2d vector1 = new Vector2d(1, 2);
        Vector2d vector2 = new Vector2d(3, 4);
        // when
        Vector2d vector3 = vector1.add(vector2);
        // then
        assertEquals(new Vector2d(4, 6), vector3);
    }

    @Test
    void subtractTwoVectors() {
        // given
        Vector2d vector1 = new Vector2d(1, 2);
        Vector2d vector2 = new Vector2d(3, 4);
        // when
        Vector2d vector3 = vector1.subtract(vector2);
        // then
        assertEquals(new Vector2d(-2, -2), vector3);
    }

    @Test
    void returnsUpperRightVector() {
        // given
        Vector2d vector1 = new Vector2d(1, 4);
        Vector2d vector2 = new Vector2d(2, 3);
        // when
        Vector2d vector3 = vector1.upperRight(vector2);
        // then
        assertEquals(new Vector2d(2, 4), vector3);
    }

    @Test
    void returnsLowerLeftVector() {
        // given
        Vector2d vector1 = new Vector2d(1, 4);
        Vector2d vector2 = new Vector2d(2, 3);
        // when
        Vector2d vector3 = vector1.lowerLeft(vector2);
        // then
        assertEquals(new Vector2d(1, 3), vector3);
    }

    @Test
    void getOppositeVector() {
        // given
        Vector2d vector1 = new Vector2d(1, 2);
        // when
        Vector2d oppositeVector = vector1.opposite();
        // then
        assertEquals(new Vector2d(-1, -2), oppositeVector);
    }

    @Test
    void twoVectorsEqual() {
        // given
        Vector2d vector1 = new Vector2d(1, 2);
        Vector2d vector2 = new Vector2d(1, 2);
        // when
        boolean isEquals = vector1.equals(vector2);
        // then
        assertTrue(isEquals);
    }

    @Test
    void twoVectorsNotEqual() {
        // given
        Vector2d vector1 = new Vector2d(1, 2);
        Vector2d vector2 = new Vector2d(1, 3);
        // when
        boolean isNotEquals = vector1.equals(vector2);
        // then
        assertFalse(isNotEquals);
    }

    @Test
    void twoVectorsNotEqualIfOneIsNull() {
        // given
        Vector2d vector1 = new Vector2d(1, 2);
        Vector2d vector2 = null;
        // when
        boolean isNotEquals = vector1.equals(vector2);
        // them
        assertFalse(isNotEquals);
    }
}

