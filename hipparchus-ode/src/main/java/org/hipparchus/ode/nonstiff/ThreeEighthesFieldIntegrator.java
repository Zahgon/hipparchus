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
package org.hipparchus.ode.nonstiff;

import org.hipparchus.CalculusFieldElement;
import org.hipparchus.Field;
import org.hipparchus.ode.FieldEquationsMapper;
import org.hipparchus.ode.FieldODEStateAndDerivative;
import org.hipparchus.ode.nonstiff.interpolators.ThreeEighthesFieldStateInterpolator;
import org.hipparchus.util.MathArrays;

/**
 * This class implements the 3/8 fourth order Runge-Kutta
 * integrator for Ordinary Differential Equations.
 *
 * <p>This method is an explicit Runge-Kutta method, its Butcher-array
 * is the following one :</p>
 * <pre>
 *    0  |  0    0    0    0
 *   1/3 | 1/3   0    0    0
 *   2/3 |-1/3   1    0    0
 *    1  |  1   -1    1    0
 *       |--------------------
 *       | 1/8  3/8  3/8  1/8
 * </pre>
 *
 * @see EulerFieldIntegrator
 * @see ClassicalRungeKuttaFieldIntegrator
 * @see GillFieldIntegrator
 * @see MidpointFieldIntegrator
 * @see LutherFieldIntegrator
 * @param <T> the type of the field elements
 */
public class ThreeEighthesFieldIntegrator<T extends CalculusFieldElement<T>> extends FixedStepRungeKuttaFieldIntegrator<T> {

    /**
     * Name of integration scheme.
     */
    public static final String METHOD_NAME = ThreeEighthesIntegrator.METHOD_NAME;

    /**
     * Simple constructor.
     * Build a 3/8 integrator with the given step.
     * @param field field to which the time and state vector elements belong
     * @param step integration step
     */
    public ThreeEighthesFieldIntegrator(final Field<T> field, final T step) {
        super(field, METHOD_NAME, step);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T[] getC() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T[][] getA() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T[] getB() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ThreeEighthesFieldStateInterpolator<T> createInterpolator(final boolean forward, T[][] yDotK, final FieldODEStateAndDerivative<T> globalPreviousState, final FieldODEStateAndDerivative<T> globalCurrentState, final FieldEquationsMapper<T> mapper) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
