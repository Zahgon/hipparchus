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
import org.hipparchus.exception.MathIllegalStateException;
import org.hipparchus.exception.NullArgumentException;
import org.hipparchus.util.MathArrays;
import org.hipparchus.util.MathUtils;

/**
 * Implementation of {@link FieldMatrix} using a {@link FieldElement}[][] array to store entries.
 * <p>
 * As specified in the {@link FieldMatrix} interface, matrix element indexing
 * is 0-based -- e.g., {@code getEntry(0, 0)}
 * returns the element in the first row, first column of the matrix
 * </p>
 *
 * @param <T> the type of the field elements
 */
public class Array2DRowFieldMatrix<T extends FieldElement<T>> extends AbstractFieldMatrix<T> implements Serializable {

    /**
     * Serializable version identifier
     */
    private static final long serialVersionUID = 7260756672015356458L;

    /**
     * Entries of the matrix
     */
    private T[][] data;

    /**
     * Creates a matrix with no data
     * @param field field to which the elements belong
     */
    public Array2DRowFieldMatrix(final Field<T> field) {
        super(field);
    }

    /**
     * Create a new {@code FieldMatrix<T>} with the supplied row and column dimensions.
     *
     * @param field Field to which the elements belong.
     * @param rowDimension Number of rows in the new matrix.
     * @param columnDimension Number of columns in the new matrix.
     * @throws MathIllegalArgumentException if row or column dimension is not positive.
     */
    public Array2DRowFieldMatrix(final Field<T> field, final int rowDimension, final int columnDimension) throws MathIllegalArgumentException {
        super(field, rowDimension, columnDimension);
        data = MathArrays.buildArray(field, rowDimension, columnDimension);
    }

    /**
     * Create a new {@code FieldMatrix<T>} using the input array as the underlying
     * data array.
     * <p>The input array is copied, not referenced. This constructor has
     * the same effect as calling {@link #Array2DRowFieldMatrix(FieldElement[][], boolean)}
     * with the second argument set to {@code true}.</p>
     *
     * @param d Data for the new matrix.
     * @throws MathIllegalArgumentException if {@code d} is not rectangular.
     * @throws NullArgumentException if {@code d} is {@code null}.
     * @throws MathIllegalArgumentException if there are not at least one row and one column.
     * @see #Array2DRowFieldMatrix(FieldElement[][], boolean)
     */
    public Array2DRowFieldMatrix(final T[][] d) throws MathIllegalArgumentException, NullArgumentException {
        this(extractField(d), d);
    }

    /**
     * Create a new {@code FieldMatrix<T>} using the input array as the underlying
     * data array.
     * <p>The input array is copied, not referenced. This constructor has
     * the same effect as calling {@link #Array2DRowFieldMatrix(FieldElement[][], boolean)}
     * with the second argument set to {@code true}.</p>
     *
     * @param field Field to which the elements belong.
     * @param d Data for the new matrix.
     * @throws MathIllegalArgumentException if {@code d} is not rectangular.
     * @throws NullArgumentException if {@code d} is {@code null}.
     * @throws MathIllegalArgumentException if there are not at least one row and one column.
     * @see #Array2DRowFieldMatrix(FieldElement[][], boolean)
     */
    public Array2DRowFieldMatrix(final Field<T> field, final T[][] d) throws MathIllegalArgumentException, NullArgumentException {
        super(field);
        copyIn(d);
    }

    /**
     * Create a new {@code FieldMatrix<T>} using the input array as the underlying
     * data array.
     * <p>If an array is built specially in order to be embedded in a
     * {@code FieldMatrix<T>} and not used directly, the {@code copyArray} may be
     * set to {@code false}. This will prevent the copying and improve
     * performance as no new array will be built and no data will be copied.</p>
     *
     * @param d Data for the new matrix.
     * @param copyArray Whether to copy or reference the input array.
     * @throws MathIllegalArgumentException if {@code d} is not rectangular.
     * @throws MathIllegalArgumentException if there are not at least one row and one column.
     * @throws NullArgumentException if {@code d} is {@code null}.
     * @see #Array2DRowFieldMatrix(FieldElement[][])
     */
    public Array2DRowFieldMatrix(final T[][] d, final boolean copyArray) throws MathIllegalArgumentException, NullArgumentException {
        this(extractField(d), d, copyArray);
    }

