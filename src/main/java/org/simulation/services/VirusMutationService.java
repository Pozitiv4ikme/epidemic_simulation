package org.simulation.services;

import org.simulation.Simulation;
import org.simulation.people.Person;
import org.simulation.virus.AgeGroupVirusImpact;
import org.simulation.virus.Virus;

public class VirusMutationService {
    private final ProbabilityService probabilityService;

    public VirusMutationService(ProbabilityService probabilityService) {
        this.probabilityService = probabilityService;
    }

    public void tryMutateVirus(Person person) {
        AgeGroupVirusImpact profile = AgeGroupVirusImpact.getProfileForAge(person.getAge());

        if (!probabilityService.happens(profile.getPercentVirusMutation())) return;
        Virus current = person.getInfectedBy();
        Virus mutated = new Virus(
                adjust(current.getInfectionProbability(), profile.getPercentInfectionProbability()),
                current.getMutationStage() + 1,
                adjust(current.getLethality(), profile.getPercentLethality()),
                adjust(current.getRecoverProbability(), profile.getPercentRecoveryProbability())
        );
        
        person.setInfectedBy(mutated);
        Simulation.addVirusToExisted(mutated);
    }
    private double adjust(double base, double percentChange) {
        return base + base * percentChange / 100.0;
    }
}
