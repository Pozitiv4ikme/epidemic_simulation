package org.simulation.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Utility class for loading the simulation configuration from a JSON file located in the resources folder.
 */

public class ConfigLoader {

    /**
     * Loads the simulation configuration from the given path inside the resources directory.
     * @param pathToJsonInResources Path to the JSON configuration file relative to the resources directory.
     * @return A fully constructed {@link SimulationConfig} object.
     * @throws IOException If the file is not found or cannot be read.
     */

    public static SimulationConfig load(String pathToJsonInResources) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        // Disable failure on missing creator properties (record components)
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, false);
        ClassLoader classLoader = ConfigLoader.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(pathToJsonInResources);

        return mapper.readValue(inputStream, SimulationConfig.class);
    }
}
