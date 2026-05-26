/*
 * Licensed to the Hipparchus project under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The Hipparchus project licenses this file to You under the Apache License, Version 2.0
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
package org.hipparchus.stat.descriptive.rank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.MathIllegalStateException;
import org.hipparchus.exception.NullArgumentException;
import org.hipparchus.random.RandomGenerator;
import org.hipparchus.random.Well19937c;
import org.hipparchus.stat.StatUtils;
import org.hipparchus.stat.descriptive.AbstractStorelessUnivariateStatistic;
import org.hipparchus.stat.descriptive.AggregatableStatistic;
import org.hipparchus.stat.descriptive.StorelessUnivariateStatistic;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.MathArrays;

/**
 * A {@link StorelessUnivariateStatistic} estimating percentiles using the
 * <a href=http://dimacs.rutgers.edu/~graham/pubs/papers/nquantiles.pdf>RANDOM</a>
 * Algorithm.
 * <p>
 * Storage requirements for the RANDOM algorithm depend on the desired
 * accuracy of quantile estimates. Quantile estimate accuracy is defined as follows.
 * <p>
 * Let \(X\) be the set of all data values consumed from the stream and let \(q\)
 * be a quantile (measured between 0 and 1) to be estimated. If <ul>
 * <li>\(\epsilon\) is the configured accuracy</li>
 * <li> \(\hat{q}\) is a RandomPercentile estimate for \(q\) (what is returned
 *      by {@link #getResult()} or {@link #getResult(double)}) with \(100q\) as
 *      actual parameter)</li>
 * <li> \(rank(\hat{q}) = |\{x \in X : x &lt; \hat{q}\}|\) is the actual rank of
 *      \(\hat{q}\) in the full data stream</li>
 * <li>\(n = |X|\) is the number of observations</li></ul>
 * then we can expect \((q - \epsilon)n &lt; rank(\hat{q}) &lt; (q + \epsilon)n\).
 * <p>
 * The algorithm maintains \(\left\lceil {log_{2}(1/\epsilon)}\right\rceil + 1\) buffers
 * of size \(\left\lceil {1/\epsilon \sqrt{log_2(1/\epsilon)}}\right\rceil\).  When
 * {@code epsilon} is set to the default value of \(10^{-4}\), this makes 15 buffers
 * of size 36,453.
 * <p>
 * The algorithm uses the buffers to maintain samples of data from the stream.  Until
 * all buffers are full, the entire sample is stored in the buffers.
 * If one of the {@code getResult} methods is called when all data are available in memory
 * and there is room to make a copy of the data (meaning the combined set of buffers is
 * less than half full), the {@code getResult} method delegates to a {@link Percentile}
 * instance to compute and return the exact value for the desired quantile.
 * For default {@code epsilon}, this means exact values will be returned whenever fewer than
 * \(\left\lceil {15 \times 36453 / 2} \right\rceil = 273,398\) values have been consumed
 * from the data stream.
 * <p>
 * When buffers become full, the algorithm merges buffers so that they effectively represent
 * a larger set of values than they can hold. Subsequently, data values are sampled from the
 * stream to fill buffers freed by merge operations.  Both the merging and the sampling
 * require random selection, which is done using a {@code RandomGenerator}.  To get
 * repeatable results for large data streams, users should provide {@code RandomGenerator}
 * instances with fixed seeds. {@code RandomPercentile} itself does not reseed or otherwise
 * initialize the {@code RandomGenerator} provided to it.  By default, it uses a
 * {@link Well19937c} generator with the default seed.
 * <p>
 * Note: This implementation is not thread-safe.
 */
public class RandomPercentile extends AbstractStorelessUnivariateStatistic implements StorelessUnivariateStatistic, AggregatableStatistic<RandomPercentile>, Serializable {

    /**
     * Default quantile estimation error setting
     */
    public static final double DEFAULT_EPSILON = 1e-4;

    /**
     * Serialization version id
     */
    private static final long serialVersionUID = 1L;

    /**
     * Storage size of each buffer
     */
    private final int s;

    /**
     * Maximum number of buffers minus 1
     */
    private final int h;

    /**
     * Data structure used to manage buffers
     */
    private final BufferMap bufferMap;

    /**
     * Bound on the quantile estimation error
     */
    private final double epsilon;

    /**
     * Source of random data
     */
    private final RandomGenerator randomGenerator;

    /**
     * Number of elements consumed from the input data stream
     */
    private long n;

    /**
     * Buffer currently being filled
     */
    private Buffer currentBuffer;

