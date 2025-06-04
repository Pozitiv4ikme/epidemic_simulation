package org.simulation.city;

import org.simulation.locations.Location;
import org.simulation.people.Person;

import java.util.ArrayList;
import java.util.List;

public class CityCell {
    private final Location location;
    private final List<Person> people = new ArrayList<>();

    public CityCell(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void addPerson(Person person) {
        people.add(person);
    }

    public void removePerson(Person person) {
        people.remove(person);
    }

    @Override
    public String toString() {
        return location.toString();
    }
}
