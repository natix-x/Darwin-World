package agh.ics.poproject.model.map;

import agh.ics.poproject.model.Vector2d;

import java.util.HashSet;
import java.util.Set;

public class ForestedEquator extends AbstractPlantGrowthMethod {

    int equatorBottom;
    int equatorTop;

    public ForestedEquator(WorldMap world) {
        super(world);
        calculateEquatorBorders();
    }

    @Override
    public Vector2d calculatePositionWithParetoPrinciple() {
        int x = random.nextInt(worldMap.getCurrentBounds().UpperBound().x());
        int y = calculateYCoordinate();
        return new Vector2d(x, y);
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

    /**
     * Positions within calculated bounds will be most desired by plants in defined growth method.
     */
    private void calculateEquatorBorders() {
        Vector2d upperBound = worldMap.getCurrentBounds().UpperBound();
        equatorBottom = (int) (upperBound.y() * 0.3);
        equatorTop = (int) (upperBound.y() * 0.7);
    }

    @Override
    public Set<Vector2d> getPreferredPositions() {
        Set<Vector2d> preferredPositions = new HashSet<>();
        Vector2d lowerBound = worldMap.getCurrentBounds().LowerBound();
        Vector2d upperBound = worldMap.getCurrentBounds().UpperBound();

        for (int y = equatorBottom; y <= equatorTop; y++) {
            for (int x = lowerBound.x(); x <= upperBound.x(); x++) {
                preferredPositions.add(new Vector2d(x, y));
            }
        }
        return preferredPositions;
    }
}
