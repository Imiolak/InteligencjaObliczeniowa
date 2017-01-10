package org.uma.jmetal.algorithm.multiobjective.emas;

import org.uma.jmetal.algorithm.impl.AbstractEmasParameters;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.AlgorithmBuilder;
import org.uma.jmetal.util.JMetalException;

import java.util.Comparator;

/**
 * Created by Piotr on 2017-01-08.
 */
public class EmasBuilder<S extends Solution<?>> implements AlgorithmBuilder<Emas<S>> {

    /**
     * EmasBuilder class
     */
    private final Problem<S> problem;
    private int maxIterations;
    private int populationSize;
    private AbstractEmasParameters emasParameters;
    private CrossoverOperator<S> crossoverOperator;
    private MutationOperator<S> mutationOperator;
    private Comparator<S> solutionComparator;


    /**
     * EmasBuilder constructor
     */
    public EmasBuilder(Problem<S> problem, AbstractEmasParameters emasParameters,
                       CrossoverOperator<S> crossoverOperator,
                       MutationOperator<S> mutationOperator,
                       Comparator<S> solutionComparator) {
        this.problem = problem;
        maxIterations = 25000;
        populationSize = 100;
        this.emasParameters = emasParameters;
        this.crossoverOperator = crossoverOperator ;
        this.mutationOperator = mutationOperator ;
        this.solutionComparator = solutionComparator;
    }

    public EmasBuilder<S> setMaxIterations(int maxIterations) {
        if (maxIterations < 0) {
            throw new JMetalException("maxIterations is negative: " + maxIterations);
        }
        this.maxIterations = maxIterations;

        return this;
    }

    public EmasBuilder<S> setPopulationSize(int populationSize) {
        if (populationSize < 0) {
            throw new JMetalException("Population size is negative: " + populationSize);
        }

        this.populationSize = populationSize;

        return this;
    }

    public EmasBuilder<S> setEmasParameters(AbstractEmasParameters emasParameters) {
        this.emasParameters = emasParameters;

        return this;
    }

    public Emas<S> build() {
        Emas<S> algorithm = new Emas<S>(problem, populationSize, maxIterations,
                emasParameters, crossoverOperator, mutationOperator, solutionComparator);
        return algorithm ;
    }

    /* Getters */
    public Problem<S> getProblem() {
        return problem;
    }

    public int getMaxIterations() {
        return maxIterations;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public AbstractEmasParameters getEmasParameters() {
        return emasParameters;
    }

    public CrossoverOperator<S> getCrossoverOperator() {
        return crossoverOperator;
    }

    public MutationOperator<S> getMutationOperator() {
        return mutationOperator;
    }

    public Comparator<S> getSolutionComparator() {
        return solutionComparator;
    }
}