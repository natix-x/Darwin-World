package agh.ics.poproject.model.map;

import agh.ics.poproject.model.Vector2d;

import java.util.*;

public class ZyciodajneTruchla extends AbstractPlantGrowthMethod {

    protected Map<Vector2d, Integer> carcasses = worldMap.getCarcasses();
    protected Random random = new Random();

    public ZyciodajneTruchla(WorldMap worldMap) {
        super(worldMap);
    }

    /**
     * To place the plants in a carcass' area, neighbouring postions are calculated
     * @param position of the carcass
     * @return set of positions around the carcass
     */
    private Set<Vector2d> getNeighbouringPositions(Vector2d position) {
        Set<Vector2d> neighbouringPositions = new HashSet<>();
        Vector2d upperBound = worldMap.getCurrentBounds().UpperBound();
        Vector2d lowerBound = worldMap.getCurrentBounds().LowerBound();

        for (int x=-1; x<=1; x++) {
            for (int y=-1; y<=1; y++) {
                if (x == 0 && y == 0) {
                    neighbouringPositions.add(position);
                }
                Vector2d newNeighbour = position.add(new Vector2d(x, y));

                if (newNeighbour.precedes(upperBound) && newNeighbour.precedes(lowerBound)) {
                    neighbouringPositions.add(newNeighbour);
                }
            }
        }
        return neighbouringPositions;
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

    /**
     * Generates positions for plant placement
     * @return set of positions
     */
    private Set<Vector2d> generateFertilisedPositions() {
        Set<Vector2d> fertilisedPositions = new HashSet<>();
        Map.Entry<Vector2d, Integer> minEntry = null;

        //szukanie minimum bo Integer to priority trupa
        for (Map.Entry<Vector2d, Integer> entry : carcasses.entrySet()) {
            if (minEntry == null || entry.getValue() < minEntry.getValue()) {
                minEntry = entry;
            }
        }

        //update minimum
        if (minEntry != null) {
            Vector2d minPosition = minEntry.getKey();
            Set<Vector2d> neighbouringPositions = getNeighbouringPositions(minPosition);

            //iterate over neighbouring positions with 80% chance of generating a fertilised position
            for (Vector2d neighbour : neighbouringPositions) {
                if (random.nextDouble() <= 0.8) { // 80% chance
                    fertilisedPositions.add(neighbour);
                }
            }
        }
        return fertilisedPositions;
    }

    /**
     *
     * @param numberOfPlantsToGenerate passed and set via map configuration
     * @return
     */
    @Override
    public Set<Vector2d> generatePlantPositions(int numberOfPlantsToGenerate) {
        Set<Vector2d> positions = generateFertilisedPositions();

        // if there are more plants to generate, keep generating
        while (positions.size() < numberOfPlantsToGenerate) {
            positions.addAll(generateFertilisedPositions());
        }

        return positions;
    }

    @Override
    public Vector2d calculatePositionWithParetoPrinciple() {
        return null;
    }
}
