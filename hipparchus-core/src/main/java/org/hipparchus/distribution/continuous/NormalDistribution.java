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
import org.hipparchus.special.Erf;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.MathUtils;

/**
 * Implementation of the normal (gaussian) distribution.
 *
 * @see <a href="http://en.wikipedia.org/wiki/Normal_distribution">Normal distribution (Wikipedia)</a>
 * @see <a href="http://mathworld.wolfram.com/NormalDistribution.html">Normal distribution (MathWorld)</a>
 */
public class NormalDistribution extends AbstractRealDistribution {

    /**
     * Serializable version identifier.
     */
    private static final long serialVersionUID = 20160320L;

    /**
     * &radic;(2)
     */
    private static final double SQRT2 = FastMath.sqrt(2.0);

    /**
     * Mean of this distribution.
     */
    private final double mean;

    /**
     * Standard deviation of this distribution.
     */
    private final double standardDeviation;

    /**
     * The value of {@code log(sd) + 0.5*log(2*pi)} stored for faster computation.
     */
    private final double logStandardDeviationPlusHalfLog2Pi;

    /**
     * Create a normal distribution with mean equal to zero and standard
     * deviation equal to one.
     */
    public NormalDistribution() {
        this(0, 1);
    }

    /**
     * Create a normal distribution using the given mean, standard deviation.
     *
     * @param mean Mean for this distribution.
     * @param sd Standard deviation for this distribution.
     * @throws MathIllegalArgumentException if {@code sd <= 0}.
     */
    public NormalDistribution(double mean, double sd) throws MathIllegalArgumentException {
        if (sd <= 0) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.STANDARD_DEVIATION, sd);
        }
        this.mean = mean;
        this.standardDeviation = sd;
        this.logStandardDeviationPlusHalfLog2Pi = FastMath.log(sd) + 0.5 * FastMath.log(2 * FastMath.PI);
    }

    /**
     * Access the mean.
     *
     * @return the mean for this distribution.
     */
    public double getMean() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Access the standard deviation.
     *
     * @return the standard deviation for this distribution.
     */
    public double getStandardDeviation() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double density(double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double logDensity(double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * If {@code x} is more than 40 standard deviations from the mean, 0 or 1
     * is returned, as in these cases the actual value is within
     * {@code Double.MIN_VALUE} of 0 or 1.
     */
    @Override
    public double cumulativeProbability(double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double inverseCumulativeProbability(final double p) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double probability(double x0, double x1) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * For mean parameter {@code mu}, the mean is {@code mu}.
     */
    @Override
    public double getNumericalMean() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * For standard deviation parameter {@code s}, the variance is {@code s^2}.
     */
    @Override
    public double getNumericalVariance() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * The lower bound of the support is always negative infinity
     * no matter the parameters.
     *
     * @return lower bound of the support (always
     * {@code Double.NEGATIVE_INFINITY})
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
     * @return upper bound of the support (always
     * {@code Double.POSITIVE_INFINITY})
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
