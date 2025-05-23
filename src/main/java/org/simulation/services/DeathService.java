package org.simulation.services;

import org.simulation.people.AgeGroupImpact;
import org.simulation.people.HealthStatus;
import org.simulation.people.Person;
import org.simulation.virus.AgeGroupVirusImpact;

public class DeathService {
    private final ProbabilityService probabilityService;

    public DeathService(ProbabilityService probabilityService) {
        this.probabilityService = probabilityService;
    }

    public boolean evaluateDeath(Person person) {
        int age = person.getAge();
        AgeGroupImpact personProfile = AgeGroupImpact.getProfileForAge(age);
        AgeGroupVirusImpact virusProfile = AgeGroupVirusImpact.getProfileForAge(age);
        if(person.getHealthStatus() == HealthStatus.HEALTHY) {
            if (!probabilityService.happens(personProfile.getBaseMortalityChancePercent())) return false;
        } else {
            if (!probabilityService.happens(personProfile.getBaseMortalityChancePercent() + virusProfile.getPercentLethality())) return false;
        }
        return true;
    }
}
