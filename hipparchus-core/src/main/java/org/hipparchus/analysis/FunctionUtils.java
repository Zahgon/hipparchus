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
package org.hipparchus.analysis;

import org.hipparchus.analysis.differentiation.DSFactory;
import org.hipparchus.analysis.differentiation.Derivative;
import org.hipparchus.analysis.differentiation.DerivativeStructure;
import org.hipparchus.analysis.differentiation.MultivariateDifferentiableFunction;
import org.hipparchus.analysis.differentiation.UnivariateDifferentiableFunction;
import org.hipparchus.analysis.function.Identity;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.util.MathArrays;
import org.hipparchus.util.MathUtils;

/**
 * Utilities for manipulating function objects.
 */
public class FunctionUtils {

    /**
     * Class only contains static methods.
     */
    private FunctionUtils() {
    }

    /**
     * Composes functions.
     * <p>
     * The functions in the argument list are composed sequentially, in the
     * given order.  For example, compose(f1,f2,f3) acts like f1(f2(f3(x))).</p>
     *
     * @param f List of functions.
     * @return the composite function.
     */
    public static UnivariateFunction compose(final UnivariateFunction... f) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Composes functions.
     * <p>
     * The functions in the argument list are composed sequentially, in the
     * given order.  For example, compose(f1,f2,f3) acts like f1(f2(f3(x))).</p>
     *
     * @param f List of functions.
     * @return the composite function.
     */
    public static UnivariateDifferentiableFunction compose(final UnivariateDifferentiableFunction... f) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Adds functions.
     *
     * @param f List of functions.
     * @return a function that computes the sum of the functions.
     */
    public static UnivariateFunction add(final UnivariateFunction... f) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Adds functions.
     *
     * @param f List of functions.
     * @return a function that computes the sum of the functions.
     */
    public static UnivariateDifferentiableFunction add(final UnivariateDifferentiableFunction... f) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Multiplies functions.
     *
     * @param f List of functions.
     * @return a function that computes the product of the functions.
     */
    public static UnivariateFunction multiply(final UnivariateFunction... f) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Multiplies functions.
     *
     * @param f List of functions.
     * @return a function that computes the product of the functions.
     */
    public static UnivariateDifferentiableFunction multiply(final UnivariateDifferentiableFunction... f) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the univariate function
     * {@code h(x) = combiner(f(x), g(x)).}
     *
     * @param combiner Combiner function.
     * @param f Function.
     * @param g Function.
     * @return the composite function.
     */
    public static UnivariateFunction combine(final BivariateFunction combiner, final UnivariateFunction f, final UnivariateFunction g) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns a MultivariateFunction h(x[]) defined by <pre> <code>
     * h(x[]) = combiner(...combiner(combiner(initialValue,f(x[0])),f(x[1]))...),f(x[x.length-1]))
     * </code></pre>
     *
     * @param combiner Combiner function.
     * @param f Function.
     * @param initialValue Initial value.
     * @return a collector function.
     */
    public static MultivariateFunction collector(final BivariateFunction combiner, final UnivariateFunction f, final double initialValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns a MultivariateFunction h(x[]) defined by <pre> <code>
     * h(x[]) = combiner(...combiner(combiner(initialValue,x[0]),x[1])...),x[x.length-1])
     * </code></pre>
     *
     * @param combiner Combiner function.
     * @param initialValue Initial value.
     * @return a collector function.
     */
    public static MultivariateFunction collector(final BivariateFunction combiner, final double initialValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Creates a unary function by fixing the first argument of a binary function.
     *
     * @param f Binary function.
     * @param fixed value to which the first argument of {@code f} is set.
     * @return the unary function h(x) = f(fixed, x)
     */
    public static UnivariateFunction fix1stArgument(final BivariateFunction f, final double fixed) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Creates a unary function by fixing the second argument of a binary function.
     *
     * @param f Binary function.
     * @param fixed value to which the second argument of {@code f} is set.
     * @return the unary function h(x) = f(x, fixed)
     */
    public static UnivariateFunction fix2ndArgument(final BivariateFunction f, final double fixed) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Samples the specified univariate real function on the specified interval.
     * <p>
     * The interval is divided equally into {@code n} sections and sample points
     * are taken from {@code min} to {@code max - (max - min) / n}; therefore
     * {@code f} is not sampled at the upper bound {@code max}.</p>
     *
     * @param f Function to be sampled
     * @param min Lower bound of the interval (included).
     * @param max Upper bound of the interval (excluded).
     * @param n Number of sample points.
     * @return the array of samples.
     * @throws MathIllegalArgumentException if the lower bound {@code min} is
     * greater than, or equal to the upper bound {@code max}.
     * @throws MathIllegalArgumentException if the number of sample points
     * {@code n} is negative.
     */
    public static double[] sample(UnivariateFunction f, double min, double max, int n) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convert regular functions to {@link UnivariateDifferentiableFunction}.
     * <p>
     * This method handle the case with one free parameter and several derivatives.
     * For the case with several free parameters and only first order derivatives,
     * see {@link #toDifferentiable(MultivariateFunction, MultivariateVectorFunction)}.
     * There are no direct support for intermediate cases, with several free parameters
     * and order 2 or more derivatives, as is would be difficult to specify all the
     * cross derivatives.
     * </p>
     * <p>
     * Note that the derivatives are expected to be computed only with respect to the
     * raw parameter x of the base function, i.e. they are df/dx, df<sup>2</sup>/dx<sup>2</sup>, ...
     * Even if the built function is later used in a composition like f(sin(t)), the provided
     * derivatives should <em>not</em> apply the composition with sine and its derivatives by
     * themselves. The composition will be done automatically here and the result will properly
     * contain f(sin(t)), df(sin(t))/dt, df<sup>2</sup>(sin(t))/dt<sup>2</sup> despite the
     * provided derivatives functions know nothing about the sine function.
     * </p>
     * @param f base function f(x)
     * @param derivatives derivatives of the base function, in increasing differentiation order
     * @return a differentiable function with value and all specified derivatives
     * @see #toDifferentiable(MultivariateFunction, MultivariateVectorFunction)
     * @see #derivative(UnivariateDifferentiableFunction, int)
     */
    public static UnivariateDifferentiableFunction toDifferentiable(final UnivariateFunction f, final UnivariateFunction... derivatives) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convert regular functions to {@link MultivariateDifferentiableFunction}.
     * <p>
     * This method handle the case with several free parameters and only first order derivatives.
     * For the case with one free parameter and several derivatives,
     * see {@link #toDifferentiable(UnivariateFunction, UnivariateFunction...)}.
     * There are no direct support for intermediate cases, with several free parameters
     * and order 2 or more derivatives, as is would be difficult to specify all the
     * cross derivatives.
     * </p>
     * <p>
     * Note that the gradient is expected to be computed only with respect to the
     * raw parameter x of the base function, i.e. it is df/dx<sub>1</sub>, df/dx<sub>2</sub>, ...
     * Even if the built function is later used in a composition like f(sin(t), cos(t)), the provided
     * gradient should <em>not</em> apply the composition with sine or cosine and their derivative by
     * itself. The composition will be done automatically here and the result will properly
     * contain f(sin(t), cos(t)), df(sin(t), cos(t))/dt despite the provided derivatives functions
     * know nothing about the sine or cosine functions.
     * </p>
     * @param f base function f(x)
     * @param gradient gradient of the base function
     * @return a differentiable function with value and gradient
     * @see #toDifferentiable(UnivariateFunction, UnivariateFunction...)
     * @see #derivative(MultivariateDifferentiableFunction, int[])
     */
    public static MultivariateDifferentiableFunction toDifferentiable(final MultivariateFunction f, final MultivariateVectorFunction gradient) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convert an {@link UnivariateDifferentiableFunction} to an
     * {@link UnivariateFunction} computing n<sup>th</sup> order derivative.
     * <p>
     * This converter is only a convenience method. Beware computing only one derivative does
     * not save any computation as the original function will really be called under the hood.
     * The derivative will be extracted from the full {@link DerivativeStructure} result.
     * </p>
     * @param f original function, with value and all its derivatives
     * @param order of the derivative to extract
     * @return function computing the derivative at required order
     * @see #derivative(MultivariateDifferentiableFunction, int[])
     * @see #toDifferentiable(UnivariateFunction, UnivariateFunction...)
     */
    public static UnivariateFunction derivative(final UnivariateDifferentiableFunction f, final int order) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convert an {@link MultivariateDifferentiableFunction} to an
     * {@link MultivariateFunction} computing n<sup>th</sup> order derivative.
     * <p>
     * This converter is only a convenience method. Beware computing only one derivative does
     * not save any computation as the original function will really be called under the hood.
     * The derivative will be extracted from the full {@link DerivativeStructure} result.
     * </p>
     * @param f original function, with value and all its derivatives
     * @param orders of the derivative to extract, for each free parameters
     * @return function computing the derivative at required order
     * @see #derivative(UnivariateDifferentiableFunction, int)
     * @see #toDifferentiable(MultivariateFunction, MultivariateVectorFunction)
     */
    public static MultivariateFunction derivative(final MultivariateDifferentiableFunction f, final int[] orders) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
