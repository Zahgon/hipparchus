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
 * Duplication algorithm for Carlson R<sub>F</sub> elliptic integral.
 * @since 2.0
 */
class RfRealDuplication extends RealDuplication {

    /**
     * Max number of iterations in the AGM scale.
     */
    static final int AGM_MAX = 32;

    /**
     * Constant term in R<sub>F</sub> polynomial.
     */
    static final double CONSTANT = 240240;

    /**
     * Coefficient of E₂ in R<sub>F</sub> polynomial.
     */
    static final double E2 = -24024;

    /**
     * Coefficient of E₃ in R<sub>F</sub> polynomial.
     */
    static final double E3 = 17160;

    /**
     * Coefficient of E₂² in R<sub>F</sub> polynomial.
     */
    static final double E2_E2 = 10010;

    /**
     * Coefficient of E₂E₃ in R<sub>F</sub> polynomial.
     */
    static final double E2_E3 = -16380;

    /**
     * Coefficient of E₃² in R<sub>F</sub> polynomial.
     */
    static final double E3_E3 = 6930;

    /**
     * Coefficient of E₂³ in R<sub>F</sub> polynomial.
     */
    static final double E2_E2_E2 = -5775;

    /**
     * Denominator in R<sub>F</sub> polynomial.
     */
    static final double DENOMINATOR = 240240;

    /**
     * Simple constructor.
     * @param x first symmetric variable of the integral
     * @param y second symmetric variable of the integral
     * @param z third symmetric variable of the integral
     */
    RfRealDuplication(final double x, final double y, final double z) {
        super(x, y, z);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public double integral() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute Carlson complete elliptic integral R<sub>F</sub>(u, v, 0).
     * @param x first symmetric variable of the integral
     * @param y second symmetric variable of the integral
     * @return Carlson complete elliptic integral R<sub>F</sub>(u, v, 0)
     */
    private double completeIntegral(final double x, final double y) {
        double xM = FastMath.sqrt(x);
        double yM = FastMath.sqrt(y);
        // iterate down
        for (int i = 1; i < AGM_MAX; ++i) {
            final double xM1 = xM;
            final double yM1 = yM;
            // arithmetic mean
            xM = (xM1 + yM1) * 0.5;
            // geometric mean
            yM = FastMath.sqrt(xM1 * yM1);
            // convergence (by the inequality of arithmetic and geometric means, this is non-negative)
            if (FastMath.abs(xM - yM) <= 4 * FastMath.ulp(xM)) {
                // convergence has been reached
                break;
            }
        }
        return FastMath.PI / (xM + yM);
    }
}
