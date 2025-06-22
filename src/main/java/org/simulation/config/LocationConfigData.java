package org.simulation.config;

/**
 * Configuration data for a specific location type.
 * @param areaPercentage Percentage of the city's area this location type occupies.
 * @param amount         Number of locations of this type to be created.
 */

import org.simulation.exceptions.LocationConfigDataException;

public record LocationConfigData(double areaPercentage, int amount) {

    public LocationConfigData {

        // Validate that area percentage is between 0 and 100
        if (areaPercentage < 0 || areaPercentage > 100) {
            throw new LocationConfigDataException("Area percentage must be between 0 and 100.");
        }

        // Ensure the amount of locations is not negative
        if (amount < 0) {
            throw new LocationConfigDataException("Amount for location cannot be negative.");
        }
    }
}
