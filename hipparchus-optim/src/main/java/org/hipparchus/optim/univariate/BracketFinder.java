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

import org.hipparchus.analysis.UnivariateFunction;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.optim.nonlinear.scalar.GoalType;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.Incrementor;

/**
 * Provide an interval that brackets a local optimum of a function.
 * This code is based on a Python implementation (from <em>SciPy</em>,
 * module {@code optimize.py} v0.5).
 */
public class BracketFinder {

    /**
     * Tolerance to avoid division by zero.
     */
    private static final double EPS_MIN = 1e-21;

    /**
     * Golden section.
     */
    private static final double GOLD = 1.618034;

    /**
     * Factor for expanding the interval.
     */
    private final double growLimit;

    /**
     * Number of allowed function evaluations.
     */
    private final int maxEvaluations;

    /**
     * Number of function evaluations performed in the last search.
     */
    private int evaluations;

    /**
     * Lower bound of the bracket.
     */
    private double lo;

    /**
     * Higher bound of the bracket.
     */
    private double hi;

    /**
     * Point inside the bracket.
     */
    private double mid;

    /**
     * Function value at {@link #lo}.
     */
    private double fLo;

    /**
     * Function value at {@link #hi}.
     */
    private double fHi;

    /**
     * Function value at {@link #mid}.
     */
    private double fMid;

    /**
     * Constructor with default values {@code 100, 500} (see the
     * {@link #BracketFinder(double,int) other constructor}).
     */
    public BracketFinder() {
        this(100, 500);
    }

    /**
     * Create a bracketing interval finder.
     *
     * @param growLimit Expanding factor.
     * @param maxEvaluations Maximum number of evaluations allowed for finding
     * a bracketing interval.
     */
    public BracketFinder(double growLimit, int maxEvaluations) {
        if (growLimit <= 0) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.NUMBER_TOO_SMALL_BOUND_EXCLUDED, growLimit, 0);
        }
        if (maxEvaluations <= 0) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.NUMBER_TOO_SMALL_BOUND_EXCLUDED, maxEvaluations, 0);
        }
        this.growLimit = growLimit;
        this.maxEvaluations = maxEvaluations;
    }

    /**
     * Search new points that bracket a local optimum of the function.
     *
     * @param func Function whose optimum should be bracketed.
     * @param goal {@link GoalType Goal type}.
     * @param xA Initial point.
     * @param xB Initial point.
     * @throws org.hipparchus.exception.MathIllegalStateException if the maximum number of evaluations
     * is exceeded.
     */
    public void search(UnivariateFunction func, GoalType goal, double xA, double xB) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get maximum number of evaluations.
     * @return the maximum number of evaluations
     */
    public int getMaxEvaluations() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get number of evaluations.
     * @return the number of evaluations
     */
    public int getEvaluations() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get lower bound of the bracket.
     * @return the lower bound of the bracket
     * @see #getFLo()
     */
    public double getLo() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get function value at {@link #getLo()}.
     * @return function value at {@link #getLo()}
     */
    public double getFLo() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get higher bound of the bracket.
     * @return the higher bound of the bracket
     * @see #getFHi()
     */
    public double getHi() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get function value at {@link #getHi()}.
     * @return function value at {@link #getHi()}
     */
    public double getFHi() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get a point in the middle of the bracket.
     * @return a point in the middle of the bracket
     * @see #getFMid()
     */
    public double getMid() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get function value at {@link #getMid()}.
     * @return function value at {@link #getMid()}
     */
    public double getFMid() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Utility for incrementing a counter at each function evaluation.
     */
    private class FunctionEvaluator {

        /**
         * Function.
         */
        private final UnivariateFunction func;

        /**
         * Counter.
         */
        private final Incrementor inc;

        /**
         * Simple constructor.
         * @param func Function.
         */
        FunctionEvaluator(UnivariateFunction func) {
            this.func = func;
            inc = new Incrementor(maxEvaluations);
            evaluations = 0;
        }

        /**
         * Evaluate function.
         * @param x Argument.
         * @return {@code f(x)}
         * @throws org.hipparchus.exception.MathIllegalStateException if the maximal number of evaluations is
         * exceeded.
         */
        double value(double x) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
