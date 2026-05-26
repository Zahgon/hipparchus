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
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.util.FastMath;

/**
 * Calculates the QR-decomposition of a matrix.
 * <p>The QR-decomposition of a matrix A consists of two matrices Q and R
 * that satisfy: A = QR, Q is orthogonal (Q<sup>T</sup>Q = I), and R is
 * upper triangular. If A is m&times;n, Q is m&times;m and R m&times;n.</p>
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
public class QRDecomposition {

    /**
     * A packed TRANSPOSED representation of the QR decomposition.
     * <p>The elements BELOW the diagonal are the elements of the UPPER triangular
     * matrix R, and the rows ABOVE the diagonal are the Householder reflector vectors
     * from which an explicit form of Q can be recomputed if desired.</p>
     */
    private double[][] qrt;

    /**
     * The diagonal elements of R.
     */
    private double[] rDiag;

    /**
     * Cached value of Q.
     */
    private RealMatrix cachedQ;

    /**
     * Cached value of QT.
     */
    private RealMatrix cachedQT;

    /**
     * Cached value of R.
     */
    private RealMatrix cachedR;

    /**
     * Cached value of H.
     */
    private RealMatrix cachedH;

    /**
     * Singularity threshold.
     */
    private final double threshold;

    /**
     * Calculates the QR-decomposition of the given matrix.
     * The singularity threshold defaults to zero.
     *
     * @param matrix The matrix to decompose.
     *
     * @see #QRDecomposition(RealMatrix,double)
     */
    public QRDecomposition(RealMatrix matrix) {
        this(matrix, 0d);
    }

    /**
     * Calculates the QR-decomposition of the given matrix.
     *
     * @param matrix The matrix to decompose.
     * @param threshold Singularity threshold.
     */
    public QRDecomposition(RealMatrix matrix, double threshold) {
        this.threshold = threshold;
        final int m = matrix.getRowDimension();
        final int n = matrix.getColumnDimension();
        qrt = matrix.transpose().getData();
        rDiag = new double[FastMath.min(m, n)];
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
    protected void decompose(double[][] matrix) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Perform Householder reflection for a minor A(minor, minor) of A.
     * @param minor minor index
     * @param matrix transposed matrix
     */
    protected void performHouseholderReflection(int minor, double[][] matrix) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the matrix R of the decomposition.
     * <p>R is an upper-triangular matrix</p>
     * @return the R matrix
     */
    public RealMatrix getR() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the matrix Q of the decomposition.
     * <p>Q is an orthogonal matrix</p>
     * @return the Q matrix
     */
    public RealMatrix getQ() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the transpose of the matrix Q of the decomposition.
     * <p>Q is an orthogonal matrix</p>
     * @return the transpose of the Q matrix, Q<sup>T</sup>
     */
    public RealMatrix getQT() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the Householder reflector vectors.
     * <p>H is a lower trapezoidal matrix whose columns represent
     * each successive Householder reflector vector. This matrix is used
     * to compute Q.</p>
     * @return a matrix containing the Householder reflector vectors
     */
    public RealMatrix getH() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get a solver for finding the A &times; X = B solution in least square sense.
     * <p>
     * Least Square sense means a solver can be computed for an overdetermined system,
     * (i.e. a system with more equations than unknowns, which corresponds to a tall A
     * matrix with more rows than columns). In any case, if the matrix is singular
     * within the tolerance set at {@link QRDecomposition#QRDecomposition(RealMatrix,
     * double) construction}, an error will be triggered when
     * the {@link DecompositionSolver#solve(RealVector) solve} method will be called.
     * </p>
     * @return a solver
     */
    public DecompositionSolver getSolver() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Specialized solver.
     */
    private class Solver implements DecompositionSolver {

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
         * @throws MathIllegalArgumentException if the decomposed matrix is singular.
         */
        @Override
        public RealMatrix getInverse() {
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
        private boolean checkSingular(double[] diag, double min, boolean raise) {
            for (final double d : diag) {
                if (FastMath.abs(d) <= min) {
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
