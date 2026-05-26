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

import java.util.Arrays;
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
 * with {@link FieldDerivativeStructure#getOrder() derivation order} limited to one.
 * It should have less overhead than {@link FieldDerivativeStructure} in its domain.</p>
 * <p>This class is an implementation of Rall's numbers. Rall's numbers are an
 * extension to the real numbers used throughout mathematical expressions; they hold
 * the derivative together with the value of a function.</p>
 * <p>{@link FieldGradient} instances can be used directly thanks to
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
 * @see FieldUnivariateDerivative2
 * @since 1.7
 */
public class FieldGradient<T extends CalculusFieldElement<T>> implements FieldDerivative1<T, FieldGradient<T>> {

    /**
     * Value of the function.
     */
    private final T value;

    /**
     * Gradient of the function.
     */
    private final T[] grad;

    /**
     * Build an instance with values and unitialized derivatives array.
     * @param value value of the function
     * @param freeParameters number of free parameters
     */
    private FieldGradient(final T value, int freeParameters) {
        this.value = value;
        this.grad = MathArrays.buildArray(value.getField(), freeParameters);
    }

    /**
     * Build an instance with values and derivative.
     * @param value value of the function
     * @param gradient gradient of the function
     */
    @SafeVarargs
    public FieldGradient(final T value, final T... gradient) {
        this(value, gradient.length);
        System.arraycopy(gradient, 0, grad, 0, grad.length);
    }

    /**
     * Build an instance from a {@link FieldDerivativeStructure}.
     * @param ds derivative structure
     * @exception MathIllegalArgumentException if {@code ds} order
     * is not 1
     */
    public FieldGradient(final FieldDerivativeStructure<T> ds) throws MathIllegalArgumentException {
        this(ds.getValue(), ds.getFreeParameters());
        MathUtils.checkDimension(ds.getOrder(), 1);
        System.arraycopy(ds.getAllDerivatives(), 1, grad, 0, grad.length);
    }

    /**
     * Build an instance corresponding to a constant value.
     * @param freeParameters number of free parameters (i.e. dimension of the gradient)
     * @param value constant value of the function
     * @param <T> the type of the function parameters and value
     * @return a {@code FieldGradient} with a constant value and all derivatives set to 0.0
     */
    public static <T extends CalculusFieldElement<T>> FieldGradient<T> constant(final int freeParameters, final T value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Build a {@code Gradient} representing a variable.
     * <p>Instances built using this method are considered
     * to be the free variables with respect to which differentials
     * are computed. As such, their differential with respect to
     * themselves is +1.</p>
     * @param freeParameters number of free parameters (i.e. dimension of the gradient)
     * @param index index of the variable (from 0 to {@link #getFreeParameters() getFreeParameters()} - 1)
     * @param value value of the variable
     * @param <T> the type of the function parameters and value
     * @return a {@code FieldGradient} with a constant value and all derivatives set to 0.0 except the
     * one at {@code index} which will be set to 1.0
     */
    public static <T extends CalculusFieldElement<T>> FieldGradient<T> variable(final int freeParameters, final int index, final T value) {
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
     * {@inheritDoc}
     */
    @Override
    public FieldGradient<T> newInstance(final double c) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldGradient<T> newInstance(final T c) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldGradient<T> withValue(final T v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldGradient<T> getAddendum() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the value part of the function.
     * @return value part of the value of the function
     */
    @Override
    public T getValue() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the gradient part of the function.
     * @return gradient part of the value of the function
     */
    public T[] getGradient() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the number of free parameters.
     * @return number of free parameters
     */
    @Override
    public int getFreeParameters() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getPartialDerivative(final int... orders) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the partial derivative with respect to one parameter.
     * @param n index of the parameter (counting from 0)
     * @return partial derivative with respect to the n<sup>th</sup> parameter
     * @exception MathIllegalArgumentException if n is either negative or larger
     * or equal to {@link #getFreeParameters()}
     */
    public T getPartialDerivative(final int n) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convert the instance to a {@link FieldDerivativeStructure}.
     * @return derivative structure with same value and derivative as the instance
     */
    public FieldDerivativeStructure<T> toDerivativeStructure() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldGradient<T> add(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldGradient<T> add(final FieldGradient<T> a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldGradient<T> subtract(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldGradient<T> subtract(final FieldGradient<T> a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * '&times;' operator.
     * @param n right hand side parameter of the operator
     * @return this&times;n
     */
    public FieldGradient<T> multiply(final T n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldGradient<T> multiply(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldGradient<T> multiply(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldGradient<T> multiply(final FieldGradient<T> a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * '&divide;' operator.
     * @param a right hand side parameter of the operator
     * @return this&divide;a
     */
    public FieldGradient<T> divide(final T a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldGradient<T> divide(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldGradient<T> divide(final FieldGradient<T> a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * IEEE remainder operator.
     * @param a right hand side parameter of the operator
     * @return this - n &times; a where n is the closest integer to this/a
     * (the even integer is chosen for n if this/a is halfway between two integers)
     */
    public FieldGradient<T> remainder(final T a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldGradient<T> remainder(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldGradient<T> remainder(final FieldGradient<T> a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldGradient<T> negate() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldGradient<T> abs() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the instance with the sign of the argument.
     * A NaN {@code sign} argument is treated as positive.
     *
     * @param sign the sign for the returned value
     * @return the instance with the same sign as the {@code sign} argument
     */
    public FieldGradient<T> copySign(final T sign) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldGradient<T> copySign(final FieldGradient<T> sign) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldGradient<T> copySign(final double sign) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldGradient<T> scalb(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldGradient<T> hypot(final FieldGradient<T> y) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute composition of the instance by a function.
     * @param g0 value of the function at the current point (i.e. at {@code g(getValue())})
     * @param g1 first derivative of the function at the current point (i.e. at {@code g'(getValue())})
     * @return g(this)
     */
    @Override
    public FieldGradient<T> compose(final T g0, final T g1) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldGradient<T> rootN(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldGradientField<T> getField() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute a<sup>x</sup> where a is a double and x a {@link FieldGradient}
     * @param a number to exponentiate
     * @param x power to apply
     * @param <T> the type of the function parameters and value
     * @return a<sup>x</sup>
     */
    public static <T extends CalculusFieldElement<T>> FieldGradient<T> pow(final double a, final FieldGradient<T> x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldGradient<T> pow(final double p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldGradient<T> pow(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldSinCos<FieldGradient<T>> sinCos() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldGradient<T> atan2(final FieldGradient<T> x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldSinhCosh<FieldGradient<T>> sinhCosh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldGradient<T> toDegrees() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldGradient<T> toRadians() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Evaluate Taylor expansion of a gradient.
     * @param delta parameters offsets (&Delta;x, &Delta;y, ...)
     * @return value of the Taylor expansion at x + &Delta;x, y + &Delta;y, ...
     */
    public T taylor(final double... delta) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Evaluate Taylor expansion of a gradient.
     * @param delta parameters offsets (&Delta;x, &Delta;y, ...)
     * @return value of the Taylor expansion at x + &Delta;x, y + &Delta;y, ...
     */
    @SuppressWarnings("unchecked")
    public T taylor(final T... delta) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldGradient<T> linearCombination(final FieldGradient<T>[] a, final FieldGradient<T>[] b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute a linear combination.
     * @param a Factors.
     * @param b Factors.
     * @return <code>&Sigma;<sub>i</sub> a<sub>i</sub> b<sub>i</sub></code>.
     * @throws MathIllegalArgumentException if arrays dimensions don't match
     */
    public FieldGradient<T> linearCombination(final T[] a, final FieldGradient<T>[] b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldGradient<T> linearCombination(final double[] a, final FieldGradient<T>[] b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldGradient<T> linearCombination(final FieldGradient<T> a1, final FieldGradient<T> b1, final FieldGradient<T> a2, final FieldGradient<T> b2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldGradient<T> linearCombination(final double a1, final FieldGradient<T> b1, final double a2, final FieldGradient<T> b2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldGradient<T> linearCombination(final FieldGradient<T> a1, final FieldGradient<T> b1, final FieldGradient<T> a2, final FieldGradient<T> b2, final FieldGradient<T> a3, final FieldGradient<T> b3) {
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
     * @see #linearCombination(double, FieldGradient, double, FieldGradient)
     * @see #linearCombination(double, FieldGradient, double, FieldGradient, double, FieldGradient, double, FieldGradient)
     * @exception MathIllegalArgumentException if number of free parameters or orders are inconsistent
     */
    public FieldGradient<T> linearCombination(final T a1, final FieldGradient<T> b1, final T a2, final FieldGradient<T> b2, final T a3, final FieldGradient<T> b3) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldGradient<T> linearCombination(final double a1, final FieldGradient<T> b1, final double a2, final FieldGradient<T> b2, final double a3, final FieldGradient<T> b3) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldGradient<T> linearCombination(final FieldGradient<T> a1, final FieldGradient<T> b1, final FieldGradient<T> a2, final FieldGradient<T> b2, final FieldGradient<T> a3, final FieldGradient<T> b3, final FieldGradient<T> a4, final FieldGradient<T> b4) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldGradient<T> linearCombination(final double a1, final FieldGradient<T> b1, final double a2, final FieldGradient<T> b2, final double a3, final FieldGradient<T> b3, final double a4, final FieldGradient<T> b4) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double norm() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Add an independent variable to the Taylor expansion.
     * @return object with one more variable
     * @since 4.0
     */
    public FieldGradient<T> stackVariable() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldGradient<T> getPi() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSmall(final FieldGradient<T> base, final double relativeThreshold) {
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
