package org.uma.jmetal.algorithm.impl;

import org.uma.jmetal.solution.Solution;

import java.util.List;

public abstract class AbstractEmasAgent<S extends Solution> {

    protected final AbstractEmasParameters parameters;

    protected S solution;
    protected int energyLevel;

    protected AbstractEmasAgent(S solution, AbstractEmasParameters parameters) {
        this.solution = solution;
        this.parameters = parameters;
        this.energyLevel = parameters.getStartingEnergyLevel();
    }

    public abstract S getSolution();
    public abstract void transferEnergy(int amount);

    protected abstract void die(List<AbstractEmasAgent<S>> population);
    protected abstract void migrate(List<AbstractEmasAgent<S>> population);
    protected abstract void meet(List<AbstractEmasAgent<S>> population);
    protected abstract void reproduce(List<AbstractEmasAgent<S>> population);

    public int getEnergyLevel() {
        return energyLevel;
    }

    public void executeLifeStep(List<AbstractEmasAgent<S>> population) {
        if (energyLevel <= this.parameters.getDeathThreshold()) {
            die(population);
        }
        if (energyLevel >= this.parameters.getMigrationThreshold()) {
            migrate(population);
        }
        meet(population);
        if (energyLevel > this.parameters.getReproductionThreshold()) {
            reproduce(population);
        }
    }
}
