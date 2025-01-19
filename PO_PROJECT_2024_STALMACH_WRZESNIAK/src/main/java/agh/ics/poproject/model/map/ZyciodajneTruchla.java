package agh.ics.poproject.model.map;

import agh.ics.poproject.model.Vector2d;

import java.util.*;

public class ZyciodajneTruchla extends AbstractPlantGrowthMethod {

    protected Map<Vector2d, Integer> carcasses;
    protected Random random = new Random();

    public ZyciodajneTruchla(WorldMap worldMap) {
        super(worldMap);
        this.carcasses = worldMap.getCarcasses();
    }

    @Override
    public Vector2d calculatePositionWithParetoPrinciple() {
        List<Map.Entry<Vector2d, Integer>> sortedCarcasses = new ArrayList<>(carcasses.entrySet());
        sortedCarcasses.sort(Comparator.comparingInt(Map.Entry::getValue));

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
                if (x == 0 && y == 0) continue; // Skip the central position itself.

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


    /**
     * To prioritise recently deceased carcasses, priority is set. With each simulation day the carcass
     * on which a plant hasn't grown yet has less of a priority. Those older than 5 days old are removed from
     * the priority list.
     *
     * @param carcasses map of deceased animals with their priorities
     */
    public void changeCarcassPriority(Map<Vector2d, Integer> carcasses) {
        Iterator<Map.Entry<Vector2d, Integer>> iterator = carcasses.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Vector2d, Integer> entry = iterator.next();
            if (entry.getValue() > 5) {
                iterator.remove();
            } else {
                carcasses.put(entry.getKey(), entry.getValue() + 1);
            }
        }
    }
}

