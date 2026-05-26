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
import org.hipparchus.special.Gamma;
import org.hipparchus.util.FastMath;

/**
 * This class implements the Nakagami distribution.
 *
 * @see <a href="http://en.wikipedia.org/wiki/Nakagami_distribution">Nakagami Distribution (Wikipedia)</a>
 */
public class NakagamiDistribution extends AbstractRealDistribution {

    /**
     * Serializable version identifier.
     */
    private static final long serialVersionUID = 20141003;

    /**
     * The shape parameter.
     */
    private final double mu;

    /**
     * The scale parameter.
     */
    private final double omega;

    /**
     * Build a new instance.
     *
     * @param mu shape parameter
     * @param omega scale parameter (must be positive)
     * @throws MathIllegalArgumentException if {@code mu < 0.5}
     * @throws MathIllegalArgumentException if {@code omega <= 0}
     */
    public NakagamiDistribution(double mu, double omega) throws MathIllegalArgumentException {
        this(mu, omega, DEFAULT_SOLVER_ABSOLUTE_ACCURACY);
    }

    /**
     * Build a new instance.
     *
     * @param mu shape parameter
     * @param omega scale parameter (must be positive)
     * @param inverseAbsoluteAccuracy the maximum absolute error in inverse
     * cumulative probability estimates (defaults to {@link #DEFAULT_SOLVER_ABSOLUTE_ACCURACY}).
     * @throws MathIllegalArgumentException if {@code mu < 0.5}
     * @throws MathIllegalArgumentException if {@code omega <= 0}
     */
    public NakagamiDistribution(double mu, double omega, double inverseAbsoluteAccuracy) throws MathIllegalArgumentException {
        super(inverseAbsoluteAccuracy);
        if (mu < 0.5) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.NUMBER_TOO_SMALL, mu, 0.5);
        }
        if (omega <= 0) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.NOT_POSITIVE_SCALE, omega);
        }
        this.mu = mu;
        this.omega = omega;
    }

    /**
     * Access the shape parameter, {@code mu}.
     *
     * @return the shape parameter.
     */
    public double getShape() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Access the scale parameter, {@code omega}.
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
