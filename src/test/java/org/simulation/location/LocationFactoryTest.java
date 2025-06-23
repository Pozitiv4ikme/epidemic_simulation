package org.simulation.location;

import org.junit.jupiter.api.Test;
import org.simulation.config.LocationConfigData;
import org.simulation.locations.Location;
import org.simulation.locations.LocationFactory;
import org.simulation.locations.LocationType;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationFactoryTest {

    int width = 10;
    int height = 10;
    Map<LocationType, LocationConfigData> cityInitialData = Map.of(
        LocationType.WORKPLACE, new LocationConfigData(5, 3),
            LocationType.ROAD, new LocationConfigData(10, 2)
    );

    Location road1 = new Location(1, "Road", LocationType.ROAD, 5);
    Location road2 = new Location(2, "Road", LocationType.ROAD, 5);

    Location workplace1 = new Location(1, "Workplace", LocationType.WORKPLACE, 2);
    Location workplace2 = new Location(2, "Workplace", LocationType.WORKPLACE, 2);
    Location workplace3 = new Location(3, "Workplace", LocationType.WORKPLACE, 2);

    @Test
    void testGenerateLocations() {
        List<Location> allLocationsExpected = List.of(workplace1, workplace2, workplace3, road1, road2);

        List<Location> allLocationsActual = LocationFactory.generateLocations(width, height, cityInitialData);

        assertEquals(new HashSet<>(allLocationsExpected), new HashSet<>(allLocationsActual));
    }
}
