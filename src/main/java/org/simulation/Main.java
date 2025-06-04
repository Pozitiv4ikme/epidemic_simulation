package org.simulation;

import org.simulation.locations.Location;
import org.simulation.locations.LocationFactory;
import org.simulation.locations.LocationType;
import org.simulation.people.HealthStatus;
import org.simulation.people.Person;
import org.simulation.people.Position;
import org.simulation.services.*;
import org.simulation.virus.Virus;

import java.util.List;
import java.util.Optional;

import org.simulation.config.ConfigLoader;
import org.simulation.config.SimulationConfig;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String configFile = "SimulationConfig.json";
        SimulationConfig config;

        try {
            config = ConfigLoader.load(configFile);
        } catch (IOException e) {
            System.out.println(e.getMessage() + "\n" + "Config file with name " + configFile + " not found. " +
                    "Please try again with new file name");
            return;
        }

        // config file reading testing
        Simulation simulation = new Simulation(config);
        System.out.println(simulation.getCity().toString());

        ProbabilityService probabilityService = new ProbabilityService();

        // Testing people mortality
//        DeathService deathService = new DeathService(probabilityService);

        // Testing people infection
//        InfectionService infectionService = new InfectionService(probabilityService);

        // Testing people recovery
//        RecoveryService recoveryService = new RecoveryService(probabilityService);

        // Testing virus mutation
//        VirusMutationService virusMutationService = new VirusMutationService(probabilityService);

        Person person1 = new Person(64,HealthStatus.HEALTHY, new Position(12,12));
        Person person2 = new Person(70,HealthStatus.INFECTED, new Position(12,12), simulation.getViruses().getFirst());

//        deathService.evaluateDeath(person1);
//        deathService.evaluateDeath(person2);

//        infectionService.evaluateInfection(person1,person2, LocationType.MEDICAL_CENTRE);

//        recoveryService.evaluateRecovery(person2,LocationType.MEDICAL_CENTRE);

//        Optional<Virus> mutatedVirus = virusMutationService.tryMutateVirus(person2);

//        System.out.println(person1.getHealthStatus());
//        System.out.println(person2.getHealthStatus());
//        System.out.println(person2.getInfectedBy().get());

        // locations generating testing
//        List<Location> locations = LocationFactory.generateLocations(config.cityConfig().width(),
//                config.cityConfig().height(), config.cityConfig().locationsData());
//
//        for(Location location : locations) {
//            System.out.println(location);
//        }
    }
}
