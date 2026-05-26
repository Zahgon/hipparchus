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
package org.hipparchus.analysis.differentiation;

import org.hipparchus.CalculusFieldElement;
import org.hipparchus.Field;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.FieldSinCos;
import org.hipparchus.util.FieldSinhCosh;
import org.hipparchus.util.MathArrays;
import org.hipparchus.util.MathUtils;

/**
 * Class representing both the value and the differentials of a function.
 * <p>This class is a stripped-down version of {@link FieldDerivativeStructure}
 * with only one {@link FieldDerivativeStructure#getFreeParameters() free parameter}
 * and {@link FieldDerivativeStructure#getOrder() derivation order} limited to two.
 * It should have less overhead than {@link FieldDerivativeStructure} in its domain.</p>
 * <p>This class is an implementation of Rall's numbers. Rall's numbers are an
 * extension to the real numbers used throughout mathematical expressions; they hold
 * the derivative together with the value of a function.</p>
 * <p>{@link FieldUnivariateDerivative2} instances can be used directly thanks to
 * the arithmetic operators to the mathematical functions provided as
 * methods by this class (+, -, *, /, %, sin, cos ...).</p>
 * <p>Implementing complex expressions by hand using {@link Derivative}-based
 * classes (or in fact any {@link org.hipparchus.CalculusFieldElement} class) is
 * a tedious and error-prone task but has the advantage of not requiring users
 * to compute the derivatives by themselves and allowing to switch for one
 * derivative implementation to another as they all share the same filed API.</p>
 * <p>Instances of this class are guaranteed to be immutable.</p>
 * @param <T> the type of the function parameters and value
 * @see DerivativeStructure
 * @see UnivariateDerivative1
 * @see UnivariateDerivative2
 * @see Gradient
 * @see FieldDerivativeStructure
 * @see FieldUnivariateDerivative1
 * @see FieldGradient
 * @since 1.7
 */
public class FieldUnivariateDerivative2<T extends CalculusFieldElement<T>> extends FieldUnivariateDerivative<T, FieldUnivariateDerivative2<T>> {

    /**
     * Value of the function.
     */
    private final T f0;

    /**
     * First derivative of the function.
     */
    private final T f1;

    /**
     * Second derivative of the function.
     */
    private final T f2;

    /**
     * Build an instance with values and derivative.
     * @param f0 value of the function
     * @param f1 first derivative of the function
     * @param f2 second derivative of the function
     */
    public FieldUnivariateDerivative2(final T f0, final T f1, final T f2) {
        this.f0 = f0;
        this.f1 = f1;
        this.f2 = f2;
    }

