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

import java.math.BigInteger;
import org.hipparchus.exception.Localizable;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathRuntimeException;
import org.hipparchus.exception.MathIllegalArgumentException;

/**
 * Some useful, arithmetics related, additions to the built-in functions in
 * {@link Math}.
 */
public final class ArithmeticUtils {

    /**
     * Private constructor.
     */
    private ArithmeticUtils() {
        super();
    }

    /**
     * Add two integers, checking for overflow.
     *
     * @param x an addend
     * @param y an addend
     * @return the sum {@code x+y}
     * @throws MathRuntimeException if the result can not be represented
     * as an {@code int}.
     */
    public static int addAndCheck(int x, int y) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Add two long integers, checking for overflow.
     *
     * @param a an addend
     * @param b an addend
     * @return the sum {@code a+b}
     * @throws MathRuntimeException if the result can not be represented as an long
     */
    public static long addAndCheck(long a, long b) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Computes the greatest common divisor of the absolute value of two
     * numbers, using a modified version of the "binary gcd" method.
     * See Knuth 4.5.2 algorithm B.
     * The algorithm is due to Josef Stein (1961).
     * <br>
     * Special cases:
     * <ul>
     *  <li>The invocations
     *   {@code gcd(Integer.MIN_VALUE, Integer.MIN_VALUE)},
     *   {@code gcd(Integer.MIN_VALUE, 0)} and
     *   {@code gcd(0, Integer.MIN_VALUE)} throw an
     *   {@code ArithmeticException}, because the result would be 2^31, which
     *   is too large for an int value.</li>
     *  <li>The result of {@code gcd(x, x)}, {@code gcd(0, x)} and
     *   {@code gcd(x, 0)} is the absolute value of {@code x}, except
     *   for the special cases above.</li>
     *  <li>The invocation {@code gcd(0, 0)} is the only one which returns
     *   {@code 0}.</li>
     * </ul>
     *
     * @param p Number.
     * @param q Number.
     * @return the greatest common divisor (never negative).
     * @throws MathRuntimeException if the result cannot be represented as
     * a non-negative {@code int} value.
     */
    public static int gcd(int p, int q) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Computes the greatest common divisor of two <em>positive</em> numbers
     * (this precondition is <em>not</em> checked and the result is undefined
     * if not fulfilled) using the "binary gcd" method which avoids division
     * and modulo operations.
     * See Knuth 4.5.2 algorithm B.
     * The algorithm is due to Josef Stein (1961).
     * <p>
     * Special cases:
     * <ul>
     *  <li>The result of {@code gcd(x, x)}, {@code gcd(0, x)} and
     *   {@code gcd(x, 0)} is the value of {@code x}.</li>
     *  <li>The invocation {@code gcd(0, 0)} is the only one which returns
     *   {@code 0}.</li>
     * </ul>
     *
     * @param a Positive number.
     * @param b Positive number.
     * @return the greatest common divisor.
     */
    private static int gcdPositive(int a, int b) {
        if (a == 0) {
            return b;
        } else if (b == 0) {
            return a;
        }
        // Make "a" and "b" odd, keeping track of common power of 2.
        final int aTwos = Integer.numberOfTrailingZeros(a);
        a >>= aTwos;
        final int bTwos = Integer.numberOfTrailingZeros(b);
        b >>= bTwos;
        final int shift = FastMath.min(aTwos, bTwos);
        // "a" and "b" are positive.
        // If a > b then "gdc(a, b)" is equal to "gcd(a - b, b)".
        // If a < b then "gcd(a, b)" is equal to "gcd(b - a, a)".
        // Hence, in the successive iterations:
        //  "a" becomes the absolute difference of the current values,
        //  "b" becomes the minimum of the current values.
        while (a != b) {
            final int delta = a - b;
            b = Math.min(a, b);
            a = Math.abs(delta);
            // Remove any power of 2 in "a" ("b" is guaranteed to be odd).
            a >>= Integer.numberOfTrailingZeros(a);
        }
        // Recover the common power of 2.
        return a << shift;
    }

