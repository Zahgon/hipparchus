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
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathRuntimeException;

/**
 * Faster, more accurate, portable alternative to {@link Math} and
 * {@link StrictMath} for large scale computation.
 * <p>
 * FastMath is a drop-in replacement for both Math and StrictMath. This
 * means that for any method in Math (say {@code Math.sin(x)} or
 * {@code Math.cbrt(y)}), user can directly change the class and use the
 * methods as is (using {@code FastMath.sin(x)} or {@code FastMath.cbrt(y)}
 * in the previous example).
 * <p>
 * FastMath speed is achieved by relying heavily on optimizing compilers
 * to native code present in many JVMs today and use of large tables.
 * The larger tables are lazily initialized on first use, so that the setup
 * time does not penalize methods that don't need them.
 *
 * <p>
 * Note that FastMath is
 * extensively used inside Hipparchus, so by calling some algorithms,
 * the overhead when the the tables need to be initialized will occur
 * regardless of the end-user calling FastMath methods directly or not.
 * Performance figures for a specific JVM and hardware can be evaluated by
 * running the FastMathTestPerformance tests in the test directory of the source
 * distribution.
 * <p>
 * FastMath accuracy should be mostly independent of the JVM as it relies only
 * on IEEE-754 basic operations and on embedded tables. Almost all operations
 * are accurate to about 0.5 ulp throughout the domain range. This statement,
 * of course is only a rough global observed behavior, it is <em>not</em> a
 * guarantee for <em>every</em> double numbers input (see William Kahan's <a
 * href="http://en.wikipedia.org/wiki/Rounding#The_table-maker.27s_dilemma">Table
 * Maker's Dilemma</a>).
 * <p>
 * FastMath additionally implements the following methods not found in Math/StrictMath:
 * <ul>
 * <li>{@link #asinh(double)}</li>
 * <li>{@link #acosh(double)}</li>
 * <li>{@link #atanh(double)}</li>
 * </ul>
 * The following methods are found in Math/StrictMath since 1.6 only, they are provided
 * by FastMath even in 1.5 Java virtual machines
 * <ul>
 * <li>{@link #copySign(double, double)}</li>
 * <li>{@link #getExponent(double)}</li>
 * <li>{@link #nextAfter(double,double)}</li>
 * <li>{@link #nextUp(double)}</li>
 * <li>{@link #scalb(double, int)}</li>
 * <li>{@link #copySign(float, float)}</li>
 * <li>{@link #getExponent(float)}</li>
 * <li>{@link #nextAfter(float,double)}</li>
 * <li>{@link #nextUp(float)}</li>
 * <li>{@link #scalb(float, int)}</li>
 * </ul>
 */
public class FastMath {

    /**
     * Archimede's constant PI, ratio of circle circumference to diameter.
     */
    public static final double PI = 105414357.0 / 33554432.0 + 1.984187159361080883e-9;

    /**
     * Napier's constant e, base of the natural logarithm.
     */
    public static final double E = 2850325.0 / 1048576.0 + 8.254840070411028747e-8;

    /**
     * Index of exp(0) in the array of integer exponentials.
     */
    static final int EXP_INT_TABLE_MAX_INDEX = 750;

    /**
     * Length of the array of integer exponentials.
     */
    static final int EXP_INT_TABLE_LEN = EXP_INT_TABLE_MAX_INDEX * 2;

    /**
     * Logarithm table length.
     */
    static final int LN_MANT_LEN = 1024;

    /**
     * Exponential fractions table length.
     */
    // 0, 1/1024, ... 1024/1024
    static final int EXP_FRAC_TABLE_LEN = 1025;

    /**
     * StrictMath.log(Double.MAX_VALUE): {@value}
     */
    private static final double LOG_MAX_VALUE = StrictMath.log(Double.MAX_VALUE);

    /**
     * Indicator for tables initialization.
     * <p>
     * This compile-time constant should be set to true only if one explicitly
     * wants to compute the tables at class loading time instead of using the
     * already computed ones provided as literal arrays below.
     * </p>
     */
    private static final boolean RECOMPUTE_TABLES_AT_RUNTIME = false;

    /**
     * log(2) (high bits).
     */
    private static final double LN_2_A = 0.693147063255310059;

    /**
     * log(2) (low bits).
     */
    private static final double LN_2_B = 1.17304635250823482e-7;

    /**
     * Coefficients for log, when input 0.99 < x < 1.01.
     */
    private static final double[][] LN_QUICK_COEF = { { 1.0, 5.669184079525E-24 }, { -0.25, -0.25 }, { 0.3333333134651184, 1.986821492305628E-8 }, { -0.25, -6.663542893624021E-14 }, { 0.19999998807907104, 1.1921056801463227E-8 }, { -0.1666666567325592, -7.800414592973399E-9 }, { 0.1428571343421936, 5.650007086920087E-9 }, { -0.12502530217170715, -7.44321345601866E-11 }, { 0.11113807559013367, 9.219544613762692E-9 } };

    /**
     * Coefficients for log in the range of 1.0 < x < 1.0 + 2^-10.
     */
    private static final double[][] LN_HI_PREC_COEF = { { 1.0, -6.032174644509064E-23 }, { -0.25, -0.25 }, { 0.3333333134651184, 1.9868161777724352E-8 }, { -0.2499999701976776, -2.957007209750105E-8 }, { 0.19999954104423523, 1.5830993332061267E-10 }, { -0.16624879837036133, -2.6033824355191673E-8 } };

    /**
     * Sine, Cosine, Tangent tables are for 0, 1/8, 2/8, ... 13/8 = PI/2 approx.
     */
    /**
     * Sine table (high bits).
     */
    private static final double[] SINE_TABLE_A = { +0.0d, +0.1246747374534607d, +0.24740394949913025d, +0.366272509098053d, +0.4794255495071411d, +0.5850973129272461d, +0.6816387176513672d, +0.7675435543060303d, +0.8414709568023682d, +0.902267575263977d, +0.9489846229553223d, +0.9808930158615112d, +0.9974949359893799d, +0.9985313415527344d };

    /**
     * Sine table (low bits).
     */
    private static final double[] SINE_TABLE_B = { +0.0d, -4.068233003401932E-9d, +9.755392680573412E-9d, +1.9987994582857286E-8d, -1.0902938113007961E-8d, -3.9986783938944604E-8d, +4.23719669792332E-8d, -5.207000323380292E-8d, +2.800552834259E-8d, +1.883511811213715E-8d, -3.5997360512765566E-9d, +4.116164446561962E-8d, +5.0614674548127384E-8d, -1.0129027912496858E-9d };

    /**
     * Cosine table (high bits).
     */
    private static final double[] COSINE_TABLE_A = { +1.0d, +0.9921976327896118d, +0.9689123630523682d, +0.9305076599121094d, +0.8775825500488281d, +0.8109631538391113d, +0.7316888570785522d, +0.6409968137741089d, +0.5403022766113281d, +0.4311765432357788d, +0.3153223395347595d, +0.19454771280288696d, +0.07073719799518585d, -0.05417713522911072d };

    /**
     * Cosine table (low bits).
     */
    private static final double[] COSINE_TABLE_B = { +0.0d, +3.4439717236742845E-8d, +5.865827662008209E-8d, -3.7999795083850525E-8d, +1.184154459111628E-8d, -3.43338934259355E-8d, +1.1795268640216787E-8d, +4.438921624363781E-8d, +2.925681159240093E-8d, -2.6437112632041807E-8d, +2.2860509143963117E-8d, -4.813899778443457E-9d, +3.6725170580355583E-9d, +2.0217439756338078E-10d };

    /**
     * Tangent table, used by atan() (high bits).
     */
    private static final double[] TANGENT_TABLE_A = { +0.0d, +0.1256551444530487d, +0.25534194707870483d, +0.3936265707015991d, +0.5463024377822876d, +0.7214844226837158d, +0.9315965175628662d, +1.1974215507507324d, +1.5574076175689697d, +2.092571258544922d, +3.0095696449279785d, +5.041914939880371d, +14.101419448852539d, -18.430862426757812d };

    /**
     * Tangent table, used by atan() (low bits).
     */
    private static final double[] TANGENT_TABLE_B = { +0.0d, -7.877917738262007E-9d, -2.5857668567479893E-8d, +5.2240336371356666E-9d, +5.206150291559893E-8d, +1.8307188599677033E-8d, -5.7618793749770706E-8d, +7.848361555046424E-8d, +1.0708593250394448E-7d, +1.7827257129423813E-8d, +2.893485277253286E-8d, +3.1660099222737955E-7d, +4.983191803254889E-7d, -3.356118100840571E-7d };

    /**
     * Bits of 1/(2*pi), need for reducePayneHanek().
     */
    private static final long[] RECIP_2PI = { (0x28be60dbL << 32) | 0x9391054aL, (0x7f09d5f4L << 32) | 0x7d4d3770L, (0x36d8a566L << 32) | 0x4f10e410L, (0x7f9458eaL << 32) | 0xf7aef158L, (0x6dc91b8eL << 32) | 0x909374b8L, (0x01924bbaL << 32) | 0x82746487L, (0x3f877ac7L << 32) | 0x2c4a69cfL, (0xba208d7dL << 32) | 0x4baed121L, (0x3a671c09L << 32) | 0xad17df90L, (0x4e64758eL << 32) | 0x60d4ce7dL, (0x272117e2L << 32) | 0xef7e4a0eL, (0xc7fe25ffL << 32) | 0xf7816603L, (0xfbcbc462L << 32) | 0xd6829b47L, (0xdb4d9fb3L << 32) | 0xc9f2c26dL, (0xd3d18fd9L << 32) | 0xa797fa8bL, (0x5d49eeb1L << 32) | 0xfaf97c5eL, (0xcf41ce7dL << 32) | 0xe294a4baL, 0x9afed7ecL << 32 };

    /**
     * Bits of pi/4, need for reducePayneHanek().
     */
    private static final long[] PI_O_4_BITS = { (0xc90fdaa2L << 32) | 0x2168c234L, (0xc4c6628bL << 32) | 0x80dc1cd1L };

    /**
     * Eighths.
     * This is used by sinQ, because its faster to do a table lookup than
     * a multiply in this time-critical routine
     */
    private static final double[] EIGHTHS = { 0, 0.125, 0.25, 0.375, 0.5, 0.625, 0.75, 0.875, 1.0, 1.125, 1.25, 1.375, 1.5, 1.625 };

    /**
     * Table of 2^((n+2)/3)
     */
    private static final double[] CBRTTWO = { 0.6299605249474366, 0.7937005259840998, 1.0, 1.2599210498948732, 1.5874010519681994 };

    /*
     *  There are 52 bits in the mantissa of a double.
     *  For additional precision, the code splits double numbers into two parts,
     *  by clearing the low order 30 bits if possible, and then performs the arithmetic
     *  on each half separately.
     */
    /**
     * 0x40000000 - used to split a double into two parts, both with the low order bits cleared.
     * Equivalent to 2^30.
     */
    // 1073741824L
    private static final long HEX_40000000 = 0x40000000L;

    /**
     * Mask used to clear low order 30 bits
     */
    // 0xFFFFFFFFC0000000L;
    private static final long MASK_30BITS = -1L - (HEX_40000000 - 1);

    /**
     * Mask used to clear the non-sign part of an int.
     */
    private static final int MASK_NON_SIGN_INT = 0x7fffffff;

    /**
     * Mask used to clear the non-sign part of a long.
     */
    private static final long MASK_NON_SIGN_LONG = 0x7fffffffffffffffL;

    /**
     * Mask used to extract exponent from double bits.
     */
    private static final long MASK_DOUBLE_EXPONENT = 0x7ff0000000000000L;

    /**
     * Mask used to extract mantissa from double bits.
     */
    private static final long MASK_DOUBLE_MANTISSA = 0x000fffffffffffffL;

    /**
     * Mask used to add implicit high order bit for normalized double.
     */
    private static final long IMPLICIT_HIGH_BIT = 0x0010000000000000L;

    /**
     * 2^52 - double numbers this large must be integral (no fraction) or NaN or Infinite
     */
    private static final double TWO_POWER_52 = 4503599627370496.0;

    /**
     * Constant: {@value}.
     */
    private static final double F_1_3 = 1d / 3d;

    /**
     * Constant: {@value}.
     */
    private static final double F_1_5 = 1d / 5d;

    /**
     * Constant: {@value}.
     */
    private static final double F_1_7 = 1d / 7d;

    /**
     * Constant: {@value}.
     */
    private static final double F_1_9 = 1d / 9d;

    /**
     * Constant: {@value}.
     */
    private static final double F_1_11 = 1d / 11d;

    /**
     * Constant: {@value}.
     */
    private static final double F_1_13 = 1d / 13d;

    /**
     * Constant: {@value}.
     */
    private static final double F_1_15 = 1d / 15d;

    /**
     * Constant: {@value}.
     */
    private static final double F_1_17 = 1d / 17d;

    /**
     * Constant: {@value}.
     */
    private static final double F_3_4 = 3d / 4d;

    /**
     * Constant: {@value}.
     */
    private static final double F_15_16 = 15d / 16d;

    /**
     * Constant: {@value}.
     */
    private static final double F_13_14 = 13d / 14d;

    /**
     * Constant: {@value}.
     */
    private static final double F_11_12 = 11d / 12d;

    /**
     * Constant: {@value}.
     */
    private static final double F_9_10 = 9d / 10d;

    /**
     * Constant: {@value}.
     */
    private static final double F_7_8 = 7d / 8d;

    /**
     * Constant: {@value}.
     */
    private static final double F_5_6 = 5d / 6d;

    /**
     * Constant: {@value}.
     */
    private static final double F_1_2 = 1d / 2d;

    /**
     * Constant: {@value}.
     */
    private static final double F_1_4 = 1d / 4d;

    /**
     * Private Constructor
     */
    private FastMath() {
    }

