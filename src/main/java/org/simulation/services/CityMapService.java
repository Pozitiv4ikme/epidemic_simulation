package org.simulation.services;

import org.simulation.city.City;
import org.simulation.city.CityCell;
import org.simulation.locations.Location;
import org.simulation.locations.LocationFactory;
import org.simulation.locations.LocationType;
import org.simulation.people.Person;
import org.simulation.virus.Virus;

import java.util.*;

public class CityMapService {

    public static CityCell[][] fillCityMap(City city) {
        List<Location> allLocations = LocationFactory.generateLocations(city.getWidth(), city.getHeight(), city.getLocationsData());

        // 1. Place hospitals first
        allLocations.stream()
                .filter(loc -> loc.getType() == LocationType.MEDICAL_CENTRE)
                .forEach(loc -> placeLocationWithConstraints(city, loc, true));

        // 2. Place other locations
        allLocations.stream()
                .filter(loc -> loc.getType() != LocationType.MEDICAL_CENTRE)
                .forEach(loc -> placeLocationWithConstraints(city, loc, false));

        // 3. Fill empty cells with ROAD
        int width = city.getWidth();
        int height = city.getHeight();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (city.getCityMap()[x][y] == null) {
                    Location road = new Location(LocationType.ROAD); // Adjust constructor as needed
                    city.getCityMap()[x][y] = new CityCell(road);
                }
            }
        }

        return city.getCityMap();
    }

    private static boolean placeLocationWithConstraints(City city, Location location, boolean isHospital) {
        int width = city.getWidth();
        int height = city.getHeight();
        int area = location.getBuildingArea();

        for (int attempt = 0; attempt < 100; attempt++) {
            int startX = (int) (Math.random() * width);
            int startY = (int) (Math.random() * height);

            if (city.getCityMap()[startX][startY] != null) continue;

            List<int[]> cells = new ArrayList<>();
            boolean[][] visited = new boolean[width][height];
            if (fillArea(city, startX, startY, area, location, isHospital, cells, visited)) {
                // For non-hospitals, check if any cell is near a hospital only once
                if (!isHospital && cells.stream().anyMatch(cell -> isNearHospital(city, cell[0], cell[1]))) continue;

                for (int[] cell : cells) {
                    city.getCityMap()[cell[0]][cell[1]] = new CityCell(location);
                }
                return true;
            }
        }
        return false;
    }

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
                if (nx >= 0 && ny >= 0 && nx < width && ny < height && !visited[nx][ny]) {
                    visited[nx][ny] = true;
                    queue.add(new int[]{nx, ny});
                }
            }
        }

        return result.size() == areaSize;
    }


    private static boolean isNearHospital(City city, int x, int y) {
        int width = city.getWidth();
        int height = city.getHeight();

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;
                int nx = x + dx, ny = y + dy;
                if (nx >= 0 && ny >= 0 && nx < width && ny < height) {
                    CityCell cell = city.getCityMap()[nx][ny];
                    if (cell != null && cell.getLocation().getType() == LocationType.MEDICAL_CENTRE) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

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