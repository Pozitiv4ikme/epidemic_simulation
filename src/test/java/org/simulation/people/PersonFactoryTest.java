package org.simulation.people;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.simulation.city.City;
import org.simulation.city.CityCell;
import org.simulation.virus.Virus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonFactoryTest {

    private City city;
    private List<Virus> viruses;

    @BeforeEach
    void setUp() {
        city = mock(City.class);
        Virus virus1 = mock(Virus.class);
        viruses = Collections.singletonList(virus1);

        when(city.getPopulation()).thenReturn(10);
        when(city.getInfectedPercentage()).thenReturn(20);
        when(city.getWidth()).thenReturn(2);
        when(city.getHeight()).thenReturn(2);

        // Setup city map and cells
        CityCell[][] cityMap = new CityCell[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                CityCell cell = mock(CityCell.class);
                when(cell.getPeople()).thenReturn(new ArrayList<>());
                cityMap[i][j] = cell;
            }
        }
        when(city.getCityMap()).thenReturn(cityMap);
    }

    @Test
    void testGeneratePeopleCorrectAmountOfInfected() {
        PersonFactory factory = new PersonFactory();

        // actual
        List<Person> people = factory.generatePeople(city, viruses);
        long infected = people.stream().filter(p -> p.getHealthStatus() == HealthStatus.INFECTED).count();

        // expected
        int expectedInfected = 2;
        assertEquals(expectedInfected, infected);
    }

    @Test
    void testGeneratePeopleCorrectly() {
        PersonFactory factory = new PersonFactory();

        // actual
        List<Person> people = factory.generatePeople(city, viruses);

        // expected
        for (Person p : people) {
            assertTrue(p.getAge() >= 6 && p.getAge() <= 100);
            Position pos = p.getPosition();
            assertTrue(pos.getX() >= 0 && pos.getX() < 2);
            assertTrue(pos.getY() >= 0 && pos.getY() < 2);
        }
    }
}
