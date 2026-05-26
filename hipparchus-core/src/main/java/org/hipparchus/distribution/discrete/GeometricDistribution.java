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
package org.hipparchus.distribution.discrete;

import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.MathUtils;

/**
 * Implementation of the geometric distribution.
 *
 * @see <a href="http://en.wikipedia.org/wiki/Geometric_distribution">Geometric distribution (Wikipedia)</a>
 * @see <a href="http://mathworld.wolfram.com/GeometricDistribution.html">Geometric Distribution (MathWorld)</a>
 */
public class GeometricDistribution extends AbstractIntegerDistribution {

    /**
     * Serializable version identifier.
     */
    private static final long serialVersionUID = 20130507L;

    /**
     * The probability of success.
     */
    private final double probabilityOfSuccess;

    /**
     * {@code log(p)} where p is the probability of success.
     */
    private final double logProbabilityOfSuccess;

    /**
     * {@code log(1 - p)} where p is the probability of success.
     */
    private final double log1mProbabilityOfSuccess;

    /**
     * Create a geometric distribution with the given probability of success.
     *
     * @param p probability of success.
     * @throws MathIllegalArgumentException if {@code p <= 0} or {@code p > 1}.
     */
    public GeometricDistribution(double p) throws MathIllegalArgumentException {
        if (p <= 0 || p > 1) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.OUT_OF_RANGE_LEFT, p, 0, 1);
        }
        probabilityOfSuccess = p;
        logProbabilityOfSuccess = FastMath.log(p);
        log1mProbabilityOfSuccess = FastMath.log1p(-p);
    }

    /**
     * Access the probability of success for this distribution.
     *
     * @return the probability of success.
     */
    public double getProbabilityOfSuccess() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double probability(int x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double logProbability(int x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double cumulativeProbability(int x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * For probability parameter {@code p}, the mean is {@code (1 - p) / p}.
     */
    @Override
    public double getNumericalMean() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * For probability parameter {@code p}, the variance is
     * {@code (1 - p) / (p * p)}.
     */
    @Override
    public double getNumericalVariance() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * The lower bound of the support is always 0.
     *
     * @return lower bound of the support (always 0)
     */
    @Override
    public int getSupportLowerBound() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * The upper bound of the support is infinite (which we approximate as
     * {@code Integer.MAX_VALUE}).
     *
     * @return upper bound of the support (always Integer.MAX_VALUE)
     */
    @Override
    public int getSupportUpperBound() {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public int inverseCumulativeProbability(double p) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
