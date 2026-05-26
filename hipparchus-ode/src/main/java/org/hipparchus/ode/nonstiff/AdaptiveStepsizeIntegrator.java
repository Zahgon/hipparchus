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

import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.MathIllegalStateException;
import org.hipparchus.ode.AbstractIntegrator;
import org.hipparchus.ode.ODEState;
import org.hipparchus.ode.ODEStateAndDerivative;
import org.hipparchus.util.FastMath;

/**
 * This abstract class holds the common part of all adaptive
 * stepsize integrators for Ordinary Differential Equations.
 *
 * <p>These algorithms perform integration with stepsize control, which
 * means the user does not specify the integration step but rather a
 * tolerance on error. The error threshold is computed as
 * </p>
 * <pre>
 * threshold_i = absTol_i + relTol_i * max (abs (ym), abs (ym+1))
 * </pre>
 * <p>
 * where absTol_i is the absolute tolerance for component i of the
 * state vector and relTol_i is the relative tolerance for the same
 * component. The user can also use only two scalar values absTol and
 * relTol which will be used for all components.
 * </p>
 * <p>
 * If the Ordinary Differential Equations is an {@link org.hipparchus.ode.ExpandableODE
 * extended ODE} rather than a {@link
 * org.hipparchus.ode.OrdinaryDifferentialEquation basic ODE}, then
 * <em>only</em> the {@link org.hipparchus.ode.ExpandableODE#getPrimary() primary part}
 * of the state vector is used for stepsize control, not the complete state vector.
 * </p>
 *
 * <p>If the estimated error for ym+1 is such that</p>
 * <pre>
 * sqrt((sum (errEst_i / threshold_i)^2 ) / n) &lt; 1
 * </pre>
 *
 * <p>(where n is the main set dimension) then the step is accepted,
 * otherwise the step is rejected and a new attempt is made with a new
 * stepsize.</p>
 */
public abstract class AdaptiveStepsizeIntegrator extends AbstractIntegrator {

    /**
     * Helper for step size control.
     */
    private StepsizeHelper stepsizeHelper;

    /**
     * Build an integrator with the given stepsize bounds.
     * The default step handler does nothing.
     * @param name name of the method
     * @param minStep minimal step (sign is irrelevant, regardless of
     * integration direction, forward or backward), the last step can
     * be smaller than this
     * @param maxStep maximal step (sign is irrelevant, regardless of
     * integration direction, forward or backward), the last step can
     * be smaller than this
     * @param scalAbsoluteTolerance allowed absolute error
     * @param scalRelativeTolerance allowed relative error
     */
    protected AdaptiveStepsizeIntegrator(final String name, final double minStep, final double maxStep, final double scalAbsoluteTolerance, final double scalRelativeTolerance) {
        super(name);
        stepsizeHelper = new StepsizeHelper(minStep, maxStep, scalAbsoluteTolerance, scalRelativeTolerance);
        resetInternalState();
    }

    /**
     * Build an integrator with the given stepsize bounds.
     * The default step handler does nothing.
     * @param name name of the method
     * @param minStep minimal step (sign is irrelevant, regardless of
     * integration direction, forward or backward), the last step can
     * be smaller than this
     * @param maxStep maximal step (sign is irrelevant, regardless of
     * integration direction, forward or backward), the last step can
     * be smaller than this
     * @param vecAbsoluteTolerance allowed absolute error
     * @param vecRelativeTolerance allowed relative error
     */
    protected AdaptiveStepsizeIntegrator(final String name, final double minStep, final double maxStep, final double[] vecAbsoluteTolerance, final double[] vecRelativeTolerance) {
        super(name);
        stepsizeHelper = new StepsizeHelper(minStep, maxStep, vecAbsoluteTolerance, vecRelativeTolerance);
        resetInternalState();
    }

    /**
     * Set the adaptive step size control parameters.
     * <p>
     * A side effect of this method is to also reset the initial
     * step so it will be automatically computed by the integrator
     * if {@link #setInitialStepSize(double) setInitialStepSize}
     * is not called by the user.
     * </p>
     * @param minimalStep minimal step (must be positive even for backward
     * integration), the last step can be smaller than this
     * @param maximalStep maximal step (must be positive even for backward
     * integration)
     * @param absoluteTolerance allowed absolute error
     * @param relativeTolerance allowed relative error
     */
    public void setStepSizeControl(final double minimalStep, final double maximalStep, final double absoluteTolerance, final double relativeTolerance) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Set the adaptive step size control parameters.
     * <p>
     * A side effect of this method is to also reset the initial
     * step so it will be automatically computed by the integrator
     * if {@link #setInitialStepSize(double) setInitialStepSize}
     * is not called by the user.
     * </p>
     * @param minimalStep minimal step (must be positive even for backward
     * integration), the last step can be smaller than this
     * @param maximalStep maximal step (must be positive even for backward
     * integration)
     * @param absoluteTolerance allowed absolute error
     * @param relativeTolerance allowed relative error
     */
    public void setStepSizeControl(final double minimalStep, final double maximalStep, final double[] absoluteTolerance, final double[] relativeTolerance) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the stepsize helper.
     * @return stepsize helper
     * @since 2.0
     */
    protected StepsizeHelper getStepSizeHelper() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Set the initial step size.
     * <p>This method allows the user to specify an initial positive
     * step size instead of letting the integrator guess it by
     * itself. If this method is not called before integration is
     * started, the initial step size will be estimated by the
     * integrator.</p>
     * @param initialStepSize initial step size to use (must be positive even
     * for backward integration ; providing a negative value or a value
     * outside of the min/max step interval will lead the integrator to
     * ignore the value and compute the initial step size by itself)
     */
    public void setInitialStepSize(final double initialStepSize) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void sanityChecks(final ODEState initialState, final double t) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Initialize the integration step.
     * @param forward forward integration indicator
     * @param order order of the method
     * @param scale scaling vector for the state vector (can be shorter than state vector)
     * @param state0 state at integration start time
     * @return first integration step
     * @exception MathIllegalStateException if the number of functions evaluations is exceeded
     * @exception MathIllegalArgumentException if arrays dimensions do not match equations settings
     */
    public double initializeStep(final boolean forward, final int order, final double[] scale, final ODEStateAndDerivative state0) throws MathIllegalArgumentException, MathIllegalStateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Reset internal state to dummy values.
     */
    protected void resetInternalState() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the minimal step.
     * @return minimal step
     */
    public double getMinStep() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the maximal step.
     * @return maximal step
     */
    public double getMaxStep() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
