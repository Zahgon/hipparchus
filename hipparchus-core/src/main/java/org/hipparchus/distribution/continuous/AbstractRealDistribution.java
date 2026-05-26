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
package org.hipparchus.distribution.continuous;

import java.io.Serializable;
import org.hipparchus.analysis.UnivariateFunction;
import org.hipparchus.analysis.solvers.UnivariateSolverUtils;
import org.hipparchus.distribution.RealDistribution;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.MathUtils;

/**
 * Base class for probability distributions on the reals.
 * <p>
 * Default implementations are provided for some of the methods
 * that do not vary from distribution to distribution.
 */
public abstract class AbstractRealDistribution implements RealDistribution, Serializable {

    /**
     * Default absolute accuracy for inverse cumulative computation.
     */
    protected static final double DEFAULT_SOLVER_ABSOLUTE_ACCURACY = 1e-9;

    /**
     * Serializable version identifier
     */
    private static final long serialVersionUID = 20160320L;

    /**
     * Inverse cumulative probability accuracy.
     */
    private final double solverAbsoluteAccuracy;

    /**
     * Simple constructor.
     * @param solverAbsoluteAccuracy the absolute accuracy to use when
     * computing the inverse cumulative probability.
     */
    protected AbstractRealDistribution(double solverAbsoluteAccuracy) {
        this.solverAbsoluteAccuracy = solverAbsoluteAccuracy;
    }

    /**
     * Create a real distribution with default solver absolute accuracy.
     */
    protected AbstractRealDistribution() {
        this.solverAbsoluteAccuracy = DEFAULT_SOLVER_ABSOLUTE_ACCURACY;
    }

    /**
     * For a random variable {@code X} whose values are distributed according
     * to this distribution, this method returns {@code P(x0 < X <= x1)}.
     *
     * @param x0 Lower bound (excluded).
     * @param x1 Upper bound (included).
     * @return the probability that a random variable with this distribution
     * takes a value between {@code x0} and {@code x1}, excluding the lower
     * and including the upper endpoint.
     * @throws MathIllegalArgumentException if {@code x0 > x1}.
     * The default implementation uses the identity
     * {@code P(x0 < X <= x1) = P(X <= x1) - P(X <= x0)}
     */
    @Override
    public double probability(double x0, double x1) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * The default implementation returns
     * <ul>
     * <li>{@link #getSupportLowerBound()} for {@code p = 0},</li>
     * <li>{@link #getSupportUpperBound()} for {@code p = 1}.</li>
     * </ul>
     */
    @Override
    public double inverseCumulativeProbability(final double p) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the solver absolute accuracy for inverse cumulative computation.
     * You can override this method in order to use a Brent solver with an
     * absolute accuracy different from the default.
     *
     * @return the maximum absolute error in inverse cumulative probability estimates
     */
    protected double getSolverAbsoluteAccuracy() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * <p>
     * The default implementation simply computes the logarithm of {@code density(x)}.
     */
    @Override
    public double logDensity(double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
