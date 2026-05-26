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

import java.text.NumberFormat;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.MathRuntimeException;
import org.hipparchus.geometry.Space;
import org.hipparchus.geometry.Vector;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.MathArrays;
import org.hipparchus.util.MathUtils;

/**
 * This class represents a 2D vector.
 * <p>Instances of this class are guaranteed to be immutable.</p>
 */
public class Vector2D implements Vector<Euclidean2D, Vector2D> {

    /**
     * Origin (coordinates: 0, 0).
     */
    public static final Vector2D ZERO = new Vector2D(0, 0);

    /**
     * First canonical vector (coordinates: 1, 0).
     * @since 1.6
     */
    public static final Vector2D PLUS_I = new Vector2D(1, 0);

    /**
     * Opposite of the first canonical vector (coordinates: -1, 0).
     * @since 1.6
     */
    public static final Vector2D MINUS_I = new Vector2D(-1, 0);

    /**
     * Second canonical vector (coordinates: 0, 1).
     * @since 1.6
     */
    public static final Vector2D PLUS_J = new Vector2D(0, 1);

    /**
     * Opposite of the second canonical vector (coordinates: 0, -1).
     * @since 1.6
     */
    public static final Vector2D MINUS_J = new Vector2D(0, -1);

    // CHECKSTYLE: stop ConstantName
    /**
     * A vector with all coordinates set to NaN.
     */
    public static final Vector2D NaN = new Vector2D(Double.NaN, Double.NaN);

