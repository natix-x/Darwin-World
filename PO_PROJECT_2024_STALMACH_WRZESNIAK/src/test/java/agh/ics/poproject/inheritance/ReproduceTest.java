package agh.ics.poproject.inheritance;

import agh.ics.poproject.model.Vector2d;
import agh.ics.poproject.model.elements.Animal;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReproduceTest {
    int energyNeededToReproduce = 20;;
    ArrayList<Integer> genomeList1 = new ArrayList<>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8));
    ArrayList<Integer> genomeList2 = new ArrayList<>(List.of(8, 7, 6, 5, 4, 3, 2, 1));
    Animal animal1 = new Animal(new Vector2d(1, 1), new Genome(genomeList1), 100);
    Animal animal2 = new Animal(new Vector2d(1, 1), new Genome(genomeList2), 100);

    Animal animal3 = new Animal(new Vector2d(1, 1), new Genome(genomeList1), 10);
    Animal animal4 = new Animal(new Vector2d(1, 1), new Genome(genomeList2), 11);

    Reproduce reproduce = new Reproduce();

    @Test
    void canAnimalsReproduceCorrectly() {
        assertTrue(reproduce.canReproduce(animal1, animal2, energyNeededToReproduce));
        assertFalse(reproduce.canReproduce(animal3, animal4, energyNeededToReproduce));
    }

//    @Test
//    void doesReproductionWork() {
//        Animal baby = reproduce.reproduce(animal1, animal2);
//        System.out.println(baby.getGenome());
//    }

}