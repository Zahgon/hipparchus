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
package org.hipparchus.ode.nonstiff;

import org.hipparchus.CalculusFieldElement;
import org.hipparchus.Field;
import org.hipparchus.ode.FieldExpandableODE;
import org.hipparchus.ode.FieldODEIntegrator;
import org.hipparchus.ode.FieldOrdinaryDifferentialEquation;
import org.hipparchus.util.MathArrays;

/**
 * This interface implements the part of Runge-Kutta
 * Field integrators for Ordinary Differential Equations
 * common to fixed- and adaptive steps.
 *
 * <p>These methods are explicit Runge-Kutta methods, their Butcher
 * arrays are as follows :</p>
 * <pre>
 *    0  |
 *   c2  | a21
 *   c3  | a31  a32
 *   ... |        ...
 *   cs  | as1  as2  ...  ass-1
 *       |--------------------------
 *       |  b1   b2  ...   bs-1  bs
 * </pre>
 *
 * @see FieldButcherArrayProvider
 * @see FixedStepRungeKuttaFieldIntegrator
 * @see EmbeddedRungeKuttaFieldIntegrator
 * @param <T> the type of the field elements
 * @since 3.1
 */
public interface FieldExplicitRungeKuttaIntegrator<T extends CalculusFieldElement<T>> extends FieldButcherArrayProvider<T>, FieldODEIntegrator<T> {

    /**
     * Get the time steps from Butcher array (without the first zero). Real version (non-Field).
     * @return time steps from Butcher array (without the first zero).
     */
    default double[] getRealC() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the internal weights from Butcher array (without the first empty row). Real version (non-Field).
     * @return internal weights from Butcher array (without the first empty row)
     */
    default double[][] getRealA() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the external weights for the high order method from Butcher array. Real version (non-Field).
     * @return external weights for the high order method from Butcher array
     */
    default double[] getRealB() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Getter for the flag between real or Field coefficients in the Butcher array.
     *
     * @return flag
     */
    boolean isUsingFieldCoefficients();

    /**
     * Getter for the number of stages corresponding to the Butcher array.
     *
     * @return number of stages
     */
    default int getNumberOfStages() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Fast computation of a single step of ODE integration.
     * <p>This method is intended for the limited use case of
     * very fast computation of only one step without using any of the
     * rich features of general integrators that may take some time
     * to set up (i.e. no step handlers, no events handlers, no additional
     * states, no interpolators, no error control, no evaluations count,
     * no sanity checks ...). It handles the strict minimum of computation,
     * so it can be embedded in outer loops.</p>
     * <p>
     * This method is <em>not</em> used at all by the {@link #integrate(FieldExpandableODE,
     * org.hipparchus.ode.FieldODEState, CalculusFieldElement)} method. It also completely ignores the step set at
     * construction time, and uses only a single step to go from {@code t0} to {@code t}.
     * </p>
     * <p>
     * As this method does not use any of the state-dependent features of the integrator,
     * it should be reasonably thread-safe <em>if and only if</em> the provided differential
     * equations are themselves thread-safe.
     * </p>
     * @param equations differential equations to integrate
     * @param t0 initial time
     * @param y0 initial value of the state vector at t0
     * @param t target time for the integration
     * (can be set to a value smaller than {@code t0} for backward integration)
     * @return state vector at {@code t}
     */
    default T[] singleStep(final FieldOrdinaryDifferentialEquation<T> equations, final T t0, final T[] y0, final T t) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Create a fraction from integers.
     *
     * @param <T> the type of the field elements
     * @param field field to which elements belong
     * @param p numerator
     * @param q denominator
     * @return p/q computed in the instance field
     */
    static <T extends CalculusFieldElement<T>> T fraction(final Field<T> field, final int p, final int q) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Create a fraction from doubles.
     * @param <T> the type of the field elements
     * @param field field to which elements belong
     * @param p numerator
     * @param q denominator
     * @return p/q computed in the instance field
     */
    static <T extends CalculusFieldElement<T>> T fraction(final Field<T> field, final double p, final double q) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Apply internal weights of Butcher array, with corresponding times.
     * @param <T> the type of the field elements
     * @param equations differential equations to integrate
     * @param t0        initial time
     * @param y0        initial value of the state vector at t0
     * @param h         step size
     * @param a         internal weights of Butcher array
     * @param c         times of Butcher array
     * @param yDotK     array where to store result
     */
    static <T extends CalculusFieldElement<T>> void applyInternalButcherWeights(final FieldExpandableODE<T> equations, final T t0, final T[] y0, final T h, final T[][] a, final T[] c, final T[][] yDotK) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Apply internal weights of Butcher array, with corresponding times. Version with real Butcher array (non-Field).
     * @param <T> the type of the field elements
     * @param equations differential equations to integrate
     * @param t0 initial time
     * @param y0 initial value of the state vector at t0
     * @param h step size
     * @param a internal weights of Butcher array
     * @param c times of Butcher array
     * @param yDotK array where to store result
     */
    static <T extends CalculusFieldElement<T>> void applyInternalButcherWeights(final FieldExpandableODE<T> equations, final T t0, final T[] y0, final T h, final double[][] a, final double[] c, final T[][] yDotK) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Apply external weights of Butcher array, assuming internal ones have been applied.
     * @param <T> the type of the field elements
     * @param yDotK output of stages
     * @param y0 initial value of the state vector at t0
     * @param h step size
     * @param b external weights of Butcher array
     * @return state vector
     */
    static <T extends CalculusFieldElement<T>> T[] applyExternalButcherWeights(final T[] y0, final T[][] yDotK, final T h, final T[] b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Apply external weights of Butcher array, assuming internal ones have been applied. Version with real Butcher
     * array (non-Field version).
     * @param <T> the type of the field elements
     * @param yDotK output of stages
     * @param y0 initial value of the state vector at t0
     * @param h step size
     * @param b external weights of Butcher array
     * @return state vector
     */
    static <T extends CalculusFieldElement<T>> T[] applyExternalButcherWeights(final T[] y0, final T[][] yDotK, final T h, final double[] b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
