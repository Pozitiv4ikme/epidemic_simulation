package org.simulation.services.movement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.simulation.city.City;
import org.simulation.people.Person;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class MovementHandlerTest {

    private MovementService movementService;
    private MovementHandler movementHandler;

    @BeforeEach
    void setUp() {
        movementService = mock(MovementService.class);
        movementHandler = new MovementHandler(movementService);
    }

    @Test
    void testHandleMovementCallsMovingForEachPerson() {
        Person p1 = mock(Person.class);
        Person p2 = mock(Person.class);
        Person p3 = mock(Person.class);
        City city = mock(City.class);

        List<Person> people = Arrays.asList(p1, p2, p3);
        int movesPerEpoch = 2;

        movementHandler.handleMovement(people, city, movesPerEpoch);

        verify(movementService).moving(p1, city, movesPerEpoch);
        verify(movementService).moving(p2, city, movesPerEpoch);
        verify(movementService).moving(p3, city, movesPerEpoch);
        verifyNoMoreInteractions(movementService);
    }
}
