package org.simulation.services.infection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.simulation.city.City;
import org.simulation.city.CityCell;
import org.simulation.locations.Location;
import org.simulation.locations.LocationType;
import org.simulation.people.HealthStatus;
import org.simulation.people.Person;
import org.simulation.services.CityMapService;
import org.simulation.virus.Virus;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InfectionHandlerTest {

    private InfectionService infectionService;
    private InfectionHandler infectionHandler;
    private Person person;
    private City city;
    private CityCell cityCell;
    private Location location;
    private Virus virus;

    @BeforeEach
    void setUp() {
        infectionService = mock(InfectionService.class);
        infectionHandler = new InfectionHandler(infectionService);
        person = mock(Person.class);
        city = mock(City.class);
        cityCell = mock(CityCell.class);
        location = mock(Location.class);
        virus = mock(Virus.class);
    }

    @Test
    void testHandleInfectionForHealthPeople_NewInfection() {
        when(person.getHealthStatus()).thenReturn(HealthStatus.HEALTHY).thenReturn(HealthStatus.INFECTED);
        when(person.getPosition()).thenReturn(new org.simulation.people.Position(0, 0));
        when(city.getCityMap()).thenReturn(new CityCell[][]{{cityCell}});
        when(cityCell.getLocation()).thenReturn(location);
        when(location.getType()).thenReturn(LocationType.HOUSE);

        Map<Integer, Virus> virusStages = new HashMap<>();
        virusStages.put(1, virus);
        when(virus.getMutationStage()).thenReturn(0);

        try (MockedStatic<CityMapService> cityMapServiceMock = Mockito.mockStatic(CityMapService.class)) {
            cityMapServiceMock.when(() -> CityMapService.allVirusStagesInCityCell(cityCell))
                    .thenReturn(Optional.of(virusStages));

            int result = infectionHandler.handleInfectionForHealthPeople(Collections.singletonList(person), city);

            assertEquals(1, result);
            verify(infectionService).evaluateInfection(person, virus, LocationType.HOUSE);
        }
    }

    @Test
    void testHandleInfectionForHealthPeople_NoInfection() {
        when(person.getHealthStatus()).thenReturn(HealthStatus.HEALTHY);
        when(person.getPosition()).thenReturn(new org.simulation.people.Position(0, 0));
        when(city.getCityMap()).thenReturn(new CityCell[][]{{cityCell}});
        when(cityCell.getLocation()).thenReturn(location);
        when(location.getType()).thenReturn(LocationType.HOUSE);

        Map<Integer, Virus> virusStages = new HashMap<>();
        virusStages.put(1, virus);
        when(virus.getMutationStage()).thenReturn(0);

        try (MockedStatic<org.simulation.services.CityMapService> cityMapServiceMock = Mockito.mockStatic(org.simulation.services.CityMapService.class)) {
            cityMapServiceMock.when(() -> org.simulation.services.CityMapService.allVirusStagesInCityCell(cityCell))
                    .thenReturn(Optional.of(virusStages));

            int result = infectionHandler.handleInfectionForHealthPeople(Collections.singletonList(person), city);

            assertEquals(0, result);
            verify(infectionService).evaluateInfection(person, virus, LocationType.HOUSE);
        }
    }

    @Test
    void testHandleInfectionForInfectedPeople_MoreAdvancedVirus() {
        when(person.getHealthStatus()).thenReturn(HealthStatus.INFECTED);
        when(person.getInfectedBy()).thenReturn(Optional.of(virus));
        when(virus.getMutationStage()).thenReturn(1);
        when(person.getPosition()).thenReturn(new org.simulation.people.Position(0, 0));
        when(city.getCityMap()).thenReturn(new CityCell[][]{{cityCell}});
        when(cityCell.getLocation()).thenReturn(location);
        when(location.getType()).thenReturn(LocationType.HOUSE);

        Virus advancedVirus = mock(Virus.class);
        when(advancedVirus.getMutationStage()).thenReturn(2);

        Map<Integer, Virus> virusStages = new HashMap<>();
        virusStages.put(2, advancedVirus);

        try (MockedStatic<org.simulation.services.CityMapService> cityMapServiceMock = Mockito.mockStatic(org.simulation.services.CityMapService.class)) {
            cityMapServiceMock.when(() -> org.simulation.services.CityMapService.allVirusStagesInCityCell(cityCell))
                    .thenReturn(Optional.of(virusStages));

            infectionHandler.handleInfectionForInfectedPeople(Collections.singletonList(person), city);

            verify(infectionService).evaluateInfection(person, advancedVirus, LocationType.HOUSE);
        }
    }

    @Test
    void testHandleInfectionForInfectedPeople_NoAdvancedVirus() {
        when(person.getHealthStatus()).thenReturn(HealthStatus.INFECTED);
        when(person.getInfectedBy()).thenReturn(Optional.of(virus));
        when(virus.getMutationStage()).thenReturn(2);
        when(person.getPosition()).thenReturn(new org.simulation.people.Position(0, 0));
        when(city.getCityMap()).thenReturn(new CityCell[][]{{cityCell}});
        when(cityCell.getLocation()).thenReturn(location);
        when(location.getType()).thenReturn(LocationType.HOUSE);

        Virus sameStageVirus = mock(Virus.class);
        when(sameStageVirus.getMutationStage()).thenReturn(2);

        Map<Integer, Virus> virusStages = new HashMap<>();
        virusStages.put(2, sameStageVirus);

        try (MockedStatic<org.simulation.services.CityMapService> cityMapServiceMock = Mockito.mockStatic(org.simulation.services.CityMapService.class)) {
            cityMapServiceMock.when(() -> org.simulation.services.CityMapService.allVirusStagesInCityCell(cityCell))
                    .thenReturn(Optional.of(virusStages));

            infectionHandler.handleInfectionForInfectedPeople(Collections.singletonList(person), city);

            verify(infectionService, never()).evaluateInfection(any(), any(), any());
        }
    }
}

