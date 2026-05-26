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

import java.util.Arrays;
import java.util.function.Predicate;
import org.hipparchus.CalculusFieldElement;
import org.hipparchus.FieldElement;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.MathArrays;

/**
 * Calculates the QR-decomposition of a field matrix.
 * <p>The QR-decomposition of a matrix A consists of two matrices Q and R
 * that satisfy: A = QR, Q is orthogonal (Q<sup>T</sup>Q = I), and R is
 * upper triangular. If A is m&times;n, Q is m&times;m and R m&times;n.</p>
 * <p>This class compute the decomposition using Householder reflectors.</p>
 * <p>For efficiency purposes, the decomposition in packed form is transposed.
 * This allows inner loop to iterate inside rows, which is much more cache-efficient
 * in Java.</p>
 * <p>This class is based on the class {@link QRDecomposition}.</p>
 *
 * @param <T> type of the underlying field elements
 * @see <a href="http://mathworld.wolfram.com/QRDecomposition.html">MathWorld</a>
 * @see <a href="http://en.wikipedia.org/wiki/QR_decomposition">Wikipedia</a>
 */
public class FieldQRDecomposition<T extends CalculusFieldElement<T>> {

    /**
     * A packed TRANSPOSED representation of the QR decomposition.
     * <p>The elements BELOW the diagonal are the elements of the UPPER triangular
     * matrix R, and the rows ABOVE the diagonal are the Householder reflector vectors
     * from which an explicit form of Q can be recomputed if desired.</p>
     */
    private T[][] qrt;

    /**
     * The diagonal elements of R.
     */
    private T[] rDiag;

    /**
     * Cached value of Q.
     */
    private FieldMatrix<T> cachedQ;

    /**
     * Cached value of QT.
     */
    private FieldMatrix<T> cachedQT;

    /**
     * Cached value of R.
     */
    private FieldMatrix<T> cachedR;

    /**
     * Cached value of H.
     */
    private FieldMatrix<T> cachedH;

    /**
     * Singularity threshold.
     */
    private final T threshold;

    /**
     * checker for zero.
     */
    private final Predicate<T> zeroChecker;

    /**
     * Calculates the QR-decomposition of the given matrix.
     * The singularity threshold defaults to zero.
     *
     * @param matrix The matrix to decompose.
     *
     * @see #FieldQRDecomposition(FieldMatrix, CalculusFieldElement)
     */
    public FieldQRDecomposition(FieldMatrix<T> matrix) {
        this(matrix, matrix.getField().getZero());
    }

    /**
     * Calculates the QR-decomposition of the given matrix.
     *
     * @param matrix The matrix to decompose.
     * @param threshold Singularity threshold.
     */
    public FieldQRDecomposition(FieldMatrix<T> matrix, T threshold) {
        this(matrix, threshold, FieldElement::isZero);
    }

    /**
     * Calculates the QR-decomposition of the given matrix.
     *
     * @param matrix The matrix to decompose.
     * @param threshold Singularity threshold.
     * @param zeroChecker checker for zero
     */
    public FieldQRDecomposition(FieldMatrix<T> matrix, T threshold, Predicate<T> zeroChecker) {
        this.threshold = threshold;
        this.zeroChecker = zeroChecker;
        final int m = matrix.getRowDimension();
        final int n = matrix.getColumnDimension();
        qrt = matrix.transpose().getData();
        rDiag = MathArrays.buildArray(threshold.getField(), FastMath.min(m, n));
        cachedQ = null;
        cachedQT = null;
        cachedR = null;
        cachedH = null;
        decompose(qrt);
    }

    /**
     * Decompose matrix.
     * @param matrix transposed matrix
     */
    protected void decompose(T[][] matrix) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Perform Householder reflection for a minor A(minor, minor) of A.
     * @param minor minor index
     * @param matrix transposed matrix
     */
    protected void performHouseholderReflection(int minor, T[][] matrix) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the matrix R of the decomposition.
     * <p>R is an upper-triangular matrix</p>
     * @return the R matrix
     */
    public FieldMatrix<T> getR() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the matrix Q of the decomposition.
     * <p>Q is an orthogonal matrix</p>
     * @return the Q matrix
     */
    public FieldMatrix<T> getQ() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the transpose of the matrix Q of the decomposition.
     * <p>Q is an orthogonal matrix</p>
     * @return the transpose of the Q matrix, Q<sup>T</sup>
     */
    public FieldMatrix<T> getQT() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the Householder reflector vectors.
     * <p>H is a lower trapezoidal matrix whose columns represent
     * each successive Householder reflector vector. This matrix is used
     * to compute Q.</p>
     * @return a matrix containing the Householder reflector vectors
     */
    public FieldMatrix<T> getH() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get a solver for finding the A &times; X = B solution in least square sense.
     * <p>
     * Least Square sense means a solver can be computed for an overdetermined system,
     * (i.e. a system with more equations than unknowns, which corresponds to a tall A
     * matrix with more rows than columns). In any case, if the matrix is singular
     * within the tolerance set at {@link #FieldQRDecomposition(FieldMatrix,
     * CalculusFieldElement) construction}, an error will be triggered when
     * the {@link DecompositionSolver#solve(RealVector) solve} method will be called.
     * </p>
     * @return a solver
     */
    public FieldDecompositionSolver<T> getSolver() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Specialized solver.
     */
    private class FieldSolver implements FieldDecompositionSolver<T> {

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
         * {@inheritDoc}
         */
        @Override
        public FieldMatrix<T> solve(FieldMatrix<T> b) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         * @throws MathIllegalArgumentException if the decomposed matrix is singular.
         */
        @Override
        public FieldMatrix<T> getInverse() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Check singularity.
         *
         * @param diag Diagonal elements of the R matrix.
         * @param min Singularity threshold.
         * @param raise Whether to raise a {@link MathIllegalArgumentException}
         * if any element of the diagonal fails the check.
         * @return {@code true} if any element of the diagonal is smaller
         * or equal to {@code min}.
         * @throws MathIllegalArgumentException if the matrix is singular and
         * {@code raise} is {@code true}.
         */
        private boolean checkSingular(T[] diag, T min, boolean raise) {
            for (final T d : diag) {
                if (FastMath.abs(d.getReal()) <= min.getReal()) {
                    if (raise) {
                        throw new MathIllegalArgumentException(LocalizedCoreFormats.SINGULAR_MATRIX);
                    } else {
                        return true;
                    }
                }
            }
            return false;
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
