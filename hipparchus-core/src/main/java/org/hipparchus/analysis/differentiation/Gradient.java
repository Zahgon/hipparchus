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

import java.io.Serializable;
import java.util.Arrays;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.FieldSinCos;
import org.hipparchus.util.FieldSinhCosh;
import org.hipparchus.util.MathArrays;
import org.hipparchus.util.MathUtils;
import org.hipparchus.util.SinCos;
import org.hipparchus.util.SinhCosh;

/**
 * Class representing both the value and the differentials of a function.
 * <p>This class is a stripped-down version of {@link DerivativeStructure}
 * with {@link DerivativeStructure#getOrder() derivation order} limited to one.
 * It should have less overhead than {@link DerivativeStructure} in its domain.</p>
 * <p>This class is an implementation of Rall's numbers. Rall's numbers are an
 * extension to the real numbers used throughout mathematical expressions; they hold
 * the derivative together with the value of a function.</p>
 * <p>{@link Gradient} instances can be used directly thanks to
 * the arithmetic operators to the mathematical functions provided as
 * methods by this class (+, -, *, /, %, sin, cos ...).</p>
 * <p>Implementing complex expressions by hand using {@link Derivative}-based
 * classes (or in fact any {@link org.hipparchus.CalculusFieldElement} class) is
 * a tedious and error-prone task but has the advantage of not requiring users
 * to compute the derivatives by themselves and allowing to switch for one
 * derivative implementation to another as they all share the same filed API.</p>
 * <p>Instances of this class are guaranteed to be immutable.</p>
 * @see DerivativeStructure
 * @see UnivariateDerivative1
 * @see UnivariateDerivative2
 * @see FieldDerivativeStructure
 * @see FieldUnivariateDerivative1
 * @see FieldUnivariateDerivative2
 * @see FieldGradient
 * @since 1.7
 */
public class Gradient implements Derivative1<Gradient>, Serializable {

    /**
     * Serializable UID.
     */
    private static final long serialVersionUID = 20200520L;

    /**
     * Value of the function.
     */
    private final double value;

    /**
     * Gradient of the function.
     */
    private final double[] grad;

    /**
     * Build an instance with values and uninitialized derivatives array.
     * @param value value of the function
     * @param freeParameters number of free parameters
     */
    private Gradient(final double value, int freeParameters) {
        this.value = value;
        this.grad = new double[freeParameters];
    }

    /**
     * Build an instance with value and derivatives array, used for performance internally (no copy).
     * @param value value of the function
     * @param gradient derivatives
     * @since 3.1
     */
    private Gradient(final double[] gradient, final double value) {
        this.value = value;
        this.grad = gradient;
    }

    /**
     * Build an instance with values and derivative.
     * @param value value of the function
     * @param gradient gradient of the function
     */
    public Gradient(final double value, final double... gradient) {
        this(value, gradient.length);
        System.arraycopy(gradient, 0, grad, 0, grad.length);
    }

    /**
     * Build an instance from a {@link DerivativeStructure}.
     * @param ds derivative structure
     * @exception MathIllegalArgumentException if {@code ds} order
     * is not 1
     */
    public Gradient(final DerivativeStructure ds) throws MathIllegalArgumentException {
        this(ds.getValue(), ds.getFreeParameters());
        MathUtils.checkDimension(ds.getOrder(), 1);
        System.arraycopy(ds.getAllDerivatives(), 1, grad, 0, grad.length);
    }

    /**
     * Build an instance corresponding to a constant value.
     * @param freeParameters number of free parameters (i.e. dimension of the gradient)
     * @param value constant value of the function
     * @return a {@code Gradient} with a constant value and all derivatives set to 0.0
     */
    public static Gradient constant(final int freeParameters, final double value) {
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
     * @return a {@code Gradient} with a constant value and all derivatives set to 0.0 except the
     * one at {@code index} which will be set to 1.0
     */
    public static Gradient variable(final int freeParameters, final int index, final double value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient newInstance(final double c) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient withValue(final double v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient getAddendum() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the value part of the function.
     * @return value part of the value of the function
     */
    @Override
    public double getValue() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the gradient part of the function.
     * @return gradient part of the value of the function
     * @see #getPartialDerivative(int)
     */
    public double[] getGradient() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getFreeParameters() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getPartialDerivative(final int... orders) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the partial derivative with respect to one parameter.
     * @param n index of the parameter (counting from 0)
     * @return partial derivative with respect to the n<sup>th</sup> parameter
     * @exception MathIllegalArgumentException if n is either negative or larger
     * or equal to {@link #getFreeParameters()}
     */
    public double getPartialDerivative(final int n) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convert the instance to a {@link DerivativeStructure}.
     * @return derivative structure with same value and derivative as the instance
     */
    public DerivativeStructure toDerivativeStructure() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient add(final Gradient a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient subtract(final Gradient a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient multiply(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient multiply(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient multiply(final Gradient a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient divide(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient divide(final Gradient a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient remainder(final Gradient a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient negate() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient abs() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient copySign(final Gradient sign) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient copySign(final double sign) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient scalb(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient hypot(final Gradient y) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient compose(final double... f) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient compose(final double f0, final double f1) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GradientField getField() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute a<sup>x</sup> where a is a double and x a {@link Gradient}
     * @param a number to exponentiate
     * @param x power to apply
     * @return a<sup>x</sup>
     */
    public static Gradient pow(final double a, final Gradient x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient pow(final double p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient pow(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldSinCos<Gradient> sinCos() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient atan2(final Gradient x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldSinhCosh<Gradient> sinhCosh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient toDegrees() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient toRadians() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Evaluate Taylor expansion a derivative structure.
     * @param delta parameters offsets (&Delta;x, &Delta;y, ...)
     * @return value of the Taylor expansion at x + &Delta;x, y + &Delta;y, ...
     */
    public double taylor(final double... delta) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient linearCombination(final Gradient[] a, final Gradient[] b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient linearCombination(final double[] a, final Gradient[] b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient linearCombination(final Gradient a1, final Gradient b1, final Gradient a2, final Gradient b2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient linearCombination(final double a1, final Gradient b1, final double a2, final Gradient b2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient linearCombination(final Gradient a1, final Gradient b1, final Gradient a2, final Gradient b2, final Gradient a3, final Gradient b3) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient linearCombination(final double a1, final Gradient b1, final double a2, final Gradient b2, final double a3, final Gradient b3) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient linearCombination(final Gradient a1, final Gradient b1, final Gradient a2, final Gradient b2, final Gradient a3, final Gradient b3, final Gradient a4, final Gradient b4) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gradient linearCombination(final double a1, final Gradient b1, final double a2, final Gradient b2, final double a3, final Gradient b3, final double a4, final Gradient b4) {
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
    public Gradient stackVariable() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSmall(final Gradient base, final double relativeThreshold) {
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
