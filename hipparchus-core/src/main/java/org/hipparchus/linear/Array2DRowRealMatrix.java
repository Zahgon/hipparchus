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
import org.hipparchus.exception.MathIllegalStateException;
import org.hipparchus.exception.NullArgumentException;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.MathUtils;

/**
 * Implementation of {@link RealMatrix} using a {@code double[][]} array to
 * store entries.
 */
public class Array2DRowRealMatrix extends AbstractRealMatrix implements Serializable {

    /**
     * Serializable version identifier.
     */
    private static final long serialVersionUID = -1067294169172445528L;

    /**
     * Entries of the matrix.
     */
    private double[][] data;

    /**
     * Creates a matrix with no data
     */
    public Array2DRowRealMatrix() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    /**
     * Create a new RealMatrix with the supplied row and column dimensions.
     *
     * @param rowDimension Number of rows in the new matrix.
     * @param columnDimension Number of columns in the new matrix.
     * @throws MathIllegalArgumentException if the row or column dimension is
     * not positive.
     */
    public Array2DRowRealMatrix(final int rowDimension, final int columnDimension) throws MathIllegalArgumentException {
        super(rowDimension, columnDimension);
        data = new double[rowDimension][columnDimension];
    }

    /**
     * Create a new {@code RealMatrix} using the input array as the underlying
     * data array.
     * <p>The input array is copied, not referenced. This constructor has
     * the same effect as calling {@link #Array2DRowRealMatrix(double[][], boolean)}
     * with the second argument set to {@code true}.</p>
     *
     * @param d Data for the new matrix.
     * @throws MathIllegalArgumentException if {@code d} is not rectangular.
     * @throws MathIllegalArgumentException if {@code d} row or column dimension is zero.
     * @throws NullArgumentException if {@code d} is {@code null}.
     * @see #Array2DRowRealMatrix(double[][], boolean)
     */
    public Array2DRowRealMatrix(final double[][] d) throws MathIllegalArgumentException, NullArgumentException {
        copyIn(d);
    }

    /**
     * Create a new RealMatrix using the input array as the underlying
     * data array.
     * If an array is built specially in order to be embedded in a
     * RealMatrix and not used directly, the {@code copyArray} may be
     * set to {@code false}. This will prevent the copying and improve
     * performance as no new array will be built and no data will be copied.
     *
     * @param d Data for new matrix.
     * @param copyArray if {@code true}, the input array will be copied,
     * otherwise it will be referenced.
     * @throws MathIllegalArgumentException if {@code d} is not rectangular.
     * @throws MathIllegalArgumentException if {@code d} row or column dimension is zero.
     * @throws NullArgumentException if {@code d} is {@code null}.
     * @see #Array2DRowRealMatrix(double[][])
     */
    public Array2DRowRealMatrix(final double[][] d, final boolean copyArray) throws MathIllegalArgumentException, NullArgumentException {
        if (copyArray) {
            copyIn(d);
        } else {
            if (d == null) {
                throw new NullArgumentException();
            }
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
                    throw new MathIllegalArgumentException(LocalizedCoreFormats.DIMENSIONS_MISMATCH, d[r].length, nCols);
                }
            }
            // NOPMD - array copy is taken care of by parameter
            data = d;
        }
    }

    /**
     * Create a new (column) RealMatrix using {@code v} as the
     * data for the unique column of the created matrix.
     * The input array is copied.
     *
     * @param v Column vector holding data for new matrix.
     */
    public Array2DRowRealMatrix(final double[] v) {
        final int nRows = v.length;
        data = new double[nRows][1];
        for (int row = 0; row < nRows; row++) {
            data[row][0] = v[row];
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RealMatrix createMatrix(final int rowDimension, final int columnDimension) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RealMatrix copy() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the sum of {@code this} and {@code m}.
     *
     * @param m Matrix to be added.
     * @return {@code this + m}.
     * @throws MathIllegalArgumentException if {@code m} is not the same
     * size as {@code this}.
     */
    public Array2DRowRealMatrix add(final Array2DRowRealMatrix m) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns {@code this} minus {@code m}.
     *
     * @param m Matrix to be subtracted.
     * @return {@code this - m}
     * @throws MathIllegalArgumentException if {@code m} is not the same
     * size as {@code this}.
     */
    public Array2DRowRealMatrix subtract(final Array2DRowRealMatrix m) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the result of postmultiplying {@code this} by {@code m}.
     *
     * @param m matrix to postmultiply by
     * @return {@code this * m}
     * @throws MathIllegalArgumentException if
     * {@code columnDimension(this) != rowDimension(m)}
     */
    public Array2DRowRealMatrix multiply(final Array2DRowRealMatrix m) throws MathIllegalArgumentException {
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
    public RealMatrix multiplyTransposed(final Array2DRowRealMatrix m) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RealMatrix multiplyTransposed(final RealMatrix m) {
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
    public RealMatrix transposeMultiply(final Array2DRowRealMatrix m) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RealMatrix transposeMultiply(final RealMatrix m) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double[][] getData() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get a reference to the underlying data array.
     *
     * @return 2-dimensional array of entries.
     */
    public double[][] getDataRef() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSubMatrix(final double[][] subMatrix, final int row, final int column) throws MathIllegalArgumentException, NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getEntry(final int row, final int column) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setEntry(final int row, final int column, final double value) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addToEntry(final int row, final int column, final double increment) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void multiplyEntry(final int row, final int column, final double factor) throws MathIllegalArgumentException {
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
    public double[] operate(final double[] v) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double[] preMultiply(final double[] v) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RealMatrix getSubMatrix(final int startRow, final int endRow, final int startColumn, final int endColumn) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double walkInRowOrder(final RealMatrixChangingVisitor visitor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double walkInRowOrder(final RealMatrixPreservingVisitor visitor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double walkInRowOrder(final RealMatrixChangingVisitor visitor, final int startRow, final int endRow, final int startColumn, final int endColumn) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double walkInRowOrder(final RealMatrixPreservingVisitor visitor, final int startRow, final int endRow, final int startColumn, final int endColumn) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double walkInColumnOrder(final RealMatrixChangingVisitor visitor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double walkInColumnOrder(final RealMatrixPreservingVisitor visitor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double walkInColumnOrder(final RealMatrixChangingVisitor visitor, final int startRow, final int endRow, final int startColumn, final int endColumn) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double walkInColumnOrder(final RealMatrixPreservingVisitor visitor, final int startRow, final int endRow, final int startColumn, final int endColumn) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get a fresh copy of the underlying data array.
     *
     * @return a copy of the underlying data array.
     */
    private double[][] copyOut() {
        final int nRows = this.getRowDimension();
        final double[][] out = new double[nRows][this.getColumnDimension()];
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
    private void copyIn(final double[][] in) throws MathIllegalArgumentException, NullArgumentException {
        setSubMatrix(in, 0, 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double[] getRow(final int row) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRow(final int row, final double[] array) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Kronecker product of the current matrix and the parameter matrix.
     *
     * @param b matrix to post Kronecker-multiply by
     * @return this ⨂ b
     */
    public RealMatrix kroneckerProduct(final RealMatrix b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Transforms a matrix in a vector (Vectorization).
     * @return a one column matrix
     */
    public RealMatrix stack() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Transforms a one-column stacked matrix into a squared matrix (devectorization).
     * @return square matrix
     */
    public RealMatrix unstackSquare() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
