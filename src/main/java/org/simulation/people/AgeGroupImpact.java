package org.simulation.people;

public enum AgeGroupImpact {
    YOUNG(6, 24, 0.1, 2.5, 12.0, 100),
    ADULT(25, 44, 5, 7.5, 40, 80),
    MIDDLE_AGED(45, 64, 20, 15.0, 25.0, 65),
    SENIOR(65, 100, 1.5, 25.0, 30.0, 30);

    private final int minAge;
    private final int maxAge;
    private final double baseMortalityChancePercent;
    private final double baseRecoveryChancePercent;
    private final double baseInfectionChancePercent;
    private final double baseMovementChancePercent;

    AgeGroupImpact(int minAge, int maxAge,
                   double baseInfectionChancePercent,
                   double baseMortalityChancePercent,
                   double baseRecoveryChancePercent,
                   double baseMovementChancePercent) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.baseMortalityChancePercent = baseMortalityChancePercent;
        this.baseRecoveryChancePercent = baseRecoveryChancePercent;
        this.baseInfectionChancePercent = baseInfectionChancePercent;
        this.baseMovementChancePercent = baseMovementChancePercent;
    }

    public int getMinAge() { return minAge; }
    public int getMaxAge() { return maxAge; }
    public double getBaseMortalityChancePercent() { return baseMortalityChancePercent; }
    public double getBaseRecoveryChancePercent() { return baseRecoveryChancePercent; }
    public double getBaseInfectionChancePercent() { return baseInfectionChancePercent; }
    public double getBaseMovementChancePercent() { return baseMovementChancePercent; }

    public static AgeGroupImpact getProfileForAge(int age) {
        for (AgeGroupImpact profile : values()) {
            if (age >= profile.getMinAge() && age <= profile.getMaxAge()) {
                return profile;
            }
        }
        throw new IllegalArgumentException("No mortality/recovery profile found for age: " + age);
    }
}