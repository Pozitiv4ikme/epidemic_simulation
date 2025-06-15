package org.simulation.services;

import org.simulation.city.City;
import org.simulation.city.CityCell;
import org.simulation.locations.Location;
import org.simulation.locations.LocationFactory;
import org.simulation.locations.LocationType;
import org.simulation.people.Person;
import org.simulation.virus.Virus;

import java.util.*;

/**
 * A utility service responsible for managing the city map,
 * including placing locations and retrieving virus data.
 */

public class CityMapService {

    /**
     * Fills the city map with locations based on city size and location data.
     * Medical centres are placed first with special constraints,
     * then other locations are placed. Remaining empty cells are filled as roads.
     * @param city the city to fill
     */

    public static void fillCityMap(City city) {
        List<Location> allLocations = LocationFactory.generateLocations(city.getWidth(), city.getHeight(), city.getLocationsData());

        allLocations.stream()
                .filter(loc -> loc.getType() == LocationType.MEDICAL_CENTRE)
                .forEach(loc -> placeLocationWithConstraints(city, loc, true));

        allLocations.stream()
                .filter(loc -> loc.getType() != LocationType.MEDICAL_CENTRE)
                .forEach(loc -> placeLocationWithConstraints(city, loc, false));

        int width = city.getWidth();
        int height = city.getHeight();
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                if (city.getCityMap()[x][y] == null) {
                    Location road = new Location(LocationType.ROAD); // Adjust constructor as needed
                    city.getCityMap()[x][y] = new CityCell(road);
                }
            }
        }
    }

    /**
     * Tries to place a location on the city map respecting constraints.
     * Hospitals can be placed anywhere; other locations must avoid proximity to hospitals.
     * @param city       the city map
     * @param location   location to place
     * @param isHospital true if the location is a hospital
     */

    private static void placeLocationWithConstraints(City city, Location location, boolean isHospital) {
        int width = city.getWidth();
        int height = city.getHeight();
        int area = location.getBuildingArea();

        for (int attempt = 0; attempt < 100; attempt++) {
            int startX = (int) (Math.random() * height);
            int startY = (int) (Math.random() * width);

            if (city.getCityMap()[startX][startY] != null) continue;

            List<int[]> cells = new ArrayList<>();
            boolean[][] visited = new boolean[height][width];
            if (fillArea(city, startX, startY, area, location, isHospital, cells, visited)) {
                if (!isHospital && cells.stream().anyMatch(cell -> isNearHospital(city, cell[0], cell[1]))) continue;

                for (int[] cell : cells) {
                    city.getCityMap()[cell[0]][cell[1]] = new CityCell(location);
                }
                return;
            }
        }
    }

    /**
     * Attempts to fill a contiguous area in the city map for the location.
     * Ensures cells meet constraints such as not being near hospitals if required.
     * @param city       city map
     * @param startX     start x coordinate
     * @param startY     start y coordinate
     * @param areaSize   required area size
     * @param location   location to place
     * @param isHospital is this a hospital
     * @param result     collected cells for placement
     * @param visited    visited cells tracker
     * @return true if the area could be filled
     */

    private static boolean fillArea(City city, int startX, int startY, int areaSize, Location location, boolean isHospital, List<int[]> result, boolean[][] visited) {
        int width = city.getWidth(), height = city.getHeight();
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startX, startY});
        visited[startX][startY] = true;

        while (!queue.isEmpty() && result.size() < areaSize) {
            int[] current = queue.poll();
            int x = current[0], y = current[1];

            if (city.getCityMap()[x][y] != null) continue;
            if (!isHospital && isNearHospital(city, x, y)) continue;

            result.add(current);

            for (int[] dir : new int[][]{{1,0},{-1,0},{0,1},{0,-1}}) {
                int nx = x + dir[0], ny = y + dir[1];
                if (nx >= 0 && ny >= 0 && nx < height && ny < width && !visited[nx][ny]) {
                    visited[nx][ny] = true;
                    queue.add(new int[]{nx, ny});
                }
            }
        }

        return result.size() == areaSize;
    }

    /**
     * Checks if a given cell is adjacent to any hospital location.
     * @param city city map
     * @param x    x coordinate
     * @param y    y coordinate
     * @return true if near a hospital, false otherwise
     */

    private static boolean isNearHospital(City city, int x, int y) {
        int width = city.getWidth();
        int height = city.getHeight();

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;
                int nx = x + dx, ny = y + dy;
                if (nx >= 0 && ny >= 0 && nx < height && ny < width) {
                    CityCell cell = city.getCityMap()[nx][ny];
                    if (cell != null && cell.getLocation().getType() == LocationType.MEDICAL_CENTRE) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Collects all distinct virus mutation stages present in a city cell.
     * Returns a map of mutation stage to virus, sorted in descending order.
     * @param cell the city cell to inspect
     * @return Optional map of mutation stage to Virus if there are multiple people, empty otherwise
     */

    public static Optional<Map<Integer, Virus>> allVirusStagesInCityCell(CityCell cell) {
        List<Person> peopleInCell = cell.getPeople();
        Map<Integer, Virus> virusStages = new TreeMap<>(Comparator.reverseOrder());
        if(peopleInCell.size() != 1) {
            for(Person person: peopleInCell) {
                person.getInfectedBy().ifPresent(infectedBy -> {
                    int stage = infectedBy.getMutationStage();
                    if(!virusStages.containsKey(stage)) {
                        virusStages.put(stage, infectedBy);
                    }
                });
            }
            return Optional.of(virusStages);
        }
        return Optional.empty();
    }
}