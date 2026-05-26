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
import java.util.Arrays;
import java.util.List;
import org.hipparchus.CalculusFieldElement;
import org.hipparchus.Field;
import org.hipparchus.FieldElement;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.MathRuntimeException;
import org.hipparchus.exception.NullArgumentException;
import org.hipparchus.fraction.BigFraction;
import org.hipparchus.fraction.Fraction;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.MathArrays;
import org.hipparchus.util.MathUtils;
import org.hipparchus.util.Precision;

/**
 * A collection of static methods that operate on or return matrices.
 */
public class MatrixUtils {

    /**
     * The default format for {@link RealMatrix} objects.
     */
    public static final RealMatrixFormat DEFAULT_FORMAT = RealMatrixFormat.getRealMatrixFormat();

    /**
     * A format for {@link RealMatrix} objects compatible with octave.
     */
    public static final RealMatrixFormat OCTAVE_FORMAT = new RealMatrixFormat("[", "]", "", "", "; ", ", ");

    /**
     * Pade coefficients required for the matrix exponential calculation.
     */
    private static final double[] PADE_COEFFICIENTS_3 = { 120.0, 60.0, 12.0, 1.0 };

    /**
     * Pade coefficients required for the matrix exponential calculation.
     */
    private static final double[] PADE_COEFFICIENTS_5 = { 30240.0, 15120.0, 3360.0, 420.0, 30.0, 1 };

    /**
     * Pade coefficients required for the matrix exponential calculation.
     */
    private static final double[] PADE_COEFFICIENTS_7 = { 17297280.0, 8648640.0, 1995840.0, 277200.0, 25200.0, 1512.0, 56.0, 1.0 };

    /**
     * Pade coefficients required for the matrix exponential calculation.
     */
    private static final double[] PADE_COEFFICIENTS_9 = { 17643225600.0, 8821612800.0, 2075673600.0, 302702400.0, 30270240.0, 2162160.0, 110880.0, 3960.0, 90.0, 1.0 };

    /**
     * Pade coefficients required for the matrix exponential calculation.
     */
    private static final double[] PADE_COEFFICIENTS_13 = { 6.476475253248e+16, 3.238237626624e+16, 7.7717703038976e+15, 1.1873537964288e+15, 129060195264000.0, 10559470521600.0, 670442572800.0, 33522128640.0, 1323241920.0, 40840800.0, 960960.0, 16380.0, 182.0, 1.0 };

    /**
     * Private constructor.
     */
    private MatrixUtils() {
        super();
    }

