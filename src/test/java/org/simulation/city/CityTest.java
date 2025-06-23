package org.simulation.city;

import org.junit.jupiter.api.Test;
import org.simulation.config.CityConfig;
import org.simulation.locations.Location;
import org.simulation.locations.LocationType;
import org.simulation.people.HealthStatus;
import org.simulation.people.Person;
import org.simulation.people.Position;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CityTest {
    City city = new City(new CityConfig(5, 5, 2, 0, Map.of()));
    Location location = new Location(LocationType.ROAD);
    Person healthy = new Person(10, HealthStatus.HEALTHY, new Position(1, 1), "P1");
    Person infected = new Person(20, HealthStatus.INFECTED, new Position(2, 2), "P2");

    @Test
    void testSetPeople() {
        // actual
        city.setPeople(List.of(healthy, infected));
        Map<HealthStatus, List<Person>> actual = city.getPeople();

        // expected
        Map<HealthStatus, List<Person>> expected = Map.of(
                HealthStatus.HEALTHY, List.of(healthy),
                HealthStatus.INFECTED, List.of(infected)
        );

        assertEquals(2, actual.size());
        assertTrue(actual.containsKey(HealthStatus.HEALTHY));
        assertTrue(actual.containsKey(HealthStatus.INFECTED));
        assertEquals(1, actual.get(HealthStatus.HEALTHY).size());
        assertEquals(1, actual.get(HealthStatus.INFECTED).size());
        assertEquals(expected, actual);
    }

    @Test
    void testDeletePersonFromMapRemovesPerson() {
        CityCell[][] map = city.getCityMap();
        for (int i = 0; i < city.getHeight(); i++) {
            for (int j = 0; j < city.getWidth(); j++) {
                map[i][j] = new CityCell(location);
            }
        }

        healthy.setPosition(new Position(1, 1));

        map[1][1].addPerson(healthy);
        assertEquals(1, map[1][1].getPeople().size());

        // actual
        city.deletePersonFromMap(healthy);

        // expected empty list
        assertTrue(map[1][1].getPeople().isEmpty());
    }
}
