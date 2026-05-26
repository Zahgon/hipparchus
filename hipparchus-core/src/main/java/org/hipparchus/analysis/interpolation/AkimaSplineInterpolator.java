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
package org.hipparchus.analysis.interpolation;

import java.lang.reflect.Array;
import org.hipparchus.Field;
import org.hipparchus.CalculusFieldElement;
import org.hipparchus.analysis.polynomials.FieldPolynomialFunction;
import org.hipparchus.analysis.polynomials.FieldPolynomialSplineFunction;
import org.hipparchus.analysis.polynomials.PolynomialFunction;
import org.hipparchus.analysis.polynomials.PolynomialSplineFunction;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.NullArgumentException;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.MathArrays;
import org.hipparchus.util.Precision;

/**
 * Computes a cubic spline interpolation for the data set using the Akima
 * algorithm, as originally formulated by Hiroshi Akima in his 1970 paper
 * <a href="http://doi.acm.org/10.1145/321607.321609">A New Method of
 * Interpolation and Smooth Curve Fitting Based on Local Procedures.</a>
 * J. ACM 17, 4 (October 1970), 589-602. DOI=10.1145/321607.321609
 * <p>
 * This implementation is based on the Akima implementation in the CubicSpline
 * class in the Math.NET Numerics library. The method referenced is
 * CubicSpline.InterpolateAkimaSorted
 * </p>
 * <p>
 * The {@link #interpolate(double[], double[]) interpolate} method returns a
 * {@link PolynomialSplineFunction} consisting of n cubic polynomials, defined
 * over the subintervals determined by the x values, {@code x[0] < x[i] ... < x[n]}.
 * The Akima algorithm requires that {@code n >= 5}.
 * </p>
 */
public class AkimaSplineInterpolator implements UnivariateInterpolator, FieldUnivariateInterpolator {

    /**
     * The minimum number of points that are needed to compute the function.
     */
    private static final int MINIMUM_NUMBER_POINTS = 5;

    /**
     * Weight modifier to avoid overshoots.
     */
    private final boolean useModifiedWeights;

    /**
     * Simple constructor.
     * <p>
     * This constructor is equivalent to call {@link #AkimaSplineInterpolator(boolean)
     * AkimaSplineInterpolator(false)}, i.e. to use original Akima weights
     * </p>
     * @since 2.1
     */
    public AkimaSplineInterpolator() {
        this(false);
    }

    /**
     * Simple constructor.
     * <p>
     * The weight modification is described in <a
     * href="https://blogs.mathworks.com/cleve/2019/04/29/makima-piecewise-cubic-interpolation/">
     * Makima Piecewise Cubic Interpolation</a>. It attempts to avoid overshoots
     * near near constant slopes sub-samples.
     * </p>
     * @param useModifiedWeights if true, use modified weights to avoid overshoots
     * @since 2.1
     */
    public AkimaSplineInterpolator(final boolean useModifiedWeights) {
        this.useModifiedWeights = useModifiedWeights;
    }

    /**
     * Computes an interpolating function for the data set.
     *
     * @param xvals the arguments for the interpolation points
     * @param yvals the values for the interpolation points
     * @return a function which interpolates the data set
     * @throws MathIllegalArgumentException if {@code xvals} and {@code yvals} have
     *         different sizes.
     * @throws MathIllegalArgumentException if {@code xvals} is not sorted in
     *         strict increasing order.
     * @throws MathIllegalArgumentException if the size of {@code xvals} is smaller
     *         than 5.
     */
    @Override
    public PolynomialSplineFunction interpolate(double[] xvals, double[] yvals) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Computes an interpolating function for the data set.
     *
     * @param xvals the arguments for the interpolation points
     * @param yvals the values for the interpolation points
     * @param <T> the type of the field elements
     * @return a function which interpolates the data set
     * @throws MathIllegalArgumentException if {@code xvals} and {@code yvals} have
     *         different sizes.
     * @throws MathIllegalArgumentException if {@code xvals} is not sorted in
     *         strict increasing order.
     * @throws MathIllegalArgumentException if the size of {@code xvals} is smaller
     *         than 5.
     * @since 1.5
     */
    @Override
    public <T extends CalculusFieldElement<T>> FieldPolynomialSplineFunction<T> interpolate(final T[] xvals, final T[] yvals) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Three point differentiation helper, modeled off of the same method in the
     * Math.NET CubicSpline class. This is used by both the Apache Math and the
     * Math.NET Akima Cubic Spline algorithms
     *
     * @param xvals x values to calculate the numerical derivative with
     * @param yvals y values to calculate the numerical derivative with
     * @param indexOfDifferentiation index of the elemnt we are calculating the derivative around
     * @param indexOfFirstSample index of the first element to sample for the three point method
     * @param indexOfSecondsample index of the second element to sample for the three point method
     * @param indexOfThirdSample index of the third element to sample for the three point method
     * @return the derivative
     */
    private double differentiateThreePoint(double[] xvals, double[] yvals, int indexOfDifferentiation, int indexOfFirstSample, int indexOfSecondsample, int indexOfThirdSample) {
        final double x0 = yvals[indexOfFirstSample];
        final double x1 = yvals[indexOfSecondsample];
        final double x2 = yvals[indexOfThirdSample];
        final double t = xvals[indexOfDifferentiation] - xvals[indexOfFirstSample];
        final double t1 = xvals[indexOfSecondsample] - xvals[indexOfFirstSample];
        final double t2 = xvals[indexOfThirdSample] - xvals[indexOfFirstSample];
        final double a = (x2 - x0 - (t2 / t1 * (x1 - x0))) / (t2 * t2 - t1 * t2);
        final double b = (x1 - x0 - a * t1 * t1) / t1;
        return (2 * a * t) + b;
    }