    /**
     * Returns a {@link RealMatrix} with specified dimensions.
     * <p>The type of matrix returned depends on the dimension. Below
     * 2<sup>12</sup> elements (i.e. 4096 elements or 64&times;64 for a
     * square matrix) which can be stored in a 32kB array, a {@link
     * Array2DRowRealMatrix} instance is built. Above this threshold a {@link
     * BlockRealMatrix} instance is built.</p>
     * <p>The matrix elements are all set to 0.0.</p>
     * @param rows number of rows of the matrix
     * @param columns number of columns of the matrix
     * @return  RealMatrix with specified dimensions
     * @see #createRealMatrix(double[][])
     */
    public static RealMatrix createRealMatrix(final int rows, final int columns) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns a {@link FieldMatrix} with specified dimensions.
     * <p>The type of matrix returned depends on the dimension. Below
     * 2<sup>12</sup> elements (i.e. 4096 elements or 64&times;64 for a
     * square matrix), a {@link FieldMatrix} instance is built. Above
     * this threshold a {@link BlockFieldMatrix} instance is built.</p>
     * <p>The matrix elements are all set to field.getZero().</p>
     * @param <T> the type of the field elements
     * @param field field to which the matrix elements belong
     * @param rows number of rows of the matrix
     * @param columns number of columns of the matrix
     * @return  FieldMatrix with specified dimensions
     * @see #createFieldMatrix(FieldElement[][])
     */
    public static <T extends FieldElement<T>> FieldMatrix<T> createFieldMatrix(final Field<T> field, final int rows, final int columns) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns a {@link RealMatrix} whose entries are the the values in the
     * the input array.
     * <p>The type of matrix returned depends on the dimension. Below
     * 2<sup>12</sup> elements (i.e. 4096 elements or 64&times;64 for a
     * square matrix) which can be stored in a 32kB array, a {@link
     * Array2DRowRealMatrix} instance is built. Above this threshold a {@link
     * BlockRealMatrix} instance is built.</p>
     * <p>The input array is copied, not referenced.</p>
     *
     * @param data input array
     * @return  RealMatrix containing the values of the array
     * @throws org.hipparchus.exception.MathIllegalArgumentException
     * if {@code data} is not rectangular (not all rows have the same length).
     * @throws MathIllegalArgumentException if a row or column is empty.
     * @throws NullArgumentException if either {@code data} or {@code data[0]}
     * is {@code null}.
     * @throws MathIllegalArgumentException if {@code data} is not rectangular.
     * @see #createRealMatrix(int, int)
     */
    public static RealMatrix createRealMatrix(double[][] data) throws MathIllegalArgumentException, NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns a {@link FieldMatrix} whose entries are the the values in the
     * the input array.
     * <p>The type of matrix returned depends on the dimension. Below
     * 2<sup>12</sup> elements (i.e. 4096 elements or 64&times;64 for a
     * square matrix), a {@link FieldMatrix} instance is built. Above
     * this threshold a {@link BlockFieldMatrix} instance is built.</p>
     * <p>The input array is copied, not referenced.</p>
     * @param <T> the type of the field elements
     * @param data input array
     * @return a matrix containing the values of the array.
     * @throws org.hipparchus.exception.MathIllegalArgumentException
     * if {@code data} is not rectangular (not all rows have the same length).
     * @throws MathIllegalArgumentException if a row or column is empty.
     * @throws NullArgumentException if either {@code data} or {@code data[0]}
     * is {@code null}.
     * @see #createFieldMatrix(Field, int, int)
     */
    public static <T extends FieldElement<T>> FieldMatrix<T> createFieldMatrix(T[][] data) throws MathIllegalArgumentException, NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns <code>dimension x dimension</code> identity matrix.
     *
     * @param dimension dimension of identity matrix to generate
     * @return identity matrix
     * @throws IllegalArgumentException if dimension is not positive
     */
    public static RealMatrix createRealIdentityMatrix(int dimension) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns <code>dimension x dimension</code> identity matrix.
     *
     * @param <T> the type of the field elements
     * @param field field to which the elements belong
     * @param dimension dimension of identity matrix to generate
     * @return identity matrix
     * @throws IllegalArgumentException if dimension is not positive
     */
    public static <T extends FieldElement<T>> FieldMatrix<T> createFieldIdentityMatrix(final Field<T> field, final int dimension) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns a diagonal matrix with specified elements.
     *
     * @param diagonal diagonal elements of the matrix (the array elements
     * will be copied)
     * @return diagonal matrix
     */
    public static RealMatrix createRealDiagonalMatrix(final double[] diagonal) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns a diagonal matrix with specified elements.
     *
     * @param <T> the type of the field elements
     * @param diagonal diagonal elements of the matrix (the array elements
     * will be copied)
     * @return diagonal matrix
     */
    public static <T extends FieldElement<T>> FieldMatrix<T> createFieldDiagonalMatrix(final T[] diagonal) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Creates a {@link RealVector} using the data from the input array.
     *
     * @param data the input data
     * @return a data.length RealVector
     * @throws MathIllegalArgumentException if {@code data} is empty.
     * @throws NullArgumentException if {@code data} is {@code null}.
     */
    public static RealVector createRealVector(double[] data) throws MathIllegalArgumentException, NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Creates a {@link RealVector} with specified dimensions.
     *
     * @param dimension dimension of the vector
     * @return a new vector
     * @since 1.3
     */
    public static RealVector createRealVector(final int dimension) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Creates a {@link FieldVector} using the data from the input array.
     *
     * @param <T> the type of the field elements
     * @param data the input data
     * @return a data.length FieldVector
     * @throws MathIllegalArgumentException if {@code data} is empty.
     * @throws NullArgumentException if {@code data} is {@code null}.
     * @throws MathIllegalArgumentException if {@code data} has 0 elements
     */
    public static <T extends FieldElement<T>> FieldVector<T> createFieldVector(final T[] data) throws MathIllegalArgumentException, NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Creates a {@link FieldVector} with specified dimensions.
     *
     * @param <T> the type of the field elements
     * @param field field to which array elements belong
     * @param dimension dimension of the vector
     * @return a new vector
     * @since 1.3
     */
    public static <T extends FieldElement<T>> FieldVector<T> createFieldVector(final Field<T> field, final int dimension) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Create a row {@link RealMatrix} using the data from the input
     * array.
     *
     * @param rowData the input row data
     * @return a 1 x rowData.length RealMatrix
     * @throws MathIllegalArgumentException if {@code rowData} is empty.
     * @throws NullArgumentException if {@code rowData} is {@code null}.
     */
    public static RealMatrix createRowRealMatrix(double[] rowData) throws MathIllegalArgumentException, NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Create a row {@link FieldMatrix} using the data from the input
     * array.
     *
     * @param <T> the type of the field elements
     * @param rowData the input row data
     * @return a 1 x rowData.length FieldMatrix
     * @throws MathIllegalArgumentException if {@code rowData} is empty.
     * @throws NullArgumentException if {@code rowData} is {@code null}.
     */
    public static <T extends FieldElement<T>> FieldMatrix<T> createRowFieldMatrix(final T[] rowData) throws MathIllegalArgumentException, NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Creates a column {@link RealMatrix} using the data from the input
     * array.
     *
     * @param columnData  the input column data
     * @return a columnData x 1 RealMatrix
     * @throws MathIllegalArgumentException if {@code columnData} is empty.
     * @throws NullArgumentException if {@code columnData} is {@code null}.
     */
    public static RealMatrix createColumnRealMatrix(double[] columnData) throws MathIllegalArgumentException, NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Creates a column {@link FieldMatrix} using the data from the input
     * array.
     *
     * @param <T> the type of the field elements
     * @param columnData  the input column data
     * @return a columnData x 1 FieldMatrix
     * @throws MathIllegalArgumentException if {@code data} is empty.
     * @throws NullArgumentException if {@code columnData} is {@code null}.
     */
    public static <T extends FieldElement<T>> FieldMatrix<T> createColumnFieldMatrix(final T[] columnData) throws MathIllegalArgumentException, NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Checks whether a matrix is symmetric, within a given relative tolerance.
     *
     * @param matrix Matrix to check.
     * @param relativeTolerance Tolerance of the symmetry check.
     * @param raiseException If {@code true}, an exception will be raised if
     * the matrix is not symmetric.
     * @return {@code true} if {@code matrix} is symmetric.
     * @throws MathIllegalArgumentException if the matrix is not square.
     * @throws MathIllegalArgumentException if the matrix is not symmetric.
     */
    private static boolean isSymmetricInternal(RealMatrix matrix, double relativeTolerance, boolean raiseException) {
        final int rows = matrix.getRowDimension();
        if (rows != matrix.getColumnDimension()) {
            if (raiseException) {
                throw new MathIllegalArgumentException(LocalizedCoreFormats.NON_SQUARE_MATRIX, rows, matrix.getColumnDimension());
            } else {
                return false;
            }
        }
        for (int i = 0; i < rows; i++) {
            for (int j = i + 1; j < rows; j++) {
                final double mij = matrix.getEntry(i, j);
                final double mji = matrix.getEntry(j, i);
                if (FastMath.abs(mij - mji) > FastMath.max(FastMath.abs(mij), FastMath.abs(mji)) * relativeTolerance) {
                    if (raiseException) {
                        throw new MathIllegalArgumentException(LocalizedCoreFormats.NON_SYMMETRIC_MATRIX, i, j, relativeTolerance);
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Checks whether a matrix is symmetric.
     *
     * @param matrix Matrix to check.
     * @param eps Relative tolerance.
     * @throws MathIllegalArgumentException if the matrix is not square.
     * @throws MathIllegalArgumentException if the matrix is not symmetric.
     */
    public static void checkSymmetric(RealMatrix matrix, double eps) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Checks whether a matrix is symmetric.
     *
     * @param matrix Matrix to check.
     * @param eps Relative tolerance.
     * @return {@code true} if {@code matrix} is symmetric.
     */
    public static boolean isSymmetric(RealMatrix matrix, double eps) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if matrix indices are valid.
     *
     * @param m Matrix.
     * @param row Row index to check.
     * @param column Column index to check.
     * @throws MathIllegalArgumentException if {@code row} or {@code column} is not
     * a valid index.
     */
    public static void checkMatrixIndex(final AnyMatrix m, final int row, final int column) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if a row index is valid.
     *
     * @param m Matrix.
     * @param row Row index to check.
     * @throws MathIllegalArgumentException if {@code row} is not a valid index.
     */
    public static void checkRowIndex(final AnyMatrix m, final int row) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if a column index is valid.
     *
     * @param m Matrix.
     * @param column Column index to check.
     * @throws MathIllegalArgumentException if {@code column} is not a valid index.
     */
    public static void checkColumnIndex(final AnyMatrix m, final int column) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if submatrix ranges indices are valid.
     * Rows and columns are indicated counting from 0 to {@code n - 1}.
     *
     * @param m Matrix.
     * @param startRow Initial row index.
     * @param endRow Final row index.
     * @param startColumn Initial column index.
     * @param endColumn Final column index.
     * @throws MathIllegalArgumentException if the indices are invalid.
     * @throws MathIllegalArgumentException if {@code endRow < startRow} or
     * {@code endColumn < startColumn}.
     */
    public static void checkSubMatrixIndex(final AnyMatrix m, final int startRow, final int endRow, final int startColumn, final int endColumn) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if submatrix ranges indices are valid.
     * Rows and columns are indicated counting from 0 to n-1.
     *
     * @param m Matrix.
     * @param selectedRows Array of row indices.
     * @param selectedColumns Array of column indices.
     * @throws NullArgumentException if {@code selectedRows} or
     * {@code selectedColumns} are {@code null}.
     * @throws MathIllegalArgumentException if the row or column selections are empty (zero
     * length).
     * @throws MathIllegalArgumentException if row or column selections are not valid.
     */
    public static void checkSubMatrixIndex(final AnyMatrix m, final int[] selectedRows, final int[] selectedColumns) throws MathIllegalArgumentException, NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if matrices are addition compatible.
     *
     * @param left Left hand side matrix.
     * @param right Right hand side matrix.
     * @throws MathIllegalArgumentException if the matrices are not addition
     * compatible.
     */
    public static void checkAdditionCompatible(final AnyMatrix left, final AnyMatrix right) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if matrices are subtraction compatible
     *
     * @param left Left hand side matrix.
     * @param right Right hand side matrix.
     * @throws MathIllegalArgumentException if the matrices are not addition
     * compatible.
     */
    public static void checkSubtractionCompatible(final AnyMatrix left, final AnyMatrix right) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if matrices are multiplication compatible
     *
     * @param left Left hand side matrix.
     * @param right Right hand side matrix.
     * @throws MathIllegalArgumentException if matrices are not multiplication
     * compatible.
     */
    public static void checkMultiplicationCompatible(final AnyMatrix left, final AnyMatrix right) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if matrices have the same number of columns.
     *
     * @param left Left hand side matrix.
     * @param right Right hand side matrix.
     * @throws MathIllegalArgumentException if matrices don't have the same number of columns.
     * @since 1.3
     */
    public static void checkSameColumnDimension(final AnyMatrix left, final AnyMatrix right) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if matrices have the same number of rows.
     *
     * @param left Left hand side matrix.
     * @param right Right hand side matrix.
     * @throws MathIllegalArgumentException if matrices don't have the same number of rows.
     * @since 1.3
     */
    public static void checkSameRowDimension(final AnyMatrix left, final AnyMatrix right) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convert a {@link FieldMatrix}/{@link Fraction} matrix to a {@link RealMatrix}.
     * @param m Matrix to convert.
     * @return the converted matrix.
     */
    public static Array2DRowRealMatrix fractionMatrixToRealMatrix(final FieldMatrix<Fraction> m) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Converter for {@link FieldMatrix}/{@link Fraction}.
     */
    private static class FractionMatrixConverter extends DefaultFieldMatrixPreservingVisitor<Fraction> {

        /**
         * Converted array.
         */
        private double[][] data;

        /**
         * Simple constructor.
         */
        FractionMatrixConverter() {
            super(Fraction.ZERO);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void start(int rows, int columns, int startRow, int endRow, int startColumn, int endColumn) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visit(int row, int column, Fraction value) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Get the converted matrix.
         *
         * @return the converted matrix.
         */
        Array2DRowRealMatrix getConvertedMatrix() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Convert a {@link FieldMatrix}/{@link BigFraction} matrix to a {@link RealMatrix}.
     *
     * @param m Matrix to convert.
     * @return the converted matrix.
     */
    public static Array2DRowRealMatrix bigFractionMatrixToRealMatrix(final FieldMatrix<BigFraction> m) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Converter for {@link FieldMatrix}/{@link BigFraction}.
     */
    private static class BigFractionMatrixConverter extends DefaultFieldMatrixPreservingVisitor<BigFraction> {

        /**
         * Converted array.
         */
        private double[][] data;

        /**
         * Simple constructor.
         */
        BigFractionMatrixConverter() {
            super(BigFraction.ZERO);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void start(int rows, int columns, int startRow, int endRow, int startColumn, int endColumn) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visit(int row, int column, BigFraction value) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Get the converted matrix.
         *
         * @return the converted matrix.
         */
        Array2DRowRealMatrix getConvertedMatrix() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Solve  a  system of composed of a Lower Triangular Matrix
     *  {@link RealMatrix}.
     *  <p>
     *  This method is called to solve systems of equations which are
     *  of the lower triangular form. The matrix {@link RealMatrix}
     *  is assumed, though not checked, to be in lower triangular form.
     *  The vector {@link RealVector} is overwritten with the solution.
     *  The matrix is checked that it is square and its dimensions match
     *  the length of the vector.
     *  </p>
     *  @param rm RealMatrix which is lower triangular
     *  @param b  RealVector this is overwritten
     *  @throws MathIllegalArgumentException if the matrix and vector are not
     *  conformable
     *  @throws MathIllegalArgumentException if the matrix {@code rm} is not square
     *  @throws MathRuntimeException if the absolute value of one of the diagonal
     *  coefficient of {@code rm} is lower than {@link Precision#SAFE_MIN}
     */
    public static void solveLowerTriangularSystem(RealMatrix rm, RealVector b) throws MathIllegalArgumentException, MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Solver a  system composed  of an Upper Triangular Matrix
     * {@link RealMatrix}.
     * <p>
     * This method is called to solve systems of equations which are
     * of the lower triangular form. The matrix {@link RealMatrix}
     * is assumed, though not checked, to be in upper triangular form.
     * The vector {@link RealVector} is overwritten with the solution.
     * The matrix is checked that it is square and its dimensions match
     * the length of the vector.
     * </p>
     * @param rm RealMatrix which is upper triangular
     * @param b  RealVector this is overwritten
     * @throws MathIllegalArgumentException if the matrix and vector are not
     * conformable
     * @throws MathIllegalArgumentException if the matrix {@code rm} is not
     * square
     * @throws MathRuntimeException if the absolute value of one of the diagonal
     * coefficient of {@code rm} is lower than {@link Precision#SAFE_MIN}
     */
    public static void solveUpperTriangularSystem(RealMatrix rm, RealVector b) throws MathIllegalArgumentException, MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Computes the inverse of the given matrix by splitting it into
     * 4 sub-matrices.
     *
     * @param m Matrix whose inverse must be computed.
     * @param splitIndex Index that determines the "split" line and
     * column.
     * The element corresponding to this index will part of the
     * upper-left sub-matrix.
     * @return the inverse of {@code m}.
     * @throws MathIllegalArgumentException if {@code m} is not square.
     */
    public static RealMatrix blockInverse(RealMatrix m, int splitIndex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Computes the inverse of the given matrix.
     * <p>
     * By default, the inverse of the matrix is computed using the QR-decomposition,
     * unless a more efficient method can be determined for the input matrix.
     * <p>
     * Note: this method will use a singularity threshold of 0,
     * use {@link #inverse(RealMatrix, double)} if a different threshold is needed.
     *
     * @param matrix Matrix whose inverse shall be computed
     * @return the inverse of {@code matrix}
     * @throws NullArgumentException if {@code matrix} is {@code null}
     * @throws MathIllegalArgumentException if m is singular
     * @throws MathIllegalArgumentException if matrix is not square
     */
    public static RealMatrix inverse(RealMatrix matrix) throws MathIllegalArgumentException, NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Computes the inverse of the given matrix.
     * <p>
     * By default, the inverse of the matrix is computed using the QR-decomposition,
     * unless a more efficient method can be determined for the input matrix.
     *
     * @param matrix Matrix whose inverse shall be computed
     * @param threshold Singularity threshold
     * @return the inverse of {@code m}
     * @throws NullArgumentException if {@code matrix} is {@code null}
     * @throws MathIllegalArgumentException if matrix is singular
     * @throws MathIllegalArgumentException if matrix is not square
     */
    public static RealMatrix inverse(RealMatrix matrix, double threshold) throws MathIllegalArgumentException, NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Computes the <a href="https://mathworld.wolfram.com/MatrixExponential.html">
     * matrix exponential</a> of the given matrix.
     *
     * The algorithm implementation follows the Pade approximant method of
     * <p>Higham, Nicholas J. “The Scaling and Squaring Method for the Matrix Exponential
     * Revisited.” SIAM Journal on Matrix Analysis and Applications 26, no. 4 (January 2005): 1179–93.</p>
     *
     * @param rm RealMatrix whose inverse shall be computed
     * @return The inverse of {@code rm}
     * @throws MathIllegalArgumentException if matrix is not square
     */
    public static RealMatrix matrixExponential(final RealMatrix rm) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Orthonormalize a list of vectors.
     * <p>
     * Orthonormalization is performed by using the Modified Gram-Schmidt process.
     * </p>
     * @param independent list of independent vectors
     * @param threshold projected vectors with a norm less than or equal to this threshold
     * are considered to have zero norm, hence the vectors they come from are not independent from
     * previous vectors
     * @param handler handler for dependent vectors
     * @return orthonormal basis having the same span as {@code independent}
     * @since 2.1
     */
    public static List<RealVector> orthonormalize(final List<RealVector> independent, final double threshold, final DependentVectorsHandler handler) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Orthonormalize a list of vectors.
     * <p>
     * Orthonormalization is performed by using the Modified Gram-Schmidt process.
     * </p>
     * @param <T> type of the field elements
     * @param independent list of independent vectors
     * @param threshold projected vectors with a norm less than or equal to this threshold
     * are considered to have zero norm, hence the vectors they come from are not independent from
     * previous vectors
     * @param field type of the files elements
     * @param handler handler for dependent vectors
     * @return orthonormal basis having the same span as {@code independent}
     * @since 2.1
     */
    public static <T extends CalculusFieldElement<T>> List<FieldVector<T>> orthonormalize(final Field<T> field, final List<FieldVector<T>> independent, final T threshold, final DependentVectorsHandler handler) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
