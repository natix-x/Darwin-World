package agh.ics.poproject.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// TODO - obsługa błędów
public class SaveConfigurationToFile {

    public void saveConfig(Configuration config, File file) {

        try (FileWriter writer = new FileWriter(file)) {
            String[] values = {
                    String.valueOf(config.mapHeight()),
                    String.valueOf(config.mapWidth()),
                    String.valueOf(config.initialPlants()),
                    String.valueOf(config.energyPerPlant()),
                    String.valueOf(config.dailyPlantGrowth()),
                    String.valueOf(config.initialAnimals()),
                    String.valueOf(config.initialEnergy()),
                    String.valueOf(config.neededEnergyForReproduction()),
                    String.valueOf(config.reproductionEnergyLost()),
                    String.valueOf(config.minMutations()),
                    String.valueOf(config.maxMutations()),
                    String.valueOf(config.genomeLength()),
                    String.valueOf(config.corpseDecompositionTime()),
                    String.valueOf(config.isGlobeMap()),
                    String.valueOf(config.isForestedEquator()),
                    String.valueOf(config.isCemetery()),
                    String.valueOf(config.isFullRandomMutation()),
                    String.valueOf(config.isSlightCorrectionMutation()),
                    String.valueOf(config.isFullPredestination()),
                    String.valueOf(config.saveStats()),
                    String.valueOf(config.simulationSpeed())

            };

            writer.write(String.join(",", values));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
