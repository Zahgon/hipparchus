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
package org.hipparchus.ode;

import java.lang.reflect.Array;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.MathIllegalStateException;

/**
 * This class defines a set of {@link SecondaryODE secondary equations} to
 * compute the global Jacobian matrices with respect to the initial state
 * vector and, if any, to some parameters of the primary ODE set.
 * <p>
 * The primary set of ODE for which Jaobian matrices are requested may be:
 * </p>
 * <ul>
 * <li>a full-fledged {@link ODEJacobiansProvider} that computes by itself
 * both the ODE and its local partial derivatives,</li>
 * <li>a simple {@link OrdinaryDifferentialEquation} which must therefore
 * be completed with a finite differences configuration to compute local
 * partial derivatives (so-called internal differentiation).</li>
 * </ul>
 * <p>
 * As the variational equation automatically inserts {@link
 * ExpandableODE#addSecondaryEquations(SecondaryODE) secondary differential
 * equations}, in the {@link ExpandableODE expandable ODE}, data for
 * initial state must also be inserted before integration and matrices
 * result must be extracted after integration. This implies a precise
 * scheduling of the calls to the various methods of this class. The
 * proper scheduling is the following one:
 * </p>
 * <pre>
 *   // set up equations
 *   ODEJacobiansProvider jode       = new MyODE(...);
 *   ExpandableODE        expandable = new Expandable(jode);
 *   VariationalEquation  ve         = new VariationalEquation(expandable, jode);
 *
 *   // set up initial state
 *   ODEState initWithoutDerivatives = new ODEState(t0, y0);
 *   ve.setInitialMainStateJacobian(dYdY0); // only needed if the default identity matrix is not suitable
 *   ve.setInitialParameterJacobian(name, dYdP); // only needed if the default zero matrix is not suitable
 *   ODEState initWithDerivatives = ve.setUpInitialState(initWithoutDerivatives);
 *
 *   // perform integration on the expanded equations with the expanded initial state
 *   ODEStateAndDerivative finalState = integrator.integrate(expandable, initWithDerivatives, finalT);
 *
 *   // extract Jacobian matrices
 *   dYdY0 = ve.extractMainSetJacobian(finalState);
 *   dYdP  = ve.extractParameterJacobian(finalState, name);
 * </pre>
 * <p>
 * The most important part is to not forget to call {@link #setUpInitialState(ODEState)} to add
 * the secondary state with the initial matrices to the {@link ODEState} used in the
 * {@link ODEIntegrator#integrate(ExpandableODE, ODEState, double) integrate} method.
 * Forgetting to do this and passing only a {@link ODEState} without the secondary state
 * set up will trigger an error as the state vector will not have the correct dimension.
 * </p>
 *
 * @see ExpandableODE
 * @see ODEJacobiansProvider
 * @see OrdinaryDifferentialEquation
 * @see NamedParameterJacobianProvider
 * @see ParametersController
 */
public class VariationalEquation {

    /**
     * ODE with Jacobian computation skill.
     */
    private final ODEJacobiansProvider jode;

    /**
     * Expandable first order differential equation.
     */
    private final ExpandableODE expandable;

    /**
     * Index of the instance in the expandable set.
     */
    private final int index;

    /**
     * State and parameters Jacobian matrices in a row.
     */
    private double[] matricesData;

    /**
     * Build variational equation using finite differences for local
     * partial derivatives.
     * @param expandable expandable set into which variational equations should be registered
     * @param ode base ordinary differential equation for which Jacobians
     * matrices are requested
     * @param hY step used for finite difference computation with respect to state vector
     * @param controller controller to change parameters
     * @param paramsAndSteps parameters and steps to compute the Jacobians df/dp
     * @exception MismatchedEquations if the primary set of the expandable set does
     * not match the {@code ode}
     */
    public VariationalEquation(final ExpandableODE expandable, final OrdinaryDifferentialEquation ode, final double[] hY, final ParametersController controller, final ParameterConfiguration... paramsAndSteps) throws MismatchedEquations {
        this(expandable, new ParameterJacobianWrapper(ode, hY, controller, paramsAndSteps));
    }

