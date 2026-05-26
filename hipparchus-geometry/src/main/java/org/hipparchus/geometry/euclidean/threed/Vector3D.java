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
import org.hipparchus.exception.MathRuntimeException;
import org.hipparchus.geometry.Space;
import org.hipparchus.geometry.Vector;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.MathArrays;
import org.hipparchus.util.MathUtils;
import org.hipparchus.util.SinCos;
import java.io.Serializable;
import java.text.NumberFormat;

/**
 * This class implements vectors in a three-dimensional space.
 * <p>Instance of this class are guaranteed to be immutable.</p>
 */
public class Vector3D implements Serializable, Vector<Euclidean3D, Vector3D> {

    /**
     * Null vector (coordinates: 0, 0, 0).
     */
    public static final Vector3D ZERO = new Vector3D(0, 0, 0);

    /**
     * First canonical vector (coordinates: 1, 0, 0).
     */
    public static final Vector3D PLUS_I = new Vector3D(1, 0, 0);

    /**
     * Opposite of the first canonical vector (coordinates: -1, 0, 0).
     */
    public static final Vector3D MINUS_I = new Vector3D(-1, 0, 0);

    /**
     * Second canonical vector (coordinates: 0, 1, 0).
     */
    public static final Vector3D PLUS_J = new Vector3D(0, 1, 0);

    /**
     * Opposite of the second canonical vector (coordinates: 0, -1, 0).
     */
    public static final Vector3D MINUS_J = new Vector3D(0, -1, 0);

    /**
     * Third canonical vector (coordinates: 0, 0, 1).
     */
    public static final Vector3D PLUS_K = new Vector3D(0, 0, 1);

    /**
     * Opposite of the third canonical vector (coordinates: 0, 0, -1).
     */
    public static final Vector3D MINUS_K = new Vector3D(0, 0, -1);

    // CHECKSTYLE: stop ConstantName
    /**
     * A vector with all coordinates set to NaN.
     */
    public static final Vector3D NaN = new Vector3D(Double.NaN, Double.NaN, Double.NaN);

