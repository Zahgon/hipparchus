/*
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
package org.hipparchus.analysis.interpolation;

import org.hipparchus.CalculusFieldElement;
import org.hipparchus.Field;
import org.hipparchus.analysis.CalculusFieldTrivariateFunction;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.util.MathArrays;

/**
 * Interpolate grid data using tri-linear interpolation.
 * <p>
 * This interpolator is thread-safe.
 * </p>
 * @param <T> Type of the field elements.
 * @since 4.1
 */
public class FieldTrilinearInterpolatingFunction<T extends CalculusFieldElement<T>> implements CalculusFieldTrivariateFunction<T> {

    /**
     * Grid along the x axis.
     */
    private final FieldGridAxis<T> xGrid;

    /**
     * Grid along the y axis.
     */
    private final FieldGridAxis<T> yGrid;

    /**
     * Grid along the z axis.
     */
    private final FieldGridAxis<T> zGrid;

    /**
     * Values of the interpolation points on all the grid knots
     */
    private final T[][][] fVal;

    /**
     * Simple constructor.
     * @param xVal All the x-coordinates of the interpolation points, sorted
     * in increasing order.
     * @param yVal All the y-coordinates of the interpolation points, sorted
     * in increasing order.
     * @param zVal All the z-coordinates of the interpolation points, sorted
     * in increasing order.
     * @param fVal The values of the interpolation points on all the grid knots:
     * {@code fVal[i][j][k] = f(xVal[i], yVal[j], zVal[k])}.
     * @exception MathIllegalArgumentException if grid size is smaller than 2
     * or if the grid is not sorted in strict increasing order
     */
    public FieldTrilinearInterpolatingFunction(final T[] xVal, final T[] yVal, final T[] zVal, final T[][][] fVal) throws MathIllegalArgumentException {
        final Field<T> field = fVal[0][0][0].getField();
        this.xGrid = new FieldGridAxis<>(xVal, 2);
        this.yGrid = new FieldGridAxis<>(yVal, 2);
        this.zGrid = new FieldGridAxis<>(zVal, 2);
        this.fVal = MathArrays.buildArray(field, xVal.length, yVal.length, zVal.length);
        for (int i = 0; i < xVal.length; i++) {
            for (int j = 0; j < yVal.length; j++) {
                System.arraycopy(fVal[i][j], 0, this.fVal[i][j], 0, zVal.length);
            }
        }
    }

    /**
     * Get the lowest grid x coordinate.
     *
     * @return lowest grid x coordinate
     */
    public T getXInf() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the highest grid x coordinate.
     *
     * @return highest grid x coordinate
     */
    public T getXSup() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the lowest grid y coordinate.
     *
     * @return lowest grid y coordinate
     */
    public T getYInf() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the highest grid y coordinate.
     *
     * @return highest grid y coordinate
     */
    public T getYSup() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the lowest grid z coordinate.
     *
     * @return lowest grid z coordinate
     */
    public T getZInf() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the highest grid z coordinate.
     *
     * @return highest grid z coordinate
     */
    public T getZSup() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T value(T x, T y, T z) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
