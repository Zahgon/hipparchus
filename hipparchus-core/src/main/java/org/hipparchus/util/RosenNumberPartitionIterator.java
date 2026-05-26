/* Copyright 2011 Axel Kramer
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
package org.hipparchus.util;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;

/**
 * An iterator that generates all partitions of <code>n</code> elements, into <code>k</code> parts
 * containing the number of elements in each part, based on Rosen's algorithm.
 * <p>
 * This is a copy of the class (with slight edits) with the same name from the
 * <a href="https://github.com/axkr/symja_android_library">Symja Library</a>.
 * The original file was published under the terms of the GPLV3 license,
 * but the Hipparchus project was <a
 * href="https://github.com/Hipparchus-Math/hipparchus/issues/197#issuecomment-1193259547">explicitly allowed</a>
 * to include it relicensed to Apache V2.
 * </p>
 * <p>
 * See Kenneth H. Rosen, Discrete Mathematics and Its Applications, 2nd edition (NY: McGraw-Hill,
 * 1991), pp. 284-286
 * </p>
 */
public class RosenNumberPartitionIterator implements Iterator<int[]> {

    /**
     * Number of elements.
     */
    private final int n;

    /**
     * Subset/sample size.
     */
    private final int k;

    /**
     * Work array.
     */
    private int[] a;

    /**
     * Count of unique combinations.
     */
    private long count;

    /**
     * Simple constructor.
     * @param n the number of elements
     * @param k divided into k parts
     */
    public RosenNumberPartitionIterator(final int n, final int k) {
        this.n = n - 1;
        this.k = k - 1;
        if (k > n || k < 1) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.OUT_OF_RANGE_SIMPLE, k, 1, n);
        }
        reset();
    }

    /**
     * {@inheritDoc}
     *
     * @see java.util.Iterator#hasNext()
     */
    @Override
    public final boolean hasNext() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * @see java.util.Iterator#next()
     */
    @Override
    public final int[] next() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Reset this iterator to the start condition.
     */
    public void reset() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
