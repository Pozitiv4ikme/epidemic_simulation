package org.simulation.services;

import org.simulation.Simulation;
import org.simulation.people.Person;
import org.simulation.virus.Virus;

public class VirusMutationService {
    public static void tryMutateVirus(Person person, Simulation simulation){
        Virus mutated = person.getInfectedBy().mutation(person.getAge());
        person.setInfectedBy(mutated);
    } // wirus próbuje mutować każdą epokę; nowy wirus będzie dodawany w List wszystkich wirusów
}
