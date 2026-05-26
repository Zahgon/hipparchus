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
package org.hipparchus.linear;

import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.MathIllegalStateException;
import org.hipparchus.exception.NullArgumentException;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.IterationManager;
import org.hipparchus.util.MathUtils;

/**
 * <p>
 * Implementation of the SYMMLQ iterative linear solver proposed by <a
 * href="#PAIG1975">Paige and Saunders (1975)</a>. This implementation is
 * largely based on the FORTRAN code by Pr. Michael A. Saunders, available <a
 * href="http://www.stanford.edu/group/SOL/software/symmlq/f77/">here</a>.
 * </p>
 * <p>
 * SYMMLQ is designed to solve the system of linear equations A &middot; x = b
 * where A is an n &times; n self-adjoint linear operator (defined as a
 * {@link RealLinearOperator}), and b is a given vector. The operator A is not
 * required to be positive definite. If A is known to be definite, the method of
 * conjugate gradients might be preferred, since it will require about the same
 * number of iterations as SYMMLQ but slightly less work per iteration.
 * </p>
 * <p>
 * SYMMLQ is designed to solve the system (A - shift &middot; I) &middot; x = b,
 * where shift is a specified scalar value. If shift and b are suitably chosen,
 * the computed vector x may approximate an (unnormalized) eigenvector of A, as
 * in the methods of inverse iteration and/or Rayleigh-quotient iteration.
 * Again, the linear operator (A - shift &middot; I) need not be positive
 * definite (but <em>must</em> be self-adjoint). The work per iteration is very
 * slightly less if shift = 0.
 * </p>
 * <p><strong>Preconditioning</strong></p>
 * <p>
 * Preconditioning may reduce the number of iterations required. The solver may
 * be provided with a positive definite preconditioner
 * M = P<sup>T</sup> &middot; P
 * that is known to approximate
 * (A - shift &middot; I)<sup>-1</sup> in some sense, where matrix-vector
 * products of the form M &middot; y = x can be computed efficiently. Then
 * SYMMLQ will implicitly solve the system of equations
 * P &middot; (A - shift &middot; I) &middot; P<sup>T</sup> &middot;
 * x<sub>hat</sub> = P &middot; b, i.e.
 * A<sub>hat</sub> &middot; x<sub>hat</sub> = b<sub>hat</sub>,
 * where
 * A<sub>hat</sub> = P &middot; (A - shift &middot; I) &middot; P<sup>T</sup>,
 * b<sub>hat</sub> = P &middot; b,
 * and return the solution
 * x = P<sup>T</sup> &middot; x<sub>hat</sub>.
 * The associated residual is
 * r<sub>hat</sub> = b<sub>hat</sub> - A<sub>hat</sub> &middot; x<sub>hat</sub>
 *                 = P &middot; [b - (A - shift &middot; I) &middot; x]
 *                 = P &middot; r.
 * </p>
 * <p>
 * In the case of preconditioning, the {@link IterativeLinearSolverEvent}s that
 * this solver fires are such that
 * {@link IterativeLinearSolverEvent#getNormOfResidual()} returns the norm of
 * the <em>preconditioned</em>, updated residual, ||P &middot; r||, not the norm
 * of the <em>true</em> residual ||r||.
 * </p>
 * <p><strong>Default stopping criterion</strong></p>
 * <p>
 * A default stopping criterion is implemented. The iterations stop when || rhat
 * || &le; &delta; || Ahat || || xhat ||, where xhat is the current estimate of
 * the solution of the transformed system, rhat the current estimate of the
 * corresponding residual, and &delta; a user-specified tolerance.
 * </p>
 * <strong>Iteration count</strong>
 * <p>
 * In the present context, an iteration should be understood as one evaluation
 * of the matrix-vector product A &middot; x. The initialization phase therefore
 * counts as one iteration. If the user requires checks on the symmetry of A,
 * this entails one further matrix-vector product in the initial phase. This
 * further product is <em>not</em> accounted for in the iteration count. In
 * other words, the number of iterations required to reach convergence will be
 * identical, whether checks have been required or not.
 * </p>
 * <p>
 * The present definition of the iteration count differs from that adopted in
 * the original FOTRAN code, where the initialization phase was <em>not</em>
 * taken into account.
 * </p>
 * <strong>Initial guess of the solution</strong>
 * <p>
 * The {@code x} parameter in
 * </p>
 * <ul>
 * <li>{@link #solve(RealLinearOperator, RealVector, RealVector)},</li>
 * <li>{@link #solve(RealLinearOperator, RealLinearOperator, RealVector, RealVector)}},</li>
 * <li>{@link #solveInPlace(RealLinearOperator, RealVector, RealVector)},</li>
 * <li>{@link #solveInPlace(RealLinearOperator, RealLinearOperator, RealVector, RealVector)},</li>
 * <li>{@link #solveInPlace(RealLinearOperator, RealLinearOperator, RealVector, RealVector, boolean, double)},</li>
 * </ul>
 * <p>
 * should not be considered as an initial guess, as it is set to zero in the
 * initial phase. If x<sub>0</sub> is known to be a good approximation to x, one
 * should compute r<sub>0</sub> = b - A &middot; x, solve A &middot; dx = r0,
 * and set x = x<sub>0</sub> + dx.
 * </p>
 * <p><strong>Exception context</strong></p>
 * <p>
 * Besides standard {@link MathIllegalArgumentException}, this class might throw
 * {@link MathIllegalArgumentException} if the linear operator or the
 * preconditioner are not symmetric.
 * </p>
 * <ul>
 * <li>key {@code "operator"} points to the offending linear operator, say L,</li>
 * <li>key {@code "vector1"} points to the first offending vector, say x,
 * <li>key {@code "vector2"} points to the second offending vector, say y, such
 * that x<sup>T</sup> &middot; L &middot; y &ne; y<sup>T</sup> &middot; L
 * &middot; x (within a certain accuracy).</li>
 * </ul>
 * <p>
 * {@link MathIllegalArgumentException} might also be thrown in case the
 * preconditioner is not positive definite.
 * </p>
 * <p><strong>References</strong></p>
 * <dl>
 * <dt><a id="PAIG1975">Paige and Saunders (1975)</a></dt>
 * <dd>C. C. Paige and M. A. Saunders, <a
 * href="http://www.stanford.edu/group/SOL/software/symmlq/PS75.pdf"><em>
 * Solution of Sparse Indefinite Systems of Linear Equations</em></a>, SIAM
 * Journal on Numerical Analysis 12(4): 617-629, 1975</dd>
 * </dl>
 */
