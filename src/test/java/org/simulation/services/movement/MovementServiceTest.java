package org.simulation.services.movement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.simulation.city.City;
import org.simulation.city.CityCell;
import org.simulation.people.AgeGroupImpact;
import org.simulation.people.Person;
import org.simulation.people.Position;
import org.simulation.services.ProbabilityService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class MovementServiceTest {

    private ProbabilityService probabilityService;
    private MovementService movementService;
    private Person person;
    private City city;

    @BeforeEach
    void setUp() {
        probabilityService = mock(ProbabilityService.class);
        movementService = new MovementService(probabilityService);
        person = mock(Person.class);
        city = mock(City.class);
    }

    @Test
    void testPersonMoves() {
        Position currentPosition = mock(Position.class);
        Position newPosition = mock(Position.class);
        List<Position> neighbours = new ArrayList<>();
        neighbours.add(newPosition);

        when(person.getPosition()).thenReturn(currentPosition);
        when(currentPosition.getNeighbours()).thenReturn(neighbours);

        when(city.getHeight()).thenReturn(10);
        when(city.getWidth()).thenReturn(10);
        when(newPosition.isInBounds(10, 10)).thenReturn(true);

        AgeGroupImpact ageGroupImpact = mock(AgeGroupImpact.class);
        when(person.getAge()).thenReturn(20);
        when(ageGroupImpact.getBaseMovementChancePercent()).thenReturn(100.0);

        CityCell[][] cityMap = new CityCell[10][10];
        CityCell fromCell = mock(CityCell.class);
        CityCell toCell = mock(CityCell.class);
        cityMap[0][0] = fromCell;
        cityMap[1][0] = toCell;
        when(currentPosition.getX()).thenReturn(0);
        when(currentPosition.getY()).thenReturn(0);
        when(newPosition.getX()).thenReturn(1);
        when(newPosition.getY()).thenReturn(0);
        when(city.getCityMap()).thenReturn(cityMap);
        when(fromCell.getPeople()).thenReturn(new ArrayList<>());
        when(toCell.getPeople()).thenReturn(new ArrayList<>());

        try (MockedStatic<AgeGroupImpact> ageGroupMock = Mockito.mockStatic(AgeGroupImpact.class)) {
            ageGroupMock.when(() -> AgeGroupImpact.getProfileForAge(20)).thenReturn(ageGroupImpact);
            when(probabilityService.happens(100.0)).thenReturn(true);

            // actual
            movementService.moving(person, city, 1);

            verify(person).move(newPosition);
        }
    }

    @Test
    void testPersonNotMove() {
        Position currentPosition = mock(Position.class);
        Position newPosition = mock(Position.class);
        List<Position> neighbours = new ArrayList<>();
        neighbours.add(newPosition);

        when(person.getPosition()).thenReturn(currentPosition);
        when(currentPosition.getNeighbours()).thenReturn(neighbours);

        when(city.getHeight()).thenReturn(10);
        when(city.getWidth()).thenReturn(10);
        when(newPosition.isInBounds(10, 10)).thenReturn(true);

        AgeGroupImpact ageGroupImpact = mock(AgeGroupImpact.class);
        when(person.getAge()).thenReturn(20);
        when(ageGroupImpact.getBaseMovementChancePercent()).thenReturn(0.0);

        try (MockedStatic<AgeGroupImpact> ageGroupMock = Mockito.mockStatic(AgeGroupImpact.class)) {
            ageGroupMock.when(() -> AgeGroupImpact.getProfileForAge(20)).thenReturn(ageGroupImpact);
            when(probabilityService.happens(0.0)).thenReturn(false);

            // actual
            movementService.moving(person, city, 1);

            verify(person, never()).move(any());
        }
    }
}

