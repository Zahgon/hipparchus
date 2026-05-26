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
package org.hipparchus.dfp;

/**
 * Mathematical routines for use with {@link Dfp}.
 * The constants are defined in {@link DfpField}
 */
public class DfpMath {

    /**
     * Name for traps triggered by pow.
     */
    private static final String POW_TRAP = "pow";

    /**
     * Private Constructor.
     */
    private DfpMath() {
    }

    /**
     * Breaks a string representation up into two dfp's.
     * <p>The two dfp are such that the sum of them is equivalent
     * to the input string, but has higher precision than using a
     * single dfp. This is useful for improving accuracy of
     * exponentiation and critical multiplies.
     * @param field field to which the Dfp must belong
     * @param a string representation to split
     * @return an array of two {@link Dfp} which sum is a
     */
    protected static Dfp[] split(final DfpField field, final String a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Splits a {@link Dfp} into 2 {@link Dfp}'s such that their sum is equal to the input {@link Dfp}.
     * @param a number to split
     * @return two elements array containing the split number
     */
    protected static Dfp[] split(final Dfp a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Multiply two numbers that are split in to two pieces that are
     *  meant to be added together.
     *  Use binomial multiplication so ab = a0 b0 + a0 b1 + a1 b0 + a1 b1
     *  Store the first term in result0, the rest in result1
     *  @param a first factor of the multiplication, in split form
     *  @param b second factor of the multiplication, in split form
     *  @return a &times; b, in split form
     */
    protected static Dfp[] splitMult(final Dfp[] a, final Dfp[] b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Divide two numbers that are split in to two pieces that are meant to be added together.
     * Inverse of split multiply above:
     *  (a+b) / (c+d) = (a/c) + ( (bc-ad)/(c**2+cd) )
     *  @param a dividend, in split form
     *  @param b divisor, in split form
     *  @return a / b, in split form
     */
    protected static Dfp[] splitDiv(final Dfp[] a, final Dfp[] b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Raise a split base to the a power.
     * @param base number to raise
     * @param a power
     * @return base<sup>a</sup>
     */
    protected static Dfp splitPow(final Dfp[] base, int a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Raises base to the power a by successive squaring.
     * @param base number to raise
     * @param a power
     * @return base<sup>a</sup>
     */
    public static Dfp pow(Dfp base, int a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Computes e to the given power.
     * a is broken into two parts, such that a = n+m  where n is an integer.
     * We use pow() to compute e<sup>n</sup> and a Taylor series to compute
     * e<sup>m</sup>.  We return e*<sup>n</sup> &times; e<sup>m</sup>
     * @param a power at which e should be raised
     * @return e<sup>a</sup>
     */
    public static Dfp exp(final Dfp a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Computes e to the given power.
     * Where -1 &lt; a &lt; 1.  Use the classic Taylor series.  1 + x**2/2! + x**3/3! + x**4/4!  ...
     * @param a power at which e should be raised
     * @return e<sup>a</sup>
     */
    protected static Dfp expInternal(final Dfp a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the natural logarithm of a.
     * a is first split into three parts such that  a = (10000^h)(2^j)k.
     * ln(a) is computed by ln(a) = ln(5)*h + ln(2)*(h+j) + ln(k)
     * k is in the range 2/3 &lt; k &lt; 4/3 and is passed on to a series expansion.
     * @param a number from which logarithm is requested
     * @return log(a)
     */
    public static Dfp log(Dfp a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Computes the natural log of a number between 0 and 2.
     *  Let f(x) = ln(x),
     *
     *  We know that f'(x) = 1/x, thus from Taylor's theorum we have:
     *
     *           -----          n+1         n
     *  f(x) =   \           (-1)    (x - 1)
     *           /          ----------------    for 1 &lt;= n &lt;= infinity
     *           -----             n
     *
     *  or
     *                       2        3       4
     *                   (x-1)   (x-1)    (x-1)
     *  ln(x) =  (x-1) - ----- + ------ - ------ + ...
     *                     2       3        4
     *
     *  alternatively,
     *
     *                  2    3   4
     *                 x    x   x
     *  ln(x+1) =  x - -  + - - - + ...
     *                 2    3   4
     *
     *  This series can be used to compute ln(x), but it converges too slowly.
     *
     *  If we substitute -x for x above, we get
     *
     *                   2    3    4
     *                  x    x    x
     *  ln(1-x) =  -x - -  - -  - - + ...
     *                  2    3    4
     *
     *  Note that all terms are now negative.  Because the even powered ones
     *  absorbed the sign.  Now, subtract the series above from the previous
     *  one to get ln(x+1) - ln(1-x).  Note the even terms cancel out leaving
     *  only the odd ones
     *
     *                             3     5      7
     *                           2x    2x     2x
     *  ln(x+1) - ln(x-1) = 2x + --- + --- + ---- + ...
     *                            3     5      7
     *
     *  By the property of logarithms that ln(a) - ln(b) = ln (a/b) we have:
     *
     *                                3        5        7
     *      x+1           /          x        x        x          \
     *  ln ----- =   2 *  |  x  +   ----  +  ----  +  ---- + ...  |
     *      x-1           \          3        5        7          /
     *
     *  But now we want to find ln(a), so we need to find the value of x
     *  such that a = (x+1)/(x-1).   This is easily solved to find that
     *  x = (a-1)/(a+1).
     * @param a number from which logarithm is requested, in split form
     * @return log(a)
     */
    protected static Dfp[] logInternal(final Dfp[] a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Computes x to the y power.
     *
     *  <p>Uses the following method:</p>
     *
     *  <ol>
     *  <li> Set u = rint(y), v = y-u
     *  <li> Compute a = v * ln(x)
     *  <li> Compute b = rint( a/ln(2) )
     *  <li> Compute c = a - b*ln(2)
     *  <li> x<sup>y</sup> = x<sup>u</sup>  *   2<sup>b</sup> * e<sup>c</sup>
     *  </ol>
     *  if |y| &gt; 1e8, then we compute by exp(y*ln(x))
     *
     *  <p>Special Cases</p>
     *  <ul>
     *  <li>  if y is 0.0 or -0.0 then result is 1.0</li>
     *  <li>  if y is 1.0 then result is x</li>
     *  <li>  if y is NaN then result is NaN</li>
     *  <li>  if x is NaN and y is not zero then result is NaN</li>
     *  <li>  if |x| &gt; 1.0 and y is +Infinity then result is +Infinity</li>
     *  <li>  if |x| &lt; 1.0 and y is -Infinity then result is +Infinity</li>
     *  <li>  if |x| &gt; 1.0 and y is -Infinity then result is +0</li>
     *  <li>  if |x| &lt; 1.0 and y is +Infinity then result is +0</li>
     *  <li>  if |x| = 1.0 and y is +/-Infinity then result is NaN</li>
     *  <li>  if x = +0 and y &gt; 0 then result is +0</li>
     *  <li>  if x = +Inf and y &lt; 0 then result is +0</li>
     *  <li>  if x = +0 and y &lt; 0 then result is +Inf</li>
     *  <li>  if x = +Inf and y &gt; 0 then result is +Inf</li>
     *  <li>  if x = -0 and y &gt; 0, finite, not odd integer then result is +0</li>
     *  <li>  if x = -0 and y &lt; 0, finite, and odd integer then result is -Inf</li>
     *  <li>  if x = -Inf and y &gt; 0, finite, and odd integer then result is -Inf</li>
     *  <li>  if x = -0 and y &lt; 0, not finite odd integer then result is +Inf</li>
     *  <li>  if x = -Inf and y &gt; 0, not finite odd integer then result is +Inf</li>
     *  <li>  if x &lt; 0 and y &gt; 0, finite, and odd integer then result is -(|x|<sup>y</sup>)</li>
     *  <li>  if x &lt; 0 and y &gt; 0, finite, and not integer then result is NaN</li>
     *  </ul>
     *  @param x base to be raised
     *  @param y power to which base should be raised
     *  @return x<sup>y</sup>
     */
    public static Dfp pow(Dfp x, final Dfp y) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Computes sin(a)  Used when 0 &lt; a &lt; pi/4.
     * Uses the classic Taylor series.  x - x**3/3! + x**5/5!  ...
     * @param a number from which sine is desired, in split form
     * @return sin(a)
     */
    protected static Dfp sinInternal(Dfp[] a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Computes cos(a)  Used when 0 &lt; a &lt; pi/4.
     * Uses the classic Taylor series for cosine.  1 - x**2/2! + x**4/4!  ...
     * @param a number from which cosine is desired, in split form
     * @return cos(a)
     */
    protected static Dfp cosInternal(Dfp[] a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * computes the sine of the argument.
     * @param a number from which sine is desired
     * @return sin(a)
     */
    public static Dfp sin(final Dfp a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * computes the cosine of the argument.
     * @param a number from which cosine is desired
     * @return cos(a)
     */
    public static Dfp cos(Dfp a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * computes the tangent of the argument.
     * @param a number from which tangent is desired
     * @return tan(a)
     */
    public static Dfp tan(final Dfp a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * computes the arc-tangent of the argument.
     * @param a number from which arc-tangent is desired
     * @return atan(a)
     */
    protected static Dfp atanInternal(final Dfp a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * computes the arc tangent of the argument
     *
     *  Uses the typical taylor series
     *
     *  but may reduce arguments using the following identity
     * tan(x+y) = (tan(x) + tan(y)) / (1 - tan(x)*tan(y))
     *
     * since tan(PI/8) = sqrt(2)-1,
     *
     * atan(x) = atan( (x - sqrt(2) + 1) / (1+x*sqrt(2) - x) + PI/8.0
     * @param a number from which arc-tangent is desired
     * @return atan(a)
     */
    public static Dfp atan(final Dfp a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * computes the arc-sine of the argument.
     * @param a number from which arc-sine is desired
     * @return asin(a)
     */
    public static Dfp asin(final Dfp a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * computes the arc-cosine of the argument.
     * @param a number from which arc-cosine is desired
     * @return acos(a)
     */
    public static Dfp acos(Dfp a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
