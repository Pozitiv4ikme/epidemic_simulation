package org.simulation.city;

import org.simulation.config.CityConfig;
import org.simulation.config.LocationConfigData;
import org.simulation.locations.LocationType;
import org.simulation.people.HealthStatus;
import org.simulation.people.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the simulation city with its map, population, and location data.
 */

public class City {
    private final int width;
    private final int height;
    private final int population;
    private Map<HealthStatus, List<Person>> people;
    private final int infectedPercentage;
    private final Map<LocationType, LocationConfigData> locationsData;
    private CityCell[][] cityMap;

    /**
     * Creates a city instance using configuration data.
     * @param cityConfig configuration for the city
     */

    public City(CityConfig cityConfig) {
        this.width = cityConfig.width();
        this.height = cityConfig.height();
        this.cityMap = new CityCell[height][width];
        this.population = cityConfig.population();
        this.infectedPercentage = cityConfig.infectedPercentage();
        this.locationsData = cityConfig.locationsData();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getPopulation() {
        return population;
    }

    public Map<HealthStatus, List<Person>> getPeople() {
        return people;
    }

    /**
     * Groups the provided people by health status and stores them.
     */

    public void setPeople(List<Person> people) {
        Map<HealthStatus, List<Person>> cityPeople = new HashMap<>();
        for(Person person: people) {
            cityPeople.computeIfAbsent(person.getHealthStatus(), k -> new ArrayList<>()).add(person);
        }
        this.people = cityPeople;
    }

    /**
     * Replaces the city's population with the updated data.
     */

    public void updatePopulation(Map<HealthStatus, List<Person>> updatedPeople) {
        this.people = updatedPeople;
    }

    /**
     * Removes a person from their cell on the city map.
     */

    public void deletePersonFromMap(Person person) {
        cityMap[person.getPosition().getX()][person.getPosition().getY()].removePerson(person);
    }

    public int getInfectedPercentage() {
        return infectedPercentage;
    }

    public Map<LocationType, LocationConfigData> getLocationsData() {
        return locationsData;
    }

    public CityCell[][] getCityMap() {
        return cityMap;
    }

    @Override
    public String toString() {
        return "City{" +
                "width=" + width +
                ", height=" + height +
                ", population=" + population +
                ", infectedPercentage=" + infectedPercentage +
                ", locationsData=" + locationsData +
                '}';
    }
}
