package org.simulation;

import org.simulation.city.CityCell;
import org.simulation.people.HealthStatus;
import org.simulation.people.Person;
import org.simulation.people.Position;
import org.simulation.services.*;

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

        for (CityCell[] cityCellRow : simulation.getCity().getCityMap()) {
            for (CityCell cell : cityCellRow) {
                if(cell == null) {
                    System.out.print(0 + " ");
                    continue;
                }
                System.out.print(cell + " ");
            }
            System.out.println();
        }

        for (CityCell[] cityCellRow : simulation.getCity().getCityMap()) {
            for (CityCell cell : cityCellRow) {
                if(cell.getPeople().isEmpty()) {
                    System.out.print(0 + " ");
                    continue;
                }
                System.out.print(cell.getPeople().size() + " ");
            }
            System.out.println();
        }

        simulation.worldSimulation();

        for (CityCell[] cityCellRow : simulation.getCity().getCityMap()) {
            for (CityCell cell : cityCellRow) {
                if(cell.getPeople().isEmpty()) {
                    System.out.print(0 + " ");
                    continue;
                }
                System.out.print(cell.getPeople().size() + " ");
            }
            System.out.println();
        }
    }
}
