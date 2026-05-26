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

import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.MathIllegalStateException;
import org.hipparchus.util.FastMath;

/**
 * This class implements the <a href="http://mathworld.wolfram.com/MullersMethod.html">
 * Muller's Method</a> for root finding of real univariate functions. For
 * reference, see <b>Elementary Numerical Analysis</b>, ISBN 0070124477,
 * chapter 3.
 * <p>
 * Muller's method applies to both real and complex functions, but here we
 * restrict ourselves to real functions.
 * This class differs from {@link MullerSolver} in the way it avoids complex
 * operations.</p><p>
 * Except for the initial [min, max], it does not require bracketing
 * condition, e.g. f(x0), f(x1), f(x2) can have the same sign. If a complex
 * number arises in the computation, we simply use its modulus as a real
 * approximation.</p>
 * <p>
 * Because the interval may not be bracketing, the bisection alternative is
 * not applicable here. However in practice our treatment usually works
 * well, especially near real zeroes where the imaginary part of the complex
 * approximation is often negligible.</p>
 * <p>
 * The formulas here do not use divided differences directly.</p>
 *
 * @see MullerSolver
 */
public class MullerSolver2 extends AbstractUnivariateSolver {

    /**
     * Default absolute accuracy.
     */
    private static final double DEFAULT_ABSOLUTE_ACCURACY = 1e-6;

    /**
     * Construct a solver with default accuracy (1e-6).
     */
    public MullerSolver2() {
        this(DEFAULT_ABSOLUTE_ACCURACY);
    }

    /**
     * Construct a solver.
     *
     * @param absoluteAccuracy Absolute accuracy.
     */
    public MullerSolver2(double absoluteAccuracy) {
        super(absoluteAccuracy);
    }

    /**
     * Construct a solver.
     *
     * @param relativeAccuracy Relative accuracy.
     * @param absoluteAccuracy Absolute accuracy.
     */
    public MullerSolver2(double relativeAccuracy, double absoluteAccuracy) {
        super(relativeAccuracy, absoluteAccuracy);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected double doSolve() throws MathIllegalArgumentException, MathIllegalStateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
