package org.simulation.services.mutation;

import org.junit.jupiter.api.BeforeEach;
import org.simulation.people.HealthStatus;
import org.simulation.people.Person;
import org.simulation.people.Position;
import org.simulation.virus.Virus;
import static org.mockito.Mockito.*;

public class MutationHandlerTest {
    private MutationHandler mutationHandler;
    private VirusMutationService virusMutationService;

    private Virus virusStage1;
    private Virus virusStage2;
    private Person person;

    @BeforeEach
    void setUp() {
        virusMutationService = mock(VirusMutationService.class);
        mutationHandler = new MutationHandler(virusMutationService);

        virusStage1 = new Virus(0.1, 1, 0.05, 0.9);
        virusStage2 = new Virus(0.2, 2, 0.1, 0.8);

        person = new Person(21, HealthStatus.INFECTED, new Position(0, 0), virusStage1, "test");
    }

}
