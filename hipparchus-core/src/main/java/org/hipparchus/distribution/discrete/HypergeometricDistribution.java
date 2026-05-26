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
package org.hipparchus.distribution.discrete;

import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.util.FastMath;

/**
 * Implementation of the hypergeometric distribution.
 *
 * @see <a href="http://en.wikipedia.org/wiki/Hypergeometric_distribution">Hypergeometric distribution (Wikipedia)</a>
 * @see <a href="http://mathworld.wolfram.com/HypergeometricDistribution.html">Hypergeometric distribution (MathWorld)</a>
 */
public class HypergeometricDistribution extends AbstractIntegerDistribution {

    /**
     * Serializable version identifier.
     */
    private static final long serialVersionUID = 20160320L;

    /**
     * The number of successes in the population.
     */
    private final int numberOfSuccesses;

    /**
     * The population size.
     */
    private final int populationSize;

    /**
     * The sample size.
     */
    private final int sampleSize;

    /**
     * Cached numerical variance
     */
    private final double numericalVariance;

    /**
     * Construct a new hypergeometric distribution with the specified population
     * size, number of successes in the population, and sample size.
     *
     * @param populationSize Population size.
     * @param numberOfSuccesses Number of successes in the population.
     * @param sampleSize Sample size.
     * @throws MathIllegalArgumentException if {@code numberOfSuccesses < 0}.
     * @throws MathIllegalArgumentException if {@code populationSize <= 0}.
     * @throws MathIllegalArgumentException if {@code numberOfSuccesses > populationSize},
     * or {@code sampleSize > populationSize}.
     */
    public HypergeometricDistribution(int populationSize, int numberOfSuccesses, int sampleSize) throws MathIllegalArgumentException {
        if (populationSize <= 0) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.POPULATION_SIZE, populationSize);
        }
        if (numberOfSuccesses < 0) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.NUMBER_OF_SUCCESSES, numberOfSuccesses);
        }
        if (sampleSize < 0) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.NUMBER_OF_SAMPLES, sampleSize);
        }
        if (numberOfSuccesses > populationSize) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.NUMBER_OF_SUCCESS_LARGER_THAN_POPULATION_SIZE, numberOfSuccesses, populationSize, true);
        }
        if (sampleSize > populationSize) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.SAMPLE_SIZE_LARGER_THAN_POPULATION_SIZE, sampleSize, populationSize, true);
        }
        this.numberOfSuccesses = numberOfSuccesses;
        this.populationSize = populationSize;
        this.sampleSize = sampleSize;
        this.numericalVariance = calculateNumericalVariance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double cumulativeProbability(int x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Return the domain for the given hypergeometric distribution parameters.
     *
     * @param n Population size.
     * @param m Number of successes in the population.
     * @param k Sample size.
     * @return a two element array containing the lower and upper bounds of the
     * hypergeometric distribution.
     */
    private int[] getDomain(int n, int m, int k) {
        return new int[] { getLowerDomain(n, m, k), getUpperDomain(m, k) };
    }

    /**
     * Return the lowest domain value for the given hypergeometric distribution
     * parameters.
     *
     * @param n Population size.
     * @param m Number of successes in the population.
     * @param k Sample size.
     * @return the lowest domain value of the hypergeometric distribution.
     */
    private int getLowerDomain(int n, int m, int k) {
        return FastMath.max(0, m - (n - k));
    }

    /**
     * Access the number of successes.
     *
     * @return the number of successes.
     */
    public int getNumberOfSuccesses() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Access the population size.
     *
     * @return the population size.
     */
    public int getPopulationSize() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Access the sample size.
     *
     * @return the sample size.
     */
    public int getSampleSize() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Return the highest domain value for the given hypergeometric distribution
     * parameters.
     *
     * @param m Number of successes in the population.
     * @param k Sample size.
     * @return the highest domain value of the hypergeometric distribution.
     */
    private int getUpperDomain(int m, int k) {
        return FastMath.min(k, m);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double probability(int x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double logProbability(int x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * For this distribution, {@code X}, this method returns {@code P(X >= x)}.
     *
     * @param x Value at which the CDF is evaluated.
     * @return the upper tail CDF for this distribution.
     */
    public double upperCumulativeProbability(int x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * For this distribution, {@code X}, this method returns
     * {@code P(x0 <= X <= x1)}.
     * This probability is computed by summing the point probabilities for the
     * values {@code x0, x0 + 1, x0 + 2, ..., x1}, in the order directed by
     * {@code dx}.
     *
     * @param x0 Inclusive lower bound.
     * @param x1 Inclusive upper bound.
     * @param dx Direction of summation (1 indicates summing from x0 to x1, and
     * 0 indicates summing from x1 to x0).
     * @return {@code P(x0 <= X <= x1)}.
     */
    private double innerCumulativeProbability(int x0, int x1, int dx) {
        double ret = probability(x0);
        while (x0 != x1) {
            x0 += dx;
            ret += probability(x0);
        }
        return ret;
    }

    /**
     * {@inheritDoc}
     *
     * For population size {@code N}, number of successes {@code m}, and sample
     * size {@code n}, the mean is {@code n * m / N}.
     */
    @Override
    public double getNumericalMean() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * For population size {@code N}, number of successes {@code m}, and sample
     * size {@code n}, the variance is
     * {@code [n * m * (N - n) * (N - m)] / [N^2 * (N - 1)]}.
     */
    @Override
    public double getNumericalVariance() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Calculate the numerical variance.
     *
     * @return the variance of this distribution
     */
    private double calculateNumericalVariance() {
        final double N = getPopulationSize();
        final double m = getNumberOfSuccesses();
        final double n = getSampleSize();
        return (n * m * (N - n) * (N - m)) / (N * N * (N - 1));
    }

    /**
     * {@inheritDoc}
     *
     * For population size {@code N}, number of successes {@code m}, and sample
     * size {@code n}, the lower bound of the support is
     * {@code max(0, n + m - N)}.
     *
     * @return lower bound of the support
     */
    @Override
    public int getSupportLowerBound() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * For number of successes {@code m} and sample size {@code n}, the upper
     * bound of the support is {@code min(m, n)}.
     *
     * @return upper bound of the support
     */
    @Override
    public int getSupportUpperBound() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * The support of this distribution is connected.
     *
     * @return {@code true}
     */
    @Override
    public boolean isSupportConnected() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
