package org.simulation.locations;

public class Location {
    private int id;
    private String name;
    private LocationType type;
    private int buildingArea;

    public Location(int id, String name, LocationType type, int buildingArea) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.buildingArea = buildingArea;
    }

    public String getMapDisplaySymbol() {
        return String.format("%c%d", type.getBaseDisplaySymbol(), id);
    }

    public LocationHealthImpact getLocationHealthImpact() {
        if(!type.hasHealthImpact()) {
           return LocationHealthImpact.NONE;
        }
        return LocationHealthImpact.getImpactForLocationType(this.type);
    }

    public int getId() {
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
