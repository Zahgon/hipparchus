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

import org.hipparchus.analysis.ParametricUnivariateFunction;
import org.hipparchus.analysis.differentiation.Derivative;
import org.hipparchus.analysis.differentiation.UnivariateDifferentiableFunction;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.NullArgumentException;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.MathUtils;

/**
 * <a href="http://en.wikipedia.org/wiki/Logit">
 *  Logit</a> function.
 * It is the inverse of the {@link Sigmoid sigmoid} function.
 */
public class Logit implements UnivariateDifferentiableFunction {

    /**
     * Lower bound.
     */
    private final double lo;

    /**
     * Higher bound.
     */
    private final double hi;

    /**
     * Usual logit function, where the lower bound is 0 and the higher
     * bound is 1.
     */
    public Logit() {
        this(0, 1);
    }

    /**
     * Logit function.
     *
     * @param lo Lower bound of the function domain.
     * @param hi Higher bound of the function domain.
     */
    public Logit(double lo, double hi) {
        this.lo = lo;
        this.hi = hi;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double value(double x) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Parametric function where the input array contains the parameters of
     * the logit function, ordered as follows:
     * <ul>
     *  <li>Lower bound</li>
     *  <li>Higher bound</li>
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
         * Computes the value of the logit at {@code x}.
         *
         * @param x Value for which the function must be computed.
         * @param param Values of lower bound and higher bounds.
         * @return the value of the function.
         * @throws NullArgumentException if {@code param} is {@code null}.
         * @throws MathIllegalArgumentException if the size of {@code param} is
         * not 2.
         */
        @Override
        public double value(double x, double... param) throws MathIllegalArgumentException, NullArgumentException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Computes the value of the gradient at {@code x}.
         * The components of the gradient vector are the partial
         * derivatives of the function with respect to each of the
         * <em>parameters</em> (lower bound and higher bound).
         *
         * @param x Value at which the gradient must be computed.
         * @param param Values for lower and higher bounds.
         * @return the gradient vector at {@code x}.
         * @throws NullArgumentException if {@code param} is {@code null}.
         * @throws MathIllegalArgumentException if the size of {@code param} is
         * not 2.
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
         * @param param Values for lower and higher bounds.
         * @throws NullArgumentException if {@code param} is {@code null}.
         * @throws MathIllegalArgumentException if the size of {@code param} is
         * not 2.
         */
        private void validateParameters(double[] param) throws MathIllegalArgumentException, NullArgumentException {
            MathUtils.checkNotNull(param);
            MathUtils.checkDimension(param.length, 2);
        }
    }

    /**
     * @param x Value at which to compute the logit.
     * @param lo Lower bound.
     * @param hi Higher bound.
     * @return the value of the logit function at {@code x}.
     * @throws MathIllegalArgumentException if {@code x < lo} or {@code x > hi}.
     */
    private static double value(double x, double lo, double hi) throws MathIllegalArgumentException {
        MathUtils.checkRangeInclusive(x, lo, hi);
        return FastMath.log((x - lo) / (hi - x));
    }

    /**
     * {@inheritDoc}
     * @exception MathIllegalArgumentException if parameter is outside of function domain
     */
    @Override
    public <T extends Derivative<T>> T value(T t) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
