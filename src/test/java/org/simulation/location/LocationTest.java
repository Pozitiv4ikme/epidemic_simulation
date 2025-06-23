package org.simulation.location;

import org.junit.jupiter.api.Test;
import org.simulation.locations.Location;
import org.simulation.locations.LocationType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationTest {

    Location locationWithoutID = new Location(LocationType.MEDICAL_CENTRE);
    Location locationWithID = new Location(1, "Hospital", LocationType.MEDICAL_CENTRE, 100);

    @Test
    void testGetMapDisplaySymbolWithoutId() {
        String expectedSymbol = "M";

        assertEquals(expectedSymbol, locationWithoutID.getMapDisplaySymbol());
    }

    @Test
    void testGetMapDisplaySymbolWithId() {
        String expectedSymbol = "M1";

        assertEquals(expectedSymbol, locationWithID.getMapDisplaySymbol());
    }
}
