package agh.ics.poproject.util;

import agh.ics.poproject.simulation.statistics.Stats;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;

public class SaveStats {

    private final Stats stats;
    private final Path savePath;

    public SaveStats(Stats stats, UUID simulationId) throws IOException {
        this.stats = stats;
        Files.createDirectories(Paths.get("simulations_stats/" + simulationId + "/"));
        savePath = Paths.get("simulations_stats/" + simulationId + "/" + simulationId + "_stats.csv");
        if (!Files.exists(savePath)) {
            try (FileWriter writer = new FileWriter(savePath.toFile(), true)) {
                String headers = String.join(",", Arrays.asList(
                        "Animal Count",
                        "Plant Count",
                        "Unoccupied Positions",
                        "Most Popular Genotype",
                        "Average Energy",
                        "Average Life Span",
                        "Average Number of Children"
                ));
                writer.write(headers + "\n");
            }
        }
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
                    String.format(Locale.US, "%.2f", stats.countAverageNumberOfChildrenForAliveAnimals())
            };
            writer.write(String.join(",", values) + "\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
