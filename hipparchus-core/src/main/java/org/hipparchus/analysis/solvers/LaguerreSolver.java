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
package org.hipparchus.analysis.solvers;

import org.hipparchus.analysis.polynomials.PolynomialFunction;
import org.hipparchus.complex.Complex;
import org.hipparchus.complex.ComplexUtils;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.MathIllegalStateException;
import org.hipparchus.exception.NullArgumentException;
import org.hipparchus.util.FastMath;

/**
 * Implements the <a href="http://mathworld.wolfram.com/LaguerresMethod.html">
 * Laguerre's Method</a> for root finding of real coefficient polynomials.
 * For reference, see
 * <blockquote>
 *  <b>A First Course in Numerical Analysis</b>,
 *  ISBN 048641454X, chapter 8.
 * </blockquote>
 * Laguerre's method is global in the sense that it can start with any initial
 * approximation and be able to solve all roots from that point.
 * The algorithm requires a bracketing condition.
 */
public class LaguerreSolver extends AbstractPolynomialSolver {

    /**
     * Default absolute accuracy.
     */
    private static final double DEFAULT_ABSOLUTE_ACCURACY = 1e-6;

    /**
     * Complex solver.
     */
    private final ComplexSolver complexSolver = new ComplexSolver();

    /**
     * Construct a solver with default accuracy (1e-6).
     */
    public LaguerreSolver() {
        this(DEFAULT_ABSOLUTE_ACCURACY);
    }

    /**
     * Construct a solver.
     *
     * @param absoluteAccuracy Absolute accuracy.
     */
    public LaguerreSolver(double absoluteAccuracy) {
        super(absoluteAccuracy);
    }

    /**
     * Construct a solver.
     *
     * @param relativeAccuracy Relative accuracy.
     * @param absoluteAccuracy Absolute accuracy.
     */
    public LaguerreSolver(double relativeAccuracy, double absoluteAccuracy) {
        super(relativeAccuracy, absoluteAccuracy);
    }

