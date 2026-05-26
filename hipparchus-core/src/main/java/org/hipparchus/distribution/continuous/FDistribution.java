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

import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.special.Beta;
import org.hipparchus.util.FastMath;

/**
 * Implementation of the F-distribution.
 *
 * @see <a href="http://en.wikipedia.org/wiki/F-distribution">F-distribution (Wikipedia)</a>
 * @see <a href="http://mathworld.wolfram.com/F-Distribution.html">F-distribution (MathWorld)</a>
 */
public class FDistribution extends AbstractRealDistribution {

    /**
     * Serializable version identifier.
     */
    private static final long serialVersionUID = 20160320L;

    /**
     * The numerator degrees of freedom.
     */
    private final double numeratorDegreesOfFreedom;

    /**
     * The numerator degrees of freedom.
     */
    private final double denominatorDegreesOfFreedom;

    /**
     * Cached numerical variance
     */
    private final double numericalVariance;

    /**
     * Creates an F distribution using the given degrees of freedom.
     *
     * @param numeratorDegreesOfFreedom Numerator degrees of freedom.
     * @param denominatorDegreesOfFreedom Denominator degrees of freedom.
     * @throws MathIllegalArgumentException if
     * {@code numeratorDegreesOfFreedom <= 0} or
     * {@code denominatorDegreesOfFreedom <= 0}.
     */
    public FDistribution(double numeratorDegreesOfFreedom, double denominatorDegreesOfFreedom) throws MathIllegalArgumentException {
        this(numeratorDegreesOfFreedom, denominatorDegreesOfFreedom, DEFAULT_SOLVER_ABSOLUTE_ACCURACY);
    }

    /**
     * Creates an F distribution.
     *
     * @param numeratorDegreesOfFreedom Numerator degrees of freedom.
     * @param denominatorDegreesOfFreedom Denominator degrees of freedom.
     * @param inverseCumAccuracy the maximum absolute error in inverse
     * cumulative probability estimates.
     * @throws MathIllegalArgumentException if {@code numeratorDegreesOfFreedom <= 0} or
     * {@code denominatorDegreesOfFreedom <= 0}.
     */
    public FDistribution(double numeratorDegreesOfFreedom, double denominatorDegreesOfFreedom, double inverseCumAccuracy) throws MathIllegalArgumentException {
        super(inverseCumAccuracy);
        if (numeratorDegreesOfFreedom <= 0) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.DEGREES_OF_FREEDOM, numeratorDegreesOfFreedom);
        }
        if (denominatorDegreesOfFreedom <= 0) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.DEGREES_OF_FREEDOM, denominatorDegreesOfFreedom);
        }
        this.numeratorDegreesOfFreedom = numeratorDegreesOfFreedom;
        this.denominatorDegreesOfFreedom = denominatorDegreesOfFreedom;
        this.numericalVariance = calculateNumericalVariance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double density(double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc} *
     */
    @Override
    public double logDensity(double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * The implementation of this method is based on
     * <ul>
     *  <li>
     *   <a href="http://mathworld.wolfram.com/F-Distribution.html">
     *   F-Distribution</a>, equation (4).
     *  </li>
     * </ul>
     */
    @Override
    public double cumulativeProbability(double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Access the numerator degrees of freedom.
     *
     * @return the numerator degrees of freedom.
     */
    public double getNumeratorDegreesOfFreedom() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Access the denominator degrees of freedom.
     *
     * @return the denominator degrees of freedom.
     */
    public double getDenominatorDegreesOfFreedom() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * For denominator degrees of freedom parameter {@code b}, the mean is
     * <ul>
     *  <li>if {@code b > 2} then {@code b / (b - 2)},</li>
     *  <li>else undefined ({@code Double.NaN}).
     * </ul>
     */
    @Override
    public double getNumericalMean() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * For numerator degrees of freedom parameter {@code a} and denominator
     * degrees of freedom parameter {@code b}, the variance is
     * <ul>
     *  <li>
     *    if {@code b > 4} then
     *    {@code [2 * b^2 * (a + b - 2)] / [a * (b - 2)^2 * (b - 4)]},
     *  </li>
     *  <li>else undefined ({@code Double.NaN}).
     * </ul>
     */
    @Override
    public double getNumericalVariance() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Calculates the numerical variance.
     *
     * @return the variance of this distribution
     */
    private double calculateNumericalVariance() {
        final double denominatorDF = getDenominatorDegreesOfFreedom();
        if (denominatorDF > 4) {
            final double numeratorDF = getNumeratorDegreesOfFreedom();
            final double denomDFMinusTwo = denominatorDF - 2;
            return (2 * (denominatorDF * denominatorDF) * (numeratorDF + denominatorDF - 2)) / ((numeratorDF * (denomDFMinusTwo * denomDFMinusTwo) * (denominatorDF - 4)));
        }
        return Double.NaN;
    }

    /**
     * {@inheritDoc}
     *
     * The lower bound of the support is always 0 no matter the parameters.
     *
     * @return lower bound of the support (always 0)
     */
    @Override
    public double getSupportLowerBound() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * The upper bound of the support is always positive infinity
     * no matter the parameters.
     *
     * @return upper bound of the support (always Double.POSITIVE_INFINITY)
     */
    @Override
    public double getSupportUpperBound() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * The support of this distribution is connected.
     *
     * @return {@code true}
     */
    @Override
    public boolean isSupportConnected() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
