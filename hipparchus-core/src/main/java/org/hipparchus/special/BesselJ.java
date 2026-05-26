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
package org.hipparchus.special;

import org.hipparchus.analysis.UnivariateFunction;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.MathIllegalStateException;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.SinCos;

/**
 * This class provides computation methods related to Bessel
 * functions of the first kind. Detailed descriptions of these functions are
 * available in <a
 * href="http://en.wikipedia.org/wiki/Bessel_function">Wikipedia</a>, <a
 * href="http://en.wikipedia.org/wiki/Abramowitz_and_Stegun">Abramowitz and
 * Stegun</a> (Ch. 9-11), and <a href="http://dlmf.nist.gov/">DLMF</a> (Ch. 10).
 * <p>
 * This implementation is based on the rjbesl Fortran routine at
 * <a href="http://www.netlib.org/specfun/rjbesl">Netlib</a>.
 * </p>
 * <p>
 * From the Fortran code: </p>
 * <p>
 * This program is based on a program written by David J. Sookne (2) that
 * computes values of the Bessel functions J or I of real argument and integer
 * order. Modifications include the restriction of the computation to the J
 * Bessel function of non-negative real argument, the extension of the
 * computation to arbitrary positive order, and the elimination of most
 * underflow.</p>
 * <p>
 * References:</p>
 * <ul>
 * <li>"A Note on Backward Recurrence Algorithms," Olver, F. W. J., and Sookne,
 * D. J., Math. Comp. 26, 1972, pp 941-947.</li>
 * <li>"Bessel Functions of Real Argument and Integer Order," Sookne, D. J., NBS
 * Jour. of Res. B. 77B, 1973, pp 125-132.</li>
 * </ul>
 */
public class BesselJ implements UnivariateFunction {

    // ---------------------------------------------------------------------
    // Mathematical constants
    // ---------------------------------------------------------------------
    /**
     * -2 / pi
     */
    private static final double PI2 = 0.636619772367581343075535;

    /**
     * first few significant digits of 2pi
     */
    private static final double TOWPI1 = 6.28125;

    /**
     * 2pi - TWOPI1 to working precision
     */
    private static final double TWOPI2 = 1.935307179586476925286767e-3;

    /**
     * TOWPI1 + TWOPI2
     */
    private static final double TWOPI = TOWPI1 + TWOPI2;

    // ---------------------------------------------------------------------
    // Machine-dependent parameters
    // ---------------------------------------------------------------------
    /**
     * 10.0^K, where K is the largest integer such that ENTEN is
     * machine-representable in working precision
     */
    private static final double ENTEN = 1.0e308;

    /**
     * Decimal significance desired. Should be set to (INT(log_{10}(2) * (it)+1)).
     * Setting NSIG lower will result in decreased accuracy while setting
     * NSIG higher will increase CPU time without increasing accuracy.
     * The truncation error is limited to a relative error of
     * T=.5(10^(-NSIG)).
     */
    private static final double ENSIG = 1.0e16;

    /**
     * 10.0 ** (-K) for the smallest integer K such that K >= NSIG/4
     */
    private static final double RTNSIG = 1.0e-4;

    /**
     * Smallest ABS(X) such that X/4 does not underflow
     */
    private static final double ENMTEN = 8.90e-308;

    /**
     * Minimum acceptable value for x
     */
    private static final double X_MIN = 0.0;

    /**
     * Upper limit on the magnitude of x. If abs(x) = n, then at least
     * n iterations of the backward recursion will be executed. The value of
     * 10.0 ** 4 is used on every machine.
     */
    private static final double X_MAX = 1.0e4;

    /**
     * First 25 factorials as doubles
     */
    private static final double[] FACT = { 1.0, 1.0, 2.0, 6.0, 24.0, 120.0, 720.0, 5040.0, 40320.0, 362880.0, 3628800.0, 39916800.0, 479001600.0, 6227020800.0, 87178291200.0, 1.307674368e12, 2.0922789888e13, 3.55687428096e14, 6.402373705728e15, 1.21645100408832e17, 2.43290200817664e18, 5.109094217170944e19, 1.12400072777760768e21, 2.585201673888497664e22, 6.2044840173323943936e23 };