    /**
     * Build an instance from a {@link FieldDerivativeStructure}.
     * @param ds derivative structure
     * @exception MathIllegalArgumentException if either {@code ds} parameters
     * is not 1 or {@code ds} order is not 2
     */
    public FieldUnivariateDerivative2(final FieldDerivativeStructure<T> ds) throws MathIllegalArgumentException {
        MathUtils.checkDimension(ds.getFreeParameters(), 1);
        MathUtils.checkDimension(ds.getOrder(), 2);
        this.f0 = ds.getValue();
        this.f1 = ds.getPartialDerivative(1);
        this.f2 = ds.getPartialDerivative(2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> newInstance(final double value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> newInstance(final T value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> withValue(final T value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> getAddendum() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the value part of the univariate derivative.
     * @return value part of the univariate derivative
     */
    @Override
    public T getValue() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get a derivative from the univariate derivative.
     * @param n derivation order (must be between 0 and {@link #getOrder()}, both inclusive)
     * @return n<sup>th</sup> derivative, or {@code NaN} if n is
     * either negative or strictly larger than {@link #getOrder()}
     */
    @Override
    public T getDerivative(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the derivation order.
     * @return derivation order
     */
    @Override
    public int getOrder() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the first derivative.
     * @return first derivative
     * @see #getValue()
     */
    public T getFirstDerivative() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the second derivative.
     * @return second derivative
     * @see #getValue()
     * @see #getFirstDerivative()
     */
    public T getSecondDerivative() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the {@link Field} the value and parameters of the function belongs to.
     * @return {@link Field} the value and parameters of the function belongs to
     */
    public Field<T> getValueField() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convert the instance to a {@link FieldDerivativeStructure}.
     * @return derivative structure with same value and derivative as the instance
     */
    @Override
    public FieldDerivativeStructure<T> toDerivativeStructure() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> add(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> add(final FieldUnivariateDerivative2<T> a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> subtract(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> subtract(final FieldUnivariateDerivative2<T> a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * '&times;' operator.
     * @param a right hand side parameter of the operator
     * @return this&times;a
     */
    public FieldUnivariateDerivative2<T> multiply(final T a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> multiply(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> multiply(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> multiply(final FieldUnivariateDerivative2<T> a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> square() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * '&divide;' operator.
     * @param a right hand side parameter of the operator
     * @return this&divide;a
     */
    public FieldUnivariateDerivative2<T> divide(final T a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> divide(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> divide(final FieldUnivariateDerivative2<T> a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * IEEE remainder operator.
     * @param a right hand side parameter of the operator
     * @return this - n &times; a where n is the closest integer to this/a
     * (the even integer is chosen for n if this/a is halfway between two integers)
     */
    public FieldUnivariateDerivative2<T> remainder(final T a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> remainder(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> remainder(final FieldUnivariateDerivative2<T> a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> negate() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> abs() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the instance with the sign of the argument.
     * A NaN {@code sign} argument is treated as positive.
     *
     * @param sign the sign for the returned value
     * @return the instance with the same sign as the {@code sign} argument
     */
    public FieldUnivariateDerivative2<T> copySign(final T sign) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> copySign(final FieldUnivariateDerivative2<T> sign) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> copySign(final double sign) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> scalb(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> hypot(final FieldUnivariateDerivative2<T> y) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> reciprocal() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute composition of the instance by a function.
     * @param g0 value of the function at the current point (i.e. at {@code g(getValue())})
     * @param g1 first derivative of the function at the current point (i.e. at {@code g'(getValue())})
     * @param g2 second derivative of the function at the current point (i.e. at {@code g''(getValue())})
     * @return g(this)
     */
    public FieldUnivariateDerivative2<T> compose(final T g0, final T g1, final T g2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> sqrt() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> cbrt() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> rootN(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2Field<T> getField() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute a<sup>x</sup> where a is a double and x a {@link FieldUnivariateDerivative2}
     * @param a number to exponentiate
     * @param x power to apply
     * @param <T> the type of the function parameters and value
     * @return a<sup>x</sup>
     */
    public static <T extends CalculusFieldElement<T>> FieldUnivariateDerivative2<T> pow(final double a, final FieldUnivariateDerivative2<T> x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> pow(final double p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> pow(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> exp() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> expm1() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> log() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> log1p() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> log10() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> cos() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> sin() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldSinCos<FieldUnivariateDerivative2<T>> sinCos() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> tan() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> acos() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> asin() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> atan() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> atan2(final FieldUnivariateDerivative2<T> x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> cosh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> sinh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldSinhCosh<FieldUnivariateDerivative2<T>> sinhCosh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> tanh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> acosh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> asinh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> atanh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> toDegrees() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> toRadians() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Evaluate Taylor expansion a univariate derivative.
     * @param delta parameter offset Δx
     * @return value of the Taylor expansion at x + Δx
     */
    public T taylor(final double delta) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Evaluate Taylor expansion a univariate derivative.
     * @param delta parameter offset Δx
     * @return value of the Taylor expansion at x + Δx
     */
    public T taylor(final T delta) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute a linear combination.
     * @param a Factors.
     * @param b Factors.
     * @return <code>&Sigma;<sub>i</sub> a<sub>i</sub> b<sub>i</sub></code>.
     * @throws MathIllegalArgumentException if arrays dimensions don't match
     */
    public FieldUnivariateDerivative2<T> linearCombination(final T[] a, final FieldUnivariateDerivative2<T>[] b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> linearCombination(final FieldUnivariateDerivative2<T>[] a, final FieldUnivariateDerivative2<T>[] b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> linearCombination(final double[] a, final FieldUnivariateDerivative2<T>[] b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> linearCombination(final FieldUnivariateDerivative2<T> a1, final FieldUnivariateDerivative2<T> b1, final FieldUnivariateDerivative2<T> a2, final FieldUnivariateDerivative2<T> b2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> linearCombination(final double a1, final FieldUnivariateDerivative2<T> b1, final double a2, final FieldUnivariateDerivative2<T> b2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> linearCombination(final FieldUnivariateDerivative2<T> a1, final FieldUnivariateDerivative2<T> b1, final FieldUnivariateDerivative2<T> a2, final FieldUnivariateDerivative2<T> b2, final FieldUnivariateDerivative2<T> a3, final FieldUnivariateDerivative2<T> b3) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute a linear combination.
     * @param a1 first factor of the first term
     * @param b1 second factor of the first term
     * @param a2 first factor of the second term
     * @param b2 second factor of the second term
     * @param a3 first factor of the third term
     * @param b3 second factor of the third term
     * @return a<sub>1</sub>&times;b<sub>1</sub> +
     * a<sub>2</sub>&times;b<sub>2</sub> + a<sub>3</sub>&times;b<sub>3</sub>
     * @see #linearCombination(double, FieldUnivariateDerivative2, double, FieldUnivariateDerivative2)
     * @see #linearCombination(double, FieldUnivariateDerivative2, double, FieldUnivariateDerivative2, double, FieldUnivariateDerivative2, double, FieldUnivariateDerivative2)
     * @exception MathIllegalArgumentException if number of free parameters or orders are inconsistent
     */
    public FieldUnivariateDerivative2<T> linearCombination(final T a1, final FieldUnivariateDerivative2<T> b1, final T a2, final FieldUnivariateDerivative2<T> b2, final T a3, final FieldUnivariateDerivative2<T> b3) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> linearCombination(final double a1, final FieldUnivariateDerivative2<T> b1, final double a2, final FieldUnivariateDerivative2<T> b2, final double a3, final FieldUnivariateDerivative2<T> b3) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> linearCombination(final FieldUnivariateDerivative2<T> a1, final FieldUnivariateDerivative2<T> b1, final FieldUnivariateDerivative2<T> a2, final FieldUnivariateDerivative2<T> b2, final FieldUnivariateDerivative2<T> a3, final FieldUnivariateDerivative2<T> b3, final FieldUnivariateDerivative2<T> a4, final FieldUnivariateDerivative2<T> b4) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> linearCombination(final double a1, final FieldUnivariateDerivative2<T> b1, final double a2, final FieldUnivariateDerivative2<T> b2, final double a3, final FieldUnivariateDerivative2<T> b3, final double a4, final FieldUnivariateDerivative2<T> b4) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldUnivariateDerivative2<T> getPi() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSmall(final FieldUnivariateDerivative2<T> base, final double relativeThreshold) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Test for the equality of two univariate derivatives.
     * <p>
     * univariate derivatives are considered equal if they have the same derivatives.
     * </p>
     * @param other Object to test for equality to this
     * @return true if two univariate derivatives are equal
     */
    @Override
    public boolean equals(Object other) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get a hashCode for the univariate derivative.
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
