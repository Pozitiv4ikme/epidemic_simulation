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
    void testHandleDeathCountsCorrectly() {
        Person p1 = mock(Person.class);
        Person p2 = mock(Person.class);
        Person p3 = mock(Person.class);

        // deathService.evaluateDeath(...) нічого не повертає — він змінює стан person

        when(p1.getHealthStatus()).thenReturn(HealthStatus.DEAD);
        when(p2.getHealthStatus()).thenReturn(HealthStatus.HEALTHY);
        when(p3.getHealthStatus()).thenReturn(HealthStatus.DEAD);

        List<Person> people = Arrays.asList(p1, p2, p3);

        int result = deathHandler.handleDeath(people);

        // Має бути 2 смерті
        assertEquals(2, result);

        // Перевірка, що deathService викликався для кожної особи
        verify(deathService).evaluateDeath(p1);
        verify(deathService).evaluateDeath(p2);
        verify(deathService).evaluateDeath(p3);
    }
}
