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
package org.hipparchus.optim.nonlinear.vector.leastsquares;

import org.hipparchus.linear.RealMatrix;
import org.hipparchus.linear.RealVector;
import org.hipparchus.optim.nonlinear.vector.leastsquares.LeastSquaresOptimizer.Optimum;
import org.hipparchus.optim.nonlinear.vector.leastsquares.LeastSquaresProblem.Evaluation;

/**
 * A pedantic implementation of {@link Optimum}.
 */
class OptimumImpl implements Optimum {

    /**
     * abscissa and ordinate
     */
    private final Evaluation value;

    /**
     * number of evaluations to compute this optimum
     */
    private final int evaluations;

    /**
     * number of iterations to compute this optimum
     */
    private final int iterations;

    /**
     * Construct an optimum from an evaluation and the values of the counters.
     *
     * @param value       the function value
     * @param evaluations number of times the function was evaluated
     * @param iterations  number of iterations of the algorithm
     */
    OptimumImpl(final Evaluation value, final int evaluations, final int iterations) {
        this.value = value;
        this.evaluations = evaluations;
        this.iterations = iterations;
    }

    /* auto-generated implementations */
    /**
     * {@inheritDoc}
     */
    @Override
    public int getEvaluations() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getIterations() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RealMatrix getCovariances(double threshold) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RealVector getSigma(double covarianceSingularityThreshold) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getRMS() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RealMatrix getJacobian() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getCost() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getChiSquare() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getReducedChiSquare(int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RealVector getResiduals() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RealVector getPoint() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
