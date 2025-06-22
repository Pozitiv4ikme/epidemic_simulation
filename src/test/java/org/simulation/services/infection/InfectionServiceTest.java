package org.simulation.services.infection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.simulation.locations.LocationHealthImpact;
import org.simulation.locations.LocationType;
import org.simulation.people.AgeGroupImpact;
import org.simulation.people.HealthStatus;
import org.simulation.people.Person;
import org.simulation.services.ProbabilityService;
import org.simulation.virus.Virus;

import java.util.Optional;

import static org.mockito.Mockito.*;

class InfectionServiceTest {

    private ProbabilityService probabilityService;
    private InfectionService infectionService;
    private Person person;
    private Virus virus;

    @BeforeEach
    void setUp() {
        probabilityService = mock(ProbabilityService.class);
        infectionService = new InfectionService(probabilityService);
        person = mock(Person.class);
        virus = mock(Virus.class);
    }

    @Test
    void testPersonGetsInfected() {
        when(person.getAge()).thenReturn(25);
        AgeGroupImpact profile = mock(AgeGroupImpact.class);
        when(profile.getBaseInfectionChancePercent()).thenReturn(10.0);
        when(virus.getInfectionProbability()).thenReturn(20.0);
        LocationHealthImpact locationImpact = mock(LocationHealthImpact.class);
        when(locationImpact.getPercentInfectionProbability()).thenReturn(5.0);

        try (MockedStatic<AgeGroupImpact> ageGroupMock = Mockito.mockStatic(AgeGroupImpact.class);
             MockedStatic<LocationHealthImpact> locationMock = Mockito.mockStatic(LocationHealthImpact.class)) {
            ageGroupMock.when(() -> AgeGroupImpact.getProfileForAge(25)).thenReturn(profile);
            locationMock.when(() -> LocationHealthImpact.getImpactForLocationType(LocationType.HOUSE)).thenReturn(locationImpact);

            when(probabilityService.happens(35.0)).thenReturn(true);

            infectionService.evaluateInfection(person, virus, LocationType.HOUSE);

            verify(person).setHealthStatus(HealthStatus.INFECTED);
            verify(person).setInfectedBy(Optional.of(virus));
        }
    }

    @Test
    void testPersonDoesNotGetInfected() {
        when(person.getAge()).thenReturn(25);
        AgeGroupImpact profile = mock(AgeGroupImpact.class);
        when(profile.getBaseInfectionChancePercent()).thenReturn(10.0);
        when(virus.getInfectionProbability()).thenReturn(20.0);
        LocationHealthImpact locationImpact = mock(LocationHealthImpact.class);
        when(locationImpact.getPercentInfectionProbability()).thenReturn(5.0);

        try (MockedStatic<AgeGroupImpact> ageGroupMock = Mockito.mockStatic(AgeGroupImpact.class);
             MockedStatic<LocationHealthImpact> locationMock = Mockito.mockStatic(LocationHealthImpact.class)) {
            ageGroupMock.when(() -> AgeGroupImpact.getProfileForAge(25)).thenReturn(profile);
            locationMock.when(() -> LocationHealthImpact.getImpactForLocationType(LocationType.HOUSE)).thenReturn(locationImpact);

            when(probabilityService.happens(35.0)).thenReturn(false);

            infectionService.evaluateInfection(person, virus, LocationType.HOUSE);

            verify(person, never()).setHealthStatus(HealthStatus.INFECTED);
            verify(person, never()).setInfectedBy(any());
        }
    }
}

