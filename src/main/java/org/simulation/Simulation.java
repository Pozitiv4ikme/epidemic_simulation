package org.simulation;

import org.simulation.city.City;
import org.simulation.config.SimulationConfig;
import org.simulation.virus.Virus;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final City city;
    private final List<Virus> viruses;
    private final SimulationConfig config;
    private final List<Epoch> epochs;

    public Simulation(SimulationConfig config) {
        this.config = config;
        this.epochs = new ArrayList<>();
        this.city = new City(config.cityConfig());

        Virus initialVirus = new Virus(config.initialVirusConfig());
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
