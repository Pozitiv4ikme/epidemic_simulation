package org.simulation;

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
        }

        Simulation simulation = new Simulation(config);
    }
}
