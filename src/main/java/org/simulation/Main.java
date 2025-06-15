package org.simulation;

import org.simulation.city.CityCell;

import org.simulation.config.ConfigLoader;
import org.simulation.config.SimulationConfig;
import org.simulation.exceptions.CityConfigException;
import org.simulation.exceptions.LocationConfigDataException;
import org.simulation.exceptions.SimulationConfigException;
import org.simulation.exceptions.VirusConfigException;

import java.io.IOException;

/**
 * Entry point of the simulation program.
 */

public class Main {
    /**
     * Prints the number of people in each cell of the city map.
     * @param simulation The current simulation instance
     */

    private static void printPeopleOnTheCityMap(Simulation simulation) {
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

    public static void main(String[] args) {
        String configFile = "SimulationConfig.json";
        SimulationConfig config;

        // Try loading the configuration from the specified file
        try {
            config = ConfigLoader.load(configFile);
        } catch (IOException e) {
            System.out.println(e.getMessage() + "\n" + "Config file with name " + configFile + " not found. " +
                    "Please try again with new file name");
            return;
        }

        try {
            config.validate();
        } catch (SimulationConfigException | CityConfigException | LocationConfigDataException | VirusConfigException e) {
            System.out.println("Configuration error: " + e.getMessage());
            return;
        }

        Simulation simulation = new Simulation(config);

        // Print initial layout of the city map (locations only)
        for (CityCell[] cityCellRow : simulation.getCity().getCityMap()) {
            for (CityCell cell : cityCellRow) {
                if (cell == null) {
                    System.out.print(0 + " ");
                    continue;
                }
                System.out.print(cell + " ");
            }
            System.out.println();
        }

        // Print the number of people in each cell before simulation starts
        printPeopleOnTheCityMap(simulation);

        // Run the simulation
        simulation.worldSimulation();

        // Print the number of people in each cell after simulation ends
        printPeopleOnTheCityMap(simulation);
    }
}
