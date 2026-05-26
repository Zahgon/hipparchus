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
 * Duplication algorithm for Carlson R<sub>J</sub> elliptic integral.
 * @since 2.0
 */
class RjRealDuplication extends RealDuplication {

    /**
     * Delta product.
     */
    private double delta;

    /**
     * sₘ iteration parameter.
     */
    private double sM;

    /**
     * Simple constructor.
     * @param x first symmetric variable of the integral
     * @param y second symmetric variable of the integral
     * @param z third symmetric variable of the integral
     * @param p fourth <em>not</em> symmetric variable of the integral
     * @param delta precomputed value of (p-x)(p-y)(p-z)
     */
    RjRealDuplication(final double x, final double y, final double z, final double p, final double delta) {
        super(x, y, z, p);
        this.delta = delta;
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
