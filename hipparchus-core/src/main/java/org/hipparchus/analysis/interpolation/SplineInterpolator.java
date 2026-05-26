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

import java.lang.reflect.Array;
import org.hipparchus.Field;
import org.hipparchus.CalculusFieldElement;
import org.hipparchus.analysis.polynomials.FieldPolynomialFunction;
import org.hipparchus.analysis.polynomials.FieldPolynomialSplineFunction;
import org.hipparchus.analysis.polynomials.PolynomialFunction;
import org.hipparchus.analysis.polynomials.PolynomialSplineFunction;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.util.MathArrays;
import org.hipparchus.util.MathUtils;

/**
 * Computes a natural (also known as "free", "unclamped") cubic spline interpolation for the data set.
 * <p>
 * The {@link #interpolate(double[], double[])} method returns a {@link PolynomialSplineFunction}
 * consisting of n cubic polynomials, defined over the subintervals determined by the x values,
 * {@code x[0] < x[i] ... < x[n].}  The x values are referred to as "knot points."</p>
 * <p>
 * The value of the PolynomialSplineFunction at a point x that is greater than or equal to the smallest
 * knot point and strictly less than the largest knot point is computed by finding the subinterval to which
 * x belongs and computing the value of the corresponding polynomial at <code>x - x[i] </code> where
 * <code>i</code> is the index of the subinterval.  See {@link PolynomialSplineFunction} for more details.
 * </p>
 * <p>
 * The interpolating polynomials satisfy:
 * </p>
 * <ol>
 * <li>The value of the PolynomialSplineFunction at each of the input x values equals the
 *  corresponding y value.</li>
 * <li>Adjacent polynomials are equal through two derivatives at the knot points (i.e., adjacent polynomials
 *  "match up" at the knot points, as do their first and second derivatives).</li>
 * </ol>
 * <p>
 * The cubic spline interpolation algorithm implemented is as described in R.L. Burden, J.D. Faires,
 * <u>Numerical Analysis</u>, 4th Ed., 1989, PWS-Kent, ISBN 0-53491-585-X, pp 126-131.
 * </p>
 */
public class SplineInterpolator implements UnivariateInterpolator, FieldUnivariateInterpolator {

    /**
     * Empty constructor.
     * <p>
     * This constructor is not strictly necessary, but it prevents spurious
     * javadoc warnings with JDK 18 and later.
     * </p>
     * @since 3.0
     */
    public SplineInterpolator() {
        // NOPMD - unnecessary constructor added intentionally to make javadoc happy
        // nothing to do
    }

    /**
     * Computes an interpolating function for the data set.
     * @param x the arguments for the interpolation points
     * @param y the values for the interpolation points
     * @return a function which interpolates the data set
     * @throws MathIllegalArgumentException if {@code x} and {@code y}
     * have different sizes.
     * @throws MathIllegalArgumentException if {@code x} is not sorted in
     * strict increasing order.
     * @throws MathIllegalArgumentException if the size of {@code x} is smaller
     * than 3.
     */
    @Override
    public PolynomialSplineFunction interpolate(double[] x, double[] y) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Computes an interpolating function for the data set.
     * @param x the arguments for the interpolation points
     * @param y the values for the interpolation points
     * @param <T> the type of the field elements
     * @return a function which interpolates the data set
     * @throws MathIllegalArgumentException if {@code x} and {@code y}
     * have different sizes.
     * @throws MathIllegalArgumentException if {@code x} is not sorted in
     * strict increasing order.
     * @throws MathIllegalArgumentException if the size of {@code x} is smaller
     * than 3.
     * @since 1.5
     */
    @Override
    public <T extends CalculusFieldElement<T>> FieldPolynomialSplineFunction<T> interpolate(T[] x, T[] y) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
