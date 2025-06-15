package org.simulation.services.death;

import org.simulation.people.HealthStatus;
import org.simulation.people.Person;

import java.util.List;

/**
 * A handler that processes death evaluations for a group of people.
 */

public class DeathHandler {
    private final DeathService deathService;

    /**
     * Constructs the DeathHandler with a given death evaluation service.
     * @param deathService the service used to evaluate death for individuals
     */

    public DeathHandler(DeathService deathService) {
        this.deathService = deathService;
    }

    /**
     * Evaluates which people in the given list have died and counts new deaths.
     * @param people the list of people to evaluate
     * @return the number of new deaths that occurred
     */

    public int handleDeath(List<Person> people) {
        int newDeath = 0;
        for (Person person: people) {
            deathService.evaluateDeath(person);
            if (person.getHealthStatus()==HealthStatus.DEAD) {
                newDeath++;
            }
        }
        return newDeath;
    }
}
