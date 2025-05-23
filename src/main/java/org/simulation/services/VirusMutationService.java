package org.simulation.services;

import org.simulation.Simulation;
import org.simulation.people.Person;
import org.simulation.virus.Virus;

public class VirusMutationService {
    public static ProbabilityService probabilityService;

    public static void tryMutateVirus(Person person) {
        double percentMutation = 0.0;
        double percentLethality = 0.0;
        double percentInfectionProbability = 0.0;
        double percentRecoverProbability = 0.0;

        if (person.getAge() <= 24) {
            percentMutation = 25.0;
            percentLethality = 0;
            percentInfectionProbability = 0;
            percentRecoverProbability = 0;
        } else if (25 <= person.getAge() && person.getAge() <= 44) {
            percentMutation = 17.5;
            percentLethality = 0;
            percentInfectionProbability = 0;
            percentRecoverProbability = 0;
        } else if (45 <= person.getAge() && person.getAge() <= 64) {
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
        if (probabilityService.happens(percentMutation)) {
            int mutationStage = person.getInfectedBy().getMutationStage() + 1;
            double lethality = person.getInfectedBy().getLethality() + person.getInfectedBy().getLethality() * percentLethality / 100;
            double infectionProbability = person.getInfectedBy().getInfectionProbability() + person.getInfectedBy().getInfectionProbability() * percentInfectionProbability / 100;
            double recoverProbability = person.getInfectedBy().getRecoverProbability() + person.getInfectedBy().getRecoverProbability() * percentRecoverProbability / 100;

            Virus mutated = new Virus(infectionProbability, mutationStage, lethality, recoverProbability);

            person.setInfectedBy(mutated);
            Simulation.addVirusToExisted(mutated);
        }
    }// wirus próbuje mutować każdą epokę; nowy wirus będzie dodawany w List wszystkich wirusów
}
