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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.hipparchus.Field;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.MathArrays;
import org.hipparchus.util.MathUtils;
import org.hipparchus.util.Precision;

/**
 * First derivative computation with large number of variables.
 * <p>
 * This class plays a similar role to {@link DerivativeStructure}, with
 * a focus on efficiency when dealing with large number of independent variables
 * and most computation depend only on a few of them, and when only first derivative
 * is desired. When these conditions are met, this class should be much faster than
 * {@link DerivativeStructure} and use less memory.
 * </p>
 */
public class SparseGradient implements Derivative1<SparseGradient>, Serializable {

    /**
     * Serializable UID.
     */
    private static final long serialVersionUID = 20131025L;

    /**
     * Value of the calculation.
     */
    private double value;

    /**
     * Stored derivative, each key representing a different independent variable.
     */
    private final Map<Integer, Double> derivatives;

    /**
     * Internal constructor.
     * @param value value of the function
     * @param derivatives derivatives map, a deep copy will be performed,
     * so the map given here will remain safe from changes in the new instance,
     * may be null to create an empty derivatives map, i.e. a constant value
     */
    private SparseGradient(final double value, final Map<Integer, Double> derivatives) {
        this.value = value;
        this.derivatives = new HashMap<>();
        if (derivatives != null) {
            this.derivatives.putAll(derivatives);
        }
    }

