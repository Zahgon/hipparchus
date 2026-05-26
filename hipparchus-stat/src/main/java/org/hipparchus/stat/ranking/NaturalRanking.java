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
package org.hipparchus.stat.ranking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.MathRuntimeException;
import org.hipparchus.random.RandomDataGenerator;
import org.hipparchus.random.RandomGenerator;
import org.hipparchus.util.FastMath;

/**
 * <p> Ranking based on the natural ordering on doubles.</p>
 * <p>NaNs are treated according to the configured {@link NaNStrategy} and ties
 * are handled using the selected {@link TiesStrategy}.
 * Configuration settings are supplied in optional constructor arguments.
 * Defaults are {@link NaNStrategy#FAILED} and {@link TiesStrategy#AVERAGE},
 * respectively. When using {@link TiesStrategy#RANDOM}, a
 * {@link RandomGenerator} may be supplied as a constructor argument.</p>
 * <table border="1">
 * <caption>Examples</caption>
 * <tr><th colspan="3">
 * Input data: (20, 17, 30, 42.3, 17, 50, Double.NaN, Double.NEGATIVE_INFINITY, 17)
 * </th></tr>
 * <tr><th>NaNStrategy</th><th>TiesStrategy</th>
 * <th><code>rank(data)</code></th>
 * <tr>
 * <td>default (NaNs maximal)</td>
 * <td>default (ties averaged)</td>
 * <td>(5, 3, 6, 7, 3, 8, 9, 1, 3)</td></tr>
 * <tr>
 * <td>default (NaNs maximal)</td>
 * <td>MINIMUM</td>
 * <td>(5, 2, 6, 7, 2, 8, 9, 1, 2)</td></tr>
 * <tr>
 * <td>MINIMAL</td>
 * <td>default (ties averaged)</td>
 * <td>(6, 4, 7, 8, 4, 9, 1.5, 1.5, 4)</td></tr>
 * <tr>
 * <td>REMOVED</td>
 * <td>SEQUENTIAL</td>
 * <td>(5, 2, 6, 7, 3, 8, 1, 4)</td></tr>
 * <tr>
 * <td>MINIMAL</td>
 * <td>MAXIMUM</td>
 * <td>(6, 5, 7, 8, 5, 9, 2, 2, 5)</td></tr></table>
 */
public class NaturalRanking implements RankingAlgorithm {

    /**
     * default NaN strategy
     */
    public static final NaNStrategy DEFAULT_NAN_STRATEGY = NaNStrategy.FAILED;

    /**
     * default ties strategy
     */
    public static final TiesStrategy DEFAULT_TIES_STRATEGY = TiesStrategy.AVERAGE;

    /**
     * NaN strategy - defaults to NaNs maximal
     */
    private final NaNStrategy nanStrategy;

    /**
     * Ties strategy - defaults to ties averaged
     */
    private final TiesStrategy tiesStrategy;

    /**
     * Source of random data - used only when ties strategy is RANDOM
     */
    private final RandomDataGenerator randomData;

    /**
     * Create a NaturalRanking with default strategies for handling ties and NaNs.
     */
    public NaturalRanking() {
        tiesStrategy = DEFAULT_TIES_STRATEGY;
        nanStrategy = DEFAULT_NAN_STRATEGY;
        randomData = null;
    }

    /**
     * Create a NaturalRanking with the given TiesStrategy.
     *
     * @param tiesStrategy the TiesStrategy to use
     */
    public NaturalRanking(TiesStrategy tiesStrategy) {
        this.tiesStrategy = tiesStrategy;
        nanStrategy = DEFAULT_NAN_STRATEGY;
        randomData = new RandomDataGenerator();
    }

    /**
     * Create a NaturalRanking with the given NaNStrategy.
     *
     * @param nanStrategy the NaNStrategy to use
     */
    public NaturalRanking(NaNStrategy nanStrategy) {
        this.nanStrategy = nanStrategy;
        tiesStrategy = DEFAULT_TIES_STRATEGY;
        randomData = null;
    }

