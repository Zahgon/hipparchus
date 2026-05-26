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
package org.hipparchus.complex;

import java.util.function.DoubleFunction;
import org.hipparchus.analysis.CalculusFieldUnivariateFunction;
import org.hipparchus.analysis.integration.UnivariateIntegrator;

/**
 * Wrapper to perform univariate complex integration using an underlying real integration algorithms.
 * @since 2.0
 */
public class ComplexUnivariateIntegrator {

    /**
     * Underlying real integrator.
     */
    private UnivariateIntegrator integrator;

    /**
     * Crate a complex integrator from a real integrator.
     * @param integrator underlying real integrator to use
     */
    public ComplexUnivariateIntegrator(final UnivariateIntegrator integrator) {
        this.integrator = integrator;
    }

    /**
     * Integrate a function along a straight path between points.
     *
     * @param maxEval maximum number of evaluations (real and imaginary
     * parts are evaluated separately, so up to twice this number may be used)
     * @param f the integrand function
     * @param start start point of the integration path
     * @param end end point of the integration path
     * @return the value of integral along the straight path
     */
    public Complex integrate(final int maxEval, final CalculusFieldUnivariateFunction<Complex> f, final Complex start, final Complex end) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Integrate a function along a polyline path between any number of points.
     *
     * @param maxEval maximum number of evaluations (real and imaginary
     * parts are evaluated separately and each path segments are also evaluated
     * separately, so up to 2n times this number may be used for n segments)
     * @param f the integrand function
     * @param start start point of the integration path
     * @param path successive points defining the path vertices
     * @return the value of integral along the polyline path
     */
    public Complex integrate(final int maxEval, final CalculusFieldUnivariateFunction<Complex> f, final Complex start, final Complex... path) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
