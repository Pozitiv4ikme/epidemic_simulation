package org.simulation.services;

import org.simulation.people.Person;
import org.simulation.virus.AgeGroupVirusImpact;
import org.simulation.virus.Virus;

import java.util.Optional;

public class VirusMutationService {
    private final ProbabilityService probabilityService;

    public VirusMutationService(ProbabilityService probabilityService) {
        this.probabilityService = probabilityService;
    }

    public Optional<Virus> tryMutateVirus(Person person) {
        AgeGroupVirusImpact profile = AgeGroupVirusImpact.getProfileForAge(person.getAge());

        Virus current = person.getInfectedBy().get();
        if (!probabilityService.happens(profile.getPercentVirusMutation())) return Optional.of(current);
        Virus mutated = new Virus(
                adjust(current.getInfectionProbability(), profile.getPercentInfectionProbability()),
                current.getMutationStage() + 1,
                adjust(current.getLethality(), profile.getPercentLethality()),
                adjust(current.getRecoverProbability(), profile.getPercentRecoveryProbability())
        );
        
        person.setInfectedBy(Optional.of(mutated));
        return Optional.of(mutated);
    }
    private double adjust(double base, double percentChange) {
        return base + base * percentChange / 100.0;
    }
}
