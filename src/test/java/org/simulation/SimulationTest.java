package org.simulation;

import org.junit.jupiter.api.BeforeEach;
import org.simulation.config.CityConfig;
import org.simulation.config.SimulationConfig;
import org.simulation.config.VirusConfig;

import java.util.Map;

import static org.mockito.Mockito.*;

class SimulationTest {

    private SimulationConfig config;
    private VirusConfig virusConfig;
    private CityConfig cityConfig;

    @BeforeEach
    void setUp() {
        cityConfig = new CityConfig(5, 3, 2, 1, Map.of());
        virusConfig = new VirusConfig(10, 5, 25);

        config = mock(SimulationConfig.class);
        when(config.cityConfig()).thenReturn(cityConfig);
        when(config.initialVirusConfig()).thenReturn(virusConfig);
        when(config.totalNumberOfEpochs()).thenReturn(5);
        when(config.numberOfMovesPerEpoch()).thenReturn(2);
        when(config.resultsCsvFilePath()).thenReturn("testResult.csv");
    }
}
