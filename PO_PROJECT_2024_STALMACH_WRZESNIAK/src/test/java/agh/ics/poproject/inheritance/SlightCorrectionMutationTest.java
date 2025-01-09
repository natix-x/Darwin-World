package agh.ics.poproject.inheritance;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


class SlightCorrectionMutationTest {

    @Test
    void mutateGeneCorrectly() {
        // given
        SlightCorrectionMutation mutationMethod = new SlightCorrectionMutation(3, 5);
        // when
        int mutatedGene1 = mutationMethod.mutateGene(0);
        int mutatedGene2 = mutationMethod.mutateGene(1);
        int mutatedGene3 = mutationMethod.mutateGene(2);
        int mutatedGene4 = mutationMethod.mutateGene(3);
        int mutatedGene5 = mutationMethod.mutateGene(4);
        int mutatedGene6 = mutationMethod.mutateGene(5);
        int mutatedGene7 = mutationMethod.mutateGene(6);
        int mutatedGene8 = mutationMethod.mutateGene(7);
        // then
        assertTrue(List.of(7, 1).contains(mutatedGene1));
        assertTrue(List.of(0, 2).contains(mutatedGene2));
        assertTrue(List.of(1, 3).contains(mutatedGene3));
        assertTrue(List.of(2, 4).contains(mutatedGene4));
        assertTrue(List.of(3, 5).contains(mutatedGene5));
        assertTrue(List.of(4, 6).contains(mutatedGene6));
        assertTrue(List.of(5, 7).contains(mutatedGene7));
        assertTrue(List.of(6, 0).contains(mutatedGene8));
    }
}