    /**
     * Internal constructor.
     * @param value value of the function
     * @param scale scaling factor to apply to all derivatives
     * @param derivatives derivatives map, a deep copy will be performed,
     * so the map given here will remain safe from changes in the new instance,
     * may be null to create an empty derivatives map, i.e. a constant value
     */
    private SparseGradient(final double value, final double scale, final Map<Integer, Double> derivatives) {
        this.value = value;
        this.derivatives = new HashMap<>();
        if (derivatives != null) {
            for (final Map.Entry<Integer, Double> entry : derivatives.entrySet()) {
                this.derivatives.put(entry.getKey(), scale * entry.getValue());
            }
        }
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
    public double getPartialDerivative(int... orders) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SparseGradient newInstance(final double v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SparseGradient withValue(final double v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Factory method creating a constant.
     * @param value value of the constant
     * @return a new instance
     */
    public static SparseGradient createConstant(final double value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Factory method creating an independent variable.
     * @param idx index of the variable
     * @param value value of the variable
     * @return a new instance
     */
    public static SparseGradient createVariable(final int idx, final double value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the derivative with respect to a particular index variable.
     *
     * @param index index to differentiate with.
     * @return derivative with respect to a particular index variable
     */
    public double getDerivative(final int index) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SparseGradient getAddendum() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the value of the function.
     * @return value of the function.
     */
    @Override
    public double getValue() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SparseGradient add(final SparseGradient a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Add in place.
     * <p>
     * This method is designed to be faster when used multiple times in a loop.
     * </p>
     * <p>
     * The instance is changed here, in order to not change the
     * instance the {@link #add(SparseGradient)} method should
     * be used.
     * </p>
     * @param a instance to add
     */
    public void addInPlace(final SparseGradient a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SparseGradient subtract(final SparseGradient a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SparseGradient multiply(final SparseGradient a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Multiply in place.
     * <p>
     * This method is designed to be faster when used multiple times in a loop.
     * </p>
     * <p>
     * The instance is changed here, in order to not change the
     * instance the {@link #add(SparseGradient)} method should
     * be used.
     * </p>
     * @param a instance to multiply
     */
    public void multiplyInPlace(final SparseGradient a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SparseGradient multiply(final double c) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SparseGradient multiply(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SparseGradient divide(final SparseGradient a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SparseGradient divide(final double c) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SparseGradient negate() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<SparseGradient> getField() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Local field for sparse gradient.
     */
    private static class SparseGradientField implements Field<SparseGradient> {

        /**
         * Zero constant.
         */
        private final SparseGradient zero;

        /**
         * One constant.
         */
        private final SparseGradient one;

        /**
         * Private constructor for the singleton.
         */
        private SparseGradientField() {
            zero = createConstant(0);
            one = createConstant(1);
        }

        /**
         * Get the unique instance.
         * @return the unique instance
         */
        public static SparseGradientField getInstance() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public SparseGradient getZero() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public SparseGradient getOne() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Class<SparseGradient> getRuntimeClass() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(final Object other) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        // CHECKSTYLE: stop HideUtilityClassConstructor
        /**
         * Holder for the instance.
         * <p>We use here the Initialization On Demand Holder Idiom.</p>
         */
        private static class LazyHolder {

            /**
             * Cached field instance.
             */
            private static final SparseGradientField INSTANCE = new SparseGradientField();
        }
        // CHECKSTYLE: resume HideUtilityClassConstructor
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SparseGradient remainder(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SparseGradient remainder(final SparseGradient a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SparseGradient abs() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SparseGradient copySign(final SparseGradient sign) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SparseGradient copySign(final double sign) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SparseGradient scalb(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SparseGradient hypot(final SparseGradient y) {
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
     */
    public static SparseGradient hypot(final SparseGradient x, final SparseGradient y) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SparseGradient sqrt() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SparseGradient pow(final double p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SparseGradient pow(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute a<sup>x</sup> where a is a double and x a {@link SparseGradient}
     * @param a number to exponentiate
     * @param x power to apply
     * @return a<sup>x</sup>
     */
    public static SparseGradient pow(final double a, final SparseGradient x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SparseGradient atan2(final SparseGradient x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Two arguments arc tangent operation.
     * @param y first argument of the arc tangent
     * @param x second argument of the arc tangent
     * @return atan2(y, x)
     */
    public static SparseGradient atan2(final SparseGradient y, final SparseGradient x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SparseGradient toDegrees() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SparseGradient toRadians() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Evaluate Taylor expansion of a sparse gradient.
     * @param delta parameters offsets (&Delta;x, &Delta;y, ...)
     * @return value of the Taylor expansion at x + &Delta;x, y + &Delta;y, ...
     */
    public double taylor(final double... delta) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute composition of the instance by a univariate function.
     * @param f array of value and derivatives of the function at
     * the current point (i.e. [f({@link #getValue()}),
     * f'({@link #getValue()}), f''({@link #getValue()})...]).
     * @return f(this)
     * @exception MathIllegalArgumentException if the number of elements
     * in the array is not equal to 2 (i.e. value and first derivative)
     */
    @Override
    public SparseGradient compose(final double... f) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SparseGradient compose(final double f0, final double f1) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SparseGradient linearCombination(final SparseGradient[] a, final SparseGradient[] b) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SparseGradient linearCombination(final double[] a, final SparseGradient[] b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SparseGradient linearCombination(final SparseGradient a1, final SparseGradient b1, final SparseGradient a2, final SparseGradient b2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SparseGradient linearCombination(final double a1, final SparseGradient b1, final double a2, final SparseGradient b2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SparseGradient linearCombination(final SparseGradient a1, final SparseGradient b1, final SparseGradient a2, final SparseGradient b2, final SparseGradient a3, final SparseGradient b3) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SparseGradient linearCombination(final double a1, final SparseGradient b1, final double a2, final SparseGradient b2, final double a3, final SparseGradient b3) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SparseGradient linearCombination(final SparseGradient a1, final SparseGradient b1, final SparseGradient a2, final SparseGradient b2, final SparseGradient a3, final SparseGradient b3, final SparseGradient a4, final SparseGradient b4) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SparseGradient linearCombination(final double a1, final SparseGradient b1, final double a2, final SparseGradient b2, final double a3, final SparseGradient b3, final double a4, final SparseGradient b4) {
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
    public SparseGradient getPi() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSmall(final SparseGradient base, final double relativeThreshold) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Test for the equality of two sparse gradients.
     * <p>
     * Sparse gradients are considered equal if they have the same value
     * and the same derivatives.
     * </p>
     * @param other Object to test for equality to this
     * @return true if two sparse gradients are equal
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