    /**
     * Three point differentiation helper, modeled off of the same method in the
     * Math.NET CubicSpline class. This is used by both the Apache Math and the
     * Math.NET Akima Cubic Spline algorithms
     *
     * @param xvals x values to calculate the numerical derivative with
     * @param yvals y values to calculate the numerical derivative with
     * @param <T> the type of the field elements
     * @param indexOfDifferentiation index of the elemnt we are calculating the derivative around
     * @param indexOfFirstSample index of the first element to sample for the three point method
     * @param indexOfSecondsample index of the second element to sample for the three point method
     * @param indexOfThirdSample index of the third element to sample for the three point method
     * @return the derivative
     * @since 1.5
     */
    private <T extends CalculusFieldElement<T>> T differentiateThreePoint(T[] xvals, T[] yvals, int indexOfDifferentiation, int indexOfFirstSample, int indexOfSecondsample, int indexOfThirdSample) {
        final T x0 = yvals[indexOfFirstSample];
        final T x1 = yvals[indexOfSecondsample];
        final T x2 = yvals[indexOfThirdSample];
        final T t = xvals[indexOfDifferentiation].subtract(xvals[indexOfFirstSample]);
        final T t1 = xvals[indexOfSecondsample].subtract(xvals[indexOfFirstSample]);
        final T t2 = xvals[indexOfThirdSample].subtract(xvals[indexOfFirstSample]);
        final T a = x2.subtract(x0).subtract(t2.divide(t1).multiply(x1.subtract(x0))).divide(t2.multiply(t2).subtract(t1.multiply(t2)));
        final T b = x1.subtract(x0).subtract(a.multiply(t1).multiply(t1)).divide(t1);
        return a.multiply(t).twice().add(b);
    }

    /**
     * Creates a Hermite cubic spline interpolation from the set of (x,y) value
     * pairs and their derivatives. This is modeled off of the
     * InterpolateHermiteSorted method in the Math.NET CubicSpline class.
     *
     * @param xvals x values for interpolation
     * @param yvals y values for interpolation
     * @param firstDerivatives first derivative values of the function
     * @return polynomial that fits the function
     */
    private PolynomialSplineFunction interpolateHermiteSorted(double[] xvals, double[] yvals, double[] firstDerivatives) {
        MathArrays.checkEqualLength(xvals, yvals);
        MathArrays.checkEqualLength(xvals, firstDerivatives);
        final int minimumLength = 2;
        if (xvals.length < minimumLength) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.NUMBER_OF_POINTS, xvals.length, minimumLength, true);
        }
        final int size = xvals.length - 1;
        final PolynomialFunction[] polynomials = new PolynomialFunction[size];
        final double[] coefficients = new double[4];
        for (int i = 0; i < polynomials.length; i++) {
            final double w = xvals[i + 1] - xvals[i];
            final double w2 = w * w;
            final double yv = yvals[i];
            final double yvP = yvals[i + 1];
            final double fd = firstDerivatives[i];
            final double fdP = firstDerivatives[i + 1];
            coefficients[0] = yv;
            coefficients[1] = firstDerivatives[i];
            coefficients[2] = (3 * (yvP - yv) / w - 2 * fd - fdP) / w;
            coefficients[3] = (2 * (yv - yvP) / w + fd + fdP) / w2;
            polynomials[i] = new PolynomialFunction(coefficients);
        }
        return new PolynomialSplineFunction(xvals, polynomials);
    }

    /**
     * Creates a Hermite cubic spline interpolation from the set of (x,y) value
     * pairs and their derivatives. This is modeled off of the
     * InterpolateHermiteSorted method in the Math.NET CubicSpline class.
     *
     * @param xvals x values for interpolation
     * @param yvals y values for interpolation
     * @param firstDerivatives first derivative values of the function
     * @param <T> the type of the field elements
     * @return polynomial that fits the function
     * @since 1.5
     */
    private <T extends CalculusFieldElement<T>> FieldPolynomialSplineFunction<T> interpolateHermiteSorted(T[] xvals, T[] yvals, T[] firstDerivatives) {
        MathArrays.checkEqualLength(xvals, yvals);
        MathArrays.checkEqualLength(xvals, firstDerivatives);
        final int minimumLength = 2;
        if (xvals.length < minimumLength) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.NUMBER_OF_POINTS, xvals.length, minimumLength, true);
        }
        final Field<T> field = xvals[0].getField();
        final int size = xvals.length - 1;
        @SuppressWarnings("unchecked")
        final FieldPolynomialFunction<T>[] polynomials = (FieldPolynomialFunction<T>[]) Array.newInstance(FieldPolynomialFunction.class, size);
        final T[] coefficients = MathArrays.buildArray(field, 4);
        for (int i = 0; i < polynomials.length; i++) {
            final T w = xvals[i + 1].subtract(xvals[i]);
            final T w2 = w.multiply(w);
            final T yv = yvals[i];
            final T yvP = yvals[i + 1];
            final T fd = firstDerivatives[i];
            final T fdP = firstDerivatives[i + 1];
            coefficients[0] = yv;
            coefficients[1] = firstDerivatives[i];
            final T ratio = yvP.subtract(yv).divide(w);
            coefficients[2] = ratio.multiply(+3).subtract(fd.add(fd)).subtract(fdP).divide(w);
            coefficients[3] = ratio.multiply(-2).add(fd).add(fdP).divide(w2);
            polynomials[i] = new FieldPolynomialFunction<>(coefficients);
        }
        return new FieldPolynomialSplineFunction<>(xvals, polynomials);
    }
}
