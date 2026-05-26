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
package org.hipparchus.geometry.euclidean.twod;

import java.util.List;
import org.hipparchus.fraction.BigFraction;
import org.hipparchus.geometry.enclosing.EnclosingBall;
import org.hipparchus.geometry.enclosing.SupportBallGenerator;
import org.hipparchus.util.FastMath;

/**
 * Class generating an enclosing ball from its support points.
 */
public class DiskGenerator implements SupportBallGenerator<Euclidean2D, Vector2D> {

    /**
     * Empty constructor.
     * <p>
     * This constructor is not strictly necessary, but it prevents spurious
     * javadoc warnings with JDK 18 and later.
     * </p>
     * @since 3.0
     */
    public DiskGenerator() {
        // NOPMD - unnecessary constructor added intentionally to make javadoc happy
        // nothing to do
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EnclosingBall<Euclidean2D, Vector2D> ballOnSupport(final List<Vector2D> support) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute a dimension 3 minor, when 3<sup>d</sup> column is known to be filled with 1.0.
     * @param c1 first column
     * @param c2 second column
     * @return value of the minor computed has an exact fraction
     */
    private BigFraction minor(final BigFraction[] c1, final BigFraction[] c2) {
        return c2[0].multiply(c1[2].subtract(c1[1])).add(c2[1].multiply(c1[0].subtract(c1[2]))).add(c2[2].multiply(c1[1].subtract(c1[0])));
    }
}
