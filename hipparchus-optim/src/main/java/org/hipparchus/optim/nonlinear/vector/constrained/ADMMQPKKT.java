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
import org.hipparchus.linear.DecompositionSolver;
import org.hipparchus.linear.EigenDecompositionSymmetric;
import org.hipparchus.linear.MatrixUtils;
import org.hipparchus.linear.RealMatrix;
import org.hipparchus.linear.RealVector;
import org.hipparchus.util.FastMath;

/**
 * Alternative Direction Method of Multipliers Solver.
 * @since 3.1
 */
public class ADMMQPKKT implements KarushKuhnTuckerSolver<ADMMQPSolution> {

    /**
     * Tolerance for symmetric matrices decomposition.
     * @since 4.1
     */
    private double decompositionEpsilon;

    /**
     * Square matrix of weights for quadratic terms.
     */
    private RealMatrix H;

    /**
     * Vector of weights for linear terms.
     */
    private RealVector q;

    /**
     * Constraints coefficients matrix.
     */
    private RealMatrix A;

    /**
     * Regularization term sigma for Karush–Kuhn–Tucker solver.
     */
    private double sigma;

    /**
     * TBC.
     */
    private RealMatrix R;

    /**
     * Inverse of R.
     */
    private RealMatrix Rinv;

    /**
     * Lower bound.
     */
    private RealVector lb;

    /**
     * Upper bound.
     */
    private RealVector ub;

    /**
     * Alpha filter for ADMM iteration.
     */
    private double alpha;

    /**
     * Constrained problem KKT matrix.
     */
    // NOPMD
    private RealMatrix M;

    /**
     * Solver for M.
     */
    private DecompositionSolver dsX;

    /**
     * Simple constructor.
     * <p>
     * BEWARE, nothing is initialized here, it is {@link #initialize(RealMatrix, RealMatrix,
     * RealVector, int, RealVector, RealVector, double, double, double) initialize} <em>must</em>
     * be called before using the instance.
     * </p>
     */
    ADMMQPKKT() {
        decompositionEpsilon = EigenDecompositionSymmetric.DEFAULT_EPSILON;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ADMMQPSolution solve(RealVector b1, final RealVector b2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Update tolerance for matrix decomposition
     * @param newDecompositionEpsilon tolerance for symmetric matrix decomposition
     * @since 4.1
     */
    public void updateDecompositionEpsilon(final double newDecompositionEpsilon) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Update steps
     * @param newSigma new regularization term sigma for Karush–Kuhn–Tucker solver
     * @param me number of equality constraints
     * @param rho new step size
     */
    public void updateSigmaRho(double newSigma, int me, double rho) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Initialize problem
     * @param newH square matrix of weights for quadratic term
     * @param newA constraints coefficients matrix
     * @param newQ TBD
     * @param me number of equality constraints
     * @param newLb lower bound
     * @param newUb upper bound
     * @param rho step size
     * @param newSigma regularization term sigma for Karush–Kuhn–Tucker solver
     * @param newAlpha alpha filter for ADMM iteration
     */
    public void initialize(RealMatrix newH, RealMatrix newA, RealVector newQ, int me, RealVector newLb, RealVector newUb, double rho, double newSigma, double newAlpha) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void createPenaltyMatrix(int me, double rho) {
        this.R = MatrixUtils.createRealIdentityMatrix(A.getRowDimension());
        for (int i = 0; i < R.getRowDimension(); i++) {
            if (i < me) {
                R.setEntry(i, i, rho * 1000.0);
            } else {
                R.setEntry(i, i, rho);
            }
        }
        this.Rinv = MatrixUtils.inverse(R);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ADMMQPSolution iterate(RealVector... previousSol) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
