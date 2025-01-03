//package agh.ics.poproject.model.elements;
//
//import agh.ics.poproject.inheritance.Genome;
//import agh.ics.poproject.model.Vector2d;
//import agh.ics.poproject.model.map.GlobeMap;
//import agh.ics.poproject.model.map.MapDirection;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class AnimalTest {
//    @Test
//    void correctDirectionAfterPossibleMove() {
//        // given
//        Vector2d animalPosition = new Vector2d(2, 2);
//        Genome genome = new Genome(List.of(0, 5, 7, 4, 0, 1));
//        GlobeMap globe = new GlobeMap(8, 10);
//        Animal animal1 = new Animal(animalPosition, genome, 50);
//        MapDirection currentDirection = animal1.getDirection();
//        // when
//        animal1.rotateAndMove(0, globe);
//        // then
//        assertEquals(currentDirection, animal1.getDirection());
//    }
//
//    // TODO LATER
//    @Test
//    void correctDirectionBeforePossibleMove() {
//        // given
//        Vector2d animalPosition = new Vector2d(3, 3);
//        Genome genome = new Genome(List.of(0, 5, 7, 4, 0, 1));
//        GlobeMap globe = new GlobeMap(3, 4);
//        Animal animal1 = new Animal(animalPosition, genome, 50);
//        MapDirection currentDirection = animal1.getDirection();
//        // when
//        animal1.rotateAndMove(0, globe);
//        // then
//        assertEquals(currentDirection, animal1.getDirection());
//    }
//}