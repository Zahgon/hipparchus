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
 * Class transforming any matrix to bi-diagonal shape.
 * <p>Any m &times; n matrix A can be written as the product of three matrices:
 * A = U &times; B &times; V<sup>T</sup> with U an m &times; m orthogonal matrix,
 * B an m &times; n bi-diagonal matrix (lower diagonal if m &lt; n, upper diagonal
 * otherwise), and V an n &times; n orthogonal matrix.</p>
 * <p>Transformation to bi-diagonal shape is often not a goal by itself, but it is
 * an intermediate step in more general decomposition algorithms like {@link
 * SingularValueDecomposition Singular Value Decomposition}. This class is therefore
 * intended for internal use by the library and is not public. As a consequence of
 * this explicitly limited scope, many methods directly returns references to
 * internal arrays, not copies.</p>
 */
class BiDiagonalTransformer {

    /**
     * Householder vectors.
     */
    private final double[][] householderVectors;

    /**
     * Main diagonal.
     */
    private final double[] main;

    /**
     * Secondary diagonal.
     */
    private final double[] secondary;

    /**
     * Cached value of U.
     */
    private RealMatrix cachedU;

    /**
     * Cached value of B.
     */
    private RealMatrix cachedB;

    /**
     * Cached value of V.
     */
    private RealMatrix cachedV;

    /**
     * Build the transformation to bi-diagonal shape of a matrix.
     * @param matrix the matrix to transform.
     */
    BiDiagonalTransformer(RealMatrix matrix) {
        final int m = matrix.getRowDimension();
        final int n = matrix.getColumnDimension();
        final int p = FastMath.min(m, n);
        householderVectors = matrix.getData();
        main = new double[p];
        secondary = new double[p - 1];
        cachedU = null;
        cachedB = null;
        cachedV = null;
        // transform matrix
        if (m >= n) {
            transformToUpperBiDiagonal();
        } else {
            transformToLowerBiDiagonal();
        }
    }

    /**
     * Returns the matrix U of the transform.
     * <p>U is an orthogonal matrix, i.e. its transpose is also its inverse.</p>
     * @return the U matrix
     */
    public RealMatrix getU() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the bi-diagonal matrix B of the transform.
     * @return the B matrix
     */
    public RealMatrix getB() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the matrix V of the transform.
     * <p>V is an orthogonal matrix, i.e. its transpose is also its inverse.</p>
     * @return the V matrix
     */
    public RealMatrix getV() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the Householder vectors of the transform.
     * <p>Note that since this class is only intended for internal use,
     * it returns directly a reference to its internal arrays, not a copy.</p>
     * @return the main diagonal elements of the B matrix
     */
    double[][] getHouseholderVectorsRef() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the main diagonal elements of the matrix B of the transform.
     * <p>Note that since this class is only intended for internal use,
     * it returns directly a reference to its internal arrays, not a copy.</p>
     * @return the main diagonal elements of the B matrix
     */
    double[] getMainDiagonalRef() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the secondary diagonal elements of the matrix B of the transform.
     * <p>Note that since this class is only intended for internal use,
     * it returns directly a reference to its internal arrays, not a copy.</p>
     * @return the secondary diagonal elements of the B matrix
     */
    double[] getSecondaryDiagonalRef() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if the matrix is transformed to upper bi-diagonal.
     * @return true if the matrix is transformed to upper bi-diagonal
     */
    boolean isUpperBiDiagonal() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Transform original matrix to upper bi-diagonal form.
     * <p>Transformation is done using alternate Householder transforms
     * on columns and rows.</p>
     */
    private void transformToUpperBiDiagonal() {
        final int m = householderVectors.length;
        final int n = householderVectors[0].length;
        for (int k = 0; k < n; k++) {
            //zero-out a column
            double xNormSqr = 0;
            for (int i = k; i < m; ++i) {
                final double c = householderVectors[i][k];
                xNormSqr += c * c;
            }
            final double[] hK = householderVectors[k];
            final double a = (hK[k] > 0) ? -FastMath.sqrt(xNormSqr) : FastMath.sqrt(xNormSqr);
            main[k] = a;
            if (a != 0.0) {
                hK[k] -= a;
                for (int j = k + 1; j < n; ++j) {
                    double alpha = 0;
                    for (int i = k; i < m; ++i) {
                        final double[] hI = householderVectors[i];
                        alpha -= hI[j] * hI[k];
                    }
                    alpha /= a * householderVectors[k][k];
                    for (int i = k; i < m; ++i) {
                        final double[] hI = householderVectors[i];
                        hI[j] -= alpha * hI[k];
                    }
                }
            }
            if (k < n - 1) {
                //zero-out a row
                xNormSqr = 0;
                for (int j = k + 1; j < n; ++j) {
                    final double c = hK[j];
                    xNormSqr += c * c;
                }
                final double b = (hK[k + 1] > 0) ? -FastMath.sqrt(xNormSqr) : FastMath.sqrt(xNormSqr);
                secondary[k] = b;
                if (b != 0.0) {
                    hK[k + 1] -= b;
                    for (int i = k + 1; i < m; ++i) {
                        final double[] hI = householderVectors[i];
                        double beta = 0;
                        for (int j = k + 1; j < n; ++j) {
                            beta -= hI[j] * hK[j];
                        }
                        beta /= b * hK[k + 1];
                        for (int j = k + 1; j < n; ++j) {
                            hI[j] -= beta * hK[j];
                        }
                    }
                }
            }
        }
    }

    /**
     * Transform original matrix to lower bi-diagonal form.
     * <p>Transformation is done using alternate Householder transforms
     * on rows and columns.</p>
     */
    private void transformToLowerBiDiagonal() {
        final int m = householderVectors.length;
        final int n = householderVectors[0].length;
        for (int k = 0; k < m; k++) {
            //zero-out a row
            final double[] hK = householderVectors[k];
            double xNormSqr = 0;
            for (int j = k; j < n; ++j) {
                final double c = hK[j];
                xNormSqr += c * c;
            }
            final double a = (hK[k] > 0) ? -FastMath.sqrt(xNormSqr) : FastMath.sqrt(xNormSqr);
            main[k] = a;
            if (a != 0.0) {
                hK[k] -= a;
                for (int i = k + 1; i < m; ++i) {
                    final double[] hI = householderVectors[i];
                    double alpha = 0;
                    for (int j = k; j < n; ++j) {
                        alpha -= hI[j] * hK[j];
                    }
                    alpha /= a * householderVectors[k][k];
                    for (int j = k; j < n; ++j) {
                        hI[j] -= alpha * hK[j];
                    }
                }
            }
            if (k < m - 1) {
                //zero-out a column
                final double[] hKp1 = householderVectors[k + 1];
                xNormSqr = 0;
                for (int i = k + 1; i < m; ++i) {
                    final double c = householderVectors[i][k];
                    xNormSqr += c * c;
                }
                final double b = (hKp1[k] > 0) ? -FastMath.sqrt(xNormSqr) : FastMath.sqrt(xNormSqr);
                secondary[k] = b;
                if (b != 0.0) {
                    hKp1[k] -= b;
                    for (int j = k + 1; j < n; ++j) {
                        double beta = 0;
                        for (int i = k + 1; i < m; ++i) {
                            final double[] hI = householderVectors[i];
                            beta -= hI[j] * hI[k];
                        }
                        beta /= b * hKp1[k];
                        for (int i = k + 1; i < m; ++i) {
                            final double[] hI = householderVectors[i];
                            hI[j] -= beta * hI[k];
                        }
                    }
                }
            }
        }
    }
}
