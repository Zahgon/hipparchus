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
package org.hipparchus.analysis.integration;

import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.MathIllegalStateException;
import org.hipparchus.util.FastMath;

/**
 * Implements the <a href="http://mathworld.wolfram.com/TrapezoidalRule.html">
 * Trapezoid Rule</a> for integration of real univariate functions. For
 * reference, see <b>Introduction to Numerical Analysis</b>, ISBN 038795452X,
 * chapter 3.
 * <p>
 * The function should be integrable.</p>
 */
public class TrapezoidIntegrator extends BaseAbstractUnivariateIntegrator {

    /**
     * Maximum number of iterations for trapezoid.
     */
    public static final int TRAPEZOID_MAX_ITERATIONS_COUNT = 64;

    /**
     * Intermediate result.
     */
    private double s;

    /**
     * Build a trapezoid integrator with given accuracies and iterations counts.
     * @param relativeAccuracy relative accuracy of the result
     * @param absoluteAccuracy absolute accuracy of the result
     * @param minimalIterationCount minimum number of iterations
     * @param maximalIterationCount maximum number of iterations
     * (must be less than or equal to {@link #TRAPEZOID_MAX_ITERATIONS_COUNT}
     * @exception MathIllegalArgumentException if minimal number of iterations
     * is not strictly positive
     * @exception MathIllegalArgumentException if maximal number of iterations
     * is lesser than or equal to the minimal number of iterations
     * @exception MathIllegalArgumentException if maximal number of iterations
     * is greater than {@link #TRAPEZOID_MAX_ITERATIONS_COUNT}
     */
    public TrapezoidIntegrator(final double relativeAccuracy, final double absoluteAccuracy, final int minimalIterationCount, final int maximalIterationCount) throws MathIllegalArgumentException {
        super(relativeAccuracy, absoluteAccuracy, minimalIterationCount, maximalIterationCount);
        if (maximalIterationCount > TRAPEZOID_MAX_ITERATIONS_COUNT) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.NUMBER_TOO_LARGE_BOUND_EXCLUDED, maximalIterationCount, TRAPEZOID_MAX_ITERATIONS_COUNT);
        }
    }

    /**
     * Build a trapezoid integrator with given iteration counts.
     * @param minimalIterationCount minimum number of iterations
     * @param maximalIterationCount maximum number of iterations
     * (must be less than or equal to {@link #TRAPEZOID_MAX_ITERATIONS_COUNT}
     * @exception MathIllegalArgumentException if minimal number of iterations
     * is not strictly positive
     * @exception MathIllegalArgumentException if maximal number of iterations
     * is lesser than or equal to the minimal number of iterations
     * @exception MathIllegalArgumentException if maximal number of iterations
     * is greater than {@link #TRAPEZOID_MAX_ITERATIONS_COUNT}
     */
    public TrapezoidIntegrator(final int minimalIterationCount, final int maximalIterationCount) throws MathIllegalArgumentException {
        super(minimalIterationCount, maximalIterationCount);
        if (maximalIterationCount > TRAPEZOID_MAX_ITERATIONS_COUNT) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.NUMBER_TOO_LARGE_BOUND_EXCLUDED, maximalIterationCount, TRAPEZOID_MAX_ITERATIONS_COUNT);
        }
    }

    /**
     * Construct a trapezoid integrator with default settings.
     * (max iteration count set to {@link #TRAPEZOID_MAX_ITERATIONS_COUNT})
     */
    public TrapezoidIntegrator() {
        super(DEFAULT_MIN_ITERATIONS_COUNT, TRAPEZOID_MAX_ITERATIONS_COUNT);
    }

    /**
     * Compute the n-th stage integral of trapezoid rule. This function
     * should only be called by API <code>integrate()</code> in the package.
     * To save time it does not verify arguments - caller does.
     * <p>
     * The interval is divided equally into 2^n sections rather than an
     * arbitrary m sections because this configuration can best utilize the
     * already computed values.</p>
     *
     * @param baseIntegrator integrator holding integration parameters
     * @param n the stage of 1/2 refinement, n = 0 is no refinement
     * @return the value of n-th stage integral
     * @throws MathIllegalStateException if the maximal number of evaluations
     * is exceeded.
     */
    double stage(final BaseAbstractUnivariateIntegrator baseIntegrator, final int n) throws MathIllegalStateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected double doIntegrate() throws MathIllegalArgumentException, MathIllegalStateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
