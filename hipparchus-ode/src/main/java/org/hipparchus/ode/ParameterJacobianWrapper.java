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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.MathIllegalStateException;

/**
 * Wrapper class to compute Jacobian matrices by finite differences for ODE
 *  which do not compute them by themselves.
 */
class ParameterJacobianWrapper implements ODEJacobiansProvider {

    /**
     * ode base ordinary differential equation for which Jacobians
     * matrices are requested.
     */
    private final OrdinaryDifferentialEquation ode;

    /**
     * Steps for finite difference computation of the jacobian df/dy w.r.t. state.
     */
    private final double[] hY;

    /**
     * Controller to change parameters.
     */
    private final ParametersController controller;

    /**
     * Steps for finite difference computation of the Jacobian df/dp w.r.t. parameters.
     */
    private final Map<String, Double> hParam;

    /**
     * Wrap a {@link ParametersController} into a {@link NamedParameterJacobianProvider}.
     * @param ode ode base ordinary differential equation for which Jacobians
     * matrices are requested
     * @param hY step used for finite difference computation with respect to state vector
     * @param controller controller to change parameters
     * @param paramsAndSteps parameters and steps to compute the Jacobians df/dp
     */
    ParameterJacobianWrapper(final OrdinaryDifferentialEquation ode, final double[] hY, final ParametersController controller, final ParameterConfiguration[] paramsAndSteps) {
        this.ode = ode;
        this.hY = hY.clone();
        this.controller = controller;
        this.hParam = new HashMap<>();
        // set up parameters for jacobian computation
        for (final ParameterConfiguration param : paramsAndSteps) {
            final String name = param.getParameterName();
            if (controller.isSupported(name)) {
                hParam.put(name, param.getHP());
            }
        }
    }

    /**
     * Get the underlying ode.
     * @return underlying ode
     */
    public OrdinaryDifferentialEquation getODE() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDimension() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double[] computeDerivatives(double t, double[] y) throws MathIllegalArgumentException, MathIllegalStateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double[][] computeMainStateJacobian(double t, double[] y, double[] yDot) throws MathIllegalArgumentException, MathIllegalStateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getParametersNames() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSupported(String name) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double[] computeParameterJacobian(final double t, final double[] y, final double[] yDot, final String paramName) throws MathIllegalArgumentException, MathIllegalStateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
