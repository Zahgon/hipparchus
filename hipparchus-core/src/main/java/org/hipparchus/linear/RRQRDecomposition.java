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

import org.hipparchus.util.FastMath;

/**
 * Calculates the rank-revealing QR-decomposition of a matrix, with column pivoting.
 * <p>The rank-revealing QR-decomposition of a matrix A consists of three matrices Q,
 * R and P such that AP=QR.  Q is orthogonal (Q<sup>T</sup>Q = I), and R is upper triangular.
 * If A is m&times;n, Q is m&times;m and R is m&times;n and P is n&times;n.</p>
 * <p>QR decomposition with column pivoting produces a rank-revealing QR
 * decomposition and the {@link #getRank(double)} method may be used to return the rank of the
 * input matrix A.</p>
 * <p>This class compute the decomposition using Householder reflectors.</p>
 * <p>For efficiency purposes, the decomposition in packed form is transposed.
 * This allows inner loop to iterate inside rows, which is much more cache-efficient
 * in Java.</p>
 * <p>This class is based on the class with similar name from the
 * <a href="http://math.nist.gov/javanumerics/jama/">JAMA</a> library, with the
 * following changes:</p>
 * <ul>
 *   <li>a {@link #getQT() getQT} method has been added,</li>
 *   <li>the {@code solve} and {@code isFullRank} methods have been replaced
 *   by a {@link #getSolver() getSolver} method and the equivalent methods
 *   provided by the returned {@link DecompositionSolver}.</li>
 * </ul>
 *
 * @see <a href="http://mathworld.wolfram.com/QRDecomposition.html">MathWorld</a>
 * @see <a href="http://en.wikipedia.org/wiki/QR_decomposition">Wikipedia</a>
 */
public class RRQRDecomposition extends QRDecomposition {

    /**
     * An array to record the column pivoting for later creation of P.
     */
    private int[] p;

    /**
     * Cached value of P.
     */
    private RealMatrix cachedP;

    /**
     * Calculates the QR-decomposition of the given matrix.
     * The singularity threshold defaults to zero.
     *
     * @param matrix The matrix to decompose.
     *
     * @see #RRQRDecomposition(RealMatrix, double)
     */
    public RRQRDecomposition(RealMatrix matrix) {
        this(matrix, 0d);
    }

    /**
     * Calculates the QR-decomposition of the given matrix.
     *
     * @param matrix The matrix to decompose.
     * @param threshold Singularity threshold.
     * @see #RRQRDecomposition(RealMatrix)
     */
    public RRQRDecomposition(RealMatrix matrix, double threshold) {
        super(matrix, threshold);
    }

    /**
     * Decompose matrix.
     * @param qrt transposed matrix
     */
    @Override
    protected void decompose(double[][] qrt) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Perform Householder reflection for a minor A(minor, minor) of A.
     * @param minor minor index
     * @param qrt transposed matrix
     */
    @Override
    protected void performHouseholderReflection(int minor, double[][] qrt) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the pivot matrix, P, used in the QR Decomposition of matrix A such that AP = QR.
     *
     * If no pivoting is used in this decomposition then P is equal to the identity matrix.
     *
     * @return a permutation matrix.
     */
    public RealMatrix getP() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Return the effective numerical matrix rank.
     * <p>The effective numerical rank is the number of non-negligible
     * singular values.</p>
     * <p>This implementation looks at Frobenius norms of the sequence of
     * bottom right submatrices.  When a large fall in norm is seen,
     * the rank is returned. The drop is computed as:</p>
     * <pre>
     *   (thisNorm/lastNorm) * rNorm &lt; dropThreshold
     * </pre>
     * <p>
     * where thisNorm is the Frobenius norm of the current submatrix,
     * lastNorm is the Frobenius norm of the previous submatrix,
     * rNorm is is the Frobenius norm of the complete matrix
     * </p>
     *
     * @param dropThreshold threshold triggering rank computation
     * @return effective numerical matrix rank
     */
    public int getRank(final double dropThreshold) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get a solver for finding the A &times; X = B solution in least square sense.
     * <p>
     * Least Square sense means a solver can be computed for an overdetermined system,
     * (i.e. a system with more equations than unknowns, which corresponds to a tall A
     * matrix with more rows than columns). In any case, if the matrix is singular
     * within the tolerance set at {@link RRQRDecomposition#RRQRDecomposition(RealMatrix,
     * double) construction}, an error will be triggered when
     * the {@link DecompositionSolver#solve(RealVector) solve} method will be called.
     * </p>
     * @return a solver
     */
    @Override
    public DecompositionSolver getSolver() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Specialized solver.
     */
    private static class Solver implements DecompositionSolver {

        /**
         * Upper level solver.
         */
        private final DecompositionSolver upper;

        /**
         * A permutation matrix for the pivots used in the QR decomposition
         */
        private RealMatrix p;

        /**
         * Build a solver from decomposed matrix.
         *
         * @param upper upper level solver.
         * @param p permutation matrix
         */
        private Solver(final DecompositionSolver upper, final RealMatrix p) {
            this.upper = upper;
            this.p = p;
        }

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
        public RealVector solve(RealVector b) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public RealMatrix solve(RealMatrix b) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         * @throws org.hipparchus.exception.MathIllegalArgumentException
         * if the decomposed matrix is singular.
         */
        @Override
        public RealMatrix getInverse() {
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
