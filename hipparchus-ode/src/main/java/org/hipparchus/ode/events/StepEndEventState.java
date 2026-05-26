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
package org.hipparchus.ode.events;

import org.hipparchus.ode.ODEState;
import org.hipparchus.ode.ODEStateAndDerivative;
import org.hipparchus.ode.sampling.ODEStateInterpolator;

/**
 * This class handles the state for one {@link ODEEventHandler
 * event handler} that triggers at step end.
 * @since 3.0
 */
public class StepEndEventState implements EventState {

    /**
     * Step end handler.
     */
    private final ODEStepEndHandler handler;

    /**
     * Time at step end.
     */
    private double stepEnd;

    /**
     * Integration direction.
     */
    private boolean forward;

    /**
     * Simple constructor.
     * @param handler step end handler
     */
    public StepEndEventState(final ODEStepEndHandler handler) {
        this.handler = handler;
        this.stepEnd = Double.NaN;
    }

    /**
     * Get the underlying step end handler.
     * @return underlying step end handler
     */
    public ODEStepEndHandler getHandler() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(final ODEStateAndDerivative s0, final double t) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Set the step end.
     * @param stepEnd step end
     */
    public void setStepEnd(final double stepEnd) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean evaluateStep(final ODEStateInterpolator interpolator) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getEventTime() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EventOccurrence doEvent(final ODEStateAndDerivative state) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
