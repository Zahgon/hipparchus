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
package org.hipparchus.ode.events;

import java.util.Arrays;
import org.hipparchus.analysis.UnivariateFunction;
import org.hipparchus.analysis.solvers.BracketedUnivariateSolver;
import org.hipparchus.ode.ODEState;
import org.hipparchus.ode.ODEStateAndDerivative;

/**
 * Wrapper used to detect only increasing or decreasing events.
 *
 * <p>General {@link ODEEventDetector events} are defined implicitly
 * by a {@link ODEEventDetector#g(ODEStateAndDerivative) g function} crossing
 * zero. This function needs to be continuous in the event neighborhood,
 * and its sign must remain consistent between events. This implies that
 * during an ODE integration, events triggered are alternately events
 * for which the function increases from negative to positive values,
 * and events for which the function decreases from positive to
 * negative values.
 * </p>
 *
 * <p>Sometimes, users are only interested in one type of event (say
 * increasing events for example) and not in the other type. In these
 * cases, looking precisely for all events location and triggering
 * events that will later be ignored is a waste of computing time.</p>
 *
 * <p>Users can wrap a regular {@link ODEEventDetector event detector} in
 * an instance of this class and provide this wrapping instance to
 * the {@link org.hipparchus.ode.ODEIntegrator ODE solver}
 * in order to avoid wasting time looking for uninteresting events.
 * The wrapper will intercept the calls to the {@link
 * ODEEventDetector#g(ODEStateAndDerivative) g function} and to the {@link
 * ODEEventHandler#eventOccurred(ODEStateAndDerivative, ODEEventDetector, boolean)
 * eventOccurred} method in order to ignore uninteresting events. The
 * wrapped regular {@link ODEEventHandler event handler} will the see only
 * the interesting events, i.e. either only {@code increasing} events or
 * {@code decreasing} events. the number of calls to the {@link
 * ODEEventDetector#g(ODEStateAndDerivative) g function} will also be reduced.</p>
 * @param <T> type of the event detector
 * @since 3.0
 */
public class EventSlopeFilter<T extends ODEEventDetector> extends AbstractODEDetector<EventSlopeFilter<T>> {

    /**
     * Number of past transformers updates stored.
     */
    private static final int HISTORY_SIZE = 100;

    /**
     * Wrapped event detector.
     * @since 3.0
     */
    private final T rawDetector;

    /**
     * Filter to use.
     */
    private final FilterType filter;

    /**
     * Transformers of the g function.
     */
    private final Transformer[] transformers;

    /**
     * Update time of the transformers.
     */
    private final double[] updates;

    /**
     * Indicator for forward integration.
     */
    private boolean forward;

    /**
     * Extreme time encountered so far.
     */
    private double extremeT;

    /**
     * Wrap an {@link ODEEventDetector event detector}.
     * @param rawDetector event detector to wrap
     * @param filter filter to use
     * @since 3.0
     */
    public EventSlopeFilter(final T rawDetector, final FilterType filter) {
        this(rawDetector.getMaxCheckInterval(), rawDetector.getMaxIterationCount(), rawDetector.getSolver(), new LocalHandler<>(rawDetector.getHandler()), rawDetector, filter);
    }

    /**
     * Private constructor with full parameters.
     * <p>
     * This constructor is private as users are expected to use the builder
     * API with the various {@code withXxx()} methods to set up the instance
     * in a readable manner without using a huge amount of parameters.
     * </p>
     * @param maxCheck maximum checking interval (s)
     * @param maxIter maximum number of iterations in the event time search
     * @param solver root-finding algorithm to use to detect state events
     * @param handler event handler to call at event occurrences
     * @param rawDetector event detector to wrap
     * @param filter filter to use
     */
    private EventSlopeFilter(final AdaptableInterval maxCheck, final int maxIter, final BracketedUnivariateSolver<UnivariateFunction> solver, final ODEEventHandler handler, final T rawDetector, final FilterType filter) {
        super(maxCheck, maxIter, solver, handler);
        this.rawDetector = rawDetector;
        this.filter = filter;
        this.transformers = new Transformer[HISTORY_SIZE];
        this.updates = new double[HISTORY_SIZE];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected EventSlopeFilter<T> create(final AdaptableInterval newMaxCheck, final int newMaxIter, final BracketedUnivariateSolver<UnivariateFunction> newSolver, final ODEEventHandler newHandler) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the wrapped raw detector.
     * @return the wrapped raw detector
     */
    public T getDetector() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    public void init(final ODEStateAndDerivative initialState, double finalTime) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    public void reset(final ODEStateAndDerivative intermediateState, final double finalTime) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    public boolean isForward() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    public double g(final ODEStateAndDerivative state) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Local handler.
     * @param <T> type of the event detector
     */
    private static class LocalHandler<T extends ODEEventDetector> implements ODEEventHandler {

        /**
         * Raw handler.
         */
        private final ODEEventHandler rawHandler;

        /**
         * Simple constructor.
         * @param rawHandler raw handler
         */
        LocalHandler(final ODEEventHandler rawHandler) {
            this.rawHandler = rawHandler;
        }

        /**
         *  {@inheritDoc}
         */
        @Override
        public Action eventOccurred(final ODEStateAndDerivative state, final ODEEventDetector detector, final boolean increasing) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         *  {@inheritDoc}
         */
        @Override
        public ODEState resetState(final ODEEventDetector detector, final ODEStateAndDerivative state) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
