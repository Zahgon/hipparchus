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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;
import org.hipparchus.Field;
import org.hipparchus.FieldElement;
import org.hipparchus.CalculusFieldElement;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.MathRuntimeException;
import org.hipparchus.exception.NullArgumentException;
import org.hipparchus.random.RandomGenerator;
import org.hipparchus.random.Well19937c;

/**
 * Arrays utilities.
 */
public class MathArrays {

    /**
     * Private constructor.
     */
    private MathArrays() {
    }

    /**
     * Real-valued function that operates on an array or a part of it.
     */
    public interface Function {

        /**
         * Operates on an entire array.
         * @param array Array to operate on.
         * @return the result of the operation.
         */
        double evaluate(double[] array);

        /**
         * Operates on a sub-array.
         * @param array Array to operate on.
         * @param startIndex Index of the first element to take into account.
         * @param numElements Number of elements to take into account.
         * @return the result of the operation.
         */
        double evaluate(double[] array, int startIndex, int numElements);
    }

    /**
     * Create a copy of an array scaled by a value.
     *
     * @param arr Array to scale.
     * @param val Scalar.
     * @return scaled copy of array with each entry multiplied by val.
     */
    public static double[] scale(double val, final double[] arr) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Multiply each element of an array by a value.
     * <p>
     * The array is modified in place (no copy is created).
     *
     * @param arr Array to scale
     * @param val Scalar
     */
    public static void scaleInPlace(double val, final double[] arr) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Creates an array whose contents will be the element-by-element
     * addition of the arguments.
     *
     * @param a First term of the addition.
     * @param b Second term of the addition.
     * @return a new array {@code r} where {@code r[i] = a[i] + b[i]}.
     * @throws MathIllegalArgumentException if the array lengths differ.
     */
    public static double[] ebeAdd(double[] a, double[] b) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Creates an array whose contents will be the element-by-element
     * subtraction of the second argument from the first.
     *
     * @param a First term.
     * @param b Element to be subtracted.
     * @return a new array {@code r} where {@code r[i] = a[i] - b[i]}.
     * @throws MathIllegalArgumentException if the array lengths differ.
     */
    public static double[] ebeSubtract(double[] a, double[] b) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Creates an array whose contents will be the element-by-element
     * multiplication of the arguments.
     *
     * @param a First factor of the multiplication.
     * @param b Second factor of the multiplication.
     * @return a new array {@code r} where {@code r[i] = a[i] * b[i]}.
     * @throws MathIllegalArgumentException if the array lengths differ.
     */
    public static double[] ebeMultiply(double[] a, double[] b) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Creates an array whose contents will be the element-by-element
     * division of the first argument by the second.
     *
     * @param a Numerator of the division.
     * @param b Denominator of the division.
     * @return a new array {@code r} where {@code r[i] = a[i] / b[i]}.
     * @throws MathIllegalArgumentException if the array lengths differ.
     */
    public static double[] ebeDivide(double[] a, double[] b) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Calculates the L<sub>1</sub> (sum of abs) distance between two points.
     *
     * @param p1 the first point
     * @param p2 the second point
     * @return the L<sub>1</sub> distance between the two points
     * @throws MathIllegalArgumentException if the array lengths differ.
     */
    public static double distance1(double[] p1, double[] p2) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Calculates the L<sub>1</sub> (sum of abs) distance between two points.
     *
     * @param p1 the first point
     * @param p2 the second point
     * @return the L<sub>1</sub> distance between the two points
     * @throws MathIllegalArgumentException if the array lengths differ.
     */
    public static int distance1(int[] p1, int[] p2) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Calculates the L<sub>2</sub> (Euclidean) distance between two points.
     *
     * @param p1 the first point
     * @param p2 the second point
     * @return the L<sub>2</sub> distance between the two points
     * @throws MathIllegalArgumentException if the array lengths differ.
     */
    public static double distance(double[] p1, double[] p2) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Calculates the cosine of the angle between two vectors.
     *
     * @param v1 Cartesian coordinates of the first vector.
     * @param v2 Cartesian coordinates of the second vector.
     * @return the cosine of the angle between the vectors.
     */
    public static double cosAngle(double[] v1, double[] v2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Calculates the L<sub>2</sub> (Euclidean) distance between two points.
     *
     * @param p1 the first point
     * @param p2 the second point
     * @return the L<sub>2</sub> distance between the two points
     * @throws MathIllegalArgumentException if the array lengths differ.
     */
    public static double distance(int[] p1, int[] p2) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Calculates the L<sub>&infin;</sub> (max of abs) distance between two points.
     *
     * @param p1 the first point
     * @param p2 the second point
     * @return the L<sub>&infin;</sub> distance between the two points
     * @throws MathIllegalArgumentException if the array lengths differ.
     */
    public static double distanceInf(double[] p1, double[] p2) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Calculates the L<sub>&infin;</sub> (max of abs) distance between two points.
     *
     * @param p1 the first point
     * @param p2 the second point
     * @return the L<sub>&infin;</sub> distance between the two points
     * @throws MathIllegalArgumentException if the array lengths differ.
     */
    public static int distanceInf(int[] p1, int[] p2) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Specification of ordering direction.
     */
    public enum OrderDirection {

        /**
         * Constant for increasing direction.
         */
        INCREASING,
        /**
         * Constant for decreasing direction.
         */
        DECREASING
    }

    /**
     * Check that an array is monotonically increasing or decreasing.
     *
     * @param <T> the type of the elements in the specified array
     * @param val Values.
     * @param dir Ordering direction.
     * @param strict Whether the order should be strict.
     * @return {@code true} if sorted, {@code false} otherwise.
     */
    public static <T extends Comparable<? super T>> boolean isMonotonic(T[] val, OrderDirection dir, boolean strict) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check that an array is monotonically increasing or decreasing.
     *
     * @param val Values.
     * @param dir Ordering direction.
     * @param strict Whether the order should be strict.
     * @return {@code true} if sorted, {@code false} otherwise.
     */
    public static boolean isMonotonic(double[] val, OrderDirection dir, boolean strict) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check that both arrays have the same length.
     *
     * @param a Array.
     * @param b Array.
     * @param abort Whether to throw an exception if the check fails.
     * @return {@code true} if the arrays have the same length.
     * @throws MathIllegalArgumentException if the lengths differ and
     * {@code abort} is {@code true}.
     */
    public static boolean checkEqualLength(double[] a, double[] b, boolean abort) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check that both arrays have the same length.
     *
     * @param a Array.
     * @param b Array.
     * @throws MathIllegalArgumentException if the lengths differ.
     */
    public static void checkEqualLength(double[] a, double[] b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check that both arrays have the same length.
     *
     * @param a Array.
     * @param b Array.
     * @param abort Whether to throw an exception if the check fails.
     * @return {@code true} if the arrays have the same length.
     * @throws MathIllegalArgumentException if the lengths differ and
     * {@code abort} is {@code true}.
     * @param <T> the type of the field elements
     * @since 1.5
     */
    public static <T extends CalculusFieldElement<T>> boolean checkEqualLength(final T[] a, final T[] b, boolean abort) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check that both arrays have the same length.
     *
     * @param a Array.
     * @param b Array.
     * @throws MathIllegalArgumentException if the lengths differ.
     * @param <T> the type of the field elements
     * @since 1.5
     */
    public static <T extends CalculusFieldElement<T>> void checkEqualLength(final T[] a, final T[] b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check that both arrays have the same length.
     *
     * @param a Array.
     * @param b Array.
     * @param abort Whether to throw an exception if the check fails.
     * @return {@code true} if the arrays have the same length.
     * @throws MathIllegalArgumentException if the lengths differ and
     * {@code abort} is {@code true}.
     */
    public static boolean checkEqualLength(int[] a, int[] b, boolean abort) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check that both arrays have the same length.
     *
     * @param a Array.
     * @param b Array.
     * @throws MathIllegalArgumentException if the lengths differ.
     */
    public static void checkEqualLength(int[] a, int[] b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check that the given array is sorted.
     *
     * @param val Values.
     * @param dir Ordering direction.
     * @param strict Whether the order should be strict.
     * @param abort Whether to throw an exception if the check fails.
     * @return {@code true} if the array is sorted.
     * @throws MathIllegalArgumentException if the array is not sorted
     * and {@code abort} is {@code true}.
     */
    public static boolean checkOrder(double[] val, OrderDirection dir, boolean strict, boolean abort) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check that the given array is sorted.
     *
     * @param val Values.
     * @param dir Ordering direction.
     * @param strict Whether the order should be strict.
     * @throws MathIllegalArgumentException if the array is not sorted.
     */
    public static void checkOrder(double[] val, OrderDirection dir, boolean strict) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check that the given array is sorted in strictly increasing order.
     *
     * @param val Values.
     * @throws MathIllegalArgumentException if the array is not sorted.
     */
    public static void checkOrder(double[] val) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check that the given array is sorted.
     *
     * @param val Values.
     * @param dir Ordering direction.
     * @param strict Whether the order should be strict.
     * @param abort Whether to throw an exception if the check fails.
     * @return {@code true} if the array is sorted.
     * @throws MathIllegalArgumentException if the array is not sorted
     * and {@code abort} is {@code true}.
     * @param <T> the type of the field elements
     * @since 1.5
     */
    public static <T extends CalculusFieldElement<T>> boolean checkOrder(T[] val, OrderDirection dir, boolean strict, boolean abort) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check that the given array is sorted.
     *
     * @param val Values.
     * @param dir Ordering direction.
     * @param strict Whether the order should be strict.
     * @throws MathIllegalArgumentException if the array is not sorted.
     * @param <T> the type of the field elements
     * @since 1.5
     */
    public static <T extends CalculusFieldElement<T>> void checkOrder(T[] val, OrderDirection dir, boolean strict) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check that the given array is sorted in strictly increasing order.
     *
     * @param val Values.
     * @throws MathIllegalArgumentException if the array is not sorted.
     * @param <T> the type of the field elements
     * @since 1.5
     */
    public static <T extends CalculusFieldElement<T>> void checkOrder(T[] val) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Throws MathIllegalArgumentException if the input array is not rectangular.
     *
     * @param in array to be tested
     * @throws NullArgumentException if input array is null
     * @throws MathIllegalArgumentException if input array is not rectangular
     */
    public static void checkRectangular(final long[][] in) throws MathIllegalArgumentException, NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check that all entries of the input array are strictly positive.
     *
     * @param in Array to be tested
     * @throws MathIllegalArgumentException if any entries of the array are not
     * strictly positive.
     */
    public static void checkPositive(final double[] in) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check that no entry of the input array is {@code NaN}.
     *
     * @param in Array to be tested.
     * @throws MathIllegalArgumentException if an entry is {@code NaN}.
     */
    public static void checkNotNaN(final double[] in) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check that all entries of the input array are &gt;= 0.
     *
     * @param in Array to be tested
     * @throws MathIllegalArgumentException if any array entries are less than 0.
     */
    public static void checkNonNegative(final long[] in) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check all entries of the input array are &gt;= 0.
     *
     * @param in Array to be tested
     * @throws MathIllegalArgumentException if any array entries are less than 0.
     */
    public static void checkNonNegative(final long[][] in) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the Cartesian norm (2-norm), handling both overflow and underflow.
     * Translation of the minpack enorm subroutine.
     *
     * <p>
     * The redistribution policy for MINPACK is available
     * <a href="http://www.netlib.org/minpack/disclaimer">here</a>, for
     * convenience, it is reproduced below.
     * </p>
     *
     * <blockquote>
     * <p>
     *    Minpack Copyright Notice (1999) University of Chicago.
     *    All rights reserved
     * </p>
     * <p>
     * Redistribution and use in source and binary forms, with or without
     * modification, are permitted provided that the following conditions
     * are met:
     * </p>
     * <ol>
     *  <li>Redistributions of source code must retain the above copyright
     *      notice, this list of conditions and the following disclaimer.</li>
     * <li>Redistributions in binary form must reproduce the above
     *     copyright notice, this list of conditions and the following
     *     disclaimer in the documentation and/or other materials provided
     *     with the distribution.</li>
     * <li>The end-user documentation included with the redistribution, if any,
     *     must include the following acknowledgment:
     *     {@code This product includes software developed by the University of
     *           Chicago, as Operator of Argonne National Laboratory.}
     *     Alternately, this acknowledgment may appear in the software itself,
     *     if and wherever such third-party acknowledgments normally appear.</li>
     * <li><strong>WARRANTY DISCLAIMER. THE SOFTWARE IS SUPPLIED "AS IS"
     *     WITHOUT WARRANTY OF ANY KIND. THE COPYRIGHT HOLDER, THE
     *     UNITED STATES, THE UNITED STATES DEPARTMENT OF ENERGY, AND
     *     THEIR EMPLOYEES: (1) DISCLAIM ANY WARRANTIES, EXPRESS OR
     *     IMPLIED, INCLUDING BUT NOT LIMITED TO ANY IMPLIED WARRANTIES
     *     OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, TITLE
     *     OR NON-INFRINGEMENT, (2) DO NOT ASSUME ANY LEGAL LIABILITY
     *     OR RESPONSIBILITY FOR THE ACCURACY, COMPLETENESS, OR
     *     USEFULNESS OF THE SOFTWARE, (3) DO NOT REPRESENT THAT USE OF
     *     THE SOFTWARE WOULD NOT INFRINGE PRIVATELY OWNED RIGHTS, (4)
     *     DO NOT WARRANT THAT THE SOFTWARE WILL FUNCTION
     *     UNINTERRUPTED, THAT IT IS ERROR-FREE OR THAT ANY ERRORS WILL
     *     BE CORRECTED.</strong></li>
     * <li><strong>LIMITATION OF LIABILITY. IN NO EVENT WILL THE COPYRIGHT
     *     HOLDER, THE UNITED STATES, THE UNITED STATES DEPARTMENT OF
     *     ENERGY, OR THEIR EMPLOYEES: BE LIABLE FOR ANY INDIRECT,
     *     INCIDENTAL, CONSEQUENTIAL, SPECIAL OR PUNITIVE DAMAGES OF
     *     ANY KIND OR NATURE, INCLUDING BUT NOT LIMITED TO LOSS OF
     *     PROFITS OR LOSS OF DATA, FOR ANY REASON WHATSOEVER, WHETHER
     *     SUCH LIABILITY IS ASSERTED ON THE BASIS OF CONTRACT, TORT
     *     (INCLUDING NEGLIGENCE OR STRICT LIABILITY), OR OTHERWISE,
     *     EVEN IF ANY OF SAID PARTIES HAS BEEN WARNED OF THE
     *     POSSIBILITY OF SUCH LOSS OR DAMAGES.</strong></li>
     * </ol>
     * </blockquote>
     *
     * @param v Vector of doubles.
     * @return the 2-norm of the vector.
     */
    public static double safeNorm(double[] v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Sort an array in ascending order in place and perform the same reordering
     * of entries on other arrays. For example, if
     * {@code x = [3, 1, 2], y = [1, 2, 3]} and {@code z = [0, 5, 7]}, then
     * {@code sortInPlace(x, y, z)} will update {@code x} to {@code [1, 2, 3]},
     * {@code y} to {@code [2, 3, 1]} and {@code z} to {@code [5, 7, 0]}.
     *
     * @param x Array to be sorted and used as a pattern for permutation
     * of the other arrays.
     * @param yList Set of arrays whose permutations of entries will follow
     * those performed on {@code x}.
     * @throws MathIllegalArgumentException if any {@code y} is not the same
     * size as {@code x}.
     * @throws NullArgumentException if {@code x} or any {@code y} is null.
     */
    public static void sortInPlace(double[] x, double[]... yList) throws MathIllegalArgumentException, NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Helper data structure holding a (double, integer) pair.
     */
    private static class PairDoubleInteger {

        /**
         * Key
         */
        private final double key;

        /**
         * Value
         */
        private final int value;

        /**
         * @param key Key.
         * @param value Value.
         */
        PairDoubleInteger(double key, int value) {
            this.key = key;
            this.value = value;
        }

        /**
         * @return the key.
         */
        public double getKey() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * @return the value.
         */
        public int getValue() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Sort an array in place and perform the same reordering of entries on
     * other arrays.  This method works the same as the other
     * {@link #sortInPlace(double[], double[][]) sortInPlace} method, but
     * allows the order of the sort to be provided in the {@code dir}
     * parameter.
     *
     * @param x Array to be sorted and used as a pattern for permutation
     * of the other arrays.
     * @param dir Order direction.
     * @param yList Set of arrays whose permutations of entries will follow
     * those performed on {@code x}.
     * @throws MathIllegalArgumentException if any {@code y} is not the same
     * size as {@code x}.
     * @throws NullArgumentException if {@code x} or any {@code y} is null
     */
    public static void sortInPlace(double[] x, final OrderDirection dir, double[]... yList) throws MathIllegalArgumentException, NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute a linear combination accurately.
     * This method computes the sum of the products
     * <code>a<sub>i</sub> b<sub>i</sub></code> to high accuracy.
     * It does so by using specific multiplication and addition algorithms to
     * preserve accuracy and reduce cancellation effects.
     * <br>
     * It is based on the 2005 paper
     * <a href="http://citeseerx.ist.psu.edu/viewdoc/summary?doi=10.1.1.2.1547">
     * Accurate Sum and Dot Product</a> by Takeshi Ogita, Siegfried M. Rump,
     * and Shin'ichi Oishi published in SIAM J. Sci. Comput.
     *
     * @param a Factors.
     * @param b Factors.
     * @return <code>&Sigma;<sub>i</sub> a<sub>i</sub> b<sub>i</sub></code>.
     * @throws MathIllegalArgumentException if arrays dimensions don't match
     */
    public static double linearCombination(final double[] a, final double[] b) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute a linear combination accurately.
     * <p>
     * This method computes a<sub>1</sub>&times;b<sub>1</sub> +
     * a<sub>2</sub>&times;b<sub>2</sub> to high accuracy. It does
     * so by using specific multiplication and addition algorithms to
     * preserve accuracy and reduce cancellation effects. It is based
     * on the 2005 paper <a
     * href="http://citeseerx.ist.psu.edu/viewdoc/summary?doi=10.1.1.2.1547">
     * Accurate Sum and Dot Product</a> by Takeshi Ogita,
     * Siegfried M. Rump, and Shin'ichi Oishi published in SIAM J. Sci. Comput.
     * </p>
     * @param a1 first factor of the first term
     * @param b1 second factor of the first term
     * @param a2 first factor of the second term
     * @param b2 second factor of the second term
     * @return a<sub>1</sub>&times;b<sub>1</sub> +
     * a<sub>2</sub>&times;b<sub>2</sub>
     * @see #linearCombination(double, double, double, double, double, double)
     * @see #linearCombination(double, double, double, double, double, double, double, double)
     */
    public static double linearCombination(final double a1, final double b1, final double a2, final double b2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute a linear combination accurately.
     * <p>
     * This method computes a<sub>1</sub>&times;b<sub>1</sub> +
     * a<sub>2</sub>&times;b<sub>2</sub> + a<sub>3</sub>&times;b<sub>3</sub>
     * to high accuracy. It does so by using specific multiplication and
     * addition algorithms to preserve accuracy and reduce cancellation effects.
     * It is based on the 2005 paper <a
     * href="http://citeseerx.ist.psu.edu/viewdoc/summary?doi=10.1.1.2.1547">
     * Accurate Sum and Dot Product</a> by Takeshi Ogita,
     * Siegfried M. Rump, and Shin'ichi Oishi published in SIAM J. Sci. Comput.
     * </p>
     * @param a1 first factor of the first term
     * @param b1 second factor of the first term
     * @param a2 first factor of the second term
     * @param b2 second factor of the second term
     * @param a3 first factor of the third term
     * @param b3 second factor of the third term
     * @return a<sub>1</sub>&times;b<sub>1</sub> +
     * a<sub>2</sub>&times;b<sub>2</sub> + a<sub>3</sub>&times;b<sub>3</sub>
     * @see #linearCombination(double, double, double, double)
     * @see #linearCombination(double, double, double, double, double, double, double, double)
     */
    public static double linearCombination(final double a1, final double b1, final double a2, final double b2, final double a3, final double b3) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute a linear combination accurately.
     * <p>
     * This method computes a<sub>1</sub>&times;b<sub>1</sub> +
     * a<sub>2</sub>&times;b<sub>2</sub> + a<sub>3</sub>&times;b<sub>3</sub> +
     * a<sub>4</sub>&times;b<sub>4</sub>
     * to high accuracy. It does so by using specific multiplication and
     * addition algorithms to preserve accuracy and reduce cancellation effects.
     * It is based on the 2005 paper <a
     * href="http://citeseerx.ist.psu.edu/viewdoc/summary?doi=10.1.1.2.1547">
     * Accurate Sum and Dot Product</a> by Takeshi Ogita,
     * Siegfried M. Rump, and Shin'ichi Oishi published in SIAM J. Sci. Comput.
     * </p>
     * @param a1 first factor of the first term
     * @param b1 second factor of the first term
     * @param a2 first factor of the second term
     * @param b2 second factor of the second term
     * @param a3 first factor of the third term
     * @param b3 second factor of the third term
     * @param a4 first factor of the third term
     * @param b4 second factor of the third term
     * @return a<sub>1</sub>&times;b<sub>1</sub> +
     * a<sub>2</sub>&times;b<sub>2</sub> + a<sub>3</sub>&times;b<sub>3</sub> +
     * a<sub>4</sub>&times;b<sub>4</sub>
     * @see #linearCombination(double, double, double, double)
     * @see #linearCombination(double, double, double, double, double, double)
     */
    public static double linearCombination(final double a1, final double b1, final double a2, final double b2, final double a3, final double b3, final double a4, final double b4) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns true iff both arguments are null or have same dimensions and all
     * their elements are equal as defined by
     * {@link Precision#equals(float,float)}.
     *
     * @param x first array
     * @param y second array
     * @return true if the values are both null or have same dimension
     * and equal elements.
     */
    public static boolean equals(float[] x, float[] y) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns true iff both arguments are null or have same dimensions and all
     * their elements are equal as defined by
     * {@link Precision#equalsIncludingNaN(double,double) this method}.
     *
     * @param x first array
     * @param y second array
     * @return true if the values are both null or have same dimension and
     * equal elements
     */
    public static boolean equalsIncludingNaN(float[] x, float[] y) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns {@code true} iff both arguments are {@code null} or have same
     * dimensions and all their elements are equal as defined by
     * {@link Precision#equals(double,double)}.
     *
     * @param x First array.
     * @param y Second array.
     * @return {@code true} if the values are both {@code null} or have same
     * dimension and equal elements.
     */
    public static boolean equals(double[] x, double[] y) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns {@code true} iff both arguments are {@code null} or have same
     * dimensions and all their elements are equal as defined by
     * {@link Object#equals(Object)}.
     *
     * @param <T> type of the field elements
     * @param x First array.
     * @param y Second array.
     * @return {@code true} if the values are both {@code null} or have same
     * dimension and equal elements.
     * @since 4.0
     */
    public static <T extends FieldElement<T>> boolean equals(T[] x, T[] y) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns {@code true} iff both arguments are {@code null} or have same
     * dimensions and all their elements are equal as defined by
     * {@link Precision#equalsIncludingNaN(double,double) this method}.
     *
     * @param x First array.
     * @param y Second array.
     * @return {@code true} if the values are both {@code null} or have same
     * dimension and equal elements.
     */
    public static boolean equalsIncludingNaN(double[] x, double[] y) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns {@code true} if both arguments are {@code null} or have same
     * dimensions and all their elements are equals.
     *
     * @param x First array.
     * @param y Second array.
     * @return {@code true} if the values are both {@code null} or have same
     * dimension and equal elements.
     */
    public static boolean equals(long[] x, long[] y) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns {@code true} if both arguments are {@code null} or have same
     * dimensions and all their elements are equals.
     *
     * @param x First array.
     * @param y Second array.
     * @return {@code true} if the values are both {@code null} or have same
     * dimension and equal elements.
     */
    public static boolean equals(int[] x, int[] y) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns {@code true} if both arguments are {@code null} or have same
     * dimensions and all their elements are equals.
     *
     * @param x First array.
     * @param y Second array.
     * @return {@code true} if the values are both {@code null} or have same
     * dimension and equal elements.
     */
    public static boolean equals(byte[] x, byte[] y) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns {@code true} if both arguments are {@code null} or have same
     * dimensions and all their elements are equals.
     *
     * @param x First array.
     * @param y Second array.
     * @return {@code true} if the values are both {@code null} or have same
     * dimension and equal elements.
     */
    public static boolean equals(short[] x, short[] y) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Normalizes an array to make it sum to a specified value.
     * Returns the result of the transformation
     * <pre>
     *    x ↦ x * normalizedSum / sum
     * </pre>
     * applied to each non-NaN element x of the input array, where sum is the
     * sum of the non-NaN entries in the input array.
     * <p>
     * Throws IllegalArgumentException if {@code normalizedSum} is infinite
     * or NaN and ArithmeticException if the input array contains any infinite elements
     * or sums to 0.
     * <p>
     * Ignores (i.e., copies unchanged to the output array) NaNs in the input array.
     * The input array is unchanged by this method.
     *
     * @param values Input array to be normalized
     * @param normalizedSum Target sum for the normalized array
     * @return the normalized array
     * @throws MathRuntimeException if the input array contains infinite
     * elements or sums to zero
     * @throws MathIllegalArgumentException if the target sum is infinite or {@code NaN}
     */
    public static double[] normalizeArray(double[] values, double normalizedSum) throws MathIllegalArgumentException, MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Build an array of elements.
     * <p>
     * Arrays are filled with {@code field.getZero()}
     *
     * @param <T> the type of the field elements
     * @param field field to which array elements belong
     * @param length of the array
     * @return a new array
     */
    public static <T extends FieldElement<T>> T[] buildArray(final Field<T> field, final int length) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Build a double dimension array of elements.
     * <p>
     * Arrays are filled with {@code field.getZero()}
     *
     * @param <T> the type of the field elements
     * @param field field to which array elements belong
     * @param rows number of rows in the array
     * @param columns number of columns (may be negative to build partial
     * arrays in the same way {@code new Field[rows][]} works)
     * @return a new array
     */
    @SuppressWarnings("unchecked")
    public static <T extends FieldElement<T>> T[][] buildArray(final Field<T> field, final int rows, final int columns) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Build a triple dimension array of elements.
     * <p>
     * Arrays are filled with {@code field.getZero()}
     *
     * @param <T> the type of the field elements
     * @param field field to which array elements belong
     * @param l1 number of elements along first dimension
     * @param l2 number of elements along second dimension
     * @param l3 number of elements along third dimension (may be negative to build partial
     * arrays in the same way {@code new Field[l1][l2][]} works)
     * @return a new array
     * @since 1.4
     */
    @SuppressWarnings("unchecked")
    public static <T extends FieldElement<T>> T[][][] buildArray(final Field<T> field, final int l1, final int l2, final int l3) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Calculates the <a href="http://en.wikipedia.org/wiki/Convolution">
     * convolution</a> between two sequences.
     * <p>
     * The solution is obtained via straightforward computation of the
     * convolution sum (and not via FFT). Whenever the computation needs
     * an element that would be located at an index outside the input arrays,
     * the value is assumed to be zero.
     *
     * @param x First sequence.
     * Typically, this sequence will represent an input signal to a system.
     * @param h Second sequence.
     * Typically, this sequence will represent the impulse response of the system.
     * @return the convolution of {@code x} and {@code h}.
     * This array's length will be {@code x.length + h.length - 1}.
     * @throws NullArgumentException if either {@code x} or {@code h} is {@code null}.
     * @throws MathIllegalArgumentException if either {@code x} or {@code h} is empty.
     */
    public static double[] convolve(double[] x, double[] h) throws MathIllegalArgumentException, NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Specification for indicating that some operation applies
     * before or after a given index.
     */
    public enum Position {

        /**
         * Designates the beginning of the array (near index 0).
         */
        HEAD,
        /**
         * Designates the end of the array.
         */
        TAIL
    }

    /**
     * Shuffle the entries of the given array.
     * The {@code start} and {@code pos} parameters select which portion
     * of the array is randomized and which is left untouched.
     *
     * @see #shuffle(int[],int,Position,RandomGenerator)
     *
     * @param list Array whose entries will be shuffled (in-place).
     * @param start Index at which shuffling begins.
     * @param pos Shuffling is performed for index positions between
     * {@code start} and either the end (if {@link Position#TAIL})
     * or the beginning (if {@link Position#HEAD}) of the array.
     */
    public static void shuffle(int[] list, int start, Position pos) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Shuffle the entries of the given array, using the
     * <a href="https://en.wikipedia.org/wiki/Fisher-Yates_shuffle#The_modern_algorithm">
     * Fisher–Yates</a> algorithm.
     * The {@code start} and {@code pos} parameters select which portion
     * of the array is randomized and which is left untouched.
     *
     * @param list Array whose entries will be shuffled (in-place).
     * @param start Index at which shuffling begins.
     * @param pos Shuffling is performed for index positions between
     * {@code start} and either the end (if {@link Position#TAIL})
     * or the beginning (if {@link Position#HEAD}) of the array.
     * @param rng Random number generator.
     */
    public static void shuffle(int[] list, int start, Position pos, RandomGenerator rng) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Shuffle the entries of the given array.
     *
     * @see #shuffle(int[],int,Position,RandomGenerator)
     *
     * @param list Array whose entries will be shuffled (in-place).
     * @param rng Random number generator.
     */
    public static void shuffle(int[] list, RandomGenerator rng) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Shuffle the entries of the given array.
     *
     * @see #shuffle(int[],int,Position,RandomGenerator)
     *
     * @param list Array whose entries will be shuffled (in-place).
     */
    public static void shuffle(int[] list) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns an array representing the natural number {@code n}.
     *
     * @param n Natural number.
     * @return an array whose entries are the numbers 0, 1, ..., {@code n}-1.
     * If {@code n == 0}, the returned array is empty.
     */
    public static int[] natural(int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns an array of {@code size} integers starting at {@code start},
     * skipping {@code stride} numbers.
     *
     * @param size Natural number.
     * @param start Natural number.
     * @param stride Natural number.
     * @return an array whose entries are the numbers
     * {@code start, start + stride, ..., start + (size - 1) * stride}.
     * If {@code size == 0}, the returned array is empty.
     */
    public static int[] sequence(int size, int start, int stride) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * This method is used
     * to verify that the input parameters designate a subarray of positive length.
     * <ul>
     * <li>returns {@code true} iff the parameters designate a subarray of
     * positive length</li>
     * <li>throws {@code MathIllegalArgumentException} if the array is null or
     * or the indices are invalid</li>
     * <li>returns {@code false} if the array is non-null, but
     * {@code length} is 0.</li>
     * </ul>
     *
     * @param values the input array
     * @param begin index of the first array element to include
     * @param length the number of elements to include
     * @return true if the parameters are valid and designate a subarray of positive length
     * @throws MathIllegalArgumentException if the indices are invalid or the array is null
     */
    public static boolean verifyValues(final double[] values, final int begin, final int length) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * This method is used
     * to verify that the input parameters designate a subarray of positive length.
     * <ul>
     * <li>returns {@code true} iff the parameters designate a subarray of
     * non-negative length</li>
     * <li>throws {@code IllegalArgumentException} if the array is null or
     * or the indices are invalid</li>
     * <li>returns {@code false} if the array is non-null, but
     * {@code length} is 0 unless {@code allowEmpty} is {@code true}</li>
     * </ul>
     *
     * @param values the input array
     * @param begin index of the first array element to include
     * @param length the number of elements to include
     * @param allowEmpty if <code>true</code> then zero length arrays are allowed
     * @return true if the parameters are valid
     * @throws MathIllegalArgumentException if the indices are invalid or the array is null
     */
    public static boolean verifyValues(final double[] values, final int begin, final int length, final boolean allowEmpty) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * This method is used
     * to verify that the begin and length parameters designate a subarray of positive length
     * and the weights are all non-negative, non-NaN, finite, and not all zero.
     * <ul>
     * <li>returns {@code true} iff the parameters designate a subarray of
     * positive length and the weights array contains legitimate values.</li>
     * <li>throws {@code IllegalArgumentException} if any of the following are true:
     * <ul><li>the values array is null</li>
     *     <li>the weights array is null</li>
     *     <li>the weights array does not have the same length as the values array</li>
     *     <li>the weights array contains one or more infinite values</li>
     *     <li>the weights array contains one or more NaN values</li>
     *     <li>the weights array contains negative values</li>
     *     <li>the start and length arguments do not determine a valid array</li></ul>
     * </li>
     * <li>returns {@code false} if the array is non-null, but {@code length} is 0.</li>
     * </ul>
     *
     * @param values the input array
     * @param weights the weights array
     * @param begin index of the first array element to include
     * @param length the number of elements to include
     * @return true if the parameters are valid and designate a subarray of positive length
     * @throws MathIllegalArgumentException if the indices are invalid or the array is null
     */
    public static boolean verifyValues(final double[] values, final double[] weights, final int begin, final int length) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * This method is used
     * to verify that the begin and length parameters designate a subarray of positive length
     * and the weights are all non-negative, non-NaN, finite, and not all zero.
     * <ul>
     * <li>returns {@code true} iff the parameters designate a subarray of
     * non-negative length and the weights array contains legitimate values.</li>
     * <li>throws {@code MathIllegalArgumentException} if any of the following are true:
     * <ul><li>the values array is null</li>
     *     <li>the weights array is null</li>
     *     <li>the weights array does not have the same length as the values array</li>
     *     <li>the weights array contains one or more infinite values</li>
     *     <li>the weights array contains one or more NaN values</li>
     *     <li>the weights array contains negative values</li>
     *     <li>the start and length arguments do not determine a valid array</li></ul>
     * </li>
     * <li>returns {@code false} if the array is non-null, but
     * {@code length} is 0 unless {@code allowEmpty} is {@code true}</li>
     * </ul>
     *
     * @param values the input array.
     * @param weights the weights array.
     * @param begin index of the first array element to include.
     * @param length the number of elements to include.
     * @param allowEmpty if {@code true} than allow zero length arrays to pass.
     * @return {@code true} if the parameters are valid.
     * @throws NullArgumentException if either of the arrays are null
     * @throws MathIllegalArgumentException if the array indices are not valid,
     * the weights array contains NaN, infinite or negative elements, or there
     * are no positive weights.
     */
    public static boolean verifyValues(final double[] values, final double[] weights, final int begin, final int length, final boolean allowEmpty) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Concatenates a sequence of arrays. The return array consists of the
     * entries of the input arrays concatenated in the order they appear in
     * the argument list.  Null arrays cause NullPointerExceptions; zero
     * length arrays are allowed (contributing nothing to the output array).
     *
     * @param x list of double[] arrays to concatenate
     * @return a new array consisting of the entries of the argument arrays
     * @throws NullPointerException if any of the arrays are null
     */
    public static double[] concatenate(double[]... x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns an array consisting of the unique values in {@code data}.
     * The return array is sorted in descending order.  Empty arrays
     * are allowed, but null arrays result in NullPointerException.
     * Infinities are allowed.  NaN values are allowed with maximum
     * sort order - i.e., if there are NaN values in {@code data},
     * {@code Double.NaN} will be the first element of the output array,
     * even if the array also contains {@code Double.POSITIVE_INFINITY}.
     *
     * @param data array to scan
     * @return descending list of values included in the input array
     * @throws NullPointerException if data is null
     */
    public static double[] unique(double[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
