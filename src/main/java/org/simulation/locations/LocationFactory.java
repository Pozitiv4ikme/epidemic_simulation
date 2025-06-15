package org.simulation.locations;

import org.simulation.config.LocationConfigData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Factory class to generate a list of locations for the city based on configuration data.
 */

public class LocationFactory {

    /**
     * Generates locations for the city according to width, height, and initial data.
     * @param width the width of the city map
     * @param height the height of the city map
     * @param cityInitialData map with location types and their configuration
     * @return list of generated locations
     */

    public static List<Location> generateLocations(
            int width,
            int height,
            Map<LocationType, LocationConfigData> cityInitialData
    ) {
        List<Location> allLocations = new ArrayList<>();
        int totalArea = width * height;
        Map<LocationType, Integer> idCounters = new HashMap<>();

        for (Map.Entry<LocationType, LocationConfigData> entry : cityInitialData.entrySet()) {
            LocationType type = entry.getKey();
            LocationConfigData data = entry.getValue();
            int amount = data.amount();

            // Calculate area to allocate for each location typ
            double totalAreaForType = totalArea * (data.areaPercentage() / 100.0);

            // Calculate area per location, minimum 1
            int buildingAreaPerLocation = Math.max(1, (int) Math.round(totalAreaForType / amount));

            for (int i = 0; i < amount; i++) {
                int localId = idCounters.getOrDefault(type, 1);

                Location location = new Location(localId, type.getName(),type,buildingAreaPerLocation);
                allLocations.add(location);

                idCounters.put(type, localId + 1);
            }
        }

        return allLocations;
    }
}
