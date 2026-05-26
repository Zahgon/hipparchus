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
package org.hipparchus.stat.correlation;

import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.linear.BlockRealMatrix;
import org.hipparchus.linear.RealMatrix;
import org.hipparchus.stat.descriptive.moment.Mean;
import org.hipparchus.stat.descriptive.moment.Variance;

/**
 * Computes covariances for pairs of arrays or columns of a matrix.
 * <p>
 * The constructors that take {@code RealMatrix} or {@code double[][]}
 * arguments generate covariance matrices. The columns of the input
 * matrices are assumed to represent variable values.
 * <p>
 * The constructor argument {@code biasCorrected} determines whether or
 * not computed covariances are bias-corrected.
 * <p>
 * Unbiased covariances are given by the formula:
 * <p>
 * <code>cov(X, Y) = &Sigma;[(x<sub>i</sub> - E(X))(y<sub>i</sub> - E(Y))] / (n - 1)</code>
 * <p>
 * where {@code E(X)} is the mean of {@code X} and {@code E(Y)}
 * is the mean of the <code>Y</code> values.
 * <p>
 * Non-bias-corrected estimates use {@code n} in place of {@code n - 1}.
 */
public class Covariance {

    /**
     * The covariance matrix.
     */
    private final RealMatrix covarianceMatrix;

    /**
     * Number of observations (length of covariate vectors).
     */
    private final int n;

    /**
     * Create a Covariance with no data.
     */
    public Covariance() {
        covarianceMatrix = null;
        n = 0;
    }

    /**
     * Create a Covariance matrix from a rectangular array
     * whose columns represent covariates.
     * <p>
     * The <code>biasCorrected</code> parameter determines whether or not
     * covariance estimates are bias-corrected.
     * <p>
     * The input array must be rectangular with at least one column
     * and two rows.
     *
     * @param data rectangular array with columns representing covariates
     * @param biasCorrected true means covariances are bias-corrected
     * @throws MathIllegalArgumentException if the input data array is not
     * rectangular with at least two rows and one column.
     * @throws MathIllegalArgumentException if the input data array is not
     * rectangular with at least one row and one column.
     */
    public Covariance(double[][] data, boolean biasCorrected) throws MathIllegalArgumentException {
        this(new BlockRealMatrix(data), biasCorrected);
    }

    /**
     * Create a Covariance matrix from a rectangular array
     * whose columns represent covariates.
     * <p>
     * The input array must be rectangular with at least one column
     * and two rows.
     *
     * @param data rectangular array with columns representing covariates
     * @throws MathIllegalArgumentException if the input data array is not
     * rectangular with at least two rows and one column.
     * @throws MathIllegalArgumentException if the input data array is not
     * rectangular with at least one row and one column.
     */
    public Covariance(double[][] data) throws MathIllegalArgumentException {
        this(data, true);
    }

    /**
     * Create a covariance matrix from a matrix whose columns
     * represent covariates.
     * <p>
     * The <code>biasCorrected</code> parameter determines whether or not
     * covariance estimates are bias-corrected.
     * <p>
     * The matrix must have at least one column and two rows.
     *
     * @param matrix matrix with columns representing covariates
     * @param biasCorrected true means covariances are bias-corrected
     * @throws MathIllegalArgumentException if the input matrix does not have
     * at least two rows and one column
     */
    public Covariance(RealMatrix matrix, boolean biasCorrected) throws MathIllegalArgumentException {
        checkSufficientData(matrix);
        n = matrix.getRowDimension();
        covarianceMatrix = computeCovarianceMatrix(matrix, biasCorrected);
    }

    /**
     * Create a covariance matrix from a matrix whose columns
     * represent covariates.
     * <p>
     * The matrix must have at least one column and two rows.
     *
     * @param matrix matrix with columns representing covariates
     * @throws MathIllegalArgumentException if the input matrix does not have
     * at least two rows and one column
     */
    public Covariance(RealMatrix matrix) throws MathIllegalArgumentException {
        this(matrix, true);
    }

    /**
     * Returns the covariance matrix
     *
     * @return covariance matrix
     */
    public RealMatrix getCovarianceMatrix() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the number of observations (length of covariate vectors)
     *
     * @return number of observations
     */
    public int getN() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute a covariance matrix from a matrix whose columns represent covariates.
     *
     * @param matrix input matrix (must have at least one column and two rows)
     * @param biasCorrected determines whether or not covariance estimates are bias-corrected
     * @return covariance matrix
     * @throws MathIllegalArgumentException if the matrix does not contain sufficient data
     */
    protected RealMatrix computeCovarianceMatrix(RealMatrix matrix, boolean biasCorrected) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Create a covariance matrix from a matrix whose columns represent
     * covariates. Covariances are computed using the bias-corrected formula.
     *
     * @param matrix input matrix (must have at least one column and two rows)
     * @return covariance matrix
     * @throws MathIllegalArgumentException if matrix does not contain sufficient data
     * @see #Covariance
     */
    protected RealMatrix computeCovarianceMatrix(RealMatrix matrix) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute a covariance matrix from a rectangular array whose columns represent covariates.
     *
     * @param data input array (must have at least one column and two rows)
     * @param biasCorrected determines whether or not covariance estimates are bias-corrected
     * @return covariance matrix
     * @throws MathIllegalArgumentException if the data array does not contain sufficient data
     * @throws MathIllegalArgumentException if the input data array is not
     * rectangular with at least one row and one column.
     */
    protected RealMatrix computeCovarianceMatrix(double[][] data, boolean biasCorrected) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Create a covariance matrix from a rectangular array whose columns represent
     * covariates. Covariances are computed using the bias-corrected formula.
     *
     * @param data input array (must have at least one column and two rows)
     * @return covariance matrix
     * @throws MathIllegalArgumentException if the data array does not contain sufficient data
     * @throws MathIllegalArgumentException if the input data array is not
     * rectangular with at least one row and one column.
     * @see #Covariance
     */
    protected RealMatrix computeCovarianceMatrix(double[][] data) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Computes the covariance between the two arrays.
     * <p>
     * Array lengths must match and the common length must be at least 2.
     *
     * @param xArray first data array
     * @param yArray second data array
     * @param biasCorrected if true, returned value will be bias-corrected
     * @return returns the covariance for the two arrays
     * @throws  MathIllegalArgumentException if the arrays lengths do not match or
     * there is insufficient data
     */
    public double covariance(final double[] xArray, final double[] yArray, boolean biasCorrected) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Computes the covariance between the two arrays, using the bias-corrected
     * formula.
     * <p>
     * Array lengths must match and the common length must be at least 2.
     *
     * @param xArray first data array
     * @param yArray second data array
     * @return returns the covariance for the two arrays
     * @throws MathIllegalArgumentException if the arrays lengths do not match or
     * there is insufficient data
     */
    public double covariance(final double[] xArray, final double[] yArray) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Throws MathIllegalArgumentException if the matrix does not have at least
     * one column and two rows.
     *
     * @param matrix matrix to check
     * @throws MathIllegalArgumentException if the matrix does not contain sufficient data
     * to compute covariance
     */
    private void checkSufficientData(final RealMatrix matrix) throws MathIllegalArgumentException {
        int nRows = matrix.getRowDimension();
        int nCols = matrix.getColumnDimension();
        if (nRows < 2 || nCols < 1) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.INSUFFICIENT_ROWS_AND_COLUMNS, nRows, nCols);
        }
    }
}
