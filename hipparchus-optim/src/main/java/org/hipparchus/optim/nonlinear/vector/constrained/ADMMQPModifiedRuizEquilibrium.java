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
package org.hipparchus.optim.nonlinear.vector.constrained;

import org.hipparchus.linear.ArrayRealVector;
import org.hipparchus.linear.MatrixUtils;
import org.hipparchus.linear.RealMatrix;
import org.hipparchus.linear.RealVector;
import org.hipparchus.util.FastMath;

/**
 * TBD.
 * @since 3.1
 */
public class ADMMQPModifiedRuizEquilibrium {

    /**
     * Minimum scaling value.
     */
    private static final double MIN_SCALING = 1.0e-6;

    /**
     * Maximum scaling value.
     */
    private static final double MAX_SCALING = 1.0e+6;

    /**
     * Square matrix of weights for quadratic terms.
     */
    private final RealMatrix H;

    /**
     * Vector of weights for linear terms.
     */
    private final RealVector q;

    /**
     * Constraints coefficients matrix.
     */
    private final RealMatrix A;

    /**
     * TBC.
     */
    private RealMatrix D;

    /**
     * TBC.
     */
    private RealMatrix E;

    /**
     * TBC.
     */
    private double c;

    /**
     * Inverse of D.
     */
    private RealMatrix Dinv;

    /**
     * Inverse of E.
     */
    private RealMatrix Einv;

    /**
     * Inverse of c.
     */
    private double cinv;

    /**
     * Simple constructor
     * @param H square matrix of weights for quadratic terms
     * @param A constraints coefficients matrix
     * @param q vector of weights for linear terms
     */
    public ADMMQPModifiedRuizEquilibrium(RealMatrix H, RealMatrix A, RealVector q) {
        this.H = H;
        this.A = A;
        this.q = q;
    }

    /**
     * Normalize matrices.
     * @param epsilon TBD
     * @param maxIteration TBD
     */
    public void normalize(double epsilon, int maxIteration) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get scaled square matrix of weights for quadratic terms.
     * @return scaled square matrix of weights for quadratic terms
     */
    public RealMatrix getScaledH() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get scaled constraints coefficients matrix.
     * @return scaled constraints coefficients matrix
     */
    public RealMatrix getScaledA() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get scaled vector of weights for linear terms.
     * @return scaled vector of weights for linear terms
     */
    public RealVector getScaledQ() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get scaled upper bound
     * @param lb1 unscaled lower bound
     * @return scaled lower bound
     */
    public RealVector getScaledLUb(RealVector lb1) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Unscale solution vector.
     * @param x scaled solution vector
     * @return unscaled solution vector
     */
    public RealVector unscaleX(RealVector x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Unscale Y vector.
     * @param y scaled Y vector
     * @return unscaled Y vector
     */
    public RealVector unscaleY(RealVector y) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Unscale Z vector.
     * @param z scaled Z vector
     * @return unscaled Z vector
     */
    public RealVector unscaleZ(RealVector z) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Scale solution vector.
     * @param x unscaled solution vector
     * @return scaled solution vector
     */
    RealVector scaleX(RealVector x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Limit scaling.
     * @param v vector to limit
     * @return rescaled vector, taking scaling limits into account
     */
    private RealVector limitScaling(RealVector v) {
        RealVector result = new ArrayRealVector(v.getDimension());
        for (int i = 0; i < v.getDimension(); i++) {
            result.setEntry(i, v.getEntry(i) < MIN_SCALING ? 1.0 : v.getEntry(i));
            result.setEntry(i, v.getEntry(i) > MAX_SCALING ? MAX_SCALING : v.getEntry(i));
        }
        return result;
    }
}
