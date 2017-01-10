package org.uma.jmetal.algorithm.multiobjective.emas;

import org.apache.commons.math3.analysis.function.Abs;
import org.uma.jmetal.algorithm.impl.AbstractEmas;
import org.uma.jmetal.algorithm.impl.AbstractEmasAgent;
import org.uma.jmetal.algorithm.impl.AbstractEmasParameters;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import java.util.*;
import java.util.function.Predicate;

/**
 * Created by macie on 22-Nov-16.
 */
public class EmasAgent<S extends Solution<?>> extends AbstractEmasAgent<S> {

    private final Problem<S> problem;
    private final CrossoverOperator<S> crossoverOperator;
    private final MutationOperator<S> mutationOperator;
    private final Comparator<S> solutionComparator;

    protected EmasAgent(Problem<S> problem, AbstractEmasParameters parameters, CrossoverOperator<S> crossoverOperator,
                        MutationOperator<S> mutationOperator, Comparator<S> solutionComparator) {

        super(problem.createSolution(), parameters);

        this.problem = problem;
        this.crossoverOperator = crossoverOperator;
        this.mutationOperator = mutationOperator;
        this.solutionComparator = solutionComparator;
    }

    protected EmasAgent(Problem<S> problem, AbstractEmasParameters parameters, CrossoverOperator<S> crossoverOperator,
                        MutationOperator<S> mutationOperator, Comparator<S> solutionComparator, S solution) {

        super(solution, parameters);

        this.problem = problem;
        this.crossoverOperator = crossoverOperator;
        this.mutationOperator = mutationOperator;
        this.solutionComparator = solutionComparator;
    }

    @Override
    public S getSolution() {
        return solution;
    }

    @Override
    public void transferEnergy(int amount) {
        energyLevel += amount;
    }

    @Override
    protected void die(List<AbstractEmasAgent<S>> population) {
        population.remove(this);
        population.add(new EmasAgent<>(problem, parameters, crossoverOperator, mutationOperator, solutionComparator));
    }

    @Override
    protected void migrate(List<AbstractEmasAgent<S>> population) {

    }

    @Override
    protected void meet(List<AbstractEmasAgent<S>> population) {
        AbstractEmasAgent<S> otherAgent = getRandomAgent(population, ind -> true);

        int compareResult = solutionComparator.compare(this.getSolution(), otherAgent.getSolution());
        if (compareResult == 0) {
            return;
        }

        AbstractEmasAgent betterAgent;
        AbstractEmasAgent worseAgent;

        if (compareResult < 0) {
            betterAgent = this;
            worseAgent = otherAgent;
        } else {
            betterAgent = otherAgent;
            worseAgent = this;
        }

        betterAgent.transferEnergy(parameters.getEnergyTransferredOnMeeting());
        worseAgent.transferEnergy(-parameters.getEnergyTransferredOnMeeting());
    }

    @Override
    protected void reproduce(List<AbstractEmasAgent<S>> population) {
        if (population.stream()
                .filter(agent -> agent.getEnergyLevel() > parameters.getReproductionThreshold())
                .count() < 2) {
            return;
        }

        AbstractEmasAgent<S> otherAgent = getRandomAgent(population,
                ind -> ind.getEnergyLevel() > parameters.getReproductionThreshold());

        this.transferEnergy(parameters.getStartingEnergyLevel());
        otherAgent.transferEnergy(parameters.getStartingEnergyLevel());

        List<S> parents = Arrays.asList(this.getSolution(), otherAgent.getSolution());
        List<S> offspring = crossoverOperator.execute(parents);

        mutationOperator.execute(offspring.get(0));
        mutationOperator.execute(offspring.get(1));

        AbstractEmasAgent<S> firstOffspring = new EmasAgent<>(problem, parameters, crossoverOperator, mutationOperator,
                solutionComparator, offspring.get(0));
        AbstractEmasAgent<S> secondOffspring = new EmasAgent<>(problem, parameters, crossoverOperator, mutationOperator,
                solutionComparator, offspring.get(1));

        Map<AbstractEmasAgent<S>, Integer> wins = new HashMap<>();
        wins.put(this, 0);
        wins.put(otherAgent, 0);
        wins.put(firstOffspring, 0);
        wins.put(secondOffspring, 0);

        for (int i = 0; i < 3; i++) {
            for (int j = i + 1; j < 4; j++) {
                AbstractEmasAgent<S> agent1 = (AbstractEmasAgent<S>) wins.keySet().toArray()[i];
                AbstractEmasAgent<S> agent2 = (AbstractEmasAgent<S>) wins.keySet().toArray()[j];

                int result = solutionComparator.compare(agent1.getSolution(), agent2.getSolution());
                if (result < 0) {
                    wins.put(agent1, wins.get(agent1) + 1);
                } else if (result > 0) {
                    wins.put(agent2, wins.get(agent2) + 1);
                }
            }
        }

        List<AbstractEmasAgent<S>> sorted = new LinkedList<>();
        wins.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEachOrdered(e -> sorted.add(e.getKey()));

        population.remove(this);
        population.remove(otherAgent);
        population.addAll(sorted.subList(2, 4));
    }

    private AbstractEmasAgent<S> getRandomAgent(List<AbstractEmasAgent<S>> population, Predicate<AbstractEmasAgent<S>> predicate) {
        AbstractEmasAgent<S> agent;

        do {
            agent = population.get(JMetalRandom.getInstance().nextInt(0, population.size()-1));
        } while (agent == this || !predicate.test(agent));

        return agent;
    }
}
