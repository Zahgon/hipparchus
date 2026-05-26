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
import org.hipparchus.exception.MathRuntimeException;
import org.hipparchus.util.FieldSinCos;
import org.hipparchus.util.FieldSinhCosh;
import org.hipparchus.util.MathArrays;
import org.hipparchus.util.MathUtils;

/**
 * Class representing both the value and the differentials of a function.
 * <p>This class is similar to {@link DerivativeStructure} except function
 * parameters and value can be any {@link CalculusFieldElement}.</p>
 * <p>Instances of this class are guaranteed to be immutable.</p>
 * @see DerivativeStructure
 * @see FDSFactory
 * @see DSCompiler
 * @param <T> the type of the field elements
 */
public class FieldDerivativeStructure<T extends CalculusFieldElement<T>> implements FieldDerivative<T, FieldDerivativeStructure<T>> {

    /**
     * Factory that built the instance.
     */
    private final FDSFactory<T> factory;

    /**
     * Combined array holding all values.
     */
    private final T[] data;

    /**
     * Build an instance with all values and derivatives set to 0.
     * @param factory factory that built the instance
     * @param data combined array holding all values
     */
    FieldDerivativeStructure(final FDSFactory<T> factory, final T[] data) {
        this.factory = factory;
        this.data = data.clone();
    }

