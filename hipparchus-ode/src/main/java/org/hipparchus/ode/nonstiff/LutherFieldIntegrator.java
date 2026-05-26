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
import org.hipparchus.ode.nonstiff.interpolators.LutherFieldStateInterpolator;
import org.hipparchus.util.MathArrays;

/**
 * This class implements the Luther sixth order Runge-Kutta
 * integrator for Ordinary Differential Equations.
 *
 * <p>
 * This method is described in H. A. Luther 1968 paper <a
 * href="http://www.ams.org/journals/mcom/1968-22-102/S0025-5718-68-99876-1/S0025-5718-68-99876-1.pdf">
 * An explicit Sixth-Order Runge-Kutta Formula</a>.
 * </p>
 *
 * <p>This method is an explicit Runge-Kutta method, its Butcher-array
 * is the following one :</p>
 * <pre>
 *        0   |               0                     0                     0                     0                     0                     0
 *        1   |               1                     0                     0                     0                     0                     0
 *       1/2  |              3/8                   1/8                    0                     0                     0                     0
 *       2/3  |              8/27                  2/27                  8/27                   0                     0                     0
 *   (7-q)/14 | (  -21 +   9q)/392    (  -56 +   8q)/392    (  336 -  48q)/392    (  -63 +   3q)/392                  0                     0
 *   (7+q)/14 | (-1155 - 255q)/1960   ( -280 -  40q)/1960   (    0 - 320q)/1960   (   63 + 363q)/1960   ( 2352 + 392q)/1960                 0
 *        1   | (  330 + 105q)/180    (  120 +   0q)/180    ( -200 + 280q)/180    (  126 - 189q)/180    ( -686 - 126q)/180     ( 490 -  70q)/180
 *            |--------------------------------------------------------------------------------------------------------------------------------------------------
 *            |              1/20                   0                   16/45                  0                   49/180                 49/180         1/20
 * </pre>
 * <p>where q = &radic;21</p>
 *
 * @see EulerFieldIntegrator
 * @see ClassicalRungeKuttaFieldIntegrator
 * @see GillFieldIntegrator
 * @see MidpointFieldIntegrator
 * @see ThreeEighthesFieldIntegrator
 * @param <T> the type of the field elements
 */
public class LutherFieldIntegrator<T extends CalculusFieldElement<T>> extends FixedStepRungeKuttaFieldIntegrator<T> {

    /**
     * Name of integration scheme.
     */
    public static final String METHOD_NAME = LutherIntegrator.METHOD_NAME;

    /**
     * Simple constructor.
     * Build a fourth-order Luther integrator with the given step.
     * @param field field to which the time and state vector elements belong
     * @param step integration step
     */
    public LutherFieldIntegrator(final Field<T> field, final T step) {
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
    protected LutherFieldStateInterpolator<T> createInterpolator(final boolean forward, T[][] yDotK, final FieldODEStateAndDerivative<T> globalPreviousState, final FieldODEStateAndDerivative<T> globalCurrentState, final FieldEquationsMapper<T> mapper) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
