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

import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.geometry.euclidean.oned.Euclidean1D;
import org.hipparchus.geometry.euclidean.oned.IntervalsSet;
import org.hipparchus.geometry.euclidean.oned.Vector1D;
import org.hipparchus.geometry.partitioning.Embedding;
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
 *
 * @see #fromDirection(Vector3D, Vector3D, double)
 * @see #Line(Vector3D, Vector3D, double)
 */
public class Line implements Embedding<Euclidean3D, Vector3D, Euclidean1D, Vector1D> {

    /**
     * Line direction.
     */
    private Vector3D direction;

    /**
     * Line point closest to the origin.
     */
    private Vector3D zero;

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
     * @see #fromDirection(Vector3D, Vector3D, double)
     */
    public Line(final Vector3D p1, final Vector3D p2, final double tolerance) throws MathIllegalArgumentException {
        this(tolerance);
        reset(p1, p2);
    }

    /**
     * Copy constructor.
     * <p>The created instance is completely independent from the
     * original instance, it is a deep copy.</p>
     * @param line line to copy
     */
    public Line(final Line line) {
        this(line.tolerance);
        this.direction = line.direction;
        this.zero = line.zero;
    }

    /**
     * Private constructor. Just sets the tolerance.
     *
     * @param tolerance below which points are considered identical.
     */
    private Line(final double tolerance) {
        this.tolerance = tolerance;
    }

    /**
     * Create a line from a point and a direction. Line = {@code point} + t * {@code
     * direction}, where t is any real number.
     *
     * @param point     on the line. Can be any point.
     * @param direction of the line. Must not be the zero vector.
     * @param tolerance below which points are considered identical.
     * @return a new Line with the given point and direction.
     * @throws MathIllegalArgumentException if {@code direction} is the zero vector.
     * @see #Line(Vector3D, Vector3D, double)
     */
    public static Line fromDirection(final Vector3D point, final Vector3D direction, final double tolerance) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Reset the instance as if built from two points.
     * @param p1 first point belonging to the line (this can be any point)
     * @param p2 second point belonging to the line (this can be any point, different from p1)
     * @exception MathIllegalArgumentException if the points are equal
     */
    public void reset(final Vector3D p1, final Vector3D p2) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Reset the instance as if built from a point and direction.
     *
     * @param p1    point belonging to the line (this can be any point).
     * @param delta direction of the line.
     * @throws MathIllegalArgumentException if {@code delta} is the zero vector.
     */
    private void resetWithDirection(final Vector3D p1, final Vector3D delta) {
        final double norm2 = delta.getNorm2Sq();
        if (norm2 == 0.0) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.ZERO_NORM);
        }
        this.direction = new Vector3D(1.0 / FastMath.sqrt(norm2), delta);
        zero = new Vector3D(1.0, p1, -p1.dotProduct(delta) / norm2, delta);
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
    public Line revert() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the normalized direction vector.
     * @return normalized direction vector
     */
    public Vector3D getDirection() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the line point closest to the origin.
     * @return line point closest to the origin
     */
    public Vector3D getOrigin() {
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
    public double getAbscissa(final Vector3D point) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get one point from the line.
     * @param abscissa desired abscissa for the point
     * @return one point belonging to the line, at specified abscissa
     */
    public Vector3D pointAt(final double abscissa) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * @see #getAbscissa(Vector3D)
     */
    @Override
    public Vector1D toSubSpace(final Vector3D point) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * @see #pointAt(double)
     */
    @Override
    public Vector3D toSpace(final Vector1D point) {
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
    public boolean isSimilarTo(final Line line) {
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
    public double distance(final Vector3D p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the shortest distance between the instance and another line.
     * @param line line to check against the instance
     * @return shortest distance between the instance and the line
     */
    public double distance(final Line line) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the point of the instance closest to another line.
     * @param line line to check against the instance
     * @return point of the instance closest to another line
     */
    public Vector3D closestPoint(final Line line) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the intersection point of the instance and another line.
     * @param line other line
     * @return intersection point of the instance and the other line
     * or null if there are no intersection points
     */
    public Vector3D intersection(final Line line) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Build a sub-line covering the whole line.
     * @return a sub-line covering the whole line
     */
    public SubLine wholeLine() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
