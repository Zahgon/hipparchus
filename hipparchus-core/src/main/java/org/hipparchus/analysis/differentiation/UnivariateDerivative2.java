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
 * with only one {@link DerivativeStructure#getFreeParameters() free parameter}
 * and {@link DerivativeStructure#getOrder() derivation order} also limited to two.
 * It should have less overhead than {@link DerivativeStructure} in its domain.</p>
 * <p>This class is an implementation of Rall's numbers. Rall's numbers are an
 * extension to the real numbers used throughout mathematical expressions; they hold
 * the derivative together with the value of a function.</p>
 * <p>{@link UnivariateDerivative2} instances can be used directly thanks to
 * the arithmetic operators to the mathematical functions provided as
 * methods by this class (+, -, *, /, %, sin, cos ...).</p>
 * <p>Implementing complex expressions by hand using {@link Derivative}-based
 * classes (or in fact any {@link org.hipparchus.CalculusFieldElement} class) is
 * a tedious and error-prone task but has the advantage of not requiring users
 * to compute the derivatives by themselves and allowing to switch for one
 * derivative implementation to another as they all share the same filed API.</p>
 * <p>Instances of this class are guaranteed to be immutable.</p>
 * @see DerivativeStructure
 * @see UnivariateDerivative2
 * @see Gradient
 * @see FieldDerivativeStructure
 * @see FieldUnivariateDerivative2
 * @see FieldUnivariateDerivative2
 * @see FieldGradient
 * @since 1.7
 */
public class UnivariateDerivative2 extends UnivariateDerivative<UnivariateDerivative2> {

    /**
     * The constant value of π as a {@code UnivariateDerivative2}.
     * @since 2.0
     */
    public static final UnivariateDerivative2 PI = new UnivariateDerivative2(FastMath.PI, 0.0, 0.0);

    /**
     * Serializable UID.
     */
    private static final long serialVersionUID = 20200520L;

    /**
     * Value of the function.
     */
    private final double f0;

    /**
     * First derivative of the function.
     */
    private final double f1;

    /**
     * Second derivative of the function.
     */
    private final double f2;

    /**
     * Build an instance with values and derivative.
     * @param f0 value of the function
     * @param f1 first derivative of the function
     * @param f2 second derivative of the function
     */
    public UnivariateDerivative2(final double f0, final double f1, final double f2) {
        this.f0 = f0;
        this.f1 = f1;
        this.f2 = f2;
    }

    /**
     * Build an instance from a {@link DerivativeStructure}.
     * @param ds derivative structure
     * @exception MathIllegalArgumentException if either {@code ds} parameters
     * is not 1 or {@code ds} order is not 2
     */
    public UnivariateDerivative2(final DerivativeStructure ds) throws MathIllegalArgumentException {
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
    public UnivariateDerivative2 newInstance(final double value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public UnivariateDerivative2 withValue(final double value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 getAddendum() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getValue() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getDerivative(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getOrder() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the first derivative.
     * @return first derivative
     * @see #getValue()
     * @see #getSecondDerivative()
     */
    public double getFirstDerivative() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the second derivative.
     * @return second derivative
     * @see #getValue()
     * @see #getFirstDerivative()
     */
    public double getSecondDerivative() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DerivativeStructure toDerivativeStructure() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 add(final UnivariateDerivative2 a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 subtract(final UnivariateDerivative2 a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 multiply(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 multiply(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 multiply(final UnivariateDerivative2 a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 square() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 divide(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 divide(final UnivariateDerivative2 a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 remainder(final UnivariateDerivative2 a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 negate() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 abs() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 copySign(final UnivariateDerivative2 sign) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 copySign(final double sign) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 scalb(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 hypot(final UnivariateDerivative2 y) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 reciprocal() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 compose(final double... f) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 sqrt() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 cbrt() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 rootN(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2Field getField() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute a<sup>x</sup> where a is a double and x a {@link UnivariateDerivative2}
     * @param a number to exponentiate
     * @param x power to apply
     * @return a<sup>x</sup>
     */
    public static UnivariateDerivative2 pow(final double a, final UnivariateDerivative2 x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 pow(final double p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 pow(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 exp() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 expm1() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 log() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 log1p() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 log10() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 cos() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 sin() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldSinCos<UnivariateDerivative2> sinCos() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 tan() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 acos() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 asin() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 atan() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 atan2(final UnivariateDerivative2 x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 cosh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 sinh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldSinhCosh<UnivariateDerivative2> sinhCosh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 tanh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 acosh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 asinh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 atanh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 toDegrees() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 toRadians() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Evaluate Taylor expansion a univariate derivative.
     * @param delta parameter offset Δx
     * @return value of the Taylor expansion at x + Δx
     */
    public double taylor(final double delta) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 linearCombination(final UnivariateDerivative2[] a, final UnivariateDerivative2[] b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 linearCombination(final double[] a, final UnivariateDerivative2[] b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 linearCombination(final UnivariateDerivative2 a1, final UnivariateDerivative2 b1, final UnivariateDerivative2 a2, final UnivariateDerivative2 b2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 linearCombination(final double a1, final UnivariateDerivative2 b1, final double a2, final UnivariateDerivative2 b2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 linearCombination(final UnivariateDerivative2 a1, final UnivariateDerivative2 b1, final UnivariateDerivative2 a2, final UnivariateDerivative2 b2, final UnivariateDerivative2 a3, final UnivariateDerivative2 b3) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 linearCombination(final double a1, final UnivariateDerivative2 b1, final double a2, final UnivariateDerivative2 b2, final double a3, final UnivariateDerivative2 b3) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 linearCombination(final UnivariateDerivative2 a1, final UnivariateDerivative2 b1, final UnivariateDerivative2 a2, final UnivariateDerivative2 b2, final UnivariateDerivative2 a3, final UnivariateDerivative2 b3, final UnivariateDerivative2 a4, final UnivariateDerivative2 b4) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 linearCombination(final double a1, final UnivariateDerivative2 b1, final double a2, final UnivariateDerivative2 b2, final double a3, final UnivariateDerivative2 b3, final double a4, final UnivariateDerivative2 b4) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative2 getPi() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSmall(final UnivariateDerivative2 base, final double relativeThreshold) {
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

    /**
     * {@inheritDoc}
     * <p>
     * Comparison performed considering that derivatives are intrinsically linked to monomials in the corresponding
     * Taylor expansion and that the higher the degree, the smaller the term.
     * </p>
     * @since 3.0
     */
    @Override
    public int compareTo(final UnivariateDerivative2 o) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
