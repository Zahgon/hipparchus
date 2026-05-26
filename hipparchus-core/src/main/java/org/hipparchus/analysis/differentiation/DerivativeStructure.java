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
package org.hipparchus.analysis.differentiation;

import java.io.Serializable;
import org.hipparchus.Field;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.MathRuntimeException;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.FieldSinCos;
import org.hipparchus.util.FieldSinhCosh;
import org.hipparchus.util.MathArrays;
import org.hipparchus.util.MathUtils;

/**
 * Class representing both the value and the differentials of a function.
 * <p>This class is the workhorse of the differentiation package.</p>
 * <p>This class is an implementation of the extension to Rall's
 * numbers described in Dan Kalman's paper <a
 * href="http://www.dankalman.net/AUhome/pdffiles/mmgautodiff.pdf">Doubly
 * Recursive Multivariate Automatic Differentiation</a>, Mathematics Magazine, vol. 75,
 * no. 3, June 2002. Rall's numbers are an extension to the real numbers used
 * throughout mathematical expressions; they hold the derivative together with the
 * value of a function. Dan Kalman's derivative structures hold all partial derivatives
 * up to any specified order, with respect to any number of free parameters. Rall's
 * numbers therefore can be seen as derivative structures for order one derivative and
 * one free parameter, and real numbers can be seen as derivative structures with zero
 * order derivative and no free parameters.</p>
 * <p>{@link DerivativeStructure} instances can be used directly thanks to
 * the arithmetic operators to the mathematical functions provided as
 * methods by this class (+, -, *, /, %, sin, cos ...).</p>
 * <p>Implementing complex expressions by hand using {@link Derivative}-based
 * classes (or in fact any {@link org.hipparchus.CalculusFieldElement} class) is
 * a tedious and error-prone task but has the advantage of not requiring users
 * to compute the derivatives by themselves and allowing to switch for one
 * derivative implementation to another as they all share the same filed API.</p>
 * <p>Implementing complex expression can also be done by developing computation
 * code using standard primitive double values and to use {@link
 * UnivariateFunctionDifferentiator differentiators} to create the {@link
 * DerivativeStructure}-based instances. This method is simpler but may be limited in
 * the accuracy and derivation orders and may be computationally intensive (this is
 * typically the case for {@link FiniteDifferencesDifferentiator finite differences
 * differentiator}.</p>
 * <p>Instances of this class are guaranteed to be immutable.</p>
 * @see DSCompiler
 * @see FieldDerivativeStructure
 */
public class DerivativeStructure implements Derivative<DerivativeStructure>, Serializable {

    /**
     * Serializable UID.
     */
    private static final long serialVersionUID = 20161220L;

    /**
     * Factory that built the instance.
     */
    private final DSFactory factory;

    /**
     * Combined array holding all values.
     */
    private final double[] data;

    /**
     * Build an instance with all values and derivatives set to 0.
     * @param factory factory that built the instance
     * @param data combined array holding all values
     */
    DerivativeStructure(final DSFactory factory, final double[] data) {
        this.factory = factory;
        this.data = data.clone();
    }

