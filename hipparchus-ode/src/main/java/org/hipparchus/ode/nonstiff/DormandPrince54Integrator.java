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
import org.hipparchus.ode.nonstiff.interpolators.DormandPrince54StateInterpolator;
import org.hipparchus.util.FastMath;

/**
 * This class implements the 5(4) Dormand-Prince integrator for Ordinary
 * Differential Equations.
 *
 * <p>This integrator is an embedded Runge-Kutta integrator
 * of order 5(4) used in local extrapolation mode (i.e. the solution
 * is computed using the high order formula) with stepsize control
 * (and automatic step initialization) and continuous output. This
 * method uses 7 functions evaluations per step. However, since this
 * is an <i>fsal</i>, the last evaluation of one step is the same as
 * the first evaluation of the next step and hence can be avoided. So
 * the cost is really 6 functions evaluations per step.</p>
 *
 * <p>This method has been published (whithout the continuous output
 * that was added by Shampine in 1986) in the following article :</p>
 * <pre>
 *  A family of embedded Runge-Kutta formulae
 *  J. R. Dormand and P. J. Prince
 *  Journal of Computational and Applied Mathematics
 *  volume 6, no 1, 1980, pp. 19-26
 * </pre>
 */
public class DormandPrince54Integrator extends EmbeddedRungeKuttaIntegrator {

    /**
     * Name of integration scheme.
     */
    public static final String METHOD_NAME = "Dormand-Prince 5 (4)";

    /**
     * Error array, element 1.
     */
    static final double E1 = 71.0 / 57600.0;

    // element 2 is zero, so it is neither stored nor used
    /**
     * Error array, element 3.
     */
    static final double E3 = -71.0 / 16695.0;

    /**
     * Error array, element 4.
     */
    static final double E4 = 71.0 / 1920.0;

    /**
     * Error array, element 5.
     */
    static final double E5 = -17253.0 / 339200.0;

    /**
     * Error array, element 6.
     */
    static final double E6 = 22.0 / 525.0;

    /**
     * Error array, element 7.
     */
    static final double E7 = -1.0 / 40.0;

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
    public DormandPrince54Integrator(final double minStep, final double maxStep, final double scalAbsoluteTolerance, final double scalRelativeTolerance) {
        super(METHOD_NAME, 6, minStep, maxStep, scalAbsoluteTolerance, scalRelativeTolerance);
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
    public DormandPrince54Integrator(final double minStep, final double maxStep, final double[] vecAbsoluteTolerance, final double[] vecRelativeTolerance) {
        super(METHOD_NAME, 6, minStep, maxStep, vecAbsoluteTolerance, vecRelativeTolerance);
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
    protected DormandPrince54StateInterpolator createInterpolator(final boolean forward, double[][] yDotK, final ODEStateAndDerivative globalPreviousState, final ODEStateAndDerivative globalCurrentState, final EquationsMapper mapper) {
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
