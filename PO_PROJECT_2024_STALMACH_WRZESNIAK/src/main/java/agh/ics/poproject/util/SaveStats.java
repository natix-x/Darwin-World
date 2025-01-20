package agh.ics.poproject.util;

import agh.ics.poproject.simulation.Simulation;
import agh.ics.poproject.simulation.statistics.Stats;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.UUID;

public class SaveStats {

    private Stats stats;
    private UUID simulationId;
    private Path savePath;

    public SaveStats(Stats stats, UUID simulationId) throws IOException {
        this.stats = stats;
        this.simulationId = simulationId;
        Files.createDirectories(Paths.get("simulations_stats/" + simulationId + "/"));
        savePath = Paths.get("simulations_stats/" + simulationId + "/" + "simulation_stats.csv");

    }
    public void saveDayStats() throws IOException {

        try (FileWriter writer = new FileWriter(savePath.toFile(), true)) {
            String[] values = {
                    String.valueOf(stats.countAnimalsNumber()),
                    String.valueOf(stats.countPlantsNumber()),
                    String.valueOf(stats.countNumberOfPositionsUnoccupiedByAnyAnimal()),
                    String.valueOf(stats.getMostPopularGenotype()),
                    String.valueOf(stats.countAverageEnergyOfAliveAnimals()),
                    String.valueOf(stats.countAverageAnimalLifeSpan()),
                    String.valueOf(stats.countAverageNumberOfChildrenForAliveAnimals())
            };
            writer.write(String.join(",", Arrays.toString(values) + "\n"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
