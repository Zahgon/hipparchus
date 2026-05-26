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
 * Class transforming a symmetrical matrix to tridiagonal shape.
 * <p>A symmetrical m &times; m matrix A can be written as the product of three matrices:
 * A = Q &times; T &times; Q<sup>T</sup> with Q an orthogonal matrix and T a symmetrical
 * tridiagonal matrix. Both Q and T are m &times; m matrices.</p>
 * <p>This implementation only uses the upper part of the matrix, the part below the
 * diagonal is not accessed at all.</p>
 * <p>Transformation to tridiagonal shape is often not a goal by itself, but it is
 * an intermediate step in more general decomposition algorithms like {@link
 * EigenDecompositionSymmetric eigen decomposition}. This class is therefore intended for internal
 * use by the library and is not public. As a consequence of this explicitly limited scope,
 * many methods directly returns references to internal arrays, not copies.</p>
 */
class TriDiagonalTransformer {

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
     * Cached value of Q.
     */
    private RealMatrix cachedQ;

    /**
     * Cached value of Qt.
     */
    private RealMatrix cachedQt;

    /**
     * Cached value of T.
     */
    private RealMatrix cachedT;

    /**
     * Build the transformation to tridiagonal shape of a symmetrical matrix.
     * <p>The specified matrix is assumed to be symmetrical without any check.
     * Only the upper triangular part of the matrix is used.</p>
     *
     * @param matrix Symmetrical matrix to transform.
     * @throws MathIllegalArgumentException if the matrix is not square.
     */
    TriDiagonalTransformer(RealMatrix matrix) {
        if (!matrix.isSquare()) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.NON_SQUARE_MATRIX, matrix.getRowDimension(), matrix.getColumnDimension());
        }
        final int m = matrix.getRowDimension();
        householderVectors = matrix.getData();
        main = new double[m];
        secondary = new double[m - 1];
        cachedQ = null;
        cachedQt = null;
        cachedT = null;
        // transform matrix
        transform();
    }

    /**
     * Returns the matrix Q of the transform.
     * <p>Q is an orthogonal matrix, i.e. its transpose is also its inverse.</p>
     * @return the Q matrix
     */
    public RealMatrix getQ() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the transpose of the matrix Q of the transform.
     * <p>Q is an orthogonal matrix, i.e. its transpose is also its inverse.</p>
     * @return the Q matrix
     */
    public RealMatrix getQT() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the tridiagonal matrix T of the transform.
     * @return the T matrix
     */
    public RealMatrix getT() {
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
     * Get the main diagonal elements of the matrix T of the transform.
     * <p>Note that since this class is only intended for internal use,
     * it returns directly a reference to its internal arrays, not a copy.</p>
     * @return the main diagonal elements of the T matrix
     */
    double[] getMainDiagonalRef() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the secondary diagonal elements of the matrix T of the transform.
     * <p>Note that since this class is only intended for internal use,
     * it returns directly a reference to its internal arrays, not a copy.</p>
     * @return the secondary diagonal elements of the T matrix
     */
    double[] getSecondaryDiagonalRef() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Transform original matrix to tridiagonal form.
     * <p>Transformation is done using Householder transforms.</p>
     */
    private void transform() {
        final int m = householderVectors.length;
        final double[] z = new double[m];
        for (int k = 0; k < m - 1; k++) {
            //zero-out a row and a column simultaneously
            final double[] hK = householderVectors[k];
            main[k] = hK[k];
            double xNormSqr = 0;
            for (int j = k + 1; j < m; ++j) {
                final double c = hK[j];
                xNormSqr += c * c;
            }
            final double a = (hK[k + 1] > 0) ? -FastMath.sqrt(xNormSqr) : FastMath.sqrt(xNormSqr);
            secondary[k] = a;
            if (a != 0.0) {
                // apply Householder transform from left and right simultaneously
                hK[k + 1] -= a;
                final double beta = -1 / (a * hK[k + 1]);
                // compute a = beta A v, where v is the Householder vector
                // this loop is written in such a way
                //   1) only the upper triangular part of the matrix is accessed
                //   2) access is cache-friendly for a matrix stored in rows
                Arrays.fill(z, k + 1, m, 0);
                for (int i = k + 1; i < m; ++i) {
                    final double[] hI = householderVectors[i];
                    final double hKI = hK[i];
                    double zI = hI[i] * hKI;
                    for (int j = i + 1; j < m; ++j) {
                        final double hIJ = hI[j];
                        zI += hIJ * hK[j];
                        z[j] += hIJ * hKI;
                    }
                    z[i] = beta * (z[i] + zI);
                }
                // compute gamma = beta vT z / 2
                double gamma = 0;
                for (int i = k + 1; i < m; ++i) {
                    gamma += z[i] * hK[i];
                }
                gamma *= beta / 2;
                // compute z = z - gamma v
                for (int i = k + 1; i < m; ++i) {
                    z[i] -= gamma * hK[i];
                }
                // update matrix: A = A - v zT - z vT
                // only the upper triangular part of the matrix is updated
                for (int i = k + 1; i < m; ++i) {
                    final double[] hI = householderVectors[i];
                    for (int j = i; j < m; ++j) {
                        hI[j] -= hK[i] * z[j] + z[i] * hK[j];
                    }
                }
            }
        }
        main[m - 1] = householderVectors[m - 1][m - 1];
    }
}
