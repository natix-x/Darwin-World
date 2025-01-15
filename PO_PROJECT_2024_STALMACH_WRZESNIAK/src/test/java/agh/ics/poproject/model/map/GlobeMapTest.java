package agh.ics.poproject.model.map;

import agh.ics.poproject.inheritance.Genome;
import agh.ics.poproject.model.Vector2d;
import agh.ics.poproject.model.elements.Animal;
import agh.ics.poproject.model.elements.Plant;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GlobeMapTest {

    @Test
    void correctlyPlaceDifferentElementsOnTheMap() throws IncorrectPositionException {
        // given
        GlobeMap worldMap = new GlobeMap(10, 10);
        Genome genome = new Genome(List.of(0, 5, 7, 4, 0, 1));
        Animal animal1 = new Animal(new Vector2d(2, 2), genome, 50);
        Animal animal2 = new Animal(new Vector2d(3, 5), genome, 50);
        Animal animal3 = new Animal(new Vector2d(4, 5), genome, 50);
        Animal animal4 = new Animal(new Vector2d(4, 9), genome, 50);
        Plant plant1 = new Plant(new Vector2d(4,7));
        Plant plant2 = new Plant(new Vector2d(5,7));
        Plant plant3 = new Plant(new Vector2d(2,7));
        // when
        worldMap.placeWorldElement(animal1);
        worldMap.placeWorldElement(animal2);
        worldMap.placeWorldElement(animal3);
        worldMap.placeWorldElement(animal4);
        worldMap.placeWorldElement(plant1);
        worldMap.placeWorldElement(plant2);
        worldMap.placeWorldElement(plant3);
        // then
        assertEquals(4, worldMap.getAnimals().size());
        assertEquals(3, worldMap.getPlants().size());
        assertEquals(animal1, worldMap.objectAt(new Vector2d(2,2)));
        assertEquals(plant1, worldMap.objectAt(new Vector2d(4,7)));
    }

    @Test
    void animalsCanOnlyBePlacedWithinBounds() {
        // given
        GlobeMap worldMap = new GlobeMap(10, 10);
        Genome genome = new Genome(List.of(0, 5, 7, 4, 0, 1));
        Animal animal1 = new Animal(new Vector2d(2, 2), genome, 50);
        Animal animal2 = new Animal(new Vector2d(11, 2), genome, 50);
        // when & then
        assertDoesNotThrow(() -> worldMap.placeWorldElement(animal1));
        assertThrows(IncorrectPositionException.class, () -> worldMap.placeWorldElement(animal2));
    }

    @Test
    void multipleAnimalsCanBePlacedOnTheSamePosition() throws IncorrectPositionException {
        // given
        GlobeMap worldMap = new GlobeMap(10, 10);
        Genome genome = new Genome(List.of(0, 5, 7, 4, 0, 1));
        Animal animal1 = new Animal(new Vector2d(2, 2), genome, 50);
        worldMap.placeWorldElement(animal1);
        Animal animal2 = new Animal(new Vector2d(2, 2), genome, 50);
        // when & then
        assertDoesNotThrow(() -> worldMap.placeWorldElement(animal2));
    }

    @Test
    void ifAnimalNotPlacedOnTheMapCannotBeMoved() {
        // given
        GlobeMap worldMap = new GlobeMap(10, 10);
        Genome genome = new Genome(List.of(0, 5, 7, 4, 0, 1));
        Animal animal1 = new Animal(new Vector2d(2, 2), genome, 50);
        // when & then
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> worldMap.move(animal1));
        assertEquals("Animal not found at position (2,2)", exception.getMessage());
    }

    @Test
    void twoPlantsCannotGrowOnTheSamePosition() {
        // given
        GlobeMap worldMap = new GlobeMap(10, 10);
        Plant plant1 = new Plant(new Vector2d(2, 3));
        Plant plant2 = new Plant(new Vector2d(2, 3));
        // when & then
        assertDoesNotThrow(() -> worldMap.placeWorldElement(plant1));
        assertThrows(IncorrectPositionException.class, () -> worldMap.placeWorldElement(plant2));
    }

    @Test
    void animalWithInBoundsMovesCorrectly() throws IncorrectPositionException {
        // given
        Vector2d animalPosition = new Vector2d(4, 2);
        Genome genome = new Genome(List.of(0, 5, 7, 4, 0, 1));
        int initialActiveGene = genome.getActiveGene();

        GlobeMap worldMap = new GlobeMap(8, 10);
        Animal animal1 = new Animal(animalPosition, genome, 50);
        MapDirection currentDirection = animal1.getDirection();
        worldMap.placeWorldElement(animal1);

        // when
        worldMap.move(animal1);

        // then
        MapDirection expectedDirection = currentDirection.rotate(initialActiveGene);
        Vector2d movement = expectedDirection.toUnitVector();
        Vector2d expectedPosition = animalPosition.add(movement);

        assertEquals(expectedPosition, animal1.getPosition());
    }
}