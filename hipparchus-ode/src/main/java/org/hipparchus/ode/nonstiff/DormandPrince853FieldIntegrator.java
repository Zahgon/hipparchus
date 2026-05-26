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
import org.hipparchus.ode.nonstiff.interpolators.DormandPrince853FieldStateInterpolator;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.MathArrays;

/**
 * This class implements the 8(5,3) Dormand-Prince integrator for Ordinary
 * Differential Equations.
 *
 * <p>This integrator is an embedded Runge-Kutta integrator
 * of order 8(5,3) used in local extrapolation mode (i.e. the solution
 * is computed using the high order formula) with stepsize control
 * (and automatic step initialization) and continuous output. This
 * method uses 12 functions evaluations per step for integration and 4
 * evaluations for interpolation. However, since the first
 * interpolation evaluation is the same as the first integration
 * evaluation of the next step, we have included it in the integrator
 * rather than in the interpolator and specified the method was an
 * <i>fsal</i>. Hence, despite we have 13 stages here, the cost is
 * really 12 evaluations per step even if no interpolation is done,
 * and the overcost of interpolation is only 3 evaluations.</p>
 *
 * <p>This method is based on an 8(6) method by Dormand and Prince
 * (i.e. order 8 for the integration and order 6 for error estimation)
 * modified by Hairer and Wanner to use a 5th order error estimator
 * with 3rd order correction. This modification was introduced because
 * the original method failed in some cases (wrong steps can be
 * accepted when step size is too large, for example in the
 * Brusselator problem) and also had <i>severe difficulties when
 * applied to problems with discontinuities</i>. This modification is
 * explained in the second edition of the first volume (Nonstiff
 * Problems) of the reference book by Hairer, Norsett and Wanner:
 * <i>Solving Ordinary Differential Equations</i> (Springer-Verlag,
 * ISBN 3-540-56670-8).</p>
 *
 * @param <T> the type of the field elements
 */
public class DormandPrince853FieldIntegrator<T extends CalculusFieldElement<T>> extends EmbeddedRungeKuttaFieldIntegrator<T> {

    /**
     * Name of integration scheme.
     */
    public static final String METHOD_NAME = DormandPrince853Integrator.METHOD_NAME;

    /**
     * Simple constructor.
     * Build an eighth order Dormand-Prince integrator with the given step bounds
     * @param field field to which the time and state vector elements belong
     * @param minStep minimal step (sign is irrelevant, regardless of
     * integration direction, forward or backward), the last step can
     * be smaller than this
     * @param maxStep maximal step (sign is irrelevant, regardless of
     * integration direction, forward or backward), the last step can
     * be smaller than this
     * @param scalAbsoluteTolerance allowed absolute error
     * @param scalRelativeTolerance allowed relative error
     */
    public DormandPrince853FieldIntegrator(final Field<T> field, final double minStep, final double maxStep, final double scalAbsoluteTolerance, final double scalRelativeTolerance) {
        super(field, METHOD_NAME, 12, minStep, maxStep, scalAbsoluteTolerance, scalRelativeTolerance);
    }

    /**
     * Simple constructor.
     * Build an eighth order Dormand-Prince integrator with the given step bounds
     * @param field field to which the time and state vector elements belong
     * @param minStep minimal step (sign is irrelevant, regardless of
     * integration direction, forward or backward), the last step can
     * be smaller than this
     * @param maxStep maximal step (sign is irrelevant, regardless of
     * integration direction, forward or backward), the last step can
     * be smaller than this
     * @param vecAbsoluteTolerance allowed absolute error
     * @param vecRelativeTolerance allowed relative error
     */
    public DormandPrince853FieldIntegrator(final Field<T> field, final double minStep, final double maxStep, final double[] vecAbsoluteTolerance, final double[] vecRelativeTolerance) {
        super(field, DormandPrince853Integrator.METHOD_NAME, 12, minStep, maxStep, vecAbsoluteTolerance, vecRelativeTolerance);
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
    protected DormandPrince853FieldStateInterpolator<T> createInterpolator(final boolean forward, T[][] yDotK, final FieldODEStateAndDerivative<T> globalPreviousState, final FieldODEStateAndDerivative<T> globalCurrentState, final FieldEquationsMapper<T> mapper) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getOrder() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected double estimateError(final T[][] yDotK, final T[] y0, final T[] y1, final T h) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
