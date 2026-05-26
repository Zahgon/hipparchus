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

import java.util.ArrayList;
import java.util.List;
import org.hipparchus.FieldElement;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.MathRuntimeException;
import org.hipparchus.exception.NullArgumentException;
import org.hipparchus.util.MathArrays;
import org.hipparchus.util.MathUtils;

/**
 * Polynomial interpolator using both sample values and sample derivatives.
 * <p>
 * The interpolation polynomials match all sample points, including both values
 * and provided derivatives. There is one polynomial for each component of
 * the values vector. All polynomials have the same degree. The degree of the
 * polynomials depends on the number of points and number of derivatives at each
 * point. For example the interpolation polynomials for n sample points without
 * any derivatives all have degree n-1. The interpolation polynomials for n
 * sample points with the two extreme points having value and first derivative
 * and the remaining points having value only all have degree n+1. The
 * interpolation polynomial for n sample points with value, first and second
 * derivative for all points all have degree 3n-1.
 * </p>
 *
 * @param <T> Type of the field elements.
 */
public class FieldHermiteInterpolator<T extends FieldElement<T>> {

    /**
     * Sample abscissae.
     */
    private final List<T> abscissae;

    /**
     * Top diagonal of the divided differences array.
     */
    private final List<T[]> topDiagonal;

    /**
     * Bottom diagonal of the divided differences array.
     */
    private final List<T[]> bottomDiagonal;

    /**
     * Create an empty interpolator.
     */
    public FieldHermiteInterpolator() {
        this.abscissae = new ArrayList<>();
        this.topDiagonal = new ArrayList<>();
        this.bottomDiagonal = new ArrayList<>();
    }

    /**
     * Add a sample point.
     * <p>
     * This method must be called once for each sample point. It is allowed to
     * mix some calls with values only with calls with values and first
     * derivatives.
     * </p>
     * <p>
     * The point abscissae for all calls <em>must</em> be different.
     * </p>
     * @param x abscissa of the sample point
     * @param value value and derivatives of the sample point
     * (if only one row is passed, it is the value, if two rows are
     * passed the first one is the value and the second the derivative
     * and so on)
     * @exception MathIllegalArgumentException if the abscissa difference between added point
     * and a previous point is zero (i.e. the two points are at same abscissa)
     * @exception MathRuntimeException if the number of derivatives is larger
     * than 20, which prevents computation of a factorial
     * @throws MathIllegalArgumentException if derivative structures are inconsistent
     * @throws NullArgumentException if x is null
     */
    @SafeVarargs
    public final void addSamplePoint(final T x, final T[]... value) throws MathRuntimeException, NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Interpolate value at a specified abscissa.
     * @param x interpolation abscissa
     * @return interpolated value
     * @exception MathIllegalArgumentException if sample is empty
     * @throws NullArgumentException if x is null
     */
    public T[] value(T x) throws MathIllegalArgumentException, NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Interpolate value and first derivatives at a specified abscissa.
     * @param x interpolation abscissa
     * @param order maximum derivation order
     * @return interpolated value and derivatives (value in row 0,
     * 1<sup>st</sup> derivative in row 1, ... n<sup>th</sup> derivative in row n)
     * @exception MathIllegalArgumentException if sample is empty
     * @throws NullArgumentException if x is null
     */
    public T[][] derivatives(T x, int order) throws MathIllegalArgumentException, NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
