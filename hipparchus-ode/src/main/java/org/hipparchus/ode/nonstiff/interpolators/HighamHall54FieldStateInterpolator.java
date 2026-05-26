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
package org.hipparchus.ode.nonstiff.interpolators;

import org.hipparchus.CalculusFieldElement;
import org.hipparchus.Field;
import org.hipparchus.ode.FieldEquationsMapper;
import org.hipparchus.ode.FieldODEStateAndDerivative;
import org.hipparchus.ode.nonstiff.HighamHall54FieldIntegrator;

/**
 * This class represents an interpolator over the last step during an
 * ODE integration for the 5(4) Higham and Hall integrator.
 *
 * @see HighamHall54FieldIntegrator
 *
 * @param <T> the type of the field elements
 */
public class HighamHall54FieldStateInterpolator<T extends CalculusFieldElement<T>> extends RungeKuttaFieldStateInterpolator<T> {

    /**
     * Simple constructor.
     * @param field field to which the time and state vector elements belong
     * @param forward integration direction indicator
     * @param yDotK slopes at the intermediate points
     * @param globalPreviousState start of the global step
     * @param globalCurrentState end of the global step
     * @param softPreviousState start of the restricted step
     * @param softCurrentState end of the restricted step
     * @param mapper equations mapper for the all equations
     */
    public HighamHall54FieldStateInterpolator(final Field<T> field, final boolean forward, final T[][] yDotK, final FieldODEStateAndDerivative<T> globalPreviousState, final FieldODEStateAndDerivative<T> globalCurrentState, final FieldODEStateAndDerivative<T> softPreviousState, final FieldODEStateAndDerivative<T> softCurrentState, final FieldEquationsMapper<T> mapper) {
        super(field, forward, yDotK, globalPreviousState, globalCurrentState, softPreviousState, softCurrentState, mapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected HighamHall54FieldStateInterpolator<T> create(final Field<T> newField, final boolean newForward, final T[][] newYDotK, final FieldODEStateAndDerivative<T> newGlobalPreviousState, final FieldODEStateAndDerivative<T> newGlobalCurrentState, final FieldODEStateAndDerivative<T> newSoftPreviousState, final FieldODEStateAndDerivative<T> newSoftCurrentState, final FieldEquationsMapper<T> newMapper) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    protected FieldODEStateAndDerivative<T> computeInterpolatedStateAndDerivatives(final FieldEquationsMapper<T> mapper, final T time, final T theta, final T thetaH, final T oneMinusThetaH) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
