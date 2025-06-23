package org.simulation.services.death;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.simulation.people.HealthStatus;
import org.simulation.people.Person;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DeathHandlerTest {

    private DeathService deathService;
    private DeathHandler deathHandler;

    @BeforeEach
    void setUp() {
        deathService = mock(DeathService.class);
        deathHandler = new DeathHandler(deathService);
    }

    @Test
    void testDeathCountsCorrectly() {
        Person p1 = mock(Person.class);
        Person p2 = mock(Person.class);
        Person p3 = mock(Person.class);

        // expected
        int expectedDeaths = 2;
        when(p1.getHealthStatus()).thenReturn(HealthStatus.DEAD);
        when(p2.getHealthStatus()).thenReturn(HealthStatus.HEALTHY);
        when(p3.getHealthStatus()).thenReturn(HealthStatus.DEAD);

        List<Person> people = List.of(p1, p2, p3);

        // actual
        int actualDeaths = deathHandler.handleDeath(people);

        assertEquals(expectedDeaths, actualDeaths);

        verify(deathService).evaluateDeath(p1);
        verify(deathService).evaluateDeath(p2);
        verify(deathService).evaluateDeath(p3);
    }
}
