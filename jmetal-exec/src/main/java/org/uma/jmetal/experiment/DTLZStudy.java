//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
//
//  You should have received a copy of the GNU Lesser General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.

package org.uma.jmetal.experiment;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.emas.EmasBuilder;
import org.uma.jmetal.algorithm.multiobjective.emas.EmasParameters;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.smpso.SMPSOBuilder;
import org.uma.jmetal.algorithm.multiobjective.spea2.SPEA2Builder;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.multiobjective.dtlz.DTLZ1;
import org.uma.jmetal.problem.multiobjective.dtlz.DTLZ2;
import org.uma.jmetal.problem.multiobjective.dtlz.DTLZ3;
import org.uma.jmetal.problem.multiobjective.zdt.*;
import org.uma.jmetal.qualityindicator.impl.*;
import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.archive.impl.CrowdingDistanceArchive;
import org.uma.jmetal.util.comparator.DominanceComparator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;
import org.uma.jmetal.util.experiment.Experiment;
import org.uma.jmetal.util.experiment.ExperimentBuilder;
import org.uma.jmetal.util.experiment.component.*;
import org.uma.jmetal.util.experiment.util.TaggedAlgorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DTLZStudy {
    private static final int INDEPENDENT_RUNS = 3;

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new JMetalException("Missing argument: experiment base directory");
        }
        String experimentBaseDirectory = args[0];

        List<Problem<DoubleSolution>> problemList = Arrays.<Problem<DoubleSolution>>asList(new DTLZ1(),
                new DTLZ2(),
                new DTLZ3());

        List<String> referenceFrontFileNames = Arrays.asList("DTLZ1.3D.pf", "DTLZ2.3D.pf", "DTLZ3.3D.pf");

        List<TaggedAlgorithm<List<DoubleSolution>>> algorithmList = configureAlgorithmList(problemList, INDEPENDENT_RUNS);

        Experiment<DoubleSolution, List<DoubleSolution>> experiment =
                new ExperimentBuilder<DoubleSolution, List<DoubleSolution>>("DTLZStudy")
                        .setAlgorithmList(algorithmList)
                        .setProblemList(problemList)
                        .setReferenceFrontDirectory("/pareto_fronts")
                        .setReferenceFrontFileNames(referenceFrontFileNames)
                        .setExperimentBaseDirectory(experimentBaseDirectory)
                        .setOutputParetoFrontFileName("FUN")
                        .setOutputParetoSetFileName("VAR")
                        .setIndicatorList(Arrays.asList(
                                new Epsilon<>(),
                                new Spread<>(),
                                new GenerationalDistance<>(),
                                new PISAHypervolume<>(),
                                new InvertedGenerationalDistance<>(),
                                new InvertedGenerationalDistancePlus<>()))
                        .setIndependentRuns(INDEPENDENT_RUNS)
                        .setNumberOfCores(4)
                        .build();

        new ExecuteAlgorithms<>(experiment).run();
        new ComputeQualityIndicators<>(experiment).run();
        new GenerateLatexTablesWithStatistics(experiment).run();
        new GenerateWilcoxonTestTablesWithR<>(experiment).run();
        new GenerateFriedmanTestTables<>(experiment).run();
        new GenerateBoxplotsWithR<>(experiment).setRows(3).setColumns(3).setDisplayNotch().run();
    }

    /**
     * The algorithm list is composed of pairs {@link Algorithm} + {@link Problem} which form part of a
     * {@link TaggedAlgorithm}, which is a decorator for class {@link Algorithm}.
     *
     * @param problemList
     * @return
     */
    static List<TaggedAlgorithm<List<DoubleSolution>>> configureAlgorithmList(
            List<Problem<DoubleSolution>> problemList,
            int independentRuns) {
        List<TaggedAlgorithm<List<DoubleSolution>>> algorithms = new ArrayList<>();

        for (int run = 0; run < independentRuns; run++) {

            for (int i = 0; i < problemList.size(); i++) {
                Algorithm<List<DoubleSolution>> algorithm = new NSGAIIBuilder<>(problemList.get(i),
                        new SBXCrossover(1.0, 20.0),
                        new PolynomialMutation(1.0 / problemList.get(i).getNumberOfVariables(), 20.0))
                        .setMaxEvaluations(2000)
                        .setPopulationSize(100)
                        .build();
                algorithms.add(new TaggedAlgorithm<>(algorithm, problemList.get(i), run));
            }

            for (int i = 0; i < problemList.size(); i++) {
                Algorithm<List<DoubleSolution>> algorithm = new SPEA2Builder<>(problemList.get(i),
                        new SBXCrossover(1.0, 10.0),
                        new PolynomialMutation(1.0 / problemList.get(i).getNumberOfVariables(), 20.0))
                        .setMaxIterations(200)
                        .setPopulationSize(100)
                        .build();
                algorithms.add(new TaggedAlgorithm<>(algorithm, problemList.get(i), run));
            }

            for (int i = 0; i < problemList.size(); i++) {
                Algorithm<List<DoubleSolution>> algorithm = new EmasBuilder<>(problemList.get(i),
                        new EmasParameters(5, 4, 0, 7, 15),
                        new SBXCrossover(1.0, 20.0),
                        new PolynomialMutation(1.0 / problemList.get(i).getNumberOfVariables(), 20.0),
                        new DominanceComparator<>())
                        .setMaxIterations(100)
                        .setPopulationSize(100)
                        .build();
                algorithms.add(new TaggedAlgorithm<>(algorithm, problemList.get(i), run));
            }
        }
        return algorithms;
    }
}