    // CHECKSTYLE: resume ConstantName
    /**
     * A vector with all coordinates set to positive infinity.
     */
    public static final Vector3D POSITIVE_INFINITY = new Vector3D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);

    /**
     * A vector with all coordinates set to negative infinity.
     */
    public static final Vector3D NEGATIVE_INFINITY = new Vector3D(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);

    /**
     * Serializable version identifier.
     */
    private static final long serialVersionUID = 1313493323784566947L;

    /**
     * Abscissa.
     */
    private final double x;

    /**
     * Ordinate.
     */
    private final double y;

    /**
     * Height.
     */
    private final double z;

    /**
     * Simple constructor.
     * Build a vector from its coordinates
     * @param x abscissa
     * @param y ordinate
     * @param z height
     * @see #getX()
     * @see #getY()
     * @see #getZ()
     */
    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Simple constructor.
     * Build a vector from its coordinates
     * @param v coordinates array
     * @exception MathIllegalArgumentException if array does not have 3 elements
     * @see #toArray()
     */
    public Vector3D(double[] v) throws MathIllegalArgumentException {
        if (v.length != 3) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.DIMENSIONS_MISMATCH, v.length, 3);
        }
        this.x = v[0];
        this.y = v[1];
        this.z = v[2];
    }

    /**
     * Simple constructor.
     * Build a vector from its azimuthal coordinates
     * @param alpha azimuth (&alpha;) around Z
     *              (0 is +X, &pi;/2 is +Y, &pi; is -X and 3&pi;/2 is -Y)
     * @param delta elevation (&delta;) above (XY) plane, from -&pi;/2 to +&pi;/2
     * @see #getAlpha()
     * @see #getDelta()
     */
    public Vector3D(double alpha, double delta) {
        SinCos sinCosAlpha = FastMath.sinCos(alpha);
        SinCos sinCosDelta = FastMath.sinCos(delta);
        this.x = sinCosAlpha.cos() * sinCosDelta.cos();
        this.y = sinCosAlpha.sin() * sinCosDelta.cos();
        this.z = sinCosDelta.sin();
    }

    /**
     * Multiplicative constructor
     * Build a vector from another one and a scale factor.
     * The vector built will be a * u
     * @param a scale factor
     * @param u base (unscaled) vector
     */
    public Vector3D(double a, Vector3D u) {
        this.x = a * u.x;
        this.y = a * u.y;
        this.z = a * u.z;
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
    public Vector3D(double a1, Vector3D u1, double a2, Vector3D u2) {
        this.x = MathArrays.linearCombination(a1, u1.x, a2, u2.x);
        this.y = MathArrays.linearCombination(a1, u1.y, a2, u2.y);
        this.z = MathArrays.linearCombination(a1, u1.z, a2, u2.z);
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
    public Vector3D(double a1, Vector3D u1, double a2, Vector3D u2, double a3, Vector3D u3) {
        this.x = MathArrays.linearCombination(a1, u1.x, a2, u2.x, a3, u3.x);
        this.y = MathArrays.linearCombination(a1, u1.y, a2, u2.y, a3, u3.y);
        this.z = MathArrays.linearCombination(a1, u1.z, a2, u2.z, a3, u3.z);
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
    public Vector3D(double a1, Vector3D u1, double a2, Vector3D u2, double a3, Vector3D u3, double a4, Vector3D u4) {
        this.x = MathArrays.linearCombination(a1, u1.x, a2, u2.x, a3, u3.x, a4, u4.x);
        this.y = MathArrays.linearCombination(a1, u1.y, a2, u2.y, a3, u3.y, a4, u4.y);
        this.z = MathArrays.linearCombination(a1, u1.z, a2, u2.z, a3, u3.z, a4, u4.z);
    }

    /**
     * Get the abscissa of the vector.
     * @return abscissa of the vector
     * @see #Vector3D(double, double, double)
     */
    public double getX() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the ordinate of the vector.
     * @return ordinate of the vector
     * @see #Vector3D(double, double, double)
     */
    public double getY() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the height of the vector.
     * @return height of the vector
     * @see #Vector3D(double, double, double)
     */
    public double getZ() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the vector coordinates as a dimension 3 array.
     * @return vector coordinates
     * @see #Vector3D(double[])
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
    public Vector3D getZero() {
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
        // there are no cancellation problems here, so we use the straightforward formula
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
     * Get the azimuth of the vector.
     * @return azimuth (&alpha;) of the vector, between -&pi; and +&pi;
     * @see #Vector3D(double, double)
     */
    public double getAlpha() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the elevation of the vector.
     * @return elevation (&delta;) of the vector, between -&pi;/2 and +&pi;/2
     * @see #Vector3D(double, double)
     */
    public double getDelta() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector3D add(final Vector3D v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector3D add(double factor, final Vector3D v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector3D subtract(final Vector3D v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector3D subtract(final double factor, final Vector3D v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get a vector orthogonal to the instance.
     * <p>There are an infinite number of normalized vectors orthogonal
     * to the instance. This method picks up one of them almost
     * arbitrarily. It is useful when one needs to compute a reference
     * frame with one of the axes in a predefined direction. The
     * following example shows how to build a frame having the k axis
     * aligned with the known vector u :
     * </p>
     * <pre><code>
     *   Vector3D k = u.normalize();
     *   Vector3D i = k.orthogonal();
     *   Vector3D j = Vector3D.crossProduct(k, i);
     * </code></pre>
     * @return a new normalized vector orthogonal to the instance
     * @exception MathRuntimeException if the norm of the instance is null
     */
    public Vector3D orthogonal() throws MathRuntimeException {
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
    public static double angle(Vector3D v1, Vector3D v2) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector3D negate() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector3D scalarMultiply(double a) {
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
     * Test for the equality of two 3D vectors.
     * <p>
     * If all coordinates of two 3D vectors are exactly the same, and none are
     * {@code Double.NaN}, the two 3D vectors are considered to be equal.
     * </p>
     * <p>
     * {@code NaN} coordinates are considered to affect globally the vector
     * and be equals to each other - i.e, if either (or all) coordinates of the
     * 3D vector are equal to {@code Double.NaN}, the 3D vector is equal to
     * {@link #NaN}.
     * </p>
     *
     * @param other Object to test for equality to this
     * @return true if two 3D vector objects are equal, false if
     *         object is null, not an instance of Vector3D, or
     *         not equal to this Vector3D instance
     */
    @Override
    public boolean equals(Object other) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Test for the equality of two 3D vectors.
     * <p>
     * If all coordinates of two 3D vectors are exactly the same, and none are
     * {@code NaN}, the two 3D vectors are considered to be equal.
     * </p>
     * <p>
     * In compliance with IEEE754 handling, if any coordinates of any of the
     * two vectors are {@code NaN}, then the vectors are considered different.
     * This implies that {@link #NaN Vector3D.NaN}.equals({@link #NaN Vector3D.NaN})
     * returns {@code false} despite the instance is checked against itself.
     * </p>
     *
     * @param other Object to test for equality to this
     * @return true if two 3D vector objects are equal, false if
     *         object is null, not an instance of Vector3D, or
     *         not equal to this Vector3D instance
     * @since 2.1
     */
    public boolean equalsIeee754(Object other) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get a hashCode for the 3D vector.
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
     * {@inheritDoc}
     * <p>
     * The implementation uses specific multiplication and addition
     * algorithms to preserve accuracy and reduce cancellation effects.
     * It should be very accurate even for nearly orthogonal vectors.
     * </p>
     * @see MathArrays#linearCombination(double, double, double, double, double, double)
     */
    @Override
    public double dotProduct(final Vector3D v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the cross-product of the instance with another vector.
     * @param v other vector
     * @return the cross product this ^ v as a new Vector3D
     */
    public Vector3D crossProduct(final Vector3D v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double distance1(Vector3D v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double distance(Vector3D v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double distanceInf(Vector3D v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double distanceSq(Vector3D v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the dot-product of two vectors.
     * @param v1 first vector
     * @param v2 second vector
     * @return the dot product v1.v2
     */
    public static double dotProduct(Vector3D v1, Vector3D v2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the cross-product of two vectors.
     * @param v1 first vector
     * @param v2 second vector
     * @return the cross product v1 ^ v2 as a new Vector
     */
    public static Vector3D crossProduct(final Vector3D v1, final Vector3D v2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the distance between two vectors according to the L<sub>1</sub> norm.
     * <p>Calling this method is equivalent to calling:
     * <code>v1.subtract(v2).getNorm1()</code> except that no intermediate
     * vector is built</p>
     * @param v1 first vector
     * @param v2 second vector
     * @return the distance between v1 and v2 according to the L<sub>1</sub> norm
     */
    public static double distance1(Vector3D v1, Vector3D v2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the distance between two vectors according to the L<sub>2</sub> norm.
     * <p>Calling this method is equivalent to calling:
     * <code>v1.subtract(v2).getNorm()</code> except that no intermediate
     * vector is built</p>
     * @param v1 first vector
     * @param v2 second vector
     * @return the distance between v1 and v2 according to the L<sub>2</sub> norm
     */
    public static double distance(Vector3D v1, Vector3D v2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the distance between two vectors according to the L<sub>&infin;</sub> norm.
     * <p>Calling this method is equivalent to calling:
     * <code>v1.subtract(v2).getNormInf()</code> except that no intermediate
     * vector is built</p>
     * @param v1 first vector
     * @param v2 second vector
     * @return the distance between v1 and v2 according to the L<sub>&infin;</sub> norm
     */
    public static double distanceInf(Vector3D v1, Vector3D v2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the square of the distance between two vectors.
     * <p>Calling this method is equivalent to calling:
     * <code>v1.subtract(v2).getNorm2Sq()</code> except that no intermediate
     * vector is built</p>
     * @param v1 first vector
     * @param v2 second vector
     * @return the square of the distance between v1 and v2
     */
    public static double distanceSq(Vector3D v1, Vector3D v2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector3D moveTowards(final Vector3D other, final double ratio) {
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
