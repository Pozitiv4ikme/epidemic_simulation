package org.simulation.services.mutation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.simulation.people.HealthStatus;
import org.simulation.people.Person;
import org.simulation.people.Position;
import org.simulation.services.ProbabilityService;
import org.simulation.virus.Virus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VirusMutationServiceTest {

    private ProbabilityService probabilityService;
    private VirusMutationService virusMutationService;
    private Person personForNewMutation;
    private Person personGetMutationForExistingVirus;
    private Virus virusOld;
    private Virus virusNew;
    private List<Virus> viruses;

    @BeforeEach
    void setUp() {
        probabilityService = mock(ProbabilityService.class);
        virusMutationService = new VirusMutationService(probabilityService);

        virusOld = new Virus(0.1, 1, 0.05, 0.9);
        virusNew = new Virus(0.2, 2, 0.1, 0.8);
        viruses = List.of(virusOld, virusNew);

        personForNewMutation = new Person(20, HealthStatus.INFECTED, new Position(0, 0), virusOld, "person1");
        personGetMutationForExistingVirus = new Person(30, HealthStatus.INFECTED, new Position(1, 1), virusOld, "person2");
    }

    @Test
    void testTryNewMutateVirusSuccess() {
        when(probabilityService.happens(anyDouble())).thenReturn(true);

        // actual
        Optional<Virus> newVirus = virusMutationService.tryNewMutateVirus(personForNewMutation);

        // expected mutation stage
        int expectedMutationStage = 2;

        assertTrue(newVirus.isPresent());
        assertNotEquals(virusOld, newVirus.get());
        assertEquals(expectedMutationStage, newVirus.get().getMutationStage());
        assertEquals(newVirus.get(), personForNewMutation.getInfectedBy().get());
    }

    @Test
    void testTryNewMutateVirusNotSuccess() {
        when(probabilityService.happens(anyDouble())).thenReturn(false);

        // actual
        Optional<Virus> newVirus = virusMutationService.tryNewMutateVirus(personForNewMutation);

        // expected mutation stage
        int expectedMutationStage = 1;

        assertTrue(newVirus.isPresent());
        assertEquals(virusOld, newVirus.get());
        assertEquals(expectedMutationStage, newVirus.get().getMutationStage());
        assertEquals(newVirus.get(), personForNewMutation.getInfectedBy().get());
    }

    @Test
    void testTryMutateVirusSuccess() {
        when(probabilityService.happens(anyDouble())).thenReturn(true);

        // actual
        Optional<Virus> newVirus = virusMutationService.tryMutateVirus(personGetMutationForExistingVirus, viruses);

        assertTrue(newVirus.isPresent());
        assertEquals(virusNew, newVirus.get());
        assertEquals(newVirus.get(), personGetMutationForExistingVirus.getInfectedBy().get());
    }

    @Test
    void testTryMutateVirusNotSuccess() {
        when(probabilityService.happens(anyDouble())).thenReturn(false);

        // actual
        Optional<Virus> newVirus = virusMutationService.tryMutateVirus(personGetMutationForExistingVirus, viruses);

        assertTrue(newVirus.isPresent());
        assertEquals(virusOld, newVirus.get());
        assertEquals(virusOld, personGetMutationForExistingVirus.getInfectedBy().get());
    }
}
