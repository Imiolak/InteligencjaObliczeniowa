package org.uma.jmetal.algorithm.impl;

public abstract class AbstractEmasParameters {

    public abstract int getStartingEnergyLevel();
    public abstract int getDeathThreshold();
    public abstract int getMigrationThreshold();
    public abstract int getReproductionThreshold();
    public abstract int getEnergyTransferredOnMeeting();
}
