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
package org.hipparchus.geometry.euclidean.twod;

import java.text.NumberFormat;
import org.hipparchus.Field;
import org.hipparchus.CalculusFieldElement;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.MathRuntimeException;
import org.hipparchus.geometry.LocalizedGeometryFormats;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.MathArrays;

/**
 * This class is a re-implementation of {@link Vector2D} using {@link CalculusFieldElement}.
 * <p>Instance of this class are guaranteed to be immutable.</p>
 * @param <T> the type of the field elements
 * @since 1.6
 */
public class FieldVector2D<T extends CalculusFieldElement<T>> {

    /**
     * Abscissa.
     */
    private final T x;

    /**
     * Ordinate.
     */
    private final T y;

    /**
     * Simple constructor.
     * Build a vector from its coordinates
     * @param x abscissa
     * @param y ordinate
     * @see #getX()
     * @see #getY()
     */
    public FieldVector2D(final T x, final T y) {
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
    public FieldVector2D(final T[] v) throws MathIllegalArgumentException {
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
    public FieldVector2D(final T a, final FieldVector2D<T> u) {
        this.x = a.multiply(u.x);
        this.y = a.multiply(u.y);
    }

    /**
     * Multiplicative constructor
     * Build a vector from another one and a scale factor.
     * The vector built will be a * u
     * @param a scale factor
     * @param u base (unscaled) vector
     */
    public FieldVector2D(final T a, final Vector2D u) {
        this.x = a.multiply(u.getX());
        this.y = a.multiply(u.getY());
    }

    /**
     * Multiplicative constructor
     * Build a vector from another one and a scale factor.
     * The vector built will be a * u
     * @param a scale factor
     * @param u base (unscaled) vector
     */
    public FieldVector2D(final double a, final FieldVector2D<T> u) {
        this.x = u.x.multiply(a);
        this.y = u.y.multiply(a);
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
    public FieldVector2D(final T a1, final FieldVector2D<T> u1, final T a2, final FieldVector2D<T> u2) {
        final T prototype = a1;
        this.x = prototype.linearCombination(a1, u1.getX(), a2, u2.getX());
        this.y = prototype.linearCombination(a1, u1.getY(), a2, u2.getY());
    }

    /**
     * Linear constructor.
     * Build a vector from two other ones and corresponding scale factors.
     * The vector built will be a1 * u1 + a2 * u2
     * @param a1 first scale factor
     * @param u1 first base (unscaled) vector
     * @param a2 second scale factor
     * @param u2 second base (unscaled) vector
     */
    public FieldVector2D(final T a1, final Vector2D u1, final T a2, final Vector2D u2) {
        final T prototype = a1;
        this.x = prototype.linearCombination(u1.getX(), a1, u2.getX(), a2);
        this.y = prototype.linearCombination(u1.getY(), a1, u2.getY(), a2);
    }

    /**
     * Linear constructor.
     * Build a vector from two other ones and corresponding scale factors.
     * The vector built will be a1 * u1 + a2 * u2
     * @param a1 first scale factor
     * @param u1 first base (unscaled) vector
     * @param a2 second scale factor
     * @param u2 second base (unscaled) vector
     */
    public FieldVector2D(final double a1, final FieldVector2D<T> u1, final double a2, final FieldVector2D<T> u2) {
        final T prototype = u1.getX();
        this.x = prototype.linearCombination(a1, u1.getX(), a2, u2.getX());
        this.y = prototype.linearCombination(a1, u1.getY(), a2, u2.getY());
    }

    /**
     * Linear constructor.
     * Build a vector from three other ones and corresponding scale factors.
     * The vector built will be a1 * u1 + a2 * u2 + a3 * u3
     * @param a1 first scale factor
     * @param u1 first base (unscaled) vector
     * @param a2 second scale factor
     * @param u2 second base (unscaled) vector
     * @param a3 third scale factor
     * @param u3 third base (unscaled) vector
     */
    public FieldVector2D(final T a1, final FieldVector2D<T> u1, final T a2, final FieldVector2D<T> u2, final T a3, final FieldVector2D<T> u3) {
        final T prototype = a1;
        this.x = prototype.linearCombination(a1, u1.getX(), a2, u2.getX(), a3, u3.getX());
        this.y = prototype.linearCombination(a1, u1.getY(), a2, u2.getY(), a3, u3.getY());
    }

    /**
     * Linear constructor.
     * Build a vector from three other ones and corresponding scale factors.
     * The vector built will be a1 * u1 + a2 * u2 + a3 * u3
     * @param a1 first scale factor
     * @param u1 first base (unscaled) vector
     * @param a2 second scale factor
     * @param u2 second base (unscaled) vector
     * @param a3 third scale factor
     * @param u3 third base (unscaled) vector
     */
    public FieldVector2D(final T a1, final Vector2D u1, final T a2, final Vector2D u2, final T a3, final Vector2D u3) {
        final T prototype = a1;
        this.x = prototype.linearCombination(u1.getX(), a1, u2.getX(), a2, u3.getX(), a3);
        this.y = prototype.linearCombination(u1.getY(), a1, u2.getY(), a2, u3.getY(), a3);
    }

    /**
     * Linear constructor.
     * Build a vector from three other ones and corresponding scale factors.
     * The vector built will be a1 * u1 + a2 * u2 + a3 * u3
     * @param a1 first scale factor
     * @param u1 first base (unscaled) vector
     * @param a2 second scale factor
     * @param u2 second base (unscaled) vector
     * @param a3 third scale factor
     * @param u3 third base (unscaled) vector
     */
    public FieldVector2D(final double a1, final FieldVector2D<T> u1, final double a2, final FieldVector2D<T> u2, final double a3, final FieldVector2D<T> u3) {
        final T prototype = u1.getX();
        this.x = prototype.linearCombination(a1, u1.getX(), a2, u2.getX(), a3, u3.getX());
        this.y = prototype.linearCombination(a1, u1.getY(), a2, u2.getY(), a3, u3.getY());
    }

    /**
     * Linear constructor.
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
    public FieldVector2D(final T a1, final FieldVector2D<T> u1, final T a2, final FieldVector2D<T> u2, final T a3, final FieldVector2D<T> u3, final T a4, final FieldVector2D<T> u4) {
        final T prototype = a1;
        this.x = prototype.linearCombination(a1, u1.getX(), a2, u2.getX(), a3, u3.getX(), a4, u4.getX());
        this.y = prototype.linearCombination(a1, u1.getY(), a2, u2.getY(), a3, u3.getY(), a4, u4.getY());
    }

    /**
     * Linear constructor.
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
    public FieldVector2D(final T a1, final Vector2D u1, final T a2, final Vector2D u2, final T a3, final Vector2D u3, final T a4, final Vector2D u4) {
        final T prototype = a1;
        this.x = prototype.linearCombination(u1.getX(), a1, u2.getX(), a2, u3.getX(), a3, u4.getX(), a4);
        this.y = prototype.linearCombination(u1.getY(), a1, u2.getY(), a2, u3.getY(), a3, u4.getY(), a4);
    }

    /**
     * Linear constructor.
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
    public FieldVector2D(final double a1, final FieldVector2D<T> u1, final double a2, final FieldVector2D<T> u2, final double a3, final FieldVector2D<T> u3, final double a4, final FieldVector2D<T> u4) {
        final T prototype = u1.getX();
        this.x = prototype.linearCombination(a1, u1.getX(), a2, u2.getX(), a3, u3.getX(), a4, u4.getX());
        this.y = prototype.linearCombination(a1, u1.getY(), a2, u2.getY(), a3, u3.getY(), a4, u4.getY());
    }

    /**
     * Build a {@link FieldVector2D} from a {@link Vector2D}.
     * @param field field for the components
     * @param v vector to convert
     */
    public FieldVector2D(final Field<T> field, final Vector2D v) {
        this.x = field.getZero().add(v.getX());
        this.y = field.getZero().add(v.getY());
    }

    /**
     * Get null vector (coordinates: 0, 0).
     * @param field field for the components
     * @return a new vector
     * @param <T> the type of the field elements
     */
    public static <T extends CalculusFieldElement<T>> FieldVector2D<T> getZero(final Field<T> field) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get first canonical vector (coordinates: 1, 0).
     * @param field field for the components
     * @return a new vector
     * @param <T> the type of the field elements
     */
    public static <T extends CalculusFieldElement<T>> FieldVector2D<T> getPlusI(final Field<T> field) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get opposite of the first canonical vector (coordinates: -1).
     * @param field field for the components
     * @return a new vector
     * @param <T> the type of the field elements
     */
    public static <T extends CalculusFieldElement<T>> FieldVector2D<T> getMinusI(final Field<T> field) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get second canonical vector (coordinates: 0, 1).
     * @param field field for the components
     * @return a new vector
     * @param <T> the type of the field elements
     */
    public static <T extends CalculusFieldElement<T>> FieldVector2D<T> getPlusJ(final Field<T> field) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get opposite of the second canonical vector (coordinates: 0, -1).
     * @param field field for the components
     * @return a new vector
     * @param <T> the type of the field elements
     */
    public static <T extends CalculusFieldElement<T>> FieldVector2D<T> getMinusJ(final Field<T> field) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get a vector with all coordinates set to NaN.
     * @param field field for the components
     * @return a new vector
     * @param <T> the type of the field elements
     */
    public static <T extends CalculusFieldElement<T>> FieldVector2D<T> getNaN(final Field<T> field) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get a vector with all coordinates set to positive infinity.
     * @param field field for the components
     * @return a new vector
     * @param <T> the type of the field elements
     */
    public static <T extends CalculusFieldElement<T>> FieldVector2D<T> getPositiveInfinity(final Field<T> field) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get a vector with all coordinates set to negative infinity.
     * @param field field for the components
     * @return a new vector
     * @param <T> the type of the field elements
     */
    public static <T extends CalculusFieldElement<T>> FieldVector2D<T> getNegativeInfinity(final Field<T> field) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the abscissa of the vector.
     * @return abscissa of the vector
     * @see #FieldVector2D(CalculusFieldElement, CalculusFieldElement)
     */
    public T getX() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the ordinate of the vector.
     * @return ordinate of the vector
     * @see #FieldVector2D(CalculusFieldElement, CalculusFieldElement)
     */
    public T getY() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the vector coordinates as a dimension 2 array.
     * @return vector coordinates
     * @see #FieldVector2D(CalculusFieldElement[])
     */
    public T[] toArray() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convert to a constant vector without extra field parts.
     * @return a constant vector
     */
    public Vector2D toVector2D() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the L<sub>1</sub> norm for the vector.
     * @return L<sub>1</sub> norm for the vector
     */
    public T getNorm1() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the L<sub>2</sub> norm for the vector.
     * @return Euclidean norm for the
     * @since 4.1
     */
    public T getNorm2() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the most common norm for the vector, by default the Euclidean one.
     * @return norm for the vector
     */
    public T getNorm() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the square of the norm for the vector.
     * @return square of the norm for the vector
     * @deprecated since 4.1, use getNorm2Sq
     */
    @Deprecated
    public T getNormSq() {
        // there are no cancellation problems here, so we use the straightforward formula
        return getNorm2Sq();
    }

    /**
     * Get the square of the 2-norm for the vector.
     * @return square of the Euclidean norm for the vector
     * @since 4.1
     */
    public T getNorm2Sq() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the L<sub>&infin;</sub> norm for the vector.
     * @return L<sub>&infin;</sub> norm for the vector
     */
    public T getNormInf() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Add a vector to the instance.
     * @param v vector to add
     * @return a new vector
     */
    public FieldVector2D<T> add(final FieldVector2D<T> v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Add a vector to the instance.
     * @param v vector to add
     * @return a new vector
     */
    public FieldVector2D<T> add(final Vector2D v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Add a scaled vector to the instance.
     * @param factor scale factor to apply to v before adding it
     * @param v vector to add
     * @return a new vector
     */
    public FieldVector2D<T> add(final T factor, final FieldVector2D<T> v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Add a scaled vector to the instance.
     * @param factor scale factor to apply to v before adding it
     * @param v vector to add
     * @return a new vector
     */
    public FieldVector2D<T> add(final T factor, final Vector2D v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Add a scaled vector to the instance.
     * @param factor scale factor to apply to v before adding it
     * @param v vector to add
     * @return a new vector
     */
    public FieldVector2D<T> add(final double factor, final FieldVector2D<T> v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Add a scaled vector to the instance.
     * @param factor scale factor to apply to v before adding it
     * @param v vector to add
     * @return a new vector
     */
    public FieldVector2D<T> add(final double factor, final Vector2D v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Subtract a vector from the instance.
     * @param v vector to subtract
     * @return a new vector
     */
    public FieldVector2D<T> subtract(final FieldVector2D<T> v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Subtract a vector from the instance.
     * @param v vector to subtract
     * @return a new vector
     */
    public FieldVector2D<T> subtract(final Vector2D v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Subtract a scaled vector from the instance.
     * @param factor scale factor to apply to v before subtracting it
     * @param v vector to subtract
     * @return a new vector
     */
    public FieldVector2D<T> subtract(final T factor, final FieldVector2D<T> v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Subtract a scaled vector from the instance.
     * @param factor scale factor to apply to v before subtracting it
     * @param v vector to subtract
     * @return a new vector
     */
    public FieldVector2D<T> subtract(final T factor, final Vector2D v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Subtract a scaled vector from the instance.
     * @param factor scale factor to apply to v before subtracting it
     * @param v vector to subtract
     * @return a new vector
     */
    public FieldVector2D<T> subtract(final double factor, final FieldVector2D<T> v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Subtract a scaled vector from the instance.
     * @param factor scale factor to apply to v before subtracting it
     * @param v vector to subtract
     * @return a new vector
     */
    public FieldVector2D<T> subtract(final double factor, final Vector2D v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get a normalized vector aligned with the instance.
     * @return a new normalized vector
     * @exception MathRuntimeException if the norm is zero
     */
    public FieldVector2D<T> normalize() throws MathRuntimeException {
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
     * @param <T> the type of the field elements
     * @return angular separation between v1 and v2
     * @exception MathRuntimeException if either vector has a null norm
     */
    public static <T extends CalculusFieldElement<T>> T angle(final FieldVector2D<T> v1, final FieldVector2D<T> v2) throws MathRuntimeException {
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
     * @param <T> the type of the field elements
     * @return angular separation between v1 and v2
     * @exception MathRuntimeException if either vector has a null norm
     */
    public static <T extends CalculusFieldElement<T>> T angle(final FieldVector2D<T> v1, final Vector2D v2) throws MathRuntimeException {
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
     * @param <T> the type of the field elements
     * @return angular separation between v1 and v2
     * @exception MathRuntimeException if either vector has a null norm
     */
    public static <T extends CalculusFieldElement<T>> T angle(final Vector2D v1, final FieldVector2D<T> v2) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the opposite of the instance.
     * @return a new vector which is opposite to the instance
     */
    public FieldVector2D<T> negate() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Multiply the instance by a scalar.
     * @param a scalar
     * @return a new vector
     */
    public FieldVector2D<T> scalarMultiply(final T a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Multiply the instance by a scalar.
     * @param a scalar
     * @return a new vector
     */
    public FieldVector2D<T> scalarMultiply(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns true if any coordinate of this vector is NaN; false otherwise
     * @return  true if any coordinate of this vector is NaN; false otherwise
     */
    public boolean isNaN() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns true if any coordinate of this vector is infinite and none are NaN;
     * false otherwise
     * @return  true if any coordinate of this vector is infinite and none are NaN;
     * false otherwise
     */
    public boolean isInfinite() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Test for the equality of two 2D vectors.
     * <p>
     * If all coordinates of two 2D vectors are exactly the same, and none of their
     * {@link CalculusFieldElement#getReal() real part} are <code>NaN</code>, the
     * two 2D vectors are considered to be equal.
     * </p>
     * <p>
     * <code>NaN</code> coordinates are considered to affect globally the vector
     * and be equals to each other - i.e, if either (or all) real part of the
     * coordinates of the 3D vector are <code>NaN</code>, the 2D vector is <code>NaN</code>.
     * </p>
     *
     * @param other Object to test for equality to this
     * @return true if two 2D vector objects are equal, false if
     *         object is null, not an instance of FieldVector2D, or
     *         not equal to this FieldVector2D instance
     */
    @Override
    public boolean equals(Object other) {
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
     * Compute the distance between the instance and another vector according to the L<sub>1</sub> norm.
     * <p>Calling this method is equivalent to calling:
     * <code>q.subtract(p).getNorm1()</code> except that no intermediate
     * vector is built</p>
     * @param v second vector
     * @return the distance between the instance and p according to the L<sub>1</sub> norm
     */
    public T distance1(final FieldVector2D<T> v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the distance between the instance and another vector according to the L<sub>1</sub> norm.
     * <p>Calling this method is equivalent to calling:
     * <code>q.subtract(p).getNorm1()</code> except that no intermediate
     * vector is built</p>
     * @param v second vector
     * @return the distance between the instance and p according to the L<sub>1</sub> norm
     */
    public T distance1(final Vector2D v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the distance between the instance and another vector according to the L<sub>2</sub> norm.
     * <p>Calling this method is equivalent to calling:
     * <code>q.subtract(p).getNorm()</code> except that no intermediate
     * vector is built</p>
     * @param v second vector
     * @return the distance between the instance and p according to the L<sub>2</sub> norm
     */
    public T distance(final FieldVector2D<T> v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the distance between the instance and another vector according to the L<sub>2</sub> norm.
     * <p>Calling this method is equivalent to calling:
     * <code>q.subtract(p).getNorm()</code> except that no intermediate
     * vector is built</p>
     * @param v second vector
     * @return the distance between the instance and p according to the L<sub>2</sub> norm
     */
    public T distance(final Vector2D v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the distance between the instance and another vector according to the L<sub>&infin;</sub> norm.
     * <p>Calling this method is equivalent to calling:
     * <code>q.subtract(p).getNormInf()</code> except that no intermediate
     * vector is built</p>
     * @param v second vector
     * @return the distance between the instance and p according to the L<sub>&infin;</sub> norm
     */
    public T distanceInf(final FieldVector2D<T> v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the distance between the instance and another vector according to the L<sub>&infin;</sub> norm.
     * <p>Calling this method is equivalent to calling:
     * <code>q.subtract(p).getNormInf()</code> except that no intermediate
     * vector is built</p>
     * @param v second vector
     * @return the distance between the instance and p according to the L<sub>&infin;</sub> norm
     */
    public T distanceInf(final Vector2D v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the square of the distance between the instance and another vector.
     * <p>Calling this method is equivalent to calling:
     * <code>q.subtract(p).getNorm2Sq()</code> except that no intermediate
     * vector is built</p>
     * @param v second vector
     * @return the square of the distance between the instance and p
     */
    public T distanceSq(final FieldVector2D<T> v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the square of the distance between the instance and another vector.
     * <p>Calling this method is equivalent to calling:
     * <code>q.subtract(p).getNorm2Sq()</code> except that no intermediate
     * vector is built</p>
     * @param v second vector
     * @return the square of the distance between the instance and p
     */
    public T distanceSq(final Vector2D v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the dot-product of the instance and another vector.
     * <p>
     * The implementation uses specific multiplication and addition
     * algorithms to preserve accuracy and reduce cancellation effects.
     * It should be very accurate even for nearly orthogonal vectors.
     * </p>
     * @see MathArrays#linearCombination(double, double, double, double, double, double)
     * @param v second vector
     * @return the dot product this.v
     */
    public T dotProduct(final FieldVector2D<T> v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the dot-product of the instance and another vector.
     * <p>
     * The implementation uses specific multiplication and addition
     * algorithms to preserve accuracy and reduce cancellation effects.
     * It should be very accurate even for nearly orthogonal vectors.
     * </p>
     * @see MathArrays#linearCombination(double, double, double, double, double, double)
     * @param v second vector
     * @return the dot product this.v
     */
    public T dotProduct(final Vector2D v) {
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
    public T crossProduct(final FieldVector2D<T> p1, final FieldVector2D<T> p2) {
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
    public T crossProduct(final Vector2D p1, final Vector2D p2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the distance between two vectors according to the L<sub>2</sub> norm.
     * <p>Calling this method is equivalent to calling:
     * <code>p1.subtract(p2).getNorm()</code> except that no intermediate
     * vector is built</p>
     * @param p1 first vector
     * @param p2 second vector
     * @param <T> the type of the field elements
     * @return the distance between p1 and p2 according to the L<sub>2</sub> norm
     */
    public static <T extends CalculusFieldElement<T>> T distance1(final FieldVector2D<T> p1, final FieldVector2D<T> p2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the distance between two vectors according to the L<sub>2</sub> norm.
     * <p>Calling this method is equivalent to calling:
     * <code>p1.subtract(p2).getNorm()</code> except that no intermediate
     * vector is built</p>
     * @param p1 first vector
     * @param p2 second vector
     * @param <T> the type of the field elements
     * @return the distance between p1 and p2 according to the L<sub>2</sub> norm
     */
    public static <T extends CalculusFieldElement<T>> T distance1(final FieldVector2D<T> p1, final Vector2D p2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the distance between two vectors according to the L<sub>2</sub> norm.
     * <p>Calling this method is equivalent to calling:
     * <code>p1.subtract(p2).getNorm()</code> except that no intermediate
     * vector is built</p>
     * @param p1 first vector
     * @param p2 second vector
     * @param <T> the type of the field elements
     * @return the distance between p1 and p2 according to the L<sub>2</sub> norm
     */
    public static <T extends CalculusFieldElement<T>> T distance1(final Vector2D p1, final FieldVector2D<T> p2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the distance between two vectors according to the L<sub>2</sub> norm.
     * <p>Calling this method is equivalent to calling:
     * <code>p1.subtract(p2).getNorm()</code> except that no intermediate
     * vector is built</p>
     * @param p1 first vector
     * @param p2 second vector
     * @param <T> the type of the field elements
     * @return the distance between p1 and p2 according to the L<sub>2</sub> norm
     */
    public static <T extends CalculusFieldElement<T>> T distance(final FieldVector2D<T> p1, final FieldVector2D<T> p2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the distance between two vectors according to the L<sub>2</sub> norm.
     * <p>Calling this method is equivalent to calling:
     * <code>p1.subtract(p2).getNorm()</code> except that no intermediate
     * vector is built</p>
     * @param p1 first vector
     * @param p2 second vector
     * @param <T> the type of the field elements
     * @return the distance between p1 and p2 according to the L<sub>2</sub> norm
     */
    public static <T extends CalculusFieldElement<T>> T distance(final FieldVector2D<T> p1, final Vector2D p2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the distance between two vectors according to the L<sub>2</sub> norm.
     * <p>Calling this method is equivalent to calling:
     * <code>p1.subtract(p2).getNorm()</code> except that no intermediate
     * vector is built</p>
     * @param p1 first vector
     * @param p2 second vector
     * @param <T> the type of the field elements
     * @return the distance between p1 and p2 according to the L<sub>2</sub> norm
     */
    public static <T extends CalculusFieldElement<T>> T distance(final Vector2D p1, final FieldVector2D<T> p2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the distance between two vectors according to the L<sub>&infin;</sub> norm.
     * <p>Calling this method is equivalent to calling:
     * <code>p1.subtract(p2).getNormInf()</code> except that no intermediate
     * vector is built</p>
     * @param p1 first vector
     * @param p2 second vector
     * @param <T> the type of the field elements
     * @return the distance between p1 and p2 according to the L<sub>&infin;</sub> norm
     */
    public static <T extends CalculusFieldElement<T>> T distanceInf(final FieldVector2D<T> p1, final FieldVector2D<T> p2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the distance between two vectors according to the L<sub>&infin;</sub> norm.
     * <p>Calling this method is equivalent to calling:
     * <code>p1.subtract(p2).getNormInf()</code> except that no intermediate
     * vector is built</p>
     * @param p1 first vector
     * @param p2 second vector
     * @param <T> the type of the field elements
     * @return the distance between p1 and p2 according to the L<sub>&infin;</sub> norm
     */
    public static <T extends CalculusFieldElement<T>> T distanceInf(final FieldVector2D<T> p1, final Vector2D p2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the distance between two vectors according to the L<sub>&infin;</sub> norm.
     * <p>Calling this method is equivalent to calling:
     * <code>p1.subtract(p2).getNormInf()</code> except that no intermediate
     * vector is built</p>
     * @param p1 first vector
     * @param p2 second vector
     * @param <T> the type of the field elements
     * @return the distance between p1 and p2 according to the L<sub>&infin;</sub> norm
     */
    public static <T extends CalculusFieldElement<T>> T distanceInf(final Vector2D p1, final FieldVector2D<T> p2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the square of the distance between two vectors.
     * <p>Calling this method is equivalent to calling:
     * <code>p1.subtract(p2).getNorm2Sq()</code> except that no intermediate
     * vector is built</p>
     * @param p1 first vector
     * @param p2 second vector
     * @param <T> the type of the field elements
     * @return the square of the distance between p1 and p2
     */
    public static <T extends CalculusFieldElement<T>> T distanceSq(final FieldVector2D<T> p1, final FieldVector2D<T> p2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the square of the distance between two vectors.
     * <p>Calling this method is equivalent to calling:
     * <code>p1.subtract(p2).getNorm2Sq()</code> except that no intermediate
     * vector is built</p>
     * @param p1 first vector
     * @param p2 second vector
     * @param <T> the type of the field elements
     * @return the square of the distance between p1 and p2
     */
    public static <T extends CalculusFieldElement<T>> T distanceSq(final FieldVector2D<T> p1, final Vector2D p2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the square of the distance between two vectors.
     * <p>Calling this method is equivalent to calling:
     * <code>p1.subtract(p2).getNorm2Sq()</code> except that no intermediate
     * vector is built</p>
     * @param p1 first vector
     * @param p2 second vector
     * @param <T> the type of the field elements
     * @return the square of the distance between p1 and p2
     */
    public static <T extends CalculusFieldElement<T>> T distanceSq(final Vector2D p1, final FieldVector2D<T> p2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the orientation of a triplet of points.
     * @param p first vector of the triplet
     * @param q second vector of the triplet
     * @param r third vector of the triplet
     * @param <T> the type of the field elements
     * @return a positive value if (p, q, r) defines a counterclockwise oriented
     * triangle, a negative value if (p, q, r) defines a clockwise oriented
     * triangle, and 0 if (p, q, r) are collinear or some points are equal
     * @since 1.2
     */
    public static <T extends CalculusFieldElement<T>> T orientation(final FieldVector2D<T> p, final FieldVector2D<T> q, final FieldVector2D<T> r) {
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
     * Get a string representation of this vector.
     * @param format the custom format for components
     * @return a string representation of this vector
     */
    public String toString(final NumberFormat format) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
