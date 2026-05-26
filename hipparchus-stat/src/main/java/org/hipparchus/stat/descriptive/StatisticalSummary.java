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

import java.util.Arrays;
import org.hipparchus.util.MathUtils;

/**
 * Reporting interface for basic univariate statistics.
 */
public interface StatisticalSummary {

    /**
     * Computes aggregated statistical summaries.
     * <p>
     * This method can be used to combine statistics computed over partitions or
     * subsamples - i.e., the returned StatisticalSummary should contain
     * the same values that would have been obtained by computing a single
     * StatisticalSummary over the combined dataset.
     *
     * @param statistics StatisticalSummary instances to aggregate
     * @return summary statistics for the combined dataset
     * @throws org.hipparchus.exception.NullArgumentException if the input is null
     */
    static StatisticalSummary aggregate(StatisticalSummary... statistics) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Computes aggregated statistical summaries.
     * <p>
     * This method can be used to combine statistics computed over partitions or
     * subsamples - i.e., the returned StatisticalSummary should contain
     * the same values that would have been obtained by computing a single
     * StatisticalSummary over the combined dataset.
     *
     * @param statistics iterable of StatisticalSummary instances to aggregate
     * @return summary statistics for the combined dataset
     * @throws org.hipparchus.exception.NullArgumentException if the input is null
     */
    static StatisticalSummary aggregate(Iterable<? extends StatisticalSummary> statistics) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the <a href="http://www.xycoon.com/arithmetic_mean.htm">
     * arithmetic mean </a> of the available values
     * @return The mean or Double.NaN if no values have been added.
     */
    double getMean();

    /**
     * Returns the variance of the available values.
     * @return The variance, Double.NaN if no values have been added
     * or 0.0 for a single value set.
     */
    double getVariance();

    /**
     * Returns the standard deviation of the available values.
     * @return The standard deviation, Double.NaN if no values have been added
     * or 0.0 for a single value set.
     */
    double getStandardDeviation();

    /**
     * Returns the maximum of the available values
     * @return The max or Double.NaN if no values have been added.
     */
    double getMax();

    /**
     * Returns the minimum of the available values
     * @return The min or Double.NaN if no values have been added.
     */
    double getMin();

    /**
     * Returns the number of available values
     * @return The number of available values
     */
    long getN();

    /**
     * Returns the sum of the values that have been added to Univariate.
     * @return The sum or Double.NaN if no values have been added
     */
    double getSum();
}