public class SymmLQ extends PreconditionedIterativeLinearSolver {

    /**
     * {@code true} if symmetry of matrix and conditioner must be checked.
     */
    private final boolean check;

    /**
     * The value of the custom tolerance &delta; for the default stopping
     * criterion.
     */
    private final double delta;

    /*
     * IMPLEMENTATION NOTES
     * --------------------
     * The implementation follows as closely as possible the notations of Paige
     * and Saunders (1975). Attention must be paid to the fact that some
     * quantities which are relevant to iteration k can only be computed in
     * iteration (k+1). Therefore, minute attention must be paid to the index of
     * each state variable of this algorithm.
     *
     * 1. Preconditioning
     *    ---------------
     * The Lanczos iterations associated with Ahat and bhat read
     *   beta[1] = ||P * b||
     *   v[1] = P * b / beta[1]
     *   beta[k+1] * v[k+1] = Ahat * v[k] - alpha[k] * v[k] - beta[k] * v[k-1]
     *                      = P * (A - shift * I) * P' * v[k] - alpha[k] * v[k]
     *                        - beta[k] * v[k-1]
     * Multiplying both sides by P', we get
     *   beta[k+1] * (P' * v)[k+1] = M * (A - shift * I) * (P' * v)[k]
     *                               - alpha[k] * (P' * v)[k]
     *                               - beta[k] * (P' * v[k-1]),
     * and
     *   alpha[k+1] = v[k+1]' * Ahat * v[k+1]
     *              = v[k+1]' * P * (A - shift * I) * P' * v[k+1]
     *              = (P' * v)[k+1]' * (A - shift * I) * (P' * v)[k+1].
     *
     * In other words, the Lanczos iterations are unchanged, except for the fact
     * that we really compute (P' * v) instead of v. It can easily be checked
     * that all other formulas are unchanged. It must be noted that P is never
     * explicitly used, only matrix-vector products involving are invoked.
     *
     * 2. Accounting for the shift parameter
     *    ----------------------------------
     * Is trivial: each time A.operate(x) is invoked, one must subtract shift * x
     * to the result.
     *
     * 3. Accounting for the goodb flag
     *    -----------------------------
     * When goodb is set to true, the component of xL along b is computed
     * separately. From Paige and Saunders (1975), equation (5.9), we have
     *   wbar[k+1] = s[k] * wbar[k] - c[k] * v[k+1],
     *   wbar[1] = v[1].
     * Introducing wbar2[k] = wbar[k] - s[1] * ... * s[k-1] * v[1], it can
     * easily be verified by induction that wbar2 follows the same recursive
     * relation
     *   wbar2[k+1] = s[k] * wbar2[k] - c[k] * v[k+1],
     *   wbar2[1] = 0,
     * and we then have
     *   w[k] = c[k] * wbar2[k] + s[k] * v[k+1]
     *          + s[1] * ... * s[k-1] * c[k] * v[1].
     * Introducing w2[k] = w[k] - s[1] * ... * s[k-1] * c[k] * v[1], we find,
     * from (5.10)
     *   xL[k] = zeta[1] * w[1] + ... + zeta[k] * w[k]
     *         = zeta[1] * w2[1] + ... + zeta[k] * w2[k]
     *           + (s[1] * c[2] * zeta[2] + ...
     *           + s[1] * ... * s[k-1] * c[k] * zeta[k]) * v[1]
     *         = xL2[k] + bstep[k] * v[1],
     * where xL2[k] is defined by
     *   xL2[0] = 0,
     *   xL2[k+1] = xL2[k] + zeta[k+1] * w2[k+1],
     * and bstep is defined by
     *   bstep[1] = 0,
     *   bstep[k] = bstep[k-1] + s[1] * ... * s[k-1] * c[k] * zeta[k].
     * We also have, from (5.11)
     *   xC[k] = xL[k-1] + zbar[k] * wbar[k]
     *         = xL2[k-1] + zbar[k] * wbar2[k]
     *           + (bstep[k-1] + s[1] * ... * s[k-1] * zbar[k]) * v[1].
     */
    /**
     * <p>
     * A simple container holding the non-final variables used in the
     * iterations. Making the current state of the solver visible from the
     * outside is necessary, because during the iterations, {@code x} does not
     * <em>exactly</em> hold the current estimate of the solution. Indeed,
     * {@code x} needs in general to be moved from the LQ point to the CG point.
     * Besides, additional upudates must be carried out in case {@code goodb} is
     * set to {@code true}.
     * </p>
     * <p>
     * In all subsequent comments, the description of the state variables refer
     * to their value after a call to {@link #update()}. In these comments, k is
     * the current number of evaluations of matrix-vector products.
     * </p>
     */
    private static class State {

