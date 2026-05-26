/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * This is not the original file distributed by the Apache Software Foundation
 * It has been modified by the Hipparchus project
 */
package org.hipparchus.optim.univariate;

import java.util.Arrays;
import java.util.Comparator;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalStateException;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.optim.MaxEval;
import org.hipparchus.optim.OptimizationData;
import org.hipparchus.optim.nonlinear.scalar.GoalType;
import org.hipparchus.random.RandomGenerator;

/**
 * Special implementation of the {@link UnivariateOptimizer} interface
 * adding multi-start features to an existing optimizer.
 * <br>
 * This class wraps an optimizer in order to use it several times in
 * turn with different starting points (trying to avoid being trapped
 * in a local extremum when looking for a global one).
 */
public class MultiStartUnivariateOptimizer extends UnivariateOptimizer {

    /**
     * Underlying classical optimizer.
     */
    private final UnivariateOptimizer optimizer;

    /**
     * Number of evaluations already performed for all starts.
     */
    private int totalEvaluations;

    /**
     * Number of starts to go.
     */
    private final int starts;

    /**
     * Random generator for multi-start.
     */
    private final RandomGenerator generator;

    /**
     * Found optima.
     */
    private UnivariatePointValuePair[] optima;

    /**
     * Optimization data.
     */
    private OptimizationData[] optimData;

    /**
     * Location in {@link #optimData} where the updated maximum
     * number of evaluations will be stored.
     */
    private int maxEvalIndex = -1;

    /**
     * Location in {@link #optimData} where the updated start value
     * will be stored.
     */
    private int searchIntervalIndex = -1;

    /**
     * Create a multi-start optimizer from a single-start optimizer.
     *
     * @param optimizer Single-start optimizer to wrap.
     * @param starts Number of starts to perform. If {@code starts == 1},
     * the {@code optimize} methods will return the same solution as
     * {@code optimizer} would.
     * @param generator Random generator to use for restarts.
     * @throws MathIllegalArgumentException if {@code starts < 1}.
     */
    public MultiStartUnivariateOptimizer(final UnivariateOptimizer optimizer, final int starts, final RandomGenerator generator) {
        super(optimizer.getConvergenceChecker());
        if (starts < 1) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.NUMBER_TOO_SMALL, starts, 1);
        }
        this.optimizer = optimizer;
        this.starts = starts;
        this.generator = generator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getEvaluations() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets all the optima found during the last call to {@code optimize}.
     * The optimizer stores all the optima found during a set of
     * restarts. The {@code optimize} method returns the best point only.
     * This method returns all the points found at the end of each starts,
     * including the best one already returned by the {@code optimize} method.
     * <br>
     * The returned array as one element for each start as specified
     * in the constructor. It is ordered with the results from the
     * runs that did converge first, sorted from best to worst
     * objective value (i.e in ascending order if minimizing and in
     * descending order if maximizing), followed by {@code null} elements
     * corresponding to the runs that did not converge. This means all
     * elements will be {@code null} if the {@code optimize} method did throw
     * an exception.
     * This also means that if the first element is not {@code null}, it is
     * the best point found across all starts.
     *
     * @return an array containing the optima.
     * @throws MathIllegalStateException if {@link #optimize(OptimizationData[])
     * optimize} has not been called.
     */
    public UnivariatePointValuePair[] getOptima() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * @throws MathIllegalStateException if {@code optData} does not contain an
     * instance of {@link MaxEval} or {@link SearchInterval}.
     */
    @Override
    public UnivariatePointValuePair optimize(OptimizationData... optData) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected UnivariatePointValuePair doOptimize() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Sort the optima from best to worst, followed by {@code null} elements.
     *
     * @param goal Goal type.
     */
    private void sortPairs(final GoalType goal) {
        Arrays.sort(optima, new Comparator<UnivariatePointValuePair>() {

            /**
             * {@inheritDoc}
             */
            @Override
            public int compare(final UnivariatePointValuePair o1, final UnivariatePointValuePair o2) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }
        });
    }
}
