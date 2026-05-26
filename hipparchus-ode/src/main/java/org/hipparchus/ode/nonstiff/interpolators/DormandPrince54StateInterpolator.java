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
package org.hipparchus.ode.nonstiff.interpolators;

import org.hipparchus.ode.EquationsMapper;
import org.hipparchus.ode.ODEStateAndDerivative;
import org.hipparchus.ode.nonstiff.DormandPrince54Integrator;

/**
 * This class represents an interpolator over the last step during an
 * ODE integration for the 5(4) Dormand-Prince integrator.
 *
 * @see DormandPrince54Integrator
 */
public class DormandPrince54StateInterpolator extends RungeKuttaStateInterpolator {

    /**
     * Last row of the Butcher-array internal weights, element 0.
     */
    private static final double A70 = 35.0 / 384.0;

    // element 1 is zero, so it is neither stored nor used
    /**
     * Last row of the Butcher-array internal weights, element 2.
     */
    private static final double A72 = 500.0 / 1113.0;

    /**
     * Last row of the Butcher-array internal weights, element 3.
     */
    private static final double A73 = 125.0 / 192.0;

    /**
     * Last row of the Butcher-array internal weights, element 4.
     */
    private static final double A74 = -2187.0 / 6784.0;

    /**
     * Last row of the Butcher-array internal weights, element 5.
     */
    private static final double A75 = 11.0 / 84.0;

    /**
     * Shampine (1986) Dense output, element 0.
     */
    private static final double D0 = -12715105075.0 / 11282082432.0;

    // element 1 is zero, so it is neither stored nor used
    /**
     * Shampine (1986) Dense output, element 2.
     */
    private static final double D2 = 87487479700.0 / 32700410799.0;

    /**
     * Shampine (1986) Dense output, element 3.
     */
    private static final double D3 = -10690763975.0 / 1880347072.0;

    /**
     * Shampine (1986) Dense output, element 4.
     */
    private static final double D4 = 701980252875.0 / 199316789632.0;

    /**
     * Shampine (1986) Dense output, element 5.
     */
    private static final double D5 = -1453857185.0 / 822651844.0;

    /**
     * Shampine (1986) Dense output, element 6.
     */
    private static final double D6 = 69997945.0 / 29380423.0;

    /**
     * Serializable version identifier.
     */
    private static final long serialVersionUID = 20160328L;

    /**
     * Simple constructor.
     * @param forward integration direction indicator
     * @param yDotK slopes at the intermediate points
     * @param globalPreviousState start of the global step
     * @param globalCurrentState end of the global step
     * @param softPreviousState start of the restricted step
     * @param softCurrentState end of the restricted step
     * @param mapper equations mapper for the all equations
     */
    public DormandPrince54StateInterpolator(final boolean forward, final double[][] yDotK, final ODEStateAndDerivative globalPreviousState, final ODEStateAndDerivative globalCurrentState, final ODEStateAndDerivative softPreviousState, final ODEStateAndDerivative softCurrentState, final EquationsMapper mapper) {
        super(forward, yDotK, globalPreviousState, globalCurrentState, softPreviousState, softCurrentState, mapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected DormandPrince54StateInterpolator create(final boolean newForward, final double[][] newYDotK, final ODEStateAndDerivative newGlobalPreviousState, final ODEStateAndDerivative newGlobalCurrentState, final ODEStateAndDerivative newSoftPreviousState, final ODEStateAndDerivative newSoftCurrentState, final EquationsMapper newMapper) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ODEStateAndDerivative computeInterpolatedStateAndDerivatives(final EquationsMapper mapper, final double time, final double theta, final double thetaH, final double oneMinusThetaH) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
