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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.MathIllegalStateException;
import org.hipparchus.ode.sampling.ODEStateInterpolator;
import org.hipparchus.ode.sampling.ODEStepHandler;
import org.hipparchus.util.FastMath;

/**
 * This class stores all information provided by an ODE integrator
 * during the integration process and build a continuous model of the
 * solution from this.
 *
 * <p>This class act as a step handler from the integrator point of
 * view. It is called iteratively during the integration process and
 * stores a copy of all steps information in a sorted collection for
 * later use. Once the integration process is over, the user can use
 * the {@link #getInterpolatedState(double) getInterpolatedState}
 * method to retrieve this information at any time. It is important
 * to wait for the integration to be over before attempting to call
 * {@link #getInterpolatedState(double) getInterpolatedState} because
 * some internal variables are set only once the last step has been
 * handled.</p>
 *
 * <p>This is useful for example if the main loop of the user
 * application should remain independent from the integration process
 * or if one needs to mimic the behaviour of an analytical model
 * despite a numerical model is used (i.e. one needs the ability to
 * get the model value at any time or to navigate through the
 * data).</p>
 *
 * <p>If problem modeling is done with several separate
 * integration phases for contiguous intervals, the same
 * DenseOutputModel can be used as step handler for all
 * integration phases as long as they are performed in order and in
 * the same direction. As an example, one can extrapolate the
 * trajectory of a satellite with one model (i.e. one set of
 * differential equations) up to the beginning of a maneuver, use
 * another more complex model including thrusters modeling and
 * accurate attitude control during the maneuver, and revert to the
 * first model after the end of the maneuver. If the same continuous
 * output model handles the steps of all integration phases, the user
 * do not need to bother when the maneuver begins or ends, he has all
 * the data available in a transparent manner.</p>
 *
 * <p>An important feature of this class is that it implements the
 * <code>Serializable</code> interface. This means that the result of
 * an integration can be serialized and reused later (if stored into a
 * persistent medium like a filesystem or a database) or elsewhere (if
 * sent to another application). Only the result of the integration is
 * stored, there is no reference to the integrated problem by
 * itself.</p>
 *
 * <p>One should be aware that the amount of data stored in a
 * DenseOutputModel instance can be important if the state vector
 * is large, if the integration interval is long or if the steps are
 * small (which can result from small tolerance settings in {@link
 * org.hipparchus.ode.nonstiff.AdaptiveStepsizeIntegrator adaptive
 * step size integrators}).</p>
 *
 * @see ODEStepHandler
 * @see ODEStateInterpolator
 */
public class DenseOutputModel implements ODEStepHandler, Serializable {

    /**
     * Serializable version identifier
     */
    private static final long serialVersionUID = 20160328L;

    /**
     * Initial integration time.
     */
    private double initialTime;

    /**
     * Final integration time.
     */
    private double finalTime;

    /**
     * Integration direction indicator.
     */
    private boolean forward;

    /**
     * Current interpolator index.
     */
    private int index;

    /**
     * Steps table.
     */
    private List<ODEStateInterpolator> steps;

    /**
     * Simple constructor.
     * Build an empty continuous output model.
     */
    public DenseOutputModel() {
        steps = new ArrayList<>();
        initialTime = Double.NaN;
        finalTime = Double.NaN;
        forward = true;
        index = 0;
    }

    /**
     * Append another model at the end of the instance.
     * @param model model to add at the end of the instance
     * @exception MathIllegalArgumentException if the model to append is not
     * compatible with the instance (dimension of the state vector,
     * propagation direction, hole between the dates)
     * @exception MathIllegalStateException if the number of functions evaluations is exceeded
     * during step finalization
     */
    public void append(final DenseOutputModel model) throws MathIllegalArgumentException, MathIllegalStateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check dimensions equality.
     * @param d1 first dimension
     * @param d2 second dimansion
     * @exception MathIllegalArgumentException if dimensions do not match
     */
    private void checkDimensionsEquality(final int d1, final int d2) throws MathIllegalArgumentException {
        if (d1 != d2) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.DIMENSIONS_MISMATCH, d2, d1);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(final ODEStateAndDerivative initialState, final double targetTime) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleStep(final ODEStateInterpolator interpolator) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void finish(final ODEStateAndDerivative finalState) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the initial integration time.
     * @return initial integration time
     */
    public double getInitialTime() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the final integration time.
     * @return final integration time
     */
    public double getFinalTime() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the state at interpolated time.
     * @param time time of the interpolated point
     * @return state at interpolated time
     */
    public ODEStateAndDerivative getInterpolatedState(final double time) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compare a step interval and a double.
     * @param time point to locate
     * @param interval step interval
     * @return -1 if the double is before the interval, 0 if it is in
     * the interval, and +1 if it is after the interval, according to
     * the interval direction
     */
    private int locatePoint(final double time, final ODEStateInterpolator interval) {
        if (forward) {
            if (time < interval.getPreviousState().getTime()) {
                return -1;
            } else if (time > interval.getCurrentState().getTime()) {
                return +1;
            } else {
                return 0;
            }
        }
        if (time > interval.getPreviousState().getTime()) {
            return -1;
        } else if (time < interval.getCurrentState().getTime()) {
            return +1;
        } else {
            return 0;
        }
    }
}
