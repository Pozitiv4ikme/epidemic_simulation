package org.simulation;

import org.simulation.city.City;
import org.simulation.config.SimulationConfig;
import org.simulation.virus.Virus;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private City city;
    private List<Virus> viruses;
    private SimulationConfig config;
    private List<Epoch> epochs;

    public Simulation(SimulationConfig config) {
//        this.city = new City(config.width());
        this.epochs = new ArrayList<>();

        Virus initialVirus = new Virus(config.initialInfectionProbability(), 1,
                config.initialVirusLethality(), config.initialRecoveryProbability());
        this.viruses = new ArrayList<>();
        this.viruses.add(initialVirus);
    }

    public City getCity() {
        return city;
    }

    public List<Virus> getViruses() {
        return viruses;
    }

    public SimulationConfig getConfig() {
        return config;
    }

    public List<Epoch> getEpochs() {
        return epochs;
    }

    public void worldSimulation() {}
}