    /**
     * Create a new {@code FieldMatrix<T>} using the input array as the underlying
     * data array.
     * <p>If an array is built specially in order to be embedded in a
     * {@code FieldMatrix<T>} and not used directly, the {@code copyArray} may be
     * set to {@code false}. This will prevent the copying and improve
     * performance as no new array will be built and no data will be copied.</p>
     *
     * @param field Field to which the elements belong.
     * @param d Data for the new matrix.
     * @param copyArray Whether to copy or reference the input array.
     * @throws MathIllegalArgumentException if {@code d} is not rectangular.
     * @throws MathIllegalArgumentException if there are not at least one row and one column.
     * @throws NullArgumentException if {@code d} is {@code null}.
     * @see #Array2DRowFieldMatrix(FieldElement[][])
     */
    public Array2DRowFieldMatrix(final Field<T> field, final T[][] d, final boolean copyArray) throws MathIllegalArgumentException, NullArgumentException {
        super(field);
        if (copyArray) {
            copyIn(d);
        } else {
            MathUtils.checkNotNull(d);
            final int nRows = d.length;
            if (nRows == 0) {
                throw new MathIllegalArgumentException(LocalizedCoreFormats.AT_LEAST_ONE_ROW);
            }
            final int nCols = d[0].length;
            if (nCols == 0) {
                throw new MathIllegalArgumentException(LocalizedCoreFormats.AT_LEAST_ONE_COLUMN);
            }
            for (int r = 1; r < nRows; r++) {
                if (d[r].length != nCols) {
                    throw new MathIllegalArgumentException(LocalizedCoreFormats.DIMENSIONS_MISMATCH, nCols, d[r].length);
                }
            }
            // NOPMD - array copy is taken care of by parameter
            data = d;
        }
    }

    /**
     * Create a new (column) {@code FieldMatrix<T>} using {@code v} as the
     * data for the unique column of the created matrix.
     * The input array is copied.
     *
     * @param v Column vector holding data for new matrix.
     * @throws MathIllegalArgumentException if v is empty
     */
    public Array2DRowFieldMatrix(final T[] v) throws MathIllegalArgumentException {
        this(extractField(v), v);
    }

