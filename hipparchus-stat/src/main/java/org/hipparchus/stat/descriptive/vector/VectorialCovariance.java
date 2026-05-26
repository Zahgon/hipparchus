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
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.linear.MatrixUtils;
import org.hipparchus.linear.RealMatrix;
import org.hipparchus.util.MathArrays;

/**
 * Returns the covariance matrix of the available vectors.
 */
public class VectorialCovariance implements Serializable {

    /**
     * Serializable version identifier
     */
    private static final long serialVersionUID = 4118372414238930270L;

    /**
     * Sums for each component.
     */
    private final double[] sums;

    /**
     * Sums of products for each component.
     */
    private final double[] productsSums;

    /**
     * Indicator for bias correction.
     */
    private final boolean isBiasCorrected;

    /**
     * Number of vectors in the sample.
     */
    private long n;

    /**
     * Constructs a VectorialCovariance.
     * @param dimension vectors dimension
     * @param isBiasCorrected if true, computed the unbiased sample covariance,
     * otherwise computes the biased population covariance
     */
    public VectorialCovariance(int dimension, boolean isBiasCorrected) {
        sums = new double[dimension];
        productsSums = new double[dimension * (dimension + 1) / 2];
        n = 0;
        this.isBiasCorrected = isBiasCorrected;
    }

    /**
     * Add a new vector to the sample.
     * @param v vector to add
     * @throws MathIllegalArgumentException if the vector does not have the right dimension
     */
    public void increment(double[] v) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the covariance matrix.
     * @return covariance matrix
     */
    public RealMatrix getResult() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the number of vectors in the sample.
     * @return number of vectors in the sample
     */
    public long getN() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Clears the internal state of the Statistic
     */
    public void clear() {
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
