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

import java.util.function.Predicate;
import org.hipparchus.Field;
import org.hipparchus.FieldElement;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.MathArrays;

/**
 * Calculates the LUP-decomposition of a square matrix.
 * <p>The LUP-decomposition of a matrix A consists of three matrices
 * L, U and P that satisfy: PA = LU, L is lower triangular, and U is
 * upper triangular and P is a permutation matrix. All matrices are
 * m&times;m.</p>
 * <p>This class is based on the class with similar name from the
 * <a href="http://math.nist.gov/javanumerics/jama/">JAMA</a> library.</p>
 * <ul>
 *   <li>a {@link #getP() getP} method has been added,</li>
 *   <li>the {@code det} method has been renamed as {@link #getDeterminant()
 *   getDeterminant},</li>
 *   <li>the {@code getDoublePivot} method has been removed (but the int based
 *   {@link #getPivot() getPivot} method has been kept),</li>
 *   <li>the {@code solve} and {@code isNonSingular} methods have been replaced
 *   by a {@link #getSolver() getSolver} method and the equivalent methods
 *   provided by the returned {@link DecompositionSolver}.</li>
 * </ul>
 *
 * @param <T> the type of the field elements
 * @see <a href="http://mathworld.wolfram.com/LUDecomposition.html">MathWorld</a>
 * @see <a href="http://en.wikipedia.org/wiki/LU_decomposition">Wikipedia</a>
 */
public class FieldLUDecomposition<T extends FieldElement<T>> {

    /**
     * Field to which the elements belong.
     */
    private final Field<T> field;

    /**
     * Entries of LU decomposition.
     */
    private T[][] lu;

    /**
     * Pivot permutation associated with LU decomposition.
     */
    private int[] pivot;

    /**
     * Parity of the permutation associated with the LU decomposition.
     */
    private boolean even;

    /**
     * Singularity indicator.
     */
    private boolean singular;

    /**
     * Cached value of L.
     */
    private FieldMatrix<T> cachedL;

    /**
     * Cached value of U.
     */
    private FieldMatrix<T> cachedU;

    /**
     * Cached value of P.
     */
    private FieldMatrix<T> cachedP;

    /**
     * Calculates the LU-decomposition of the given matrix.
     * <p>
     * By default, <code>numericPermutationChoice</code> is set to <code>true</code>.
     * </p>
     * @param matrix The matrix to decompose.
     * @throws MathIllegalArgumentException if matrix is not square
     * @see #FieldLUDecomposition(FieldMatrix, Predicate)
     * @see #FieldLUDecomposition(FieldMatrix, Predicate, boolean)
     */
    public FieldLUDecomposition(FieldMatrix<T> matrix) {
        this(matrix, FieldElement::isZero);
    }

    /**
     * Calculates the LU-decomposition of the given matrix.
     * <p>
     * By default, <code>numericPermutationChoice</code> is set to <code>true</code>.
     * </p>
     * @param matrix The matrix to decompose.
     * @param zeroChecker checker for zero elements
     * @throws MathIllegalArgumentException if matrix is not square
     * @see #FieldLUDecomposition(FieldMatrix, Predicate, boolean)
     */
    public FieldLUDecomposition(FieldMatrix<T> matrix, final Predicate<T> zeroChecker) {
        this(matrix, zeroChecker, true);
    }