    /**
     * Order of the function computed when {@link #value(double)} is used
     */
    private final double order;

    /**
     * Create a new BesselJ with the given order.
     *
     * @param order order of the function computed when using {@link #value(double)}.
     */
    public BesselJ(double order) {
        this.order = order;
    }

    /**
     * Returns the value of the constructed Bessel function of the first kind,
     * for the passed argument.
     *
     * @param x Argument
     * @return Value of the Bessel function at x
     * @throws MathIllegalArgumentException if {@code x} is too large relative to {@code order}
     * @throws MathIllegalStateException if the algorithm fails to converge
     */
    @Override
    public double value(double x) throws MathIllegalArgumentException, MathIllegalStateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the first Bessel function, \(J_{order}(x)\).
     *
     * @param order Order of the Bessel function
     * @param x Argument
     * @return Value of the Bessel function of the first kind, \(J_{order}(x)\)
     * @throws MathIllegalArgumentException if {@code x} is too large relative to {@code order}
     * @throws MathIllegalStateException if the algorithm fails to converge
     */
    public static double value(double order, double x) throws MathIllegalArgumentException, MathIllegalStateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Encapsulates the results returned by {@link BesselJ#rjBesl(double, double, int)}.
     * <p>
     * {@link #getVals()} returns the computed function values.
     * {@link #getnVals()} is the number of values among those returned by {@link #getnVals()}
     * that can be considered accurate.
     * </p>
     * <ul>
     * <li>nVals &lt; 0: An argument is out of range. For example, nb &lt;= 0, alpha
     * &lt; 0 or &gt; 1, or x is too large. In this case, b(0) is set to zero, the
     * remainder of the b-vector is not calculated, and nVals is set to
     * MIN(nb,0) - 1 so that nVals != nb.</li>
     * <li>nb &gt; nVals &gt; 0: Not all requested function values could be calculated
     * accurately. This usually occurs because nb is much larger than abs(x). In
     * this case, b(n) is calculated to the desired accuracy for n &lt; nVals, but
     * precision is lost for nVals &lt; n &lt;= nb. If b(n) does not vanish for n &gt;
     * nVals (because it is too small to be represented), and b(n)/b(nVals) =
     * \(10^{-k}\), then only the first NSIG-k significant figures of b(n) can be
     * trusted.</li></ul>
     */
    public static class BesselJResult {

        /**
         * Bessel function values
         */
        private final double[] vals;

        /**
         * Valid value count
         */
        private final int nVals;

        /**
         * Create a new BesselJResult with the given values and valid value count.
         *
         * @param b values
         * @param n count of valid values
         */
        public BesselJResult(double[] b, int n) {
            vals = b.clone();
            nVals = n;
        }

        /**
         * Get computed function values.
         * @return the computed function values
         */
        public double[] getVals() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Get number of valid function values.
         * @return the number of valid function values (normally the same as the
         *         length of the array returned by {@link #getnVals()})
         */
        public int getnVals() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Calculates Bessel functions \(J_{n+alpha}(x)\) for
     * non-negative argument x, and non-negative order n + alpha.
     * <p>
     * Before using the output vector, the user should check that
     * nVals = nb, i.e., all orders have been calculated to the desired accuracy.
     * See BesselResult class javadoc for details on return values.
     * </p>
     * @param x non-negative real argument for which J's are to be calculated
     * @param alpha fractional part of order for which J's or exponentially
     * scaled J's (\(J\cdot e^{x}\)) are to be calculated. 0 &lt;= alpha &lt; 1.0.
     * @param nb integer number of functions to be calculated, nb &gt; 0. The first
     * function calculated is of order alpha, and the last is of order
     * nb - 1 + alpha.
     * @return BesselJResult a vector of the functions
     * \(J_{alpha}(x)\) through \(J_{nb-1+alpha}(x)\), or the corresponding exponentially
     * scaled functions and an integer output variable indicating possible errors
     */
    public static BesselJResult rjBesl(double x, double alpha, int nb) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
