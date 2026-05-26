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
package org.hipparchus.geometry.euclidean.threed;

import org.hipparchus.CalculusFieldElement;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.Precision;

/**
 * The class represent lines in a three dimensional space.
 *
 * <p>Each oriented line is intrinsically associated with an abscissa
 * which is a coordinate on the line. The point at abscissa 0 is the
 * orthogonal projection of the origin on the line, another equivalent
 * way to express this is to say that it is the point of the line
 * which is closest to the origin. Abscissa increases in the line
 * direction.</p>
 * @param <T> the type of the field elements
 */
public class FieldLine<T extends CalculusFieldElement<T>> {

    /**
     * Line direction.
     */
    private FieldVector3D<T> direction;

    /**
     * Line point closest to the origin.
     */
    private FieldVector3D<T> zero;

    /**
     * Tolerance below which points are considered identical.
     */
    private final double tolerance;

    /**
     * Build a line from two points.
     * @param p1 first point belonging to the line (this can be any point)
     * @param p2 second point belonging to the line (this can be any point, different from p1)
     * @param tolerance tolerance below which points are considered identical
     * @exception MathIllegalArgumentException if the points are equal
     */
    public FieldLine(final FieldVector3D<T> p1, final FieldVector3D<T> p2, final double tolerance) throws MathIllegalArgumentException {
        reset(p1, p2);
        this.tolerance = tolerance;
    }

    /**
     * Copy constructor.
     * <p>The created instance is completely independent from the
     * original instance, it is a deep copy.</p>
     * @param line line to copy
     */
    public FieldLine(final FieldLine<T> line) {
        this.direction = line.direction;
        this.zero = line.zero;
        this.tolerance = line.tolerance;
    }

    /**
     * Reset the instance as if built from two points.
     * @param p1 first point belonging to the line (this can be any point)
     * @param p2 second point belonging to the line (this can be any point, different from p1)
     * @exception MathIllegalArgumentException if the points are equal
     */
    public void reset(final FieldVector3D<T> p1, final FieldVector3D<T> p2) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the tolerance below which points are considered identical.
     * @return tolerance below which points are considered identical
     */
    public double getTolerance() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get a line with reversed direction.
     * @return a new instance, with reversed direction
     */
    public FieldLine<T> revert() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the normalized direction vector.
     * @return normalized direction vector
     */
    public FieldVector3D<T> getDirection() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the line point closest to the origin.
     * @return line point closest to the origin
     */
    public FieldVector3D<T> getOrigin() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the abscissa of a point with respect to the line.
     * <p>The abscissa is 0 if the projection of the point and the
     * projection of the frame origin on the line are the same
     * point.</p>
     * @param point point to check
     * @return abscissa of the point
     */
    public T getAbscissa(final FieldVector3D<T> point) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the abscissa of a point with respect to the line.
     * <p>The abscissa is 0 if the projection of the point and the
     * projection of the frame origin on the line are the same
     * point.</p>
     * @param point point to check
     * @return abscissa of the point
     */
    public T getAbscissa(final Vector3D point) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get one point from the line.
     * @param abscissa desired abscissa for the point
     * @return one point belonging to the line, at specified abscissa
     */
    public FieldVector3D<T> pointAt(final T abscissa) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get one point from the line.
     * @param abscissa desired abscissa for the point
     * @return one point belonging to the line, at specified abscissa
     */
    public FieldVector3D<T> pointAt(final double abscissa) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if the instance is similar to another line.
     * <p>Lines are considered similar if they contain the same
     * points. This does not mean they are equal since they can have
     * opposite directions.</p>
     * @param line line to which instance should be compared
     * @return true if the lines are similar
     */
    public boolean isSimilarTo(final FieldLine<T> line) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if the instance contains a point.
     * @param p point to check
     * @return true if p belongs to the line
     */
    public boolean contains(final FieldVector3D<T> p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if the instance contains a point.
     * @param p point to check
     * @return true if p belongs to the line
     */
    public boolean contains(final Vector3D p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the distance between the instance and a point.
     * @param p to check
     * @return distance between the instance and the point
     */
    public T distance(final FieldVector3D<T> p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the distance between the instance and a point.
     * @param p to check
     * @return distance between the instance and the point
     */
    public T distance(final Vector3D p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the shortest distance between the instance and another line.
     * @param line line to check against the instance
     * @return shortest distance between the instance and the line
     */
    public T distance(final FieldLine<T> line) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the point of the instance closest to another line.
     * @param line line to check against the instance
     * @return point of the instance closest to another line
     */
    public FieldVector3D<T> closestPoint(final FieldLine<T> line) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the intersection point of the instance and another line.
     * @param line other line
     * @return intersection point of the instance and the other line
     * or null if there are no intersection points
     */
    public FieldVector3D<T> intersection(final FieldLine<T> line) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
