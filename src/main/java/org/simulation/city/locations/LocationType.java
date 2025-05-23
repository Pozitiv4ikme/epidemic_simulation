package org.simulation.city.locations;

public enum LocationType {
    MEDICAL_CENTRE('M', "Medical centre", true),
    HOUSE('H', "House", true),
    WORKPLACE('W', "Workplace", true),
    SCHOOL('S', "School", true),
    ROAD('.', "Road", false);

    private final char baseDisplaySymbol;
    private final String name;
    private final boolean hasHealthImpact;

    LocationType(char baseDisplaySymbol, String name, boolean hasHealthImpact) {
        this.baseDisplaySymbol = baseDisplaySymbol;
        this.name = name;
        this.hasHealthImpact = hasHealthImpact;
    }

    public char getBaseDisplaySymbol() {
        return baseDisplaySymbol;
    }

    public String getName() {
        return name;
    }

    public boolean hasHealthImpact() {
        return hasHealthImpact;
    }
}
