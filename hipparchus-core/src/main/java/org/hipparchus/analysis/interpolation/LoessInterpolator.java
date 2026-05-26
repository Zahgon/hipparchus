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

import java.io.Serializable;
import java.util.Arrays;
import org.hipparchus.analysis.polynomials.PolynomialSplineFunction;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.MathArrays;
import org.hipparchus.util.MathUtils;

/**
 * Implements the <a href="http://en.wikipedia.org/wiki/Local_regression">
 * Local Regression Algorithm</a> (also Loess, Lowess) for interpolation of
 * real univariate functions.
 * <p>
 * For reference, see
 * <a href="http://amstat.tandfonline.com/doi/abs/10.1080/01621459.1979.10481038">
 * William S. Cleveland - Robust Locally Weighted Regression and Smoothing
 * Scatterplots</a></p>
 * <p>
 * This class implements both the loess method and serves as an interpolation
 * adapter to it, allowing one to build a spline on the obtained loess fit.</p>
 */
public class LoessInterpolator implements UnivariateInterpolator, Serializable {

    /**
     * Default value of the bandwidth parameter.
     */
    public static final double DEFAULT_BANDWIDTH = 0.3;

    /**
     * Default value of the number of robustness iterations.
     */
    public static final int DEFAULT_ROBUSTNESS_ITERS = 2;

    /**
     * Default value for accuracy.
     */
    public static final double DEFAULT_ACCURACY = 1e-12;

    /**
     * serializable version identifier.
     */
    private static final long serialVersionUID = 5204927143605193821L;

    /**
     * The bandwidth parameter: when computing the loess fit at
     * a particular point, this fraction of source points closest
     * to the current point is taken into account for computing
     * a least-squares regression.
     * <p>
     * A sensible value is usually 0.25 to 0.5.</p>
     */
    private final double bandwidth;

    /**
     * The number of robustness iterations parameter: this many
     * robustness iterations are done.
     * <p>
     * A sensible value is usually 0 (just the initial fit without any
     * robustness iterations) to 4.</p>
     */
    private final int robustnessIters;

    /**
     * If the median residual at a certain robustness iteration
     * is less than this amount, no more iterations are done.
     */
    private final double accuracy;

    /**
     * Constructs a new {@link LoessInterpolator}
     * with a bandwidth of {@link #DEFAULT_BANDWIDTH},
     * {@link #DEFAULT_ROBUSTNESS_ITERS} robustness iterations
     * and an accuracy of {#link #DEFAULT_ACCURACY}.
     * See {@link #LoessInterpolator(double, int, double)} for an explanation of
     * the parameters.
     */
    public LoessInterpolator() {
        this.bandwidth = DEFAULT_BANDWIDTH;
        this.robustnessIters = DEFAULT_ROBUSTNESS_ITERS;
        this.accuracy = DEFAULT_ACCURACY;
    }

    /**
     * Construct a new {@link LoessInterpolator}
     * with given bandwidth and number of robustness iterations.
     * <p>
     * Calling this constructor is equivalent to calling {link {@link
     * #LoessInterpolator(double, int, double) LoessInterpolator(bandwidth,
     * robustnessIters, LoessInterpolator.DEFAULT_ACCURACY)}
     * </p>
     *
     * @param bandwidth  when computing the loess fit at
     * a particular point, this fraction of source points closest
     * to the current point is taken into account for computing
     * a least-squares regression.
     * A sensible value is usually 0.25 to 0.5, the default value is
     * {@link #DEFAULT_BANDWIDTH}.
     * @param robustnessIters This many robustness iterations are done.
     * A sensible value is usually 0 (just the initial fit without any
     * robustness iterations) to 4, the default value is
     * {@link #DEFAULT_ROBUSTNESS_ITERS}.
     *
     * @see #LoessInterpolator(double, int, double)
     */
    public LoessInterpolator(double bandwidth, int robustnessIters) {
        this(bandwidth, robustnessIters, DEFAULT_ACCURACY);
    }

