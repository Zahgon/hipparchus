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
package org.hipparchus.analysis.interpolation;

import java.io.Serializable;
import org.hipparchus.analysis.polynomials.PolynomialFunctionLagrangeForm;
import org.hipparchus.analysis.polynomials.PolynomialFunctionNewtonForm;
import org.hipparchus.exception.MathIllegalArgumentException;

/**
 * Implements the <a href=
 * "http://mathworld.wolfram.com/NewtonsDividedDifferenceInterpolationFormula.html">
 * Divided Difference Algorithm</a> for interpolation of real univariate
 * functions. For reference, see <b>Introduction to Numerical Analysis</b>,
 * ISBN 038795452X, chapter 2.
 * <p>
 * The actual code of Neville's evaluation is in PolynomialFunctionLagrangeForm,
 * this class provides an easy-to-use interface to it.</p>
 */
public class DividedDifferenceInterpolator implements UnivariateInterpolator, Serializable {

    /**
     * serializable version identifier
     */
    private static final long serialVersionUID = 107049519551235069L;

    /**
     * Empty constructor.
     * <p>
     * This constructor is not strictly necessary, but it prevents spurious
     * javadoc warnings with JDK 18 and later.
     * </p>
     * @since 3.0
     */
    public DividedDifferenceInterpolator() {
        // NOPMD - unnecessary constructor added intentionally to make javadoc happy
        // nothing to do
    }

    /**
     * Compute an interpolating function for the dataset.
     *
     * @param x Interpolating points array.
     * @param y Interpolating values array.
     * @return a function which interpolates the dataset.
     * @throws MathIllegalArgumentException if the array lengths are different.
     * @throws MathIllegalArgumentException if the number of points is less than 2.
     * @throws MathIllegalArgumentException if {@code x} is not sorted in
     * strictly increasing order.
     */
    @Override
    public PolynomialFunctionNewtonForm interpolate(double[] x, double[] y) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Return a copy of the divided difference array.
     * <p>
     * The divided difference array is defined recursively by <pre>
     * f[x0] = f(x0)
     * f[x0,x1,...,xk] = (f[x1,...,xk] - f[x0,...,x[k-1]]) / (xk - x0)
     * </pre>
     * <p>
     * The computational complexity is \(O(n^2)\) where \(n\) is the common
     * length of {@code x} and {@code y}.</p>
     *
     * @param x Interpolating points array.
     * @param y Interpolating values array.
     * @return a fresh copy of the divided difference array.
     * @throws MathIllegalArgumentException if the array lengths are different.
     * @throws MathIllegalArgumentException if the number of points is less than 2.
     * @throws MathIllegalArgumentException
     * if {@code x} is not sorted in strictly increasing order.
     */
    protected static double[] computeDividedDifference(final double[] x, final double[] y) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
