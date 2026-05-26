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
package org.hipparchus.util;

import org.hipparchus.CalculusFieldElement;
import org.hipparchus.Field;
import org.hipparchus.exception.MathIllegalArgumentException;

/**
 * This class wraps a {@code double} value in an object. It is similar to the
 * standard class {@link Double}, while also implementing the
 * {@link CalculusFieldElement} interface.
 */
public class Binary64 extends Number implements CalculusFieldElement<Binary64>, Comparable<Binary64> {

    /**
     * The constant value of {@code 0d} as a {@code Binary64}.
     */
    public static final Binary64 ZERO;

    /**
     * The constant value of {@code 1d} as a {@code Binary64}.
     */
    public static final Binary64 ONE;

    /**
     * The constant value of π as a {@code Binary64}.
     */
    public static final Binary64 PI;

    /**
     * The constant value of {@link Double#NEGATIVE_INFINITY} as a
     * {@code Binary64}.
     */
    public static final Binary64 NEGATIVE_INFINITY;

    /**
     * The constant value of {@link Double#POSITIVE_INFINITY} as a
     * {@code Binary64}.
     */
    public static final Binary64 POSITIVE_INFINITY;

    /**
     * The constant value of {@link Double#NaN} as a {@code Binary64}.
     */
    public static final Binary64 NAN;

    /**
     */
    private static final long serialVersionUID = 20120227L;

    static {
        ZERO = new Binary64(0d);
        ONE = new Binary64(1d);
        PI = new Binary64(FastMath.PI);
        NEGATIVE_INFINITY = new Binary64(Double.NEGATIVE_INFINITY);
        POSITIVE_INFINITY = new Binary64(Double.POSITIVE_INFINITY);
        NAN = new Binary64(Double.NaN);
    }

    /**
     * The primitive {@code double} value of this object.
     */
    private final double value;

    /**
     * Creates a new instance of this class.
     *
     * @param x the primitive {@code double} value of the object to be created
     */
    public Binary64(final double x) {
        this.value = x;
    }

    /*
     * Methods from the FieldElement interface.
     */
    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 newInstance(final double v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Binary64> getField() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * The current implementation strictly enforces
     * {@code this.add(a).equals(new Binary64(this.doubleValue()
     * + a.doubleValue()))}.
     */
    @Override
    public Binary64 add(final Binary64 a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * The current implementation strictly enforces
     * {@code this.subtract(a).equals(new Binary64(this.doubleValue()
     * - a.doubleValue()))}.
     */
    @Override
    public Binary64 subtract(final Binary64 a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * The current implementation strictly enforces
     * {@code this.negate().equals(new Binary64(-this.doubleValue()))}.
     */
    @Override
    public Binary64 negate() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Binary64 square() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * The current implementation strictly enforces
     * {@code this.multiply(a).equals(new Binary64(this.doubleValue()
     * * a.doubleValue()))}.
     */
    @Override
    public Binary64 multiply(final Binary64 a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * The current implementation strictly enforces
     * {@code this.multiply(n).equals(new Binary64(n * this.doubleValue()))}.
     */
    @Override
    public Binary64 multiply(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * The current implementation strictly enforces
     * {@code this.divide(a).equals(new Binary64(this.doubleValue()
     * / a.doubleValue()))}.
     */
    @Override
    public Binary64 divide(final Binary64 a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * The current implementation strictly enforces
     * {@code this.reciprocal().equals(new Binary64(1.0
     * / this.doubleValue()))}.
     */
    @Override
    public Binary64 reciprocal() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
     * Methods from the Number abstract class
     */
    /**
     * {@inheritDoc}
     *
     * The current implementation performs casting to a {@code byte}.
     */
    @Override
    public byte byteValue() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * The current implementation performs casting to a {@code short}.
     */
    @Override
    public short shortValue() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * The current implementation performs casting to a {@code int}.
     */
    @Override
    public int intValue() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * The current implementation performs casting to a {@code long}.
     */
    @Override
    public long longValue() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * The current implementation performs casting to a {@code float}.
     */
    @Override
    public float floatValue() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double doubleValue() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
     * Methods from the Comparable interface.
     */
    /**
     * {@inheritDoc}
     *
     * The current implementation returns the same value as
     * {@code new Double(this.doubleValue()).compareTo(new
     * Double(o.doubleValue()))}
     *
     * @see Double#compareTo(Double)
     */
    @Override
    public int compareTo(final Binary64 o) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
     * Methods from the Object abstract class.
     */
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation considers +0.0 and -0.0 to be equal.
     * </p>
     * @since 1.8
     */
    @Override
    public boolean isZero() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * The current implementation returns the same value as
     * {@code new Double(this.doubleValue()).hashCode()}
     *
     * @see Double#hashCode()
     */
    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * The returned {@code String} is equal to
     * {@code Double.toString(this.doubleValue())}
     *
     * @see Double#toString(double)
     */
    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
     * Methods inspired by the Double class.
     */
    /**
     * Returns {@code true} if {@code this} double precision number is infinite
     * ({@link Double#POSITIVE_INFINITY} or {@link Double#NEGATIVE_INFINITY}).
     *
     * @return {@code true} if {@code this} number is infinite
     */
    @Override
    public boolean isInfinite() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns {@code true} if {@code this} double precision number is
     * Not-a-Number ({@code NaN}), false otherwise.
     *
     * @return {@code true} if {@code this} is {@code NaN}
     */
    @Override
    public boolean isNaN() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getReal() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 getAddendum() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 add(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 subtract(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 multiply(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 divide(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 remainder(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 remainder(final Binary64 a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 abs() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 ceil() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 floor() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 rint() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 sign() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 copySign(final Binary64 sign) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 copySign(final double sign) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 scalb(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 ulp() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 hypot(final Binary64 y) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 sqrt() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 cbrt() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 rootN(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 pow(final double p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 pow(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 pow(final Binary64 e) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 exp() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 expm1() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 log() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 log1p() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Base 10 logarithm.
     * @return base 10 logarithm of the instance
     */
    @Override
    public Binary64 log10() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 cos() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 sin() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldSinCos<Binary64> sinCos() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 tan() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 acos() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 asin() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 atan() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 atan2(final Binary64 x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 cosh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 sinh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldSinhCosh<Binary64> sinhCosh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 tanh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 acosh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 asinh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 atanh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 toDegrees() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 toRadians() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 linearCombination(final Binary64[] a, final Binary64[] b) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 linearCombination(final double[] a, final Binary64[] b) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 linearCombination(final Binary64 a1, final Binary64 b1, final Binary64 a2, final Binary64 b2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 linearCombination(final double a1, final Binary64 b1, final double a2, final Binary64 b2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 linearCombination(final Binary64 a1, final Binary64 b1, final Binary64 a2, final Binary64 b2, final Binary64 a3, final Binary64 b3) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 linearCombination(final double a1, final Binary64 b1, final double a2, final Binary64 b2, final double a3, final Binary64 b3) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 linearCombination(final Binary64 a1, final Binary64 b1, final Binary64 a2, final Binary64 b2, final Binary64 a3, final Binary64 b3, final Binary64 a4, final Binary64 b4) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 linearCombination(final double a1, final Binary64 b1, final double a2, final Binary64 b2, final double a3, final Binary64 b3, final double a4, final Binary64 b4) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 getPi() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