        /**
         * The cubic root of {@link #MACH_PREC}.
         */
        static final double CBRT_MACH_PREC;

        /**
         * The machine precision.
         */
        static final double MACH_PREC;

        /**
         * Reference to the linear operator.
         */
        private final RealLinearOperator a;

        /**
         * Reference to the right-hand side vector.
         */
        private final RealVector b;

        /**
         * {@code true} if symmetry of matrix and conditioner must be checked.
         */
        private final boolean check;

        /**
         * The value of the custom tolerance &delta; for the default stopping
         * criterion.
         */
        private final double delta;

        /**
         * The value of beta[k+1].
         */
        private double beta;

        /**
         * The value of beta[1].
         */
        private double beta1;

        /**
         * The value of bstep[k-1].
         */
        private double bstep;

        /**
         * The estimate of the norm of P * rC[k].
         */
        private double cgnorm;

        /**
         * The value of dbar[k+1] = -beta[k+1] * c[k-1].
         */
        private double dbar;

        /**
         * The value of gamma[k] * zeta[k]. Was called {@code rhs1} in the
         * initial code.
         */
        private double gammaZeta;

        /**
         * The value of gbar[k].
         */
        private double gbar;

        /**
         * The value of max(|alpha[1]|, gamma[1], ..., gamma[k-1]).
         */
        private double gmax;

