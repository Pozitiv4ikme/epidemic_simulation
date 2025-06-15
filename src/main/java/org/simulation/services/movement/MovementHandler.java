package org.simulation.services.movement;

import org.simulation.city.City;
import org.simulation.people.Person;

import java.util.List;

/**
 * Handles the movement logic of all people in the city during each simulation epoch.
 */

public class MovementHandler {
    private final MovementService movementService;

    /**
     * Constructs a MovementHandler with the provided MovementService.
     * @param movementService the service responsible for executing individual movements
     */

    public MovementHandler(MovementService movementService) {
        this.movementService = movementService;
    }

    /**
     * Iterates over all people and moves each person a specified number of times,
     * using the movement logic defined in MovementService.
     * @param people list of people to move
     * @param city city environment in which the people are moving
     * @param movesPerEpoch number of movement attempts per person in one epoch
     */

    public void handleMovement(List<Person> people, City city, int movesPerEpoch) {
        for (Person person : people) {
            movementService.moving(person, city, movesPerEpoch);
        }
    }
}