    /**
     * Calculates the LU-decomposition of the given matrix.
     * @param matrix The matrix to decompose.
     * @param zeroChecker checker for zero elements
     * @param numericPermutationChoice if <code>true</code> choose permutation index with numeric calculations, otherwise choose with <code>zeroChecker</code>
     * @throws MathIllegalArgumentException if matrix is not square
     */
    public FieldLUDecomposition(FieldMatrix<T> matrix, final Predicate<T> zeroChecker, boolean numericPermutationChoice) {
        if (!matrix.isSquare()) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.NON_SQUARE_MATRIX, matrix.getRowDimension(), matrix.getColumnDimension());
        }
        final int m = matrix.getColumnDimension();
        field = matrix.getField();
        lu = matrix.getData();
        pivot = new int[m];
        cachedL = null;
        cachedU = null;
        cachedP = null;
        // Initialize permutation array and parity
        for (int row = 0; row < m; row++) {
            pivot[row] = row;
        }
        even = true;
        singular = false;
        // Loop over columns
        for (int col = 0; col < m; col++) {
            // upper
            for (int row = 0; row < col; row++) {
                final T[] luRow = lu[row];
                T sum = luRow[col];
                for (int i = 0; i < row; i++) {
                    sum = sum.subtract(luRow[i].multiply(lu[i][col]));
                }
                luRow[col] = sum;
            }
            // permutation row
            int max = col;
            if (numericPermutationChoice) {
                // lower
                double largest = Double.NEGATIVE_INFINITY;
                for (int row = col; row < m; row++) {
                    final T[] luRow = lu[row];
                    T sum = luRow[col];
                    for (int i = 0; i < col; i++) {
                        sum = sum.subtract(luRow[i].multiply(lu[i][col]));
                    }
                    luRow[col] = sum;
                    // maintain best permutation choice
                    double absSum = FastMath.abs(sum.getReal());
                    if (absSum > largest) {
                        largest = absSum;
                        max = row;
                    }
                }
            } else {
                // lower
                // permutation row
                int nonZero = col;
                for (int row = col; row < m; row++) {
                    final T[] luRow = lu[row];
                    T sum = luRow[col];
                    for (int i = 0; i < col; i++) {
                        sum = sum.subtract(luRow[i].multiply(lu[i][col]));
                    }
                    luRow[col] = sum;
                    if (zeroChecker.test(lu[nonZero][col])) {
                        // try to select a better permutation choice
                        ++nonZero;
                    }
                }
                max = FastMath.min(m - 1, nonZero);
            }
            // Singularity check
            if (zeroChecker.test(lu[max][col])) {
                singular = true;
                return;
            }
            // Pivot if necessary
            if (max != col) {
                final T[] luMax = lu[max];
                final T[] luCol = lu[col];
                for (int i = 0; i < m; i++) {
                    final T tmp = luMax[i];
                    luMax[i] = luCol[i];
                    luCol[i] = tmp;
                }
                int temp = pivot[max];
                pivot[max] = pivot[col];
                pivot[col] = temp;
                even = !even;
            }
            // Divide the lower elements by the "winning" diagonal elt.
            final T luDiag = lu[col][col];
            for (int row = col + 1; row < m; row++) {
                lu[row][col] = lu[row][col].divide(luDiag);
            }
        }
    }

    /**
     * Returns the matrix L of the decomposition.
     * <p>L is a lower-triangular matrix</p>
     * @return the L matrix (or null if decomposed matrix is singular)
     */
    public FieldMatrix<T> getL() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the matrix U of the decomposition.
     * <p>U is an upper-triangular matrix</p>
     * @return the U matrix (or null if decomposed matrix is singular)
     */
    public FieldMatrix<T> getU() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the P rows permutation matrix.
     * <p>P is a sparse matrix with exactly one element set to 1.0 in
     * each row and each column, all other elements being set to 0.0.</p>
     * <p>The positions of the 1 elements are given by the {@link #getPivot()
     * pivot permutation vector}.</p>
     * @return the P rows permutation matrix (or null if decomposed matrix is singular)
     * @see #getPivot()
     */
    public FieldMatrix<T> getP() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the pivot permutation vector.
     * @return the pivot permutation vector
     * @see #getP()
     */
    public int[] getPivot() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Return the determinant of the matrix.
     * @return determinant of the matrix
     */
    public T getDeterminant() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get a solver for finding the A &times; X = B solution in exact linear sense.
     * @return a solver
     */
    public FieldDecompositionSolver<T> getSolver() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Specialized solver.
     */
    private class Solver implements FieldDecompositionSolver<T> {

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isNonSingular() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public FieldVector<T> solve(FieldVector<T> b) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Solve the linear equation A &times; X = B.
         * <p>The A matrix is implicit here. It is </p>
         * @param b right-hand side of the equation A &times; X = B
         * @return a vector X such that A &times; X = B
         * @throws MathIllegalArgumentException if the matrices dimensions do not match.
         * @throws MathIllegalArgumentException if the decomposed matrix is singular.
         */
        public ArrayFieldVector<T> solve(ArrayFieldVector<T> b) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public FieldMatrix<T> solve(FieldMatrix<T> b) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public FieldMatrix<T> getInverse() {
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
    }
}
