package org.simulation.config;

import org.simulation.locations.LocationType;

import java.util.Map;

public record CityConfig(int width, int height, int population, int infectedPercentage,
                         Map<LocationType, LocationConfigData> locationsData) {
}
