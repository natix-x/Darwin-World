package agh.ics.poproject.model.map;

import agh.ics.poproject.model.IncorrectPositionException;
import agh.ics.poproject.model.MapChangeListener;
import agh.ics.poproject.model.Vector2d;
import agh.ics.poproject.model.elements.Animal;
import agh.ics.poproject.model.util.MapVisualizer;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {

    private final Map<Vector2d, Animal> animals = new HashMap<>();
    private final List<MapChangeListener> mapChangeListeners = new ArrayList<>();
    private final UUID mapID = UUID.randomUUID();

    @Override
    public void subscribe(MapChangeListener listener) {
        mapChangeListeners.add(listener);
    }

    public void unsubscribe(MapChangeListener listener) {
        mapChangeListeners.remove(listener);
    }

    private void mapChanged(String message){
        mapChangeListeners.forEach(listener -> listener.mapChange(this, message));
    }

    @Override
    public void place(Animal animal) throws IncorrectPositionException {
        Vector2d position = animal.getPosition();
        if (canMoveTo(position)) {
            animals.put(position, animal);
            mapChanged("Animal placed at position " + position);
        }
        else
            throw new IncorrectPositionException(position);
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {
        Vector2d currentPosition = animal.getPosition();
        if(Objects.equals(objectAt(currentPosition), animal)) {
            this.animals.remove(currentPosition);
            animal.move(direction, this);
            this.animals.put(animal.getPosition(), animal);
            mapChanged("Animal moved to the position " + animal.getPosition());
        }
        else {
            throw new IllegalArgumentException("Animal not found at position " + currentPosition);
        };
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        return this.animals.get(position);
    }

    @Override
    public List<WorldElement> getElements() {;
        List<WorldElement> elements = new ArrayList<>();
        for (Map.Entry<Vector2d, Animal> entry : animals.entrySet()) {
            elements.add(entry.getValue());}
        return elements;
    }

    @Override
    public abstract Boundary getCurrentBounds();

    @Override
    public String toString() {
        MapVisualizer map = new MapVisualizer(this);
        Boundary currentBounds = getCurrentBounds();
        return map.draw(currentBounds.LowerBound(), currentBounds.UpperBound());
    }

    @Override
    public UUID getId() {
        return mapID;
    }

}
