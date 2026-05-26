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

import org.hipparchus.ode.EquationsMapper;
import org.hipparchus.ode.ODEStateAndDerivative;
import org.hipparchus.ode.nonstiff.interpolators.DormandPrince853StateInterpolator;
import org.hipparchus.util.FastMath;

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
 */
public class DormandPrince853Integrator extends EmbeddedRungeKuttaIntegrator {

    /**
     * Name of integration scheme.
     */
    public static final String METHOD_NAME = "Dormand-Prince 8 (5, 3)";

    /**
     * First error weights array, element 1.
     */
    static final double E1_01 = 116092271.0 / 8848465920.0;

    // elements 2 to 5 are zero, so they are neither stored nor used
    /**
     * First error weights array, element 6.
     */
    static final double E1_06 = -1871647.0 / 1527680.0;

    /**
     * First error weights array, element 7.
     */
    static final double E1_07 = -69799717.0 / 140793660.0;

    /**
     * First error weights array, element 8.
     */
    static final double E1_08 = 1230164450203.0 / 739113984000.0;

    /**
     * First error weights array, element 9.
     */
    static final double E1_09 = -1980813971228885.0 / 5654156025964544.0;

    /**
     * First error weights array, element 10.
     */
    static final double E1_10 = 464500805.0 / 1389975552.0;

    /**
     * First error weights array, element 11.
     */
    static final double E1_11 = 1606764981773.0 / 19613062656000.0;

    /**
     * First error weights array, element 12.
     */
    static final double E1_12 = -137909.0 / 6168960.0;

    /**
     * Second error weights array, element 1.
     */
    static final double E2_01 = -364463.0 / 1920240.0;

    // elements 2 to 5 are zero, so they are neither stored nor used
    /**
     * Second error weights array, element 6.
     */
    static final double E2_06 = 3399327.0 / 763840.0;

    /**
     * Second error weights array, element 7.
     */
    static final double E2_07 = 66578432.0 / 35198415.0;

    /**
     * Second error weights array, element 8.
     */
    static final double E2_08 = -1674902723.0 / 288716400.0;

    /**
     * Second error weights array, element 9.
     */
    static final double E2_09 = -74684743568175.0 / 176692375811392.0;

    /**
     * Second error weights array, element 10.
     */
    static final double E2_10 = -734375.0 / 4826304.0;

    /**
     * Second error weights array, element 11.
     */
    static final double E2_11 = 171414593.0 / 851261400.0;

    /**
     * Second error weights array, element 12.
     */
    static final double E2_12 = 69869.0 / 3084480.0;

    /**
     * Simple constructor.
     * Build a fifth order Dormand-Prince integrator with the given step bounds
     * @param minStep minimal step (sign is irrelevant, regardless of
     * integration direction, forward or backward), the last step can
     * be smaller than this
     * @param maxStep maximal step (sign is irrelevant, regardless of
     * integration direction, forward or backward), the last step can
     * be smaller than this
     * @param scalAbsoluteTolerance allowed absolute error
     * @param scalRelativeTolerance allowed relative error
     */
    public DormandPrince853Integrator(final double minStep, final double maxStep, final double scalAbsoluteTolerance, final double scalRelativeTolerance) {
        super(METHOD_NAME, 12, minStep, maxStep, scalAbsoluteTolerance, scalRelativeTolerance);
    }

    /**
     * Simple constructor.
     * Build a fifth order Dormand-Prince integrator with the given step bounds
     * @param minStep minimal step (sign is irrelevant, regardless of
     * integration direction, forward or backward), the last step can
     * be smaller than this
     * @param maxStep maximal step (sign is irrelevant, regardless of
     * integration direction, forward or backward), the last step can
     * be smaller than this
     * @param vecAbsoluteTolerance allowed absolute error
     * @param vecRelativeTolerance allowed relative error
     */
    public DormandPrince853Integrator(final double minStep, final double maxStep, final double[] vecAbsoluteTolerance, final double[] vecRelativeTolerance) {
        super(METHOD_NAME, 12, minStep, maxStep, vecAbsoluteTolerance, vecRelativeTolerance);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double[] getC() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double[][] getA() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double[] getB() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected DormandPrince853StateInterpolator createInterpolator(final boolean forward, double[][] yDotK, final ODEStateAndDerivative globalPreviousState, final ODEStateAndDerivative globalCurrentState, final EquationsMapper mapper) {
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
    protected double estimateError(final double[][] yDotK, final double[] y0, final double[] y1, final double h) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
