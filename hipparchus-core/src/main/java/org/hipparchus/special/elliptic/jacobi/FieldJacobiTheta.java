/*
 * Licensed to the Hipparchus project under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The Hipparchus project licenses this file to You under the Apache License, Version 2.0
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
package org.hipparchus.special.elliptic.jacobi;

import org.hipparchus.CalculusFieldElement;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.FieldSinCos;

/**
 * Algorithm computing Jacobi theta functions.
 * @param <T> the type of the field elements
 * @since 2.0
 */
public class FieldJacobiTheta<T extends CalculusFieldElement<T>> {

    /**
     * Maximum number of terms in the Fourier series.
     */
    private static final int N_MAX = 100;

    /**
     * Nome.
     */
    private final T q;

    /**
     * q².
     */
    private final T qSquare;

    /**
     * ∜q.
     */
    private final T qFourth;

    /**
     * Simple constructor.
     * <p>
     * The nome {@code q} can be computed using ratios of complete elliptic integrals
     * ({@link org.hipparchus.special.elliptic.legendre.LegendreEllipticIntegral#nome(CalculusFieldElement)
     * LegendreEllipticIntegral.nome(m)} which are themselves defined in term of parameter m,
     * where m=k² and k is the elliptic modulus.
     * </p>
     * @param q nome
     */
    public FieldJacobiTheta(final T q) {
        this.q = q;
        this.qSquare = q.multiply(q);
        this.qFourth = FastMath.sqrt(FastMath.sqrt(q));
    }

    /**
     * Get the nome.
     * @return nome
     */
    public T getQ() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Evaluate the Jacobi theta functions.
     * @param z argument of the functions
     * @return container for the four Jacobi theta functions θ₁(z|τ), θ₂(z|τ), θ₃(z|τ), and θ₄(z|τ)
     */
    public FieldTheta<T> values(final T z) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
