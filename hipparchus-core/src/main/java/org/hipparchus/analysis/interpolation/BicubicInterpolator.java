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
package org.hipparchus.analysis.interpolation;

import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.util.MathArrays;
import org.hipparchus.util.MathUtils;

/**
 * Generates a {@link BicubicInterpolatingFunction bicubic interpolating
 * function}.
 * <p>
 *  Caveat: Because the interpolation scheme requires that derivatives be
 *  specified at the sample points, those are approximated with finite
 *  differences (using the 2-points symmetric formulae).
 *  Since their values are undefined at the borders of the provided
 *  interpolation ranges, the interpolated values will be wrong at the
 *  edges of the patch.
 *  The {@code interpolate} method will return a function that overrides
 *  {@link BicubicInterpolatingFunction#isValidPoint(double,double)} to
 *  indicate points where the interpolation will be inaccurate.
 * </p>
 */
public class BicubicInterpolator implements BivariateGridInterpolator {

    /**
     * Empty constructor.
     * <p>
     * This constructor is not strictly necessary, but it prevents spurious
     * javadoc warnings with JDK 18 and later.
     * </p>
     * @since 3.0
     */
    public BicubicInterpolator() {
        // NOPMD - unnecessary constructor added intentionally to make javadoc happy
        // nothing to do
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BicubicInterpolatingFunction interpolate(final double[] xval, final double[] yval, final double[][] fval) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
