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
package org.hipparchus.linear;

import java.lang.reflect.Array;
import org.hipparchus.complex.Complex;
import org.hipparchus.complex.ComplexField;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.MathRuntimeException;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.Precision;

/**
 * Given a matrix A, it computes a complex eigen decomposition AV = VD.
 *
 * <p>
 * Complex Eigen Decomposition differs from the {@link EigenDecompositionSymmetric} since it
 * computes the eigen vectors as complex eigen vectors (if applicable).
 * </p>
 *
 * <p>
 * Beware that in the complex case, you do not always have \(V \times V^{T} = I\) or even a
 * diagonal matrix, even if the eigenvectors that form the columns of the V
 * matrix are independent. On example is the square matrix
 * \[
 * A = \left(\begin{matrix}
 * 3 &amp; -2\\
 * 4 &amp; -1
 * \end{matrix}\right)
 * \]
 * which has two conjugate eigenvalues \(\lambda_1=1+2i\) and \(\lambda_2=1-2i\)
 * with associated eigenvectors \(v_1^T = (1, 1-i)\) and \(v_2^T = (1, 1+i)\).
 * \[
 * V\timesV^T = \left(\begin{matrix}
 * 2 &amp; 2\\
 * 2 &amp; 0
 * \end{matrix}\right)
 * \]
 * which is not the identity matrix. Therefore, despite \(A \times V = V \times D\),
 * \(A \ne V \times D \time V^T\), which would hold for real eigendecomposition.
 * </p>
 * <p>
 * Also note that for consistency with Wolfram langage
 * <a href="https://reference.wolfram.com/language/ref/Eigenvectors.html">eigenvectors</a>,
 * we add zero vectors when the geometric multiplicity of the eigenvalue is smaller than
 * its algebraic multiplicity (hence the regular eigenvector matrix should be non-square).
 * With these additional null vectors, the eigenvectors matrix becomes square. This happens
 * for example with the square matrix
 * \[
 * A = \left(\begin{matrix}
 *  1 &amp; 0 &amp; 0\\
 * -2 &amp; 1 &amp; 0\\
 *  0 &amp; 0 &amp; 1
 * \end{matrix}\right)
 * \]
 * Its characteristic polynomial is \((1-\lambda)^3\), hence is has one eigen value \(\lambda=1\)
 * with algebraic multiplicity 3. However, this eigenvalue leads to only two eigenvectors
 * \(v_1=(0, 1, 0)\) and \(v_2=(0, 0, 1)\), hence its geometric multiplicity is only 2, not 3.
 * So we add a third zero vector \(v_3=(0, 0, 0)\), in the same way Wolfram language does.
 * </p>
 *
 * Compute complex eigen values from the Schur transform. Compute complex eigen
 * vectors based on eigen values and the inverse iteration method.
 *
 * see: <a href="https://en.wikipedia.org/wiki/Inverse_iteration">Inverse iteration</a>
 * <a href="https://en.wikiversity.org/wiki/Shifted_inverse_iteration">Shifted inverse iteration</a>
 * <a href="https://www.robots.ox.ac.uk/~sjrob/Teaching/EngComp/ecl4.pdf">Computation of matrix eigenvalues and eigenvectors</a>
 */
public class ComplexEigenDecomposition {

    /**
     * Default threshold below which eigenvectors are considered equal.
     */
    public static final double DEFAULT_EIGENVECTORS_EQUALITY = 1.0e-5;

    /**
     * Default value to use for internal epsilon.
     */
    public static final double DEFAULT_EPSILON = 1e-12;

    /**
     * Internally used epsilon criteria for final AV=VD check.
     */
    public static final double DEFAULT_EPSILON_AV_VD_CHECK = 1e-6;

    /**
     * Maximum number of inverse iterations.
     */
    private static final int MAX_ITER = 10;

    /**
     * complex eigenvalues.
     */
    private Complex[] eigenvalues;

    /**
     * Eigenvectors.
     */
    private FieldVector<Complex>[] eigenvectors;

    /**
     * Cached value of V.
     */
    private FieldMatrix<Complex> V;

    /**
     * Cached value of D.
     */
    private FieldMatrix<Complex> D;