    /**
     * Build an instance with all values and derivatives set to 0.
     * @param factory factory that built the instance
     * @since 1.4
     */
    DerivativeStructure(final DSFactory factory) {
        this.factory = factory;
        this.data = new double[factory.getCompiler().getSize()];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DerivativeStructure newInstance(final double value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DerivativeStructure withValue(final double value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the factory that built the instance.
     * @return factory that built the instance
     */
    public DSFactory getFactory() {
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
    public int getOrder() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Set a derivative component.
     * <p>
     * This method is package-private (no modifier specified), as it is intended
     * to be used only by Hipparchus classes since it relied on the ordering of
     * derivatives within the class. This allows avoiding checks on the index,
     * for performance reasons.
     * </p>
     * @param index index of the derivative
     * @param value of the derivative to set
     * @since 1.4
     */
    void setDerivativeComponent(final int index, final double value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get a derivative component.
     * <p>
     * This method is package-private (no modifier specified), as it is intended
     * to be used only by Hipparchus classes since it relied on the ordering of
     * derivatives within the class. This allows avoiding checks on the index,
     * for performance reasons.
     * </p>
     * @param index index of the derivative
     * @return value of the derivative
     * @since 2.2
     */
    double getDerivativeComponent(final int index) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DerivativeStructure getAddendum() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the value part of the derivative structure.
     * @return value part of the derivative structure
     * @see #getPartialDerivative(int...)
     */
    @Override
    public double getValue() {
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
     * Get all partial derivatives.
     * @return a fresh copy of partial derivatives, in an array sorted according to
     * {@link DSCompiler#getPartialDerivativeIndex(int...)}
     */
    public double[] getAllDerivatives() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * @exception MathIllegalArgumentException if number of free parameters
     * or orders do not match
     */
    @Override
    public DerivativeStructure add(final DerivativeStructure a) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * @exception MathIllegalArgumentException if number of free parameters
     * or orders do not match
     */
    @Override
    public DerivativeStructure subtract(final DerivativeStructure a) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DerivativeStructure multiply(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * @exception MathIllegalArgumentException if number of free parameters
     * or orders do not match
     */
    @Override
    public DerivativeStructure multiply(final DerivativeStructure a) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DerivativeStructure square() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DerivativeStructure divide(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * @exception MathIllegalArgumentException if number of free parameters
     * or orders do not match
     */
    @Override
    public DerivativeStructure divide(final DerivativeStructure a) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * @exception MathIllegalArgumentException if number of free parameters
     * or orders do not match
     */
    @Override
    public DerivativeStructure remainder(final DerivativeStructure a) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DerivativeStructure negate() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DerivativeStructure abs() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DerivativeStructure copySign(final DerivativeStructure sign) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DerivativeStructure copySign(final double sign) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DerivativeStructure scalb(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * @exception MathIllegalArgumentException if number of free parameters
     * or orders do not match
     */
    @Override
    public DerivativeStructure hypot(final DerivativeStructure y) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the hypotenuse of a triangle with sides {@code x} and {@code y}
     * - sqrt(<i>x</i><sup>2</sup>&nbsp;+<i>y</i><sup>2</sup>)
     * avoiding intermediate overflow or underflow.
     *
     * <ul>
     * <li> If either argument is infinite, then the result is positive infinity.</li>
     * <li> else, if either argument is NaN then the result is NaN.</li>
     * </ul>
     *
     * @param x a value
     * @param y a value
     * @return sqrt(<i>x</i><sup>2</sup>&nbsp;+<i>y</i><sup>2</sup>)
     * @exception MathIllegalArgumentException if number of free parameters
     * or orders do not match
     */
    public static DerivativeStructure hypot(final DerivativeStructure x, final DerivativeStructure y) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute composition of the instance by a univariate function.
     * @param f array of value and derivatives of the function at
     * the current point (i.e. [f({@link #getValue()}),
     * f'({@link #getValue()}), f''({@link #getValue()})...]).
     * @return f(this)
     * @exception MathIllegalArgumentException if the number of derivatives
     * in the array is not equal to {@link #getOrder() order} + 1
     */
    @Override
    public DerivativeStructure compose(final double... f) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DerivativeStructure reciprocal() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DerivativeStructure sqrt() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DerivativeStructure rootN(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<DerivativeStructure> getField() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute a<sup>x</sup> where a is a double and x a {@link DerivativeStructure}
     * @param a number to exponentiate
     * @param x power to apply
     * @return a<sup>x</sup>
     */
    public static DerivativeStructure pow(final double a, final DerivativeStructure x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DerivativeStructure pow(final double p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DerivativeStructure pow(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * @exception MathIllegalArgumentException if number of free parameters
     * or orders do not match
     */
    @Override
    public DerivativeStructure pow(final DerivativeStructure e) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DerivativeStructure exp() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DerivativeStructure expm1() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DerivativeStructure log() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DerivativeStructure log1p() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Base 10 logarithm.
     * @return base 10 logarithm of the instance
     */
    @Override
    public DerivativeStructure log10() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DerivativeStructure cos() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DerivativeStructure sin() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldSinCos<DerivativeStructure> sinCos() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DerivativeStructure tan() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DerivativeStructure acos() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DerivativeStructure asin() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DerivativeStructure atan() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DerivativeStructure atan2(final DerivativeStructure x) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Two arguments arc tangent operation.
     * @param y first argument of the arc tangent
     * @param x second argument of the arc tangent
     * @return atan2(y, x)
     * @exception MathIllegalArgumentException if number of free parameters
     * or orders do not match
     */
    public static DerivativeStructure atan2(final DerivativeStructure y, final DerivativeStructure x) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DerivativeStructure cosh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DerivativeStructure sinh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldSinhCosh<DerivativeStructure> sinhCosh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DerivativeStructure tanh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DerivativeStructure acosh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DerivativeStructure asinh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DerivativeStructure atanh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DerivativeStructure toDegrees() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DerivativeStructure toRadians() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Integrate w.r.t. one independent variable.
     * <p>
     * Rigorously, if the derivatives of a function are known up to
     * order N, the ones of its M-th integral w.r.t. a given variable
     * (seen as a function itself) are actually known up to order N+M.
     * However, this method still casts the output as a DerivativeStructure
     * of order N. The integration constants are systematically set to zero.
     * </p>
     * @param varIndex Index of independent variable w.r.t. which integration is done.
     * @param integrationOrder Number of times the integration operator must be applied. If non-positive, call the
     *                         differentiation operator.
     * @return DerivativeStructure on which integration operator has been applied a certain number of times.
     * @since 2.2
     */
    public DerivativeStructure integrate(final int varIndex, final int integrationOrder) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Differentiate w.r.t. one independent variable.
     * <p>
     * Rigorously, if the derivatives of a function are known up to
     * order N, the ones of its M-th derivative w.r.t. a given variable
     * (seen as a function itself) are only known up to order N-M.
     * However, this method still casts the output as a DerivativeStructure
     * of order N with zeroes for the higher order terms.
     * </p>
     * @param varIndex Index of independent variable w.r.t. which differentiation is done.
     * @param differentiationOrder Number of times the differentiation operator must be applied. If non-positive, call
     *                             the integration operator instead.
     * @return DerivativeStructure on which differentiation operator has been applied a certain number of times
     * @since 2.2
     */
    public DerivativeStructure differentiate(final int varIndex, final int differentiationOrder) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Evaluate Taylor expansion a derivative structure.
     * @param delta parameters offsets (&Delta;x, &Delta;y, ...)
     * @return value of the Taylor expansion at x + &Delta;x, y + &Delta;y, ...
     * @throws MathRuntimeException if factorials becomes too large
     */
    public double taylor(final double... delta) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Rebase instance with respect to low level parameter functions.
     * <p>
     * The instance is considered to be a function of {@link #getFreeParameters()
     * n free parameters} up to order {@link #getOrder() o} \(f(p_0, p_1, \ldots p_{n-1})\).
     * Its {@link #getPartialDerivative(int...) partial derivatives} are therefore
     * \(f, \frac{\partial f}{\partial p_0}, \frac{\partial f}{\partial p_1}, \ldots
     * \frac{\partial^2 f}{\partial p_0^2}, \frac{\partial^2 f}{\partial p_0 p_1},
     * \ldots \frac{\partial^o f}{\partial p_{n-1}^o}\). The free parameters
     * \(p_0, p_1, \ldots p_{n-1}\) are considered to be functions of \(m\) lower
     * level other parameters \(q_0, q_1, \ldots q_{m-1}\).
     * </p>
     * \( \begin{align}
     * p_0 &amp; = p_0(q_0, q_1, \ldots q_{m-1})\\
     * p_1 &amp; = p_1(q_0, q_1, \ldots q_{m-1})\\
     * p_{n-1} &amp; = p_{n-1}(q_0, q_1, \ldots q_{m-1})
     * \end{align}\)
     * <p>
     * This method compute the composition of the partial derivatives of \(f\)
     * and the partial derivatives of \(p_0, p_1, \ldots p_{n-1}\), i.e. the
     * {@link #getPartialDerivative(int...) partial derivatives} of the value
     * returned will be
     * \(f, \frac{\partial f}{\partial q_0}, \frac{\partial f}{\partial q_1}, \ldots
     * \frac{\partial^2 f}{\partial q_0^2}, \frac{\partial^2 f}{\partial q_0 q_1},
     * \ldots \frac{\partial^o f}{\partial q_{m-1}^o}\).
     * </p>
     * <p>
     * The number of parameters must match {@link #getFreeParameters()} and the
     * derivation orders of the instance and parameters must also match.
     * </p>
     * @param p base parameters with respect to which partial derivatives
     * were computed in the instance
     * @return derivative structure with partial derivatives computed
     * with respect to the lower level parameters used in the \(p_i\)
     * @since 2.2
     */
    public DerivativeStructure rebase(final DerivativeStructure... p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * @exception MathIllegalArgumentException if number of free parameters
     * or orders do not match
     */
    @Override
    public DerivativeStructure linearCombination(final DerivativeStructure[] a, final DerivativeStructure[] b) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * @exception MathIllegalArgumentException if number of free parameters
     * or orders do not match
     */
    @Override
    public DerivativeStructure linearCombination(final double[] a, final DerivativeStructure[] b) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * @exception MathIllegalArgumentException if number of free parameters
     * or orders do not match
     */
    @Override
    public DerivativeStructure linearCombination(final DerivativeStructure a1, final DerivativeStructure b1, final DerivativeStructure a2, final DerivativeStructure b2) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * @exception MathIllegalArgumentException if number of free parameters
     * or orders do not match
     */
    @Override
    public DerivativeStructure linearCombination(final double a1, final DerivativeStructure b1, final double a2, final DerivativeStructure b2) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * @exception MathIllegalArgumentException if number of free parameters
     * or orders do not match
     */
    @Override
    public DerivativeStructure linearCombination(final DerivativeStructure a1, final DerivativeStructure b1, final DerivativeStructure a2, final DerivativeStructure b2, final DerivativeStructure a3, final DerivativeStructure b3) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * @exception MathIllegalArgumentException if number of free parameters
     * or orders do not match
     */
    @Override
    public DerivativeStructure linearCombination(final double a1, final DerivativeStructure b1, final double a2, final DerivativeStructure b2, final double a3, final DerivativeStructure b3) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * @exception MathIllegalArgumentException if number of free parameters
     * or orders do not match
     */
    @Override
    public DerivativeStructure linearCombination(final DerivativeStructure a1, final DerivativeStructure b1, final DerivativeStructure a2, final DerivativeStructure b2, final DerivativeStructure a3, final DerivativeStructure b3, final DerivativeStructure a4, final DerivativeStructure b4) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * @exception MathIllegalArgumentException if number of free parameters
     * or orders do not match
     */
    @Override
    public DerivativeStructure linearCombination(final double a1, final DerivativeStructure b1, final double a2, final DerivativeStructure b2, final double a3, final DerivativeStructure b3, final double a4, final DerivativeStructure b4) throws MathIllegalArgumentException {
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
     * {@inheritDoc}
     */
    @Override
    public DerivativeStructure getPi() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSmall(final DerivativeStructure base, final double relativeThreshold) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Test for the equality of two derivative structures.
     * <p>
     * Derivative structures are considered equal if they have the same number
     * of free parameters, the same derivation order, and the same derivatives.
     * </p>
     * @param other Object to test for equality to this
     * @return true if two derivative structures are equal
     */
    @Override
    public boolean equals(Object other) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get a hashCode for the derivative structure.
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