    /**
     * Build variational equation using analytical local partial derivatives.
     * <p>
     * Parameters must belong to the supported ones given by {@link
     * Parameterizable#getParametersNames()}, so the primary set of differential
     * equations must be {@link Parameterizable}.
     * </p>
     * <p>Note that each selection clears the previous selected parameters.</p>
     *
     * @param expandable expandable set into which variational equations should be registered
     * @param jode the primary first order differential equations set to extend
     * @exception MismatchedEquations if the primary set of the expandable set does
     * not match the {@code ode}
     */
    public VariationalEquation(final ExpandableODE expandable, final ODEJacobiansProvider jode) throws MismatchedEquations {
        // safety checks
        final OrdinaryDifferentialEquation ode;
        if (jode instanceof ParameterJacobianWrapper) {
            ode = ((ParameterJacobianWrapper) jode).getODE();
        } else {
            ode = jode;
        }
        if (expandable.getPrimary() != ode) {
            throw new MismatchedEquations();
        }
        this.jode = jode;
        this.expandable = expandable;
        this.index = expandable.addSecondaryEquations(new JacobiansSecondaryODE());
        // set the default initial state Jacobian to the identity
        // and the default initial parameters Jacobian to the null matrix
        matricesData = new double[(jode.getDimension() + jode.getParametersNames().size()) * jode.getDimension()];
        for (int i = 0; i < jode.getDimension(); ++i) {
            matricesData[i * (jode.getDimension() + 1)] = 1.0;
        }
    }

    /**
     * Set the initial value of the Jacobian matrix with respect to state.
     * <p>
     * If this method is not called, the initial value of the Jacobian
     * matrix with respect to state is set to identity.
     * </p>
     * <p>
     * This method must be called <em>before {@link #setUpInitialState(ODEState)}</em>
     * </p>
     * @param dYdY0 initial Jacobian matrix w.r.t. state
     * @exception MathIllegalArgumentException if matrix dimensions are incorrect
     */
    public void setInitialMainStateJacobian(final double[][] dYdY0) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Set the initial value of a column of the Jacobian matrix with respect to one parameter.
     * <p>
     * If this method is not called for some parameter, the initial value of
     * the column of the Jacobian matrix with respect to this parameter is set to zero.
     * </p>
     * <p>
     * This method must be called <em>before {@link #setUpInitialState(ODEState)}</em>
     * </p>
     * @param pName parameter name
     * @param dYdP initial Jacobian column vector with respect to the parameter
     * @exception MathIllegalArgumentException if a parameter is not supported
     * @throws MathIllegalArgumentException if the column vector does not match state dimension
     */
    public void setInitialParameterJacobian(final String pName, final double[] dYdP) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Set up initial state.
     * <p>
     * This method inserts the initial Jacobian matrices data into
     * an {@link ODEState ODE state} by overriding the additional
     * state components corresponding to the instance. It must be
     * called prior to integrate the equations.
     * </p>
     * <p>
     * This method must be called <em>after</em>
     * {@link #setInitialMainStateJacobian(double[][])} and
     * {@link #setInitialParameterJacobian(String, double[])}.
     * </p>
     * @param initialState initial state, without the initial Jacobians
     * matrices
     * @return a new instance of initial state, with the initial Jacobians
     * matrices properly initialized
     */
    public ODEState setUpInitialState(final ODEState initialState) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Extract the Jacobian matrix with respect to state.
     * @param state state from which to extract Jacobian matrix
     * @return Jacobian matrix dY/dY0 with respect to state.
     */
    public double[][] extractMainSetJacobian(final ODEState state) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Extract the Jacobian matrix with respect to one parameter.
     * @param state state from which to extract Jacobian matrix
     * @param pName name of the parameter for the computed Jacobian matrix
     * @return Jacobian matrix dY/dP with respect to the named parameter
     */
    public double[] extractParameterJacobian(final ODEState state, final String pName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check array dimensions.
     * @param expected expected dimension
     * @param array (may be null if expected is 0)
     * @throws MathIllegalArgumentException if the array dimension does not match the expected one
     */
    private void checkDimension(final int expected, final Object array) throws MathIllegalArgumentException {
        int arrayDimension = (array == null) ? 0 : Array.getLength(array);
        if (arrayDimension != expected) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.DIMENSIONS_MISMATCH, arrayDimension, expected);
        }
    }

    /**
     * Local implementation of secondary equations.
     */
    private class JacobiansSecondaryODE implements SecondaryODE {

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
        public double[] computeDerivatives(final double t, final double[] y, final double[] yDot, final double[] z) throws MathIllegalArgumentException, MathIllegalStateException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Special exception for equations mismatch.
     */
    public static class MismatchedEquations extends MathIllegalArgumentException {

        /**
         * Serializable UID.
         */
        private static final long serialVersionUID = 20120902L;

        /**
         * Simple constructor.
         */
        public MismatchedEquations() {
            super(LocalizedODEFormats.UNMATCHED_ODE_IN_EXPANDED_SET);
        }
    }
}