    /**
     * Internally used threshold below which eigenvectors are considered equal.
     */
    private final double eigenVectorsEquality;

    /**
     * Internally used epsilon criteria.
     */
    private final double epsilon;

    /**
     * Internally used epsilon criteria for final AV=VD check.
     */
    private final double epsilonAVVDCheck;

    /**
     * Constructor for decomposition.
     * <p>
     * This constructor uses the default values {@link #DEFAULT_EIGENVECTORS_EQUALITY},
     * {@link #DEFAULT_EPSILON} and {@link #DEFAULT_EPSILON_AV_VD_CHECK}
     * </p>
     * @param matrix
     *            real matrix.
     */
    public ComplexEigenDecomposition(final RealMatrix matrix) {
        this(matrix, DEFAULT_EIGENVECTORS_EQUALITY, DEFAULT_EPSILON, DEFAULT_EPSILON_AV_VD_CHECK);
    }

    /**
     * Constructor for decomposition.
     * <p>
     * The {@code eigenVectorsEquality} threshold is used to ensure the L∞-normalized
     * eigenvectors found using inverse iteration are different from each other.
     * if \(min(|e_i-e_j|,|e_i+e_j|)\) is smaller than this threshold, the algorithm
     * considers it has found again an already known vector, so it drops it and attempts
     * a new inverse iteration with a different start vector. This value should be
     * much larger than {@code epsilon} which is used for convergence
     * </p>
     * @param matrix real matrix.
     * @param eigenVectorsEquality threshold below which eigenvectors are considered equal
     * @param epsilon Epsilon used for internal tests (e.g. is singular, eigenvalue ratio, etc.)
     * @param epsilonAVVDCheck Epsilon criteria for final AV=VD check
     * @since 1.8
     */
    public ComplexEigenDecomposition(final RealMatrix matrix, final double eigenVectorsEquality, final double epsilon, final double epsilonAVVDCheck) {
        if (!matrix.isSquare()) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.NON_SQUARE_MATRIX, matrix.getRowDimension(), matrix.getColumnDimension());
        }
        this.eigenVectorsEquality = eigenVectorsEquality;
        this.epsilon = epsilon;
        this.epsilonAVVDCheck = epsilonAVVDCheck;
        // computing the eigen values
        findEigenValues(matrix);
        // computing the eigen vectors
        findEigenVectors(convertToFieldComplex(matrix));
        // V
        final int m = eigenvectors.length;
        V = MatrixUtils.createFieldMatrix(ComplexField.getInstance(), m, m);
        for (int k = 0; k < m; ++k) {
            V.setColumnVector(k, eigenvectors[k]);
        }
        // D
        D = MatrixUtils.createFieldDiagonalMatrix(eigenvalues);
        checkDefinition(matrix);
    }

    /**
     * Getter of the eigen values.
     *
     * @return eigen values.
     */
    public Complex[] getEigenvalues() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Getter of the eigen vectors.
     *
     * @param i
     *            which eigen vector.
     * @return eigen vector.
     */
    public FieldVector<Complex> getEigenvector(final int i) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Reset eigenvalues and eigen vectors from matrices.
     * <p>
     * This method is intended to be called by sub-classes (mainly {@link OrderedComplexEigenDecomposition})
     * that reorder the matrices elements. It rebuild the eigenvalues and eigen vectors arrays
     * from the D and V matrices.
     * </p>
     * @since 2.1
     */
    protected void matricesToEigenArrays() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Confirm if there are complex eigen values.
     *
     * @return true if there are complex eigen values.
     */
    public boolean hasComplexEigenvalues() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Computes the determinant.
     *
     * @return the determinant.
     */
    public double getDeterminant() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Getter V.
     *
     * @return V.
     */
    public FieldMatrix<Complex> getV() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Getter D.
     *
     * @return D.
     */
    public FieldMatrix<Complex> getD() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Getter VT.
     *
     * @return VT.
     */
    public FieldMatrix<Complex> getVT() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute eigen values using the Schur transform.
     *
     * @param matrix
     *            real matrix to compute eigen values.
     */
    protected void findEigenValues(final RealMatrix matrix) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the eigen vectors using the inverse power method.
     *
     * @param matrix
     *            real matrix to compute eigen vectors.
     */
    @SuppressWarnings("unchecked")
    protected void findEigenVectors(final FieldMatrix<Complex> matrix) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Find a start vector orthogonal to all already found normalized eigenvectors.
     * @param index index of the vector
     * @return start vector
     */
    private FieldVector<Complex> findStart(final int index) {
        // create vector
        final FieldVector<Complex> start = MatrixUtils.createFieldVector(ComplexField.getInstance(), eigenvalues.length);
        // initialize with a canonical vector
        start.setEntry(index, Complex.ONE);
        return start;
    }

    /**
     * Compute the L∞ norm of the a given vector.
     *
     * @param vector
     *            vector.
     * @return L∞ norm.
     */
    private Complex getNorm(FieldVector<Complex> vector) {
        double normR = 0;
        Complex normC = Complex.ZERO;
        for (int i = 0; i < vector.getDimension(); i++) {
            final Complex ci = vector.getEntry(i);
            final double ni = FastMath.hypot(ci.getReal(), ci.getImaginary());
            if (ni > normR) {
                normR = ni;
                normC = ci;
            }
        }
        return normC;
    }

    /**
     * Normalize a vector with respect to L∞ norm.
     * @param v vector to normalized
     */
    private void normalize(final FieldVector<Complex> v) {
        final Complex invNorm = getNorm(v).reciprocal();
        for (int j = 0; j < v.getDimension(); ++j) {
            v.setEntry(j, v.getEntry(j).multiply(invNorm));
        }
    }

    /**
     * Compute the separation between two normalized vectors (which may be in opposite directions).
     * @param v1 first normalized vector
     * @param v2 second normalized vector
     * @return min (|v1 - v2|, |v1+v2|)
     */
    private double separation(final FieldVector<Complex> v1, final FieldVector<Complex> v2) {
        double deltaPlus = 0;
        double deltaMinus = 0;
        for (int j = 0; j < v1.getDimension(); ++j) {
            final Complex bCurrj = v1.getEntry(j);
            final Complex bNextj = v2.getEntry(j);
            deltaPlus = FastMath.max(deltaPlus, FastMath.hypot(bNextj.getReal() + bCurrj.getReal(), bNextj.getImaginary() + bCurrj.getImaginary()));
            deltaMinus = FastMath.max(deltaMinus, FastMath.hypot(bNextj.getReal() - bCurrj.getReal(), bNextj.getImaginary() - bCurrj.getImaginary()));
        }
        return FastMath.min(deltaPlus, deltaMinus);
    }

    /**
     * Check definition of the decomposition in runtime.
     *
     * @param matrix
     *            matrix to be decomposed.
     */
    protected void checkDefinition(final RealMatrix matrix) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Helper method that checks with two matrix is equals taking into account a
     * given precision.
     *
     * @param matrix1 first matrix to compare
     * @param matrix2 second matrix to compare
     * @param tolerance tolerance on matrices entries
     * @return true is matrices entries are equal within tolerance,
     * false otherwise
     */
    private boolean equalsWithPrecision(final FieldMatrix<Complex> matrix1, final FieldMatrix<Complex> matrix2, final double tolerance) {
        boolean toRet = true;
        for (int i = 0; i < matrix1.getRowDimension(); i++) {
            for (int j = 0; j < matrix1.getColumnDimension(); j++) {
                Complex c1 = matrix1.getEntry(i, j);
                Complex c2 = matrix2.getEntry(i, j);
                if (c1.add(c2.negate()).norm() > tolerance) {
                    toRet = false;
                    break;
                }
            }
        }
        return toRet;
    }

    /**
     * It converts a real matrix into a complex field matrix.
     *
     * @param matrix
     *            real matrix.
     * @return complex matrix.
     */
    private FieldMatrix<Complex> convertToFieldComplex(RealMatrix matrix) {
        final FieldMatrix<Complex> toRet = MatrixUtils.createFieldIdentityMatrix(ComplexField.getInstance(), matrix.getRowDimension());
        for (int i = 0; i < toRet.getRowDimension(); i++) {
            for (int j = 0; j < toRet.getColumnDimension(); j++) {
                toRet.setEntry(i, j, new Complex(matrix.getEntry(i, j)));
            }
        }
        return toRet;
    }
}
