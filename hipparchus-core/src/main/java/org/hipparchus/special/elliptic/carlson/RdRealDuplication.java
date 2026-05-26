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
 * Duplication algorithm for Carlson R<sub>D</sub> elliptic integral.
 * @since 2.0
 */
class RdRealDuplication extends RealDuplication {

    /**
     * Constant term in R<sub>J</sub> and R<sub>D</sub> polynomials.
     */
    static final double CONSTANT = 4084080;

    /**
     * Coefficient of E₂ in R<sub>J</sub> and R<sub>D</sub> polynomials.
     */
    static final double E2 = -875160;

    /**
     * Coefficient of E₃ in R<sub>J</sub> and R<sub>D</sub> polynomials.
     */
    static final double E3 = 680680;

    /**
     * Coefficient of E₂² in R<sub>J</sub> and R<sub>D</sub> polynomials.
     */
    static final double E2_E2 = 417690;

    /**
     * Coefficient of E₄ in R<sub>J</sub> and R<sub>D</sub> polynomials.
     */
    static final double E4 = -556920;

    /**
     * Coefficient of E₂E₃ in R<sub>J</sub> and R<sub>D</sub> polynomials.
     */
    static final double E2_E3 = -706860;

    /**
     * Coefficient of E₅ in R<sub>J</sub> and R<sub>D</sub> polynomials.
     */
    static final double E5 = 471240;

    /**
     * Coefficient of E₂³ in R<sub>J</sub> and R<sub>D</sub> polynomials.
     */
    static final double E2_E2_E2 = -255255;

    /**
     * Coefficient of E₃² in R<sub>J</sub> and R<sub>D</sub> polynomials.
     */
    static final double E3_E3 = 306306;

    /**
     * Coefficient of E₂E₄ in R<sub>J</sub> and R<sub>D</sub> polynomials.
     */
    static final double E2_E4 = 612612;

    /**
     * Coefficient of E₂²E₃ in R<sub>J</sub> and R<sub>D</sub> polynomials.
     */
    static final double E2_E2_E3 = 675675;

    /**
     * Coefficient of E₃E₄+E₂E₅ in R<sub>J</sub> and R<sub>D</sub> polynomials.
     */
    static final double E3_E4_P_E2_E5 = -540540;

    /**
     * Denominator in R<sub>J</sub> and R<sub>D</sub> polynomials.
     */
    static final double DENOMINATOR = 4084080;

    /**
     * Partial sum.
     */
    private double sum;

    /**
     * Simple constructor.
     * @param x first symmetric variable of the integral
     * @param y second symmetric variable of the integral
     * @param z third symmetric variable of the integral
     */
    RdRealDuplication(final double x, final double y, final double z) {
        super(x, y, z);
        sum = 0;
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