    /**
     * Gets the greatest common divisor of the absolute value of two numbers,
     * using the "binary gcd" method which avoids division and modulo
     * operations. See Knuth 4.5.2 algorithm B. This algorithm is due to Josef
     * Stein (1961).
     * <p>
     * Special cases:
     * <ul>
     * <li>The invocations
     * {@code gcd(Long.MIN_VALUE, Long.MIN_VALUE)},
     * {@code gcd(Long.MIN_VALUE, 0L)} and
     * {@code gcd(0L, Long.MIN_VALUE)} throw an
     * {@code ArithmeticException}, because the result would be 2^63, which
     * is too large for a long value.</li>
     * <li>The result of {@code gcd(x, x)}, {@code gcd(0L, x)} and
     * {@code gcd(x, 0L)} is the absolute value of {@code x}, except
     * for the special cases above.
     * <li>The invocation {@code gcd(0L, 0L)} is the only one which returns
     * {@code 0L}.</li>
     * </ul>
     *
     * @param p Number.
     * @param q Number.
     * @return the greatest common divisor, never negative.
     * @throws MathRuntimeException if the result cannot be represented as
     * a non-negative {@code long} value.
     */
    public static long gcd(final long p, final long q) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the least common multiple of the absolute value of two numbers,
     * using the formula {@code lcm(a,b) = (a / gcd(a,b)) * b}.
     * <p>
     * Special cases:
     * <ul>
     * <li>The invocations {@code lcm(Integer.MIN_VALUE, n)} and
     * {@code lcm(n, Integer.MIN_VALUE)}, where {@code abs(n)} is a
     * power of 2, throw an {@code ArithmeticException}, because the result
     * would be 2^31, which is too large for an int value.</li>
     * <li>The result of {@code lcm(0, x)} and {@code lcm(x, 0)} is
     * {@code 0} for any {@code x}.
     * </ul>
     *
     * @param a Number.
     * @param b Number.
     * @return the least common multiple, never negative.
     * @throws MathRuntimeException if the result cannot be represented as
     * a non-negative {@code int} value.
     */
    public static int lcm(int a, int b) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the least common multiple of the absolute value of two numbers,
     * using the formula {@code lcm(a,b) = (a / gcd(a,b)) * b}.
     * <p>
     * Special cases:
     * <ul>
     * <li>The invocations {@code lcm(Long.MIN_VALUE, n)} and
     * {@code lcm(n, Long.MIN_VALUE)}, where {@code abs(n)} is a
     * power of 2, throw an {@code ArithmeticException}, because the result
     * would be 2^63, which is too large for an int value.</li>
     * <li>The result of {@code lcm(0L, x)} and {@code lcm(x, 0L)} is
     * {@code 0L} for any {@code x}.
     * </ul>
     *
     * @param a Number.
     * @param b Number.
     * @return the least common multiple, never negative.
     * @throws MathRuntimeException if the result cannot be represented
     * as a non-negative {@code long} value.
     */
    public static long lcm(long a, long b) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Multiply two integers, checking for overflow.
     *
     * @param x Factor.
     * @param y Factor.
     * @return the product {@code x * y}.
     * @throws MathRuntimeException if the result can not be
     * represented as an {@code int}.
     */
    public static int mulAndCheck(int x, int y) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Multiply two long integers, checking for overflow.
     *
     * @param a Factor.
     * @param b Factor.
     * @return the product {@code a * b}.
     * @throws MathRuntimeException if the result can not be represented
     * as a {@code long}.
     */
    public static long mulAndCheck(long a, long b) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Subtract two integers, checking for overflow.
     *
     * @param x Minuend.
     * @param y Subtrahend.
     * @return the difference {@code x - y}.
     * @throws MathRuntimeException if the result can not be represented
     * as an {@code int}.
     */
    public static int subAndCheck(int x, int y) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Subtract two long integers, checking for overflow.
     *
     * @param a Value.
     * @param b Value.
     * @return the difference {@code a - b}.
     * @throws MathRuntimeException if the result can not be represented as a
     * {@code long}.
     */
    public static long subAndCheck(long a, long b) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Raise an int to an int power.
     *
     * @param k Number to raise.
     * @param e Exponent (must be positive or zero).
     * @return \( k^e \)
     * @throws MathIllegalArgumentException if {@code e < 0}.
     * @throws MathRuntimeException if the result would overflow.
     */
    public static int pow(final int k, final int e) throws MathIllegalArgumentException, MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Raise a long to an int power.
     *
     * @param k Number to raise.
     * @param e Exponent (must be positive or zero).
     * @return \( k^e \)
     * @throws MathIllegalArgumentException if {@code e < 0}.
     * @throws MathRuntimeException if the result would overflow.
     */
    public static long pow(final long k, final int e) throws MathIllegalArgumentException, MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Raise a BigInteger to an int power.
     *
     * @param k Number to raise.
     * @param e Exponent (must be positive or zero).
     * @return k<sup>e</sup>
     * @throws MathIllegalArgumentException if {@code e < 0}.
     */
    public static BigInteger pow(final BigInteger k, int e) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Raise a BigInteger to a long power.
     *
     * @param k Number to raise.
     * @param e Exponent (must be positive or zero).
     * @return k<sup>e</sup>
     * @throws MathIllegalArgumentException if {@code e < 0}.
     */
    public static BigInteger pow(final BigInteger k, long e) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Raise a BigInteger to a BigInteger power.
     *
     * @param k Number to raise.
     * @param e Exponent (must be positive or zero).
     * @return k<sup>e</sup>
     * @throws MathIllegalArgumentException if {@code e < 0}.
     */
    public static BigInteger pow(final BigInteger k, BigInteger e) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Add two long integers, checking for overflow.
     *
     * @param a Addend.
     * @param b Addend.
     * @param pattern Pattern to use for any thrown exception.
     * @return the sum {@code a + b}.
     * @throws MathRuntimeException if the result cannot be represented
     * as a {@code long}.
     */
    private static long addAndCheck(long a, long b, Localizable pattern) throws MathRuntimeException {
        final long result = a + b;
        if (!((a ^ b) < 0 || (a ^ result) >= 0)) {
            throw new MathRuntimeException(pattern, a, b);
        }
        return result;
    }

