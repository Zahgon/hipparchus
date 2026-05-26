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

import org.hipparchus.CalculusFieldElement;
import org.hipparchus.complex.Complex;
import org.hipparchus.complex.FieldComplex;
import org.hipparchus.util.FastMath;

/**
 * Duplication algorithm for Carlson R<sub>D</sub> elliptic integral.
 * @param <T> type of the field elements (really {@link Complex} or {@link FieldComplex})
 * @since 2.0
 */
class RdFieldDuplication<T extends CalculusFieldElement<T>> extends FieldDuplication<T> {

    /**
     * Partial sum.
     */
    private T sum;

    /**
     * Simple constructor.
     * @param x first symmetric variable of the integral
     * @param y second symmetric variable of the integral
     * @param z third symmetric variable of the integral
     */
    RdFieldDuplication(final T x, final T y, final T z) {
        super(x, y, z);
        sum = x.getField().getZero();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialMeanPoint(final T[] va) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected T convergenceCriterion(final T r, final T max) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void update(final int m, final T[] vaM, final T[] sqrtM, final double fourM) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected T evaluate(final T[] va0, final T aM, final double fourM) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
