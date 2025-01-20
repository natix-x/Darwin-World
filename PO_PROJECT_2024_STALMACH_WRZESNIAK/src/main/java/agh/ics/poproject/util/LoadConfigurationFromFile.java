package agh.ics.poproject.util;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

// TODO: obsługa errorów - zły format, ma wiele lini itp, nie ma niektórych pól
public class LoadConfigurationFromFile {

    public Configuration loadToConfig(Path path) {
        try (Scanner scanner = new Scanner(path)) {
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] configSettings = line.split(",");
                if (configSettings.length != 20) {
                    throw new RuntimeException("Invalid configuration file. Expected 20 fields.");
                }

                int mapHeight = Integer.parseInt(configSettings[0].trim());
                int mapWidth = Integer.parseInt(configSettings[1].trim());
                int initialPlants = Integer.parseInt(configSettings[2].trim());
                int energyPerPlant = Integer.parseInt(configSettings[3].trim());
                int dailyPlantGrowth = Integer.parseInt(configSettings[4].trim());
                int initialAnimals = Integer.parseInt(configSettings[5].trim());
                int initialEnergy = Integer.parseInt(configSettings[6].trim());
                int neededEnergyForReproduction = Integer.parseInt(configSettings[7].trim());
                int reproductionEnergyLost = Integer.parseInt(configSettings[8].trim());
                int minMutations = Integer.parseInt(configSettings[9].trim());
                int maxMutations = Integer.parseInt(configSettings[10].trim());
                int genomeLength = Integer.parseInt(configSettings[11].trim());

                boolean isGlobeMap = Boolean.parseBoolean(configSettings[12].trim());
                boolean isForestedEquator = Boolean.parseBoolean(configSettings[13].trim());
                boolean isZyciodajneTruchla = Boolean.parseBoolean(configSettings[14].trim());
                boolean isFullRandomMutation = Boolean.parseBoolean(configSettings[15].trim());
                boolean isSlightCorrectionMutation = Boolean.parseBoolean(configSettings[16].trim());
                boolean isFullPredestination = Boolean.parseBoolean(configSettings[17].trim());

                boolean saveConfig = Boolean.parseBoolean(configSettings[18].trim());
                boolean saveStats = Boolean.parseBoolean(configSettings[19].trim());

                return new Configuration(
                        mapHeight, mapWidth, initialPlants, energyPerPlant, dailyPlantGrowth,
                        initialAnimals, initialEnergy, neededEnergyForReproduction, reproductionEnergyLost,
                        minMutations, maxMutations, genomeLength, isGlobeMap, isForestedEquator,
                        isZyciodajneTruchla, isFullRandomMutation, isSlightCorrectionMutation, isFullPredestination,
                        saveConfig, saveStats
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
