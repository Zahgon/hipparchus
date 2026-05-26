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
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.NullArgumentException;
import org.hipparchus.stat.descriptive.AbstractStorelessUnivariateStatistic;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.MathArrays;
import org.hipparchus.util.MathUtils;

/**
 * Computes the Kurtosis of the available values.
 * <p>
 * We use the following (unbiased) formula to define kurtosis:
 * <p>
 * kurtosis = { [n(n+1) / (n -1)(n - 2)(n-3)] sum[(x_i - mean)^4] / std^4 } - [3(n-1)^2 / (n-2)(n-3)]
 * <p>
 * where n is the number of values, mean is the {@link Mean} and std is the
 * {@link StandardDeviation}.
 * <p>
 * Note that this statistic is undefined for n &lt; 4.  <code>Double.Nan</code>
 * is returned when there is not sufficient data to compute the statistic.
 * Note that Double.NaN may also be returned if the input includes NaN
 * and / or infinite values.
 * <p>
 * <strong>Note that this implementation is not synchronized.</strong> If
 * multiple threads access an instance of this class concurrently, and at least
 * one of the threads invokes the <code>increment()</code> or
 * <code>clear()</code> method, it must be synchronized externally.
 */
public class Kurtosis extends AbstractStorelessUnivariateStatistic implements Serializable {

    /**
     * Serializable version identifier
     */
    private static final long serialVersionUID = 20150412L;

    /**
     * Fourth Moment on which this statistic is based
     */
    protected final FourthMoment moment;

    /**
     * Determines whether or not this statistic can be incremented or cleared.
     * <p>
     * Statistics based on (constructed from) external moments cannot
     * be incremented or cleared.
     */
    protected final boolean incMoment;

    /**
     * Construct a Kurtosis.
     */
    public Kurtosis() {
        moment = new FourthMoment();
        incMoment = true;
    }

    /**
     * Construct a Kurtosis from an external moment.
     *
     * @param m4 external Moment
     */
    public Kurtosis(final FourthMoment m4) {
        this.moment = m4;
        incMoment = false;
    }

    /**
     * Copy constructor, creates a new {@code Kurtosis} identical
     * to the {@code original}.
     *
     * @param original the {@code Kurtosis} instance to copy
     * @throws NullArgumentException if original is null
     */
    public Kurtosis(Kurtosis original) throws NullArgumentException {
        MathUtils.checkNotNull(original);
        this.moment = original.moment.copy();
        this.incMoment = original.incMoment;
    }

    /**
     * {@inheritDoc}
     * <p>Note that when {@link #Kurtosis(FourthMoment)} is used to
     * create a Variance, this method does nothing. In that case, the
     * FourthMoment should be incremented directly.</p>
     */
    @Override
    public void increment(final double d) {
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
    public void clear() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getN() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* UnvariateStatistic Approach  */
    /**
     * Returns the kurtosis of the entries in the specified portion of the
     * input array.
     * <p>
     * See {@link Kurtosis} for details on the computing algorithm.</p>
     * <p>
     * Throws <code>IllegalArgumentException</code> if the array is null.</p>
     *
     * @param values the input array
     * @param begin index of the first array element to include
     * @param length the number of elements to include
     * @return the kurtosis of the values or Double.NaN if length is less than 4
     * @throws MathIllegalArgumentException if the input array is null or the array
     * index parameters are not valid
     */
    @Override
    public double evaluate(final double[] values, final int begin, final int length) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Kurtosis copy() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
