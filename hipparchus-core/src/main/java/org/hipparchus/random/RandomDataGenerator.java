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
package org.hipparchus.random;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.hipparchus.distribution.EnumeratedDistribution;
import org.hipparchus.distribution.IntegerDistribution;
import org.hipparchus.distribution.RealDistribution;
import org.hipparchus.distribution.continuous.BetaDistribution;
import org.hipparchus.distribution.continuous.EnumeratedRealDistribution;
import org.hipparchus.distribution.continuous.ExponentialDistribution;
import org.hipparchus.distribution.continuous.GammaDistribution;
import org.hipparchus.distribution.continuous.LogNormalDistribution;
import org.hipparchus.distribution.continuous.NormalDistribution;
import org.hipparchus.distribution.continuous.UniformRealDistribution;
import org.hipparchus.distribution.discrete.EnumeratedIntegerDistribution;
import org.hipparchus.distribution.discrete.PoissonDistribution;
import org.hipparchus.distribution.discrete.UniformIntegerDistribution;
import org.hipparchus.distribution.discrete.ZipfDistribution;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.util.CombinatoricsUtils;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.MathArrays;
import org.hipparchus.util.MathUtils;
import org.hipparchus.util.Pair;
import org.hipparchus.util.Precision;
import org.hipparchus.util.ResizableDoubleArray;

/**
 * A class for generating random data.
 */
