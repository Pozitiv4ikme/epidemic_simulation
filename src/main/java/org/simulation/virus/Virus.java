package org.simulation.virus;

public class Virus {
    private int infectionProbability;
    private int mutationStage;
    private int lethality;
    private int recoverProbability;

    public Virus mutation(int age) {
        return new Virus();  // stub
    } // im młodszy człowiek tym większy szans na mutację
      // kiedy tworzy się drugi poziom virusa, to on będzie dla wszystkich ten sam
}
