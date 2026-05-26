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
import org.hipparchus.CalculusFieldElement;
import org.hipparchus.Field;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.MathIllegalStateException;
import org.hipparchus.ode.events.Action;
import org.hipparchus.ode.events.FieldDetectorBasedEventState;
import org.hipparchus.ode.events.FieldEventOccurrence;
import org.hipparchus.ode.events.FieldEventState;
import org.hipparchus.ode.events.FieldODEEventDetector;
import org.hipparchus.ode.events.FieldODEStepEndHandler;
import org.hipparchus.ode.events.FieldStepEndEventState;
import org.hipparchus.ode.sampling.AbstractFieldODEStateInterpolator;
import org.hipparchus.ode.sampling.FieldODEStateInterpolator;
import org.hipparchus.ode.sampling.FieldODEStepHandler;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.Incrementor;

/**
 * Base class managing common boilerplate for all integrators.
 * @param <T> the type of the field elements
 */
public abstract class AbstractFieldIntegrator<T extends CalculusFieldElement<T>> implements FieldODEIntegrator<T> {

    /**
     * Step handler.
     */
    private final List<FieldODEStepHandler<T>> stepHandlers;

    /**
     * Current step start.
     */
    private FieldODEStateAndDerivative<T> stepStart;

    /**
     * Current stepsize.
     */
    private T stepSize;

    /**
     * Indicator for last step.
     */
    private boolean isLastStep;

    /**
     * Indicator that a state or derivative reset was triggered by some event.
     */
    private boolean resetOccurred;

    /**
     * Field to which the time and state vector elements belong.
     */
    private final Field<T> field;

    /**
     * Events states.
     */
    private final List<FieldDetectorBasedEventState<T>> detectorBasedEventsStates;

    /**
     * Events states related to step end.
     */
    private final List<FieldStepEndEventState<T>> stepEndEventsStates;

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
    private transient FieldExpandableODE<T> equations;

    /**
     * Build an instance.
     * @param field field to which the time and state vector elements belong
     * @param name name of the method
     */
    protected AbstractFieldIntegrator(final Field<T> field, final String name) {
        this.field = field;
        this.name = name;
        stepHandlers = new ArrayList<>();
        stepStart = null;
        stepSize = null;
        detectorBasedEventsStates = new ArrayList<>();
        stepEndEventsStates = new ArrayList<>();
        statesInitialized = false;
        evaluations = new Incrementor();
    }

    /**
     * Get the field to which state vector elements belong.
     * @return field to which state vector elements belong
     */
    public Field<T> getField() {
        throw new UnsupportedOperationException("STUB: not implemented");
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
    public void addStepHandler(final FieldODEStepHandler<T> handler) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FieldODEStepHandler<T>> getStepHandlers() {
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
    public void addEventDetector(final FieldODEEventDetector<T> detector) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FieldODEEventDetector<T>> getEventDetectors() {
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
    public void addStepEndHandler(FieldODEStepEndHandler<T> handler) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FieldODEStepEndHandler<T>> getStepEndHandlers() {
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
    public T getCurrentSignedStepsize() {
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
     * @param eqn equations to integrate
     * @param s0 initial state vector
     * @param t target time for the integration
     * @return initial state with derivatives added
     */
    protected FieldODEStateAndDerivative<T> initIntegration(final FieldExpandableODE<T> eqn, final FieldODEState<T> s0, final T t) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the differential equations to integrate.
     * @return differential equations to integrate
     */
    protected FieldExpandableODE<T> getEquations() {
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
     * is called outside of a call to {@link #integrate(FieldExpandableODE, FieldODEState,
     * CalculusFieldElement) integrate}
     */
    public T[] computeDerivatives(final T t, final T[] y) throws MathIllegalArgumentException, MathIllegalStateException, NullPointerException {
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
     *
     * @param interpolator step interpolator
     * @param tEnd         final integration time
     * @return state at end of step
     * @throws MathIllegalStateException    if the interpolator throws one because the
     *                                      number of functions evaluations is exceeded
     * @throws MathIllegalArgumentException if the location of an event cannot be
     *                                      bracketed
     * @throws MathIllegalArgumentException if arrays dimensions do not match equations
     *                                      settings
     */
    protected FieldODEStateAndDerivative<T> acceptStep(final AbstractFieldODEStateInterpolator<T> interpolator, final T tEnd) throws MathIllegalArgumentException, MathIllegalStateException {
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
    protected void sanityChecks(final FieldODEState<T> initialState, final T t) throws MathIllegalArgumentException {
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
    protected void setStepSize(final T stepSize) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the current step size.
     * @return current step size
     */
    protected T getStepSize() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Set current step start.
     * @param stepStart step start
     */
    protected void setStepStart(final FieldODEStateAndDerivative<T> stepStart) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    public FieldODEStateAndDerivative<T> getStepStart() {
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
