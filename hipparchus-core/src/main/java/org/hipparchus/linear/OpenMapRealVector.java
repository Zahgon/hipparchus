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
package org.hipparchus.linear;

import java.io.Serializable;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.MathRuntimeException;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.OpenIntToDoubleHashMap;
import org.hipparchus.util.OpenIntToDoubleHashMap.Iterator;

/**
 * This class implements the {@link RealVector} interface with a
 * {@link OpenIntToDoubleHashMap} backing store.
 * <p>
 *  Caveat: This implementation assumes that, for any {@code x},
 *  the equality {@code x * 0d == 0d} holds. But it is is not true for
 *  {@code NaN}. Moreover, zero entries will lose their sign.
 *  Some operations (that involve {@code NaN} and/or infinities) may
 *  thus give incorrect results, like multiplications, divisions or
 *  functions mapping.
 * </p>
 */
public class OpenMapRealVector extends SparseRealVector implements Serializable {

    /**
     * Default Tolerance for having a value considered zero.
     */
    public static final double DEFAULT_ZERO_TOLERANCE = 1.0e-12;

    /**
     * Serializable version identifier.
     */
    private static final long serialVersionUID = 8772222695580707260L;

    /**
     * Entries of the vector.
     */
    private final OpenIntToDoubleHashMap entries;

    /**
     * Dimension of the vector.
     */
    private final int virtualSize;

    /**
     * Tolerance for having a value considered zero.
     */
    private final double epsilon;

    /**
     * Build a 0-length vector.
     * Zero-length vectors may be used to initialized construction of vectors
     * by data gathering. We start with zero-length and use either the {@link
     * #OpenMapRealVector(OpenMapRealVector, int)} constructor
     * or one of the {@code append} method ({@link #append(double)},
     * {@link #append(RealVector)}) to gather data into this vector.
     */
    public OpenMapRealVector() {
        this(0, DEFAULT_ZERO_TOLERANCE);
    }

    /**
     * Construct a vector of zeroes.
     *
     * @param dimension Size of the vector.
     */
    public OpenMapRealVector(int dimension) {
        this(dimension, DEFAULT_ZERO_TOLERANCE);
    }

    /**
     * Construct a vector of zeroes, specifying zero tolerance.
     *
     * @param dimension Size of the vector.
     * @param epsilon Tolerance below which a value considered zero.
     */
    public OpenMapRealVector(int dimension, double epsilon) {
        virtualSize = dimension;
        entries = new OpenIntToDoubleHashMap(0.0);
        this.epsilon = epsilon;
    }

    /**
     * Build a resized vector, for use with append.
     *
     * @param v Original vector.
     * @param resize Amount to add.
     */
    protected OpenMapRealVector(OpenMapRealVector v, int resize) {
        virtualSize = v.getDimension() + resize;
        entries = new OpenIntToDoubleHashMap(v.entries);
        epsilon = v.epsilon;
    }

    /**
     * Build a vector with known the sparseness (for advanced use only).
     *
     * @param dimension Size of the vector.
     * @param expectedSize The expected number of non-zero entries.
     */
    public OpenMapRealVector(int dimension, int expectedSize) {
        this(dimension, expectedSize, DEFAULT_ZERO_TOLERANCE);
    }

    /**
     * Build a vector with known the sparseness and zero tolerance
     * setting (for advanced use only).
     *
     * @param dimension Size of the vector.
     * @param expectedSize Expected number of non-zero entries.
     * @param epsilon Tolerance below which a value is considered zero.
     */
    public OpenMapRealVector(int dimension, int expectedSize, double epsilon) {
        virtualSize = dimension;
        entries = new OpenIntToDoubleHashMap(expectedSize, 0.0);
        this.epsilon = epsilon;
    }

    /**
     * Create from an array.
     * Only non-zero entries will be stored.
     *
     * @param values Set of values to create from.
     */
    public OpenMapRealVector(double[] values) {
        this(values, DEFAULT_ZERO_TOLERANCE);
    }

    /**
     * Create from an array, specifying zero tolerance.
     * Only non-zero entries will be stored.
     *
     * @param values Set of values to create from.
     * @param epsilon Tolerance below which a value is considered zero.
     */
    public OpenMapRealVector(double[] values, double epsilon) {
        virtualSize = values.length;
        entries = new OpenIntToDoubleHashMap(0.0);
        this.epsilon = epsilon;
        for (int key = 0; key < values.length; key++) {
            double value = values[key];
            if (!isDefaultValue(value)) {
                entries.put(key, value);
            }
        }
    }

    /**
     * Create from an array.
     * Only non-zero entries will be stored.
     *
     * @param values The set of values to create from
     */
    public OpenMapRealVector(Double[] values) {
        this(values, DEFAULT_ZERO_TOLERANCE);
    }

    /**
     * Create from an array.
     * Only non-zero entries will be stored.
     *
     * @param values Set of values to create from.
     * @param epsilon Tolerance below which a value is considered zero.
     */
    public OpenMapRealVector(Double[] values, double epsilon) {
        virtualSize = values.length;
        entries = new OpenIntToDoubleHashMap(0.0);
        this.epsilon = epsilon;
        for (int key = 0; key < values.length; key++) {
            double value = values[key];
            if (!isDefaultValue(value)) {
                entries.put(key, value);
            }
        }
    }

    /**
     * Copy constructor.
     *
     * @param v Instance to copy from.
     */
    public OpenMapRealVector(OpenMapRealVector v) {
        virtualSize = v.getDimension();
        entries = new OpenIntToDoubleHashMap(v.getEntries());
        epsilon = v.epsilon;
    }

