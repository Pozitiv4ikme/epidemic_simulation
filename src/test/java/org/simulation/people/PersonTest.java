package org.simulation.people;

import org.junit.jupiter.api.Test;
import org.simulation.virus.Virus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonTest {
    Person person = new Person(89, HealthStatus.HEALTHY, new Position(0, 0), "TestPerson");

    @Test
    void testMove() {
        // actual
        Position position = new Position(1, 1);
        person.move(position);

        // expected
        Person expectedPerson = new Person(89, HealthStatus.HEALTHY, position, "TestPerson");

        assertEquals(expectedPerson.getPosition(), person.getPosition());
    }

    @Test
    void testSetHealthStatus() {
        // actual
        person.setHealthStatus(HealthStatus.INFECTED);

        // expected
        Person expectedPerson = new Person(89, HealthStatus.INFECTED, new Position(0, 0), "TestPerson");

        assertEquals(expectedPerson.getHealthStatus(), person.getHealthStatus());
    }

    @Test
    void testSetInfectedBy() {
        // actual
        Virus virus = new Virus(10, 1, 5, 25);
        person.setInfectedBy(Optional.of(virus));

        // expected
        Person expectedPerson = new Person(89, HealthStatus.HEALTHY, new Position(0, 0), virus, "TestPerson");

        assertEquals(expectedPerson.getInfectedBy(), person.getInfectedBy());
    }
}
