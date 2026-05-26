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
import java.util.Random;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.util.MathUtils;

/**
 * A {@link RandomGenerator} adapter that delegates the random number
 * generation to the standard {@link java.util.Random} class.
 */
public class JDKRandomGenerator extends IntRandomGenerator implements Serializable {

    /**
     * Serializable version identifier.
     */
    private static final long serialVersionUID = 20151227L;

    /**
     * JDK's RNG.
     */
    private final Random delegate;

    /**
     * Creates an instance with an arbitrary seed.
     */
    public JDKRandomGenerator() {
        delegate = new Random();
    }

    /**
     * Creates an instance with the given seed.
     *
     * @param seed Initial seed.
     */
    public JDKRandomGenerator(long seed) {
        delegate = new Random(seed);
    }

    /**
     * Creates an instance that wraps the given {@link Random} instance.
     *
     * @param random JDK {@link Random} instance that will generate the
     * the random data.
     * @throws MathIllegalArgumentException if random is null
     */
    public JDKRandomGenerator(Random random) {
        MathUtils.checkNotNull(random);
        delegate = random;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSeed(int seed) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSeed(long seed) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSeed(int[] seed) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void nextBytes(byte[] bytes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int nextInt() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long nextLong() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean nextBoolean() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float nextFloat() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double nextDouble() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double nextGaussian() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int nextInt(int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Converts seed from one representation to another.
     *
     * @param seed Original seed.
     * @return the converted seed.
     */
    private static long convertToLong(int[] seed) {
        // The following number is the largest prime that fits
        // in 32 bits (i.e. 2^32 - 5).
        final long prime = 4294967291l;
        long combined = 0l;
        for (int s : seed) {
            combined = combined * prime + s;
        }
        return combined;
    }
}
