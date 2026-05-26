/*
 * Licensed to the Hipparchus project under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The Hipparchus project licenses this file to You under the Apache License, Version 2.0
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
package org.hipparchus.special.elliptic.carlson;

import org.hipparchus.util.FastMath;
import org.hipparchus.util.MathArrays;

/**
 * Duplication algorithm for Carlson R<sub>C</sub> elliptic integral.
 * @since 2.0
 */
class RcRealDuplication extends RealDuplication {

    /**
     * Constant term in R<sub>C</sub> polynomial.
     */
    static final double S0 = 80080;

    /**
     * Coefficient of s² in R<sub>C</sub> polynomial.
     */
    static final double S2 = 24024;

    /**
     * Coefficient of s³ in R<sub>C</sub> polynomial.
     */
    static final double S3 = 11440;

    /**
     * Coefficient of s⁴ in R<sub>C</sub> polynomial.
     */
    static final double S4 = 30030;

    /**
     * Coefficient of s⁵ in R<sub>C</sub> polynomial.
     */
    static final double S5 = 32760;

    /**
     * Coefficient of s⁶ in R<sub>C</sub> polynomial.
     */
    static final double S6 = 61215;

    /**
     * Coefficient of s⁷ in R<sub>C</sub> polynomial.
     */
    static final double S7 = 90090;

    /**
     * Denominator in R<sub>C</sub> polynomial.
     */
    static final double DENOMINATOR = 80080;

    /**
     * Simple constructor.
     * @param x first symmetric variable of the integral
     * @param y second symmetric variable of the integral
     */
    RcRealDuplication(final double x, final double y) {
        super(x, y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialMeanPoint(final double[] va) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected double convergenceCriterion(final double r, final double max) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void update(final int m, final double[] vaM, final double[] sqrtM, final double fourM) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected double evaluate(final double[] va0, final double aM, final double fourM) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
