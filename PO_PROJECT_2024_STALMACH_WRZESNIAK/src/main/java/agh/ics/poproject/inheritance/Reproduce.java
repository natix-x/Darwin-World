package agh.ics.poproject.inheritance;

import agh.ics.poproject.model.elements.Animal;

import java.util.ArrayList;
import java.util.List;

public class Reproduce {

    int energyNeededToReproduce;

    public Reproduce(int energyNeededToReproduce) {
        this.energyNeededToReproduce = energyNeededToReproduce;
    }

    public boolean canReproduce(Animal animal1, Animal animal2) {
        return animal1.getRemainingEnergy() >= energyNeededToReproduce && animal2.getRemainingEnergy() >= energyNeededToReproduce;
    }

//    public Genome shuffleGenome(Animal animal1, Animal animal2) {
//        ArrayList<Integer> babyGenome;
//
//        Genome gene1 = animal1.getGenome();
//        Genome gene2 = animal2.getGenome();
//
//        double energy1 = animal1.getRemainingEnergy();
//        double energy2 = animal2.getRemainingEnergy();
//
//        double divisionRatio = Math.floor((energy1 / (energy1 + energy2) * 100) * 8 / 100);
//        for (int i = 0; i < divisionRatio; i++) {
//            if (energy1 > energy2) {
//
//            }
//        }
//
//
//    };

    public Animal reproduce(Animal animal1, Animal animal2) {
        if (canReproduce(animal1, animal2)) {
            animal1.changeEnergy(-energyNeededToReproduce);
            animal2.changeEnergy(-energyNeededToReproduce);

//            Animal baby = new Animal(animal1.getPosition(), Genome)
//            return baby;
            return animal1;
        }
        return animal1;
    }

}
