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

import org.hipparchus.CalculusFieldElement;
import org.hipparchus.ode.FieldODEState;
import org.hipparchus.ode.FieldODEStateAndDerivative;
import org.hipparchus.ode.sampling.FieldODEStateInterpolator;

/**
 * This class handles the state for one {@link ODEEventHandler
 * event handler} that triggers at step end.
 * @since 3.0
 * @param <T> the type of the field elements
 */
public class FieldStepEndEventState<T extends CalculusFieldElement<T>> implements FieldEventState<T> {

    /**
     * Step end handler.
     */
    private final FieldODEStepEndHandler<T> handler;

    /**
     * Time at step end.
     */
    private T stepEnd;

    /**
     * Integration direction.
     */
    private boolean forward;

    /**
     * Simple constructor.
     * @param handler step end handler
     */
    public FieldStepEndEventState(final FieldODEStepEndHandler<T> handler) {
        this.handler = handler;
        this.stepEnd = null;
    }

    /**
     * Get the underlying step end handler.
     * @return underlying step end handler
     */
    public FieldODEStepEndHandler<T> getHandler() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(final FieldODEStateAndDerivative<T> s0, final T t) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Set the step end.
     * @param stepEnd step end
     */
    public void setStepEnd(final T stepEnd) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean evaluateStep(final FieldODEStateInterpolator<T> interpolator) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getEventTime() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldEventOccurrence<T> doEvent(final FieldODEStateAndDerivative<T> state) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
