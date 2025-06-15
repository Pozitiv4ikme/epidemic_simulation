package org.simulation.services.death;

import org.simulation.people.AgeGroupImpact;
import org.simulation.people.HealthStatus;
import org.simulation.people.Person;
import org.simulation.services.ProbabilityService;

/**
 * A service responsible for evaluating whether a person dies
 * based on age-related base mortality and virus lethality.
 */

public class DeathService {
    private final ProbabilityService probabilityService;

    /**
     * Constructs the DeathService with a given probability service.
     * @param probabilityService the probability service used to evaluate random events
     */

    public DeathService(ProbabilityService probabilityService) {
        this.probabilityService = probabilityService;
    }

    /**
     * Evaluates whether a person dies based on their health status and infection.
     * If the person is healthy, only base mortality chance applies.
     * If infected, virus lethality is added to the base mortality chance.
     * @param person the person whose death is being evaluated
     */

    public void evaluateDeath(Person person) {
        AgeGroupImpact personProfile = AgeGroupImpact.getProfileForAge(person.getAge());
        if(person.getHealthStatus() == HealthStatus.HEALTHY) {
            if (!probabilityService.happens(personProfile.getBaseMortalityChancePercent())) return;
        } else {
            if (!probabilityService.happens(personProfile.getBaseMortalityChancePercent()
                    + person.getInfectedBy().get().getLethality())) return;
        }
        person.setHealthStatus(HealthStatus.DEAD);
    }
}
