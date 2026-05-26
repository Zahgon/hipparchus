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

import org.hipparchus.analysis.UnivariateFunction;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.MathIllegalStateException;
import org.hipparchus.exception.MathRuntimeException;
import org.hipparchus.util.FastMath;

/**
 * Base class for all bracketing <em>Secant</em>-based methods for root-finding
 * (approximating a zero of a univariate real function).
 *
 * <p>Implementation of the {@link RegulaFalsiSolver <em>Regula Falsi</em>} and
 * {@link IllinoisSolver <em>Illinois</em>} methods is based on the
 * following article: M. Dowell and P. Jarratt,
 * <em>A modified regula falsi method for computing the root of an
 * equation</em>, BIT Numerical Mathematics, volume 11, number 2,
 * pages 168-174, Springer, 1971.</p>
 *
 * <p>Implementation of the {@link PegasusSolver <em>Pegasus</em>} method is
 * based on the following article: M. Dowell and P. Jarratt,
 * <em>The "Pegasus" method for computing the root of an equation</em>,
 * BIT Numerical Mathematics, volume 12, number 4, pages 503-508, Springer,
 * 1972.</p>
 *
 * <p>The {@link SecantSolver <em>Secant</em>} method is <em>not</em> a
 * bracketing method, so it is not implemented here. It has a separate
 * implementation.</p>
 */
public abstract class BaseSecantSolver extends AbstractUnivariateSolver implements BracketedUnivariateSolver<UnivariateFunction> {

    /**
     * Default absolute accuracy.
     */
    protected static final double DEFAULT_ABSOLUTE_ACCURACY = 1e-6;

    /**
     * The kinds of solutions that the algorithm may accept.
     */
    private AllowedSolution allowed;

    /**
     * The <em>Secant</em>-based root-finding method to use.
     */
    private final Method method;

    /**
     * Construct a solver.
     *
     * @param absoluteAccuracy Absolute accuracy.
     * @param method <em>Secant</em>-based root-finding method to use.
     */
    protected BaseSecantSolver(final double absoluteAccuracy, final Method method) {
        super(absoluteAccuracy);
        this.allowed = AllowedSolution.ANY_SIDE;
        this.method = method;
    }

    /**
     * Construct a solver.
     *
     * @param relativeAccuracy Relative accuracy.
     * @param absoluteAccuracy Absolute accuracy.
     * @param method <em>Secant</em>-based root-finding method to use.
     */
    protected BaseSecantSolver(final double relativeAccuracy, final double absoluteAccuracy, final Method method) {
        super(relativeAccuracy, absoluteAccuracy);
        this.allowed = AllowedSolution.ANY_SIDE;
        this.method = method;
    }

    /**
     * Construct a solver.
     *
     * @param relativeAccuracy Maximum relative error.
     * @param absoluteAccuracy Maximum absolute error.
     * @param functionValueAccuracy Maximum function value error.
     * @param method <em>Secant</em>-based root-finding method to use
     */
    protected BaseSecantSolver(final double relativeAccuracy, final double absoluteAccuracy, final double functionValueAccuracy, final Method method) {
        super(relativeAccuracy, absoluteAccuracy, functionValueAccuracy);
        this.allowed = AllowedSolution.ANY_SIDE;
        this.method = method;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double solve(final int maxEval, final UnivariateFunction f, final double min, final double max, final AllowedSolution allowedSolution) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double solve(final int maxEval, final UnivariateFunction f, final double min, final double max, final double startValue, final AllowedSolution allowedSolution) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double solve(final int maxEval, final UnivariateFunction f, final double min, final double max, final double startValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Interval solveInterval(final int maxEval, final UnivariateFunction f, final double min, final double max, final double startValue) throws MathIllegalArgumentException, MathIllegalStateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * @throws MathIllegalStateException if the algorithm failed due to finite
     * precision.
     */
    @Override
    protected final double doSolve() throws MathIllegalStateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Find a root and return the containing interval.
     *
     * @return an interval containing the root such that the selected end point meets the
     * convergence criteria.
     * @throws MathIllegalStateException if convergence fails.
     */
    protected final Interval doSolveInterval() throws MathIllegalStateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * <em>Secant</em>-based root-finding methods.
     */
    protected enum Method {

        /**
         * The {@link RegulaFalsiSolver <em>Regula Falsi</em>} or
         * <em>False Position</em> method.
         */
        REGULA_FALSI,
        /**
         * The {@link IllinoisSolver <em>Illinois</em>} method.
         */
        ILLINOIS,
        /**
         * The {@link PegasusSolver <em>Pegasus</em>} method.
         */
        PEGASUS
    }
}