    /**
     * Create a NaturalRanking with the given NaNStrategy and TiesStrategy.
     *
     * @param nanStrategy NaNStrategy to use
     * @param tiesStrategy TiesStrategy to use
     */
    public NaturalRanking(NaNStrategy nanStrategy, TiesStrategy tiesStrategy) {
        this.nanStrategy = nanStrategy;
        this.tiesStrategy = tiesStrategy;
        randomData = new RandomDataGenerator();
    }

    /**
     * Create a NaturalRanking with TiesStrategy.RANDOM and the given
     * RandomGenerator as the source of random data.
     *
     * @param randomGenerator source of random data
     */
    public NaturalRanking(RandomGenerator randomGenerator) {
        this.tiesStrategy = TiesStrategy.RANDOM;
        nanStrategy = DEFAULT_NAN_STRATEGY;
        randomData = RandomDataGenerator.of(randomGenerator);
    }

    /**
     * Create a NaturalRanking with the given NaNStrategy, TiesStrategy.RANDOM
     * and the given source of random data.
     *
     * @param nanStrategy NaNStrategy to use
     * @param randomGenerator source of random data
     */
    public NaturalRanking(NaNStrategy nanStrategy, RandomGenerator randomGenerator) {
        this.nanStrategy = nanStrategy;
        this.tiesStrategy = TiesStrategy.RANDOM;
        randomData = RandomDataGenerator.of(randomGenerator);
    }

