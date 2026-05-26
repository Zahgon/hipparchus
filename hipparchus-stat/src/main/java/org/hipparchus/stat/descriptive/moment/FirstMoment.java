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
package org.hipparchus.stat.descriptive.moment;

import java.io.Serializable;
import org.hipparchus.exception.NullArgumentException;
import org.hipparchus.stat.descriptive.AbstractStorelessUnivariateStatistic;
import org.hipparchus.util.MathUtils;

/**
 * Computes the first moment (arithmetic mean). Uses the definitional formula:
 * <p>
 * mean = sum(x_i) / n
 * <p>
 * where <code>n</code> is the number of observations.
 * <p>
 * To limit numeric errors, the value of the statistic is computed using the
 * following recursive updating algorithm:
 * </p>
 * <ol>
 * <li>Initialize <code>m = </code> the first value</li>
 * <li>For each additional value, update using <br>
 *   <code>m = m + (new value - m) / (number of observations)</code></li>
 * </ol>
 * <p>
 * Returns <code>Double.NaN</code> if the dataset is empty. Note that
 * Double.NaN may also be returned if the input includes NaN and / or infinite
 * values.
 * <p>
 * <strong>Note that this implementation is not synchronized.</strong> If
 * multiple threads access an instance of this class concurrently, and at least
 * one of the threads invokes the <code>increment()</code> or
 * <code>clear()</code> method, it must be synchronized externally.
 */
class FirstMoment extends AbstractStorelessUnivariateStatistic implements Serializable {

    /**
     * Serializable version identifier
     */
    private static final long serialVersionUID = 20150412L;

    /**
     * Count of values that have been added
     */
    protected long n;

    /**
     * First moment of values that have been added
     */
    protected double m1;

    /**
     * Deviation of most recently added value from previous first moment.
     * Retained to prevent repeated computation in higher order moments.
     */
    protected double dev;

    /**
     * Deviation of most recently added value from previous first moment,
     * normalized by previous sample size.  Retained to prevent repeated
     * computation in higher order moments.
     */
    protected double nDev;

    /**
     * Create a FirstMoment instance.
     */
    FirstMoment() {
        n = 0;
        m1 = Double.NaN;
        dev = Double.NaN;
        nDev = Double.NaN;
    }

    /**
     * Copy constructor, creates a new {@code FirstMoment} identical
     * to the {@code original}
     *
     * @param original the {@code FirstMoment} instance to copy
     * @throws NullArgumentException if original is null
     */
    FirstMoment(FirstMoment original) throws NullArgumentException {
        MathUtils.checkNotNull(original);
        this.n = original.n;
        this.m1 = original.m1;
        this.dev = original.dev;
        this.nDev = original.nDev;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void increment(final double d) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getResult() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getN() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Aggregates the results of the provided instance
     * into this instance.
     *
     * @param other the instance to aggregate from
     */
    protected void aggregate(FirstMoment other) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FirstMoment copy() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