    /**
     * Build an instance with all values and derivatives set to 0.
     * @param factory factory that built the instance
     * @since 1.4
     */
    FieldDerivativeStructure(final FDSFactory<T> factory) {
        this.factory = factory;
        this.data = MathArrays.buildArray(factory.getValueField(), factory.getCompiler().getSize());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> newInstance(final double value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> newInstance(final T value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> withValue(final T value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the factory that built the instance.
     * @return factory that built the instance
     */
    public FDSFactory<T> getFactory() {
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
    void setDerivativeComponent(final int index, final T value) {
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
    T getDerivativeComponent(final int index) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> getAddendum() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the value part of the derivative structure.
     * @return value part of the derivative structure
     * @see #getPartialDerivative(int...)
     */
    @Override
    public T getValue() {
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
     * Get all partial derivatives.
     * @return a fresh copy of partial derivatives, in an array sorted according to
     * {@link DSCompiler#getPartialDerivativeIndex(int...)}
     */
    public T[] getAllDerivatives() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> add(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * @exception MathIllegalArgumentException if number of free parameters
     * or orders do not match
     */
    @Override
    public FieldDerivativeStructure<T> add(final FieldDerivativeStructure<T> a) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> subtract(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * @exception MathIllegalArgumentException if number of free parameters
     * or orders do not match
     */
    @Override
    public FieldDerivativeStructure<T> subtract(final FieldDerivativeStructure<T> a) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * '&times;' operator.
     * @param a right hand side parameter of the operator
     * @return this&times;a
     */
    public FieldDerivativeStructure<T> multiply(final T a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> multiply(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * @exception MathIllegalArgumentException if number of free parameters
     * or orders do not match
     */
    @Override
    public FieldDerivativeStructure<T> multiply(final FieldDerivativeStructure<T> a) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> square() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * '&divide;' operator.
     * @param a right hand side parameter of the operator
     * @return this&divide;a
     */
    public FieldDerivativeStructure<T> divide(final T a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> divide(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * @exception MathIllegalArgumentException if number of free parameters
     * or orders do not match
     */
    @Override
    public FieldDerivativeStructure<T> divide(final FieldDerivativeStructure<T> a) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * IEEE remainder operator.
     * @param a right hand side parameter of the operator
     * @return this - n &times; a where n is the closest integer to this/a
     * (the even integer is chosen for n if this/a is halfway between two integers)
     */
    public FieldDerivativeStructure<T> remainder(final T a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> remainder(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * @exception MathIllegalArgumentException if number of free parameters
     * or orders do not match
     */
    @Override
    public FieldDerivativeStructure<T> remainder(final FieldDerivativeStructure<T> a) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> negate() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> abs() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the instance with the sign of the argument.
     * A NaN {@code sign} argument is treated as positive.
     *
     * @param sign the sign for the returned value
     * @return the instance with the same sign as the {@code sign} argument
     */
    public FieldDerivativeStructure<T> copySign(final T sign) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> copySign(final double sign) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> copySign(final FieldDerivativeStructure<T> sign) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> scalb(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * @exception MathIllegalArgumentException if number of free parameters
     * or orders do not match
     */
    @Override
    public FieldDerivativeStructure<T> hypot(final FieldDerivativeStructure<T> y) throws MathIllegalArgumentException {
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
     * @param <T> the type of the field elements
     */
    public static <T extends CalculusFieldElement<T>> FieldDerivativeStructure<T> hypot(final FieldDerivativeStructure<T> x, final FieldDerivativeStructure<T> y) throws MathIllegalArgumentException {
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
    @SafeVarargs
    public final FieldDerivativeStructure<T> compose(final T... f) throws MathIllegalArgumentException {
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
    public FieldDerivativeStructure<T> compose(final double... f) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> reciprocal() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> sqrt() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> rootN(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<FieldDerivativeStructure<T>> getField() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute a<sup>x</sup> where a is a double and x a {@link FieldDerivativeStructure}
     * @param a number to exponentiate
     * @param x power to apply
     * @param <T> the type of the field elements
     * @return a<sup>x</sup>
     */
    public static <T extends CalculusFieldElement<T>> FieldDerivativeStructure<T> pow(final double a, final FieldDerivativeStructure<T> x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> pow(final double p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> pow(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * @exception MathIllegalArgumentException if number of free parameters
     * or orders do not match
     */
    @Override
    public FieldDerivativeStructure<T> pow(final FieldDerivativeStructure<T> e) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> exp() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> expm1() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> log() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> log1p() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Base 10 logarithm.
     * @return base 10 logarithm of the instance
     */
    @Override
    public FieldDerivativeStructure<T> log10() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> cos() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> sin() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldSinCos<FieldDerivativeStructure<T>> sinCos() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> tan() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> acos() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> asin() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> atan() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> atan2(final FieldDerivativeStructure<T> x) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Two arguments arc tangent operation.
     * @param y first argument of the arc tangent
     * @param x second argument of the arc tangent
     * @param <T> the type of the field elements
     * @return atan2(y, x)
     * @exception MathIllegalArgumentException if number of free parameters
     * or orders do not match
     */
    public static <T extends CalculusFieldElement<T>> FieldDerivativeStructure<T> atan2(final FieldDerivativeStructure<T> y, final FieldDerivativeStructure<T> x) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> cosh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> sinh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldSinhCosh<FieldDerivativeStructure<T>> sinhCosh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> tanh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> acosh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> asinh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> atanh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> toDegrees() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDerivativeStructure<T> toRadians() {
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
    public FieldDerivativeStructure<T> integrate(final int varIndex, final int integrationOrder) {
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
    public FieldDerivativeStructure<T> differentiate(final int varIndex, final int differentiationOrder) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Evaluate Taylor expansion of a derivative structure.
     * @param delta parameters offsets (&Delta;x, &Delta;y, ...)
     * @return value of the Taylor expansion at x + &Delta;x, y + &Delta;y, ...
     * @throws MathRuntimeException if factorials becomes too large
     */
    @SafeVarargs
    public final T taylor(final T... delta) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Evaluate Taylor expansion of a derivative structure.
     * @param delta parameters offsets (&Delta;x, &Delta;y, ...)
     * @return value of the Taylor expansion at x + &Delta;x, y + &Delta;y, ...
     * @throws MathRuntimeException if factorials becomes too large
     */
    public T taylor(final double... delta) throws MathRuntimeException {
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
    @SuppressWarnings("unchecked")
    public FieldDerivativeStructure<T> rebase(final FieldDerivativeStructure<T>... p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * @exception MathIllegalArgumentException if number of free parameters
     * or orders do not match
     */
    @Override
    public FieldDerivativeStructure<T> linearCombination(final FieldDerivativeStructure<T>[] a, final FieldDerivativeStructure<T>[] b) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute a linear combination.
     * @param a Factors.
     * @param b Factors.
     * @return <code>&Sigma;<sub>i</sub> a<sub>i</sub> b<sub>i</sub></code>.
     * @throws MathIllegalArgumentException if arrays dimensions don't match
     */
    public FieldDerivativeStructure<T> linearCombination(final T[] a, final FieldDerivativeStructure<T>[] b) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * @exception MathIllegalArgumentException if number of free parameters
     * or orders do not match
     */
    @Override
    public FieldDerivativeStructure<T> linearCombination(final double[] a, final FieldDerivativeStructure<T>[] b) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * @exception MathIllegalArgumentException if number of free parameters
     * or orders do not match
     */
    @Override
    public FieldDerivativeStructure<T> linearCombination(final FieldDerivativeStructure<T> a1, final FieldDerivativeStructure<T> b1, final FieldDerivativeStructure<T> a2, final FieldDerivativeStructure<T> b2) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute a linear combination.
     * @param a1 first factor of the first term
     * @param b1 second factor of the first term
     * @param a2 first factor of the second term
     * @param b2 second factor of the second term
     * @return a<sub>1</sub>&times;b<sub>1</sub> +
     * a<sub>2</sub>&times;b<sub>2</sub>
     * @see #linearCombination(double, FieldDerivativeStructure, double, FieldDerivativeStructure)
     * @see #linearCombination(double, FieldDerivativeStructure, double, FieldDerivativeStructure, double, FieldDerivativeStructure, double, FieldDerivativeStructure)
     * @exception MathIllegalArgumentException if number of free parameters or orders are inconsistent
     */
    public FieldDerivativeStructure<T> linearCombination(final T a1, final FieldDerivativeStructure<T> b1, final T a2, final FieldDerivativeStructure<T> b2) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * @exception MathIllegalArgumentException if number of free parameters
     * or orders do not match
     */
    @Override
    public FieldDerivativeStructure<T> linearCombination(final double a1, final FieldDerivativeStructure<T> b1, final double a2, final FieldDerivativeStructure<T> b2) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * @exception MathIllegalArgumentException if number of free parameters
     * or orders do not match
     */
    @Override
    public FieldDerivativeStructure<T> linearCombination(final FieldDerivativeStructure<T> a1, final FieldDerivativeStructure<T> b1, final FieldDerivativeStructure<T> a2, final FieldDerivativeStructure<T> b2, final FieldDerivativeStructure<T> a3, final FieldDerivativeStructure<T> b3) throws MathIllegalArgumentException {
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
     * @see #linearCombination(double, FieldDerivativeStructure, double, FieldDerivativeStructure)
     * @see #linearCombination(double, FieldDerivativeStructure, double, FieldDerivativeStructure, double, FieldDerivativeStructure, double, FieldDerivativeStructure)
     * @exception MathIllegalArgumentException if number of free parameters or orders are inconsistent
     */
    public FieldDerivativeStructure<T> linearCombination(final T a1, final FieldDerivativeStructure<T> b1, final T a2, final FieldDerivativeStructure<T> b2, final T a3, final FieldDerivativeStructure<T> b3) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * @exception MathIllegalArgumentException if number of free parameters
     * or orders do not match
     */
    @Override
    public FieldDerivativeStructure<T> linearCombination(final double a1, final FieldDerivativeStructure<T> b1, final double a2, final FieldDerivativeStructure<T> b2, final double a3, final FieldDerivativeStructure<T> b3) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * @exception MathIllegalArgumentException if number of free parameters
     * or orders do not match
     */
    @Override
    public FieldDerivativeStructure<T> linearCombination(final FieldDerivativeStructure<T> a1, final FieldDerivativeStructure<T> b1, final FieldDerivativeStructure<T> a2, final FieldDerivativeStructure<T> b2, final FieldDerivativeStructure<T> a3, final FieldDerivativeStructure<T> b3, final FieldDerivativeStructure<T> a4, final FieldDerivativeStructure<T> b4) throws MathIllegalArgumentException {
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
     * @param a4 first factor of the third term
     * @param b4 second factor of the third term
     * @return a<sub>1</sub>&times;b<sub>1</sub> +
     * a<sub>2</sub>&times;b<sub>2</sub> + a<sub>3</sub>&times;b<sub>3</sub> +
     * a<sub>4</sub>&times;b<sub>4</sub>
     * @see #linearCombination(double, FieldDerivativeStructure, double, FieldDerivativeStructure)
     * @see #linearCombination(double, FieldDerivativeStructure, double, FieldDerivativeStructure, double, FieldDerivativeStructure)
     * @exception MathIllegalArgumentException if number of free parameters or orders are inconsistent
     */
    public FieldDerivativeStructure<T> linearCombination(final T a1, final FieldDerivativeStructure<T> b1, final T a2, final FieldDerivativeStructure<T> b2, final T a3, final FieldDerivativeStructure<T> b3, final T a4, final FieldDerivativeStructure<T> b4) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * @exception MathIllegalArgumentException if number of free parameters
     * or orders do not match
     */
    @Override
    public FieldDerivativeStructure<T> linearCombination(final double a1, final FieldDerivativeStructure<T> b1, final double a2, final FieldDerivativeStructure<T> b2, final double a3, final FieldDerivativeStructure<T> b3, final double a4, final FieldDerivativeStructure<T> b4) throws MathIllegalArgumentException {
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
    public FieldDerivativeStructure<T> getPi() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSmall(final FieldDerivativeStructure<T> base, final double relativeThreshold) {
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
