package org.simulation.services.movement;

import org.simulation.city.City;
import org.simulation.people.Person;

import java.util.List;

public class MovementHandler {
    private final MovementService movementService;

    public MovementHandler(MovementService movementService) {
        this.movementService = movementService;
    }

    public void handleMovement(List<Person> people, City city, int movesPerEpoch) {
        for (Person person : people) {
            movementService.moving(person, city, movesPerEpoch);
        }
    }
}
