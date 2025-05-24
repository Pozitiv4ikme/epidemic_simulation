package org.simulation.services;

import org.simulation.people.AgeGroupImpact;
import org.simulation.people.HealthStatus;
import org.simulation.people.Person;

public class DeathService {
    private final ProbabilityService probabilityService;

    public DeathService(ProbabilityService probabilityService) {
        this.probabilityService = probabilityService;
    }

    public void evaluateDeath(Person person) {
        AgeGroupImpact personProfile = AgeGroupImpact.getProfileForAge(person.getAge());
        if(person.getHealthStatus() == HealthStatus.HEALTHY) {
            if (!probabilityService.happens(personProfile.getBaseMortalityChancePercent())) return;
        } else {
            if (!probabilityService.happens(personProfile.getBaseMortalityChancePercent() + person.getInfectedBy().getLethality())) return;
        }
        person.setHealthStatus(HealthStatus.DEAD);
    }
}
