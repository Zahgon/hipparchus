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
package org.hipparchus.stat.descriptive;

import java.io.Serializable;
import java.util.Arrays;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.linear.RealMatrix;
import org.hipparchus.stat.descriptive.moment.GeometricMean;
import org.hipparchus.stat.descriptive.moment.Mean;
import org.hipparchus.stat.descriptive.rank.Max;
import org.hipparchus.stat.descriptive.rank.Min;
import org.hipparchus.stat.descriptive.summary.Sum;
import org.hipparchus.stat.descriptive.summary.SumOfLogs;
import org.hipparchus.stat.descriptive.summary.SumOfSquares;
import org.hipparchus.stat.descriptive.vector.VectorialCovariance;
import org.hipparchus.stat.descriptive.vector.VectorialStorelessStatistic;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.MathArrays;
import org.hipparchus.util.MathUtils;

/**
 * Computes summary statistics for a stream of n-tuples added using the
 * {@link #addValue(double[]) addValue} method. The data values are not stored
 * in memory, so this class can be used to compute statistics for very large
 * n-tuple streams.
 * <p>
 * To compute statistics for a stream of n-tuples, construct a
 * {@link MultivariateSummaryStatistics} instance with dimension n and then use
 * {@link #addValue(double[])} to add n-tuples. The <code>getXxx</code>
 * methods where Xxx is a statistic return an array of <code>double</code>
 * values, where for <code>i = 0,...,n-1</code> the i<sup>th</sup> array element
 * is the value of the given statistic for data range consisting of the i<sup>th</sup>
 * element of each of the input n-tuples.  For example, if <code>addValue</code> is
 * called with actual parameters {0, 1, 2}, then {3, 4, 5} and finally {6, 7, 8},
 * <code>getSum</code> will return a three-element array with values {0+3+6, 1+4+7, 2+5+8}
 * <p>
 * Note: This class is not thread-safe.
 */
public class MultivariateSummaryStatistics implements StatisticalMultivariateSummary, Serializable {

    /**
     * Serialization UID
     */
    private static final long serialVersionUID = 20160424L;

    /**
     * Dimension of the data.
     */
    private final int k;

    /**
     * Sum statistic implementation
     */
    private final StorelessMultivariateStatistic sumImpl;

    /**
     * Sum of squares statistic implementation
     */
    private final StorelessMultivariateStatistic sumSqImpl;

    /**
     * Minimum statistic implementation
     */
    private final StorelessMultivariateStatistic minImpl;

    /**
     * Maximum statistic implementation
     */
    private final StorelessMultivariateStatistic maxImpl;

    /**
     * Sum of log statistic implementation
     */
    private final StorelessMultivariateStatistic sumLogImpl;

    /**
     * Geometric mean statistic implementation
     */
    private final StorelessMultivariateStatistic geoMeanImpl;

    /**
     * Mean statistic implementation
     */
    private final StorelessMultivariateStatistic meanImpl;

    /**
     * Covariance statistic implementation
     */
    private final VectorialCovariance covarianceImpl;

    /**
     * Count of values that have been added
     */
    private long n;

    /**
     * Construct a MultivariateSummaryStatistics instance for the given
     * dimension. The returned instance will compute the unbiased sample
     * covariance.
     * <p>
     * The returned instance is <b>not</b> thread-safe.
     *
     * @param dimension dimension of the data
     */
    public MultivariateSummaryStatistics(int dimension) {
        this(dimension, true);
    }

    /**
     * Construct a MultivariateSummaryStatistics instance for the given
     * dimension.
     * <p>
     * The returned instance is <b>not</b> thread-safe.
     *
     * @param dimension dimension of the data
     * @param covarianceBiasCorrection if true, the returned instance will compute
     * the unbiased sample covariance, otherwise the population covariance
     */
    public MultivariateSummaryStatistics(int dimension, boolean covarianceBiasCorrection) {
        this.k = dimension;
        sumImpl = new VectorialStorelessStatistic(k, new Sum());
        sumSqImpl = new VectorialStorelessStatistic(k, new SumOfSquares());
        minImpl = new VectorialStorelessStatistic(k, new Min());
        maxImpl = new VectorialStorelessStatistic(k, new Max());
        sumLogImpl = new VectorialStorelessStatistic(k, new SumOfLogs());
        geoMeanImpl = new VectorialStorelessStatistic(k, new GeometricMean());
        meanImpl = new VectorialStorelessStatistic(k, new Mean());
        covarianceImpl = new VectorialCovariance(k, covarianceBiasCorrection);
    }

    /**
     * Add an n-tuple to the data
     *
     * @param value  the n-tuple to add
     * @throws MathIllegalArgumentException if the array is null or the length
     * of the array does not match the one used at construction
     */
    public void addValue(double[] value) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Resets all statistics and storage.
     */
    public void clear() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc} *
     */
    @Override
    public int getDimension() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc} *
     */
    @Override
    public long getN() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc} *
     */
    @Override
    public double[] getSum() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc} *
     */
    @Override
    public double[] getSumSq() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc} *
     */
    @Override
    public double[] getSumLog() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc} *
     */
    @Override
    public double[] getMean() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc} *
     */
    @Override
    public RealMatrix getCovariance() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc} *
     */
    @Override
    public double[] getMax() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc} *
     */
    @Override
    public double[] getMin() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc} *
     */
    @Override
    public double[] getGeometricMean() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns an array whose i<sup>th</sup> entry is the standard deviation of the
     * i<sup>th</sup> entries of the arrays that have been added using
     * {@link #addValue(double[])}
     *
     * @return the array of component standard deviations
     */
    @Override
    public double[] getStandardDeviation() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Generates a text report displaying
     * summary statistics from values that
     * have been added.
     * @return String with line feeds displaying statistics
     */
    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Append a text representation of an array to a buffer.
     * @param buffer buffer to fill
     * @param data data array
     * @param prefix text prefix
     * @param separator elements separator
     * @param suffix text suffix
     */
    private void append(StringBuilder buffer, double[] data, String prefix, String separator, String suffix) {
        buffer.append(prefix);
        for (int i = 0; i < data.length; ++i) {
            if (i > 0) {
                buffer.append(separator);
            }
            buffer.append(data[i]);
        }
        buffer.append(suffix);
    }

    /**
     * Returns true iff <code>object</code> is a <code>MultivariateSummaryStatistics</code>
     * instance and all statistics have the same values as this.
     * @param object the object to test equality against.
     * @return true if object equals this
     */
    @Override
    public boolean equals(Object object) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns hash code based on values of statistics
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
