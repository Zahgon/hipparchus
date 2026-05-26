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

import org.hipparchus.CalculusFieldElement;
import org.hipparchus.Field;
import org.hipparchus.util.FastMath;

/**
 * This is a utility class that provides computation methods related to the
 * error functions.
 */
public class Erf {

    /**
     * The number {@code X_CRIT} is used by {@link #erf(double, double)} internally.
     * This number solves {@code erf(x)=0.5} within 1ulp.
     * More precisely, the current implementations of
     * {@link #erf(double)} and {@link #erfc(double)} satisfy:<br>
     * {@code erf(X_CRIT) < 0.5},<br>
     * {@code erf(Math.nextUp(X_CRIT) > 0.5},<br>
     * {@code erfc(X_CRIT) = 0.5}, and<br>
     * {@code erfc(Math.nextUp(X_CRIT) < 0.5}
     */
    private static final double X_CRIT = 0.4769362762044697;

    /**
     * Default constructor.  Prohibit instantiation.
     */
    private Erf() {
    }

    /**
     * Returns the error function.
     *
     * \[
     * \mathrm{erf}(x) = \frac{2}{\sqrt{\pi}} \int_{t=0}^x e^{-t^2}dt
     * \]
     *
     * <p>This implementation computes erf(x) using the
     * {@link Gamma#regularizedGammaP(double, double, double, int) regularized gamma function},
     * following <a href="http://mathworld.wolfram.com/Erf.html"> Erf</a>, equation (3)</p>
     *
     * <p>The value returned is always between -1 and 1 (inclusive).
     * If {@code abs(x) > 40}, then {@code erf(x)} is indistinguishable from
     * either 1 or -1 as a double, so the appropriate extreme value is returned.
     * </p>
     *
     * @param x the value.
     * @return the error function erf(x)
     * @throws org.hipparchus.exception.MathIllegalStateException
     * if the algorithm fails to converge.
     * @see Gamma#regularizedGammaP(double, double, double, int)
     */
    public static double erf(double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the error function.
     *
     * \[
     * \mathrm{erf}(x) = \frac{2}{\sqrt{\pi}} \int_{t=0}^x e^{-t^2}dt
     * \]
     *
     * <p>This implementation computes erf(x) using the
     * {@link Gamma#regularizedGammaP(double, double, double, int) regularized gamma function},
     * following <a href="http://mathworld.wolfram.com/Erf.html"> Erf</a>, equation (3)</p>
     *
     * <p>The value returned is always between -1 and 1 (inclusive).
     * If {@code abs(x) > 40}, then {@code erf(x)} is indistinguishable from
     * either 1 or -1 as a double, so the appropriate extreme value is returned.
     * </p>
     *
     * @param <T> type of the field elements
     * @param x the value.
     * @return the error function erf(x)
     * @throws org.hipparchus.exception.MathIllegalStateException
     * if the algorithm fails to converge.
     * @see Gamma#regularizedGammaP(double, double, double, int)
     */
    public static <T extends CalculusFieldElement<T>> T erf(T x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the complementary error function.
     *
     * \[
     * \mathrm{erfc}(x) =  \frac{2}{\sqrt{\pi}} \int_{t=x}^\infty e^{-t^2}dt = 1 - \mathrm{erf}
     *
     * <p>This implementation computes erfc(x) using the
     * {@link Gamma#regularizedGammaQ(double, double, double, int) regularized gamma function},
     * following <a href="http://mathworld.wolfram.com/Erf.html"> Erf</a>, equation (3).</p>
     *
     * <p>The value returned is always between 0 and 2 (inclusive).
     * If {@code abs(x) > 40}, then {@code erf(x)} is indistinguishable from
     * either 0 or 2 as a double, so the appropriate extreme value is returned.
     * </p>
     *
     * @param x the value
     * @return the complementary error function erfc(x)
     * @throws org.hipparchus.exception.MathIllegalStateException
     * if the algorithm fails to converge.
     * @see Gamma#regularizedGammaQ(double, double, double, int)
     */
    public static double erfc(double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the complementary error function.
     *
     * \[
     * erfc(x) = \frac{2}{\sqrt{\pi}} \int_x^\infty e^{-t^2}dt = 1 - erf(x)
     * \]
     *
     * <p>This implementation computes erfc(x) using the
     * {@link Gamma#regularizedGammaQ(double, double, double, int) regularized gamma function}, following <a
     * href="http://mathworld.wolfram.com/Erf.html"> Erf</a>, equation (3).</p>
     *
     * <p>The value returned is always between 0 and 2 (inclusive).
     * If {@code abs(x) > 40}, then {@code erf(x)} is indistinguishable from either 0 or 2 as a double, so the
     * appropriate extreme value is returned. <b>This implies that the current implementation does not allow the use of
     * {@link org.hipparchus.dfp.Dfp Dfp} with extended precision.</b>
     * </p>
     *
     * @param x the value
     * @param <T> type of the field elements
     *
     * @return the complementary error function erfc(x)
     *
     * @throws org.hipparchus.exception.MathIllegalStateException if the algorithm fails to converge.
     * @see Gamma#regularizedGammaQ(double, double, double, int)
     */
    public static <T extends CalculusFieldElement<T>> T erfc(T x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the difference between erf(x1) and erf(x2).
     * <p>
     * The implementation uses either erf(double) or erfc(double)
     * depending on which provides the most precise result.
     *
     * @param x1 the first value
     * @param x2 the second value
     * @return erf(x2) - erf(x1)
     */
    public static double erf(double x1, double x2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the difference between erf(x1) and erf(x2).
     * <p>
     * The implementation uses either erf(double) or erfc(double)
     * depending on which provides the most precise result.
     *
     * @param x1 the first value
     * @param x2 the second value
     * @param <T> type of the field elements
     *
     * @return erf(x2) - erf(x1)
     */
    public static <T extends CalculusFieldElement<T>> T erf(T x1, T x2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the inverse erf.
     * <p>
     * This implementation is described in the paper:
     * <a href="http://people.maths.ox.ac.uk/gilesm/files/gems_erfinv.pdf">Approximating
     * the erfinv function</a> by Mike Giles, Oxford-Man Institute of Quantitative Finance,
     * which was published in GPU Computing Gems, volume 2, 2010.
     * The source code is available <a href="http://gpucomputing.net/?q=node/1828">here</a>.
     * </p>
     * @param x the value
     * @return t such that x = erf(t)
     */
    public static double erfInv(final double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the inverse erf.
     * <p>
     * This implementation is described in the paper:
     * <a href="http://people.maths.ox.ac.uk/gilesm/files/gems_erfinv.pdf">Approximating
     * the erfinv function</a> by Mike Giles, Oxford-Man Institute of Quantitative Finance,
     * which was published in GPU Computing Gems, volume 2, 2010.
     * The source code is available <a href="http://gpucomputing.net/?q=node/1828">here</a>.
     * </p>
     * @param <T> type of the filed elements
     * @param x the value
     * @return t such that x = erf(t)
     */
    public static <T extends CalculusFieldElement<T>> T erfInv(final T x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the inverse erfc.
     * @param x the value
     * @return t such that x = erfc(t)
     */
    public static double erfcInv(final double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the inverse erfc.
     * @param x the value
     * @param <T> type of the field elements
     * @return t such that x = erfc(t)
     */
    public static <T extends CalculusFieldElement<T>> T erfcInv(final T x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
