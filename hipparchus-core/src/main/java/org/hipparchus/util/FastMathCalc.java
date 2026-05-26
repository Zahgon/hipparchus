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

import java.io.PrintStream;

/**
 * Class used to compute the classical functions tables.
 */
class FastMathCalc {

    /**
     * 0x40000000 - used to split a double into two parts, both with the low order bits cleared.
     * Equivalent to 2^30.
     */
    // 1073741824L
    private static final long HEX_40000000 = 0x40000000L;

    /**
     * Factorial table, for Taylor series expansions. 0!, 1!, 2!, ... 19!
     */
    private static final double[] FACT = { // 0
    +1.0d, // 1
    +1.0d, // 2
    +2.0d, // 3
    +6.0d, // 4
    +24.0d, // 5
    +120.0d, // 6
    +720.0d, // 7
    +5040.0d, // 8
    +40320.0d, // 9
    +362880.0d, // 10
    +3628800.0d, // 11
    +39916800.0d, // 12
    +479001600.0d, // 13
    +6227020800.0d, // 14
    +87178291200.0d, // 15
    +1307674368000.0d, // 16
    +20922789888000.0d, // 17
    +355687428096000.0d, // 18
    +6402373705728000.0d, // 19
    +121645100408832000.0d };

    /**
     * Coefficients for slowLog.
     */
    private static final double[][] LN_SPLIT_COEF = { { 2.0, 0.0 }, { 0.6666666269302368, 3.9736429850260626E-8 }, { 0.3999999761581421, 2.3841857910019882E-8 }, { 0.2857142686843872, 1.7029898543501842E-8 }, { 0.2222222089767456, 1.3245471311735498E-8 }, { 0.1818181574344635, 2.4384203044354907E-8 }, { 0.1538461446762085, 9.140260083262505E-9 }, { 0.13333332538604736, 9.220590270857665E-9 }, { 0.11764700710773468, 1.2393345855018391E-8 }, { 0.10526403784751892, 8.251545029714408E-9 }, { 0.0952233225107193, 1.2675934823758863E-8 }, { 0.08713622391223907, 1.1430250008909141E-8 }, { 0.07842259109020233, 2.404307984052299E-9 }, { 0.08371849358081818, 1.176342548272881E-8 }, { 0.030589580535888672, 1.2958646899018938E-9 }, { 0.14982303977012634, 1.225743062930824E-8 } };

    /**
     * Table start declaration.
     */
    private static final String TABLE_START_DECL = "    {";

    /**
     * Table end declaration.
     */
    private static final String TABLE_END_DECL = "    };";

    /**
     * Private Constructor.
     */
    private FastMathCalc() {
    }

