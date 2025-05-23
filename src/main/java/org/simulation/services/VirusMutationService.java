package org.simulation.services;

import org.simulation.Simulation;
import org.simulation.people.Person;
import org.simulation.virus.Virus;

public class VirusMutationService {
    private final ProbabilityService probabilityService;

    public VirusMutationService(ProbabilityService probabilityService) {
        this.probabilityService = probabilityService;
    }

    public void tryMutateVirus(Person person) {
        int age = person.getAge();
        double percentMutation = 0.0;
        double percentLethality = 0.0;
        double percentInfectionProbability = 0.0;
        double percentRecoverProbability = 0.0;

        if (age <= 24) {
            percentMutation = 25.0;
            percentLethality = 0;
            percentInfectionProbability = 0;
            percentRecoverProbability = 0;
        } else if (25 <= age && age <= 44) {
            percentMutation = 17.5;
            percentLethality = 0;
            percentInfectionProbability = 0;
            percentRecoverProbability = 0;
        } else if (45 <= age && age <= 64) {
            percentMutation = 12.0;
            percentLethality = 0;
            percentInfectionProbability = 0;
            percentRecoverProbability = 0;
        } else {
            percentMutation = 6.5;
            percentLethality = 80.0;
            percentInfectionProbability = 70.0;
            percentRecoverProbability = -60.0;
        }
        if (!probabilityService.happens(percentMutation)) return;
        Virus current = person.getInfectedBy();
        Virus mutated = new Virus(
                adjust(current.getInfectionProbability(), percentInfectionProbability),
                current.getMutationStage() + 1,
                adjust(current.getLethality(), percentLethality),
                adjust(current.getRecoverProbability(), percentRecoverProbability)
        );
        person.setInfectedBy(mutated);
        Simulation.addVirusToExisted(mutated);
    }// wirus próbuje mutować każdą epokę; nowy wirus będzie dodawany w List wszystkich wirusów
    private double adjust(double base, double percentChange) {
        return base + base * percentChange / 100.0;
    }
}
