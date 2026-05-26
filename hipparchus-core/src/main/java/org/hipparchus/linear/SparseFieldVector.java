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
import org.hipparchus.Field;
import org.hipparchus.FieldElement;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.MathRuntimeException;
import org.hipparchus.exception.NullArgumentException;
import org.hipparchus.util.MathArrays;
import org.hipparchus.util.MathUtils;
import org.hipparchus.util.OpenIntToFieldHashMap;

/**
 * This class implements the {@link FieldVector} interface with a {@link OpenIntToFieldHashMap} backing store.
 * <p>
 *  Caveat: This implementation assumes that, for any {@code x},
 *  the equality {@code x * 0d == 0d} holds. But it is is not true for
 *  {@code NaN}. Moreover, zero entries will lose their sign.
 *  Some operations (that involve {@code NaN} and/or infinities) may
 *  thus give incorrect results.
 * </p>
 * @param <T> the type of the field elements
 */
public class SparseFieldVector<T extends FieldElement<T>> implements FieldVector<T>, Serializable {

    /**
     *  Serialization identifier.
     */
    private static final long serialVersionUID = 7841233292190413362L;

    /**
     * Field to which the elements belong.
     */
    private final Field<T> field;

    /**
     * Entries of the vector.
     */
    private final OpenIntToFieldHashMap<T> entries;

    /**
     * Dimension of the vector.
     */
    private final int virtualSize;

    /**
     * Build a 0-length vector.
     * Zero-length vectors may be used to initialize construction of vectors
     * by data gathering. We start with zero-length and use either the {@link
     * #SparseFieldVector(SparseFieldVector, int)} constructor
     * or one of the {@code append} method ({@link #append(FieldVector)} or
     * {@link #append(SparseFieldVector)}) to gather data into this vector.
     *
     * @param field Field to which the elements belong.
     */
    public SparseFieldVector(Field<T> field) {
        this(field, 0);
    }

    /**
     * Construct a vector of zeroes.
     *
     * @param field Field to which the elements belong.
     * @param dimension Size of the vector.
     */
    public SparseFieldVector(Field<T> field, int dimension) {
        this.field = field;
        virtualSize = dimension;
        entries = new OpenIntToFieldHashMap<>(field);
    }

    /**
     * Build a resized vector, for use with append.
     *
     * @param v Original vector
     * @param resize Amount to add.
     */
    protected SparseFieldVector(SparseFieldVector<T> v, int resize) {
        field = v.field;
        virtualSize = v.getDimension() + resize;
        entries = new OpenIntToFieldHashMap<>(v.entries);
    }

    /**
     * Build a vector with known the sparseness (for advanced use only).
     *
     * @param field Field to which the elements belong.
     * @param dimension Size of the vector.
     * @param expectedSize Expected number of non-zero entries.
     */
    public SparseFieldVector(Field<T> field, int dimension, int expectedSize) {
        this.field = field;
        virtualSize = dimension;
        entries = new OpenIntToFieldHashMap<>(field, expectedSize);
    }

    /**
     * Create from a Field array.
     * Only non-zero entries will be stored.
     *
     * @param field Field to which the elements belong.
     * @param values Set of values to create from.
     * @exception NullArgumentException if values is null
     */
    public SparseFieldVector(Field<T> field, T[] values) throws NullArgumentException {
        MathUtils.checkNotNull(values);
        this.field = field;
        virtualSize = values.length;
        entries = new OpenIntToFieldHashMap<>(field);
        for (int key = 0; key < values.length; key++) {
            T value = values[key];
            entries.put(key, value);
        }
    }

    /**
     * Copy constructor.
     *
     * @param v Instance to copy.
     */
    public SparseFieldVector(SparseFieldVector<T> v) {
        field = v.field;
        virtualSize = v.getDimension();
        entries = new OpenIntToFieldHashMap<>(v.getEntries());
    }

    /**
     * Get the entries of this instance.
     *
     * @return the entries of this instance
     */
    private OpenIntToFieldHashMap<T> getEntries() {
        return entries;
    }

