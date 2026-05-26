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
package org.hipparchus.analysis.polynomials;

import java.io.Serializable;
import java.util.Arrays;
import org.hipparchus.CalculusFieldElement;
import org.hipparchus.analysis.FieldUnivariateFunction;
import org.hipparchus.analysis.ParametricUnivariateFunction;
import org.hipparchus.analysis.differentiation.Derivative;
import org.hipparchus.analysis.differentiation.UnivariateDifferentiableFunction;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.NullArgumentException;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.MathUtils;

/**
 * Immutable representation of a real polynomial function with real coefficients.
 * <p>
 * <a href="http://mathworld.wolfram.com/HornersMethod.html">Horner's Method</a>
 * is used to evaluate the function.</p>
 */
public class PolynomialFunction implements UnivariateDifferentiableFunction, FieldUnivariateFunction, Serializable {

    /**
     * Serialization identifier
     */
    private static final long serialVersionUID = -7726511984200295583L;

    /**
     * The coefficients of the polynomial, ordered by degree -- i.e.,
     * coefficients[0] is the constant term and coefficients[n] is the
     * coefficient of x^n where n is the degree of the polynomial.
     */
    private final double[] coefficients;

    /**
     * Construct a polynomial with the given coefficients.  The first element
     * of the coefficients array is the constant term.  Higher degree
     * coefficients follow in sequence.  The degree of the resulting polynomial
     * is the index of the last non-null element of the array, or 0 if all elements
     * are null.
     * <p>
     * The constructor makes a copy of the input array and assigns the copy to
     * the coefficients property.</p>
     *
     * @param c Polynomial coefficients.
     * @throws NullArgumentException if {@code c} is {@code null}.
     * @throws MathIllegalArgumentException if {@code c} is empty.
     */
    public PolynomialFunction(double... c) throws MathIllegalArgumentException, NullArgumentException {
        MathUtils.checkNotNull(c);
        int n = c.length;
        if (n == 0) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.EMPTY_POLYNOMIALS_COEFFICIENTS_ARRAY);
        }
        while ((n > 1) && (c[n - 1] == 0)) {
            --n;
        }
        this.coefficients = new double[n];
        System.arraycopy(c, 0, this.coefficients, 0, n);
    }

    /**
     * Compute the value of the function for the given argument.
     * <p>
     *  The value returned is </p><p>
     *  {@code coefficients[n] * x^n + ... + coefficients[1] * x  + coefficients[0]}
     * </p>
     *
     * @param x Argument for which the function value should be computed.
     * @return the value of the polynomial at the given point.
     *
     * @see org.hipparchus.analysis.UnivariateFunction#value(double)
     */
    @Override
    public double value(double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the degree of the polynomial.
     *
     * @return the degree of the polynomial.
     */
    public int degree() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns a copy of the coefficients array.
     * <p>
     * Changes made to the returned copy will not affect the coefficients of
     * the polynomial.</p>
     *
     * @return a fresh copy of the coefficients array.
     */
    public double[] getCoefficients() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Uses Horner's Method to evaluate the polynomial with the given coefficients at
     * the argument.
     *
     * @param coefficients Coefficients of the polynomial to evaluate.
     * @param argument Input value.
     * @return the value of the polynomial.
     * @throws MathIllegalArgumentException if {@code coefficients} is empty.
     * @throws NullArgumentException if {@code coefficients} is {@code null}.
     */
    protected static double evaluate(double[] coefficients, double argument) throws MathIllegalArgumentException, NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * @throws MathIllegalArgumentException if {@code coefficients} is empty.
     * @throws NullArgumentException if {@code coefficients} is {@code null}.
     */
    @Override
    public <T extends Derivative<T>> T value(final T t) throws MathIllegalArgumentException, NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * @throws MathIllegalArgumentException if {@code coefficients} is empty.
     * @throws NullArgumentException if {@code coefficients} is {@code null}.
     * @since 1.3
     */
    @Override
    public <T extends CalculusFieldElement<T>> T value(final T t) throws MathIllegalArgumentException, NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Add a polynomial to the instance.
     *
     * @param p Polynomial to add.
     * @return a new polynomial which is the sum of the instance and {@code p}.
     */
    public PolynomialFunction add(final PolynomialFunction p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Subtract a polynomial from the instance.
     *
     * @param p Polynomial to subtract.
     * @return a new polynomial which is the instance minus {@code p}.
     */
    public PolynomialFunction subtract(final PolynomialFunction p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Negate the instance.
     *
     * @return a new polynomial with all coefficients negated
     */
    public PolynomialFunction negate() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Multiply the instance by a polynomial.
     *
     * @param p Polynomial to multiply by.
     * @return a new polynomial equal to this times {@code p}
     */
    public PolynomialFunction multiply(final PolynomialFunction p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the coefficients of the derivative of the polynomial with the given coefficients.
     *
     * @param coefficients Coefficients of the polynomial to differentiate.
     * @return the coefficients of the derivative or {@code null} if coefficients has length 1.
     * @throws MathIllegalArgumentException if {@code coefficients} is empty.
     * @throws NullArgumentException if {@code coefficients} is {@code null}.
     */
    protected static double[] differentiate(double[] coefficients) throws MathIllegalArgumentException, NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns an anti-derivative of this polynomial, with 0 constant term.
     *
     * @return a polynomial whose derivative has the same coefficients as this polynomial
     */
    public PolynomialFunction antiDerivative() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the definite integral of this polymomial over the given interval.
     * <p>
     * [lower, upper] must describe a finite interval (neither can be infinite
     * and lower must be less than or equal to upper).
     *
     * @param lower lower bound for the integration
     * @param upper upper bound for the integration
     * @return the integral of this polymomial over the given interval
     * @throws MathIllegalArgumentException if the bounds do not describe a finite interval
     */
    public double integrate(final double lower, final double upper) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the derivative as a {@link PolynomialFunction}.
     *
     * @return the derivative polynomial.
     */
    public PolynomialFunction polynomialDerivative() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns a string representation of the polynomial.
     *
     * <p>The representation is user oriented. Terms are displayed lowest
     * degrees first. The multiplications signs, coefficients equals to
     * one and null terms are not displayed (except if the polynomial is 0,
     * in which case the 0 constant term is displayed). Addition of terms
     * with negative coefficients are replaced by subtraction of terms
     * with positive coefficients except for the first displayed term
     * (i.e. we display <code>-3</code> for a constant negative polynomial,
     * but <code>1 - 3 x + x^2</code> if the negative coefficient is not
     * the first one displayed).</p>
     *
     * @return a string representation of the polynomial.
     */
    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Creates a string representing a coefficient, removing ".0" endings.
     *
     * @param coeff Coefficient.
     * @return a string representation of {@code coeff}.
     */
    private static String toString(double coeff) {
        final String c = Double.toString(coeff);
        if (c.endsWith(".0")) {
            return c.substring(0, c.length() - 2);
        } else {
            return c;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Dedicated parametric polynomial class.
     */
    public static class Parametric implements ParametricUnivariateFunction {

        /**
         * Empty constructor.
         * <p>
         * This constructor is not strictly necessary, but it prevents spurious
         * javadoc warnings with JDK 18 and later.
         * </p>
         * @since 3.0
         */
        public Parametric() {
            // NOPMD - unnecessary constructor added intentionally to make javadoc happy
            // nothing to do
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public double[] gradient(double x, double... parameters) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public double value(final double x, final double... parameters) throws MathIllegalArgumentException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
