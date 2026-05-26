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
package org.hipparchus.filtering.kalman;

import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.linear.MatrixDecomposer;
import org.hipparchus.linear.RealMatrix;
import org.hipparchus.linear.RealVector;

/**
 * Shared parts between linear and non-linear Kalman filters.
 * @param <T> the type of the measurements
 * @since 1.3
 */
public abstract class AbstractKalmanFilter<T extends Measurement> implements KalmanFilter<T> {

    /**
     * Decomposer decomposer to use for the correction phase.
     */
    private final MatrixDecomposer decomposer;

    /**
     * Predicted state.
     */
    private ProcessEstimate predicted;

    /**
     * Corrected state.
     */
    private ProcessEstimate corrected;

    /**
     * Prior corrected covariance.
     */
    private RealMatrix priorCovariance;

    /**
     * State transition matrix.
     */
    private RealMatrix stateTransitionMatrix;

    /**
     * Observer.
     */
    private KalmanObserver observer;

    /**
     * Simple constructor.
     * @param decomposer decomposer to use for the correction phase
     * @param initialState initial state
     */
    protected AbstractKalmanFilter(final MatrixDecomposer decomposer, final ProcessEstimate initialState) {
        this.decomposer = decomposer;
        this.corrected = initialState;
        this.priorCovariance = null;
        this.stateTransitionMatrix = null;
        this.observer = null;
    }

    /**
     * Perform prediction step.
     * @param time process time
     * @param predictedState predicted state vector
     * @param stm state transition matrix
     * @param noise process noise covariance matrix
     */
    protected void predict(final double time, final RealVector predictedState, final RealMatrix stm, final RealMatrix noise) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute innovation covariance matrix.
     * @param r measurement covariance
     * @param h Jacobian of the measurement with respect to the state
     * (may be null if measurement should be ignored)
     * @return innovation covariance matrix, defined as \(h.P.h^T + r\), or
     * null if h is null
     */
    protected RealMatrix computeInnovationCovarianceMatrix(final RealMatrix r, final RealMatrix h) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Perform correction step.
     * @param measurement single measurement to handle
     * @param stm state transition matrix
     * @param innovation innovation vector (i.e. residuals)
     * (may be null if measurement should be ignored)
     * @param h Jacobian of the measurement with respect to the state
     * (may be null if measurement should be ignored)
     * @param s innovation covariance matrix
     * (may be null if measurement should be ignored)
     * @exception MathIllegalArgumentException if matrix cannot be decomposed
     */
    protected void correct(final T measurement, final RealMatrix stm, final RealVector innovation, final RealMatrix h, final RealMatrix s) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the observer.
     * @return the observer
     */
    protected KalmanObserver getObserver() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setObserver(final KalmanObserver kalmanObserver) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the predicted state.
     * @return predicted state
     */
    @Override
    public ProcessEstimate getPredicted() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the corrected state.
     * @return corrected state
     */
    @Override
    public ProcessEstimate getCorrected() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RealMatrix getStateCrossCovariance() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
