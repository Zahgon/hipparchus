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

import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.optim.ConvergenceChecker;
import org.hipparchus.optim.nonlinear.scalar.GoalType;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.Precision;

/**
 * For a function defined on some interval {@code (lo, hi)}, this class
 * finds an approximation {@code x} to the point at which the function
 * attains its minimum.
 * It implements Richard Brent's algorithm (from his book "Algorithms for
 * Minimization without Derivatives", p. 79) for finding minima of real
 * univariate functions.
 * <br>
 * This code is an adaptation, partly based on the Python code from SciPy
 * (module "optimize.py" v0.5); the original algorithm is also modified
 * <ul>
 *  <li>to use an initial guess provided by the user,</li>
 *  <li>to ensure that the best point encountered is the one returned.</li>
 * </ul>
 */
public class BrentOptimizer extends UnivariateOptimizer {

    /**
     * Golden section.
     */
    private static final double GOLDEN_SECTION = 0.5 * (3 - FastMath.sqrt(5));

    /**
     * Minimum relative tolerance.
     */
    private static final double MIN_RELATIVE_TOLERANCE = 2 * FastMath.ulp(1d);

    /**
     * Relative threshold.
     */
    private final double relativeThreshold;

    /**
     * Absolute threshold.
     */
    private final double absoluteThreshold;

    /**
     * The arguments are used implement the original stopping criterion
     * of Brent's algorithm.
     * {@code abs} and {@code rel} define a tolerance
     * {@code tol = rel |x| + abs}. {@code rel} should be no smaller than
     * <em>2 macheps</em> and preferably not much less than <em>sqrt(macheps)</em>,
     * where <em>macheps</em> is the relative machine precision. {@code abs} must
     * be positive.
     *
     * @param rel Relative threshold.
     * @param abs Absolute threshold.
     * @param checker Additional, user-defined, convergence checking
     * procedure.
     * @throws MathIllegalArgumentException if {@code abs <= 0}.
     * @throws MathIllegalArgumentException if {@code rel < 2 * FastMath.ulp(1d)}.
     */
    public BrentOptimizer(double rel, double abs, ConvergenceChecker<UnivariatePointValuePair> checker) {
        super(checker);
        if (rel < MIN_RELATIVE_TOLERANCE) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.NUMBER_TOO_SMALL, rel, MIN_RELATIVE_TOLERANCE);
        }
        if (abs <= 0) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.NUMBER_TOO_SMALL_BOUND_EXCLUDED, abs, 0);
        }
        relativeThreshold = rel;
        absoluteThreshold = abs;
    }

    /**
     * The arguments are used for implementing the original stopping criterion
     * of Brent's algorithm.
     * {@code abs} and {@code rel} define a tolerance
     * {@code tol = rel |x| + abs}. {@code rel} should be no smaller than
     * <em>2 macheps</em> and preferably not much less than <em>sqrt(macheps)</em>,
     * where <em>macheps</em> is the relative machine precision. {@code abs} must
     * be positive.
     *
     * @param rel Relative threshold.
     * @param abs Absolute threshold.
     * @throws MathIllegalArgumentException if {@code abs <= 0}.
     * @throws MathIllegalArgumentException if {@code rel < 2 * FastMath.ulp(1d)}.
     */
    public BrentOptimizer(double rel, double abs) {
        this(rel, abs, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected UnivariatePointValuePair doOptimize() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Selects the best of two points.
     *
     * @param a Point and value.
     * @param b Point and value.
     * @param isMinim {@code true} if the selected point must be the one with
     * the lowest value.
     * @return the best point, or {@code null} if {@code a} and {@code b} are
     * both {@code null}. When {@code a} and {@code b} have the same function
     * value, {@code a} is returned.
     */
    private UnivariatePointValuePair best(UnivariatePointValuePair a, UnivariatePointValuePair b, boolean isMinim) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        if (isMinim) {
            return a.getValue() <= b.getValue() ? a : b;
        } else {
            return a.getValue() >= b.getValue() ? a : b;
        }
    }
}