    /**
     * Build the sine and cosine tables.
     * @param SINE_TABLE_A table of the most significant part of the sines
     * @param SINE_TABLE_B table of the least significant part of the sines
     * @param COSINE_TABLE_A table of the most significant part of the cosines
     * @param COSINE_TABLE_B table of the most significant part of the cosines
     * @param SINE_TABLE_LEN length of the tables
     * @param TANGENT_TABLE_A table of the most significant part of the tangents
     * @param TANGENT_TABLE_B table of the most significant part of the tangents
     */
    @SuppressWarnings("unused")
    private static void buildSinCosTables(double[] SINE_TABLE_A, double[] SINE_TABLE_B, double[] COSINE_TABLE_A, double[] COSINE_TABLE_B, int SINE_TABLE_LEN, double[] TANGENT_TABLE_A, double[] TANGENT_TABLE_B) {
        final double[] result = new double[2];
        /* Use taylor series for 0 <= x <= 6/8 */
        for (int i = 0; i < 7; i++) {
            double x = i / 8.0;
            slowSin(x, result);
            SINE_TABLE_A[i] = result[0];
            SINE_TABLE_B[i] = result[1];
            slowCos(x, result);
            COSINE_TABLE_A[i] = result[0];
            COSINE_TABLE_B[i] = result[1];
        }
        /* Use angle addition formula to complete table to 13/8, just beyond pi/2 */
        for (int i = 7; i < SINE_TABLE_LEN; i++) {
            double[] xs = new double[2];
            double[] ys = new double[2];
            double[] as = new double[2];
            double[] bs = new double[2];
            double[] temps = new double[2];
            if ((i & 1) == 0) {
                // Even, use double angle
                xs[0] = SINE_TABLE_A[i / 2];
                xs[1] = SINE_TABLE_B[i / 2];
                ys[0] = COSINE_TABLE_A[i / 2];
                ys[1] = COSINE_TABLE_B[i / 2];
                /* compute sine */
                splitMult(xs, ys, result);
                SINE_TABLE_A[i] = result[0] * 2.0;
                SINE_TABLE_B[i] = result[1] * 2.0;
                /* Compute cosine */
                splitMult(ys, ys, as);
                splitMult(xs, xs, temps);
                temps[0] = -temps[0];
                temps[1] = -temps[1];
                splitAdd(as, temps, result);
                COSINE_TABLE_A[i] = result[0];
                COSINE_TABLE_B[i] = result[1];
            } else {
                xs[0] = SINE_TABLE_A[i / 2];
                xs[1] = SINE_TABLE_B[i / 2];
                ys[0] = COSINE_TABLE_A[i / 2];
                ys[1] = COSINE_TABLE_B[i / 2];
                as[0] = SINE_TABLE_A[i / 2 + 1];
                as[1] = SINE_TABLE_B[i / 2 + 1];
                bs[0] = COSINE_TABLE_A[i / 2 + 1];
                bs[1] = COSINE_TABLE_B[i / 2 + 1];
                /* compute sine */
                splitMult(xs, bs, temps);
                splitMult(ys, as, result);
                splitAdd(result, temps, result);
                SINE_TABLE_A[i] = result[0];
                SINE_TABLE_B[i] = result[1];
                /* Compute cosine */
                splitMult(ys, bs, result);
                splitMult(xs, as, temps);
                temps[0] = -temps[0];
                temps[1] = -temps[1];
                splitAdd(result, temps, result);
                COSINE_TABLE_A[i] = result[0];
                COSINE_TABLE_B[i] = result[1];
            }
        }
        /* Compute tangent = sine/cosine */
        for (int i = 0; i < SINE_TABLE_LEN; i++) {
            double[] xs = new double[2];
            double[] ys = new double[2];
            double[] as = new double[2];
            as[0] = COSINE_TABLE_A[i];
            as[1] = COSINE_TABLE_B[i];
            splitReciprocal(as, ys);
            xs[0] = SINE_TABLE_A[i];
            xs[1] = SINE_TABLE_B[i];
            splitMult(xs, ys, as);
            TANGENT_TABLE_A[i] = as[0];
            TANGENT_TABLE_B[i] = as[1];
        }
    }

