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

import org.hipparchus.Field;
import org.hipparchus.FieldElement;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.util.OpenIntToFieldHashMap;

/**
 * Sparse matrix implementation based on an open addressed map.
 *
 * <p>
 *  Caveat: This implementation assumes that, for any {@code x},
 *  the equality {@code x * 0d == 0d} holds. But it is is not true for
 *  {@code NaN}. Moreover, zero entries will lose their sign.
 *  Some operations (that involve {@code NaN} and/or infinities) may
 *  thus give incorrect results.
 * </p>
 * @param <T> the type of the field elements
 */
public class SparseFieldMatrix<T extends FieldElement<T>> extends AbstractFieldMatrix<T> {

    /**
     * Storage for (sparse) matrix elements.
     */
    private final OpenIntToFieldHashMap<T> entries;

    /**
     * Row dimension.
     */
    private final int rows;

    /**
     * Column dimension.
     */
    private final int columns;

    /**
     * Create a matrix with no data.
     *
     * @param field Field to which the elements belong.
     */
    public SparseFieldMatrix(final Field<T> field) {
        super(field);
        rows = 0;
        columns = 0;
        entries = new OpenIntToFieldHashMap<>(field);
    }

    /**
     * Create a new {@link SparseFieldMatrix} with the supplied row and column
     * dimensions.
     *
     * @param field Field to which the elements belong.
     * @param rowDimension Number of rows in the new matrix.
     * @param columnDimension Number of columns in the new matrix.
     * @throws org.hipparchus.exception.MathIllegalArgumentException
     * if row or column dimension is not positive.
     */
    public SparseFieldMatrix(final Field<T> field, final int rowDimension, final int columnDimension) {
        super(field, rowDimension, columnDimension);
        this.rows = rowDimension;
        this.columns = columnDimension;
        entries = new OpenIntToFieldHashMap<>(field);
    }

    /**
     * Copy constructor.
     *
     * @param other Instance to copy.
     */
    public SparseFieldMatrix(SparseFieldMatrix<T> other) {
        super(other.getField(), other.getRowDimension(), other.getColumnDimension());
        rows = other.getRowDimension();
        columns = other.getColumnDimension();
        entries = new OpenIntToFieldHashMap<>(other.entries);
    }

    /**
     * Generic copy constructor.
     *
     * @param other Instance to copy.
     */
    public SparseFieldMatrix(FieldMatrix<T> other) {
        super(other.getField(), other.getRowDimension(), other.getColumnDimension());
        rows = other.getRowDimension();
        columns = other.getColumnDimension();
        entries = new OpenIntToFieldHashMap<>(getField());
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                setEntry(i, j, other.getEntry(i, j));
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addToEntry(int row, int column, T increment) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldMatrix<T> copy() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldMatrix<T> createMatrix(int rowDimension, int columnDimension) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getColumnDimension() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getEntry(int row, int column) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRowDimension() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void multiplyEntry(int row, int column, T factor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setEntry(int row, int column, T value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * @throws MathIllegalArgumentException if {@code m} is an
     * {@code OpenMapRealMatrix}, and the total number of entries of the product
     * is larger than {@code Integer.MAX_VALUE}.
     */
    @Override
    public FieldMatrix<T> multiplyTransposed(final FieldMatrix<T> m) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * @throws MathIllegalArgumentException if {@code m} is an
     * {@code OpenMapRealMatrix}, and the total number of entries of the product
     * is larger than {@code Integer.MAX_VALUE}.
     */
    @Override
    public FieldMatrix<T> transposeMultiply(final FieldMatrix<T> m) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the key to access a matrix element.
     *
     * @param row Row index of the matrix element.
     * @param column Column index of the matrix element.
     * @return the key within the map to access the matrix element.
     */
    private int computeKey(int row, int column) {
        return row * columns + column;
    }
}