    /**
     * Optimized method to add sparse vectors.
     *
     * @param v Vector to add.
     * @return {@code this + v}.
     * @throws MathIllegalArgumentException if {@code v} is not the same size as
     * {@code this}.
     */
    public FieldVector<T> add(SparseFieldVector<T> v) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Construct a vector by appending a vector to this vector.
     *
     * @param v Vector to append to this one.
     * @return a new vector.
     */
    public FieldVector<T> append(SparseFieldVector<T> v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldVector<T> append(FieldVector<T> v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * @exception NullArgumentException if d is null
     */
    @Override
    public FieldVector<T> append(T d) throws NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldVector<T> copy() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T dotProduct(FieldVector<T> v) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldVector<T> ebeDivide(FieldVector<T> v) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldVector<T> ebeMultiply(FieldVector<T> v) throws MathIllegalArgumentException {
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
     * {@inheritDoc}
     */
    @Override
    public T getEntry(int index) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<T> getField() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldVector<T> getSubVector(int index, int n) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldVector<T> mapAdd(T d) throws NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldVector<T> mapAddToSelf(T d) throws NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldVector<T> mapDivide(T d) throws NullArgumentException, MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldVector<T> mapDivideToSelf(T d) throws NullArgumentException, MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldVector<T> mapInv() throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldVector<T> mapInvToSelf() throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldVector<T> mapMultiply(T d) throws NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldVector<T> mapMultiplyToSelf(T d) throws NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldVector<T> mapSubtract(T d) throws NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldVector<T> mapSubtractToSelf(T d) throws NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Optimized method to compute outer product when both vectors are sparse.
     * @param v vector with which outer product should be computed
     * @return the matrix outer product between instance and v
     */
    public FieldMatrix<T> outerProduct(SparseFieldVector<T> v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldMatrix<T> outerProduct(FieldVector<T> v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldVector<T> projection(FieldVector<T> v) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * @exception NullArgumentException if value is null
     */
    @Override
    public void set(T value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * @exception NullArgumentException if value is null
     */
    @Override
    public void setEntry(int index, T value) throws MathIllegalArgumentException, NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSubVector(int index, FieldVector<T> v) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Optimized method to compute {@code this} minus {@code v}.
     * @param v vector to be subtracted
     * @return {@code this - v}
     * @throws MathIllegalArgumentException if {@code v} is not the same size as
     * {@code this}.
     */
    public SparseFieldVector<T> subtract(SparseFieldVector<T> v) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldVector<T> subtract(FieldVector<T> v) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T[] toArray() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check whether an index is valid.
     *
     * @param index Index to check.
     * @throws MathIllegalArgumentException if the index is not valid.
     */
    private void checkIndex(final int index) throws MathIllegalArgumentException {
        MathUtils.checkRangeInclusive(index, 0, getDimension() - 1);
    }

    /**
     * Checks that the indices of a subvector are valid.
     *
     * @param start the index of the first entry of the subvector
     * @param end the index of the last entry of the subvector (inclusive)
     * @throws MathIllegalArgumentException if {@code start} of {@code end} are not valid
     * @throws MathIllegalArgumentException if {@code end < start}
     */
    private void checkIndices(final int start, final int end) throws MathIllegalArgumentException {
        final int dim = getDimension();
        if ((start < 0) || (start >= dim)) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.INDEX, start, 0, dim - 1);
        }
        if ((end < 0) || (end >= dim)) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.INDEX, end, 0, dim - 1);
        }
        if (end < start) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.INITIAL_ROW_AFTER_FINAL_ROW, end, start, false);
        }
    }

