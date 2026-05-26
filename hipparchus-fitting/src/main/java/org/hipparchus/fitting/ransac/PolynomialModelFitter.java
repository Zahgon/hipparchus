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
package org.hipparchus.fitting.ransac;

import java.util.List;
import java.util.stream.IntStream;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.linear.Array2DRowRealMatrix;
import org.hipparchus.linear.ArrayRealVector;
import org.hipparchus.linear.RealMatrix;
import org.hipparchus.linear.RealVector;
import org.hipparchus.linear.SingularValueDecomposition;
import org.hipparchus.util.FastMath;

/**
 * Fitter for polynomial model.
 * @since 4.1
 */
public class PolynomialModelFitter implements IModelFitter<PolynomialModelFitter.Model> {

    /**
     * Class representing the polynomial model to fit.
     */
    public static final class Model {

        /**
         * Coefficients of the polynomial model.
         */
        private final double[] coefficients;

        /**
         * Constructor.
         * @param coefficients coefficients of the polynomial model
         */
        public Model(final double[] coefficients) {
            this.coefficients = coefficients.clone();
        }

        /**
         * Predicts the model value for the input point.
         * @param x point
         * @return the model value for the given point
         */
        public double predict(final double x) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Get the coefficients of the polynomial model.
         * <p>
         * The coefficients are sort by degree.
         * For instance, for a quadratic equation the coefficients are as followed:
         * <code>y = coefficients[2] * x * x + coefficients[1] * x + coefficients[0]</code>
         * </p>
         * @return the coefficients of the polynomial model
         */
        public double[] getCoefficients() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Degree of the polynomial to fit.
     */
    private final int degree;

    /**
     * Constructor.
     * @param degree degree of the polynomial to fit
     */
    public PolynomialModelFitter(final int degree) {
        if (degree < 1) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.NUMBER_TOO_SMALL, degree, 1);
        }
        this.degree = degree;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Model fitModel(final List<Fittable> points) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public double computeModelError(final Model model, final Fittable point) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Verifies that the size of the set of observed data is consistent with the degree of the polynomial to fit.
     * @param size size of the set of observed data
     */
    private void checkSampleSize(final int size) {
        if (size < degree + 1) {
            throw new IllegalArgumentException(String.format("Not enough points to fit polynomial model, at least %d points are required", degree + 1));
        }
    }
}
