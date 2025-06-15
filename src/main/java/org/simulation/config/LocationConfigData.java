package org.simulation.config;

/**
 * Configuration data for a specific location type.
 * @param areaPercentage Percentage of the city's area this location type occupies.
 * @param amount         Number of locations of this type to be created.
 */

public record LocationConfigData(double areaPercentage, int amount) {
}