    /**
     * Returns true if the argument is a power of two.
     *
     * @param n the number to test
     * @return true if the argument is a power of two
     */
    public static boolean isPowerOfTwo(long n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the unsigned remainder from dividing the first argument
     * by the second where each argument and the result is interpreted
     * as an unsigned value.
     * <p>
     * This method does not use the {@code long} datatype.
     *
     * @param dividend the value to be divided
     * @param divisor the value doing the dividing
     * @return the unsigned remainder of the first argument divided by
     * the second argument.
     */
    public static int remainderUnsigned(int dividend, int divisor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the unsigned remainder from dividing the first argument
     * by the second where each argument and the result is interpreted
     * as an unsigned value.
     * <p>
     * This method does not use the {@code BigInteger} datatype.
     *
     * @param dividend the value to be divided
     * @param divisor the value doing the dividing
     * @return the unsigned remainder of the first argument divided by
     * the second argument.
     */
    public static long remainderUnsigned(long dividend, long divisor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the unsigned quotient of dividing the first argument by
     * the second where each argument and the result is interpreted as
     * an unsigned value.
     * <p>
     * Note that in two's complement arithmetic, the three other
     * basic arithmetic operations of add, subtract, and multiply are
     * bit-wise identical if the two operands are regarded as both
     * being signed or both being unsigned. Therefore separate {@code
     * addUnsigned}, etc. methods are not provided.
     * <p>
     * This method does not use the {@code long} datatype.
     *
     * @param dividend the value to be divided
     * @param divisor the value doing the dividing
     * @return the unsigned quotient of the first argument divided by
     * the second argument
     */
    public static int divideUnsigned(int dividend, int divisor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the unsigned quotient of dividing the first argument by
     * the second where each argument and the result is interpreted as
     * an unsigned value.
     * <p>
     * Note that in two's complement arithmetic, the three other
     * basic arithmetic operations of add, subtract, and multiply are
     * bit-wise identical if the two operands are regarded as both
     * being signed or both being unsigned. Therefore separate {@code
     * addUnsigned}, etc. methods are not provided.
     * <p>
     * This method does not use the {@code BigInteger} datatype.
     *
     * @param dividend the value to be divided
     * @param divisor the value doing the dividing
     * @return the unsigned quotient of the first argument divided by
     * the second argument.
     */
    public static long divideUnsigned(long dividend, long divisor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
