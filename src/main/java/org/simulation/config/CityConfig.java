package org.simulation.config;

import org.simulation.locations.LocationType;

import java.util.Map;

/**
 * Configuration record for initializing a City.
 * @param width             Width of the city map
 * @param height            Height of the city map
 * @param population        Total number of people in the city
 * @param infectedPercentage Percentage of initially infected people
 * @param locationsData     Configuration for location types within the city
 */

public record CityConfig(int width, int height, int population, int infectedPercentage,
                         Map<LocationType, LocationConfigData> locationsData) {
}
