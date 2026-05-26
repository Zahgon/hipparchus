/*
 * Licensed to the Hipparchus project under one or more
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
package org.hipparchus.distribution.continuous;

import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.special.Gamma;
import org.hipparchus.util.FastMath;

/**
 * Implementation of the Inverse Gamma distribution.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Inverse-gamma_distribution">Inv-Gamma distribution (Wikipedia)</a>
 */
public class InvGammaDistribution extends AbstractRealDistribution {

    /**
     * Serializable version identifier.
     */
    private static final long serialVersionUID = 20250915L;

    /**
     * The shape parameter.
     */
    private final double shape;

    /**
     * The scale parameter.
     */
    private final double scale;

    /**
     * The constant value of {@code shape + g + 0.5}, where {@code g} is the
     * Lanczos constant {@link Gamma#LANCZOS_G}.
     */
    private final double shiftedShape;

    /**
     * The constant value of
     * {@code shape / scale * sqrt(e / (2 * pi * (shape + g + 0.5))) / L(shape)},
     * where {@code L(shape)} is the Lanczos approximation returned by
     * {@link Gamma#lanczos(double)}. This prefactor is used in
     * {@link #density(double)}, when no overflow occurs with the natural
     * calculation.
     */
    private final double densityPrefactor1;

    /**
     * The constant value of
     * {@code log(shape / scale * sqrt(e / (2 * pi * (shape + g + 0.5))) / L(shape))},
     * where {@code L(shape)} is the Lanczos approximation returned by
     * {@link Gamma#lanczos(double)}. This prefactor is used in
     * {@link #logDensity(double)}, when no overflow occurs with the natural
     * calculation.
     */
    private final double logDensityPrefactor1;

    /**
     * Creates a new Inverse Gamma distribution with specified values of the shape and
     * scale parameters.
     *
     * @param shape the shape parameter
     * @param scale the scale parameter
     * @throws MathIllegalArgumentException if {@code shape <= 0} or
     * {@code scale <= 0}.
     */
    public InvGammaDistribution(double shape, double scale) throws MathIllegalArgumentException {
        this(shape, scale, DEFAULT_SOLVER_ABSOLUTE_ACCURACY);
    }

    /**
     * Creates an Inverse Gamma distribution.
     *
     * @param shape the shape parameter
     * @param scale the scale parameter
     * @param inverseCumAccuracy the maximum absolute error in inverse
     * cumulative probability estimates (defaults to
     * {@link #DEFAULT_SOLVER_ABSOLUTE_ACCURACY}).
     * @throws MathIllegalArgumentException if {@code shape <= 0} or
     * {@code scale <= 0}.
     */
    public InvGammaDistribution(final double shape, final double scale, final double inverseCumAccuracy) throws MathIllegalArgumentException {
        super(inverseCumAccuracy);
        if (shape <= 0) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.SHAPE, shape);
        }
        if (scale <= 0) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.SCALE, scale);
        }
        this.shape = shape;
        this.scale = scale;
        this.shiftedShape = shape + Gamma.LANCZOS_G + 0.5;
        // gammaShape is the Lanczos approximation of Gamma function evaluated at shape
        // See https://www.hipparchus.org/apidocs/org/hipparchus/special/Gamma.html#lanczos(double)
        final double gammaShape;
        gammaShape = FastMath.sqrt(2.0 * FastMath.PI) / shape * FastMath.sqrt(shiftedShape) * FastMath.pow(shiftedShape, shape) * FastMath.exp(-shiftedShape) * Gamma.lanczos(shape);
        this.densityPrefactor1 = 1.0 / (gammaShape * scale);
        this.logDensityPrefactor1 = -FastMath.log(gammaShape) - FastMath.log(scale);
    }

    /**
     * Returns the shape parameter of {@code this} distribution.
     *
     * @return the shape parameter
     */
    public double getShape() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the scale parameter of {@code this} distribution.
     *
     * @return the scale parameter
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
     * {@inheritDoc} *
     */
    @Override
    public double logDensity(double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * Returns the cumulative probability value for a given positive x
     */
    @Override
    public double cumulativeProbability(double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * For shape parameter {@code shape} larger than 1, the
     * mean is {@code scale/(shape-1)}.
     */
    @Override
    public double getNumericalMean() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * For shape parameter {@code shape} and scale parameter {@code scale}, the
     * variance is {@code (scale*scale)/((shape - 1.0)*(shape - 1.0)*(shape - 2.0)}.
     *
     * @return {@inheritDoc}
     */
    @Override
    public double getNumericalVariance() {
        throw new UnsupportedOperationException("STUB: not implemented");
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
