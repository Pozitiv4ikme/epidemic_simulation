package org.simulation.locations;

import org.simulation.MakeId;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Represents a location in the city with optional ID, name, type, and building area.
 */

public class Location implements MakeId {
    private Optional<Integer> id;
    private String name;
    private LocationType type;
    private int buildingArea;

    /**
     * Constructs a location with a specific ID, name, type, and building area.
     * @param id the unique identifier of the location
     * @param name the name of the location
     * @param type the type of location
     * @param buildingArea the size/area of the building
     */

    public Location(int id, String name, LocationType type, int buildingArea) {
        this.id = Optional.of(id);
        this.name = name;
        this.type = type;
        this.buildingArea = buildingArea;
    }

    /**
     * Constructs a location with a type only, without ID and zero building area.
     * @param type the type of location
     */

    public Location(LocationType type) {
        this.id = Optional.empty();
        this.type = type;
        this.name = type.getName();
        this.buildingArea = 0;
    }

    /**
     * Returns a symbol to display on the map, combining base symbol and optional ID.
     * @return map display symbol
     */

    public String getMapDisplaySymbol() {
        if(id.isPresent()) {
            return String.format("%c%d", type.getBaseDisplaySymbol(), id.get());
        } else {
            return String.format("%c", type.getBaseDisplaySymbol());
        }
    }

    /**
     * Returns the health impact of this location type.
     * @return location health impact
     */

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return buildingArea == location.buildingArea &&
                Objects.equals(id, location.id) &&
                Objects.equals(name, location.name) &&
                type == location.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, buildingArea);
    }

    @Override
    public String generateId() {
        return UUID.randomUUID().toString();
    }
}