    /**
     * Constructs a {@code RandomPercentile} with quantile estimation error
     * {@code epsilon} using {@code randomGenerator} as its source of random data.
     *
     * @param epsilon bound on quantile estimation error (see class javadoc)
     * @param randomGenerator PRNG used in sampling and merge operations
     * @throws MathIllegalArgumentException if percentile is not in the range [0, 100]
     */
    public RandomPercentile(double epsilon, RandomGenerator randomGenerator) {
        if (epsilon <= 0) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.NUMBER_TOO_SMALL, epsilon, 0);
        }
        this.h = (int) FastMath.ceil(log2(1 / epsilon));
        this.s = (int) FastMath.ceil(FastMath.sqrt(log2(1 / epsilon)) / epsilon);
        this.randomGenerator = randomGenerator;
        bufferMap = new BufferMap(h + 1, s, randomGenerator);
        currentBuffer = bufferMap.create(0);
        this.epsilon = epsilon;
    }

    /**
     * Constructs a {@code RandomPercentile} with default estimation error
     * using {@code randomGenerator} as its source of random data.
     *
     * @param randomGenerator PRNG used in sampling and merge operations
     * @throws MathIllegalArgumentException if percentile is not in the range [0, 100]
     */
    public RandomPercentile(RandomGenerator randomGenerator) {
        this(DEFAULT_EPSILON, randomGenerator);
    }

    /**
     * Constructs a {@code RandomPercentile} with quantile estimation error
     * {@code epsilon} using the default PRNG as source of random data.
     *
     * @param epsilon bound on quantile estimation error (see class javadoc)
     * @throws MathIllegalArgumentException if percentile is not in the range [0, 100]
     */
    public RandomPercentile(double epsilon) {
        this(epsilon, new Well19937c());
    }

    /**
     * Constructs a {@code RandomPercentile} with quantile estimation error
     * set to the default ({@link #DEFAULT_EPSILON}), using the default PRNG
     * as source of random data.
     */
    public RandomPercentile() {
        this(DEFAULT_EPSILON, new Well19937c());
    }

    /**
     * Copy constructor, creates a new {@code RandomPercentile} identical
     * to the {@code original}.  Note: the RandomGenerator used by the new
     * instance is referenced, not copied - i.e., the new instance shares
     * a generator with the original.
     *
     * @param original the {@code PSquarePercentile} instance to copy
     */
    public RandomPercentile(RandomPercentile original) {
        this.h = original.h;
        this.n = original.n;
        this.s = original.s;
        this.epsilon = original.epsilon;
        this.bufferMap = new BufferMap(original.bufferMap);
        this.randomGenerator = original.randomGenerator;
        Iterator<Buffer> iterator = bufferMap.iterator();
        Buffer current = null;
        Buffer curr = null;
        // See if there is a partially filled buffer - that will be currentBuffer
        while (current == null && iterator.hasNext()) {
            curr = iterator.next();
            if (curr.hasCapacity()) {
                current = curr;
            }
        }
        // If there is no partially filled buffer, just assign the last one.
        // Next increment() will find no capacity and create a new one or trigger
        // a merge.
        this.currentBuffer = current == null ? curr : current;
    }

    @Override
    public long getN() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns an estimate of the given percentile, computed using the designated
     * array segment as input data.
     *
     * @param values source of input data
     * @param begin position of the first element of the values array to include
     * @param length number of array elements to include
     * @param percentile desired percentile (scaled 0 - 100)
     *
     * @return estimated percentile
     * @throws MathIllegalArgumentException if percentile is out of the range [0, 100]
     */
    public double evaluate(final double percentile, final double[] values, final int begin, final int length) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns an estimate of the median, computed using the designated
     * array segment as input data.
     *
     * @param values source of input data
     * @param begin position of the first element of the values array to include
     * @param length number of array elements to include
     *
     * @return estimated percentile
     * @throws MathIllegalArgumentException if percentile is out of the range [0, 100]
     */
    @Override
    public double evaluate(final double[] values, final int begin, final int length) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns an estimate of percentile over the given array.
     *
     * @param values source of input data
     * @param percentile desired percentile (scaled 0 - 100)
     *
     * @return estimated percentile
     * @throws MathIllegalArgumentException if percentile is out of the range [0, 100]
     */
    public double evaluate(final double percentile, final double[] values) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public RandomPercentile copy() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns an estimate of the median.
     */
    @Override
    public double getResult() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns an estimate of the given percentile.
     *
     * @param percentile desired percentile (scaled 0 - 100)
     * @return estimated percentile
     * @throws MathIllegalArgumentException if percentile is out of the range [0, 100]
     */
    public double getResult(double percentile) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the estimated rank of {@code value}, i.e.  \(|\{x \in X : x &lt; value\}|\)
     * where \(X\) is the set of values that have been consumed from the stream.
     *
     * @param value value whose overall rank is sought
     * @return estimated number of sample values that are strictly less than {@code value}
     */
    public double getRank(double value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the estimated quantile position of value in the dataset.
     * Specifically, what is returned is an estimate of \(|\{x \in X : x &lt; value\}| / |X|\)
     * where \(X\) is the set of values that have been consumed from the stream.
     *
     * @param value value whose quantile rank is sought.
     * @return estimated proportion of sample values that are strictly less than {@code value}
     */
    public double getQuantileRank(double value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void increment(double d) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Maintains a buffer of values sampled from the input data stream.
     * <p>
     * The {@link #level} of a buffer determines its sampling frequency.
     * The {@link #consume(double)} method retains 1 out of every 2^level values
     * read in from the stream.
     * <p>
     * The {@link #size} of the buffer is the number of values that it can store
     * The buffer is considered full when it has consumed 2^level * size values.
     * <p>
     * The {@link #blockSize} of a buffer is 2^level.
     * The consume method starts each block by generating a random integer in
     * [0, blockSize - 1].  It then skips over all but the element with that offset
     * in the block, retaining only the selected value. So after 2^level * size
     * elements have been consumed, it will have retained size elements - one
     * from each 2^level block.
     * <p>
     * The {@link #mergeWith(Buffer)} method merges this buffer with another one,
     * The merge operation merges the data from the other buffer into this and clears
     * the other buffer (so it can consume data). Both buffers have their level
     * incremented by the merge. This operation is only used on full buffers.
     */
    private static class Buffer implements Serializable {

        /**
         * Serialization version id
         */
        private static final long serialVersionUID = 1L;

        /**
         * Number of values actually stored in the buffer
         */
        private final int size;

        /**
         * Data sampled from the stream
         */
        private final double[] data;

        /**
         * PRNG used for merges and stream sampling
         */
        private final RandomGenerator randomGenerator;

        /**
         * Level of the buffer
         */
        private int level;

        /**
         * Block size  = 2^level
         */
        private long blockSize;

        /**
         * Next location in backing array for stored (taken) value
         */
        private int next;

        /**
         * Number of values consumed in current 2^level block of values from the stream
         */
        private long consumed;

        /**
         * Index of next value to take in current 2^level block
         */
        private long nextToTake;

        /**
         * ID
         */
        private final UUID id;

        /**
         * Creates a new buffer capable of retaining size values with the given level.
         *
         * @param size number of values the buffer can retain
         * @param level the base 2 log of the sampling frequency
         *        (one out of every 2^level values is retained)
         * @param randomGenerator PRNG used for sampling and merge operations
         */
        Buffer(int size, int level, RandomGenerator randomGenerator) {
            this.size = size;
            data = new double[size];
            this.level = level;
            this.randomGenerator = randomGenerator;
            this.id = UUID.randomUUID();
            computeBlockSize();
        }

        /**
         * Sets blockSize and nextToTake based on level.
         */
        private void computeBlockSize() {
            if (level == 0) {
                blockSize = 1;
            } else {
                long product = 1;
                for (int i = 0; i < level; i++) {
                    product *= 2;
                }
                blockSize = product;
            }
            if (blockSize > 1) {
                nextToTake = randomGenerator.nextLong(blockSize);
            }
        }

        /**
         * Consumes a value from the input stream.
         * <p>
         * For each 2^level values consumed, one is added to the buffer.
         * The buffer is not considered full until 2^level * size values
         * have been consumed.
         * <p>
         * Sorts the data array if the consumption renders the buffer full.
         * <p>
         * There is no capacity check in this method.  Clients are expected
         * to use {@link #hasCapacity()} before invoking this method.  If
         * it is invoked on a full buffer, an ArrayIndexOutOfBounds exception
         * will result.
         *
         * @param value value to consume from the stream
         */
        public void consume(double value) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Merges this with other.
         * <p>
         * After the merge, this will be the merged buffer and other will be free.
         * Both will have level+1.
         * Post-merge, other can be used to accept new data.
         * <p>
         * The contents of the merged buffer (this after the merge) are determined
         * by randomly choosing one of the two retained elements in each of the
         * [0...size - 1] positions in the two buffers.
         * <p>
         * This and other must have the same level and both must be full.
         *
         * @param other initially full other buffer at the same level as this.
         * @throws MathIllegalArgumentException if either buffer is not full or they
         * have different levels
         */
        public void mergeWith(Buffer other) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Merge this into a higher-level buffer.
         * <p>
         * Does not alter this; but after the merge, higher may have some of its
         * data replaced by data from this.  Levels are not changed for either buffer.
         * <p>
         * Probability of selection into the newly constituted higher buffer is weighted
         * according to level. So for example, if this has level 0 and higher has level
         * 2, the ith element of higher is 4 times more likely than the corresponding
         * element of this to retain its spot.
         * <p>
         * This method is only used when aggregating RandomPercentile instances.
         * <p>
         * Preconditions:
         * <ol><li> this.level < higher.level </li>
         *     <li> this.size = higher.size </li>
         *     <li> Both buffers are full </li>
         * </ol>
         *
         * @param higher higher-level buffer to merge this into
         * @throws MathIllegalArgumentException if the buffers have different sizes,
         * either buffer is not full or this has level greater than or equal to higher
         */
        public void mergeInto(Buffer higher) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * @return true if the buffer has capacity; false if it is full
         */
        public boolean hasCapacity() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Sets the level of the buffer.
         *
         * @param level new level value
         */
        public void setLevel(int level) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Clears data, recomputes blockSize and resets consumed and nextToTake.
         */
        public void clear() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Returns a copy of the data that has been added to the buffer
         *
         * @return possibly unsorted copy of the portion of the buffer that has been filled
         */
        public double[] getData() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Returns the ordinal rank of value among the sampled values in this buffer.
         *
         * @param value value whose rank is sought
         * @return |{v in data : v < value}|
         */
        public int rankOf(double value) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * @return the smallest value held in this buffer
         */
        public double min() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * @return the largest value held in this buffer
         */
        public double max() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * @return the level of this buffer
         */
        public int getLevel() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * @return the id
         */
        public UUID getId() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Computes base 2 log of the argument.
     *
     * @param x input value
     * @return the value y such that 2^y = x
     */
    private static double log2(double x) {
        return Math.log(x) / Math.log(2);
    }

    /**
     * A map structure to hold the buffers.
     * Keys are levels and values are lists of buffers at the given level.
     * Overall capacity is limited by the total number of buffers.
     */
    private static class BufferMap implements Iterable<Buffer>, Serializable {

        /**
         * Serialization version ID
         */
        private static final long serialVersionUID = 1L;

        /**
         * Total number of buffers that can be created - cap for count
         */
        private final int capacity;

        /**
         * PRNG used in merges
         */
        private final RandomGenerator randomGenerator;

        /**
         * Total count of all buffers
         */
        private int count;

        /**
         * Uniform buffer size
         */
        private final int bufferSize;

        /**
         * Backing store for the buffer map. Keys are levels, values are lists of registered buffers.
         */
        private final Map<Integer, List<Buffer>> registry;

        /**
         * Maximum buffer level
         */
        private int maxLevel;

        /**
         * Creates a BufferMap that can manage up to capacity buffers.
         * Buffers created by the pool with have size = buffersize.
         *
         * @param capacity cap on the number of buffers
         * @param bufferSize size of each buffer
         * @param randomGenerator RandomGenerator to use in merges
         */
        BufferMap(int capacity, int bufferSize, RandomGenerator randomGenerator) {
            this.bufferSize = bufferSize;
            this.capacity = capacity;
            this.randomGenerator = randomGenerator;
            this.registry = new HashMap<>();
        }

        /**
         * Copy constructor.
         *
         * @param original BufferMap to copy
         */
        BufferMap(BufferMap original) {
            this.bufferSize = original.bufferSize;
            this.capacity = original.capacity;
            this.count = 0;
            this.randomGenerator = original.randomGenerator;
            this.registry = new HashMap<>();
            for (Buffer current : original) {
                // Create and register a new buffer at the same level
                final Buffer newCopy = create(current.getLevel());
                // Consume the data
                final double[] data = current.getData();
                for (double value : data) {
                    newCopy.consume(value);
                }
            }
        }

        /**
         * Tries to create a buffer with the given level.
         * <p>
         * If there is capacity to create a new buffer (i.e., fewer than
         * count have been created), a new buffer is created with the given
         * level, registered and returned.  If capacity has been reached,
         * null is returned.
         *
         * @param level level of the new buffer
         * @return an empty buffer or null if a buffer can't be provided
         */
        public Buffer create(int level) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Returns true if there is capacity to create a new buffer.
         *
         * @return true if fewer than capacity buffers have been created.
         */
        public boolean canCreate() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Returns true if we have used less than half of the allocated storage.
         * <p>
         * Includes a check to make sure all buffers have level 0;
         * but this should always be the case.
         * <p>
         * When this method returns true, we have all consumed data in storage
         * and enough space to make a copy of the combined dataset.
         *
         * @return true if all buffers have level 0 and less than half of the
         * available storage has been used
         */
        public boolean halfEmpty() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Returns a fresh copy of all data from level 0 buffers.
         *
         * @return combined data stored in all level 0 buffers
         */
        public double[] levelZeroData() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Finds the lowest level l where there exist at least two buffers,
         * merges them to create a new buffer with level l+1 and returns
         * a free buffer with level l+1.
         *
         * @return free buffer that can accept data
         */
        public Buffer merge() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Clears the buffer map.
         */
        public void clear() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Registers a buffer.
         *
         * @param buffer Buffer to be registered.
         */
        public void register(Buffer buffer) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * De-register a buffer, without clearing it.
         *
         * @param buffer Buffer to be de-registered
         * @throws IllegalStateException if the buffer is not registered
         */
        public void deRegister(Buffer buffer) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Returns an iterator over all of the buffers. Iteration goes by level, with
         * level 0 first.  Assumes there are no empty buffer lists.
         */
        @Override
        public Iterator<Buffer> iterator() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Absorbs the data in other into this, merging buffers as necessary to trim
         * the aggregate down to capacity. This method is only used when aggregating
         * RandomPercentile instances.
         *
         * @param other other BufferMap to merge in
         */
        public void absorb(BufferMap other) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Find two buffers, first and second, of minimal level. Then merge
         * first into second and discard first.
         * <p>
         * If the buffers have different levels, make second the higher level
         * buffer and make probability of selection in the merge proportional
         * to level weight ratio.
         * <p>
         * This method is only used when aggregating RandomPercentile instances.
         */
        public void mergeUp() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Computes the given percentile by combining the data from the collection
     * of aggregates. The result describes the combined sample of all data added
     * to any of the aggregates.
     *
     * @param percentile desired percentile (scaled 0-100)
     * @param aggregates RandomPercentile instances to combine data from
     * @return estimate of the given percentile using combined data from the aggregates
     * @throws MathIllegalArgumentException if percentile is out of the range [0, 100]
     */
    public double reduce(double percentile, Collection<RandomPercentile> aggregates) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Computes the estimated rank of value in the combined dataset of the aggregates.
     * Sums the values from {@link #getRank(double)}.
     *
     * @param value value whose rank is sought
     * @param aggregates collection to aggregate rank over
     * @return estimated number of elements in the combined dataset that are less than value
     */
    public double getAggregateRank(double value, Collection<RandomPercentile> aggregates) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the estimated quantile position of value in the combined dataset of the aggregates.
     * Specifically, what is returned is an estimate of \(|\{x \in X : x &lt; value\}| / |X|\)
     * where \(X\) is the set of values that have been consumed from all of the datastreams
     * feeding the aggregates.
     *
     * @param value value whose quantile rank is sought.
     * @param aggregates collection of RandomPercentile instances being combined
     * @return estimated proportion of combined sample values that are strictly less than {@code value}
     */
    public double getAggregateQuantileRank(double value, Collection<RandomPercentile> aggregates) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the total number of values that have been consumed by the aggregates.
     *
     * @param aggregates collection of RandomPercentile instances whose combined sample size is sought
     * @return total number of values that have been consumed by the aggregates
     */
    public double getAggregateN(Collection<RandomPercentile> aggregates) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Aggregates the provided instance into this instance.
     * <p>
     * Other must have the same buffer size as this. If the combined data size
     * exceeds the maximum storage configured for this instance, buffers are
     * merged to create capacity. If all that is needed is computation of
     * aggregate results, {@link #reduce(double, Collection)} is faster,
     * may be more accurate and does not require the buffer sizes to be the same.
     *
     * @param other the instance to aggregate into this instance
     * @throws NullArgumentException if the input is null
     * @throws IllegalArgumentException if other has different buffer size than this
     */
    @Override
    public void aggregate(RandomPercentile other) throws NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the maximum number of {@code double} values that a {@code RandomPercentile}
     * instance created with the given {@code epsilon} value will retain in memory.
     * <p>
     * If the number of values that have been consumed from the stream is less than 1/2
     * of this value, reported statistics are exact.
     *
     * @param epsilon bound on the relative quantile error (see class javadoc)
     * @return upper bound on the total number of primitive double values retained in memory
     * @throws MathIllegalArgumentException if epsilon is not in the interval (0,1)
     */
    public static long maxValuesRetained(double epsilon) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
