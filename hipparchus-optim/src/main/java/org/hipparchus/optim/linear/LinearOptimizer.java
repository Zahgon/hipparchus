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
package org.hipparchus.optim.linear;

import java.util.Collection;
import java.util.Collections;
import org.hipparchus.exception.MathIllegalStateException;
import org.hipparchus.optim.OptimizationData;
import org.hipparchus.optim.PointValuePair;
import org.hipparchus.optim.nonlinear.scalar.MultivariateOptimizer;

/**
 * Base class for implementing linear optimizers.
 */
public abstract class LinearOptimizer extends MultivariateOptimizer {

    /**
     * Linear objective function.
     */
    private LinearObjectiveFunction function;

    /**
     * Linear constraints.
     */
    private Collection<LinearConstraint> linearConstraints;

    /**
     * Whether to restrict the variables to non-negative values.
     */
    private boolean nonNegative;

    /**
     * Simple constructor with default settings.
     */
    protected LinearOptimizer() {
        // No convergence checker.
        super(null);
    }

    /**
     * Check if variables are restricted to non-negative values.
     * @return {@code true} if the variables are restricted to non-negative values
     */
    protected boolean isRestrictedToNonNegative() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get optimization type.
     * @return the optimization type
     */
    protected LinearObjectiveFunction getFunction() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get constraints.
     * @return the constraints
     */
    protected Collection<LinearConstraint> getConstraints() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * @param optData Optimization data. In addition to those documented in
     * {@link MultivariateOptimizer#parseOptimizationData(OptimizationData[])
     * MultivariateOptimizer}, this method will register the following data:
     * <ul>
     *  <li>{@link LinearObjectiveFunction}</li>
     *  <li>{@link LinearConstraintSet}</li>
     *  <li>{@link NonNegativeConstraint}</li>
     * </ul>
     * @return {@inheritDoc}
     * @throws MathIllegalStateException if the maximal number of
     * iterations is exceeded.
     */
    @Override
    public PointValuePair optimize(OptimizationData... optData) throws MathIllegalStateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Scans the list of (required and optional) optimization data that
     * characterize the problem.
     *
     * @param optData Optimization data.
     * The following data will be looked for:
     * <ul>
     *  <li>{@link LinearObjectiveFunction}</li>
     *  <li>{@link LinearConstraintSet}</li>
     *  <li>{@link NonNegativeConstraint}</li>
     * </ul>
     */
    @Override
    protected void parseOptimizationData(OptimizationData... optData) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