    /**
     *  For x between 0 and pi/4 compute cosine using Talor series
     *  cos(x) = 1 - x^2/2! + x^4/4! ...
     * @param x number from which cosine is requested
     * @param result placeholder where to put the result in extended precision
     * (may be null)
     * @return cos(x)
     */
    static double slowCos(final double x, final double[] result) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * For x between 0 and pi/4 compute sine using Taylor expansion:
     * sin(x) = x - x^3/3! + x^5/5! - x^7/7! ...
     * @param x number from which sine is requested
     * @param result placeholder where to put the result in extended precision
     * (may be null)
     * @return sin(x)
     */
    static double slowSin(final double x, final double[] result) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *  For x between 0 and 1, returns exp(x), uses extended precision
     *  @param x argument of exponential
     *  @param result placeholder where to place exp(x) split in two terms
     *  for extra precision (i.e. exp(x) = result[0] + result[1]
     *  @return exp(x)
     */
    static double slowexp(final double x, final double[] result) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute split[0], split[1] such that their sum is equal to d,
     * and split[0] has its 30 least significant bits as zero.
     * @param d number to split
     * @param split placeholder where to place the result
     */
    private static void split(final double d, final double[] split) {
        if (d < 8e298 && d > -8e298) {
            final double a = d * HEX_40000000;
            split[0] = (d + a) - a;
            split[1] = d - split[0];
        } else {
            final double a = d * 9.31322574615478515625E-10;
            split[0] = (d + a - d) * HEX_40000000;
            split[1] = d - split[0];
        }
    }

    /**
     * Recompute a split.
     * @param a input/out array containing the split, changed
     * on output
     */
    private static void resplit(final double[] a) {
        final double c = a[0] + a[1];
        final double d = -(c - a[0] - a[1]);
        if (c < 8e298 && c > -8e298) {
            // MAGIC NUMBER
            double z = c * HEX_40000000;
            a[0] = (c + z) - z;
            a[1] = c - a[0] + d;
        } else {
            double z = c * 9.31322574615478515625E-10;
            a[0] = (c + z - c) * HEX_40000000;
            a[1] = c - a[0] + d;
        }
    }

    /**
     * Multiply two numbers in split form.
     * @param a first term of multiplication
     * @param b second term of multiplication
     * @param ans placeholder where to put the result
     */
    private static void splitMult(double[] a, double[] b, double[] ans) {
        ans[0] = a[0] * b[0];
        ans[1] = a[0] * b[1] + a[1] * b[0] + a[1] * b[1];
        /* Resplit */
        resplit(ans);
    }

    /**
     * Add two numbers in split form.
     * @param a first term of addition
     * @param b second term of addition
     * @param ans placeholder where to put the result
     */
    private static void splitAdd(final double[] a, final double[] b, final double[] ans) {
        ans[0] = a[0] + b[0];
        ans[1] = a[1] + b[1];
        resplit(ans);
    }

    /**
     * Compute the reciprocal of in.  Use the following algorithm.
     *  in = c + d.
     *  want to find x + y such that x+y = 1/(c+d) and x is much
     *  larger than y and x has several zero bits on the right.
     *
     *  Set b = 1/(2^22),  a = 1 - b.  Thus (a+b) = 1.
     *  Use following identity to compute (a+b)/(c+d)
     *
     *  (a+b)/(c+d)  =   a/c   +    (bc - ad) / (c^2 + cd)
     *  set x = a/c  and y = (bc - ad) / (c^2 + cd)
     *  This will be close to the right answer, but there will be
     *  some rounding in the calculation of X.  So by carefully
     *  computing 1 - (c+d)(x+y) we can compute an error and
     *  add that back in.   This is done carefully so that terms
     *  of similar size are subtracted first.
     *  @param in initial number, in split form
     *  @param result placeholder where to put the result
     */
    static void splitReciprocal(final double[] in, final double[] result) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute (a[0] + a[1]) * (b[0] + b[1]) in extended precision.
     * @param a first term of the multiplication
     * @param b second term of the multiplication
     * @param result placeholder where to put the result
     */
    private static void quadMult(final double[] a, final double[] b, final double[] result) {
        final double[] xs = new double[2];
        final double[] ys = new double[2];
        final double[] zs = new double[2];
        /* a[0] * b[0] */
        split(a[0], xs);
        split(b[0], ys);
        splitMult(xs, ys, zs);
        result[0] = zs[0];
        result[1] = zs[1];
        /* a[0] * b[1] */
        split(b[1], ys);
        splitMult(xs, ys, zs);
        double tmp = result[0] + zs[0];
        result[1] -= tmp - result[0] - zs[0];
        result[0] = tmp;
        tmp = result[0] + zs[1];
        result[1] -= tmp - result[0] - zs[1];
        result[0] = tmp;
        /* a[1] * b[0] */
        split(a[1], xs);
        split(b[0], ys);
        splitMult(xs, ys, zs);
        tmp = result[0] + zs[0];
        result[1] -= tmp - result[0] - zs[0];
        result[0] = tmp;
        tmp = result[0] + zs[1];
        result[1] -= tmp - result[0] - zs[1];
        result[0] = tmp;
        /* a[1] * b[0] */
        split(a[1], xs);
        split(b[1], ys);
        splitMult(xs, ys, zs);
        tmp = result[0] + zs[0];
        result[1] -= tmp - result[0] - zs[0];
        result[0] = tmp;
        tmp = result[0] + zs[1];
        result[1] -= tmp - result[0] - zs[1];
        result[0] = tmp;
    }

    /**
     * Compute exp(p) for a integer p in extended precision.
     * @param p integer whose exponential is requested
     * @param result placeholder where to put the result in extended precision
     * @return exp(p) in standard precision (equal to result[0] + result[1])
     */
    static double expint(int p, final double[] result) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * xi in the range of [1, 2].
     *                                3        5        7
     *      x+1           /          x        x        x          \
     *  ln ----- =   2 *  |  x  +   ----  +  ----  +  ---- + ...  |
     *      1-x           \          3        5        7          /
     *
     * So, compute a Remez approximation of the following function
     *
     *  ln ((sqrt(x)+1)/(1-sqrt(x)))  /  x
     *
     * This will be an even function with only positive coefficents.
     * x is in the range [0 - 1/3].
     *
     * Transform xi for input to the above function by setting
     * x = (xi-1)/(xi+1).   Input to the polynomial is x^2, then
     * the result is multiplied by x.
     * @param xi number from which log is requested
     * @return log(xi)
     */
    static double[] slowLog(double xi) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Print an array.
     * @param out text output stream where output should be printed
     * @param name array name
     * @param expectedLen expected length of the array
     * @param array2d array data
     */
    static void printarray(PrintStream out, String name, int expectedLen, double[][] array2d) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Print an array.
     * @param out text output stream where output should be printed
     * @param name array name
     * @param expectedLen expected length of the array
     * @param array array data
     */
    static void printarray(PrintStream out, String name, int expectedLen, double[] array) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Format a double.
     * @param d double number to format
     * @return formatted number
     */
    static String format(double d) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
