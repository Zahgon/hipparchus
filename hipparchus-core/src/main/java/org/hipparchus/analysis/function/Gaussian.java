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
package org.hipparchus.analysis.function;

import java.util.Arrays;
import org.hipparchus.analysis.ParametricUnivariateFunction;
import org.hipparchus.analysis.differentiation.Derivative;
import org.hipparchus.analysis.differentiation.UnivariateDifferentiableFunction;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.NullArgumentException;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.MathUtils;
import org.hipparchus.util.Precision;

/**
 * <a href="http://en.wikipedia.org/wiki/Gaussian_function">
 *  Gaussian</a> function.
 */
public class Gaussian implements UnivariateDifferentiableFunction {

    /**
     * Mean.
     */
    private final double mean;

    /**
     * Inverse of the standard deviation.
     */
    private final double is;

    /**
     * Inverse of twice the square of the standard deviation.
     */
    private final double i2s2;

    /**
     * Normalization factor.
     */
    private final double norm;

    /**
     * Gaussian with given normalization factor, mean and standard deviation.
     *
     * @param norm Normalization factor.
     * @param mean Mean.
     * @param sigma Standard deviation.
     * @throws MathIllegalArgumentException if {@code sigma <= 0}.
     */
    public Gaussian(double norm, double mean, double sigma) throws MathIllegalArgumentException {
        if (sigma <= 0) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.NUMBER_TOO_SMALL_BOUND_EXCLUDED, sigma, 0);
        }
        this.norm = norm;
        this.mean = mean;
        this.is = 1 / sigma;
        this.i2s2 = 0.5 * is * is;
    }

    /**
     * Normalized gaussian with given mean and standard deviation.
     *
     * @param mean Mean.
     * @param sigma Standard deviation.
     * @throws MathIllegalArgumentException if {@code sigma <= 0}.
     */
    public Gaussian(double mean, double sigma) throws MathIllegalArgumentException {
        this(1 / (sigma * FastMath.sqrt(2 * Math.PI)), mean, sigma);
    }

    /**
     * Normalized gaussian with zero mean and unit standard deviation.
     */
    public Gaussian() {
        this(0, 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double value(double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Parametric function where the input array contains the parameters of
     * the Gaussian, ordered as follows:
     * <ul>
     *  <li>Norm</li>
     *  <li>Mean</li>
     *  <li>Standard deviation</li>
     * </ul>
     */
    public static class Parametric implements ParametricUnivariateFunction {

        /**
         * Empty constructor.
         * <p>
         * This constructor is not strictly necessary, but it prevents spurious
         * javadoc warnings with JDK 18 and later.
         * </p>
         * @since 3.0
         */
        public Parametric() {
            // NOPMD - unnecessary constructor added intentionally to make javadoc happy
            // nothing to do
        }

        /**
         * Computes the value of the Gaussian at {@code x}.
         *
         * @param x Value for which the function must be computed.
         * @param param Values of norm, mean and standard deviation.
         * @return the value of the function.
         * @throws NullArgumentException if {@code param} is {@code null}.
         * @throws MathIllegalArgumentException if the size of {@code param} is
         * not 3.
         * @throws MathIllegalArgumentException if {@code param[2]} is negative.
         */
        @Override
        public double value(double x, double... param) throws MathIllegalArgumentException, NullArgumentException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Computes the value of the gradient at {@code x}.
         * The components of the gradient vector are the partial
         * derivatives of the function with respect to each of the
         * <em>parameters</em> (norm, mean and standard deviation).
         *
         * @param x Value at which the gradient must be computed.
         * @param param Values of norm, mean and standard deviation.
         * @return the gradient vector at {@code x}.
         * @throws NullArgumentException if {@code param} is {@code null}.
         * @throws MathIllegalArgumentException if the size of {@code param} is
         * not 3.
         * @throws MathIllegalArgumentException if {@code param[2]} is negative.
         */
        @Override
        public double[] gradient(double x, double... param) throws MathIllegalArgumentException, NullArgumentException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Validates parameters to ensure they are appropriate for the evaluation of
         * the {@link #value(double,double[])} and {@link #gradient(double,double[])}
         * methods.
         *
         * @param param Values of norm, mean and standard deviation.
         * @throws NullArgumentException if {@code param} is {@code null}.
         * @throws MathIllegalArgumentException if the size of {@code param} is
         * not 3.
         * @throws MathIllegalArgumentException if {@code param[2]} is negative.
         */
        private void validateParameters(double[] param) throws MathIllegalArgumentException, NullArgumentException {
            if (param == null) {
                throw new NullArgumentException();
            }
            MathUtils.checkDimension(param.length, 3);
            if (param[2] <= 0) {
                throw new MathIllegalArgumentException(LocalizedCoreFormats.NUMBER_TOO_SMALL_BOUND_EXCLUDED, param[2], 0);
            }
        }
    }

    /**
     * @param xMinusMean {@code x - mean}.
     * @param norm Normalization factor.
     * @param i2s2 Inverse of twice the square of the standard deviation.
     * @return the value of the Gaussian at {@code x}.
     */
    private static double value(double xMinusMean, double norm, double i2s2) {
        return norm * FastMath.exp(-xMinusMean * xMinusMean * i2s2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Derivative<T>> T value(T t) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
