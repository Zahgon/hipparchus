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

import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.NullArgumentException;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.MathUtils;
import org.hipparchus.util.SinCos;

/**
 * <p>This class provides a stable normalized random generator. It samples from a stable
 * distribution with location parameter 0 and scale 1.</p>
 *
 * <p>The implementation uses the Chambers-Mallows-Stuck method as described in
 * <i>Handbook of computational statistics: concepts and methods</i> by
 * James E. Gentle, Wolfgang H&auml;rdle, Yuichi Mori.</p>
 */
public class StableRandomGenerator implements NormalizedRandomGenerator {

    /**
     * Underlying generator.
     */
    private final RandomGenerator generator;

    /**
     * stability parameter
     */
    private final double alpha;

    /**
     * skewness parameter
     */
    private final double beta;

    /**
     * cache of expression value used in generation
     */
    private final double zeta;

    /**
     * Create a new generator.
     *
     * @param generator underlying random generator to use
     * @param alpha Stability parameter. Must be in range (0, 2]
     * @param beta Skewness parameter. Must be in range [-1, 1]
     * @throws NullArgumentException if generator is null
     * @throws MathIllegalArgumentException if {@code alpha <= 0} or {@code alpha > 2}
     * or {@code beta < -1} or {@code beta > 1}
     */
    public StableRandomGenerator(final RandomGenerator generator, final double alpha, final double beta) throws MathIllegalArgumentException, NullArgumentException {
        if (generator == null) {
            throw new NullArgumentException();
        }
        if (!(alpha > 0d && alpha <= 2d)) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.OUT_OF_RANGE_LEFT, alpha, 0, 2);
        }
        MathUtils.checkRangeInclusive(beta, -1, 1);
        this.generator = generator;
        this.alpha = alpha;
        this.beta = beta;
        if (alpha < 2d && beta != 0d) {
            zeta = beta * FastMath.tan(FastMath.PI * alpha / 2);
        } else {
            zeta = 0d;
        }
    }

    /**
     * Generate a random scalar with zero location and unit scale.
     *
     * @return a random scalar with zero location and unit scale
     */
    @Override
    public double nextNormalizedDouble() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
