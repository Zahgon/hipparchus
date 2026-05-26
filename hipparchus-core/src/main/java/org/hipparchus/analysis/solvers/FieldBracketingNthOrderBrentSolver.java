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

import org.hipparchus.CalculusFieldElement;
import org.hipparchus.Field;
import org.hipparchus.analysis.CalculusFieldUnivariateFunction;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.MathIllegalStateException;
import org.hipparchus.exception.NullArgumentException;
import org.hipparchus.util.Incrementor;
import org.hipparchus.util.MathArrays;
import org.hipparchus.util.MathUtils;

/**
 * This class implements a modification of the <a
 * href="http://mathworld.wolfram.com/BrentsMethod.html"> Brent algorithm</a>.
 * <p>
 * The changes with respect to the original Brent algorithm are:
 * <ul>
 *   <li>the returned value is chosen in the current interval according
 *   to user specified {@link AllowedSolution}</li>
 *   <li>the maximal order for the invert polynomial root search is
 *   user-specified instead of being invert quadratic only</li>
 * </ul><p>
 * The given interval must bracket the root.</p>
 *
 * @param <T> the type of the field elements
 */
public class FieldBracketingNthOrderBrentSolver<T extends CalculusFieldElement<T>> implements BracketedRealFieldUnivariateSolver<T> {

    /**
     * Maximal aging triggering an attempt to balance the bracketing interval.
     */
    private static final int MAXIMAL_AGING = 2;

    /**
     * Field to which the elements belong.
     */
    private final Field<T> field;

    /**
     * Maximal order.
     */
    private final int maximalOrder;

    /**
     * Function value accuracy.
     */
    private final T functionValueAccuracy;

    /**
     * Absolute accuracy.
     */
    private final T absoluteAccuracy;

    /**
     * Relative accuracy.
     */
    private final T relativeAccuracy;

    /**
     * Evaluations counter.
     */
    private Incrementor evaluations;

    /**
     * Construct a solver.
     *
     * @param relativeAccuracy Relative accuracy.
     * @param absoluteAccuracy Absolute accuracy.
     * @param functionValueAccuracy Function value accuracy.
     * @param maximalOrder maximal order.
     * @exception MathIllegalArgumentException if maximal order is lower than 2
     */
    public FieldBracketingNthOrderBrentSolver(final T relativeAccuracy, final T absoluteAccuracy, final T functionValueAccuracy, final int maximalOrder) throws MathIllegalArgumentException {
        if (maximalOrder < 2) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.NUMBER_TOO_SMALL, maximalOrder, 2);
        }
        this.field = relativeAccuracy.getField();
        this.maximalOrder = maximalOrder;
        this.absoluteAccuracy = absoluteAccuracy;
        this.relativeAccuracy = relativeAccuracy;
        this.functionValueAccuracy = functionValueAccuracy;
        this.evaluations = new Incrementor();
    }

    /**
     * Get the maximal order.
     * @return maximal order
     */
    public int getMaximalOrder() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the maximal number of function evaluations.
     *
     * @return the maximal number of function evaluations.
     */
    @Override
    public int getMaxEvaluations() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the number of evaluations of the objective function.
     * The number of evaluations corresponds to the last call to the
     * {@code optimize} method. It is 0 if the method has not been
     * called yet.
     *
     * @return the number of evaluations of the objective function.
     */
    @Override
    public int getEvaluations() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the absolute accuracy.
     * @return absolute accuracy
     */
    @Override
    public T getAbsoluteAccuracy() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the relative accuracy.
     * @return relative accuracy
     */
    @Override
    public T getRelativeAccuracy() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the function accuracy.
     * @return function accuracy
     */
    @Override
    public T getFunctionValueAccuracy() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Solve for a zero in the given interval.
     * A solver may require that the interval brackets a single zero root.
     * Solvers that do require bracketing should be able to handle the case
     * where one of the endpoints is itself a root.
     *
     * @param maxEval Maximum number of evaluations.
     * @param f Function to solve.
     * @param min Lower bound for the interval.
     * @param max Upper bound for the interval.
     * @param allowedSolution The kind of solutions that the root-finding algorithm may
     * accept as solutions.
     * @return a value where the function is zero.
     * @exception NullArgumentException if f is null.
     * @exception MathIllegalArgumentException if root cannot be bracketed
     */
    @Override
    public T solve(final int maxEval, final CalculusFieldUnivariateFunction<T> f, final T min, final T max, final AllowedSolution allowedSolution) throws MathIllegalArgumentException, NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Solve for a zero in the given interval, start at {@code startValue}.
     * A solver may require that the interval brackets a single zero root.
     * Solvers that do require bracketing should be able to handle the case
     * where one of the endpoints is itself a root.
     *
     * @param maxEval Maximum number of evaluations.
     * @param f Function to solve.
     * @param min Lower bound for the interval.
     * @param max Upper bound for the interval.
     * @param startValue Start value to use.
     * @param allowedSolution The kind of solutions that the root-finding algorithm may
     * accept as solutions.
     * @return a value where the function is zero.
     * @exception NullArgumentException if f is null.
     * @exception MathIllegalArgumentException if root cannot be bracketed
     */
    @Override
    public T solve(final int maxEval, final CalculusFieldUnivariateFunction<T> f, final T min, final T max, final T startValue, final AllowedSolution allowedSolution) throws MathIllegalArgumentException, NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Interval<T> solveInterval(int maxEval, CalculusFieldUnivariateFunction<T> f, T min, T max, T startValue) throws MathIllegalArgumentException, MathIllegalStateException {
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
    private T guessX(final T targetY, final T[] x, final T[] y, final int start, final int end) {
        // compute Q Newton coefficients by divided differences
        for (int i = start; i < end - 1; ++i) {
            final int delta = i + 1 - start;
            for (int j = end - 1; j > i; --j) {
                x[j] = x[j].subtract(x[j - 1]).divide(y[j].subtract(y[j - delta]));
            }
        }
        // evaluate Q(targetY)
        T x0 = field.getZero();
        for (int j = end - 1; j >= start; --j) {
            x0 = x[j].add(x0.multiply(targetY.subtract(y[j])));
        }
        return x0;
    }
}