    /**
     * Construct a new {@link LoessInterpolator}
     * with given bandwidth, number of robustness iterations and accuracy.
     *
     * @param bandwidth  when computing the loess fit at
     * a particular point, this fraction of source points closest
     * to the current point is taken into account for computing
     * a least-squares regression.
     * A sensible value is usually 0.25 to 0.5, the default value is
     * {@link #DEFAULT_BANDWIDTH}.
     * @param robustnessIters This many robustness iterations are done.
     * A sensible value is usually 0 (just the initial fit without any
     * robustness iterations) to 4, the default value is
     * {@link #DEFAULT_ROBUSTNESS_ITERS}.
     * @param accuracy If the median residual at a certain robustness iteration
     * is less than this amount, no more iterations are done.
     * @throws MathIllegalArgumentException if bandwidth does not lie in the interval [0,1].
     * @throws MathIllegalArgumentException if {@code robustnessIters} is negative.
     * @see #LoessInterpolator(double, int)
     */
    public LoessInterpolator(double bandwidth, int robustnessIters, double accuracy) throws MathIllegalArgumentException {
        if (bandwidth < 0 || bandwidth > 1) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.BANDWIDTH, bandwidth, 0, 1);
        }
        this.bandwidth = bandwidth;
        if (robustnessIters < 0) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.ROBUSTNESS_ITERATIONS, robustnessIters);
        }
        this.robustnessIters = robustnessIters;
        this.accuracy = accuracy;
    }

    /**
     * Compute an interpolating function by performing a loess fit
     * on the data at the original abscissae and then building a cubic spline
     * with a
     * {@link org.hipparchus.analysis.interpolation.SplineInterpolator}
     * on the resulting fit.
     *
     * @param xval the arguments for the interpolation points
     * @param yval the values for the interpolation points
     * @return A cubic spline built upon a loess fit to the data at the original abscissae
     * @throws MathIllegalArgumentException if {@code xval} not sorted in
     * strictly increasing order.
     * @throws MathIllegalArgumentException if {@code xval} and {@code yval} have
     * different sizes.
     * @throws MathIllegalArgumentException if {@code xval} or {@code yval} has zero size.
     * @throws MathIllegalArgumentException if any of the arguments and values are
     * not finite real numbers.
     * @throws MathIllegalArgumentException if the bandwidth is too small to
     * accomodate the size of the input data (i.e. the bandwidth must be
     * larger than 2/n).
     */
    @Override
    public final PolynomialSplineFunction interpolate(final double[] xval, final double[] yval) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute a weighted loess fit on the data at the original abscissae.
     *
     * @param xval Arguments for the interpolation points.
     * @param yval Values for the interpolation points.
     * @param weights point weights: coefficients by which the robustness weight
     * of a point is multiplied.
     * @return the values of the loess fit at corresponding original abscissae.
     * @throws MathIllegalArgumentException if {@code xval} not sorted in
     * strictly increasing order.
     * @throws MathIllegalArgumentException if {@code xval} and {@code yval} have
     * different sizes.
     * @throws MathIllegalArgumentException if {@code xval} or {@code yval} has zero size.
     * @throws MathIllegalArgumentException if any of the arguments and values are
     *     not finite real numbers.
     * @throws MathIllegalArgumentException if the bandwidth is too small to
     * accomodate the size of the input data (i.e. the bandwidth must be
     * larger than 2/n).
     */
    public final double[] smooth(final double[] xval, final double[] yval, final double[] weights) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute a loess fit on the data at the original abscissae.
     *
     * @param xval the arguments for the interpolation points
     * @param yval the values for the interpolation points
     * @return values of the loess fit at corresponding original abscissae
     * @throws MathIllegalArgumentException if {@code xval} not sorted in
     * strictly increasing order.
     * @throws MathIllegalArgumentException if {@code xval} and {@code yval} have
     * different sizes.
     * @throws MathIllegalArgumentException if {@code xval} or {@code yval} has zero size.
     * @throws MathIllegalArgumentException if any of the arguments and values are
     * not finite real numbers.
     * @throws MathIllegalArgumentException if the bandwidth is too small to
     * accomodate the size of the input data (i.e. the bandwidth must be
     * larger than 2/n).
     */
    public final double[] smooth(final double[] xval, final double[] yval) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Given an index interval into xval that embraces a certain number of
     * points closest to {@code xval[i-1]}, update the interval so that it
     * embraces the same number of points closest to {@code xval[i]},
     * ignoring zero weights.
     *
     * @param xval Arguments array.
     * @param weights Weights array.
     * @param i Index around which the new interval should be computed.
     * @param bandwidthInterval a two-element array {left, right} such that:
     * {@code (left==0 or xval[i] - xval[left-1] > xval[right] - xval[i])}
     * and
     * {@code (right==xval.length-1 or xval[right+1] - xval[i] > xval[i] - xval[left])}.
     * The array will be updated.
     */
    private static void updateBandwidthInterval(final double[] xval, final double[] weights, final int i, final int[] bandwidthInterval) {
        final int left = bandwidthInterval[0];
        final int right = bandwidthInterval[1];
        // The right edge should be adjusted if the next point to the right
        // is closer to xval[i] than the leftmost point of the current interval
        int nextRight = nextNonzero(weights, right);
        if (nextRight < xval.length && xval[nextRight] - xval[i] < xval[i] - xval[left]) {
            int nextLeft = nextNonzero(weights, bandwidthInterval[0]);
            bandwidthInterval[0] = nextLeft;
            bandwidthInterval[1] = nextRight;
        }
    }

    /**
     * Return the smallest index {@code j} such that
     * {@code j > i && (j == weights.length || weights[j] != 0)}.
     *
     * @param weights Weights array.
     * @param i Index from which to start search.
     * @return the smallest compliant index.
     */
    private static int nextNonzero(final double[] weights, final int i) {
        int j = i + 1;
        while (j < weights.length && weights[j] == 0) {
            ++j;
        }
        return j;
    }

    /**
     * Compute the
     * <a href="http://en.wikipedia.org/wiki/Local_regression#Weight_function">tricube</a>
     * weight function
     *
     * @param x Argument.
     * @return <code>(1 - |x|<sup>3</sup>)<sup>3</sup></code> for |x| &lt; 1, 0 otherwise.
     */
    private static double tricube(final double x) {
        final double absX = FastMath.abs(x);
        if (absX >= 1.0) {
            return 0.0;
        }
        final double tmp = 1 - absX * absX * absX;
        return tmp * tmp * tmp;
    }

    /**
     * Check that all elements of an array are finite real numbers.
     *
     * @param values Values array.
     * @throws org.hipparchus.exception.MathIllegalArgumentException
     * if one of the values is not a finite real number.
     */
    private static void checkAllFiniteReal(final double[] values) {
        for (double value : values) {
            MathUtils.checkFinite(value);
        }
    }
}