        /**
         * The value of min(|alpha[1]|, gamma[1], ..., gamma[k-1]).
         */
        private double gmin;

        /**
         * Copy of the {@code goodb} parameter.
         */
        private final boolean goodb;

        /**
         * {@code true} if the default convergence criterion is verified.
         */
        private boolean hasConverged;

        /**
         * The estimate of the norm of P * rL[k-1].
         */
        private double lqnorm;

        /**
         * Reference to the preconditioner, M.
         */
        private final RealLinearOperator m;

        /**
         * The value of (-eps[k+1] * zeta[k-1]). Was called {@code rhs2} in the
         * initial code.
         */
        private double minusEpsZeta;

        /**
         * The value of M * b.
         */
        private final RealVector mb;

        /**
         * The value of beta[k].
         */
        private double oldb;

        /**
         * The value of beta[k] * M^(-1) * P' * v[k].
         */
        private RealVector r1;

        /**
         * The value of beta[k+1] * M^(-1) * P' * v[k+1].
         */
        private RealVector r2;

        /**
         * The value of the updated, preconditioned residual P * r. This value is
         * given by {@code min(}{@link #cgnorm}{@code , }{@link #lqnorm}{@code )}.
         */
        private double rnorm;

        /**
         * Copy of the {@code shift} parameter.
         */
        private final double shift;

        /**
         * The value of s[1] * ... * s[k-1].
         */
        private double snprod;

        /**
         * An estimate of the square of the norm of A * V[k], based on Paige and
         * Saunders (1975), equation (3.3).
         */
        private double tnorm;

        /**
         * The value of P' * wbar[k] or P' * (wbar[k] - s[1] * ... * s[k-1] *
         * v[1]) if {@code goodb} is {@code true}. Was called {@code w} in the
         * initial code.
         */
        private RealVector wbar;

        /**
         * A reference to the vector to be updated with the solution. Contains
         * the value of xL[k-1] if {@code goodb} is {@code false}, (xL[k-1] -
         * bstep[k-1] * v[1]) otherwise.
         */
        private final RealVector xL;

        /**
         * The value of beta[k+1] * P' * v[k+1].
         */
        private RealVector y;

        /**
         * The value of zeta[1]^2 + ... + zeta[k-1]^2.
         */
        private double ynorm2;

        /**
         * The value of {@code b == 0} (exact floating-point equality).
         */
        private boolean bIsNull;

        static {
            MACH_PREC = FastMath.ulp(1.);
            CBRT_MACH_PREC = FastMath.cbrt(MACH_PREC);
        }

        /**
         * Creates and inits to k = 1 a new instance of this class.
         *
         * @param a the linear operator A of the system
         * @param m the preconditioner, M (can be {@code null})
         * @param b the right-hand side vector
         * @param goodb usually {@code false}, except if {@code x} is expected
         * to contain a large multiple of {@code b}
         * @param shift the amount to be subtracted to all diagonal elements of
         * A
         * @param delta the &delta; parameter for the default stopping criterion
         * @param check {@code true} if self-adjointedness of both matrix and
         * preconditioner should be checked
         */
        State(final RealLinearOperator a, final RealLinearOperator m, final RealVector b, final boolean goodb, final double shift, final double delta, final boolean check) {
            this.a = a;
            this.m = m;
            this.b = b;
            this.xL = new ArrayRealVector(b.getDimension());
            this.goodb = goodb;
            this.shift = shift;
            this.mb = m == null ? b : m.operate(b);
            this.hasConverged = false;
            this.check = check;
            this.delta = delta;
        }

        /**
         * Performs a symmetry check on the specified linear operator, and throws an
         * exception in case this check fails. Given a linear operator L, and a
         * vector x, this method checks that
         * x' &middot; L &middot; y = y' &middot; L &middot; x
         * (within a given accuracy), where y = L &middot; x.
         * @param x the candidate vector x
         * @param y the candidate vector y = L &middot; x
         * @param z the vector z = L &middot; y
         *
         * @throws MathIllegalArgumentException when the test fails
         */
        private static void checkSymmetry(final RealVector x, final RealVector y, final RealVector z) throws MathIllegalArgumentException {
            final double s = y.dotProduct(y);
            final double t = x.dotProduct(z);
            final double epsa = (s + MACH_PREC) * CBRT_MACH_PREC;
            if (FastMath.abs(s - t) > epsa) {
                throw new MathIllegalArgumentException(LocalizedCoreFormats.NON_SELF_ADJOINT_OPERATOR);
            }
        }

