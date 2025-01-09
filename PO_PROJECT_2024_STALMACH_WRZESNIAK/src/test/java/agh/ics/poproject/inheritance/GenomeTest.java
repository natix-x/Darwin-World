package agh.ics.poproject.inheritance;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GenomeTest {
    @Test
    void correctGenomeGenerationOfGeneratedAnimals() {
        // given
        int genesNumber = 8;
        // when
        Genome genome = new Genome(genesNumber);
        // then
        assertEquals(8, genome.getGenesSequence().size());
        for (Integer gene : genome.getGenesSequence() ) {
            assertTrue(gene >= 0 && gene <= 7);
        }
    }

    @Test
    void correctGenomeGenerationOfReproducedAnimals() {
        // given
        List<Integer> genomeFromParents = List.of(0,5,7,4,0,1);
        // when
        Genome genome = new Genome(genomeFromParents);
        // then
        assertEquals(6, genome.getGenesSequence().size());
        for (Integer gene : genome.getGenesSequence() ) {
            assertTrue(gene >= 0 && gene <= 7);
        }
    }
}