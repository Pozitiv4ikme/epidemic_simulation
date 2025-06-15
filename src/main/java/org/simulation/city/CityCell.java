package org.simulation.city;

import org.simulation.locations.Location;
import org.simulation.people.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single cell in the city map.
 */

public class CityCell {
    private final Location location;
    private final List<Person> people = new ArrayList<>();

    /**
     * Creates a CityCell with the specified location.
     * @param location location assigned to this cell
     */

    public CityCell(Location location) {
        this.location = location;
    }

    /**
     * Returns the location of this city cell.
     * @return location of the cell
     */

    public Location getLocation() {
        return location;
    }

    /**
     * Returns the list of people currently in this city cell.
     * @return list of people in the cell
     */

    public List<Person> getPeople() {
        return people;
    }

    /**
     * Removes a person from this city cell.
     * @param person person to remove
     */

    public void removePerson(Person person) {
        people.remove(person);
    }

    @Override
    public String toString() {
        return location.toString();
    }
}
