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
package org.hipparchus.stat;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.hipparchus.exception.NullArgumentException;
import org.hipparchus.util.MathUtils;

/**
 * Maintains a frequency distribution of Comparable values.
 * <p>
 * The values are ordered using the default (natural order), unless a
 * {@code Comparator} is supplied in the constructor.
 *
 * @see LongFrequency
 * @param <T> the element type
 */
public class Frequency<T extends Comparable<T>> implements Serializable {

    /**
     * Serializable version identifier
     */
    private static final long serialVersionUID = 20160322L;

    /**
     * underlying collection
     */
    private final NavigableMap<T, Long> freqTable;

    /**
     * Default constructor.
     */
    public Frequency() {
        freqTable = new TreeMap<>();
    }

    /**
     * Constructor allowing values Comparator to be specified.
     *
     * @param comparator Comparator used to order values
     */
    public Frequency(Comparator<? super T> comparator) {
        freqTable = new TreeMap<>(comparator);
    }

    /**
     * Adds 1 to the frequency count for v.
     *
     * @param v the value to add.
     */
    public void addValue(T v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Increments the frequency count for v.
     *
     * @param v the value to add.
     * @param increment the amount by which the value should be incremented
     */
    public void incrementValue(T v, long increment) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Clears the frequency table
     */
    public void clear() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns an Iterator over the set of values that have been added.
     *
     * @return values Iterator
     */
    public Iterator<T> valuesIterator() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Return an Iterator over the set of keys and values that have been added.
     * Using the entry set to iterate is more efficient in the case where you
     * need to access respective counts as well as values, since it doesn't
     * require a "get" for every key...the value is provided in the Map.Entry.
     *
     * @return entry set Iterator
     */
    public Iterator<Map.Entry<T, Long>> entrySetIterator() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    //-------------------------------------------------------------------------
    /**
     * Returns the sum of all frequencies.
     *
     * @return the total frequency count.
     */
    public long getSumFreq() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the number of values equal to v.
     * Returns 0 if the value is not comparable.
     *
     * @param v the value to lookup.
     * @return the frequency of v.
     */
    public long getCount(T v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the number of values in the frequency table.
     *
     * @return the number of unique values that have been added to the frequency table.
     * @see #valuesIterator()
     */
    public int getUniqueCount() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the percentage of values that are equal to v
     * (as a proportion between 0 and 1).
     * <p>
     * Returns {@code Double.NaN} if no values have been added.
     *
     * @param v the value to lookup
     * @return the proportion of values equal to v
     */
    public double getPct(T v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    //-----------------------------------------------------------------------------------------
    /**
     * Returns the cumulative frequency of values less than or equal to v.
     *
     * @param v the value to lookup.
     * @return the proportion of values equal to v
     */
    public long getCumFreq(T v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    //----------------------------------------------------------------------------------------------
    /**
     * Returns the cumulative percentage of values less than or equal to v
     * (as a proportion between 0 and 1).
     * <p>
     * Returns {@code Double.NaN} if no values have been added.
     *
     * @param v the value to lookup
     * @return the proportion of values less than or equal to v
     */
    public double getCumPct(T v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the mode value(s) in comparator order.
     *
     * @return a list containing the value(s) which appear most often.
     */
    public List<T> getMode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    //----------------------------------------------------------------------------------------------
    /**
     * Merge another Frequency object's counts into this instance.
     * This Frequency's counts will be incremented (or set when not already set)
     * by the counts represented by other.
     *
     * @param other the other {@link Frequency} object to be merged
     * @throws NullArgumentException if {@code other} is null
     */
    public void merge(final Frequency<? extends T> other) throws NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Merge a {@link Collection} of {@link Frequency} objects into this instance.
     * This Frequency's counts will be incremented (or set when not already set)
     * by the counts represented by each of the others.
     *
     * @param others the other {@link Frequency} objects to be merged
     * @throws NullArgumentException if the collection is null
     */
    public void merge(final Collection<? extends Frequency<? extends T>> others) throws NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    //----------------------------------------------------------------------------------------------
    /**
     * Return a string representation of this frequency distribution.
     *
     * @return a string representation.
     */
    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
