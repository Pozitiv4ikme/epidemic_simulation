package org.simulation.locations;

import org.simulation.config.LocationConfigData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationFactory {
    public static List<Location> generateLocations(
            int width,
            int height,
            Map<LocationType, LocationConfigData> config
    ) {
        List<Location> allLocations = new ArrayList<>();
        int totalArea = width * height;
        Map<LocationType, Integer> idCounters = new HashMap<>();

        for (Map.Entry<LocationType, LocationConfigData> entry : config.entrySet()) {
            LocationType type = entry.getKey();
            LocationConfigData data = entry.getValue();
            int amount = data.amount();

            double totalAreaForType = totalArea * (data.areaPercentage() / 100.0);
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