        /**
         * Throws a new {@link MathIllegalArgumentException} with
         * appropriate context.
         * @throws MathIllegalArgumentException in any circumstances
         */
        private static void throwNPDLOException() throws MathIllegalArgumentException {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.NON_POSITIVE_DEFINITE_OPERATOR);
        }

        /**
         * A clone of the BLAS {@code DAXPY} function, which carries out the
         * operation y &larr; a &middot; x + y. This is for internal use only: no
         * dimension checks are provided.
         *
         * @param a the scalar by which {@code x} is to be multiplied
         * @param x the vector to be added to {@code y}
         * @param y the vector to be incremented
         */
        private static void daxpy(final double a, final RealVector x, final RealVector y) {
            final int n = x.getDimension();
            for (int i = 0; i < n; i++) {
                y.setEntry(i, a * x.getEntry(i) + y.getEntry(i));
            }
        }

        /**
         * A BLAS-like function, for the operation z &larr; a &middot; x + b
         * &middot; y + z. This is for internal use only: no dimension checks are
         * provided.
         *
         * @param a the scalar by which {@code x} is to be multiplied
         * @param x the first vector to be added to {@code z}
         * @param b the scalar by which {@code y} is to be multiplied
         * @param y the second vector to be added to {@code z}
         * @param z the vector to be incremented
         */
        private static void daxpbypz(final double a, final RealVector x, final double b, final RealVector y, final RealVector z) {
            final int n = z.getDimension();
            for (int i = 0; i < n; i++) {
                final double zi;
                zi = a * x.getEntry(i) + b * y.getEntry(i) + z.getEntry(i);
                z.setEntry(i, zi);
            }
        }