    /**
     * Create a new (column) {@code FieldMatrix<T>} using {@code v} as the
     * data for the unique column of the created matrix.
     * The input array is copied.
     *
     * @param field Field to which the elements belong.
     * @param v Column vector holding data for new matrix.
     */
    public Array2DRowFieldMatrix(final Field<T> field, final T[] v) {
        super(field);
        final int nRows = v.length;
        data = MathArrays.buildArray(getField(), nRows, 1);
        for (int row = 0; row < nRows; row++) {
            data[row][0] = v[row];
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldMatrix<T> createMatrix(final int rowDimension, final int columnDimension) throws MathIllegalArgumentException {
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
     * Add {@code m} to this matrix.
     *
     * @param m Matrix to be added.
     * @return {@code this} + m.
     * @throws MathIllegalArgumentException if {@code m} is not the same
     * size as this matrix.
     */
    public Array2DRowFieldMatrix<T> add(final Array2DRowFieldMatrix<T> m) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Subtract {@code m} from this matrix.
     *
     * @param m Matrix to be subtracted.
     * @return {@code this} + m.
     * @throws MathIllegalArgumentException if {@code m} is not the same
     * size as this matrix.
     */
    public Array2DRowFieldMatrix<T> subtract(final Array2DRowFieldMatrix<T> m) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Postmultiplying this matrix by {@code m}.
     *
     * @param m Matrix to postmultiply by.
     * @return {@code this} * m.
     * @throws MathIllegalArgumentException if the number of columns of this
     * matrix is not equal to the number of rows of {@code m}.
     */
    public Array2DRowFieldMatrix<T> multiply(final Array2DRowFieldMatrix<T> m) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the result of postmultiplying {@code this} by {@code m^T}.
     * @param m matrix to first transpose and second postmultiply by
     * @return {@code this * m^T}
     * @throws MathIllegalArgumentException if
     * {@code columnDimension(this) != columnDimension(m)}
     * @since 1.3
     */
    public FieldMatrix<T> multiplyTransposed(final Array2DRowFieldMatrix<T> m) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldMatrix<T> multiplyTransposed(final FieldMatrix<T> m) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the result of postmultiplying {@code this^T} by {@code m}.
     * @param m matrix to postmultiply by
     * @return {@code this^T * m}
     * @throws MathIllegalArgumentException if
     * {@code columnDimension(this) != columnDimension(m)}
     * @since 1.3
     */
    public FieldMatrix<T> transposeMultiply(final Array2DRowFieldMatrix<T> m) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldMatrix<T> transposeMultiply(final FieldMatrix<T> m) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T[][] getData() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get a reference to the underlying data array.
     * This methods returns internal data, <strong>not</strong> fresh copy of it.
     *
     * @return the 2-dimensional array of entries.
     */
    public T[][] getDataRef() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSubMatrix(final T[][] subMatrix, final int row, final int column) throws MathIllegalArgumentException, NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getEntry(final int row, final int column) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setEntry(final int row, final int column, final T value) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addToEntry(final int row, final int column, final T increment) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void multiplyEntry(final int row, final int column, final T factor) throws MathIllegalArgumentException {
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
    public int getColumnDimension() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T[] operate(final T[] v) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T[] preMultiply(final T[] v) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldMatrix<T> getSubMatrix(final int startRow, final int endRow, final int startColumn, final int endColumn) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T walkInRowOrder(final FieldMatrixChangingVisitor<T> visitor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T walkInRowOrder(final FieldMatrixPreservingVisitor<T> visitor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T walkInRowOrder(final FieldMatrixChangingVisitor<T> visitor, final int startRow, final int endRow, final int startColumn, final int endColumn) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T walkInRowOrder(final FieldMatrixPreservingVisitor<T> visitor, final int startRow, final int endRow, final int startColumn, final int endColumn) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T walkInColumnOrder(final FieldMatrixChangingVisitor<T> visitor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T walkInColumnOrder(final FieldMatrixPreservingVisitor<T> visitor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T walkInColumnOrder(final FieldMatrixChangingVisitor<T> visitor, final int startRow, final int endRow, final int startColumn, final int endColumn) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T walkInColumnOrder(final FieldMatrixPreservingVisitor<T> visitor, final int startRow, final int endRow, final int startColumn, final int endColumn) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get a fresh copy of the underlying data array.
     *
     * @return a copy of the underlying data array.
     */
    private T[][] copyOut() {
        final int nRows = this.getRowDimension();
        final T[][] out = MathArrays.buildArray(getField(), nRows, getColumnDimension());
        // can't copy 2-d array in one shot, otherwise get row references
        for (int i = 0; i < nRows; i++) {
            System.arraycopy(data[i], 0, out[i], 0, data[i].length);
        }
        return out;
    }

    /**
     * Replace data with a fresh copy of the input array.
     *
     * @param in Data to copy.
     * @throws MathIllegalArgumentException if the input array is empty.
     * @throws MathIllegalArgumentException if the input array is not rectangular.
     * @throws NullArgumentException if the input array is {@code null}.
     */
    private void copyIn(final T[][] in) throws MathIllegalArgumentException, NullArgumentException {
        setSubMatrix(in, 0, 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T[] getRow(final int row) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRow(final int row, final T[] array) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
