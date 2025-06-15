package org.simulation.services.death;

import org.simulation.people.HealthStatus;
import org.simulation.people.Person;

import java.util.List;

public class DeathHandler {
    private final DeathService deathService;

    public DeathHandler(DeathService deathService) {
        this.deathService = deathService;
    }

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
