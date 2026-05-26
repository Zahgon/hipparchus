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

import org.hipparchus.linear.ArrayRealVector;
import org.hipparchus.linear.DecompositionSolver;
import org.hipparchus.linear.QRDecomposition;
import org.hipparchus.linear.RealMatrix;
import org.hipparchus.linear.RealVector;
import org.hipparchus.optim.nonlinear.vector.leastsquares.LeastSquaresProblem.Evaluation;
import org.hipparchus.util.FastMath;

/**
 * An implementation of {@link Evaluation} that is designed for extension. All of the
 * methods implemented here use the methods that are left unimplemented.
 */
public abstract class AbstractEvaluation implements Evaluation {

    /**
     * number of observations
     */
    private final int observationSize;

    /**
     * Constructor.
     *
     * @param observationSize the number of observations.
     * Needed for {@link #getRMS()} and {@link #getReducedChiSquare(int)}.
     */
    protected AbstractEvaluation(final int observationSize) {
        this.observationSize = observationSize;
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
    public double getReducedChiSquare(int numberOfFittedParameters) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
