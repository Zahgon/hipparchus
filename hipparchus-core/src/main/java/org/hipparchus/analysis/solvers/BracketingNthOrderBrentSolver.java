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
package org.hipparchus.analysis.solvers;

import org.hipparchus.analysis.UnivariateFunction;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.MathIllegalStateException;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.Precision;

/**
 * This class implements a modification of the <a
 * href="http://mathworld.wolfram.com/BrentsMethod.html"> Brent algorithm</a>.
 * <p>
 * The changes with respect to the original Brent algorithm are:
 * <ul>
 *   <li>the returned value is chosen in the current interval according
 *   to user specified {@link AllowedSolution},</li>
 *   <li>the maximal order for the invert polynomial root search is
 *   user-specified instead of being invert quadratic only</li>
 * </ul><p>
 * The given interval must bracket the root.</p>
 */
public class BracketingNthOrderBrentSolver extends AbstractUnivariateSolver implements BracketedUnivariateSolver<UnivariateFunction> {

    /**
     * Default absolute accuracy.
     */
    private static final double DEFAULT_ABSOLUTE_ACCURACY = 1e-6;

    /**
     * Default maximal order.
     */
    private static final int DEFAULT_MAXIMAL_ORDER = 5;

    /**
     * Maximal aging triggering an attempt to balance the bracketing interval.
     */
    private static final int MAXIMAL_AGING = 2;

    /**
     * Reduction factor for attempts to balance the bracketing interval.
     */
    private static final double REDUCTION_FACTOR = 1.0 / 16.0;

    /**
     * Maximal order.
     */
    private final int maximalOrder;

    /**
     * The kinds of solutions that the algorithm may accept.
     */
    private AllowedSolution allowed;

    /**
     * Construct a solver with default accuracy and maximal order (1e-6 and 5 respectively)
     */
    public BracketingNthOrderBrentSolver() {
        this(DEFAULT_ABSOLUTE_ACCURACY, DEFAULT_MAXIMAL_ORDER);
    }

    /**
     * Construct a solver.
     *
     * @param absoluteAccuracy Absolute accuracy.
     * @param maximalOrder maximal order.
     * @exception MathIllegalArgumentException if maximal order is lower than 2
     */
    public BracketingNthOrderBrentSolver(final double absoluteAccuracy, final int maximalOrder) throws MathIllegalArgumentException {
        super(absoluteAccuracy);
        if (maximalOrder < 2) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.NUMBER_TOO_SMALL, maximalOrder, 2);
        }
        this.maximalOrder = maximalOrder;
        this.allowed = AllowedSolution.ANY_SIDE;
    }

    /**
     * Construct a solver.
     *
     * @param relativeAccuracy Relative accuracy.
     * @param absoluteAccuracy Absolute accuracy.
     * @param maximalOrder maximal order.
     * @exception MathIllegalArgumentException if maximal order is lower than 2
     */
    public BracketingNthOrderBrentSolver(final double relativeAccuracy, final double absoluteAccuracy, final int maximalOrder) throws MathIllegalArgumentException {
        super(relativeAccuracy, absoluteAccuracy);
        if (maximalOrder < 2) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.NUMBER_TOO_SMALL, maximalOrder, 2);
        }
        this.maximalOrder = maximalOrder;
        this.allowed = AllowedSolution.ANY_SIDE;
    }

    /**
     * Construct a solver.
     *
     * @param relativeAccuracy Relative accuracy.
     * @param absoluteAccuracy Absolute accuracy.
     * @param functionValueAccuracy Function value accuracy.
     * @param maximalOrder maximal order.
     * @exception MathIllegalArgumentException if maximal order is lower than 2
     */
    public BracketingNthOrderBrentSolver(final double relativeAccuracy, final double absoluteAccuracy, final double functionValueAccuracy, final int maximalOrder) throws MathIllegalArgumentException {
        super(relativeAccuracy, absoluteAccuracy, functionValueAccuracy);
        if (maximalOrder < 2) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.NUMBER_TOO_SMALL, maximalOrder, 2);
        }
        this.maximalOrder = maximalOrder;
        this.allowed = AllowedSolution.ANY_SIDE;
    }

    /**
     * Get the maximal order.
     * @return maximal order
     */
    public int getMaximalOrder() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected double doSolve() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Find a root and return the containing interval.
     *
     * @return an interval containing the root such that both end points meet the
     * convergence criteria.
     */
    protected Interval doSolveInterval() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Guess an x value by n<sup>th</sup> order inverse polynomial interpolation.
     * <p>
     * The x value is guessed by evaluating polynomial Q(y) at y = targetY, where Q
     * is built such that for all considered points (x<sub>i</sub>, y<sub>i</sub>),
     * Q(y<sub>i</sub>) = x<sub>i</sub>.
     * </p>
     * @param targetY target value for y
     * @param x reference points abscissas for interpolation,
     * note that this array <em>is</em> modified during computation
     * @param y reference points ordinates for interpolation
     * @param start start index of the points to consider (inclusive)
     * @param end end index of the points to consider (exclusive)
     * @return guessed root (will be a NaN if two points share the same y)
     */
    private double guessX(final double targetY, final double[] x, final double[] y, final int start, final int end) {
        // compute Q Newton coefficients by divided differences
        for (int i = start; i < end - 1; ++i) {
            final int delta = i + 1 - start;
            for (int j = end - 1; j > i; --j) {
                x[j] = (x[j] - x[j - 1]) / (y[j] - y[j - delta]);
            }
        }
        // evaluate Q(targetY)
        double x0 = 0;
        for (int j = end - 1; j >= start; --j) {
            x0 = x[j] + x0 * (targetY - y[j]);
        }
        return x0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double solve(int maxEval, UnivariateFunction f, double min, double max, AllowedSolution allowedSolution) throws MathIllegalArgumentException, MathIllegalStateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double solve(int maxEval, UnivariateFunction f, double min, double max, double startValue, AllowedSolution allowedSolution) throws MathIllegalArgumentException, MathIllegalStateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Interval solveInterval(final int maxEval, final UnivariateFunction f, final double min, final double max, final double startValue) throws MathIllegalArgumentException, MathIllegalStateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
