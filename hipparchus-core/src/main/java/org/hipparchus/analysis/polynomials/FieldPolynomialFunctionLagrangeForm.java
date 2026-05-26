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
package org.hipparchus.analysis.polynomials;

import org.hipparchus.CalculusFieldElement;
import org.hipparchus.Field;
import org.hipparchus.analysis.CalculusFieldUnivariateFunction;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.MathArrays;

/**
 * Implements the representation of a real polynomial function in
 * <a href="http://mathworld.wolfram.com/LagrangeInterpolatingPolynomial.html">
 * Lagrange Form</a>. For reference, see <b>Introduction to Numerical
 * Analysis</b>, ISBN 038795452X, chapter 2.
 * <p>
 * The approximated function should be smooth enough for Lagrange polynomial
 * to work well. Otherwise, consider using splines instead.</p>
 * @see PolynomialFunctionLagrangeForm
 * @since 4.0
 * @param <T> type of the field elements
 */
public class FieldPolynomialFunctionLagrangeForm<T extends CalculusFieldElement<T>> implements CalculusFieldUnivariateFunction<T> {

    /**
     * The coefficients of the polynomial, ordered by degree -- i.e.
     * coefficients[0] is the constant term and coefficients[n] is the
     * coefficient of x^n where n is the degree of the polynomial.
     */
    private T[] coefficients;

    /**
     * Interpolating points (abscissas).
     */
    private final T[] x;

    /**
     * Function values at interpolating points.
     */
    private final T[] y;

    /**
     * Whether the polynomial coefficients are available.
     */
    private boolean coefficientsComputed;

    /**
     * Construct a Lagrange polynomial with the given abscissas and function
     * values. The order of interpolating points is important.
     * <p>
     * The constructor makes copy of the input arrays and assigns them.</p>
     *
     * @param x interpolating points
     * @param y function values at interpolating points
     * @throws MathIllegalArgumentException if the array lengths are different.
     * @throws MathIllegalArgumentException if the number of points is less than 2.
     * @throws MathIllegalArgumentException if two abscissae have the same value.
     * @throws MathIllegalArgumentException if the abscissae are not sorted.
     */
    public FieldPolynomialFunctionLagrangeForm(final T[] x, final T[] y) throws MathIllegalArgumentException {
        this.x = x.clone();
        this.y = y.clone();
        coefficientsComputed = false;
        MathArrays.checkEqualLength(x, y);
        if (x.length < 2) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.WRONG_NUMBER_OF_POINTS, 2, x.length, true);
        }
        MathArrays.checkOrder(x, MathArrays.OrderDirection.INCREASING, true, true);
    }

    /**
     * Calculate the function value at the given point.
     *
     * @param z Point at which the function value is to be computed.
     * @return the function value.
     * @throws MathIllegalArgumentException if {@code x} and {@code y} have
     * different lengths.
     * @throws MathIllegalArgumentException
     * if {@code x} is not sorted in strictly increasing order.
     * @throws MathIllegalArgumentException if the size of {@code x} is less
     * than 2.
     */
    @Override
    public T value(final T z) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the degree of the polynomial.
     *
     * @return the degree of the polynomial
     */
    public int degree() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns a copy of the interpolating points array.
     * <p>
     * Changes made to the returned copy will not affect the polynomial.</p>
     *
     * @return a fresh copy of the interpolating points array
     */
    public T[] getInterpolatingPoints() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns a copy of the interpolating values array.
     * <p>
     * Changes made to the returned copy will not affect the polynomial.</p>
     *
     * @return a fresh copy of the interpolating values array
     */
    public T[] getInterpolatingValues() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns a copy of the coefficients array.
     * <p>
     * Changes made to the returned copy will not affect the polynomial.</p>
     * <p>
     * Note that coefficients computation can be ill-conditioned. Use with caution
     * and only when it is necessary.</p>
     *
     * @return a fresh copy of the coefficients array
     */
    public T[] getCoefficients() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Calculate the coefficients of Lagrange polynomial from the
     * interpolation data. It takes O(n^2) time.
     * Note that this computation can be ill-conditioned: Use with caution
     * and only when it is necessary.
     */
    protected void computeCoefficients() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
