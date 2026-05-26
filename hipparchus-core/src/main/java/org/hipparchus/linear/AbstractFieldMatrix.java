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

import java.util.ArrayList;
import java.util.List;
import org.hipparchus.Field;
import org.hipparchus.FieldElement;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.NullArgumentException;
import org.hipparchus.util.MathArrays;

/**
 * Basic implementation of {@link FieldMatrix} methods regardless of the underlying storage.
 * <p>All the methods implemented here use {@link #getEntry(int, int)} to access
 * matrix elements. Derived class can provide faster implementations. </p>
 *
 * @param <T> Type of the field elements.
 */
public abstract class AbstractFieldMatrix<T extends FieldElement<T>> implements FieldMatrix<T> {

    /**
     * Field to which the elements belong.
     */
    private final Field<T> field;

    /**
     * Constructor for use with Serializable
     */
    protected AbstractFieldMatrix() {
        field = null;
    }

    /**
     * Creates a matrix with no data
     * @param field field to which the elements belong
     */
    protected AbstractFieldMatrix(final Field<T> field) {
        this.field = field;
    }

    /**
     * Create a new {@link FieldMatrix} with the supplied row and column dimensions.
     *
     * @param field Field to which the elements belong.
     * @param rowDimension Number of rows in the new matrix.
     * @param columnDimension Number of columns in the new matrix.
     * @throws MathIllegalArgumentException if row or column dimension is not
     * positive.
     */
    protected AbstractFieldMatrix(final Field<T> field, final int rowDimension, final int columnDimension) throws MathIllegalArgumentException {
        if (rowDimension <= 0) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.DIMENSION, rowDimension);
        }
        if (columnDimension <= 0) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.DIMENSION, columnDimension);
        }
        this.field = field;
    }

    /**
     * Get the elements type from an array.
     *
     * @param <T> Type of the field elements.
     * @param d Data array.
     * @return the field to which the array elements belong.
     * @throws NullArgumentException if the array is {@code null}.
     * @throws MathIllegalArgumentException if the array is empty.
     */
    protected static <T extends FieldElement<T>> Field<T> extractField(final T[][] d) throws MathIllegalArgumentException, NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the elements type from an array.
     *
     * @param <T> Type of the field elements.
     * @param d Data array.
     * @return the field to which the array elements belong.
     * @throws MathIllegalArgumentException if array is empty.
     */
    protected static <T extends FieldElement<T>> Field<T> extractField(final T[] d) throws MathIllegalArgumentException {
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
    public abstract FieldMatrix<T> createMatrix(int rowDimension, int columnDimension) throws MathIllegalArgumentException;

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract FieldMatrix<T> copy();

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldMatrix<T> add(FieldMatrix<T> m) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldMatrix<T> subtract(final FieldMatrix<T> m) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldMatrix<T> scalarAdd(final T d) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldMatrix<T> scalarMultiply(final T d) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldMatrix<T> multiply(final FieldMatrix<T> m) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldMatrix<T> preMultiply(final FieldMatrix<T> m) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldMatrix<T> power(final int p) throws MathIllegalArgumentException {
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
    public FieldMatrix<T> getSubMatrix(final int[] selectedRows, final int[] selectedColumns) throws MathIllegalArgumentException, NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void copySubMatrix(final int startRow, final int endRow, final int startColumn, final int endColumn, final T[][] destination) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void copySubMatrix(int[] selectedRows, int[] selectedColumns, T[][] destination) throws MathIllegalArgumentException, NullArgumentException {
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
    public FieldMatrix<T> getRowMatrix(final int row) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRowMatrix(final int row, final FieldMatrix<T> matrix) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldMatrix<T> getColumnMatrix(final int column) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setColumnMatrix(final int column, final FieldMatrix<T> matrix) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldVector<T> getRowVector(final int row) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRowVector(final int row, final FieldVector<T> vector) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldVector<T> getColumnVector(final int column) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setColumnVector(final int column, final FieldVector<T> vector) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
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

    /**
     * {@inheritDoc}
     */
    @Override
    public T[] getColumn(final int column) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setColumn(final int column, final T[] array) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract T getEntry(int row, int column) throws MathIllegalArgumentException;

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void setEntry(int row, int column, T value) throws MathIllegalArgumentException;

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void addToEntry(int row, int column, T increment) throws MathIllegalArgumentException;

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void multiplyEntry(int row, int column, T factor) throws MathIllegalArgumentException;

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldMatrix<T> transpose() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSquare() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract int getRowDimension();

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract int getColumnDimension();

    /**
     * {@inheritDoc}
     */
    @Override
    public T getTrace() throws MathIllegalArgumentException {
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
    public FieldVector<T> operate(final FieldVector<T> v) throws MathIllegalArgumentException {
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
    public FieldVector<T> preMultiply(final FieldVector<T> v) throws MathIllegalArgumentException {
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
     * {@inheritDoc}
     */
    @Override
    public T walkInOptimizedOrder(final FieldMatrixChangingVisitor<T> visitor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T walkInOptimizedOrder(final FieldMatrixPreservingVisitor<T> visitor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T walkInOptimizedOrder(final FieldMatrixChangingVisitor<T> visitor, final int startRow, final int endRow, final int startColumn, final int endColumn) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T walkInOptimizedOrder(final FieldMatrixPreservingVisitor<T> visitor, final int startRow, final int endRow, final int startColumn, final int endColumn) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get a string representation for this matrix.
     * @return a string representation for this matrix
     */
    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns true iff <code>object</code> is a
     * <code>FieldMatrix</code> instance with the same dimensions as this
     * and all corresponding matrix entries are equal.
     *
     * @param object the object to test equality against.
     * @return true if object equals this
     */
    @Override
    public boolean equals(final Object object) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Computes a hashcode for the matrix.
     *
     * @return hashcode for matrix
     */
    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if a row index is valid.
     *
     * @param row Row index to check.
     * @throws MathIllegalArgumentException if {@code index} is not valid.
     */
    protected void checkRowIndex(final int row) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if a column index is valid.
     *
     * @param column Column index to check.
     * @throws MathIllegalArgumentException if {@code index} is not valid.
     */
    protected void checkColumnIndex(final int column) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if submatrix ranges indices are valid.
     * Rows and columns are indicated counting from 0 to n-1.
     *
     * @param startRow Initial row index.
     * @param endRow Final row index.
     * @param startColumn Initial column index.
     * @param endColumn Final column index.
     * @throws MathIllegalArgumentException if the indices are not valid.
     * @throws MathIllegalArgumentException if {@code endRow < startRow} or
     * {@code endColumn < startColumn}.
     */
    protected void checkSubMatrixIndex(final int startRow, final int endRow, final int startColumn, final int endColumn) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if submatrix ranges indices are valid.
     * Rows and columns are indicated counting from 0 to n-1.
     *
     * @param selectedRows Array of row indices.
     * @param selectedColumns Array of column indices.
     * @throws NullArgumentException if the arrays are {@code null}.
     * @throws MathIllegalArgumentException if the arrays have zero length.
     * @throws MathIllegalArgumentException if row or column selections are not valid.
     */
    protected void checkSubMatrixIndex(final int[] selectedRows, final int[] selectedColumns) throws MathIllegalArgumentException, NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if a matrix is addition compatible with the instance.
     *
     * @param m Matrix to check.
     * @throws MathIllegalArgumentException if the matrix is not
     * addition-compatible with instance.
     */
    protected void checkAdditionCompatible(final FieldMatrix<T> m) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if a matrix is subtraction compatible with the instance.
     *
     * @param m Matrix to check.
     * @throws MathIllegalArgumentException if the matrix is not
     * subtraction-compatible with instance.
     */
    protected void checkSubtractionCompatible(final FieldMatrix<T> m) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if a matrix is multiplication compatible with the instance.
     *
     * @param m Matrix to check.
     * @throws MathIllegalArgumentException if the matrix is not
     * multiplication-compatible with instance.
     */
    protected void checkMultiplicationCompatible(final FieldMatrix<T> m) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
