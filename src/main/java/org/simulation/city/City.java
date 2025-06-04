package org.simulation.city;

import org.simulation.config.CityConfig;
import org.simulation.config.LocationConfigData;
import org.simulation.locations.LocationType;
import org.simulation.people.Person;

import java.util.Map;

public class City {
    private final int width;
    private final int height;
    private final int population;
    private Map<String, Person> people;
    private final int infectedPercentage;
    private final Map<LocationType, LocationConfigData> locationsData;
    private CityCell[][] cityMap;

    public City(CityConfig cityConfig) {
        this.width = cityConfig.width();
        this.height = cityConfig.height();
        this.population = cityConfig.population();
        this.infectedPercentage = cityConfig.infectedPercentage();
        this.locationsData = cityConfig.locationsData();
    }

    private void generateCityMap() {
        this.cityMap = new CityCell[width][height];
    }

    private void generatePeople() {}

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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getPopulation() {
        return population;
    }

    public Map<String, Person> getPeople() {
        return people;
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
}