    /**
     * Check if instance dimension is equal to some expected value.
     *
     * @param n Expected dimension.
     * @throws MathIllegalArgumentException if the dimensions do not match.
     */
    protected void checkVectorDimensions(int n) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldVector<T> add(FieldVector<T> v) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Visits (but does not alter) all entries of this vector in default order
     * (increasing index).
     *
     * @param visitor the visitor to be used to process the entries of this
     * vector
     * @return the value returned by {@link FieldVectorPreservingVisitor#end()}
     * at the end of the walk
     */
    public T walkInDefaultOrder(final FieldVectorPreservingVisitor<T> visitor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Visits (but does not alter) some entries of this vector in default order
     * (increasing index).
     *
     * @param visitor visitor to be used to process the entries of this vector
     * @param start the index of the first entry to be visited
     * @param end the index of the last entry to be visited (inclusive)
     * @return the value returned by {@link FieldVectorPreservingVisitor#end()}
     * at the end of the walk
     * @throws MathIllegalArgumentException if {@code end < start}.
     * @throws MathIllegalArgumentException if the indices are not valid.
     */
    public T walkInDefaultOrder(final FieldVectorPreservingVisitor<T> visitor, final int start, final int end) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Visits (but does not alter) all entries of this vector in optimized
     * order. The order in which the entries are visited is selected so as to
     * lead to the most efficient implementation; it might depend on the
     * concrete implementation of this abstract class.
     *
     * @param visitor the visitor to be used to process the entries of this
     * vector
     * @return the value returned by {@link FieldVectorPreservingVisitor#end()}
     * at the end of the walk
     */
    public T walkInOptimizedOrder(final FieldVectorPreservingVisitor<T> visitor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Visits (but does not alter) some entries of this vector in optimized
     * order. The order in which the entries are visited is selected so as to
     * lead to the most efficient implementation; it might depend on the
     * concrete implementation of this abstract class.
     *
     * @param visitor visitor to be used to process the entries of this vector
     * @param start the index of the first entry to be visited
     * @param end the index of the last entry to be visited (inclusive)
     * @return the value returned by {@link FieldVectorPreservingVisitor#end()}
     * at the end of the walk
     * @throws MathIllegalArgumentException if {@code end < start}.
     * @throws MathIllegalArgumentException if the indices are not valid.
     */
    public T walkInOptimizedOrder(final FieldVectorPreservingVisitor<T> visitor, final int start, final int end) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Visits (and possibly alters) all entries of this vector in default order
     * (increasing index).
     *
     * @param visitor the visitor to be used to process and modify the entries
     * of this vector
     * @return the value returned by {@link FieldVectorChangingVisitor#end()}
     * at the end of the walk
     */
    public T walkInDefaultOrder(final FieldVectorChangingVisitor<T> visitor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Visits (and possibly alters) some entries of this vector in default order
     * (increasing index).
     *
     * @param visitor visitor to be used to process the entries of this vector
     * @param start the index of the first entry to be visited
     * @param end the index of the last entry to be visited (inclusive)
     * @return the value returned by {@link FieldVectorChangingVisitor#end()}
     * at the end of the walk
     * @throws MathIllegalArgumentException if {@code end < start}.
     * @throws MathIllegalArgumentException if the indices are not valid.
     */
    public T walkInDefaultOrder(final FieldVectorChangingVisitor<T> visitor, final int start, final int end) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Visits (and possibly alters) all entries of this vector in optimized
     * order. The order in which the entries are visited is selected so as to
     * lead to the most efficient implementation; it might depend on the
     * concrete implementation of this abstract class.
     *
     * @param visitor the visitor to be used to process the entries of this
     * vector
     * @return the value returned by {@link FieldVectorChangingVisitor#end()}
     * at the end of the walk
     */
    public T walkInOptimizedOrder(final FieldVectorChangingVisitor<T> visitor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Visits (and possibly change) some entries of this vector in optimized
     * order. The order in which the entries are visited is selected so as to
     * lead to the most efficient implementation; it might depend on the
     * concrete implementation of this abstract class.
     *
     * @param visitor visitor to be used to process the entries of this vector
     * @param start the index of the first entry to be visited
     * @param end the index of the last entry to be visited (inclusive)
     * @return the value returned by {@link FieldVectorChangingVisitor#end()}
     * at the end of the walk
     * @throws MathIllegalArgumentException if {@code end < start}.
     * @throws MathIllegalArgumentException if the indices are not valid.
     */
    public T walkInOptimizedOrder(final FieldVectorChangingVisitor<T> visitor, final int start, final int end) throws MathIllegalArgumentException {
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
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
