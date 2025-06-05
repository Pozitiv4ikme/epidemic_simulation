package org.simulation.locations;

import java.util.Optional;

public class Location {
    private Optional<Integer> id;
    private String name;
    private LocationType type;
    private int buildingArea;

    public Location(int id, String name, LocationType type, int buildingArea) {
        this.id = Optional.of(id);
        this.name = name;
        this.type = type;
        this.buildingArea = buildingArea;
    }

    public Location(LocationType type) {
        this.id = Optional.empty();
        this.type = type;
        this.name = type.getName();
        this.buildingArea = 0;
    }

    public String getMapDisplaySymbol() {
        if(id.isPresent()) {
            return String.format("%c%d", type.getBaseDisplaySymbol(), id.get());
        } else {
            return String.format("%c", type.getBaseDisplaySymbol());
        }
    }

    public LocationHealthImpact getLocationHealthImpact() {
        if(!type.hasHealthImpact()) {
           return LocationHealthImpact.NONE;
        }
        return LocationHealthImpact.getImpactForLocationType(this.type);
    }

    public Optional<Integer> getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocationType getType() {
        return type;
    }

    @Override
    public String toString() {
        return getMapDisplaySymbol();
    }

    public int getBuildingArea() {
        return buildingArea;
    }
}