    // Generic helper methods
    /**
     * Get the high order bits from the mantissa.
     * Equivalent to adding and subtracting HEX_40000 but also works for very large numbers
     *
     * @param d the value to split
     * @return the high order part of the mantissa
     */
    private static double doubleHighPart(double d) {
        if (d > -Precision.SAFE_MIN && d < Precision.SAFE_MIN) {
            // These are un-normalised - don't try to convert
            return d;
        }
        // can take raw bits because just gonna convert it back
        long xl = Double.doubleToRawLongBits(d);
        // Drop low order bits
        xl &= MASK_30BITS;
        return Double.longBitsToDouble(xl);
    }

    /**
     * Compute the square root of a number.
     * <p><b>Note:</b> this implementation currently delegates to {@link Math#sqrt}
     * @param a number on which evaluation is done
     * @return square root of a
     */
    public static double sqrt(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the hyperbolic cosine of a number.
     * @param x number on which evaluation is done
     * @return hyperbolic cosine of x
     */
    public static double cosh(double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the hyperbolic sine of a number.
     * @param x number on which evaluation is done
     * @return hyperbolic sine of x
     */
    public static double sinh(double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Combined hyperbolic sine and hyperbolic cosine function.
     *
     * @param x Argument.
     * @return [sinh(x), cosh(x)]
     */
    public static SinhCosh sinhCosh(double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Combined hyperbolic sine and hyperbolic cosine function.
     *
     * @param x Argument.
     * @param <T> the type of the field element
     * @return [sinh(x), cosh(x)]
     */
    public static <T extends CalculusFieldElement<T>> FieldSinhCosh<T> sinhCosh(T x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the hyperbolic tangent of a number.
     * @param x number on which evaluation is done
     * @return hyperbolic tangent of x
     */
    public static double tanh(double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the inverse hyperbolic cosine of a number.
     * @param a number on which evaluation is done
     * @return inverse hyperbolic cosine of a
     */
    public static double acosh(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the inverse hyperbolic sine of a number.
     * @param a number on which evaluation is done
     * @return inverse hyperbolic sine of a
     */
    public static double asinh(double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the inverse hyperbolic tangent of a number.
     * @param a number on which evaluation is done
     * @return inverse hyperbolic tangent of a
     */
    public static double atanh(double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the signum of a number.
     * The signum is -1 for negative numbers, +1 for positive numbers and 0 otherwise
     * @param a number on which evaluation is done
     * @return -1.0, -0.0, +0.0, +1.0 or NaN depending on sign of a
     */
    public static double signum(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the signum of a number.
     * The signum is -1 for negative numbers, +1 for positive numbers and 0 otherwise
     * @param a number on which evaluation is done
     * @return -1.0, -0.0, +0.0, +1.0 or NaN depending on sign of a
     */
    public static float signum(final float a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute next number towards positive infinity.
     * @param a number to which neighbor should be computed
     * @return neighbor of a towards positive infinity
     */
    public static double nextUp(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute next number towards positive infinity.
     * @param a number to which neighbor should be computed
     * @return neighbor of a towards positive infinity
     */
    public static float nextUp(final float a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute next number towards negative infinity.
     * @param a number to which neighbor should be computed
     * @return neighbor of a towards negative infinity
     */
    public static double nextDown(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute next number towards negative infinity.
     * @param a number to which neighbor should be computed
     * @return neighbor of a towards negative infinity
     */
    public static float nextDown(final float a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Clamp a value within an interval.
     * @param value value to clamp
     * @param inf lower bound of the clamping interval
     * @param sup upper bound of the clamping interval
     * @return value clamped within [inf; sup], or value if already within bounds.
     * @since 3.0
     */
    public static int clamp(final int value, final int inf, final int sup) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Clamp a value within an interval.
     * @param value value to clamp
     * @param inf lower bound of the clamping interval
     * @param sup upper bound of the clamping interval
     * @return value clamped within [inf; sup], or value if already within bounds.
     * @since 3.0
     */
    public static long clamp(final long value, final long inf, final long sup) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Clamp a value within an interval.
     * @param value value to clamp
     * @param inf lower bound of the clamping interval
     * @param sup upper bound of the clamping interval
     * @return value clamped within [inf; sup], or value if already within bounds.
     * @since 3.0
     */
    public static int clamp(final long value, final int inf, final int sup) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Clamp a value within an interval.
     * <p>
     * This method assumes -0.0 is below +0.0
     * </p>
     * @param value value to clamp
     * @param inf lower bound of the clamping interval
     * @param sup upper bound of the clamping interval
     * @return value clamped within [inf; sup], or value if already within bounds.
     * @since 3.0
     */
    public static float clamp(final float value, final float inf, final float sup) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Clamp a value within an interval.
     * <p>
     * This method assumes -0.0 is below +0.0
     * </p>
     * @param value value to clamp
     * @param inf lower bound of the clamping interval
     * @param sup upper bound of the clamping interval
     * @return value clamped within [inf; sup], or value if already within bounds.
     * @since 3.0
     */
    public static double clamp(final double value, final double inf, final double sup) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns a pseudo-random number between 0.0 and 1.0.
     * <p><b>Note:</b> this implementation currently delegates to {@link Math#random}
     * @return a random number between 0.0 and 1.0
     */
    public static double random() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Exponential function.
     *
     * Computes exp(x), function result is nearly rounded.   It will be correctly
     * rounded to the theoretical value for 99.9% of input values, otherwise it will
     * have a 1 ULP error.
     *
     * Method:
     *    Lookup intVal = exp(int(x))
     *    Lookup fracVal = exp(int(x-int(x) / 1024.0) * 1024.0 );
     *    Compute z as the exponential of the remaining bits by a polynomial minus one
     *    exp(x) = intVal * fracVal * (1 + z)
     *
     * Accuracy:
     *    Calculation is done with 63 bits of precision, so result should be correctly
     *    rounded for 99.9% of input values, with less than 1 ULP error otherwise.
     *
     * @param x   a double
     * @return double e<sup>x</sup>
     */
    public static double exp(double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Internal helper method for computing a high-precision exponential in
     *
     * <ul>
     * <li>{@link #sinh(double)},</li>
     * <li>{@link #cosh(double)},</li>
     * <li>{@link #tanh(double)}, and</li>
     * <li>{@link #sinhCosh(double)}</li>
     * </ul>
     *
     * @param x original argument of the exponential function
     * @param extra extra bits of precision on input (To Be Confirmed)
     * @param hiPrec extra bits of precision on output (To Be Confirmed)
     * @return exp(x)
     */
    private static double exp(double x, double extra, double[] hiPrec) {
        double intPartA;
        double intPartB;
        int intVal = (int) x;
        /* Lookup exp(floor(x)).
         * intPartA will have the upper 22 bits, intPartB will have the lower
         * 52 bits.
         */
        if (x < 0.0) {
            // We don't check against intVal here as conversion of large negative double values
            // may be affected by a JIT bug. Subsequent comparisons can safely use intVal
            if (x < -746d) {
                if (hiPrec != null) {
                    hiPrec[0] = 0.0;
                    hiPrec[1] = 0.0;
                }
                return 0.0;
            }
            if (intVal < -709) {
                /* This will produce a subnormal output */
                final double result = exp(x + 40.19140625, extra, hiPrec) / 285040095144011776.0;
                if (hiPrec != null) {
                    hiPrec[0] /= 285040095144011776.0;
                    hiPrec[1] /= 285040095144011776.0;
                }
                return result;
            }
            if (intVal == -709) {
                /* exp(1.494140625) is nearly a machine number... */
                final double result = exp(x + 1.494140625, extra, hiPrec) / 4.455505956692756620;
                if (hiPrec != null) {
                    hiPrec[0] /= 4.455505956692756620;
                    hiPrec[1] /= 4.455505956692756620;
                }
                return result;
            }
            intVal--;
        } else {
            if (intVal > 709) {
                if (hiPrec != null) {
                    hiPrec[0] = Double.POSITIVE_INFINITY;
                    hiPrec[1] = 0.0;
                }
                return Double.POSITIVE_INFINITY;
            }
        }
        intPartA = ExpIntTable.EXP_INT_TABLE_A[EXP_INT_TABLE_MAX_INDEX + intVal];
        intPartB = ExpIntTable.EXP_INT_TABLE_B[EXP_INT_TABLE_MAX_INDEX + intVal];
        /* Get the fractional part of x, find the greatest multiple of 2^-10 less than
         * x and look up the exp function of it.
         * fracPartA will have the upper 22 bits, fracPartB the lower 52 bits.
         */
        final int intFrac = (int) ((x - intVal) * 1024.0);
        final double fracPartA = ExpFracTable.EXP_FRAC_TABLE_A[intFrac];
        final double fracPartB = ExpFracTable.EXP_FRAC_TABLE_B[intFrac];
        /* epsilon is the difference in x from the nearest multiple of 2^-10.  It
         * has a value in the range 0 <= epsilon < 2^-10.
         * Do the subtraction from x as the last step to avoid possible loss of precision.
         */
        final double epsilon = x - (intVal + intFrac / 1024.0);
        /* Compute z = exp(epsilon) - 1.0 via a minimax polynomial.  z has
       full double precision (52 bits).  Since z < 2^-10, we will have
       62 bits of precision when combined with the constant 1.  This will be
       used in the last addition below to get proper rounding. */
        /* Remez generated polynomial.  Converges on the interval [0, 2^-10], error
       is less than 0.5 ULP */
        double z = 0.04168701738764507;
        z = z * epsilon + 0.1666666505023083;
        z = z * epsilon + 0.5000000000042687;
        z = z * epsilon + 1.0;
        z = z * epsilon + -3.940510424527919E-20;
        /* Compute (intPartA+intPartB) * (fracPartA+fracPartB) by binomial
       expansion.
       tempA is exact since intPartA and intPartB only have 22 bits each.
       tempB will have 52 bits of precision.
         */
        double tempA = intPartA * fracPartA;
        double tempB = intPartA * fracPartB + intPartB * fracPartA + intPartB * fracPartB;
        /* Compute the result.  (1+z)(tempA+tempB).  Order of operations is
       important.  For accuracy add by increasing size.  tempA is exact and
       much larger than the others.  If there are extra bits specified from the
       pow() function, use them. */
        final double tempC = tempB + tempA;
        // If tempC is positive infinite, the evaluation below could result in NaN,
        // because z could be negative at the same time.
        if (tempC == Double.POSITIVE_INFINITY) {
            if (hiPrec != null) {
                hiPrec[0] = Double.POSITIVE_INFINITY;
                hiPrec[1] = 0.0;
            }
            return Double.POSITIVE_INFINITY;
        }
        final double result;
        if (extra != 0.0) {
            result = tempC * extra * z + tempC * extra + tempC * z + tempB + tempA;
        } else {
            result = tempC * z + tempB + tempA;
        }
        if (hiPrec != null) {
            // If requesting high precision
            hiPrec[0] = tempA;
            hiPrec[1] = tempC * extra * z + tempC * extra + tempC * z + tempB;
        }
        return result;
    }

    /**
     * Compute exp(x) - 1
     * @param x number to compute shifted exponential
     * @return exp(x) - 1
     */
    public static double expm1(double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Internal helper method for expm1
     * @param x number to compute shifted exponential
     * @param hiPrecOut receive high precision result for -1.0 < x < 1.0
     * @return exp(x) - 1
     */
    private static double expm1(double x, double[] hiPrecOut) {
        if (Double.isNaN(x) || x == 0.0) {
            // NaN or zero
            return x;
        }
        if (x <= -1.0 || x >= 1.0) {
            // If not between +/- 1.0
            //return exp(x) - 1.0;
            double[] hiPrec = new double[2];
            exp(x, 0.0, hiPrec);
            if (x > 0.0) {
                return -1.0 + hiPrec[0] + hiPrec[1];
            } else {
                final double ra = -1.0 + hiPrec[0];
                double rb = -(ra + 1.0 - hiPrec[0]);
                rb += hiPrec[1];
                return ra + rb;
            }
        }
        double baseA;
        double baseB;
        double epsilon;
        boolean negative = false;
        if (x < 0.0) {
            x = -x;
            negative = true;
        }
        {
            int intFrac = (int) (x * 1024.0);
            double tempA = ExpFracTable.EXP_FRAC_TABLE_A[intFrac] - 1.0;
            double tempB = ExpFracTable.EXP_FRAC_TABLE_B[intFrac];
            double temp = tempA + tempB;
            tempB = -(temp - tempA - tempB);
            tempA = temp;
            temp = tempA * HEX_40000000;
            baseA = tempA + temp - temp;
            baseB = tempB + (tempA - baseA);
            epsilon = x - intFrac / 1024.0;
        }
        /* Compute expm1(epsilon) */
        double zb = 0.008336750013465571;
        zb = zb * epsilon + 0.041666663879186654;
        zb = zb * epsilon + 0.16666666666745392;
        zb = zb * epsilon + 0.49999999999999994;
        zb *= epsilon;
        zb *= epsilon;
        double za = epsilon;
        double temp = za + zb;
        zb = -(temp - za - zb);
        za = temp;
        temp = za * HEX_40000000;
        temp = za + temp - temp;
        zb += za - temp;
        za = temp;
        /* Combine the parts.   expm1(a+b) = expm1(a) + expm1(b) + expm1(a)*expm1(b) */
        double ya = za * baseA;
        //double yb = za*baseB + zb*baseA + zb*baseB;
        temp = ya + za * baseB;
        double yb = -(temp - ya - za * baseB);
        ya = temp;
        temp = ya + zb * baseA;
        yb += -(temp - ya - zb * baseA);
        ya = temp;
        temp = ya + zb * baseB;
        yb += -(temp - ya - zb * baseB);
        ya = temp;
        //ya = ya + za + baseA;
        //yb = yb + zb + baseB;
        temp = ya + baseA;
        yb += -(temp - baseA - ya);
        ya = temp;
        temp = ya + za;
        //yb += (ya > za) ? -(temp - ya - za) : -(temp - za - ya);
        yb += -(temp - ya - za);
        ya = temp;
        temp = ya + baseB;
        //yb += (ya > baseB) ? -(temp - ya - baseB) : -(temp - baseB - ya);
        yb += -(temp - ya - baseB);
        ya = temp;
        temp = ya + zb;
        //yb += (ya > zb) ? -(temp - ya - zb) : -(temp - zb - ya);
        yb += -(temp - ya - zb);
        ya = temp;
        if (negative) {
            /* Compute expm1(-x) = -expm1(x) / (expm1(x) + 1) */
            double denom = 1.0 + ya;
            double denomr = 1.0 / denom;
            double denomb = -(denom - 1.0 - ya) + yb;
            double ratio = ya * denomr;
            temp = ratio * HEX_40000000;
            final double ra = ratio + temp - temp;
            double rb = ratio - ra;
            temp = denom * HEX_40000000;
            za = denom + temp - temp;
            zb = denom - za;
            rb += (ya - za * ra - za * rb - zb * ra - zb * rb) * denomr;
            // f(x) = x/1+x
            // Compute f'(x)
            // Product rule:  d(uv) = du*v + u*dv
            // Chain rule:  d(f(g(x)) = f'(g(x))*f(g'(x))
            // d(1/x) = -1/(x*x)
            // d(1/1+x) = -1/( (1+x)^2) *  1 =  -1/((1+x)*(1+x))
            // d(x/1+x) = -x/((1+x)(1+x)) + 1/1+x = 1 / ((1+x)(1+x))
            // Adjust for yb
            // numerator
            rb += yb * denomr;
            // denominator
            rb += -ya * denomb * denomr * denomr;
            // negate
            ya = -ra;
            yb = -rb;
        }
        if (hiPrecOut != null) {
            hiPrecOut[0] = ya;
            hiPrecOut[1] = yb;
        }
        return ya + yb;
    }

    /**
     * Natural logarithm.
     *
     * @param x   a double
     * @return log(x)
     */
    public static double log(final double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Internal helper method for natural logarithm function.
     * @param x original argument of the natural logarithm function
     * @param hiPrec extra bits of precision on output (To Be Confirmed)
     * @return log(x)
     */
    private static double log(final double x, final double[] hiPrec) {
        if (x == 0) {
            // Handle special case of +0/-0
            return Double.NEGATIVE_INFINITY;
        }
        long bits = Double.doubleToRawLongBits(x);
        /* Handle special cases of negative input, and NaN */
        if (((bits & 0x8000000000000000L) != 0 || Double.isNaN(x)) && x != 0.0) {
            if (hiPrec != null) {
                hiPrec[0] = Double.NaN;
            }
            return Double.NaN;
        }
        /* Handle special cases of Positive infinity. */
        if (x == Double.POSITIVE_INFINITY) {
            if (hiPrec != null) {
                hiPrec[0] = Double.POSITIVE_INFINITY;
            }
            return Double.POSITIVE_INFINITY;
        }
        /* Extract the exponent */
        int exp = (int) (bits >> 52) - 1023;
        if ((bits & 0x7ff0000000000000L) == 0) {
            // Subnormal!
            if (x == 0) {
                // Zero
                if (hiPrec != null) {
                    hiPrec[0] = Double.NEGATIVE_INFINITY;
                }
                return Double.NEGATIVE_INFINITY;
            }
            /* Normalize the subnormal number. */
            bits <<= 1;
            while ((bits & 0x0010000000000000L) == 0) {
                --exp;
                bits <<= 1;
            }
        }
        if ((exp == -1 || exp == 0) && x < 1.01 && x > 0.99 && hiPrec == null) {
            /* The normal method doesn't work well in the range [0.99, 1.01], so call do a straight
           polynomial expansion in higer precision. */
            /* Compute x - 1.0 and split it */
            double xa = x - 1.0;
            double tmp = xa * HEX_40000000;
            double aa = xa + tmp - tmp;
            double ab = xa - aa;
            xa = aa;
            double xb = ab;
            final double[] lnCoef_last = LN_QUICK_COEF[LN_QUICK_COEF.length - 1];
            double ya = lnCoef_last[0];
            double yb = lnCoef_last[1];
            for (int i = LN_QUICK_COEF.length - 2; i >= 0; i--) {
                /* Multiply a = y * x */
                aa = ya * xa;
                ab = ya * xb + yb * xa + yb * xb;
                /* split, so now y = a */
                tmp = aa * HEX_40000000;
                ya = aa + tmp - tmp;
                yb = aa - ya + ab;
                /* Add  a = y + lnQuickCoef */
                final double[] lnCoef_i = LN_QUICK_COEF[i];
                aa = ya + lnCoef_i[0];
                ab = yb + lnCoef_i[1];
                /* Split y = a */
                tmp = aa * HEX_40000000;
                ya = aa + tmp - tmp;
                yb = aa - ya + ab;
            }
            /* Multiply a = y * x */
            aa = ya * xa;
            ab = ya * xb + yb * xa + yb * xb;
            /* split, so now y = a */
            tmp = aa * HEX_40000000;
            ya = aa + tmp - tmp;
            yb = aa - ya + ab;
            return ya + yb;
        }
        // lnm is a log of a number in the range of 1.0 - 2.0, so 0 <= lnm < ln(2)
        final double[] lnm = lnMant.LN_MANT[(int) ((bits & 0x000ffc0000000000L) >> 42)];
        /*
    double epsilon = x / Double.longBitsToDouble(bits & 0xfffffc0000000000L);

    epsilon -= 1.0;
         */
        // y is the most significant 10 bits of the mantissa
        //double y = Double.longBitsToDouble(bits & 0xfffffc0000000000L);
        //double epsilon = (x - y) / y;
        final double epsilon = (bits & 0x3ffffffffffL) / (TWO_POWER_52 + (bits & 0x000ffc0000000000L));
        double lnza;
        double lnzb = 0.0;
        if (hiPrec != null) {
            /* split epsilon -> x */
            double tmp = epsilon * HEX_40000000;
            double aa = epsilon + tmp - tmp;
            double ab = epsilon - aa;
            double xa = aa;
            double xb = ab;
            /* Need a more accurate epsilon, so adjust the division. */
            final double numer = bits & 0x3ffffffffffL;
            final double denom = TWO_POWER_52 + (bits & 0x000ffc0000000000L);
            aa = numer - xa * denom - xb * denom;
            xb += aa / denom;
            /* Remez polynomial evaluation */
            final double[] lnCoef_last = LN_HI_PREC_COEF[LN_HI_PREC_COEF.length - 1];
            double ya = lnCoef_last[0];
            double yb = lnCoef_last[1];
            for (int i = LN_HI_PREC_COEF.length - 2; i >= 0; i--) {
                /* Multiply a = y * x */
                aa = ya * xa;
                ab = ya * xb + yb * xa + yb * xb;
                /* split, so now y = a */
                tmp = aa * HEX_40000000;
                ya = aa + tmp - tmp;
                yb = aa - ya + ab;
                /* Add  a = y + lnHiPrecCoef */
                final double[] lnCoef_i = LN_HI_PREC_COEF[i];
                aa = ya + lnCoef_i[0];
                ab = yb + lnCoef_i[1];
                /* Split y = a */
                tmp = aa * HEX_40000000;
                ya = aa + tmp - tmp;
                yb = aa - ya + ab;
            }
            /* Multiply a = y * x */
            aa = ya * xa;
            ab = ya * xb + yb * xa + yb * xb;
            /* split, so now lnz = a */
            /*
      tmp = aa * 1073741824.0;
      lnza = aa + tmp - tmp;
      lnzb = aa - lnza + ab;
             */
            lnza = aa + ab;
            lnzb = -(lnza - aa - ab);
        } else {
            /* High precision not required.  Eval Remez polynomial
         using standard double precision */
            lnza = -0.16624882440418567;
            lnza = lnza * epsilon + 0.19999954120254515;
            lnza = lnza * epsilon + -0.2499999997677497;
            lnza = lnza * epsilon + 0.3333333333332802;
            lnza = lnza * epsilon + -0.5;
            lnza = lnza * epsilon + 1.0;
            lnza *= epsilon;
        }
        /* Relative sizes:
         * lnzb     [0, 2.33E-10]
         * lnm[1]   [0, 1.17E-7]
         * ln2B*exp [0, 1.12E-4]
         * lnza      [0, 9.7E-4]
         * lnm[0]   [0, 0.692]
         * ln2A*exp [0, 709]
         */
        /* Compute the following sum:
         * lnzb + lnm[1] + ln2B*exp + lnza + lnm[0] + ln2A*exp;
         */
        //return lnzb + lnm[1] + ln2B*exp + lnza + lnm[0] + ln2A*exp;
        double a = LN_2_A * exp;
        double b = 0.0;
        double c = a + lnm[0];
        double d = -(c - a - lnm[0]);
        a = c;
        b += d;
        c = a + lnza;
        d = -(c - a - lnza);
        a = c;
        b += d;
        c = a + LN_2_B * exp;
        d = -(c - a - LN_2_B * exp);
        a = c;
        b += d;
        c = a + lnm[1];
        d = -(c - a - lnm[1]);
        a = c;
        b += d;
        c = a + lnzb;
        d = -(c - a - lnzb);
        a = c;
        b += d;
        if (hiPrec != null) {
            hiPrec[0] = a;
            hiPrec[1] = b;
        }
        return a + b;
    }

    /**
     * Computes log(1 + x).
     *
     * @param x Number.
     * @return {@code log(1 + x)}.
     */
    public static double log1p(final double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the base 10 logarithm.
     * @param x a number
     * @return log10(x)
     */
    public static double log10(final double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Computes the <a href="http://mathworld.wolfram.com/Logarithm.html">
     * logarithm</a> in a given base.
     *
     * Returns {@code NaN} if either argument is negative.
     * If {@code base} is 0 and {@code x} is positive, 0 is returned.
     * If {@code base} is positive and {@code x} is 0,
     * {@code Double.NEGATIVE_INFINITY} is returned.
     * If both arguments are 0, the result is {@code NaN}.
     *
     * @param base Base of the logarithm, must be greater than 0.
     * @param x Argument, must be greater than 0.
     * @return the value of the logarithm, i.e. the number {@code y} such that
     * <code>base<sup>y</sup> = x</code>.
     */
    public static double log(double base, double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Power function.  Compute x^y.
     *
     * @param x   a double
     * @param y   a double
     * @return double
     */
    public static double pow(final double x, final double y) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Raise a double to an int power.
     *
     * @param d Number to raise.
     * @param e Exponent.
     * @return d<sup>e</sup>
     */
    public static double pow(double d, int e) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Raise a double to a long power.
     *
     * @param d Number to raise.
     * @param e Exponent.
     * @return d<sup>e</sup>
     */
    public static double pow(double d, long e) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Class operator on double numbers split into one 26 bits number and one 27 bits number.
     */
    private static class Split {

        /**
         * Split version of NaN.
         */
        public static final Split NAN = new Split(Double.NaN, 0);

        /**
         * Split version of positive infinity.
         */
        public static final Split POSITIVE_INFINITY = new Split(Double.POSITIVE_INFINITY, 0);

        /**
         * Split version of negative infinity.
         */
        public static final Split NEGATIVE_INFINITY = new Split(Double.NEGATIVE_INFINITY, 0);

        /**
         * Full number.
         */
        private final double full;

        /**
         * High order bits.
         */
        private final double high;

        /**
         * Low order bits.
         */
        private final double low;

        /**
         * Simple constructor.
         * @param x number to split
         */
        Split(final double x) {
            full = x;
            high = Double.longBitsToDouble(Double.doubleToRawLongBits(x) & ((-1L) << 27));
            low = x - high;
        }

        /**
         * Simple constructor.
         * @param high high order bits
         * @param low low order bits
         */
        Split(final double high, final double low) {
            this(high == 0.0 ? (low == 0.0 && Double.doubleToRawLongBits(high) == Long.MIN_VALUE ? /* negative zero */
            -0.0 : low) : high + low, high, low);
        }

        /**
         * Simple constructor.
         * @param full full number
         * @param high high order bits
         * @param low low order bits
         */
        Split(final double full, final double high, final double low) {
            this.full = full;
            this.high = high;
            this.low = low;
        }

        /**
         * Multiply the instance by another one.
         * @param b other instance to multiply by
         * @return product
         */
        public Split multiply(final Split b) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Compute the reciprocal of the instance.
         * @return reciprocal of the instance
         */
        public Split reciprocal() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Computes this^e.
         * @param e exponent (beware, here it MUST be > 0; the only exclusion is Long.MIN_VALUE)
         * @return d^e, split in high and low bits
         */
        private Split pow(final long e) {
            // prepare result
            Split result = new Split(1);
            // d^(2p)
            Split d2p = new Split(full, high, low);
            for (long p = e; p != 0; p >>>= 1) {
                if ((p & 0x1) != 0) {
                    // accurate multiplication result = result * d^(2p) using Veltkamp TwoProduct algorithm
                    result = result.multiply(d2p);
                }
                // accurate squaring d^(2(p+1)) = d^(2p) * d^(2p) using Veltkamp TwoProduct algorithm
                d2p = d2p.multiply(d2p);
            }
            if (Double.isNaN(result.full)) {
                if (Double.isNaN(full)) {
                    return NAN;
                } else {
                    // some intermediate numbers exceeded capacity,
                    // and the low order bits became NaN (because infinity - infinity = NaN)
                    if (abs(full) < 1) {
                        return new Split(copySign(0.0, full), 0.0);
                    } else if (full < 0 && (e & 0x1) == 1) {
                        return NEGATIVE_INFINITY;
                    } else {
                        return POSITIVE_INFINITY;
                    }
                }
            } else {
                return result;
            }
        }
    }

    /**
     *  Computes sin(x) - x, where |x| < 1/16.
     *  Use a Remez polynomial approximation.
     *  @param x a number smaller than 1/16
     *  @return sin(x) - x
     */
    private static double polySine(final double x) {
        double x2 = x * x;
        double p = 2.7553817452272217E-6;
        p = p * x2 + -1.9841269659586505E-4;
        p = p * x2 + 0.008333333333329196;
        p = p * x2 + -0.16666666666666666;
        //p *= x2;
        //p *= x;
        p = p * x2 * x;
        return p;
    }

    /**
     *  Computes cos(x) - 1, where |x| < 1/16.
     *  Use a Remez polynomial approximation.
     *  @param x a number smaller than 1/16
     *  @return cos(x) - 1
     */
    private static double polyCosine(double x) {
        double x2 = x * x;
        double p = 2.479773539153719E-5;
        p = p * x2 + -0.0013888888689039883;
        p = p * x2 + 0.041666666666621166;
        p = p * x2 + -0.49999999999999994;
        p *= x2;
        return p;
    }

    /**
     *  Compute sine over the first quadrant (0 < x < pi/2).
     *  Use combination of table lookup and rational polynomial expansion.
     *  @param xa number from which sine is requested
     *  @param xb extra bits for x (may be 0.0)
     *  @return sin(xa + xb)
     */
    private static double sinQ(double xa, double xb) {
        int idx = (int) ((xa * 8.0) + 0.5);
        //idx*0.125;
        final double epsilon = xa - EIGHTHS[idx];
        // Table lookups
        final double sintA = SINE_TABLE_A[idx];
        final double sintB = SINE_TABLE_B[idx];
        final double costA = COSINE_TABLE_A[idx];
        final double costB = COSINE_TABLE_B[idx];
        // Polynomial eval of sin(epsilon), cos(epsilon)
        double sinEpsA = epsilon;
        double sinEpsB = polySine(epsilon);
        final double cosEpsA = 1.0;
        final double cosEpsB = polyCosine(epsilon);
        // Split epsilon   xa + xb = x
        final double temp = sinEpsA * HEX_40000000;
        double temp2 = (sinEpsA + temp) - temp;
        sinEpsB += sinEpsA - temp2;
        sinEpsA = temp2;
        /* Compute sin(x) by angle addition formula */
        double result;
        /* Compute the following sum:
         *
         * result = sintA + costA*sinEpsA + sintA*cosEpsB + costA*sinEpsB +
         *          sintB + costB*sinEpsA + sintB*cosEpsB + costB*sinEpsB;
         *
         * Ranges of elements
         *
         * xxxtA   0            PI/2
         * xxxtB   -1.5e-9      1.5e-9
         * sinEpsA -0.0625      0.0625
         * sinEpsB -6e-11       6e-11
         * cosEpsA  1.0
         * cosEpsB  0           -0.0625
         *
         */
        //result = sintA + costA*sinEpsA + sintA*cosEpsB + costA*sinEpsB +
        //          sintB + costB*sinEpsA + sintB*cosEpsB + costB*sinEpsB;
        //result = sintA + sintA*cosEpsB + sintB + sintB * cosEpsB;
        //result += costA*sinEpsA + costA*sinEpsB + costB*sinEpsA + costB * sinEpsB;
        double a = 0;
        double b = 0;
        double t = sintA;
        double c = a + t;
        double d = -(c - a - t);
        a = c;
        b += d;
        t = costA * sinEpsA;
        c = a + t;
        d = -(c - a - t);
        a = c;
        b += d;
        b = b + sintA * cosEpsB + costA * sinEpsB;
        /*
    t = sintA*cosEpsB;
    c = a + t;
    d = -(c - a - t);
    a = c;
    b = b + d;

    t = costA*sinEpsB;
    c = a + t;
    d = -(c - a - t);
    a = c;
    b = b + d;
         */
        b = b + sintB + costB * sinEpsA + sintB * cosEpsB + costB * sinEpsB;
        /*
    t = sintB;
    c = a + t;
    d = -(c - a - t);
    a = c;
    b = b + d;

    t = costB*sinEpsA;
    c = a + t;
    d = -(c - a - t);
    a = c;
    b = b + d;

    t = sintB*cosEpsB;
    c = a + t;
    d = -(c - a - t);
    a = c;
    b = b + d;

    t = costB*sinEpsB;
    c = a + t;
    d = -(c - a - t);
    a = c;
    b = b + d;
         */
        if (xb != 0.0) {
            t = ((costA + costB) * (cosEpsA + cosEpsB) - (sintA + sintB) * (sinEpsA + sinEpsB)) * // approximate cosine*xb
            xb;
            c = a + t;
            d = -(c - a - t);
            a = c;
            b += d;
        }
        result = a + b;
        return result;
    }

    /**
     * Compute cosine in the first quadrant by subtracting input from PI/2 and
     * then calling sinQ.  This is more accurate as the input approaches PI/2.
     *  @param xa number from which cosine is requested
     *  @param xb extra bits for x (may be 0.0)
     *  @return cos(xa + xb)
     */
    private static double cosQ(double xa, double xb) {
        final double pi2a = 1.5707963267948966;
        final double pi2b = 6.123233995736766E-17;
        final double a = pi2a - xa;
        double b = -(a - pi2a + xa);
        b += pi2b - xb;
        return sinQ(a, b);
    }

    /**
     *  Compute tangent (or cotangent) over the first quadrant.   0 < x < pi/2
     *  Use combination of table lookup and rational polynomial expansion.
     *  @param xa number from which sine is requested
     *  @param xb extra bits for x (may be 0.0)
     *  @param cotanFlag if true, compute the cotangent instead of the tangent
     *  @return tan(xa+xb) (or cotangent, depending on cotanFlag)
     */
    private static double tanQ(double xa, double xb, boolean cotanFlag) {
        int idx = (int) ((xa * 8.0) + 0.5);
        //idx*0.125;
        final double epsilon = xa - EIGHTHS[idx];
        // Table lookups
        final double sintA = SINE_TABLE_A[idx];
        final double sintB = SINE_TABLE_B[idx];
        final double costA = COSINE_TABLE_A[idx];
        final double costB = COSINE_TABLE_B[idx];
        // Polynomial eval of sin(epsilon), cos(epsilon)
        double sinEpsA = epsilon;
        double sinEpsB = polySine(epsilon);
        final double cosEpsA = 1.0;
        final double cosEpsB = polyCosine(epsilon);
        // Split epsilon   xa + xb = x
        double temp = sinEpsA * HEX_40000000;
        double temp2 = (sinEpsA + temp) - temp;
        sinEpsB += sinEpsA - temp2;
        sinEpsA = temp2;
        /* Compute sin(x) by angle addition formula */
        /* Compute the following sum:
         *
         * result = sintA + costA*sinEpsA + sintA*cosEpsB + costA*sinEpsB +
         *          sintB + costB*sinEpsA + sintB*cosEpsB + costB*sinEpsB;
         *
         * Ranges of elements
         *
         * xxxtA   0            PI/2
         * xxxtB   -1.5e-9      1.5e-9
         * sinEpsA -0.0625      0.0625
         * sinEpsB -6e-11       6e-11
         * cosEpsA  1.0
         * cosEpsB  0           -0.0625
         *
         */
        //result = sintA + costA*sinEpsA + sintA*cosEpsB + costA*sinEpsB +
        //          sintB + costB*sinEpsA + sintB*cosEpsB + costB*sinEpsB;
        //result = sintA + sintA*cosEpsB + sintB + sintB * cosEpsB;
        //result += costA*sinEpsA + costA*sinEpsB + costB*sinEpsA + costB * sinEpsB;
        double a = 0;
        double b = 0;
        // Compute sine
        double t = sintA;
        double c = a + t;
        double d = -(c - a - t);
        a = c;
        b += d;
        t = costA * sinEpsA;
        c = a + t;
        d = -(c - a - t);
        a = c;
        b += d;
        b += sintA * cosEpsB + costA * sinEpsB;
        b += sintB + costB * sinEpsA + sintB * cosEpsB + costB * sinEpsB;
        double sina = a + b;
        double sinb = -(sina - a - b);
        // Compute cosine
        a = 0.0;
        b = 0.0;
        t = costA * cosEpsA;
        c = a + t;
        d = -(c - a - t);
        a = c;
        b += d;
        t = -sintA * sinEpsA;
        c = a + t;
        d = -(c - a - t);
        a = c;
        b += d;
        b += costB * cosEpsA + costA * cosEpsB + costB * cosEpsB;
        b -= sintB * sinEpsA + sintA * sinEpsB + sintB * sinEpsB;
        double cosa = a + b;
        double cosb = -(cosa - a - b);
        if (cotanFlag) {
            double tmp;
            tmp = cosa;
            cosa = sina;
            sina = tmp;
            tmp = cosb;
            cosb = sinb;
            sinb = tmp;
        }
        /* estimate and correct, compute 1.0/(cosa+cosb) */
        /*
    double est = (sina+sinb)/(cosa+cosb);
    double err = (sina - cosa*est) + (sinb - cosb*est);
    est += err/(cosa+cosb);
    err = (sina - cosa*est) + (sinb - cosb*est);
         */
        // f(x) = 1/x,   f'(x) = -1/x^2
        double est = sina / cosa;
        /* Split the estimate to get more accurate read on division rounding */
        temp = est * HEX_40000000;
        double esta = (est + temp) - temp;
        double estb = est - esta;
        temp = cosa * HEX_40000000;
        double cosaa = (cosa + temp) - temp;
        double cosab = cosa - cosaa;
        //double err = (sina - est*cosa)/cosa;  // Correction for division rounding
        // Correction for division rounding
        double err = (sina - esta * cosaa - esta * cosab - estb * cosaa - estb * cosab) / cosa;
        // Change in est due to sinb
        err += sinb / cosa;
        // Change in est due to cosb
        err += -sina * cosb / cosa / cosa;
        if (xb != 0.0) {
            // tan' = 1 + tan^2      cot' = -(1 + cot^2)
            // Approximate impact of xb
            double xbadj = xb + est * est * xb;
            if (cotanFlag) {
                xbadj = -xbadj;
            }
            err += xbadj;
        }
        return est + err;
    }

    /**
     * Reduce the input argument using the Payne and Hanek method.
     *  This is good for all inputs 0.0 < x < inf
     *  Output is remainder after dividing by PI/2
     *  The result array should contain 3 numbers.
     *  result[0] is the integer portion, so mod 4 this gives the quadrant.
     *  result[1] is the upper bits of the remainder
     *  result[2] is the lower bits of the remainder
     *
     * @param x number to reduce
     * @param result placeholder where to put the result
     */
    private static void reducePayneHanek(double x, double[] result) {
        /* Convert input double to bits */
        long inbits = Double.doubleToRawLongBits(x);
        int exponent = (int) ((inbits >> 52) & 0x7ff) - 1023;
        /* Convert to fixed point representation */
        inbits &= 0x000fffffffffffffL;
        inbits |= 0x0010000000000000L;
        /* Normalize input to be between 0.5 and 1.0 */
        exponent++;
        inbits <<= 11;
        /* Based on the exponent, get a shifted copy of recip2pi */
        long shpi0;
        long shpiA;
        long shpiB;
        int idx = exponent >> 6;
        int shift = exponent - (idx << 6);
        if (shift != 0) {
            shpi0 = (idx == 0) ? 0 : (RECIP_2PI[idx - 1] << shift);
            shpi0 |= RECIP_2PI[idx] >>> (64 - shift);
            shpiA = (RECIP_2PI[idx] << shift) | (RECIP_2PI[idx + 1] >>> (64 - shift));
            shpiB = (RECIP_2PI[idx + 1] << shift) | (RECIP_2PI[idx + 2] >>> (64 - shift));
        } else {
            shpi0 = (idx == 0) ? 0 : RECIP_2PI[idx - 1];
            shpiA = RECIP_2PI[idx];
            shpiB = RECIP_2PI[idx + 1];
        }
        /* Multiply input by shpiA */
        long a = inbits >>> 32;
        long b = inbits & 0xffffffffL;
        long c = shpiA >>> 32;
        long d = shpiA & 0xffffffffL;
        long ac = a * c;
        long bd = b * d;
        long bc = b * c;
        long ad = a * d;
        long prodB = bd + (ad << 32);
        long prodA = ac + (ad >>> 32);
        boolean bita = (bd & 0x8000000000000000L) != 0;
        boolean bitb = (ad & 0x80000000L) != 0;
        boolean bitsum = (prodB & 0x8000000000000000L) != 0;
        /* Carry */
        if ((bita && bitb) || ((bita || bitb) && !bitsum)) {
            prodA++;
        }
        bita = (prodB & 0x8000000000000000L) != 0;
        bitb = (bc & 0x80000000L) != 0;
        prodB += bc << 32;
        prodA += bc >>> 32;
        bitsum = (prodB & 0x8000000000000000L) != 0;
        /* Carry */
        if ((bita && bitb) || ((bita || bitb) && !bitsum)) {
            prodA++;
        }
        /* Multiply input by shpiB */
        c = shpiB >>> 32;
        d = shpiB & 0xffffffffL;
        ac = a * c;
        bc = b * c;
        ad = a * d;
        /* Collect terms */
        ac += (bc + ad) >>> 32;
        bita = (prodB & 0x8000000000000000L) != 0;
        bitb = (ac & 0x8000000000000000L) != 0;
        prodB += ac;
        bitsum = (prodB & 0x8000000000000000L) != 0;
        /* Carry */
        if ((bita && bitb) || ((bita || bitb) && !bitsum)) {
            prodA++;
        }
        /* Multiply by shpi0 */
        c = shpi0 >>> 32;
        d = shpi0 & 0xffffffffL;
        bd = b * d;
        bc = b * c;
        ad = a * d;
        prodA += bd + ((bc + ad) << 32);
        /*
         * prodA, prodB now contain the remainder as a fraction of PI.  We want this as a fraction of
         * PI/2, so use the following steps:
         * 1.) multiply by 4.
         * 2.) do a fixed point muliply by PI/4.
         * 3.) Convert to floating point.
         * 4.) Multiply by 2
         */
        /* This identifies the quadrant */
        int intPart = (int) (prodA >>> 62);
        /* Multiply by 4 */
        prodA <<= 2;
        prodA |= prodB >>> 62;
        prodB <<= 2;
        /* Multiply by PI/4 */
        a = prodA >>> 32;
        b = prodA & 0xffffffffL;
        c = PI_O_4_BITS[0] >>> 32;
        d = PI_O_4_BITS[0] & 0xffffffffL;
        ac = a * c;
        bd = b * d;
        bc = b * c;
        ad = a * d;
        long prod2B = bd + (ad << 32);
        long prod2A = ac + (ad >>> 32);
        bita = (bd & 0x8000000000000000L) != 0;
        bitb = (ad & 0x80000000L) != 0;
        bitsum = (prod2B & 0x8000000000000000L) != 0;
        /* Carry */
        if ((bita && bitb) || ((bita || bitb) && !bitsum)) {
            prod2A++;
        }
        bita = (prod2B & 0x8000000000000000L) != 0;
        bitb = (bc & 0x80000000L) != 0;
        prod2B += bc << 32;
        prod2A += bc >>> 32;
        bitsum = (prod2B & 0x8000000000000000L) != 0;
        /* Carry */
        if ((bita && bitb) || ((bita || bitb) && !bitsum)) {
            prod2A++;
        }
        /* Multiply input by pio4bits[1] */
        c = PI_O_4_BITS[1] >>> 32;
        d = PI_O_4_BITS[1] & 0xffffffffL;
        ac = a * c;
        bc = b * c;
        ad = a * d;
        /* Collect terms */
        ac += (bc + ad) >>> 32;
        bita = (prod2B & 0x8000000000000000L) != 0;
        bitb = (ac & 0x8000000000000000L) != 0;
        prod2B += ac;
        bitsum = (prod2B & 0x8000000000000000L) != 0;
        /* Carry */
        if ((bita && bitb) || ((bita || bitb) && !bitsum)) {
            prod2A++;
        }
        /* Multiply inputB by pio4bits[0] */
        a = prodB >>> 32;
        b = prodB & 0xffffffffL;
        c = PI_O_4_BITS[0] >>> 32;
        d = PI_O_4_BITS[0] & 0xffffffffL;
        ac = a * c;
        bc = b * c;
        ad = a * d;
        /* Collect terms */
        ac += (bc + ad) >>> 32;
        bita = (prod2B & 0x8000000000000000L) != 0;
        bitb = (ac & 0x8000000000000000L) != 0;
        prod2B += ac;
        bitsum = (prod2B & 0x8000000000000000L) != 0;
        /* Carry */
        if ((bita && bitb) || ((bita || bitb) && !bitsum)) {
            prod2A++;
        }
        /* Convert to double */
        // High order 52 bits
        double tmpA = (prod2A >>> 12) / TWO_POWER_52;
        // Low bits
        double tmpB = (((prod2A & 0xfffL) << 40) + (prod2B >>> 24)) / TWO_POWER_52 / TWO_POWER_52;
        double sumA = tmpA + tmpB;
        double sumB = -(sumA - tmpA - tmpB);
        /* Multiply by PI/2 and return */
        result[0] = intPart;
        result[1] = sumA * 2.0;
        result[2] = sumB * 2.0;
    }

    /**
     * Sine function.
     *
     * @param x Argument.
     * @return sin(x)
     */
    public static double sin(double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Cosine function.
     *
     * @param x Argument.
     * @return cos(x)
     */
    public static double cos(double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Combined Sine and Cosine function.
     *
     * @param x Argument.
     * @return [sin(x), cos(x)]
     */
    public static SinCos sinCos(double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Combined Sine and Cosine function.
     *
     * @param x Argument.
     * @param <T> the type of the field element
     * @return [sin(x), cos(x)]
     * @since 1.4
     */
    public static <T extends CalculusFieldElement<T>> FieldSinCos<T> sinCos(T x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Tangent function.
     *
     * @param x Argument.
     * @return tan(x)
     */
    public static double tan(double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Arctangent function
     *  @param x a number
     *  @return atan(x)
     */
    public static double atan(double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Internal helper function to compute arctangent.
     * @param xa number from which arctangent is requested
     * @param xb extra bits for x (may be 0.0)
     * @param leftPlane if true, result angle must be put in the left half plane
     * @return atan(xa + xb) (or angle shifted by {@code PI} if leftPlane is true)
     */
    private static double atan(double xa, double xb, boolean leftPlane) {
        if (xa == 0.0) {
            // Matches +/- 0.0; return correct sign
            return leftPlane ? copySign(Math.PI, xa) : xa;
        }
        final boolean negate;
        if (xa < 0) {
            // negative
            xa = -xa;
            xb = -xb;
            negate = true;
        } else {
            negate = false;
        }
        if (xa > 1.633123935319537E16) {
            // Very large input
            return (negate ^ leftPlane) ? (-Math.PI * F_1_2) : (Math.PI * F_1_2);
        }
        /* Estimate the closest tabulated arctan value, compute eps = xa-tangentTable */
        final int idx;
        if (xa < 1) {
            idx = (int) (((-1.7168146928204136 * xa * xa + 8.0) * xa) + 0.5);
        } else {
            final double oneOverXa = 1 / xa;
            idx = (int) (-((-1.7168146928204136 * oneOverXa * oneOverXa + 8.0) * oneOverXa) + 13.07);
        }
        final double ttA = TANGENT_TABLE_A[idx];
        final double ttB = TANGENT_TABLE_B[idx];
        double epsA = xa - ttA;
        double epsB = -(epsA - xa + ttA);
        epsB += xb - ttB;
        double temp = epsA + epsB;
        epsB = -(temp - epsA - epsB);
        epsA = temp;
        /* Compute eps = eps / (1.0 + xa*tangent) */
        temp = xa * HEX_40000000;
        double ya = xa + temp - temp;
        double yb = xb + xa - ya;
        xa = ya;
        xb += yb;
        //if (idx > 8 || idx == 0)
        if (idx == 0) {
            /* If the slope of the arctan is gentle enough (< 0.45), this approximation will suffice */
            //double denom = 1.0 / (1.0 + xa*tangentTableA[idx] + xb*tangentTableA[idx] + xa*tangentTableB[idx] + xb*tangentTableB[idx]);
            final double denom = 1d / (1d + (xa + xb) * (ttA + ttB));
            //double denom = 1.0 / (1.0 + xa*tangentTableA[idx]);
            ya = epsA * denom;
            yb = epsB * denom;
        } else {
            double temp2 = xa * ttA;
            double za = 1d + temp2;
            double zb = -(za - 1d - temp2);
            temp2 = xb * ttA + xa * ttB;
            temp = za + temp2;
            zb += -(temp - za - temp2);
            za = temp;
            zb += xb * ttB;
            ya = epsA / za;
            temp = ya * HEX_40000000;
            final double yaa = (ya + temp) - temp;
            final double yab = ya - yaa;
            temp = za * HEX_40000000;
            final double zaa = (za + temp) - temp;
            final double zab = za - zaa;
            /* Correct for rounding in division */
            yb = (epsA - yaa * zaa - yaa * zab - yab * zaa - yab * zab) / za;
            yb += -epsA * zb / za / za;
            yb += epsB / za;
        }
        epsA = ya;
        epsB = yb;
        /* Evaluate polynomial */
        final double epsA2 = epsA * epsA;
        /*
    yb = -0.09001346640161823;
    yb = yb * epsA2 + 0.11110718400605211;
    yb = yb * epsA2 + -0.1428571349122913;
    yb = yb * epsA2 + 0.19999999999273194;
    yb = yb * epsA2 + -0.33333333333333093;
    yb = yb * epsA2 * epsA;
         */
        yb = 0.07490822288864472;
        yb = yb * epsA2 - 0.09088450866185192;
        yb = yb * epsA2 + 0.11111095942313305;
        yb = yb * epsA2 - 0.1428571423679182;
        yb = yb * epsA2 + 0.19999999999923582;
        yb = yb * epsA2 - 0.33333333333333287;
        yb = yb * epsA2 * epsA;
        ya = epsA;
        temp = ya + yb;
        yb = -(temp - ya - yb);
        ya = temp;
        /* Add in effect of epsB.   atan'(x) = 1/(1+x^2) */
        yb += epsB / (1d + epsA * epsA);
        final double eighths = EIGHTHS[idx];
        //result = yb + eighths[idx] + ya;
        double za = eighths + ya;
        double zb = -(za - eighths - ya);
        temp = za + yb;
        zb += -(temp - za - yb);
        za = temp;
        double result = za + zb;
        if (leftPlane) {
            // Result is in the left plane
            final double resultb = -(result - za - zb);
            final double pia = 1.5707963267948966 * 2;
            final double pib = 6.123233995736766E-17 * 2;
            za = pia - result;
            zb = -(za - pia + result);
            zb += pib - resultb;
            result = za + zb;
        }
        if (negate ^ leftPlane) {
            result = -result;
        }
        return result;
    }

    /**
     * Two arguments arctangent function
     * @param y ordinate
     * @param x abscissa
     * @return phase angle of point (x,y) between {@code -PI} and {@code PI}
     */
    public static double atan2(double y, double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the arc sine of a number.
     * @param x number on which evaluation is done
     * @return arc sine of x
     */
    public static double asin(double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the arc cosine of a number.
     * @param x number on which evaluation is done
     * @return arc cosine of x
     */
    public static double acos(double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the cubic root of a number.
     * @param x number on which evaluation is done
     * @return cubic root of x
     */
    public static double cbrt(double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *  Convert degrees to radians, with error of less than 0.5 ULP
     *  @param x angle in degrees
     *  @return x converted into radians
     */
    public static double toRadians(double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *  Convert radians to degrees, with error of less than 0.5 ULP
     *  @param x angle in radians
     *  @return x converted into degrees
     */
    public static double toDegrees(double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Absolute value.
     * @param x number from which absolute value is requested
     * @return abs(x)
     */
    public static int abs(final int x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Absolute value.
     * @param x number from which absolute value is requested
     * @return abs(x)
     */
    public static long abs(final long x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Absolute value.
     * @param x number from which absolute value is requested
     * @return abs(x), or throws an exception for {@code Integer.MIN_VALUE}
     * @since 2.0
     */
    public static int absExact(final int x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Absolute value.
     * @param x number from which absolute value is requested
     * @return abs(x), or throws an exception for {@code Long.MIN_VALUE}
     * @since 2.0
     */
    public static long absExact(final long x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Absolute value.
     * @param x number from which absolute value is requested
     * @return abs(x)
     * @since 2.0
     */
    public static float abs(final float x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Absolute value.
     * @param x number from which absolute value is requested
     * @return abs(x)
     */
    public static double abs(double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Negates the argument.
     * @param x number from which opposite value is requested
     * @return -x, or throws an exception for {@code Integer.MIN_VALUE}
     * @since 2.0
     */
    public static int negateExact(final int x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Negates the argument.
     * @param x number from which opposite value is requested
     * @return -x, or throws an exception for {@code Long.MIN_VALUE}
     * @since 2.0
     */
    public static long negateExact(final long x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute least significant bit (Unit in Last Position) for a number.
     * @param x number from which ulp is requested
     * @return ulp(x)
     */
    public static double ulp(double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute least significant bit (Unit in Last Position) for a number.
     * @param x number from which ulp is requested
     * @return ulp(x)
     */
    public static float ulp(float x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Multiply a double number by a power of 2.
     * @param d number to multiply
     * @param n power of 2
     * @return d &times; 2<sup>n</sup>
     */
    public static double scalb(final double d, final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Multiply a float number by a power of 2.
     * @param f number to multiply
     * @param n power of 2
     * @return f &times; 2<sup>n</sup>
     */
    public static float scalb(final float f, final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the next machine representable number after a number, moving
     * in the direction of another number.
     * <p>
     * The ordering is as follows (increasing):
     * </p>
     * <ul>
     * <li>-INFINITY</li>
     * <li>-MAX_VALUE</li>
     * <li>-MIN_VALUE</li>
     * <li>-0.0</li>
     * <li>+0.0</li>
     * <li>+MIN_VALUE</li>
     * <li>+MAX_VALUE</li>
     * <li>+INFINITY</li>
     * </ul>
     * <p>
     * If arguments compare equal, then the second argument is returned.
     * </p>
     * <p>
     * If {@code direction} is greater than {@code d},
     * the smallest machine representable number strictly greater than
     * {@code d} is returned; if less, then the largest representable number
     * strictly less than {@code d} is returned.
     * </p>
     * <p>
     * If {@code d} is infinite and direction does not
     * bring it back to finite numbers, it is returned unchanged.
     * </p>
     *
     * @param d base number
     * @param direction (the only important thing is whether
     * {@code direction} is greater or smaller than {@code d})
     * @return the next machine representable number in the specified direction
     */
    public static double nextAfter(double d, double direction) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the next machine representable number after a number, moving
     * in the direction of another number.
     * <p>* The ordering is as follows (increasing):</p>
     * <ul>
     * <li>-INFINITY</li>
     * <li>-MAX_VALUE</li>
     * <li>-MIN_VALUE</li>
     * <li>-0.0</li>
     * <li>+0.0</li>
     * <li>+MIN_VALUE</li>
     * <li>+MAX_VALUE</li>
     * <li>+INFINITY</li>
     * </ul>
     * <p>
     * If arguments compare equal, then the second argument is returned.
     * </p>
     * <p>
     * If {@code direction} is greater than {@code f},
     * the smallest machine representable number strictly greater than
     * {@code f} is returned; if less, then the largest representable number
     * strictly less than {@code f} is returned.
     * </p>
     * <p>
     * If {@code f} is infinite and direction does not
     * bring it back to finite numbers, it is returned unchanged.
     * </p>
     *
     * @param f base number
     * @param direction (the only important thing is whether
     * {@code direction} is greater or smaller than {@code f})
     * @return the next machine representable number in the specified direction
     */
    public static float nextAfter(final float f, final double direction) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the largest whole number smaller than x.
     * @param x number from which floor is requested
     * @return a double number f such that f is an integer f &lt;= x &lt; f + 1.0
     */
    public static double floor(double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the smallest whole number larger than x.
     * @param x number from which ceil is requested
     * @return a double number c such that c is an integer c - 1.0 &lt; x &lt;= c
     */
    public static double ceil(double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the whole number that is the nearest to x, or the even one if x is exactly half way between two integers.
     * @param x number from which nearest whole number is requested
     * @return a double number r such that r is an integer r - 0.5 &lt;= x &lt;= r + 0.5
     */
    public static double rint(double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the closest long to x.
     * @param x number from which closest long is requested
     * @return closest long to x
     */
    public static long round(double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the closest int to x.
     * @param x number from which closest int is requested
     * @return closest int to x
     */
    public static int round(final float x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the minimum of two values
     * @param a first value
     * @param b second value
     * @return a if a is lesser or equal to b, b otherwise
     */
    public static int min(final int a, final int b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the minimum of two values
     * @param a first value
     * @param b second value
     * @return a if a is lesser or equal to b, b otherwise
     */
    public static long min(final long a, final long b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the minimum of two values
     * @param a first value
     * @param b second value
     * @return a if a is lesser or equal to b, b otherwise
     */
    public static float min(final float a, final float b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the minimum of two values
     * @param a first value
     * @param b second value
     * @return a if a is lesser or equal to b, b otherwise
     */
    public static double min(final double a, final double b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the maximum of two values
     * @param a first value
     * @param b second value
     * @return b if a is lesser or equal to b, a otherwise
     */
    public static int max(final int a, final int b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the maximum of two values
     * @param a first value
     * @param b second value
     * @return b if a is lesser or equal to b, a otherwise
     */
    public static long max(final long a, final long b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the maximum of two values
     * @param a first value
     * @param b second value
     * @return b if a is lesser or equal to b, a otherwise
     */
    public static float max(final float a, final float b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the maximum of two values
     * @param a first value
     * @param b second value
     * @return b if a is lesser or equal to b, a otherwise
     */
    public static double max(final double a, final double b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the hypotenuse of a triangle with sides {@code x} and {@code y}
     * - sqrt(<i>x</i><sup>2</sup>&nbsp;+<i>y</i><sup>2</sup>)<br>
     * avoiding intermediate overflow or underflow.
     *
     * <ul>
     * <li> If either argument is infinite, then the result is positive infinity.</li>
     * <li> else, if either argument is NaN then the result is NaN.</li>
     * </ul>
     *
     * @param x a value
     * @param y a value
     * @return sqrt(<i>x</i><sup>2</sup>&nbsp;+<i>y</i><sup>2</sup>)
     */
    public static double hypot(final double x, final double y) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Computes the remainder as prescribed by the IEEE 754 standard.
     * <p>
     * The remainder value is mathematically equal to {@code x - y*n}
     * where {@code n} is the mathematical integer closest to the exact mathematical value
     * of the quotient {@code x/y}.
     * If two mathematical integers are equally close to {@code x/y} then
     * {@code n} is the integer that is even.
     * </p>
     * <ul>
     * <li>If either operand is NaN, the result is NaN.</li>
     * <li>If the result is not NaN, the sign of the result equals the sign of the dividend.</li>
     * <li>If the dividend is an infinity, or the divisor is a zero, or both, the result is NaN.</li>
     * <li>If the dividend is finite and the divisor is an infinity, the result equals the dividend.</li>
     * <li>If the dividend is a zero and the divisor is finite, the result equals the dividend.</li>
     * </ul>
     * @param dividend the number to be divided
     * @param divisor the number by which to divide
     * @return the remainder, rounded
     */
    public static double IEEEremainder(final double dividend, final double divisor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convert a long to interger, detecting overflows
     * @param n number to convert to int
     * @return integer with same valie as n if no overflows occur
     * @exception MathRuntimeException if n cannot fit into an int
     */
    public static int toIntExact(final long n) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Increment a number, detecting overflows.
     * @param n number to increment
     * @return n+1 if no overflows occur
     * @exception MathRuntimeException if an overflow occurs
     */
    public static int incrementExact(final int n) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Increment a number, detecting overflows.
     * @param n number to increment
     * @return n+1 if no overflows occur
     * @exception MathRuntimeException if an overflow occurs
     */
    public static long incrementExact(final long n) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Decrement a number, detecting overflows.
     * @param n number to decrement
     * @return n-1 if no overflows occur
     * @exception MathRuntimeException if an overflow occurs
     */
    public static int decrementExact(final int n) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Decrement a number, detecting overflows.
     * @param n number to decrement
     * @return n-1 if no overflows occur
     * @exception MathRuntimeException if an overflow occurs
     */
    public static long decrementExact(final long n) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Add two numbers, detecting overflows.
     * @param a first number to add
     * @param b second number to add
     * @return a+b if no overflows occur
     * @exception MathRuntimeException if an overflow occurs
     */
    public static int addExact(final int a, final int b) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Add two numbers, detecting overflows.
     * @param a first number to add
     * @param b second number to add
     * @return a+b if no overflows occur
     * @exception MathRuntimeException if an overflow occurs
     */
    public static long addExact(final long a, final long b) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Subtract two numbers, detecting overflows.
     * @param a first number
     * @param b second number to subtract from a
     * @return a-b if no overflows occur
     * @exception MathRuntimeException if an overflow occurs
     */
    public static int subtractExact(final int a, final int b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Subtract two numbers, detecting overflows.
     * @param a first number
     * @param b second number to subtract from a
     * @return a-b if no overflows occur
     * @exception MathRuntimeException if an overflow occurs
     */
    public static long subtractExact(final long a, final long b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Multiply two numbers, detecting overflows.
     * @param a first number to multiply
     * @param b second number to multiply
     * @return a*b if no overflows occur
     * @exception MathRuntimeException if an overflow occurs
     */
    public static int multiplyExact(final int a, final int b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Multiply two numbers, detecting overflows.
     * @param a first number to multiply
     * @param b second number to multiply
     * @return a*b if no overflows occur
     * @exception MathRuntimeException if an overflow occurs
     * @since 1.3
     */
    public static long multiplyExact(final long a, final int b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Multiply two numbers, detecting overflows.
     * @param a first number to multiply
     * @param b second number to multiply
     * @return a*b if no overflows occur
     * @exception MathRuntimeException if an overflow occurs
     */
    public static long multiplyExact(final long a, final long b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Multiply two integers and give an exact result without overflow.
     * @param a first factor
     * @param b second factor
     * @return a * b exactly
     * @since 1.3
     */
    public static long multiplyFull(final int a, final int b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Multiply two long integers and give the 64 most significant bits of the result.
     * <p>
     * Beware that as Java primitive long are always considered to be signed, there are some
     * intermediate values {@code a} and {@code b} for which {@code a * b} exceeds {@code Long.MAX_VALUE}
     * but this method will still return 0l. This happens for example for {@code a = 2³¹} and
     * {@code b = 2³²} as {@code a * b = 2⁶³ = Long.MAX_VALUE + 1}, so it exceeds the max value
     * for a long, but still fits in 64 bits, so this method correctly returns 0l in this case,
     * but multiplication result would be considered negative (and in fact equal to {@code Long.MIN_VALUE}
     * </p>
     * @param a first factor
     * @param b second factor
     * @return a * b / 2<sup>64</sup>
     * @since 1.3
     */
    public static long multiplyHigh(final long a, final long b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Multiply two long unsigned integers and give the 64 most significant bits of the unsigned result.
     * <p>
     * Beware that as Java primitive long are always considered to be signed, there are some
     * intermediate values {@code a} and {@code b} for which {@code a * b} exceeds {@code Long.MAX_VALUE}
     * but this method will still return 0l. This happens for example for {@code a = 2³¹} and
     * {@code b = 2³²} as {@code a * b = 2⁶³ = Long.MAX_VALUE + 1}, so it exceeds the max value
     * for a long, but still fits in 64 bits, so this method correctly returns 0l in this case,
     * but multiplication result would be considered negative (and in fact equal to {@code Long.MIN_VALUE}
     * </p>
     * @param a first factor
     * @param b second factor
     * @return a * b / 2<sup>64</sup>
     * @since 3.0
     */
    public static long unsignedMultiplyHigh(final long a, final long b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Divide two integers, checking for overflow.
     * @param x dividend
     * @param y divisor
     * @return x / y
     * @exception MathRuntimeException if an overflow occurs
     * @since 3.0
     */
    public static int divideExact(final int x, final int y) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Divide two long integers, checking for overflow.
     * @param x dividend
     * @param y divisor
     * @return x / y
     * @exception MathRuntimeException if an overflow occurs
     * @since 3.0
     */
    public static long divideExact(final long x, final long y) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Finds q such that {@code a = q b + r} with {@code b < r <= 0} if {@code b > 0} and {@code 0 <= r < b} if {@code  b < 0}.
     * <p>
     * This methods returns the same value as integer division when
     * a and b are opposite signs, but returns a different value when
     * they are same (i.e. q is positive).
     *
     * @param a dividend
     * @param b divisor
     * @return q such that {@code a = q b + r} with {@code b < r <= 0} if {@code b > 0} and {@code 0 <= r < b} if {@code  b < 0}
     * @exception MathRuntimeException if b == 0
     * @since 3.0
     */
    public static int ceilDiv(final int a, final int b) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Finds q such that {@code a = q b + r} with {@code b < r <= 0} if {@code b > 0} and {@code 0 <= r < b} if {@code  b < 0}.
     * <p>
     * This methods returns the same value as integer division when
     * a and b are opposite signs, but returns a different value when
     * they are same (i.e. q is positive).
     *
     * @param a dividend
     * @param b divisor
     * @return q such that {@code a = q b + r} with {@code b < r <= 0} if {@code b > 0} and {@code 0 <= r < b} if {@code  b < 0}
     * @exception MathRuntimeException if b == 0 or if a == {@code Integer.MIN_VALUE} and b = -1
     * @since 3.0
     */
    public static int ceilDivExact(final int a, final int b) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Finds q such that {@code a = q b + r} with {@code b < r <= 0} if {@code b > 0} and {@code 0 <= r < b} if {@code  b < 0}.
     * <p>
     * This methods returns the same value as integer division when
     * a and b are opposite signs, but returns a different value when
     * they are same (i.e. q is positive).
     *
     * @param a dividend
     * @param b divisor
     * @return q such that {@code a = q b + r} with {@code b < r <= 0} if {@code b > 0} and {@code 0 <= r < b} if {@code  b < 0}
     * @exception MathRuntimeException if b == 0
     * @since 3.0
     */
    public static long ceilDiv(final long a, final long b) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Finds q such that {@code a = q b + r} with {@code b < r <= 0} if {@code b > 0} and {@code 0 <= r < b} if {@code  b < 0}.
     * <p>
     * This methods returns the same value as integer division when
     * a and b are opposite signs, but returns a different value when
     * they are same (i.e. q is positive).
     *
     * @param a dividend
     * @param b divisor
     * @return q such that {@code a = q b + r} with {@code b < r <= 0} if {@code b > 0} and {@code 0 <= r < b} if {@code  b < 0}
     * @exception MathRuntimeException if b == 0 or if a == {@code Long.MIN_VALUE} and b = -1
     * @since 3.0
     */
    public static long ceilDivExact(final long a, final long b) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Finds q such that {@code a = q b + r} with {@code b < r <= 0} if {@code b > 0} and {@code 0 <= r < b} if {@code  b < 0}.
     * <p>
     * This methods returns the same value as integer division when
     * a and b are opposite signs, but returns a different value when
     * they are same (i.e. q is positive).
     *
     * @param a dividend
     * @param b divisor
     * @return q such that {@code a = q b + r} with {@code b < r <= 0} if {@code b > 0} and {@code 0 <= r < b} if {@code  b < 0}
     * @exception MathRuntimeException if b == 0
     * @since 3.0
     */
    public static long ceilDiv(final long a, final int b) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Finds r such that {@code a = q b + r} with {@code b < r <= 0} if {@code b > 0} and {@code 0 <= r < b} if {@code  b < 0}.
     * <p>
     * This methods returns the same value as integer modulo when
     * a and b are opposite signs, but returns a different value when
     * they are same (i.e. q is positive).
     *
     * @param a dividend
     * @param b divisor
     * @return r such that {@code a = q b + r} with {@code b < r <= 0} if {@code b > 0} and {@code 0 <= r < b} if {@code  b < 0}
     * @exception MathRuntimeException if b == 0
     * @since 3.0
     */
    public static int ceilMod(final int a, final int b) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Finds r such that {@code a = q b + r} with {@code b < r <= 0} if {@code b > 0} and {@code 0 <= r < b} if {@code  b < 0}.
     * <p>
     * This methods returns the same value as integer modulo when
     * a and b are opposite signs, but returns a different value when
     * they are same (i.e. q is positive).
     *
     * @param a dividend
     * @param b divisor
     * @return r such that {@code a = q b + r} with {@code b < r <= 0} if {@code b > 0} and {@code 0 <= r < b} if {@code  b < 0}
     * @exception MathRuntimeException if b == 0
     * @since 3.0
     */
    public static int ceilMod(final long a, final int b) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Finds r such that {@code a = q b + r} with {@code b < r <= 0} if {@code b > 0} and {@code 0 <= r < b} if {@code  b < 0}.
     * <p>
     * This methods returns the same value as integer modulo when
     * a and b are opposite signs, but returns a different value when
     * they are same (i.e. q is positive).
     *
     * @param a dividend
     * @param b divisor
     * @return r such that {@code a = q b + r} with {@code b < r <= 0} if {@code b > 0} and {@code 0 <= r < b} if {@code  b < 0}
     * @exception MathRuntimeException if b == 0
     * @since 3.0
     */
    public static long ceilMod(final long a, final long b) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Finds q such that {@code a = q b + r} with {@code 0 <= r < b} if {@code b > 0} and {@code b < r <= 0} if {@code  b < 0}.
     * <p>
     * This methods returns the same value as integer division when
     * a and b are same signs, but returns a different value when
     * they are opposite (i.e. q is negative).
     *
     * @param a dividend
     * @param b divisor
     * @return q such that {@code a = q b + r} with {@code 0 <= r < b} if {@code b > 0} and {@code b < r <= 0} if {@code  b < 0}
     * @exception MathRuntimeException if b == 0
     * @see #floorMod(int, int)
     */
    public static int floorDiv(final int a, final int b) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Finds q such that {@code a = q b + r} with {@code 0 <= r < b} if {@code b > 0} and {@code b < r <= 0} if {@code  b < 0}.
     * <p>
     * This methods returns the same value as integer division when
     * a and b are same signs, but returns a different value when
     * they are opposite (i.e. q is negative).
     *
     * @param a dividend
     * @param b divisor
     * @return q such that {@code a = q b + r} with {@code 0 <= r < b} if {@code b > 0} and {@code b < r <= 0} if {@code  b < 0}
     * @exception MathRuntimeException if b == 0 or if a == {@code Integer.MIN_VALUE} and b = -1
     * @see #floorMod(int, int)
     * @since 3.0
     */
    public static int floorDivExact(final int a, final int b) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Finds q such that {@code a = q b + r} with {@code 0 <= r < b} if {@code b > 0} and {@code b < r <= 0} if {@code b < 0}.
     * <p>
     * This methods returns the same value as integer division when
     * a and b are same signs, but returns a different value when
     * they are opposite (i.e. q is negative).
     *
     * @param a dividend
     * @param b divisor
     * @return q such that {@code a = q b + r} with {@code 0 <= r < b} if {@code b > 0} and {@code b < r <= 0} if {@code b < 0}
     * @exception MathRuntimeException if b == 0
     * @see #floorMod(long, int)
     * @since 1.3
     */
    public static long floorDiv(final long a, final int b) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Finds q such that {@code a = q b + r} with {@code 0 <= r < b} if {@code b > 0} and {@code b < r <= 0} if {@code b < 0}.
     * <p>
     * This methods returns the same value as integer division when
     * a and b are same signs, but returns a different value when
     * they are opposite (i.e. q is negative).
     *
     * @param a dividend
     * @param b divisor
     * @return q such that {@code a = q b + r} with {@code 0 <= r < b} if {@code b > 0} and {@code b < r <= 0} if {@code b < 0}
     * @exception MathRuntimeException if b == 0
     * @see #floorMod(long, long)
     */
    public static long floorDiv(final long a, final long b) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Finds q such that {@code a = q b + r} with {@code 0 <= r < b} if {@code b > 0} and {@code b < r <= 0} if {@code b < 0}.
     * <p>
     * This methods returns the same value as integer division when
     * a and b are same signs, but returns a different value when
     * they are opposite (i.e. q is negative).
     *
     * @param a dividend
     * @param b divisor
     * @return q such that {@code a = q b + r} with {@code 0 <= r < b} if {@code b > 0} and {@code b < r <= 0} if {@code b < 0}
     * @exception MathRuntimeException if b == 0 or if a == {@code Long.MIN_VALUE} and b = -1
     * @see #floorMod(long, long)
     * @since 3.0
     */
    public static long floorDivExact(final long a, final long b) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Finds r such that {@code a = q b + r} with {@code 0 <= r < b} if {@code b > 0} and {@code b < r <= 0} if {@code b < 0}.
     * <p>
     * This methods returns the same value as integer modulo when
     * a and b are same signs, but returns a different value when
     * they are opposite (i.e. q is negative).
     * </p>
     * @param a dividend
     * @param b divisor
     * @return r such that {@code a = q b + r} with {@code 0 <= r < b} if {@code b > 0} and {@code b < r <= 0} if {@code b < 0}
     * @exception MathRuntimeException if b == 0
     * @see #floorDiv(int, int)
     */
    public static int floorMod(final int a, final int b) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Finds r such that {@code a = q b + r} with {@code 0 <= r < b} if {@code b > 0} and {@code b < r <= 0} if {@code b < 0}.
     * <p>
     * This methods returns the same value as integer modulo when
     * a and b are same signs, but returns a different value when
     * they are opposite (i.e. q is negative).
     * </p>
     * @param a dividend
     * @param b divisor
     * @return r such that {@code a = q b + r} with {@code 0 <= r < b} if {@code b > 0} and {@code b < r <= 0} if {@code b < 0}
     * @exception MathRuntimeException if b == 0
     * @see #floorDiv(long, int)
     * @since 1.3
     */
    public static int floorMod(final long a, final int b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Finds r such that {@code a = q b + r} with {@code 0 <= r < b} if {@code b > 0} and {@code b < r <= 0} if {@code b < 0}.
     * <p>
     * This methods returns the same value as integer modulo when
     * a and b are same signs, but returns a different value when
     * they are opposite (i.e. q is negative).
     * </p>
     * @param a dividend
     * @param b divisor
     * @return r such that {@code a = q b + r} with {@code 0 <= r < b} if {@code b > 0} and {@code b < r <= 0} if {@code b < 0}
     * @exception MathRuntimeException if b == 0
     * @see #floorDiv(long, long)
     */
    public static long floorMod(final long a, final long b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the first argument with the sign of the second argument.
     * A NaN {@code sign} argument is treated as positive.
     *
     * @param magnitude the value to return
     * @param sign the sign for the returned value
     * @return the magnitude with the same sign as the {@code sign} argument
     */
    public static double copySign(double magnitude, double sign) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the first argument with the sign of the second argument.
     * A NaN {@code sign} argument is treated as positive.
     *
     * @param magnitude the value to return
     * @param sign the sign for the returned value
     * @return the magnitude with the same sign as the {@code sign} argument
     */
    public static float copySign(float magnitude, float sign) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Return the exponent of a double number, removing the bias.
     * <p>
     * For double numbers of the form 2<sup>x</sup>, the unbiased
     * exponent is exactly x.
     * </p>
     * @param d number from which exponent is requested
     * @return exponent for d in IEEE754 representation, without bias
     */
    public static int getExponent(final double d) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Return the exponent of a float number, removing the bias.
     * <p>
     * For float numbers of the form 2<sup>x</sup>, the unbiased
     * exponent is exactly x.
     * </p>
     * @param f number from which exponent is requested
     * @return exponent for d in IEEE754 representation, without bias
     */
    public static int getExponent(final float f) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute Fused-multiply-add operation a * b + c.
     * <p>
     * This method was introduced in the regular {@code Math} and {@code StrictMath}
     * methods with Java 9, and then added to Hipparchus for consistency. However,
     * a more general method was available in Hipparchus that also allow to repeat
     * this computation across several terms: {@link MathArrays#linearCombination(double[], double[])}.
     * The linear combination method should probably be preferred in most cases.
     * </p>
     * @param a first factor
     * @param b second factor
     * @param c additive term
     * @return a * b + c, using extended precision in the multiplication
     * @see MathArrays#linearCombination(double[], double[])
     * @see MathArrays#linearCombination(double, double, double, double)
     * @see MathArrays#linearCombination(double, double, double, double, double, double)
     * @see MathArrays#linearCombination(double, double, double, double, double, double, double, double)
     * @since 1.3
     */
    public static double fma(final double a, final double b, final double c) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute Fused-multiply-add operation a * b + c.
     * <p>
     * This method was introduced in the regular {@code Math} and {@code StrictMath}
     * methods with Java 9, and then added to Hipparchus for consistency. However,
     * a more general method was available in Hipparchus that also allow to repeat
     * this computation across several terms: {@link MathArrays#linearCombination(double[], double[])}.
     * The linear combination method should probably be preferred in most cases.
     * </p>
     * @param a first factor
     * @param b second factor
     * @param c additive term
     * @return a * b + c, using extended precision in the multiplication
     * @see MathArrays#linearCombination(double[], double[])
     * @see MathArrays#linearCombination(double, double, double, double)
     * @see MathArrays#linearCombination(double, double, double, double, double, double)
     * @see MathArrays#linearCombination(double, double, double, double, double, double, double, double)
     */
    public static float fma(final float a, final float b, final float c) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the square root of a number.
     * @param a number on which evaluation is done
     * @param <T> the type of the field element
     * @return square root of a
     * @since 1.3
     */
    public static <T extends CalculusFieldElement<T>> T sqrt(final T a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the hyperbolic cosine of a number.
     * @param x number on which evaluation is done
     * @param <T> the type of the field element
     * @return hyperbolic cosine of x
     * @since 1.3
     */
    public static <T extends CalculusFieldElement<T>> T cosh(final T x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the hyperbolic sine of a number.
     * @param x number on which evaluation is done
     * @param <T> the type of the field element
     * @return hyperbolic sine of x
     * @since 1.3
     */
    public static <T extends CalculusFieldElement<T>> T sinh(final T x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the hyperbolic tangent of a number.
     * @param x number on which evaluation is done
     * @param <T> the type of the field element
     * @return hyperbolic tangent of x
     * @since 1.3
     */
    public static <T extends CalculusFieldElement<T>> T tanh(final T x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the inverse hyperbolic cosine of a number.
     * @param a number on which evaluation is done
     * @param <T> the type of the field element
     * @return inverse hyperbolic cosine of a
     * @since 1.3
     */
    public static <T extends CalculusFieldElement<T>> T acosh(final T a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the inverse hyperbolic sine of a number.
     * @param a number on which evaluation is done
     * @param <T> the type of the field element
     * @return inverse hyperbolic sine of a
     * @since 1.3
     */
    public static <T extends CalculusFieldElement<T>> T asinh(final T a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the inverse hyperbolic tangent of a number.
     * @param a number on which evaluation is done
     * @param <T> the type of the field element
     * @return inverse hyperbolic tangent of a
     * @since 1.3
     */
    public static <T extends CalculusFieldElement<T>> T atanh(final T a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the sign of a number.
     * The sign is -1 for negative numbers, +1 for positive numbers and 0 otherwise,
     * for Complex number, it is extended on the unit circle (equivalent to z/|z|,
     * with special handling for 0 and NaN)
     * @param a number on which evaluation is done
     * @param <T> the type of the field element
     * @return -1.0, -0.0, +0.0, +1.0 or NaN depending on sign of a
     * @since 2.0
     */
    public static <T extends CalculusFieldElement<T>> T sign(final T a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Exponential function.
     *
     * Computes exp(x), function result is nearly rounded.   It will be correctly
     * rounded to the theoretical value for 99.9% of input values, otherwise it will
     * have a 1 ULP error.
     *
     * Method:
     *    Lookup intVal = exp(int(x))
     *    Lookup fracVal = exp(int(x-int(x) / 1024.0) * 1024.0 );
     *    Compute z as the exponential of the remaining bits by a polynomial minus one
     *    exp(x) = intVal * fracVal * (1 + z)
     *
     * Accuracy:
     *    Calculation is done with 63 bits of precision, so result should be correctly
     *    rounded for 99.9% of input values, with less than 1 ULP error otherwise.
     *
     * @param x   a double
     * @param <T> the type of the field element
     * @return double e<sup>x</sup>
     * @since 1.3
     */
    public static <T extends CalculusFieldElement<T>> T exp(final T x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute exp(x) - 1
     * @param x number to compute shifted exponential
     * @param <T> the type of the field element
     * @return exp(x) - 1
     * @since 1.3
     */
    public static <T extends CalculusFieldElement<T>> T expm1(final T x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Natural logarithm.
     *
     * @param x   a double
     * @param <T> the type of the field element
     * @return log(x)
     * @since 1.3
     */
    public static <T extends CalculusFieldElement<T>> T log(final T x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Computes log(1 + x).
     *
     * @param x Number.
     * @param <T> the type of the field element
     * @return {@code log(1 + x)}.
     * @since 1.3
     */
    public static <T extends CalculusFieldElement<T>> T log1p(final T x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the base 10 logarithm.
     * @param x a number
     * @param <T> the type of the field element
     * @return log10(x)
     * @since 1.3
     */
    public static <T extends CalculusFieldElement<T>> T log10(final T x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Power function.  Compute x<sup>y</sup>.
     *
     * @param x   a double
     * @param y   a double
     * @param <T> the type of the field element
     * @return x<sup>y</sup>
     * @since 1.3
     */
    public static <T extends CalculusFieldElement<T>> T pow(final T x, final T y) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Power function.  Compute x<sup>y</sup>.
     *
     * @param x   a double
     * @param y   a double
     * @param <T> the type of the field element
     * @return x<sup>y</sup>
     * @since 1.7
     */
    public static <T extends CalculusFieldElement<T>> T pow(final T x, final double y) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Raise a double to an int power.
     *
     * @param d Number to raise.
     * @param e Exponent.
     * @param <T> the type of the field element
     * @return d<sup>e</sup>
     * @since 1.3
     */
    public static <T extends CalculusFieldElement<T>> T pow(T d, int e) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Sine function.
     *
     * @param x Argument.
     * @param <T> the type of the field element
     * @return sin(x)
     * @since 1.3
     */
    public static <T extends CalculusFieldElement<T>> T sin(final T x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Cosine function.
     *
     * @param x Argument.
     * @param <T> the type of the field element
     * @return cos(x)
     * @since 1.3
     */
    public static <T extends CalculusFieldElement<T>> T cos(final T x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Tangent function.
     *
     * @param x Argument.
     * @param <T> the type of the field element
     * @return tan(x)
     * @since 1.3
     */
    public static <T extends CalculusFieldElement<T>> T tan(final T x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Arctangent function
     *  @param x a number
     * @param <T> the type of the field element
     *  @return atan(x)
     * @since 1.3
     */
    public static <T extends CalculusFieldElement<T>> T atan(final T x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Two arguments arctangent function
     * @param y ordinate
     * @param x abscissa
     * @param <T> the type of the field element
     * @return phase angle of point (x,y) between {@code -PI} and {@code PI}
     * @since 1.3
     */
    public static <T extends CalculusFieldElement<T>> T atan2(final T y, final T x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the arc sine of a number.
     * @param x number on which evaluation is done
     * @param <T> the type of the field element
     * @return arc sine of x
     * @since 1.3
     */
    public static <T extends CalculusFieldElement<T>> T asin(final T x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the arc cosine of a number.
     * @param x number on which evaluation is done
     * @param <T> the type of the field element
     * @return arc cosine of x
     * @since 1.3
     */
    public static <T extends CalculusFieldElement<T>> T acos(final T x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the cubic root of a number.
     * @param x number on which evaluation is done
     * @param <T> the type of the field element
     * @return cubic root of x
     * @since 1.3
     */
    public static <T extends CalculusFieldElement<T>> T cbrt(final T x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Norm.
     * @param x number from which norm is requested
     * @param <T> the type of the field element
     * @return norm(x)
     * @since 2.0
     */
    public static <T extends CalculusFieldElement<T>> double norm(final T x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Absolute value.
     * @param x number from which absolute value is requested
     * @param <T> the type of the field element
     * @return abs(x)
     * @since 2.0
     */
    public static <T extends CalculusFieldElement<T>> T abs(final T x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *  Convert degrees to radians, with error of less than 0.5 ULP
     *  @param x angle in degrees
     *  @param <T> the type of the field element
     *  @return x converted into radians
     */
    public static <T extends CalculusFieldElement<T>> T toRadians(T x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *  Convert radians to degrees, with error of less than 0.5 ULP
     *  @param x angle in radians
     *  @param <T> the type of the field element
     *  @return x converted into degrees
     */
    public static <T extends CalculusFieldElement<T>> T toDegrees(T x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Multiply a double number by a power of 2.
     * @param d number to multiply
     * @param n power of 2
     * @param <T> the type of the field element
     * @return d &times; 2<sup>n</sup>
     * @since 1.3
     */
    public static <T extends CalculusFieldElement<T>> T scalb(final T d, final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute least significant bit (Unit in Last Position) for a number.
     * @param x number from which ulp is requested
     * @param <T> the type of the field element
     * @return ulp(x)
     * @since 2.0
     */
    public static <T extends CalculusFieldElement<T>> T ulp(final T x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the largest whole number smaller than x.
     * @param x number from which floor is requested
     * @param <T> the type of the field element
     * @return a double number f such that f is an integer f &lt;= x &lt; f + 1.0
     * @since 1.3
     */
    public static <T extends CalculusFieldElement<T>> T floor(final T x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the smallest whole number larger than x.
     * @param x number from which ceil is requested
     * @param <T> the type of the field element
     * @return a double number c such that c is an integer c - 1.0 &lt; x &lt;= c
     * @since 1.3
     */
    public static <T extends CalculusFieldElement<T>> T ceil(final T x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the whole number that is the nearest to x, or the even one if x is exactly half way between two integers.
     * @param x number from which nearest whole number is requested
     * @param <T> the type of the field element
     * @return a double number r such that r is an integer r - 0.5 &lt;= x &lt;= r + 0.5
     * @since 1.3
     */
    public static <T extends CalculusFieldElement<T>> T rint(final T x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the closest long to x.
     * @param x number from which closest long is requested
     * @param <T> the type of the field element
     * @return closest long to x
     * @since 1.3
     */
    public static <T extends CalculusFieldElement<T>> long round(final T x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the minimum of two values
     * @param a first value
     * @param b second value
     * @param <T> the type of the field element
     * @return a if a is lesser or equal to b, b otherwise
     * @since 1.3
     */
    public static <T extends CalculusFieldElement<T>> T min(final T a, final T b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the minimum of two values
     * @param a first value
     * @param b second value
     * @param <T> the type of the field element
     * @return a if a is lesser or equal to b, b otherwise
     * @since 1.3
     */
    public static <T extends CalculusFieldElement<T>> T min(final T a, final double b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the maximum of two values
     * @param a first value
     * @param b second value
     * @param <T> the type of the field element
     * @return b if a is lesser or equal to b, a otherwise
     * @since 1.3
     */
    public static <T extends CalculusFieldElement<T>> T max(final T a, final T b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the maximum of two values
     * @param a first value
     * @param b second value
     * @param <T> the type of the field element
     * @return b if a is lesser or equal to b, a otherwise
     * @since 1.3
     */
    public static <T extends CalculusFieldElement<T>> T max(final T a, final double b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the hypotenuse of a triangle with sides {@code x} and {@code y}
     * - sqrt(<i>x</i><sup>2</sup>&nbsp;+<i>y</i><sup>2</sup>)<br>
     * avoiding intermediate overflow or underflow.
     *
     * <ul>
     * <li> If either argument is infinite, then the result is positive infinity.</li>
     * <li> else, if either argument is NaN then the result is NaN.</li>
     * </ul>
     *
     * @param x a value
     * @param y a value
     * @param <T> the type of the field element
     * @return sqrt(<i>x</i><sup>2</sup>&nbsp;+<i>y</i><sup>2</sup>)
     * @since 1.3
     */
    public static <T extends CalculusFieldElement<T>> T hypot(final T x, final T y) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Computes the remainder as prescribed by the IEEE 754 standard.
     * <p>
     * The remainder value is mathematically equal to {@code x - y*n}
     * where {@code n} is the mathematical integer closest to the exact mathematical value
     * of the quotient {@code x/y}.
     * If two mathematical integers are equally close to {@code x/y} then
     * {@code n} is the integer that is even.
     * </p>
     * <ul>
     * <li>If either operand is NaN, the result is NaN.</li>
     * <li>If the result is not NaN, the sign of the result equals the sign of the dividend.</li>
     * <li>If the dividend is an infinity, or the divisor is a zero, or both, the result is NaN.</li>
     * <li>If the dividend is finite and the divisor is an infinity, the result equals the dividend.</li>
     * <li>If the dividend is a zero and the divisor is finite, the result equals the dividend.</li>
     * </ul>
     * @param dividend the number to be divided
     * @param divisor the number by which to divide
     * @param <T> the type of the field element
     * @return the remainder, rounded
     * @since 1.3
     */
    public static <T extends CalculusFieldElement<T>> T IEEEremainder(final T dividend, final double divisor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Computes the remainder as prescribed by the IEEE 754 standard.
     * <p>
     * The remainder value is mathematically equal to {@code x - y*n}
     * where {@code n} is the mathematical integer closest to the exact mathematical value
     * of the quotient {@code x/y}.
     * If two mathematical integers are equally close to {@code x/y} then
     * {@code n} is the integer that is even.
     * </p>
     * <ul>
     * <li>If either operand is NaN, the result is NaN.</li>
     * <li>If the result is not NaN, the sign of the result equals the sign of the dividend.</li>
     * <li>If the dividend is an infinity, or the divisor is a zero, or both, the result is NaN.</li>
     * <li>If the dividend is finite and the divisor is an infinity, the result equals the dividend.</li>
     * <li>If the dividend is a zero and the divisor is finite, the result equals the dividend.</li>
     * </ul>
     * @param dividend the number to be divided
     * @param divisor the number by which to divide
     * @param <T> the type of the field element
     * @return the remainder, rounded
     * @since 1.3
     */
    public static <T extends CalculusFieldElement<T>> T IEEEremainder(final T dividend, final T divisor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the first argument with the sign of the second argument.
     * A NaN {@code sign} argument is treated as positive.
     *
     * @param magnitude the value to return
     * @param sign the sign for the returned value
     * @param <T> the type of the field element
     * @return the magnitude with the same sign as the {@code sign} argument
     * @since 1.3
     */
    public static <T extends CalculusFieldElement<T>> T copySign(T magnitude, T sign) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the first argument with the sign of the second argument.
     * A NaN {@code sign} argument is treated as positive.
     *
     * @param magnitude the value to return
     * @param sign the sign for the returned value
     * @param <T> the type of the field element
     * @return the magnitude with the same sign as the {@code sign} argument
     * @since 1.3
     */
    public static <T extends CalculusFieldElement<T>> T copySign(T magnitude, double sign) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    //    /**
    //     * Print out contents of arrays, and check the length.
    //     * <p>used to generate the preset arrays originally.</p>
    //     * @param a unused
    //     */
    //    public static void main(String[] a) {
    //        FastMathCalc.printarray(System.out, "EXP_INT_TABLE_A", EXP_INT_TABLE_LEN, ExpIntTable.EXP_INT_TABLE_A);
    //        FastMathCalc.printarray(System.out, "EXP_INT_TABLE_B", EXP_INT_TABLE_LEN, ExpIntTable.EXP_INT_TABLE_B);
    //        FastMathCalc.printarray(System.out, "EXP_FRAC_TABLE_A", EXP_FRAC_TABLE_LEN, ExpFracTable.EXP_FRAC_TABLE_A);
    //        FastMathCalc.printarray(System.out, "EXP_FRAC_TABLE_B", EXP_FRAC_TABLE_LEN, ExpFracTable.EXP_FRAC_TABLE_B);
    //        FastMathCalc.printarray(System.out, "LN_MANT",LN_MANT_LEN, lnMant.LN_MANT);
    //        FastMathCalc.printarray(System.out, "SINE_TABLE_A", SINE_TABLE_LEN, SINE_TABLE_A);
    //        FastMathCalc.printarray(System.out, "SINE_TABLE_B", SINE_TABLE_LEN, SINE_TABLE_B);
    //        FastMathCalc.printarray(System.out, "COSINE_TABLE_A", SINE_TABLE_LEN, COSINE_TABLE_A);
    //        FastMathCalc.printarray(System.out, "COSINE_TABLE_B", SINE_TABLE_LEN, COSINE_TABLE_B);
    //        FastMathCalc.printarray(System.out, "TANGENT_TABLE_A", SINE_TABLE_LEN, TANGENT_TABLE_A);
    //        FastMathCalc.printarray(System.out, "TANGENT_TABLE_B", SINE_TABLE_LEN, TANGENT_TABLE_B);
    //    }
    /**
     * Enclose large data table in nested static class so it's only loaded on first access.
     */
    private static class ExpIntTable {

        /**
         * Exponential evaluated at integer values,
         * exp(x) =  expIntTableA[x + EXP_INT_TABLE_MAX_INDEX] + expIntTableB[x+EXP_INT_TABLE_MAX_INDEX].
         */
        private static final double[] EXP_INT_TABLE_A;

        /**
         * Exponential evaluated at integer values,
         * exp(x) =  expIntTableA[x + EXP_INT_TABLE_MAX_INDEX] + expIntTableB[x+EXP_INT_TABLE_MAX_INDEX]
         */
        private static final double[] EXP_INT_TABLE_B;

        static {
            if (RECOMPUTE_TABLES_AT_RUNTIME) {
                EXP_INT_TABLE_A = new double[EXP_INT_TABLE_LEN];
                EXP_INT_TABLE_B = new double[EXP_INT_TABLE_LEN];
                final double[] tmp = new double[2];
                final double[] recip = new double[2];
                // Populate expIntTable
                for (int i = 0; i < EXP_INT_TABLE_MAX_INDEX; i++) {
                    FastMathCalc.expint(i, tmp);
                    EXP_INT_TABLE_A[i + EXP_INT_TABLE_MAX_INDEX] = tmp[0];
                    EXP_INT_TABLE_B[i + EXP_INT_TABLE_MAX_INDEX] = tmp[1];
                    if (i != 0) {
                        // Negative integer powers
                        FastMathCalc.splitReciprocal(tmp, recip);
                        EXP_INT_TABLE_A[EXP_INT_TABLE_MAX_INDEX - i] = recip[0];
                        EXP_INT_TABLE_B[EXP_INT_TABLE_MAX_INDEX - i] = recip[1];
                    }
                }
            } else {
                EXP_INT_TABLE_A = FastMathLiteralArrays.loadExpIntA();
                EXP_INT_TABLE_B = FastMathLiteralArrays.loadExpIntB();
            }
        }
    }

    /**
     * Enclose large data table in nested static class so it's only loaded on first access.
     */
    private static class ExpFracTable {

        /**
         * Exponential over the range of 0 - 1 in increments of 2^-10
         * exp(x/1024) =  expFracTableA[x] + expFracTableB[x].
         * 1024 = 2^10
         */
        private static final double[] EXP_FRAC_TABLE_A;

        /**
         * Exponential over the range of 0 - 1 in increments of 2^-10
         * exp(x/1024) =  expFracTableA[x] + expFracTableB[x].
         */
        private static final double[] EXP_FRAC_TABLE_B;

        static {
            if (RECOMPUTE_TABLES_AT_RUNTIME) {
                EXP_FRAC_TABLE_A = new double[EXP_FRAC_TABLE_LEN];
                EXP_FRAC_TABLE_B = new double[EXP_FRAC_TABLE_LEN];
                final double[] tmp = new double[2];
                // Populate expFracTable
                final double factor = 1d / (EXP_FRAC_TABLE_LEN - 1);
                for (int i = 0; i < EXP_FRAC_TABLE_A.length; i++) {
                    FastMathCalc.slowexp(i * factor, tmp);
                    EXP_FRAC_TABLE_A[i] = tmp[0];
                    EXP_FRAC_TABLE_B[i] = tmp[1];
                }
            } else {
                EXP_FRAC_TABLE_A = FastMathLiteralArrays.loadExpFracA();
                EXP_FRAC_TABLE_B = FastMathLiteralArrays.loadExpFracB();
            }
        }
    }

    /**
     * Enclose large data table in nested static class so it's only loaded on first access.
     */
    private static class lnMant {

        /**
         * Extended precision logarithm table over the range 1 - 2 in increments of 2^-10.
         */
        private static final double[][] LN_MANT;

        static {
            if (RECOMPUTE_TABLES_AT_RUNTIME) {
                LN_MANT = new double[LN_MANT_LEN][];
                // Populate lnMant table
                for (int i = 0; i < LN_MANT.length; i++) {
                    final double d = Double.longBitsToDouble((((long) i) << 42) | 0x3ff0000000000000L);
                    LN_MANT[i] = FastMathCalc.slowLog(d);
                }
            } else {
                LN_MANT = FastMathLiteralArrays.loadLnMant();
            }
        }
    }

    /**
     * Enclose the Cody/Waite reduction (used in "sin", "cos" and "tan").
     */
    private static class CodyWaite {

        /**
         * k
         */
        private final int finalK;

        /**
         * remA
         */
        private final double finalRemA;

        /**
         * remB
         */
        private final double finalRemB;

        /**
         * @param xa Argument.
         */
        CodyWaite(double xa) {
            // Estimate k.
            //k = (int)(xa / 1.5707963267948966);
            int k = (int) (xa * 0.6366197723675814);
            // Compute remainder.
            double remA;
            double remB;
            while (true) {
                double a = -k * 1.570796251296997;
                remA = xa + a;
                remB = -(remA - xa - a);
                a = -k * 7.549789948768648E-8;
                double b = remA;
                remA = a + b;
                remB += -(remA - b - a);
                a = -k * 6.123233995736766E-17;
                b = remA;
                remA = a + b;
                remB += -(remA - b - a);
                if (remA > 0) {
                    break;
                }
                // Remainder is negative, so decrement k and try again.
                // This should only happen if the input is very close
                // to an even multiple of pi/2.
                --k;
            }
            this.finalK = k;
            this.finalRemA = remA;
            this.finalRemB = remB;
        }

        /**
         * @return k
         */
        int getK() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * @return remA
         */
        double getRemA() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * @return remB
         */
        double getRemB() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
