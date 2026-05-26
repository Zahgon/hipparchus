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
package org.hipparchus.util;

import java.util.Arrays;
import org.hipparchus.CalculusFieldElement;
import org.hipparchus.Field;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;

/**
 * This class allows to perform the same computation of all components of a Tuple at once.
 * @param <T> the type of the field elements
 * @since 1.2
 */
public class FieldTuple<T extends CalculusFieldElement<T>> implements CalculusFieldElement<FieldTuple<T>> {

    /**
     * Components of the tuple.
     */
    private final T[] values;

    /**
     * Field the instance belongs to.
     */
    private final FieldTupleField<T> field;

    /**
     * Creates a new instance from its components.
     * @param x components of the tuple
     */
    @SafeVarargs
    public FieldTuple(final T... x) {
        this(new FieldTupleField<>(x[0].getField(), x.length), x.clone());
    }

    /**
     * Creates a new instance from its components.
     * @param field field the instance belongs to
     * @param x components of the tuple (beware, it is <em>not</em> copied, it is shared with caller)
     */
    private FieldTuple(final FieldTupleField<T> field, final T[] x) {
        // NOPMD - storing user-supplied array is intentional and documented here
        this.values = x;
        this.field = field;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> newInstance(final double value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the dimension of the tuple.
     * @return dimension of the tuple
     */
    public int getDimension() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get one component of the tuple.
     * @param index index of the component, between 0 and {@link #getDimension() getDimension()} - 1
     * @return value of the component
     */
    public T getComponent(final int index) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get all components of the tuple.
     * @return all components
     */
    public T[] getComponents() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<FieldTuple<T>> getField() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> add(final FieldTuple<T> a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> subtract(final FieldTuple<T> a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> negate() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> multiply(final FieldTuple<T> a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> multiply(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> divide(final FieldTuple<T> a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> reciprocal() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        throw new UnsupportedOperationException("STUB: not implemented");
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
    public double getReal() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> getAddendum() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> add(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> subtract(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> multiply(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> square() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> divide(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> remainder(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> remainder(final FieldTuple<T> a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> abs() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> ceil() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> floor() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> rint() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> sign() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> copySign(final FieldTuple<T> sign) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> copySign(final double sign) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> scalb(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> ulp() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> hypot(final FieldTuple<T> y) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> sqrt() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> cbrt() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> rootN(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> pow(final double p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> pow(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> pow(final FieldTuple<T> e) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> exp() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> expm1() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> log() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> log1p() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> log10() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> cos() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> sin() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldSinCos<FieldTuple<T>> sinCos() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> tan() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> acos() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> asin() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> atan() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> atan2(final FieldTuple<T> x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> cosh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> sinh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldSinhCosh<FieldTuple<T>> sinhCosh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> tanh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> acosh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> asinh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> atanh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> toDegrees() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> toRadians() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> linearCombination(final FieldTuple<T>[] a, final FieldTuple<T>[] b) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> linearCombination(final double[] a, final FieldTuple<T>[] b) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> linearCombination(final FieldTuple<T> a1, final FieldTuple<T> b1, final FieldTuple<T> a2, final FieldTuple<T> b2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> linearCombination(final double a1, final FieldTuple<T> b1, final double a2, final FieldTuple<T> b2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> linearCombination(final FieldTuple<T> a1, final FieldTuple<T> b1, final FieldTuple<T> a2, final FieldTuple<T> b2, final FieldTuple<T> a3, final FieldTuple<T> b3) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> linearCombination(final double a1, final FieldTuple<T> b1, final double a2, final FieldTuple<T> b2, final double a3, final FieldTuple<T> b3) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> linearCombination(final FieldTuple<T> a1, final FieldTuple<T> b1, final FieldTuple<T> a2, final FieldTuple<T> b2, final FieldTuple<T> a3, final FieldTuple<T> b3, final FieldTuple<T> a4, final FieldTuple<T> b4) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldTuple<T> linearCombination(final double a1, final FieldTuple<T> b1, final double a2, final FieldTuple<T> b2, final double a3, final FieldTuple<T> b3, final double a4, final FieldTuple<T> b4) {
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
    public FieldTuple<T> getPi() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSmall(final FieldTuple<T> base, final double relativeThreshold) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Field for {link FieldTuple} instances.
     * @param <T> the type of the field elements
     */
    private static class FieldTupleField<T extends CalculusFieldElement<T>> implements Field<FieldTuple<T>> {

        /**
         * Constant function evaluating to 0.0.
         */
        private final FieldTuple<T> zero;

        /**
         * Constant function evaluating to 1.0.
         */
        private final FieldTuple<T> one;

        /**
         * Simple constructor.
         * @param field field to which the elements belong
         * @param dimension dimension of the tuple
         */
        FieldTupleField(final Field<T> field, final int dimension) {
            final T[] zeroData = MathArrays.buildArray(field, dimension);
            Arrays.fill(zeroData, field.getZero());
            final T[] oneData = MathArrays.buildArray(field, dimension);
            Arrays.fill(oneData, field.getOne());
            this.zero = new FieldTuple<>(this, zeroData);
            this.one = new FieldTuple<>(this, oneData);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public FieldTuple<T> getZero() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public FieldTuple<T> getOne() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @SuppressWarnings("unchecked")
        @Override
        public Class<FieldTuple<T>> getRuntimeClass() {
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
    }
}
