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
import org.hipparchus.ode.nonstiff.HighamHall54Integrator;

/**
 * This class represents an interpolator over the last step during an
 * ODE integration for the 5(4) Higham and Hall integrator.
 *
 * @see HighamHall54Integrator
 */
public class HighamHall54StateInterpolator extends RungeKuttaStateInterpolator {

    /**
     * Serializable version identifier
     */
    private static final long serialVersionUID = 20111120L;

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
    public HighamHall54StateInterpolator(final boolean forward, final double[][] yDotK, final ODEStateAndDerivative globalPreviousState, final ODEStateAndDerivative globalCurrentState, final ODEStateAndDerivative softPreviousState, final ODEStateAndDerivative softCurrentState, final EquationsMapper mapper) {
        super(forward, yDotK, globalPreviousState, globalCurrentState, softPreviousState, softCurrentState, mapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected HighamHall54StateInterpolator create(final boolean newForward, final double[][] newYDotK, final ODEStateAndDerivative newGlobalPreviousState, final ODEStateAndDerivative newGlobalCurrentState, final ODEStateAndDerivative newSoftPreviousState, final ODEStateAndDerivative newSoftCurrentState, final EquationsMapper newMapper) {
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
