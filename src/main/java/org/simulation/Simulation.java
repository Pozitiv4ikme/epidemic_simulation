package org.simulation;

import org.simulation.city.City;
import org.simulation.config.SimulationConfig;
import org.simulation.virus.Virus;

import java.util.List;

public class Simulation {
    private City city;
    private static List<Virus> viruses;
    private SimulationConfig config;
    private List<Epoch> epochs;

    public static void addVirusToExisted(Virus virus) {
        viruses.add(virus);
    }

    private void initialization() {}
    public void worldSimulation() {}
}
