package org.simulation.config;

import org.simulation.exceptions.CityConfigException;
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

    // Validates the provided configuration values
    public void validate() {

        // Check if all values are empty or null
        if (width == 0 && height == 0 && population == 0 && infectedPercentage == 0 && locationsData == null) {
            throw new CityConfigException("City configuration cannot be empty.");
        }

        // Check if the city is 1x1, which is invalid
        if (width == 1 && height == 1) {
            throw new CityConfigException("Width and height cannot be equal 1.");
        }

        // Ensure width and height are positive
        if (width <= 0 || height <= 0) {
            throw new CityConfigException("Width and height must be positive integers.");
        }

        // Ensure population is positive
        if (population <= 0) {
            throw new CityConfigException("Population must be a positive integer.");
        }

        // Check that infected percentage is within valid range
        if (infectedPercentage <= 0 || infectedPercentage > 100) {
            throw new CityConfigException("Infected percentage must be greater than 0 and less than 100.");
        }

        // Ensure that location data is not empty
        if (locationsData.isEmpty()) {
            throw new CityConfigException("Locations data cannot be empty.");
        }
    }
}
