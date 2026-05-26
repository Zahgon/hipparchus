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
package org.hipparchus.stat.descriptive.vector;

import java.io.Serializable;
import java.util.Arrays;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.stat.descriptive.StorelessMultivariateStatistic;
import org.hipparchus.stat.descriptive.StorelessUnivariateStatistic;
import org.hipparchus.util.MathUtils;

/**
 * Uses an independent {@link StorelessUnivariateStatistic} instance
 * for each component of a vector.
 */
public class VectorialStorelessStatistic implements StorelessMultivariateStatistic, Serializable {

    /**
     * Serializable UID
     */
    private static final long serialVersionUID = 20160413L;

    /**
     * Statistic for each component
     */
    private final StorelessUnivariateStatistic[] stats;

    /**
     * Create a new VectorialStorelessStatistic with the given dimension
     * and statistic implementation. A copy of the provided statistic
     * will be created for each component of the vector.
     *
     * @param dimension the vector dimension
     * @param univariateStatistic the prototype statistic
     * @throws MathIllegalArgumentException if dimension &lt; 1
     */
    public VectorialStorelessStatistic(int dimension, StorelessUnivariateStatistic univariateStatistic) {
        if (dimension < 1) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.NUMBER_TOO_SMALL, dimension, 1);
        }
        stats = new StorelessUnivariateStatistic[dimension];
        for (int i = 0; i < dimension; i++) {
            stats[i] = univariateStatistic.copy();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void increment(double[] d) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double[] getResult() {
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
    public int getDimension() {
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
