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
package org.hipparchus.ode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.MathIllegalStateException;
import org.hipparchus.ode.events.Action;
import org.hipparchus.ode.events.DetectorBasedEventState;
import org.hipparchus.ode.events.EventOccurrence;
import org.hipparchus.ode.events.EventState;
import org.hipparchus.ode.events.ODEEventDetector;
import org.hipparchus.ode.events.ODEStepEndHandler;
import org.hipparchus.ode.events.StepEndEventState;
import org.hipparchus.ode.sampling.AbstractODEStateInterpolator;
import org.hipparchus.ode.sampling.ODEStateInterpolator;
import org.hipparchus.ode.sampling.ODEStepHandler;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.Incrementor;

/**
 * Base class managing common boilerplate for all integrators.
 */
public abstract class AbstractIntegrator implements ODEIntegrator {

    /**
     * Step handler.
     */
    private List<ODEStepHandler> stepHandlers;

    /**
     * Current step start time.
     */
    private ODEStateAndDerivative stepStart;

    /**
     * Current stepsize.
     */
    private double stepSize;

    /**
     * Indicator for last step.
     */
    private boolean isLastStep;

    /**
     * Indicator that a state or derivative reset was triggered by some event.
     */
    private boolean resetOccurred;

    /**
     * Events states related to event detectors.
     */
    private List<DetectorBasedEventState> detectorBasedEventsStates;

    /**
     * Events states related to step end.
     */
    private List<StepEndEventState> stepEndEventsStates;

    /**
     * Initialization indicator of events states.
     */
    private boolean statesInitialized;

    /**
     * Name of the method.
     */
    private final String name;

    /**
     * Counter for number of evaluations.
     */
    private Incrementor evaluations;

    /**
     * Differential equations to integrate.
     */
    private ExpandableODE equations;

    /**
     * Build an instance.
     * @param name name of the method
     */
    protected AbstractIntegrator(final String name) {
        this.name = name;
        stepHandlers = new ArrayList<>();
        stepStart = null;
        stepSize = Double.NaN;
        detectorBasedEventsStates = new ArrayList<>();
        stepEndEventsStates = new ArrayList<>();
        statesInitialized = false;
        evaluations = new Incrementor();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addStepHandler(final ODEStepHandler handler) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ODEStepHandler> getStepHandlers() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearStepHandlers() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addEventDetector(final ODEEventDetector detector) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ODEEventDetector> getEventDetectors() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearEventDetectors() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addStepEndHandler(ODEStepEndHandler handler) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ODEStepEndHandler> getStepEndHandlers() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearStepEndHandlers() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getCurrentSignedStepsize() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMaxEvaluations(int maxEvaluations) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaxEvaluations() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getEvaluations() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Prepare the start of an integration.
     *
     * @param eqn equations to integrate
     * @param s0  initial state vector
     * @param t   target time for the integration
     * @return Initial state with computed derivatives.
     */
    protected ODEStateAndDerivative initIntegration(final ExpandableODE eqn, final ODEState s0, final double t) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the differential equations to integrate.
     * @return differential equations to integrate
     */
    protected ExpandableODE getEquations() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the evaluations counter.
     * @return evaluations counter
     */
    protected Incrementor getEvaluationsCounter() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the derivatives and check the number of evaluations.
     * @param t current value of the independent <I>time</I> variable
     * @param y array containing the current value of the state vector
     * @return state completed with derivatives
     * @exception MathIllegalArgumentException if arrays dimensions do not match equations settings
     * @exception MathIllegalStateException if the number of functions evaluations is exceeded
     * @exception NullPointerException if the ODE equations have not been set (i.e. if this method
     * is called outside of a call to {@link #integrate(ExpandableODE, ODEState, double) integrate}
     */
    public double[] computeDerivatives(final double t, final double[] y) throws MathIllegalArgumentException, MathIllegalStateException, NullPointerException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Increment evaluations of derivatives.
     *
     * @param nTimes number of evaluations to increment
     */
    protected void incrementEvaluations(final int nTimes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Set the stateInitialized flag.
     * <p>This method must be called by integrators with the value
     * {@code false} before they start integration, so a proper lazy
     * initialization is done automatically on the first step.</p>
     * @param stateInitialized new value for the flag
     */
    protected void setStateInitialized(final boolean stateInitialized) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Accept a step, triggering events and step handlers.
     * @param interpolator step interpolator
     * @param tEnd final integration time
     * @return state at end of step
     * @exception MathIllegalStateException if the interpolator throws one because
     * the number of functions evaluations is exceeded
     * @exception MathIllegalArgumentException if the location of an event cannot be bracketed
     * @exception MathIllegalArgumentException if arrays dimensions do not match equations settings
     */
    protected ODEStateAndDerivative acceptStep(final AbstractODEStateInterpolator interpolator, final double tEnd) throws MathIllegalArgumentException, MathIllegalStateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check the integration span.
     * @param initialState initial state
     * @param t target time for the integration
     * @exception MathIllegalArgumentException if integration span is too small
     * @exception MathIllegalArgumentException if adaptive step size integrators
     * tolerance arrays dimensions are not compatible with equations settings
     */
    protected void sanityChecks(final ODEState initialState, final double t) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if a reset occurred while last step was accepted.
     * @return true if a reset occurred while last step was accepted
     */
    protected boolean resetOccurred() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Set the current step size.
     * @param stepSize step size to set
     */
    protected void setStepSize(final double stepSize) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the current step size.
     * @return current step size
     */
    protected double getStepSize() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Set current step start.
     * @param stepStart step start
     */
    protected void setStepStart(final ODEStateAndDerivative stepStart) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    public ODEStateAndDerivative getStepStart() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Set the last state flag.
     * @param isLastStep if true, this step is the last one
     */
    protected void setIsLastStep(final boolean isLastStep) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if this step is the last one.
     * @return true if this step is the last one
     */
    protected boolean isLastStep() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
