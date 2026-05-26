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
import org.hipparchus.util.MathArrays;
import org.hipparchus.util.MathUtils;

/**
 * Class representing both the value and the differentials of a function.
 * <p>This class is a stripped-down version of {@link DerivativeStructure}
 * with only one {@link DerivativeStructure#getFreeParameters() free parameter}
 * and {@link DerivativeStructure#getOrder() derivation order} also limited to one.
 * It should have less overhead than {@link DerivativeStructure} in its domain.</p>
 * <p>This class is an implementation of Rall's numbers. Rall's numbers are an
 * extension to the real numbers used throughout mathematical expressions; they hold
 * the derivative together with the value of a function.</p>
 * <p>{@link UnivariateDerivative1} instances can be used directly thanks to
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
 * @see FieldUnivariateDerivative1
 * @see FieldUnivariateDerivative2
 * @see FieldGradient
 * @since 1.7
 */
public class UnivariateDerivative1 extends UnivariateDerivative<UnivariateDerivative1> implements Derivative1<UnivariateDerivative1> {

    /**
     * The constant value of π as a {@code UnivariateDerivative1}.
     * @since 2.0
     */
    public static final UnivariateDerivative1 PI = new UnivariateDerivative1(FastMath.PI, 0.0);

    /**
     * Serializable UID.
     */
    private static final long serialVersionUID = 20200519L;

    /**
     * Value of the function.
     */
    private final double f0;

    /**
     * First derivative of the function.
     */
    private final double f1;

    /**
     * Build an instance with values and derivative.
     * @param f0 value of the function
     * @param f1 first derivative of the function
     */
    public UnivariateDerivative1(final double f0, final double f1) {
        this.f0 = f0;
        this.f1 = f1;
    }

    /**
     * Build an instance from a {@link DerivativeStructure}.
     * @param ds derivative structure
     * @exception MathIllegalArgumentException if either {@code ds} parameters
     * is not 1 or {@code ds} order is not 1
     */
    public UnivariateDerivative1(final DerivativeStructure ds) throws MathIllegalArgumentException {
        MathUtils.checkDimension(ds.getFreeParameters(), 1);
        MathUtils.checkDimension(ds.getOrder(), 1);
        this.f0 = ds.getValue();
        this.f1 = ds.getPartialDerivative(1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative1 newInstance(final double value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative1 withValue(final double value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative1 getAddendum() {
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
     * Get the first derivative.
     * @return first derivative
     * @see #getValue()
     */
    public double getFirstDerivative() {
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
    public UnivariateDerivative1 add(final UnivariateDerivative1 a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative1 subtract(final UnivariateDerivative1 a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative1 multiply(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative1 multiply(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative1 multiply(final UnivariateDerivative1 a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative1 divide(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative1 divide(final UnivariateDerivative1 a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative1 remainder(final UnivariateDerivative1 a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative1 negate() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative1 abs() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative1 copySign(final UnivariateDerivative1 sign) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative1 copySign(final double sign) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative1 scalb(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative1 hypot(final UnivariateDerivative1 y) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative1 compose(final double... f) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative1 compose(final double ff0, final double ff1) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative1Field getField() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute a<sup>x</sup> where a is a double and x a {@link UnivariateDerivative1}
     * @param a number to exponentiate
     * @param x power to apply
     * @return a<sup>x</sup>
     */
    public static UnivariateDerivative1 pow(final double a, final UnivariateDerivative1 x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative1 pow(final double p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative1 pow(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative1 atan2(final UnivariateDerivative1 x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative1 toDegrees() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative1 toRadians() {
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
    public UnivariateDerivative1 linearCombination(final UnivariateDerivative1[] a, final UnivariateDerivative1[] b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative1 linearCombination(final double[] a, final UnivariateDerivative1[] b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative1 linearCombination(final UnivariateDerivative1 a1, final UnivariateDerivative1 b1, final UnivariateDerivative1 a2, final UnivariateDerivative1 b2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative1 linearCombination(final double a1, final UnivariateDerivative1 b1, final double a2, final UnivariateDerivative1 b2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative1 linearCombination(final UnivariateDerivative1 a1, final UnivariateDerivative1 b1, final UnivariateDerivative1 a2, final UnivariateDerivative1 b2, final UnivariateDerivative1 a3, final UnivariateDerivative1 b3) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative1 linearCombination(final double a1, final UnivariateDerivative1 b1, final double a2, final UnivariateDerivative1 b2, final double a3, final UnivariateDerivative1 b3) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative1 linearCombination(final UnivariateDerivative1 a1, final UnivariateDerivative1 b1, final UnivariateDerivative1 a2, final UnivariateDerivative1 b2, final UnivariateDerivative1 a3, final UnivariateDerivative1 b3, final UnivariateDerivative1 a4, final UnivariateDerivative1 b4) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative1 linearCombination(final double a1, final UnivariateDerivative1 b1, final double a2, final UnivariateDerivative1 b2, final double a3, final UnivariateDerivative1 b3, final double a4, final UnivariateDerivative1 b4) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnivariateDerivative1 getPi() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSmall(final UnivariateDerivative1 base, final double relativeThreshold) {
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
    public int compareTo(final UnivariateDerivative1 o) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
