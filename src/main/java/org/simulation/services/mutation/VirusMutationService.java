package org.simulation.services.mutation;

import org.simulation.people.Person;
import org.simulation.services.ProbabilityService;
import org.simulation.virus.AgeGroupVirusImpact;
import org.simulation.virus.Virus;

import java.util.List;
import java.util.Optional;

/**
 * A service responsible for simulating virus mutation in infected individuals.
 */

public class VirusMutationService {
    private final ProbabilityService probabilityService;

    /**
     * Constructs the VirusMutationService with a given ProbabilityService.
     * @param probabilityService service used to evaluate probability events
     */

    public VirusMutationService(ProbabilityService probabilityService) {
        this.probabilityService = probabilityService;
    }

    /**
     * Attempts to create a new virus mutation based on the infected person's age profile.
     * If the mutation occurs, a new Virus object is created and assigned to the person.
     * @param person the infected person
     * @return an {@code Optional<Virus>} representing the current or new virus
     */

    public Optional<Virus> tryNewMutateVirus(Person person) {
        AgeGroupVirusImpact profile = AgeGroupVirusImpact.getProfileForAge(person.getAge());

        Virus current = person.getInfectedBy().get();
        if (!probabilityService.happens(profile.getPercentVirusMutation())) return Optional.of(current);
        Virus mutated = new Virus(
                increase(current.getInfectionProbability(), profile.getPercentInfectionProbability()),
                current.getMutationStage() + 1,
                increase(current.getLethality(), profile.getPercentLethality()),
                decrease(current.getRecoverProbability(), profile.getPercentRecoveryProbability())
        );
        
        person.setInfectedBy(Optional.of(mutated));
        return Optional.of(mutated);
    }

    /**
     * Attempts to mutate the virus to the next predefined stage from a list of known viruses.
     * The mutation is based on the person's age profile.
     * @param person the infected person
     * @param viruses list of predefined virus stages
     * @return an {@code Optional<Virus>} representing the new virus if mutated, or the original one
     */

    public Optional<Virus> tryMutateVirus(Person person, List<Virus> viruses) {
        AgeGroupVirusImpact profile = AgeGroupVirusImpact.getProfileForAge(person.getAge());

        Virus current = person.getInfectedBy().get();
        Virus newVirus = viruses.get(current.getMutationStage());
        if (!probabilityService.happens(profile.getPercentVirusMutation())) return Optional.of(current);
        person.setInfectedBy(Optional.of(newVirus));
        return Optional.of(newVirus);
    }

    /**
     * Calculates a new value based on a base value and a percentage change.
     * @param base the base value
     * @param percentChange the percent change to apply
     * @return adjusted value
     */

    private double increase(double base, double percentChange) {
        double result = base + base * percentChange / 100.0;
        return Math.min(result, 100.0);
    }

    private double decrease(double base, double percentChange) {
        double result = base - base * percentChange / 100.0;
        return Math.max(result, 0.0);
    }
}