    /**
     * Generic copy constructor.
     *
     * @param v Instance to copy from.
     */
    public OpenMapRealVector(RealVector v) {
        virtualSize = v.getDimension();
        entries = new OpenIntToDoubleHashMap(0.0);
        epsilon = DEFAULT_ZERO_TOLERANCE;
        for (int key = 0; key < virtualSize; key++) {
            double value = v.getEntry(key);
            if (!isDefaultValue(value)) {
                entries.put(key, value);
            }
        }
    }

    /**
     * Get the entries of this instance.
     *
     * @return the entries of this instance.
     */
    private OpenIntToDoubleHashMap getEntries() {
        return entries;
    }

    /**
     * Determine if this value is within epsilon of zero.
     *
     * @param value Value to test
     * @return {@code true} if this value is within epsilon to zero,
     * {@code false} otherwise.
     */
    protected boolean isDefaultValue(double value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RealVector add(RealVector v) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Optimized method to add two OpenMapRealVectors.
     * It copies the larger vector, then iterates over the smaller.
     *
     * @param v Vector to add.
     * @return the sum of {@code this} and {@code v}.
     * @throws MathIllegalArgumentException if the dimensions do not match.
     */
    public OpenMapRealVector add(OpenMapRealVector v) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Optimized method to append a OpenMapRealVector.
     * @param v vector to append
     * @return The result of appending {@code v} to self
     */
    public OpenMapRealVector append(OpenMapRealVector v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OpenMapRealVector append(RealVector v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OpenMapRealVector append(double d) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OpenMapRealVector copy() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OpenMapRealVector ebeDivide(RealVector v) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OpenMapRealVector ebeMultiply(RealVector v) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OpenMapRealVector getSubVector(int index, int n) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDimension() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Optimized method to compute distance.
     *
     * @param v Vector to compute distance to.
     * @return the distance from {@code this} and {@code v}.
     * @throws MathIllegalArgumentException if the dimensions do not match.
     */
    public double getDistance(OpenMapRealVector v) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getDistance(RealVector v) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getEntry(int index) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Distance between two vectors.
     * This method computes the distance consistent with
     * L<sub>1</sub> norm, i.e. the sum of the absolute values of
     * elements differences.
     *
     * @param v Vector to which distance is requested.
     * @return distance between this vector and {@code v}.
     * @throws MathIllegalArgumentException if the dimensions do not match.
     */
    public double getL1Distance(OpenMapRealVector v) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getL1Distance(RealVector v) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Optimized method to compute LInfDistance.
     *
     * @param v Vector to compute distance from.
     * @return the LInfDistance.
     * @throws MathIllegalArgumentException if the dimensions do not match.
     */
    private double getLInfDistance(OpenMapRealVector v) throws MathIllegalArgumentException {
        checkVectorDimensions(v.getDimension());
        double max = 0;
        Iterator iter = entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            double delta = FastMath.abs(iter.value() - v.getEntry(iter.key()));
            if (delta > max) {
                max = delta;
            }
        }
        iter = v.getEntries().iterator();
        while (iter.hasNext()) {
            iter.advance();
            int key = iter.key();
            if (!entries.containsKey(key) && iter.value() > max) {
                max = iter.value();
            }
        }
        return max;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getLInfDistance(RealVector v) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInfinite() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNaN() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OpenMapRealVector mapAdd(double d) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OpenMapRealVector mapAddToSelf(double d) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setEntry(int index, double value) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSubVector(int index, RealVector v) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(double value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Optimized method to subtract OpenMapRealVectors.
     *
     * @param v Vector to subtract from {@code this}.
     * @return the difference of {@code this} and {@code v}.
     * @throws MathIllegalArgumentException if the dimensions do not match.
     */
    public OpenMapRealVector subtract(OpenMapRealVector v) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RealVector subtract(RealVector v) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OpenMapRealVector unitVector() throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unitize() throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double[] toArray() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * Implementation Note: This works on exact values, and as a result
     * it is possible for {@code a.subtract(b)} to be the zero vector, while
     * {@code a.hashCode() != b.hashCode()}.
     */
    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * Implementation Note: This performs an exact comparison, and as a result
     * it is possible for {@code a.subtract(b}} to be the zero vector, while
     * {@code  a.equals(b) == false}.
     */
    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get percentage of none zero elements as a decimal percent.
     * @return the percentage of none zero elements as a decimal percent
     */
    public double getSparsity() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public java.util.Iterator<Entry> sparseIterator() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Implementation of {@code Entry} optimized for OpenMap.
     * This implementation does not allow arbitrary calls to {@code setIndex}
     * since the order in which entries are returned is undefined.
     */
    protected class OpenMapEntry extends Entry {

        /**
         * Iterator pointing to the entry.
         */
        private final Iterator iter;

        /**
         * Build an entry from an iterator point to an element.
         *
         * @param iter Iterator pointing to the entry.
         */
        protected OpenMapEntry(Iterator iter) {
            this.iter = iter;
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
        public void setValue(double value) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getIndex() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Iterator class to do iteration over just the non-zero elements.
     * This implementation is fail-fast, so cannot be used to modify
     * any zero element.
     */
    protected class OpenMapSparseIterator implements java.util.Iterator<Entry> {

        /**
         * Underlying iterator.
         */
        private final Iterator iter;

        /**
         * Current entry.
         */
        private final Entry current;

        /**
         * Simple constructor.
         */
        protected OpenMapSparseIterator() {
            iter = entries.iterator();
            current = new OpenMapEntry(iter);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Entry next() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
