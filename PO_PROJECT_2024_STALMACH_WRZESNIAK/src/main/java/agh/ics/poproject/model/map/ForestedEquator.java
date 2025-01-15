package agh.ics.poproject.model.map;

import agh.ics.poproject.model.Vector2d;
import agh.ics.poproject.model.elements.Plant;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

// TODO: refaktoryzacja, przeniesienie części kodu do AbstractPlantGrowingMethod
public class ForestedEquator extends AbstractPlantGrowingMethod {

    int equatorBottom;
    int equatorTop;

    public ForestedEquator(WorldMap world) {
        super(world);
        calculateEquatorBorders();
    }

    @Override
    public Set<Vector2d> generatePlantPositions(int numberOfPlantsToGenerate) {
        if (allPositionsOccupied()) {
            return Collections.emptySet();
        }

        Set<Vector2d> uniquePositions = new HashSet<>();
        long PositionsUnoccupiedByPlants = worldMap.calculateCurrentSurface() - numberOfPositionOccupiedByPlants();

        if (PositionsUnoccupiedByPlants < numberOfPlantsToGenerate) {
            numberOfPlantsToGenerate = (int) PositionsUnoccupiedByPlants;
        }

        while (uniquePositions.size() < numberOfPlantsToGenerate) {
            int x = random.nextInt(worldMap.getCurrentBounds().UpperBound().x());
            int y = calculateYCoordinate();

            Vector2d position = new Vector2d(x, y);
            if (! canGrowAt(position)) {
                continue;
            }
            uniquePositions.add(position);
            if (allPositionsOccupied()) {
                break;
            }
        }
        return uniquePositions;
    }

    private int calculateYCoordinate() {
        if (random.nextDouble() <= 0.8) {
            return random.nextInt(equatorTop - equatorBottom + 1) + equatorBottom;
        } else {
            int y;
            do {
                y = random.nextInt(worldMap.getCurrentBounds().UpperBound().y());
            } while (y > equatorTop && y < equatorBottom);
            return y;
        }
    }

        //for (Vector2d position : generatedPlantsRandomPositions) {
        //    Plant plant = new Plant(position);
        //}  -> ta część pójdzie do worldmapy -> do metody generate plants
        // i do dnia -> generateInitial oraz genereteplants (na koniec każdego dnia)


    // TODO: jak duży powinien być równik?
    // zakładam na razie dała 30%
    private void calculateEquatorBorders() {
        Vector2d upperBound = worldMap.getCurrentBounds().UpperBound();
        equatorBottom = (int) (upperBound.y() * 0.3);
        equatorTop = (int) (upperBound.y() * 0.7);
    }

    private boolean allPositionsOccupied() {
        return numberOfPositionOccupiedByPlants() >= worldMap.calculateCurrentSurface();
    }

    private long numberOfPositionOccupiedByPlants() {
        return worldMap.getElements().stream()
                .filter(element -> element instanceof Plant)
                .count();
    }
}