        /**
         * <p>
         * Move to the CG point if it seems better. In this version of SYMMLQ,
         * the convergence tests involve only cgnorm, so we're unlikely to stop
         * at an LQ point, except if the iteration limit interferes.
         * </p>
         * <p>
         * Additional upudates are also carried out in case {@code goodb} is set
         * to {@code true}.
         * </p>
         *
         * @param x the vector to be updated with the refined value of xL
         */
        void refineSolution(final RealVector x) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Performs the initial phase of the SYMMLQ algorithm. On return, the
         * value of the state variables of {@code this} object correspond to k =
         * 1.
         */
        void init() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Performs the next iteration of the algorithm. The iteration count
         * should be incremented prior to calling this method. On return, the
         * value of the state variables of {@code this} object correspond to the
         * current iteration count {@code k}.
         */
        void update() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Computes the norms of the residuals, and checks for convergence.
         * Updates {@link #lqnorm} and {@link #cgnorm}.
         */
        private void updateNorms() {
            final double anorm = FastMath.sqrt(tnorm);
            final double ynorm = FastMath.sqrt(ynorm2);
            final double epsa = anorm * MACH_PREC;
            final double epsx = anorm * ynorm * MACH_PREC;
            final double epsr = anorm * ynorm * delta;
            final double diag = gbar == 0. ? epsa : gbar;
            lqnorm = FastMath.sqrt(gammaZeta * gammaZeta + minusEpsZeta * minusEpsZeta);
            final double qrnorm = snprod * beta1;
            cgnorm = qrnorm * beta / FastMath.abs(diag);
            /*
             * Estimate cond(A). In this version we look at the diagonals of L
             * in the factorization of the tridiagonal matrix, T = L * Q.
             * Sometimes, T[k] can be misleadingly ill-conditioned when T[k+1]
             * is not, so we must be careful not to overestimate acond.
             */
            final double acond;
            if (lqnorm <= cgnorm) {
                acond = gmax / gmin;
            } else {
                acond = gmax / FastMath.min(gmin, FastMath.abs(diag));
            }
            if (acond * MACH_PREC >= 0.1) {
                throw new MathIllegalArgumentException(LocalizedCoreFormats.ILL_CONDITIONED_OPERATOR, acond);
            }
            if (beta1 <= epsx) {
                /*
                 * x has converged to an eigenvector of A corresponding to the
                 * eigenvalue shift.
                 */
                throw new MathIllegalArgumentException(LocalizedCoreFormats.SINGULAR_OPERATOR);
            }
            rnorm = FastMath.min(cgnorm, lqnorm);
            hasConverged = (cgnorm <= epsx) || (cgnorm <= epsr);
        }

        /**
         * Returns {@code true} if the default stopping criterion is fulfilled.
         *
         * @return {@code true} if convergence of the iterations has occurred
         */
        boolean hasConverged() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Returns {@code true} if the right-hand side vector is zero exactly.
         *
         * @return the boolean value of {@code b == 0}
         */
        boolean bEqualsNullVector() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Returns {@code true} if {@code beta} is essentially zero. This method
         * is used to check for early stop of the iterations.
         *
         * @return {@code true} if {@code beta < }{@link #MACH_PREC}
         */
        boolean betaEqualsZero() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Returns the norm of the updated, preconditioned residual.
         *
         * @return the norm of the residual, ||P * r||
         */
        double getNormOfResidual() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Creates a new instance of this class, with <a href="#stopcrit">default
     * stopping criterion</a>. Note that setting {@code check} to {@code true}
     * entails an extra matrix-vector product in the initial phase.
     *
     * @param maxIterations the maximum number of iterations
     * @param delta the &delta; parameter for the default stopping criterion
     * @param check {@code true} if self-adjointedness of both matrix and
     * preconditioner should be checked
     */
    public SymmLQ(final int maxIterations, final double delta, final boolean check) {
        super(maxIterations);
        this.delta = delta;
        this.check = check;
    }

    /**
     * Creates a new instance of this class, with <a href="#stopcrit">default
     * stopping criterion</a> and custom iteration manager. Note that setting
     * {@code check} to {@code true} entails an extra matrix-vector product in
     * the initial phase.
     *
     * @param manager the custom iteration manager
     * @param delta the &delta; parameter for the default stopping criterion
     * @param check {@code true} if self-adjointedness of both matrix and
     * preconditioner should be checked
     */
    public SymmLQ(final IterationManager manager, final double delta, final boolean check) {
        super(manager);
        this.delta = delta;
        this.check = check;
    }

    /**
     * Returns {@code true} if symmetry of the matrix, and symmetry as well as
     * positive definiteness of the preconditioner should be checked.
     *
     * @return {@code true} if the tests are to be performed
     * @since 1.4
     */
    public final boolean shouldCheck() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * @throws MathIllegalArgumentException if {@link #shouldCheck()} is
     * {@code true}, and {@code a} or {@code m} is not self-adjoint
     * @throws MathIllegalArgumentException if {@code m} is not
     * positive definite
     * @throws MathIllegalArgumentException if {@code a} is ill-conditioned
     */
    @Override
    public RealVector solve(final RealLinearOperator a, final RealLinearOperator m, final RealVector b) throws NullArgumentException, MathIllegalStateException, MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns an estimate of the solution to the linear system (A - shift
     * &middot; I) &middot; x = b.
     * <p>
     * If the solution x is expected to contain a large multiple of {@code b}
     * (as in Rayleigh-quotient iteration), then better precision may be
     * achieved with {@code goodb} set to {@code true}; this however requires an
     * extra call to the preconditioner.
     * </p>
     * <p>
     * {@code shift} should be zero if the system A &middot; x = b is to be
     * solved. Otherwise, it could be an approximation to an eigenvalue of A,
     * such as the Rayleigh quotient b<sup>T</sup> &middot; A &middot; b /
     * (b<sup>T</sup> &middot; b) corresponding to the vector b. If b is
     * sufficiently like an eigenvector corresponding to an eigenvalue near
     * shift, then the computed x may have very large components. When
     * normalized, x may be closer to an eigenvector than b.
     * </p>
     *
     * @param a the linear operator A of the system
     * @param m the preconditioner, M (can be {@code null})
     * @param b the right-hand side vector
     * @param goodb usually {@code false}, except if {@code x} is expected to
     * contain a large multiple of {@code b}
     * @param shift the amount to be subtracted to all diagonal elements of A
     * @return a reference to {@code x} (shallow copy)
     * @throws NullArgumentException if one of the parameters is {@code null}
     * @throws MathIllegalArgumentException if {@code a} or {@code m} is not square
     * @throws MathIllegalArgumentException if {@code m} or {@code b} have dimensions
     * inconsistent with {@code a}
     * @throws MathIllegalStateException at exhaustion of the iteration count,
     * unless a custom
     * {@link org.hipparchus.util.Incrementor.MaxCountExceededCallback callback}
     * has been set at construction of the {@link IterationManager}
     * @throws MathIllegalArgumentException if {@link #shouldCheck()} is
     * {@code true}, and {@code a} or {@code m} is not self-adjoint
     * @throws MathIllegalArgumentException if {@code m} is not
     * positive definite
     * @throws MathIllegalArgumentException if {@code a} is ill-conditioned
     */
    public RealVector solve(final RealLinearOperator a, final RealLinearOperator m, final RealVector b, final boolean goodb, final double shift) throws MathIllegalArgumentException, NullArgumentException, MathIllegalStateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * @param x not meaningful in this implementation; should not be considered
     * as an initial guess (<a href="#initguess">more</a>)
     * @throws MathIllegalArgumentException if {@link #shouldCheck()} is
     * {@code true}, and {@code a} or {@code m} is not self-adjoint
     * @throws MathIllegalArgumentException if {@code m} is not positive
     * definite
     * @throws MathIllegalArgumentException if {@code a} is ill-conditioned
     */
    @Override
    public RealVector solve(final RealLinearOperator a, final RealLinearOperator m, final RealVector b, final RealVector x) throws NullArgumentException, MathIllegalArgumentException, MathIllegalStateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * @throws MathIllegalArgumentException if {@link #shouldCheck()} is
     * {@code true}, and {@code a} is not self-adjoint
     * @throws MathIllegalArgumentException if {@code a} is ill-conditioned
     */
    @Override
    public RealVector solve(final RealLinearOperator a, final RealVector b) throws NullArgumentException, MathIllegalArgumentException, MathIllegalStateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the solution to the system (A - shift &middot; I) &middot; x = b.
     * <p>
     * If the solution x is expected to contain a large multiple of {@code b}
     * (as in Rayleigh-quotient iteration), then better precision may be
     * achieved with {@code goodb} set to {@code true}.
     * </p>
     * <p>
     * {@code shift} should be zero if the system A &middot; x = b is to be
     * solved. Otherwise, it could be an approximation to an eigenvalue of A,
     * such as the Rayleigh quotient b<sup>T</sup> &middot; A &middot; b /
     * (b<sup>T</sup> &middot; b) corresponding to the vector b. If b is
     * sufficiently like an eigenvector corresponding to an eigenvalue near
     * shift, then the computed x may have very large components. When
     * normalized, x may be closer to an eigenvector than b.
     * </p>
     *
     * @param a the linear operator A of the system
     * @param b the right-hand side vector
     * @param goodb usually {@code false}, except if {@code x} is expected to
     * contain a large multiple of {@code b}
     * @param shift the amount to be subtracted to all diagonal elements of A
     * @return a reference to {@code x}
     * @throws NullArgumentException if one of the parameters is {@code null}
     * @throws MathIllegalArgumentException if {@code a} is not square
     * @throws MathIllegalArgumentException if {@code b} has dimensions
     * inconsistent with {@code a}
     * @throws MathIllegalStateException at exhaustion of the iteration count,
     * unless a custom
     * {@link org.hipparchus.util.Incrementor.MaxCountExceededCallback callback}
     * has been set at construction of the {@link IterationManager}
     * @throws MathIllegalArgumentException if {@link #shouldCheck()} is
     * {@code true}, and {@code a} is not self-adjoint
     * @throws MathIllegalArgumentException if {@code a} is ill-conditioned
     */
    public RealVector solve(final RealLinearOperator a, final RealVector b, final boolean goodb, final double shift) throws MathIllegalArgumentException, NullArgumentException, MathIllegalStateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * @param x not meaningful in this implementation; should not be considered
     * as an initial guess (<a href="#initguess">more</a>)
     * @throws MathIllegalArgumentException if {@link #shouldCheck()} is
     * {@code true}, and {@code a} is not self-adjoint
     * @throws MathIllegalArgumentException if {@code a} is ill-conditioned
     */
    @Override
    public RealVector solve(final RealLinearOperator a, final RealVector b, final RealVector x) throws NullArgumentException, MathIllegalArgumentException, MathIllegalStateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * @param x the vector to be updated with the solution; {@code x} should
     * not be considered as an initial guess (<a href="#initguess">more</a>)
     * @throws MathIllegalArgumentException if {@link #shouldCheck()} is
     * {@code true}, and {@code a} or {@code m} is not self-adjoint
     * @throws MathIllegalArgumentException if {@code m} is not
     * positive definite
     * @throws MathIllegalArgumentException if {@code a} is ill-conditioned
     */
    @Override
    public RealVector solveInPlace(final RealLinearOperator a, final RealLinearOperator m, final RealVector b, final RealVector x) throws NullArgumentException, MathIllegalArgumentException, MathIllegalStateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns an estimate of the solution to the linear system (A - shift
     * &middot; I) &middot; x = b. The solution is computed in-place.
     * <p>
     * If the solution x is expected to contain a large multiple of {@code b}
     * (as in Rayleigh-quotient iteration), then better precision may be
     * achieved with {@code goodb} set to {@code true}; this however requires an
     * extra call to the preconditioner.
     * </p>
     * <p>
     * {@code shift} should be zero if the system A &middot; x = b is to be
     * solved. Otherwise, it could be an approximation to an eigenvalue of A,
     * such as the Rayleigh quotient b<sup>T</sup> &middot; A &middot; b /
     * (b<sup>T</sup> &middot; b) corresponding to the vector b. If b is
     * sufficiently like an eigenvector corresponding to an eigenvalue near
     * shift, then the computed x may have very large components. When
     * normalized, x may be closer to an eigenvector than b.
     * </p>
     *
     * @param a the linear operator A of the system
     * @param m the preconditioner, M (can be {@code null})
     * @param b the right-hand side vector
     * @param x the vector to be updated with the solution; {@code x} should
     * not be considered as an initial guess (<a href="#initguess">more</a>)
     * @param goodb usually {@code false}, except if {@code x} is expected to
     * contain a large multiple of {@code b}
     * @param shift the amount to be subtracted to all diagonal elements of A
     * @return a reference to {@code x} (shallow copy).
     * @throws NullArgumentException if one of the parameters is {@code null}
     * @throws MathIllegalArgumentException if {@code a} or {@code m} is not square
     * @throws MathIllegalArgumentException if {@code m}, {@code b} or {@code x}
     * have dimensions inconsistent with {@code a}.
     * @throws MathIllegalStateException at exhaustion of the iteration count,
     * unless a custom
     * {@link org.hipparchus.util.Incrementor.MaxCountExceededCallback callback}
     * has been set at construction of the {@link IterationManager}
     * @throws MathIllegalArgumentException if {@link #shouldCheck()} is
     * {@code true}, and {@code a} or {@code m} is not self-adjoint
     * @throws MathIllegalArgumentException if {@code m} is not positive definite
     * @throws MathIllegalArgumentException if {@code a} is ill-conditioned
     */
    public RealVector solveInPlace(final RealLinearOperator a, final RealLinearOperator m, final RealVector b, final RealVector x, final boolean goodb, final double shift) throws MathIllegalArgumentException, NullArgumentException, MathIllegalStateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * @param x the vector to be updated with the solution; {@code x} should
     * not be considered as an initial guess (<a href="#initguess">more</a>)
     * @throws MathIllegalArgumentException if {@link #shouldCheck()} is
     * {@code true}, and {@code a} is not self-adjoint
     * @throws MathIllegalArgumentException if {@code a} is ill-conditioned
     */
    @Override
    public RealVector solveInPlace(final RealLinearOperator a, final RealVector b, final RealVector x) throws NullArgumentException, MathIllegalArgumentException, MathIllegalStateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