    // CHECKSTYLE: resume ConstantName
    /**
     * A vector with all coordinates set to positive infinity.
     */
    public static final Vector2D POSITIVE_INFINITY = new Vector2D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);

    /**
     * A vector with all coordinates set to negative infinity.
     */
    public static final Vector2D NEGATIVE_INFINITY = new Vector2D(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);

    /**
     * Serializable UID.
     */
    private static final long serialVersionUID = 266938651998679754L;

    /**
     * Abscissa.
     */
    private final double x;

    /**
     * Ordinate.
     */
    private final double y;

    /**
     * Simple constructor.
     * Build a vector from its coordinates
     * @param x abscissa
     * @param y ordinate
     * @see #getX()
     * @see #getY()
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Simple constructor.
     * Build a vector from its coordinates
     * @param v coordinates array
     * @exception MathIllegalArgumentException if array does not have 2 elements
     * @see #toArray()
     */
    public Vector2D(double[] v) throws MathIllegalArgumentException {
        if (v.length != 2) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.DIMENSIONS_MISMATCH, v.length, 2);
        }
        this.x = v[0];
        this.y = v[1];
    }

    /**
     * Multiplicative constructor
     * Build a vector from another one and a scale factor.
     * The vector built will be a * u
     * @param a scale factor
     * @param u base (unscaled) vector
     */
    public Vector2D(double a, Vector2D u) {
        this.x = a * u.x;
        this.y = a * u.y;
    }

    /**
     * Linear constructor
     * Build a vector from two other ones and corresponding scale factors.
     * The vector built will be a1 * u1 + a2 * u2
     * @param a1 first scale factor
     * @param u1 first base (unscaled) vector
     * @param a2 second scale factor
     * @param u2 second base (unscaled) vector
     */
    public Vector2D(double a1, Vector2D u1, double a2, Vector2D u2) {
        this.x = a1 * u1.x + a2 * u2.x;
        this.y = a1 * u1.y + a2 * u2.y;
    }

    /**
     * Linear constructor
     * Build a vector from three other ones and corresponding scale factors.
     * The vector built will be a1 * u1 + a2 * u2 + a3 * u3
     * @param a1 first scale factor
     * @param u1 first base (unscaled) vector
     * @param a2 second scale factor
     * @param u2 second base (unscaled) vector
     * @param a3 third scale factor
     * @param u3 third base (unscaled) vector
     */
    public Vector2D(double a1, Vector2D u1, double a2, Vector2D u2, double a3, Vector2D u3) {
        this.x = a1 * u1.x + a2 * u2.x + a3 * u3.x;
        this.y = a1 * u1.y + a2 * u2.y + a3 * u3.y;
    }

    /**
     * Linear constructor
     * Build a vector from four other ones and corresponding scale factors.
     * The vector built will be a1 * u1 + a2 * u2 + a3 * u3 + a4 * u4
     * @param a1 first scale factor
     * @param u1 first base (unscaled) vector
     * @param a2 second scale factor
     * @param u2 second base (unscaled) vector
     * @param a3 third scale factor
     * @param u3 third base (unscaled) vector
     * @param a4 fourth scale factor
     * @param u4 fourth base (unscaled) vector
     */
    public Vector2D(double a1, Vector2D u1, double a2, Vector2D u2, double a3, Vector2D u3, double a4, Vector2D u4) {
        this.x = a1 * u1.x + a2 * u2.x + a3 * u3.x + a4 * u4.x;
        this.y = a1 * u1.y + a2 * u2.y + a3 * u3.y + a4 * u4.y;
    }

    /**
     * Get the abscissa of the vector.
     * @return abscissa of the vector
     * @see #Vector2D(double, double)
     */
    public double getX() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the ordinate of the vector.
     * @return ordinate of the vector
     * @see #Vector2D(double, double)
     */
    public double getY() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the vector coordinates as a dimension 2 array.
     * @return vector coordinates
     * @see #Vector2D(double[])
     */
    public double[] toArray() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Space getSpace() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector2D getZero() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getNorm1() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getNorm2Sq() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getNorm() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Deprecated
    public double getNormSq() {
        return getNorm2Sq();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getNormInf() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector2D add(Vector2D v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector2D add(double factor, Vector2D v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector2D subtract(Vector2D p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector2D subtract(double factor, Vector2D v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the angular separation between two vectors.
     * <p>This method computes the angular separation between two
     * vectors using the dot product for well separated vectors and the
     * cross product for almost aligned vectors. This allows to have a
     * good accuracy in all cases, even for vectors very close to each
     * other.</p>
     * @param v1 first vector
     * @param v2 second vector
     * @return angular separation between v1 and v2
     * @exception MathRuntimeException if either vector has a null norm
     */
    public static double angle(Vector2D v1, Vector2D v2) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector2D negate() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector2D scalarMultiply(double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNaN() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInfinite() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double distance1(Vector2D p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double distance(Vector2D p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double distanceInf(Vector2D p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double distanceSq(Vector2D p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double dotProduct(final Vector2D v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the cross-product of the instance and the given points.
     * <p>
     * The cross product can be used to determine the location of a point
     * with regard to the line formed by (p1, p2) and is calculated as:
     * \[
     *    P = (x_2 - x_1)(y_3 - y_1) - (y_2 - y_1)(x_3 - x_1)
     * \]
     * with \(p3 = (x_3, y_3)\) being this instance.
     * <p>
     * If the result is 0, the points are collinear, i.e. lie on a single straight line L;
     * if it is positive, this point lies to the left, otherwise to the right of the line
     * formed by (p1, p2).
     *
     * @param p1 first point of the line
     * @param p2 second point of the line
     * @return the cross-product
     *
     * @see <a href="http://en.wikipedia.org/wiki/Cross_product">Cross product (Wikipedia)</a>
     */
    public double crossProduct(final Vector2D p1, final Vector2D p2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the distance between two vectors according to the L<sub>1</sub> norm.
     * <p>Calling this method is equivalent to calling:
     * <code>p1.subtract(p2).getNorm1()</code> except that no intermediate
     * vector is built</p>
     * @param p1 first vector
     * @param p2 second vector
     * @return the distance between p1 and p2 according to the L<sub>1</sub> norm
     * @since 1.6
     */
    public static double distance1(Vector2D p1, Vector2D p2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the distance between two vectors according to the L<sub>2</sub> norm.
     * <p>Calling this method is equivalent to calling:
     * <code>p1.subtract(p2).getNorm()</code> except that no intermediate
     * vector is built</p>
     * @param p1 first vector
     * @param p2 second vector
     * @return the distance between p1 and p2 according to the L<sub>2</sub> norm
     */
    public static double distance(Vector2D p1, Vector2D p2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the distance between two vectors according to the L<sub>&infin;</sub> norm.
     * <p>Calling this method is equivalent to calling:
     * <code>p1.subtract(p2).getNormInf()</code> except that no intermediate
     * vector is built</p>
     * @param p1 first vector
     * @param p2 second vector
     * @return the distance between p1 and p2 according to the L<sub>&infin;</sub> norm
     */
    public static double distanceInf(Vector2D p1, Vector2D p2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the square of the distance between two vectors.
     * <p>Calling this method is equivalent to calling:
     * <code>p1.subtract(p2).getNorm2Sq()</code> except that no intermediate
     * vector is built</p>
     * @param p1 first vector
     * @param p2 second vector
     * @return the square of the distance between p1 and p2
     */
    public static double distanceSq(Vector2D p1, Vector2D p2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector2D moveTowards(final Vector2D other, final double ratio) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the orientation of a triplet of points.
     * @param p first vector of the triplet
     * @param q second vector of the triplet
     * @param r third vector of the triplet
     * @return a positive value if (p, q, r) defines a counterclockwise oriented
     * triangle, a negative value if (p, q, r) defines a clockwise oriented
     * triangle, and 0 if (p, q, r) are collinear or some points are equal
     * @since 1.2
     */
    public static double orientation(final Vector2D p, final Vector2D q, final Vector2D r) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Test for the equality of two 2D vectors.
     * <p>
     * If all coordinates of two 2D vectors are exactly the same, and none are
     * {@code Double.NaN}, the two 2D vectors are considered to be equal.
     * </p>
     * <p>
     * {@code NaN} coordinates are considered to affect globally the vector
     * and be equals to each other - i.e, if either (or all) coordinates of the
     * 2D vector are equal to {@code Double.NaN}, the 2D vector is equal to
     * {@link #NaN}.
     * </p>
     *
     * @param other Object to test for equality to this
     * @return true if two 2D vector objects are equal, false if
     *         object is null, not an instance of Vector2D, or
     *         not equal to this Vector2D instance
     */
    @Override
    public boolean equals(Object other) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Test for the equality of two 2D vectors.
     * <p>
     * If all coordinates of two 2D vectors are exactly the same, and none are
     * {@code NaN}, the two 2D vectors are considered to be equal.
     * </p>
     * <p>
     * In compliance with IEEE754 handling, if any coordinates of any of the
     * two vectors are {@code NaN}, then the vectors are considered different.
     * This implies that {@link #NaN Vector2D.NaN}.equals({@link #NaN Vector2D.NaN})
     * returns {@code false} despite the instance is checked against itself.
     * </p>
     *
     * @param other Object to test for equality to this
     * @return true if two 2D vector objects are equal, false if
     *         object is null, not an instance of Vector2D, or
     *         not equal to this Vector2D instance
     * @since 2.1
     */
    public boolean equalsIeee754(Object other) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get a hashCode for the 2D vector.
     * <p>
     * All NaN values have the same hash code.</p>
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get a string representation of this vector.
     * @return a string representation of this vector
     */
    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString(final NumberFormat format) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
