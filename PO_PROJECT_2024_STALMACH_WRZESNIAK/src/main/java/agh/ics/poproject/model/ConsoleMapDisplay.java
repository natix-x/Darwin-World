package agh.ics.poproject.model;

public class ConsoleMapDisplay implements MapChangeListener {
    private int actualizationCounter = 0;

    @Override
    public synchronized void mapChange(WorldMap worldMap, String message) {
        System.out.println(message);
        System.out.println(worldMap);
        actualizationCounter++;
        System.out.println("Number of messages received: " + actualizationCounter);
        System.out.println();
        System.out.println("Map ID: " + worldMap.getId());
        System.out.println();
    }
}
