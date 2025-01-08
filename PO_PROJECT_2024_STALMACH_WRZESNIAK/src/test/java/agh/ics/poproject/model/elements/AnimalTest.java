package agh.ics.poproject.model.elements;

import agh.ics.poproject.inheritance.Genome;
import agh.ics.poproject.model.Vector2d;
import agh.ics.poproject.model.map.GlobeMap;
import agh.ics.poproject.model.map.MapDirection;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnimalTest {
    @Test
    void correctDirectionAfterPossibleMove() {
        // given
        Vector2d animalPosition = new Vector2d(2, 2);
        Genome genome = new Genome(List.of(0, 5, 7, 4, 0, 1));
        GlobeMap globe = new GlobeMap(8, 10);
        Animal animal1 = new Animal(animalPosition, genome, 50);
        MapDirection currentDirection = animal1.getDirection();
        // when
        animal1.rotateAndMove(0, globe);
        // then
        assertEquals(currentDirection, animal1.getDirection());
    }

    @Test
    void correctPositionAfterPossibleMove() {
        // given
        Vector2d animalPosition = new Vector2d(4, 2);
        Genome genome = new Genome(List.of(0, 5, 7, 4, 0, 1));
        int initialActiveGene = genome.getActiveGene();

        GlobeMap globe = new GlobeMap(8, 10);
        Animal animal1 = new Animal(animalPosition, genome, 50);
        MapDirection currentDirection = animal1.getDirection();

        // when
        animal1.rotateAndMove(initialActiveGene, globe);

        // then
        MapDirection expectedDirection = currentDirection.rotate(initialActiveGene);
        Vector2d movement = expectedDirection.toUnitVector();
        Vector2d expectedPosition = animalPosition.add(movement);

        assertEquals(expectedPosition, animal1.getPosition());
    }

    @Test
    void correctDirectionAfterImpossibleMove() {
        // given
        Vector2d animalPosition = new Vector2d(3, 2);
        Genome genome = new Genome(List.of(0, 5, 7, 4, 0, 1));

        GlobeMap globe = new GlobeMap(5, 10);
        Animal animal1 = new Animal(animalPosition, genome, 50);
        MapDirection currentDirection = animal1.getDirection();

        // when
        animal1.rotateAndMove(100, globe);
        // then
        assertEquals(currentDirection.opposite(), animal1.getDirection());
    }
}
