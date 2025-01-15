package agh.ics.poproject.model.map;

import agh.ics.poproject.model.Vector2d;
import agh.ics.poproject.model.elements.Plant;
import agh.ics.poproject.model.elements.WorldElement;

import java.util.List;
import java.util.Random;

public abstract class AbstractPlantGrowingMethod implements PlantGrowingMethod {
    protected WorldMap worldMap;
    protected Random random;

    AbstractPlantGrowingMethod(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    @Override
    public boolean canGrowAt(Vector2d position) {
        List<WorldElement> elements = worldMap.getElements();
        for (WorldElement element : elements) {
            if (element instanceof Plant) {
                if (element.getPosition().equals(position)) {
                    return false;
                }
            }
        }
        return true;
    }
}
