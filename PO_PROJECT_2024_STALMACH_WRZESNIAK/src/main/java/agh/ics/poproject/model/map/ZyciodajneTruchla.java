package agh.ics.poproject.model.map;

import agh.ics.poproject.model.Vector2d;
import agh.ics.poproject.simulation.Carcasses;

import java.util.*;

public class ZyciodajneTruchla extends AbstractPlantGrowthMethod {

    private Carcasses carcasses;

    public ZyciodajneTruchla(WorldMap worldMap, Carcasses carcasses) {
        super(worldMap);
        this.carcasses = carcasses;
        carcasses.subscribe(this);
    }

    @Override
    public Vector2d calculatePositionWithParetoPrinciple() {
        List<Map.Entry<Vector2d, Integer>> sortedCarcasses = new ArrayList<>(carcasses.getCarcasses().entrySet());
        sortedCarcasses.sort(Comparator.comparingInt(Map.Entry::getValue));
        System.out.println(carcasses.getCarcasses());
        for (Map.Entry<Vector2d, Integer> entry : sortedCarcasses) {
            Vector2d carcassPosition = entry.getKey();

            // Attempt to find a neighboring position with an 80% chance.
            Optional<Vector2d> neighbouringPosition = getRandomNeighbouringPosition(carcassPosition);

            if (neighbouringPosition.isPresent() && canGrowAt(neighbouringPosition.get())) {
                return neighbouringPosition.get();
            }
        }

        // Step 3: If all prioritized carcasses are exhausted, fallback to random generation.
        return generateRandomPosition();
    }

    /**
     * To place the plants in a carcass' area, neighbouring postions are calculated with 80% of choosing one from the
     * nearest area.
     *
     * @param position of the carcass
     * @return position of a plant to be placed
     */
    private Optional<Vector2d> getRandomNeighbouringPosition(Vector2d position) {
        Set<Vector2d> neighbouringPositions = getNeighbouringPositions(position);

        List<Vector2d> shuffledNeighbours = new ArrayList<>(neighbouringPositions);
        Collections.shuffle(shuffledNeighbours);

        if (!shuffledNeighbours.isEmpty() && random.nextDouble() <= 0.8) {
            return Optional.of(shuffledNeighbours.getFirst());
        }

        return Optional.empty();
    }

    private Set<Vector2d> getNeighbouringPositions(Vector2d position) {
        Set<Vector2d> neighbouringPositions = new HashSet<>();
        Vector2d upperBound = worldMap.getCurrentBounds().UpperBound();
        Vector2d lowerBound = worldMap.getCurrentBounds().LowerBound();

        // Generate all valid neighboring positions.
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0) {
                    neighbouringPositions.add(position);
                };
                Vector2d newNeighbour = position.add(new Vector2d(x, y));
                if (newNeighbour.follows(lowerBound) && newNeighbour.precedes(upperBound)) {
                    neighbouringPositions.add(newNeighbour);
                }
            }
        }
        return neighbouringPositions;
    }

    private Vector2d generateRandomPosition() {
        Vector2d upperBound = worldMap.getCurrentBounds().UpperBound();
        Vector2d lowerBound = worldMap.getCurrentBounds().LowerBound();
        
        int x = random.nextInt(upperBound.x() - lowerBound.x() + 1) + lowerBound.x();
        int y = random.nextInt(upperBound.y() - lowerBound.y() + 1) + lowerBound.y();

        return new Vector2d(x, y);
    }

    public void updateCarcasses(Carcasses carcasses) {
        this.carcasses = carcasses;
    }
}