    /**
     * Return the NaNStrategy
     *
     * @return returns the NaNStrategy
     */
    public NaNStrategy getNanStrategy() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Return the TiesStrategy
     *
     * @return the TiesStrategy
     */
    public TiesStrategy getTiesStrategy() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Rank <code>data</code> using the natural ordering on Doubles, with
     * NaN values handled according to <code>nanStrategy</code> and ties
     * resolved using <code>tiesStrategy.</code>
     *
     * @param data array to be ranked
     * @return array of ranks
     * @throws MathIllegalArgumentException if the selected {@link NaNStrategy} is {@code FAILED}
     * and a {@link Double#NaN} is encountered in the input data
     */
    @Override
    public double[] rank(double[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns an array that is a copy of the input array with IntDoublePairs
     * having NaN values removed.
     *
     * @param ranks input array
     * @return array with NaN-valued entries removed
     */
    private IntDoublePair[] removeNaNs(IntDoublePair[] ranks) {
        if (!containsNaNs(ranks)) {
            return ranks;
        }
        IntDoublePair[] outRanks = new IntDoublePair[ranks.length];
        int j = 0;
        for (int i = 0; i < ranks.length; i++) {
            if (Double.isNaN(ranks[i].getValue())) {
                // drop, but adjust original ranks of later elements
                for (int k = i + 1; k < ranks.length; k++) {
                    ranks[k] = new IntDoublePair(ranks[k].getValue(), ranks[k].getPosition() - 1);
                }
            } else {
                outRanks[j] = new IntDoublePair(ranks[i].getValue(), ranks[i].getPosition());
                j++;
            }
        }
        IntDoublePair[] returnRanks = new IntDoublePair[j];
        System.arraycopy(outRanks, 0, returnRanks, 0, j);
        return returnRanks;
    }

    /**
     * Recodes NaN values to the given value.
     *
     * @param ranks array to recode
     * @param value the value to replace NaNs with
     */
    private void recodeNaNs(IntDoublePair[] ranks, double value) {
        for (int i = 0; i < ranks.length; i++) {
            if (Double.isNaN(ranks[i].getValue())) {
                ranks[i] = new IntDoublePair(value, ranks[i].getPosition());
            }
        }
    }

    /**
     * Checks for presence of NaNs in <code>ranks.</code>
     *
     * @param ranks array to be searched for NaNs
     * @return true iff ranks contains one or more NaNs
     */
    private boolean containsNaNs(IntDoublePair[] ranks) {
        for (IntDoublePair rank : ranks) {
            if (Double.isNaN(rank.getValue())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Resolve a sequence of ties, using the configured {@link TiesStrategy}.
     * The input <code>ranks</code> array is expected to take the same value
     * for all indices in <code>tiesTrace</code>.  The common value is recoded
     * according to the tiesStrategy. For example, if ranks = <5,8,2,6,2,7,1,2>,
     * tiesTrace = <2,4,7> and tiesStrategy is MINIMUM, ranks will be unchanged.
     * The same array and trace with tiesStrategy AVERAGE will come out
     * <5,8,3,6,3,7,1,3>.
     *
     * @param ranks array of ranks
     * @param tiesTrace list of indices where <code>ranks</code> is constant
     * -- that is, for any i and j in TiesTrace, <code> ranks[i] == ranks[j]
     * </code>
     */
    private void resolveTie(double[] ranks, List<Integer> tiesTrace) {
        // constant value of ranks over tiesTrace
        final double c = ranks[tiesTrace.get(0)];
        // length of sequence of tied ranks
        final int length = tiesTrace.size();
        switch(tiesStrategy) {
            case // Replace ranks with average
            AVERAGE:
                fill(ranks, tiesTrace, (2 * c + length - 1) / 2d);
                break;
            case // Replace ranks with maximum values
            MAXIMUM:
                fill(ranks, tiesTrace, c + length - 1);
                break;
            case // Replace ties with minimum
            MINIMUM:
                fill(ranks, tiesTrace, c);
                break;
            case // Fill with random integral values in [c, c + length - 1]
            RANDOM:
                Iterator<Integer> iterator = tiesTrace.iterator();
                long f = FastMath.round(c);
                while (iterator.hasNext()) {
                    // No advertised exception because args are guaranteed valid
                    ranks[iterator.next()] = randomData.nextLong(f, f + length - 1);
                }
                break;
            case // Fill sequentially from c to c + length - 1
            SEQUENTIAL:
                // walk and fill
                iterator = tiesTrace.iterator();
                f = FastMath.round(c);
                int i = 0;
                while (iterator.hasNext()) {
                    ranks[iterator.next()] = f + i++;
                }
                break;
            default:
                // this should not happen unless TiesStrategy enum is changed
                throw MathRuntimeException.createInternalError();
        }
    }

    /**
     * Sets<code>data[i] = value</code> for each i in <code>tiesTrace.</code>
     *
     * @param data array to modify
     * @param tiesTrace list of index values to set
     * @param value value to set
     */
    private void fill(double[] data, List<Integer> tiesTrace, double value) {
        for (Integer integer : tiesTrace) {
            data[integer] = value;
        }
    }

    /**
     * Set <code>ranks[i] = Double.NaN</code> for each i in <code>nanPositions.</code>
     *
     * @param ranks array to modify
     * @param nanPositions list of index values to set to <code>Double.NaN</code>
     */
    private void restoreNaNs(double[] ranks, List<Integer> nanPositions) {
        if (nanPositions.isEmpty()) {
            return;
        }
        for (Integer nanPosition : nanPositions) {
            ranks[nanPosition] = Double.NaN;
        }
    }

    /**
     * Returns a list of indexes where <code>ranks</code> is <code>NaN.</code>
     *
     * @param ranks array to search for <code>NaNs</code>
     * @return list of indexes i such that <code>ranks[i] = NaN</code>
     */
    private List<Integer> getNanPositions(IntDoublePair[] ranks) {
        ArrayList<Integer> out = new ArrayList<>();
        for (int i = 0; i < ranks.length; i++) {
            if (Double.isNaN(ranks[i].getValue())) {
                out.add(i);
            }
        }
        return out;
    }

    /**
     * Represents the position of a double value in an ordering.
     */
    private static class IntDoublePair {

        /**
         * Value of the pair
         */
        private final double value;

        /**
         * Original position of the pair
         */
        private final int position;

        /**
         * Construct an IntDoublePair with the given value and position.
         * @param value the value of the pair
         * @param position the original position
         */
        IntDoublePair(double value, int position) {
            this.value = value;
            this.position = position;
        }

        /**
         * Returns the value of the pair.
         * @return value
         */
        public double getValue() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Returns the original position of the pair.
         * @return position
         */
        public int getPosition() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
