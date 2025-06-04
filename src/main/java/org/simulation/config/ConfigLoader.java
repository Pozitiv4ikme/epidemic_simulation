package org.simulation.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ConfigLoader {
    public static SimulationConfig load(String pathToJsonInResources) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, false);
        ClassLoader classLoader = ConfigLoader.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(pathToJsonInResources);

        return mapper.readValue(inputStream, SimulationConfig.class);
    }
}
