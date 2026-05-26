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
 * Implementation of the Gamma distribution.
 *
 * @see <a href="http://en.wikipedia.org/wiki/Gamma_distribution">Gamma distribution (Wikipedia)</a>
 * @see <a href="http://mathworld.wolfram.com/GammaDistribution.html">Gamma distribution (MathWorld)</a>
 */
public class GammaDistribution extends AbstractRealDistribution {

    /**
     * Serializable version identifier.
     */
    private static final long serialVersionUID = 20120524L;

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
     * The constant value of
     * {@code shape * sqrt(e / (2 * pi * (shape + g + 0.5))) / L(shape)},
     * where {@code L(shape)} is the Lanczos approximation returned by
     * {@link Gamma#lanczos(double)}. This prefactor is used in
     * {@link #density(double)}, when overflow occurs with the natural
     * calculation.
     */
    private final double densityPrefactor2;

    /**
     * The constant value of
     * {@code log(shape * sqrt(e / (2 * pi * (shape + g + 0.5))) / L(shape))},
     * where {@code L(shape)} is the Lanczos approximation returned by
     * {@link Gamma#lanczos(double)}. This prefactor is used in
     * {@link #logDensity(double)}, when overflow occurs with the natural
     * calculation.
     */
    private final double logDensityPrefactor2;

    /**
     * Lower bound on {@code y = x / scale} for the selection of the computation
     * method in {@link #density(double)}. For {@code y <= minY}, the natural
     * calculation overflows.
     */
    private final double minY;

    /**
     * Upper bound on {@code log(y)} ({@code y = x / scale}) for the selection
     * of the computation method in {@link #density(double)}. For
     * {@code log(y) >= maxLogY}, the natural calculation overflows.
     */
    private final double maxLogY;

    /**
     * Creates a new gamma distribution with specified values of the shape and
     * scale parameters.
     *
     * @param shape the shape parameter
     * @param scale the scale parameter
     * @throws MathIllegalArgumentException if {@code shape <= 0} or
     * {@code scale <= 0}.
     */
    public GammaDistribution(double shape, double scale) throws MathIllegalArgumentException {
        this(shape, scale, DEFAULT_SOLVER_ABSOLUTE_ACCURACY);
    }

    /**
     * Creates a Gamma distribution.
     *
     * @param shape the shape parameter
     * @param scale the scale parameter
     * @param inverseCumAccuracy the maximum absolute error in inverse
     * cumulative probability estimates (defaults to
     * {@link #DEFAULT_SOLVER_ABSOLUTE_ACCURACY}).
     * @throws MathIllegalArgumentException if {@code shape <= 0} or
     * {@code scale <= 0}.
     */
    public GammaDistribution(final double shape, final double scale, final double inverseCumAccuracy) throws MathIllegalArgumentException {
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
        final double aux = FastMath.E / (2.0 * FastMath.PI * shiftedShape);
        this.densityPrefactor2 = shape * FastMath.sqrt(aux) / Gamma.lanczos(shape);
        this.logDensityPrefactor2 = FastMath.log(shape) + 0.5 * FastMath.log(aux) - FastMath.log(Gamma.lanczos(shape));
        this.densityPrefactor1 = this.densityPrefactor2 / scale * FastMath.pow(shiftedShape, -shape) * FastMath.exp(shape + Gamma.LANCZOS_G);
        this.logDensityPrefactor1 = this.logDensityPrefactor2 - FastMath.log(scale) - FastMath.log(shiftedShape) * shape + shape + Gamma.LANCZOS_G;
        this.minY = shape + Gamma.LANCZOS_G - FastMath.log(Double.MAX_VALUE);
        this.maxLogY = FastMath.log(Double.MAX_VALUE) / (shape - 1.0);
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
     * The implementation of this method is based on:
     * <ul>
     *  <li>
     *   <a href="http://mathworld.wolfram.com/Chi-SquaredDistribution.html">
     *    Chi-Squared Distribution</a>, equation (9).
     *  </li>
     *  <li>Casella, G., &amp; Berger, R. (1990). <i>Statistical Inference</i>.
     *    Belmont, CA: Duxbury Press.
     *  </li>
     * </ul>
     */
    @Override
    public double cumulativeProbability(double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * For shape parameter {@code alpha} and scale parameter {@code beta}, the
     * mean is {@code alpha * beta}.
     */
    @Override
    public double getNumericalMean() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * For shape parameter {@code alpha} and scale parameter {@code beta}, the
     * variance is {@code alpha * beta^2}.
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