public class RandomDataGenerator extends ForwardingRandomGenerator implements // NOPMD - this class has a high number of methods, it is normal
RandomGenerator, // NOPMD - this class has a high number of methods, it is normal
Serializable {

    /**
     * Serializable version identifier.
     */
    private static final long serialVersionUID = 20160529L;

    /**
     * Used when generating Exponential samples.
     * Table containing the constants
     * q_i = sum_{j=1}^i (ln 2)^j/j! = ln 2 + (ln 2)^2/2 + ... + (ln 2)^i/i!
     * until the largest representable fraction below 1 is exceeded.
     *
     * Note that
     * 1 = 2 - 1 = exp(ln 2) - 1 = sum_{n=1}^infty (ln 2)^n / n!
     * thus q_i -> 1 as i -> +inf,
     * so the higher i, the closer to one we get (the series is not alternating).
     *
     * By trying, n = 16 in Java is enough to reach 1.0.
     */
    private static final double[] EXPONENTIAL_SA_QI;

    /**
     * Map of <classname, switch constant> for continuous distributions
     */
    private static final Map<Class<? extends RealDistribution>, RealDistributionSampler> CONTINUOUS_SAMPLERS = new ConcurrentHashMap<>();

    /**
     * Map of <classname, switch constant> for discrete distributions
     */
    private static final Map<Class<? extends IntegerDistribution>, IntegerDistributionSampler> DISCRETE_SAMPLERS = new ConcurrentHashMap<>();

    /**
     * The default sampler for continuous distributions using the inversion technique.
     */
    private static final RealDistributionSampler DEFAULT_REAL_SAMPLER = (generator, dist) -> dist.inverseCumulativeProbability(generator.nextDouble());

    /**
     * The default sampler for discrete distributions using the inversion technique.
     */
    private static final IntegerDistributionSampler DEFAULT_INTEGER_SAMPLER = (generator, dist) -> dist.inverseCumulativeProbability(generator.nextDouble());

    /**
     * Source of random data
     */
    private final RandomGenerator randomGenerator;

    /**
     * The sampler to be used for the nextZipF method
     */
    private transient ZipfRejectionInversionSampler zipfSampler;

    /**
     * Interface for samplers of continuous distributions.
     */
    @FunctionalInterface
    private interface RealDistributionSampler {

        /**
         * Return the next sample following the given distribution.
         *
         * @param generator the random data generator to use
         * @param distribution the distribution to use
         * @return the next sample
         */
        double nextSample(RandomDataGenerator generator, RealDistribution distribution);
    }

    /**
     * Interface for samplers of discrete distributions.
     */
    @FunctionalInterface
    private interface IntegerDistributionSampler {

        /**
         * Return the next sample following the given distribution.
         *
         * @param generator the random data generator to use
         * @param distribution the distribution to use
         * @return the next sample
         */
        int nextSample(RandomDataGenerator generator, IntegerDistribution distribution);
    }

    /**
     * Initialize tables.
     */
    static {
        /**
         * Filling EXPONENTIAL_SA_QI table.
         * Note that we don't want qi = 0 in the table.
         */
        final double LN2 = FastMath.log(2);
        double qi = 0;
        int i = 1;
        /**
         * ArithmeticUtils provides factorials up to 20, so let's use that
         * limit together with Precision.EPSILON to generate the following
         * code (a priori, we know that there will be 16 elements, but it is
         * better to not hardcode it).
         */
        final ResizableDoubleArray ra = new ResizableDoubleArray(20);
        while (qi < 1) {
            qi += FastMath.pow(LN2, i) / CombinatoricsUtils.factorial(i);
            ra.addElement(qi);
            ++i;
        }
        EXPONENTIAL_SA_QI = ra.getElements();
        // Continuous samplers
        CONTINUOUS_SAMPLERS.put(BetaDistribution.class, (generator, dist) -> {
            BetaDistribution beta = (BetaDistribution) dist;
            return generator.nextBeta(beta.getAlpha(), beta.getBeta());
        });
        CONTINUOUS_SAMPLERS.put(ExponentialDistribution.class, (generator, dist) -> generator.nextExponential(dist.getNumericalMean()));
        CONTINUOUS_SAMPLERS.put(GammaDistribution.class, (generator, dist) -> {
            GammaDistribution gamma = (GammaDistribution) dist;
            return generator.nextGamma(gamma.getShape(), gamma.getScale());
        });
        CONTINUOUS_SAMPLERS.put(NormalDistribution.class, (generator, dist) -> {
            NormalDistribution normal = (NormalDistribution) dist;
            return generator.nextNormal(normal.getMean(), normal.getStandardDeviation());
        });
        CONTINUOUS_SAMPLERS.put(LogNormalDistribution.class, (generator, dist) -> {
            LogNormalDistribution logNormal = (LogNormalDistribution) dist;
            return generator.nextLogNormal(logNormal.getShape(), logNormal.getLocation());
        });
        CONTINUOUS_SAMPLERS.put(UniformRealDistribution.class, (generator, dist) -> generator.nextUniform(dist.getSupportLowerBound(), dist.getSupportUpperBound()));
        CONTINUOUS_SAMPLERS.put(EnumeratedRealDistribution.class, (generator, dist) -> {
            final EnumeratedRealDistribution edist = (EnumeratedRealDistribution) dist;
            EnumeratedDistributionSampler<Double> sampler = generator.new EnumeratedDistributionSampler<>(edist.getPmf());
            return sampler.sample();
        });
        // Discrete samplers
        DISCRETE_SAMPLERS.put(PoissonDistribution.class, (generator, dist) -> generator.nextPoisson(dist.getNumericalMean()));
        DISCRETE_SAMPLERS.put(UniformIntegerDistribution.class, (generator, dist) -> generator.nextInt(dist.getSupportLowerBound(), dist.getSupportUpperBound()));
        DISCRETE_SAMPLERS.put(ZipfDistribution.class, (generator, dist) -> {
            ZipfDistribution zipfDist = (ZipfDistribution) dist;
            return generator.nextZipf(zipfDist.getNumberOfElements(), zipfDist.getExponent());
        });
        DISCRETE_SAMPLERS.put(EnumeratedIntegerDistribution.class, (generator, dist) -> {
            final EnumeratedIntegerDistribution edist = (EnumeratedIntegerDistribution) dist;
            EnumeratedDistributionSampler<Integer> sampler = generator.new EnumeratedDistributionSampler<>(edist.getPmf());
            return sampler.sample();
        });
    }

    /**
     * Construct a RandomDataGenerator with a default RandomGenerator as its source of random data.
     */
    public RandomDataGenerator() {
        this(new Well19937c());
    }

    /**
     * Construct a RandomDataGenerator with a default RandomGenerator as its source of random data, initialized
     * with the given seed value.
     *
     * @param seed seed value
     */
    public RandomDataGenerator(long seed) {
        this(new Well19937c(seed));
    }

    /**
     * Construct a RandomDataGenerator using the given RandomGenerator as its source of random data.
     *
     * @param randomGenerator the underlying PRNG
     * @throws MathIllegalArgumentException if randomGenerator is null
     */
    private RandomDataGenerator(RandomGenerator randomGenerator) {
        MathUtils.checkNotNull(randomGenerator);
        this.randomGenerator = randomGenerator;
    }

    /**
     * Factory method to create a {@code RandomData} instance using the supplied
     * {@code RandomGenerator}.
     *
     * @param randomGenerator source of random bits
     * @return a RandomData using the given RandomGenerator to source bits
     * @throws MathIllegalArgumentException if randomGenerator is null
     */
    public static RandomDataGenerator of(RandomGenerator randomGenerator) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected RandomGenerator delegate() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the next pseudo-random beta-distributed value with the given
     * shape and scale parameters.
     *
     * @param alpha First shape parameter (must be positive).
     * @param beta Second shape parameter (must be positive).
     * @return beta-distributed random deviate
     */
    public double nextBeta(double alpha, double beta) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the next pseudo-random, exponentially distributed deviate.
     *
     * @param mean mean of the exponential distribution
     * @return exponentially distributed deviate about the given mean
     */
    public double nextExponential(double mean) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the next pseudo-random gamma-distributed value with the given shape and scale parameters.
     *
     * @param shape shape parameter of the distribution
     * @param scale scale parameter of the distribution
     * @return gamma-distributed random deviate
     */
    public double nextGamma(double shape, double scale) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the next normally-distributed pseudo-random deviate.
     *
     * @param mean mean of the normal distribution
     * @param standardDeviation standard deviation of the normal distribution
     * @return a random value, normally distributed with the given mean and standard deviation
     */
    public double nextNormal(double mean, double standardDeviation) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the next log-normally-distributed pseudo-random deviate.
     *
     * @param shape shape parameter of the log-normal distribution
     * @param scale scale parameter of the log-normal distribution
     * @return a random value, normally distributed with the given mean and standard deviation
     */
    public double nextLogNormal(double shape, double scale) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns a poisson-distributed deviate with the given mean.
     *
     * @param mean expected value
     * @return poisson deviate
     * @throws MathIllegalArgumentException if mean is not strictly positive
     */
    public int nextPoisson(double mean) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns a random deviate from the given distribution.
     *
     * @param dist the distribution to sample from
     * @return a random value following the given distribution
     */
    public double nextDeviate(RealDistribution dist) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns an array of random deviates from the given distribution.
     *
     * @param dist the distribution to sample from
     * @param size the number of values to return
     *
     * @return an array of {@code size} values following the given distribution
     */
    public double[] nextDeviates(RealDistribution dist, int size) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns a random deviate from the given distribution.
     *
     * @param dist the distribution to sample from
     * @return a random value following the given distribution
     */
    public int nextDeviate(IntegerDistribution dist) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns an array of random deviates from the given distribution.
     *
     * @param dist the distribution to sample from
     * @param size the number of values to return
     *
     * @return an array of {@code size }values following the given distribution
     */
    public int[] nextDeviates(IntegerDistribution dist, int size) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns a sampler for the given continuous distribution.
     * @param dist the distribution
     * @return a sampler for the distribution
     */
    private RealDistributionSampler getSampler(RealDistribution dist) {
        RealDistributionSampler sampler = CONTINUOUS_SAMPLERS.get(dist.getClass());
        if (sampler != null) {
            return sampler;
        }
        return DEFAULT_REAL_SAMPLER;
    }

    /**
     * Returns a sampler for the given discrete distribution.
     * @param dist the distribution
     * @return a sampler for the distribution
     */
    private IntegerDistributionSampler getSampler(IntegerDistribution dist) {
        IntegerDistributionSampler sampler = DISCRETE_SAMPLERS.get(dist.getClass());
        if (sampler != null) {
            return sampler;
        }
        return DEFAULT_INTEGER_SAMPLER;
    }

    /**
     * Returns a uniformly distributed random integer between lower and upper (inclusive).
     *
     * @param lower lower bound for the generated value
     * @param upper upper bound for the generated value
     * @return a random integer value within the given bounds
     * @throws MathIllegalArgumentException if lower is not strictly less than or equal to upper
     */
    public int nextInt(int lower, int upper) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns a uniformly distributed random long integer between lower and upper (inclusive).
     *
     * @param lower lower bound for the generated value
     * @param upper upper bound for the generated value
     * @return a random long integer value within the given bounds
     * @throws MathIllegalArgumentException if lower is not strictly less than or equal to upper
     */
    public long nextLong(final long lower, final long upper) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns a double value uniformly distributed over [lower, upper]
     * @param lower lower bound
     * @param upper upper bound
     * @return uniform deviate
     * @throws MathIllegalArgumentException if upper is less than or equal to upper
     */
    public double nextUniform(double lower, double upper) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns an integer value following a Zipf distribution with the given parameter.
     *
     * @param numberOfElements number of elements of the distribution
     * @param exponent exponent of the distribution
     * @return random Zipf value
     */
    public int nextZipf(int numberOfElements, double exponent) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Generates a random string of hex characters of length {@code len}.
     * <p>
     * The generated string will be random, but not cryptographically secure.
     * <p>
     * <strong>Algorithm Description:</strong> hex strings are generated using a
     * 2-step process.
     * </p>
     * <ol>
     * <li>{@code len / 2 + 1} binary bytes are generated using the underlying
     * Random</li>
     * <li>Each binary byte is translated into 2 hex digits</li>
     * </ol>
     *
     * @param len the desired string length.
     * @return the random string.
     * @throws MathIllegalArgumentException if {@code len <= 0}.
     */
    public String nextHexString(int len) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Generates an integer array of length {@code k} whose entries are selected
     * randomly, without repetition, from the integers {@code 0, ..., n - 1}
     * (inclusive).
     * <p>
     * Generated arrays represent permutations of {@code n} taken {@code k} at a
     * time.</p>
     * This method calls {@link MathArrays#shuffle(int[],RandomGenerator)
     * MathArrays.shuffle} in order to create a random shuffle of the set
     * of natural numbers {@code { 0, 1, ..., n - 1 }}.
     *
     * @param n the domain of the permutation
     * @param k the size of the permutation
     * @return a random {@code k}-permutation of {@code n}, as an array of
     * integers
     * @throws MathIllegalArgumentException if {@code k > n}.
     * @throws MathIllegalArgumentException if {@code k <= 0}.
     */
    public int[] nextPermutation(int n, int k) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns an array of {@code k} objects selected randomly from the
     * Collection {@code c}.
     * <p>
     * Sampling from {@code c} is without replacement; but if {@code c} contains
     * identical objects, the sample may include repeats.  If all elements of
     * {@code c} are distinct, the resulting object array represents a
     * <a href="http://rkb.home.cern.ch/rkb/AN16pp/node250.html#SECTION0002500000000000000000">
     * Simple Random Sample</a> of size {@code k} from the elements of
     * {@code c}.</p>
     * <p>This method calls {@link #nextPermutation(int,int) nextPermutation(c.size(), k)}
     * in order to sample the collection.
     * </p>
     *
     * @param c the collection to be sampled
     * @param k the size of the sample
     * @return a random sample of {@code k} elements from {@code c}
     * @throws MathIllegalArgumentException if {@code k > c.size()}.
     * @throws MathIllegalArgumentException if {@code k <= 0}.
     */
    public Object[] nextSample(Collection<?> c, int k) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns an array of {@code k} double values selected randomly from the
     * double array {@code a}.
     * <p>
     * Sampling from {@code a} is without replacement; but if {@code a} contains
     * identical objects, the sample may include repeats.  If all elements of
     * {@code a} are distinct, the resulting object array represents a
     * <a href="http://rkb.home.cern.ch/rkb/AN16pp/node250.html#SECTION0002500000000000000000">
     * Simple Random Sample</a> of size {@code k} from the elements of
     * {@code a}.</p>
     *
     * @param a the array to be sampled
     * @param k the size of the sample
     * @return a random sample of {@code k} elements from {@code a}
     * @throws MathIllegalArgumentException if {@code k > c.size()}.
     * @throws MathIllegalArgumentException if {@code k <= 0}.
     */
    public double[] nextSample(double[] a, int k) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Generates a random sample of size sampleSize from {0, 1, ... , weights.length - 1},
     * using weights as probabilities.
     * <p>
     * For 0 &lt; i &lt; weights.length, the probability that i is selected (on any draw) is weights[i].
     * If necessary, the weights array is normalized to sum to 1 so that weights[i] is a probability
     * and the array sums to 1.
     * <p>
     * Weights can be 0, but must not be negative, infinite or NaN.
     * At least one weight must be positive.
     *
     * @param sampleSize size of sample to generate
     * @param weights probability sampling weights
     * @return an array of integers between 0 and weights.length - 1
     * @throws MathIllegalArgumentException if weights contains negative, NaN or infinite values or only 0s or sampleSize is less than 0
     */
    public int[] nextSampleWithReplacement(int sampleSize, double[] weights) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Utility class implementing Cheng's algorithms for beta distribution sampling.
     *
     * <blockquote>
     * <pre>
     * R. C. H. Cheng,
     * "Generating beta variates with nonintegral shape parameters",
     * Communications of the ACM, 21, 317-322, 1978.
     * </pre>
     * </blockquote>
     */
    private static class ChengBetaSampler {

        /**
         * Private constructor for utility class.
         */
        private ChengBetaSampler() {
            // NOPMD - PMD fails to detect this is a utility class
            // not called
        }

        /**
         * Returns the next sample following a beta distribution
         * with given alpha and beta parameters.
         *
         * @param generator the random generator to use
         * @param alpha the alpha parameter
         * @param beta the beta parameter
         * @return the next sample
         */
        public static double sample(RandomGenerator generator, double alpha, double beta) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Returns one Beta sample using Cheng's BB algorithm,
         * when both &alpha; and &beta; are greater than 1.
         *
         * @param generator the random generator to use
         * @param a0 distribution first shape parameter (&alpha;)
         * @param a min(&alpha;, &beta;) where &alpha;, &beta; are the two distribution shape parameters
         * @param b max(&alpha;, &beta;) where &alpha;, &beta; are the two distribution shape parameters
         * @return sampled value
         */
        private static double algorithmBB(final RandomGenerator generator, final double a0, final double a, final double b) {
            final double alpha = a + b;
            final double beta = FastMath.sqrt((alpha - 2.) / (2. * a * b - alpha));
            final double gamma = a + 1. / beta;
            double r;
            double w;
            double t;
            do {
                final double u1 = generator.nextDouble();
                final double u2 = generator.nextDouble();
                final double v = beta * (FastMath.log(u1) - FastMath.log1p(-u1));
                w = a * FastMath.exp(v);
                final double z = u1 * u1 * u2;
                r = gamma * v - 1.3862944;
                final double s = a + r - w;
                if (s + 2.609438 >= 5 * z) {
                    break;
                }
                t = FastMath.log(z);
                if (s >= t) {
                    break;
                }
            } while (r + alpha * (FastMath.log(alpha) - FastMath.log(b + w)) < t);
            w = FastMath.min(w, Double.MAX_VALUE);
            return Precision.equals(a, a0) ? w / (b + w) : b / (b + w);
        }

        /**
         * Returns a Beta-distribute value using Cheng's BC algorithm,
         * when at least one of &alpha; and &beta; is smaller than 1.
         *
         * @param generator the random generator to use
         * @param a0 distribution first shape parameter (&alpha;)
         * @param a max(&alpha;, &beta;) where &alpha;, &beta; are the two distribution shape parameters
         * @param b min(&alpha;, &beta;) where &alpha;, &beta; are the two distribution shape parameters
         * @return sampled value
         */
        private static double algorithmBC(final RandomGenerator generator, final double a0, final double a, final double b) {
            final double alpha = a + b;
            final double beta = 1. / b;
            final double delta = 1. + a - b;
            final double k1 = delta * (0.0138889 + 0.0416667 * b) / (a * beta - 0.777778);
            final double k2 = 0.25 + (0.5 + 0.25 / delta) * b;
            double w;
            for (; ; ) {
                final double u1 = generator.nextDouble();
                final double u2 = generator.nextDouble();
                final double y = u1 * u2;
                final double z = u1 * y;
                if (u1 < 0.5) {
                    if (0.25 * u2 + z - y >= k1) {
                        continue;
                    }
                } else {
                    if (z <= 0.25) {
                        final double v = beta * (FastMath.log(u1) - FastMath.log1p(-u1));
                        w = a * FastMath.exp(v);
                        break;
                    }
                    if (z >= k2) {
                        continue;
                    }
                }
                final double v = beta * (FastMath.log(u1) - FastMath.log1p(-u1));
                w = a * FastMath.exp(v);
                if (alpha * (FastMath.log(alpha) - FastMath.log(b + w) + v) - 1.3862944 >= FastMath.log(z)) {
                    break;
                }
            }
            w = FastMath.min(w, Double.MAX_VALUE);
            return Precision.equals(a, a0) ? w / (b + w) : b / (b + w);
        }
    }

    /**
     * Utility class implementing a rejection inversion sampling method for a discrete,
     * bounded Zipf distribution that is based on the method described in
     * <p>
     * Wolfgang Hörmann and Gerhard Derflinger
     * "Rejection-inversion to generate variates from monotone discrete distributions."
     * ACM Transactions on Modeling and Computer Simulation (TOMACS) 6.3 (1996): 169-184.
     * <p>
     * The paper describes an algorithm for exponents larger than 1 (Algorithm ZRI).
     * The original method uses {@code H(x) := (v + x)^(1 - q) / (1 - q)}
     * as the integral of the hat function. This function is undefined for
     * q = 1, which is the reason for the limitation of the exponent.
     * If instead the integral function
     * {@code H(x) := ((v + x)^(1 - q) - 1) / (1 - q)} is used,
     * for which a meaningful limit exists for q = 1,
     * the method works for all positive exponents.
     * <p>
     * The following implementation uses v := 0 and generates integral numbers
     * in the range [1, numberOfElements]. This is different to the original method
     * where v is defined to be positive and numbers are taken from [0, i_max].
     * This explains why the implementation looks slightly different.
     */
    static final class ZipfRejectionInversionSampler {

        /**
         * Exponent parameter of the distribution.
         */
        private final double exponent;

        /**
         * Number of elements.
         */
        private final int numberOfElements;

        /**
         * Constant equal to {@code hIntegral(1.5) - 1}.
         */
        private final double hIntegralX1;

        /**
         * Constant equal to {@code hIntegral(numberOfElements + 0.5)}.
         */
        private final double hIntegralNumberOfElements;

        /**
         * Constant equal to {@code 2 - hIntegralInverse(hIntegral(2.5) - h(2)}.
         */
        private final double s;

        /**
         * Simple constructor.
         * @param numberOfElements number of elements
         * @param exponent exponent parameter of the distribution
         */
        ZipfRejectionInversionSampler(final int numberOfElements, final double exponent) {
            this.exponent = exponent;
            this.numberOfElements = numberOfElements;
            this.hIntegralX1 = hIntegral(1.5) - 1d;
            this.hIntegralNumberOfElements = hIntegral(numberOfElements + 0.5);
            this.s = 2d - hIntegralInverse(hIntegral(2.5) - h(2));
        }

        /**
         * Generate one integral number in the range [1, numberOfElements].
         * @param random random generator to use
         * @return generated integral number in the range [1, numberOfElements]
         */
        int sample(final RandomGenerator random) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@code H(x) :=}
         * <ul>
         * <li>{@code (x^(1-exponent) - 1)/(1 - exponent)}, if {@code exponent != 1}</li>
         * <li>{@code log(x)}, if {@code exponent == 1}</li>
         * </ul>
         * H(x) is an integral function of h(x),
         * the derivative of H(x) is h(x).
         *
         * @param x free parameter
         * @return {@code H(x)}
         */
        private double hIntegral(final double x) {
            final double logX = FastMath.log(x);
            return helper2((1d - exponent) * logX) * logX;
        }

        /**
         * {@code h(x) := 1/x^exponent}
         *
         * @param x free parameter
         * @return h(x)
         */
        private double h(final double x) {
            return FastMath.exp(-exponent * FastMath.log(x));
        }

        /**
         * The inverse function of H(x).
         *
         * @param x free parameter
         * @return y for which {@code H(y) = x}
         */
        private double hIntegralInverse(final double x) {
            double t = x * (1d - exponent);
            if (t < -1d) {
                // Limit value to the range [-1, +inf).
                // t could be smaller than -1 in some rare cases due to numerical errors.
                t = -1;
            }
            return FastMath.exp(helper1(t) * x);
        }

        /**
         * @return the exponent of the distribution being sampled
         */
        public double getExponent() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * @return the number of elements of the distribution being sampled
         */
        public int getNumberOfElements() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Helper function that calculates {@code log(1+x)/x}.
         * <p>
         * A Taylor series expansion is used, if x is close to 0.
         *
         * @param x a value larger than or equal to -1
         * @return {@code log(1+x)/x}
         */
        static double helper1(final double x) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Helper function to calculate {@code (exp(x)-1)/x}.
         * <p>
         * A Taylor series expansion is used, if x is close to 0.
         *
         * @param x free parameter
         * @return {@code (exp(x)-1)/x} if x is non-zero, or 1 if x=0
         */
        static double helper2(final double x) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Sampler for enumerated distributions.
     *
     * @param <T> type of sample space objects
     */
    private final class EnumeratedDistributionSampler<T> {

        /**
         * Probabilities
         */
        private final double[] weights;

        /**
         * Values
         */
        private final List<T> values;

        /**
         * Create an EnumeratedDistributionSampler from the provided pmf.
         *
         * @param pmf probability mass function describing the distribution
         */
        EnumeratedDistributionSampler(List<Pair<T, Double>> pmf) {
            final int numMasses = pmf.size();
            weights = new double[numMasses];
            values = new ArrayList<>();
            for (int i = 0; i < numMasses; i++) {
                weights[i] = pmf.get(i).getSecond();
                values.add(pmf.get(i).getFirst());
            }
        }

        /**
         * @return a random value from the distribution
         */
        public T sample() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