    /**
     * Construct a solver.
     *
     * @param relativeAccuracy Relative accuracy.
     * @param absoluteAccuracy Absolute accuracy.
     * @param functionValueAccuracy Function value accuracy.
     */
    public LaguerreSolver(double relativeAccuracy, double absoluteAccuracy, double functionValueAccuracy) {
        super(relativeAccuracy, absoluteAccuracy, functionValueAccuracy);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double doSolve() throws MathIllegalArgumentException, MathIllegalStateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Find a real root in the given interval.
     *
     * Despite the bracketing condition, the root returned by
     * {@link LaguerreSolver.ComplexSolver#solve(Complex[],Complex)} may
     * not be a real zero inside {@code [min, max]}.
     * For example, <code> p(x) = x<sup>3</sup> + 1, </code>
     * with {@code min = -2}, {@code max = 2}, {@code initial = 0}.
     * When it occurs, this code calls
     * {@link LaguerreSolver.ComplexSolver#solveAll(Complex[],Complex)}
     * in order to obtain all roots and picks up one real root.
     *
     * @param lo Lower bound of the search interval.
     * @param hi Higher bound of the search interval.
     * @return the point at which the function value is zero.
     */
    private double laguerre(double lo, double hi) {
        final Complex[] c = ComplexUtils.convertToComplex(getCoefficients());
        final Complex initial = new Complex(0.5 * (lo + hi), 0);
        final Complex z = complexSolver.solve(c, initial);
        if (complexSolver.isRoot(lo, hi, z)) {
            return z.getReal();
        } else {
            double r = Double.NaN;
            // Solve all roots and select the one we are seeking.
            Complex[] root = complexSolver.solveAll(c, initial);
            for (Complex complex : root) {
                if (complexSolver.isRoot(lo, hi, complex)) {
                    r = complex.getReal();
                    break;
                }
            }
            return r;
        }
    }

    /**
     * Find all complex roots for the polynomial with the given
     * coefficients, starting from the given initial value.
     * <p>
     * Note: This method is not part of the API of {@link BaseUnivariateSolver}.</p>
     *
     * @param coefficients Polynomial coefficients.
     * @param initial Start value.
     * @return the point at which the function value is zero.
     * @throws org.hipparchus.exception.MathIllegalStateException
     * if the maximum number of evaluations is exceeded.
     * @throws NullArgumentException if the {@code coefficients} is
     * {@code null}.
     * @throws MathIllegalArgumentException if the {@code coefficients} array is empty.
     */
    public Complex[] solveAllComplex(double[] coefficients, double initial) throws MathIllegalArgumentException, NullArgumentException, MathIllegalStateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Find all complex roots for the polynomial with the given
     * coefficients, starting from the given initial value.
     * <p>
     * Note: This method is not part of the API of {@link BaseUnivariateSolver}.</p>
     *
     * @param coefficients Polynomial coefficients.
     * @param maxEval Maximum number of evaluations.
     * @param initial Start value.
     * @return the point at which the function value is zero.
     * @throws org.hipparchus.exception.MathIllegalStateException
     * if the maximum number of evaluations is exceeded.
     * @throws NullArgumentException if the {@code coefficients} is
     * {@code null}.
     * @throws MathIllegalArgumentException if the {@code coefficients} array is empty.
     */
    public Complex[] solveAllComplex(double[] coefficients, int maxEval, double initial) throws MathIllegalArgumentException, NullArgumentException, MathIllegalStateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Find a complex root for the polynomial with the given coefficients,
     * starting from the given initial value.
     * <p>
     * Note: This method is not part of the API of {@link BaseUnivariateSolver}.</p>
     *
     * @param coefficients Polynomial coefficients.
     * @param initial Start value.
     * @return the point at which the function value is zero.
     * @throws org.hipparchus.exception.MathIllegalStateException
     * if the maximum number of evaluations is exceeded.
     * @throws NullArgumentException if the {@code coefficients} is
     * {@code null}.
     * @throws MathIllegalArgumentException if the {@code coefficients} array is empty.
     */
    public Complex solveComplex(double[] coefficients, double initial) throws MathIllegalArgumentException, NullArgumentException, MathIllegalStateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Class for searching all (complex) roots.
     */
    private class ComplexSolver {

        /**
         * Check whether the given complex root is actually a real zero
         * in the given interval, within the solver tolerance level.
         *
         * @param min Lower bound for the interval.
         * @param max Upper bound for the interval.
         * @param z Complex root.
         * @return {@code true} if z is a real zero.
         */
        public boolean isRoot(double min, double max, Complex z) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Find all complex roots for the polynomial with the given
         * coefficients, starting from the given initial value.
         *
         * @param coefficients Polynomial coefficients.
         * @param initial Start value.
         * @return the point at which the function value is zero.
         * @throws org.hipparchus.exception.MathIllegalStateException
         * if the maximum number of evaluations is exceeded.
         * @throws NullArgumentException if the {@code coefficients} is
         * {@code null}.
         * @throws MathIllegalArgumentException if the {@code coefficients} array is empty.
         */
        public Complex[] solveAll(Complex[] coefficients, Complex initial) throws MathIllegalArgumentException, NullArgumentException, MathIllegalStateException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Find a complex root for the polynomial with the given coefficients,
         * starting from the given initial value.
         *
         * @param coefficients Polynomial coefficients.
         * @param initial Start value.
         * @return the point at which the function value is zero.
         * @throws org.hipparchus.exception.MathIllegalStateException
         * if the maximum number of evaluations is exceeded.
         * @throws NullArgumentException if the {@code coefficients} is
         * {@code null}.
         * @throws MathIllegalArgumentException if the {@code coefficients} array is empty.
         */
        public Complex solve(Complex[] coefficients, Complex initial) throws MathIllegalArgumentException, NullArgumentException, MathIllegalStateException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
