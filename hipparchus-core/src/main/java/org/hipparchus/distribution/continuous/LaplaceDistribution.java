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
import org.hipparchus.util.FastMath;
import org.hipparchus.util.MathUtils;

/**
 * This class implements the Laplace distribution.
 *
 * @see <a href="http://en.wikipedia.org/wiki/Laplace_distribution">Laplace distribution (Wikipedia)</a>
 */
public class LaplaceDistribution extends AbstractRealDistribution {

    /**
     * Serializable version identifier.
     */
    private static final long serialVersionUID = 20141003L;

    /**
     * The location parameter.
     */
    private final double mu;

    /**
     * The scale parameter.
     */
    private final double beta;

    /**
     * Build a new instance.
     *
     * @param mu location parameter
     * @param beta scale parameter (must be positive)
     * @throws MathIllegalArgumentException if {@code beta <= 0}
     */
    public LaplaceDistribution(double mu, double beta) throws MathIllegalArgumentException {
        if (beta <= 0.0) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.NOT_POSITIVE_SCALE, beta);
        }
        this.mu = mu;
        this.beta = beta;
    }

    /**
     * Access the location parameter, {@code mu}.
     *
     * @return the location parameter.
     */
    public double getLocation() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Access the scale parameter, {@code beta}.
     *
     * @return the scale parameter.
     */
    public double getScale() {
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
    public double cumulativeProbability(double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double inverseCumulativeProbability(double p) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getNumericalMean() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getNumericalVariance() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getSupportLowerBound() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getSupportUpperBound() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSupportConnected() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
