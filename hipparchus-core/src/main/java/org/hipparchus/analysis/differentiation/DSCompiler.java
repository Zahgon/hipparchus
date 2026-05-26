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
package org.hipparchus.analysis.differentiation;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import org.hipparchus.CalculusFieldElement;
import org.hipparchus.Field;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.MathRuntimeException;
import org.hipparchus.util.CombinatoricsUtils;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.FieldSinCos;
import org.hipparchus.util.FieldSinhCosh;
import org.hipparchus.util.MathArrays;
import org.hipparchus.util.MathUtils;
import org.hipparchus.util.SinCos;
import org.hipparchus.util.SinhCosh;

/**
 * Class holding "compiled" computation rules for derivative structures.
 * <p>This class implements the computation rules described in Dan Kalman's paper <a
 * href="http://www1.american.edu/cas/mathstat/People/kalman/pdffiles/mmgautodiff.pdf">Doubly
 * Recursive Multivariate Automatic Differentiation</a>, Mathematics Magazine, vol. 75,
 * no. 3, June 2002. However, in order to avoid performances bottlenecks, the recursive
 * rules are "compiled" once in an unfold form. This class does this recursion unrolling
 * and stores the computation rules as simple loops with pre-computed indirection arrays.</p>
 * <p>
 * This class maps all derivative computation into single dimension arrays that hold the
 * value and partial derivatives. The class does not hold these arrays, which remains under
 * the responsibility of the caller. For each combination of number of free parameters and
 * derivation order, only one compiler is necessary, and this compiler will be used to
 * perform computations on all arrays provided to it, which can represent hundreds or
 * thousands of different parameters kept together with all their partial derivatives.
 * </p>
 * <p>
 * The arrays on which compilers operate contain only the partial derivatives together
 * with the 0<sup>th</sup> derivative, i.e. the value. The partial derivatives are stored in
 * a compiler-specific order, which can be retrieved using methods {@link
 * #getPartialDerivativeIndex(int...) getPartialDerivativeIndex} and {@link
 * #getPartialDerivativeOrders(int)}. The value is guaranteed to be stored as the first element
 * (i.e. the {@link #getPartialDerivativeIndex(int...) getPartialDerivativeIndex} method returns
 * 0 when called with 0 for all derivation orders and {@link #getPartialDerivativeOrders(int)
 * getPartialDerivativeOrders} returns an array filled with 0 when called with 0 as the index).
 * </p>
 * <p>
 * Note that the ordering changes with number of parameters and derivation order. For example
 * given 2 parameters x and y, df/dy is stored at index 2 when derivation order is set to 1 (in
 * this case the array has three elements: f, df/dx and df/dy). If derivation order is set to
 * 2, then df/dy will be stored at index 3 (in this case the array has six elements: f, df/dx,
 * d²f/dxdx, df/dy, d²f/dxdy and d²f/dydy).
 * </p>
 * <p>
 * Given this structure, users can perform some simple operations like adding, subtracting
 * or multiplying constants and negating the elements by themselves, knowing if they want to
 * mutate their array or create a new array. These simple operations are not provided by
 * the compiler. The compiler provides only the more complex operations between several arrays.
 * </p>
 * <p>This class is mainly used as the engine for scalar variable {@link DerivativeStructure}.
 * It can also be used directly to hold several variables in arrays for more complex data
 * structures. User can for example store a vector of n variables depending on three x, y
 * and z free parameters in one array as follows:</p> <pre>
 *   // parameter 0 is x, parameter 1 is y, parameter 2 is z
 *   int parameters = 3;
 *   DSCompiler compiler = DSCompiler.getCompiler(parameters, order);
 *   int size = compiler.getSize();
 *
 *   // pack all elements in a single array
 *   double[] array = new double[n * size];
 *   for (int i = 0; i &lt; n; ++i) {
 *
 *     // we know value is guaranteed to be the first element
 *     array[i * size] = v[i];
 *
 *     // we don't know where first derivatives are stored, so we ask the compiler
 *     array[i * size + compiler.getPartialDerivativeIndex(1, 0, 0) = dvOnDx[i][0];
 *     array[i * size + compiler.getPartialDerivativeIndex(0, 1, 0) = dvOnDy[i][0];
 *     array[i * size + compiler.getPartialDerivativeIndex(0, 0, 1) = dvOnDz[i][0];
 *
 *     // we let all higher order derivatives set to 0
 *
 *   }
 * </pre>
 * <p>Then in another function, user can perform some operations on all elements stored
 * in the single array, such as a simple product of all variables:</p> <pre>
 *   // compute the product of all elements
 *   double[] product = new double[size];
 *   prod[0] = 1.0;
 *   for (int i = 0; i &lt; n; ++i) {
 *     double[] tmp = product.clone();
 *     compiler.multiply(tmp, 0, array, i * size, product, 0);
 *   }
 *
 *   // value
 *   double p = product[0];
 *
 *   // first derivatives
 *   double dPdX = product[compiler.getPartialDerivativeIndex(1, 0, 0)];
 *   double dPdY = product[compiler.getPartialDerivativeIndex(0, 1, 0)];
 *   double dPdZ = product[compiler.getPartialDerivativeIndex(0, 0, 1)];
 *
 *   // cross derivatives (assuming order was at least 2)
 *   double dPdXdX = product[compiler.getPartialDerivativeIndex(2, 0, 0)];
 *   double dPdXdY = product[compiler.getPartialDerivativeIndex(1, 1, 0)];
 *   double dPdXdZ = product[compiler.getPartialDerivativeIndex(1, 0, 1)];
 *   double dPdYdY = product[compiler.getPartialDerivativeIndex(0, 2, 0)];
 *   double dPdYdZ = product[compiler.getPartialDerivativeIndex(0, 1, 1)];
 *   double dPdZdZ = product[compiler.getPartialDerivativeIndex(0, 0, 2)];
 * </pre>
 * @see DerivativeStructure
 * @see FieldDerivativeStructure
 */
public class DSCompiler {

    /**
     * Array of all compilers created so far.
     */
    private static AtomicReference<DSCompiler[][]> compilers = new AtomicReference<>(null);

    /**
     * Number of free parameters.
     */
    private final int parameters;

    /**
     * Derivation order.
     */
    private final int order;

    /**
     * Number of partial derivatives (including the single 0 order derivative element).
     */
    private final int[][] sizes;

    /**
     * Orders array for partial derivatives.
     */
    private final int[][] derivativesOrders;

    /**
     * Sum of orders array for partial derivatives.
     */
    private final int[] derivativesOrdersSum;

    /**
     * Indirection array of the lower derivative elements.
     */
    private final int[] lowerIndirection;

    /**
     * Indirection arrays for multiplication.
     */
    private final MultiplicationMapper[][] multIndirection;

    /**
     * Indirection arrays for univariate function composition.
     */
    private final UnivariateCompositionMapper[][] compIndirection;

    /**
     * Indirection arrays for multivariate function rebasing.
     */
    private final List<MultivariateCompositionMapper[][]> rebaseIndirection;

    /**
     * Private constructor, reserved for the factory method {@link #getCompiler(int, int)}.
     * @param parameters number of free parameters
     * @param order derivation order
     * @param valueCompiler compiler for the value part
     * @param derivativeCompiler compiler for the derivative part
     * @throws MathIllegalArgumentException if order is too large
     */
    private DSCompiler(final int parameters, final int order, final DSCompiler valueCompiler, final DSCompiler derivativeCompiler) throws MathIllegalArgumentException {
        this.parameters = parameters;
        this.order = order;
        this.sizes = compileSizes(parameters, order, valueCompiler);
        this.derivativesOrders = compileDerivativesOrders(parameters, order, valueCompiler, derivativeCompiler);
        this.derivativesOrdersSum = compileDerivativesOrdersSum(derivativesOrders);
        this.lowerIndirection = compileLowerIndirection(parameters, order, valueCompiler, derivativeCompiler);
        this.multIndirection = compileMultiplicationIndirection(parameters, order, valueCompiler, derivativeCompiler, lowerIndirection);
        this.compIndirection = compileCompositionIndirection(parameters, order, valueCompiler, derivativeCompiler, sizes, derivativesOrders);
        this.rebaseIndirection = new ArrayList<>();
    }

    /**
     * Get the compiler for number of free parameters and order.
     * @param parameters number of free parameters
     * @param order derivation order
     * @return cached rules set
     * @throws MathIllegalArgumentException if order is too large
     */
    public static DSCompiler getCompiler(int parameters, int order) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compile the sizes array.
     * @param parameters number of free parameters
     * @param order derivation order
     * @param valueCompiler compiler for the value part
     * @return sizes array
     */
    private static int[][] compileSizes(final int parameters, final int order, final DSCompiler valueCompiler) {
        final int[][] sizes = new int[parameters + 1][order + 1];
        if (parameters == 0) {
            Arrays.fill(sizes[0], 1);
        } else {
            System.arraycopy(valueCompiler.sizes, 0, sizes, 0, parameters);
            sizes[parameters][0] = 1;
            for (int i = 0; i < order; ++i) {
                sizes[parameters][i + 1] = sizes[parameters][i] + sizes[parameters - 1][i + 1];
            }
        }
        return sizes;
    }

    /**
     * Compile the derivatives orders array.
     * @param parameters number of free parameters
     * @param order derivation order
     * @param valueCompiler compiler for the value part
     * @param derivativeCompiler compiler for the derivative part
     * @return derivatives orders array
     */
    private static int[][] compileDerivativesOrders(final int parameters, final int order, final DSCompiler valueCompiler, final DSCompiler derivativeCompiler) {
        if (parameters == 0 || order == 0) {
            return new int[1][parameters];
        }
        final int vSize = valueCompiler.derivativesOrders.length;
        final int dSize = derivativeCompiler.derivativesOrders.length;
        final int[][] derivativesOrders = new int[vSize + dSize][parameters];
        // set up the indices for the value part
        for (int i = 0; i < vSize; ++i) {
            // copy the first indices, the last one remaining set to 0
            System.arraycopy(valueCompiler.derivativesOrders[i], 0, derivativesOrders[i], 0, parameters - 1);
        }
        // set up the indices for the derivative part
        for (int i = 0; i < dSize; ++i) {
            // copy the indices
            System.arraycopy(derivativeCompiler.derivativesOrders[i], 0, derivativesOrders[vSize + i], 0, parameters);
            // increment the derivation order for the last parameter
            derivativesOrders[vSize + i][parameters - 1]++;
        }
        return derivativesOrders;
    }

    /**
     * Compile the sum of orders array for partial derivatives.
     * @param derivativesOrders orders array for partial derivatives
     * @return sum of orders array for partial derivatives
     */
    private static int[] compileDerivativesOrdersSum(final int[][] derivativesOrders) {
        final int[] derivativesOrdersSum = new int[derivativesOrders.length];
        // locate the partial derivatives at order 1
        for (int i = 0; i < derivativesOrdersSum.length; ++i) {
            for (final int o : derivativesOrders[i]) {
                derivativesOrdersSum[i] += o;
            }
        }
        return derivativesOrdersSum;
    }

    /**
     * Compile the lower derivatives indirection array.
     * <p>
     * This indirection array contains the indices of all elements
     * except derivatives for last derivation order.
     * </p>
     * @param parameters number of free parameters
     * @param order derivation order
     * @param valueCompiler compiler for the value part
     * @param derivativeCompiler compiler for the derivative part
     * @return lower derivatives indirection array
     */
    private static int[] compileLowerIndirection(final int parameters, final int order, final DSCompiler valueCompiler, final DSCompiler derivativeCompiler) {
        if (parameters == 0 || order <= 1) {
            return new int[] { 0 };
        }
        // this is an implementation of definition 6 in Dan Kalman's paper.
        final int vSize = valueCompiler.lowerIndirection.length;
        final int dSize = derivativeCompiler.lowerIndirection.length;
        final int[] lowerIndirection = new int[vSize + dSize];
        System.arraycopy(valueCompiler.lowerIndirection, 0, lowerIndirection, 0, vSize);
        for (int i = 0; i < dSize; ++i) {
            lowerIndirection[vSize + i] = valueCompiler.getSize() + derivativeCompiler.lowerIndirection[i];
        }
        return lowerIndirection;
    }

    /**
     * Compile the multiplication indirection array.
     * <p>
     * This indirection array contains the indices of all pairs of elements
     * involved when computing a multiplication. This allows a straightforward
     * loop-based multiplication (see {@link #multiply(double[], int, double[], int, double[], int)}).
     * </p>
     * @param parameters number of free parameters
     * @param order derivation order
     * @param valueCompiler compiler for the value part
     * @param derivativeCompiler compiler for the derivative part
     * @param lowerIndirection lower derivatives indirection array
     * @return multiplication indirection array
     */
    private static MultiplicationMapper[][] compileMultiplicationIndirection(final int parameters, final int order, final DSCompiler valueCompiler, final DSCompiler derivativeCompiler, final int[] lowerIndirection) {
        if (parameters == 0 || order == 0) {
            return new MultiplicationMapper[][] { { new MultiplicationMapper(1, 0, 0) } };
        }
        // this is an implementation of definition 3 in Dan Kalman's paper.
        final int vSize = valueCompiler.multIndirection.length;
        final int dSize = derivativeCompiler.multIndirection.length;
        final MultiplicationMapper[][] multIndirection = new MultiplicationMapper[vSize + dSize][];
        System.arraycopy(valueCompiler.multIndirection, 0, multIndirection, 0, vSize);
        for (int i = 0; i < dSize; ++i) {
            final MultiplicationMapper[] dRow = derivativeCompiler.multIndirection[i];
            final List<MultiplicationMapper> row = new ArrayList<>(dRow.length * 2);
            for (MultiplicationMapper dj : dRow) {
                row.add(new MultiplicationMapper(dj.getCoeff(), lowerIndirection[dj.lhsIndex], vSize + dj.rhsIndex));
                row.add(new MultiplicationMapper(dj.getCoeff(), vSize + dj.lhsIndex, lowerIndirection[dj.rhsIndex]));
            }
            multIndirection[vSize + i] = combineSimilarTerms(row);
        }
        return multIndirection;
    }

    /**
     * Compile the function composition indirection array.
     * <p>
     * This indirection array contains the indices of all sets of elements
     * involved when computing a composition. This allows a straightforward
     * loop-based composition (see {@link #compose(double[], int, double[], double[], int)}).
     * </p>
     * @param parameters number of free parameters
     * @param order derivation order
     * @param valueCompiler compiler for the value part
     * @param derivativeCompiler compiler for the derivative part
     * @param sizes sizes array
     * @param derivativesIndirection derivatives indirection array
     * @return multiplication indirection array
     * @throws MathIllegalArgumentException if order is too large
     */
    private static UnivariateCompositionMapper[][] compileCompositionIndirection(final int parameters, final int order, final DSCompiler valueCompiler, final DSCompiler derivativeCompiler, final int[][] sizes, final int[][] derivativesIndirection) throws MathIllegalArgumentException {
        if (parameters == 0 || order == 0) {
            return new UnivariateCompositionMapper[][] { { new UnivariateCompositionMapper(1, 0, new int[0]) } };
        }
        final int vSize = valueCompiler.compIndirection.length;
        final int dSize = derivativeCompiler.compIndirection.length;
        final UnivariateCompositionMapper[][] compIndirection = new UnivariateCompositionMapper[vSize + dSize][];
        // the composition rules from the value part can be reused as is
        System.arraycopy(valueCompiler.compIndirection, 0, compIndirection, 0, vSize);
        // the composition rules for the derivative part are deduced by
        // differentiating the rules from the underlying compiler once
        // with respect to the parameter this compiler handles and the
        // underlying one did not handle
        for (int i = 0; i < dSize; ++i) {
            List<UnivariateCompositionMapper> row = new ArrayList<>();
            for (UnivariateCompositionMapper term : derivativeCompiler.compIndirection[i]) {
                // handle term p * f_k(g(x)) * g_l1(x) * g_l2(x) * ... * g_lp(x)
                // derive the first factor in the term: f_k with respect to new parameter
                UnivariateCompositionMapper derivedTermF = new // p
                UnivariateCompositionMapper(// p
                term.getCoeff(), // f_(k+1)
                term.fIndex + 1, new int[term.dsIndices.length + 1]);
                int[] orders = new int[parameters];
                orders[parameters - 1] = 1;
                // g_1
                derivedTermF.dsIndices[term.dsIndices.length] = getPartialDerivativeIndex(parameters, order, sizes, orders);
                for (int j = 0; j < term.dsIndices.length; ++j) {
                    // convert the indices as the mapping for the current order
                    // is different from the mapping with one less order
                    derivedTermF.dsIndices[j] = convertIndex(term.dsIndices[j], parameters, derivativeCompiler.derivativesOrders, parameters, order, sizes);
                }
                derivedTermF.sort();
                row.add(derivedTermF);
                // derive the various g_l
                for (int l = 0; l < term.dsIndices.length; ++l) {
                    UnivariateCompositionMapper derivedTermG = new UnivariateCompositionMapper(term.getCoeff(), term.fIndex, new int[term.dsIndices.length]);
                    for (int j = 0; j < term.dsIndices.length; ++j) {
                        // convert the indices as the mapping for the current order
                        // is different from the mapping with one less order
                        derivedTermG.dsIndices[j] = convertIndex(term.dsIndices[j], parameters, derivativeCompiler.derivativesOrders, parameters, order, sizes);
                        if (j == l) {
                            // derive this term
                            System.arraycopy(derivativesIndirection[derivedTermG.dsIndices[j]], 0, orders, 0, parameters);
                            orders[parameters - 1]++;
                            derivedTermG.dsIndices[j] = getPartialDerivativeIndex(parameters, order, sizes, orders);
                        }
                    }
                    derivedTermG.sort();
                    row.add(derivedTermG);
                }
            }
            // combine terms with similar derivation orders
            compIndirection[vSize + i] = combineSimilarTerms(row);
        }
        return compIndirection;
    }

    /**
     * Get rebaser, creating it if needed.
     * @param baseCompiler compiler associated with the low level parameter functions
     * @return rebaser for the number of base variables specified
     * @since 2.2
     */
    private MultivariateCompositionMapper[][] getRebaser(final DSCompiler baseCompiler) {
        synchronized (rebaseIndirection) {
            final int m = baseCompiler.getFreeParameters();
            while (rebaseIndirection.size() <= m) {
                rebaseIndirection.add(null);
            }
            if (rebaseIndirection.get(m) == null) {
                // we need to create the rebaser from instance to m base variables
                if (order == 0) {
                    // at order 0, the rebaser just copies the function value
                    final MultivariateCompositionMapper[][] rebaser = { { new MultivariateCompositionMapper(1, 0, new int[0]) } };
                    rebaseIndirection.set(m, rebaser);
                    return rebaser;
                }
                // at order n > 0, the rebaser starts from the rebaser at order n-1
                // so the first rows of the rebaser (corresponding to orders 0 to n-1)
                // are just copies of the lower rebaser rows with indices adjusted,
                // the last row corresponding to order n is a term ∂ⁿf/∂qⱼ⋯∂qₖ∂qₗ
                // which can be written ∂(∂fⁿ⁻¹/∂qⱼ⋯∂qₖ)/∂qₗ, selecting any arbitrary
                // qₗ with non-zero derivation order as the base for recursion
                // the lower level rebaser provides ∂fⁿ⁻¹/∂qⱼ⋯∂qₖ as a
                // sum of products: Σᵢ ∂fⁿ⁻¹/∂pᵤ⋯∂pᵥ ∂pᵤ/∂qⱼ⋯∂qₖ ⋯ ∂pᵥ/∂qⱼ⋯∂qₖ
                // so we have to differentiate this sum of products
                //   - the term ∂fⁿ⁻¹/∂pᵤ⋯∂pᵥ depends on the p intermediate variables,
                //     not on the q base variables, so we use the composition formula
                //     ∂g/∂qₗ = Σᵢ ∂g/∂pᵢ ∂pᵢ/∂qₗ
                //   - the terms ∂pᵤ/∂qⱼ⋯∂qₖ are directly the intermediate variables p and we
                //     know their derivatives with respect to the base variables q
                final int baseSize = baseCompiler.getSize();
                final MultivariateCompositionMapper[][] rebaser = initializeFromLowerRebaser(baseCompiler);
                // derivatives for last order
                for (int k = 1; k < baseSize; ++k) {
                    // outer loop on rebased derivatives
                    // at each iteration of the loop we are dealing with one derivative
                    // like for example ∂³f/∂qⱼ∂qₖ∂qₗ, i.e. the components the rebaser produces
                    if (rebaser[k] == null) {
                        // the entry has not been set earlier
                        // it is an entry of the form ∂ⁿf/∂qⱼ⋯∂qₖ∂qₗ where n is max order
                        final List<MultivariateCompositionMapper> row = new ArrayList<>();
                        // find a variable with respect to which we have a derivative
                        final int[] orders = baseCompiler.derivativesOrders[k].clone();
                        int qIndex = -1;
                        for (int j = 0; j < orders.length; ++j) {
                            if (orders[j] > 0) {
                                qIndex = j;
                                break;
                            }
                        }
                        // find the entry corresponding to differentiating one order less with respect to this variable
                        // ∂fⁿ⁻¹/∂qⱼ⋯∂qₖ
                        orders[qIndex]--;
                        final MultivariateCompositionMapper[] lowerRow = rebaser[baseCompiler.getPartialDerivativeIndex(orders)];
                        // apply recursion formula
                        for (final MultivariateCompositionMapper lowerTerm : lowerRow) {
                            for (int i = 0; i < parameters; ++i) {
                                // differentiate the term ∂fⁿ⁻¹/∂pᵤ⋯∂pᵥ part
                                row.add(differentiateFPart(lowerTerm, i, qIndex, baseCompiler));
                            }
                            // differentiate the products ∂pᵤ/∂qⱼ⋯∂qₖ ⋯ ∂pᵥ/∂qⱼ⋯∂qₖ
                            for (int j = 0; j < lowerTerm.productIndices.length; ++j) {
                                row.add(differentiateProductPart(lowerTerm, j, qIndex, baseCompiler));
                            }
                        }
                        // simplifies and store the completed entry
                        rebaser[k] = combineSimilarTerms(row);
                    }
                }
                rebaseIndirection.set(m, rebaser);
            }
            return rebaseIndirection.get(m);
        }
    }

    /**
     * Initialize a rebaser by copying the rules from a lower rebaser.
     * @param baseCompiler compiler associated with the low level parameter functions
     * @return rebaser with rules up to order - 1 copied (with indices adjusted)
     * @since 2.2
     */
    private MultivariateCompositionMapper[][] initializeFromLowerRebaser(final DSCompiler baseCompiler) {
        // get the rebaser at order - 1
        final DSCompiler lowerCompiler = getCompiler(parameters, order - 1);
        final DSCompiler lowerBaseCompiler = getCompiler(baseCompiler.parameters, order - 1);
        final int lowerBaseSize = lowerBaseCompiler.getSize();
        final MultivariateCompositionMapper[][] lowerRebaser = lowerCompiler.getRebaser(lowerBaseCompiler);
        // allocate array for rebaser at current order
        final int baseSize = baseCompiler.getSize();
        final MultivariateCompositionMapper[][] rebaser = new MultivariateCompositionMapper[baseSize][];
        // copy the rebasing rules for orders 0 to order - 1, adjusting indices
        for (int i = 0; i < lowerRebaser.length; ++i) {
            final int index = convertIndex(i, lowerBaseCompiler.parameters, lowerBaseCompiler.derivativesOrders, baseCompiler.parameters, baseCompiler.order, baseCompiler.sizes);
            rebaser[index] = new MultivariateCompositionMapper[lowerRebaser[i].length];
            for (int j = 0; j < rebaser[index].length; ++j) {
                final int coeff = lowerRebaser[i][j].getCoeff();
                final int dsIndex = convertIndex(lowerRebaser[i][j].dsIndex, lowerCompiler.parameters, lowerCompiler.derivativesOrders, parameters, order, sizes);
                final int[] productIndices = new int[lowerRebaser[i][j].productIndices.length];
                for (int k = 0; k < productIndices.length; ++k) {
                    final int pIndex = lowerRebaser[i][j].productIndices[k] / lowerBaseSize;
                    final int baseDSIndex = lowerRebaser[i][j].productIndices[k] % lowerBaseSize;
                    productIndices[k] = pIndex * baseSize + convertIndex(baseDSIndex, lowerBaseCompiler.parameters, lowerBaseCompiler.derivativesOrders, baseCompiler.parameters, baseCompiler.order, baseCompiler.sizes);
                }
                rebaser[index][j] = new MultivariateCompositionMapper(coeff, dsIndex, productIndices);
            }
        }
        return rebaser;
    }

    /**
     * Differentiate the ∂fⁿ⁻¹/∂pᵤ⋯∂pᵥ part of a {@link MultivariateCompositionMapper}.
     * @param lowerTerm term to differentiate
     * @param i index of the intermediate variable pᵢ
     * @param qIndex index of the qₗ variable
     * @param baseCompiler compiler associated with the low level parameter functions
     * @return ∂fⁿ⁻¹/∂pᵤ⋯∂pᵥ
     */
    private MultivariateCompositionMapper differentiateFPart(final MultivariateCompositionMapper lowerTerm, final int i, final int qIndex, final DSCompiler baseCompiler) {
        // differentiate the term ∂fⁿ⁻¹/∂pᵤ⋯∂pᵥ with respect to pi
        final int[] termOrders = derivativesOrders[lowerTerm.dsIndex].clone();
        termOrders[i]++;
        // multiply by ∂pᵢ/∂qₗ
        final int fDSIndex = getPartialDerivativeIndex(termOrders);
        final int[] productIndicesF = new int[lowerTerm.productIndices.length + 1];
        System.arraycopy(lowerTerm.productIndices, 0, productIndicesF, 0, lowerTerm.productIndices.length);
        final int[] qOrders = new int[baseCompiler.parameters];
        qOrders[qIndex] = 1;
        productIndicesF[productIndicesF.length - 1] = i * baseCompiler.getSize() + baseCompiler.getPartialDerivativeIndex(qOrders);
        // generate the differentiated term
        final MultivariateCompositionMapper termF = new MultivariateCompositionMapper(lowerTerm.getCoeff(), fDSIndex, productIndicesF);
        termF.sort();
        return termF;
    }

    /**
     * Differentiate a product part of a {@link MultivariateCompositionMapper}.
     * @param lowerTerm term to differentiate
     * @param j index of the product to differentiate
     * @param qIndex index of the qₗ variable
     * @param baseCompiler compiler associated with the low level parameter functions
     * @return ∂fⁿ⁻¹/∂pᵤ⋯∂pᵥ
     */
    private MultivariateCompositionMapper differentiateProductPart(final MultivariateCompositionMapper lowerTerm, final int j, final int qIndex, final DSCompiler baseCompiler) {
        // get derivation orders of ∂p/∂q
        final int baseSize = baseCompiler.getSize();
        final int[] productIndicesP = lowerTerm.productIndices.clone();
        final int pIndex = productIndicesP[j] / baseSize;
        final int pDSIndex = productIndicesP[j] % baseSize;
        final int[] pOrders = baseCompiler.getPartialDerivativeOrders(pDSIndex);
        // derive once more with respect to the selected q
        pOrders[qIndex]++;
        final int pDSIndexHigherOrder = baseCompiler.getPartialDerivativeIndex(pOrders);
        productIndicesP[j] = pIndex * baseSize + pDSIndexHigherOrder;
        // create new term
        final MultivariateCompositionMapper termP = new MultivariateCompositionMapper(lowerTerm.getCoeff(), lowerTerm.dsIndex, productIndicesP);
        termP.sort();
        return termP;
    }

    /**
     * Get the index of a partial derivative in the array.
     * <p>
     * If all orders are set to 0, then the 0<sup>th</sup> order derivative
     * is returned, which is the value of the function.
     * </p>
     * <p>The indices of derivatives are between 0 and {@link #getSize() getSize()} - 1.
     * Their specific order is fixed for a given compiler, but otherwise not
     * publicly specified. There are however some simple cases which have guaranteed
     * indices:
     * </p>
     * <ul>
     *   <li>the index of 0<sup>th</sup> order derivative is always 0</li>
     *   <li>if there is only 1 {@link #getFreeParameters() free parameter}, then the
     *   derivatives are sorted in increasing derivation order (i.e. f at index 0, df/dp
     *   at index 1, d<sup>2</sup>f/dp<sup>2</sup> at index 2 ...
     *   d<sup>k</sup>f/dp<sup>k</sup> at index k),</li>
     *   <li>if the {@link #getOrder() derivation order} is 1, then the derivatives
     *   are sorted in increasing free parameter order (i.e. f at index 0, df/dx<sub>1</sub>
     *   at index 1, df/dx<sub>2</sub> at index 2 ... df/dx<sub>k</sub> at index k),</li>
     *   <li>all other cases are not publicly specified</li>
     * </ul>
     * <p>
     * This method is the inverse of method {@link #getPartialDerivativeOrders(int)}
     * </p>
     * @param orders derivation orders with respect to each parameter
     * @return index of the partial derivative
     * @exception MathIllegalArgumentException if the numbers of parameters does not
     * match the instance
     * @exception MathIllegalArgumentException if sum of derivation orders is larger
     * than the instance limits
     * @see #getPartialDerivativeOrders(int)
     */
    public int getPartialDerivativeIndex(final int... orders) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the index of a partial derivative in an array.
     * @param parameters number of free parameters
     * @param order derivation order
     * @param sizes sizes array
     * @param orders derivation orders with respect to each parameter
     * (the length of this array must match the number of parameters)
     * @return index of the partial derivative
     * @exception MathIllegalArgumentException if sum of derivation orders is larger
     * than the instance limits
     */
    private static int getPartialDerivativeIndex(final int parameters, final int order, final int[][] sizes, final int... orders) throws MathIllegalArgumentException {
        // the value is obtained by diving into the recursive Dan Kalman's structure
        // this is theorem 2 of his paper, with recursion replaced by iteration
        int index = 0;
        int m = order;
        int ordersSum = 0;
        for (int i = parameters - 1; i >= 0; --i) {
            // derivative order for current free parameter
            int derivativeOrder = orders[i];
            // safety check
            ordersSum += derivativeOrder;
            if (ordersSum > order) {
                throw new MathIllegalArgumentException(LocalizedCoreFormats.NUMBER_TOO_LARGE, ordersSum, order);
            }
            while (derivativeOrder > 0) {
                --derivativeOrder;
                // as long as we differentiate according to current free parameter,
                // we have to skip the value part and dive into the derivative part
                // so we add the size of the value part to the base index
                index += sizes[i][m--];
            }
        }
        return index;
    }

    /**
     * Convert an index from one (parameters, order) structure to another.
     * @param index index of a partial derivative in source derivative structure
     * @param srcP number of free parameters in source derivative structure
     * @param srcDerivativesOrders derivatives orders array for the source
     * derivative structure
     * @param destP number of free parameters in destination derivative structure
     * @param destO derivation order in destination derivative structure
     * @param destSizes sizes array for the destination derivative structure
     * @return index of the partial derivative with the <em>same</em> characteristics
     * in destination derivative structure
     * @throws MathIllegalArgumentException if order is too large
     */
    private static int convertIndex(final int index, final int srcP, final int[][] srcDerivativesOrders, final int destP, final int destO, final int[][] destSizes) throws MathIllegalArgumentException {
        int[] orders = new int[destP];
        System.arraycopy(srcDerivativesOrders[index], 0, orders, 0, FastMath.min(srcP, destP));
        return getPartialDerivativeIndex(destP, destO, destSizes, orders);
    }

    /**
     * Get the derivation orders for a specific index in the array.
     * <p>
     * This method is the inverse of {@link #getPartialDerivativeIndex(int...)}.
     * </p>
     * @param index of the partial derivative
     * @return derivation orders with respect to each parameter
     * @see #getPartialDerivativeIndex(int...)
     */
    public int[] getPartialDerivativeOrders(final int index) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the sum of derivation orders for a specific index in the array.
     * <p>
     * This method return the sum of the elements returned by
     * {@link #getPartialDerivativeIndex(int...)}, using precomputed
     * values
     * </p>
     * @param index of the partial derivative
     * @return sum of derivation orders with respect to each parameter
     * @see #getPartialDerivativeIndex(int...)
     * @since 2.2
     */
    public int getPartialDerivativeOrdersSum(final int index) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the number of free parameters.
     * @return number of free parameters
     */
    public int getFreeParameters() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the derivation order.
     * @return derivation order
     */
    public int getOrder() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the array size required for holding partial derivatives data.
     * <p>
     * This number includes the single 0 order derivative element, which is
     * guaranteed to be stored in the first element of the array.
     * </p>
     * @return array size required for holding partial derivatives data
     */
    public int getSize() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute linear combination.
     * The derivative structure built will be a1 * ds1 + a2 * ds2
     * @param a1 first scale factor
     * @param c1 first base (unscaled) component
     * @param offset1 offset of first operand in its array
     * @param a2 second scale factor
     * @param c2 second base (unscaled) component
     * @param offset2 offset of second operand in its array
     * @param result array where result must be stored (it may be
     * one of the input arrays)
     * @param resultOffset offset of the result in its array
     */
    public void linearCombination(final double a1, final double[] c1, final int offset1, final double a2, final double[] c2, final int offset2, final double[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute linear combination.
     * The derivative structure built will be a1 * ds1 + a2 * ds2
     * @param a1 first scale factor
     * @param c1 first base (unscaled) component
     * @param offset1 offset of first operand in its array
     * @param a2 second scale factor
     * @param c2 second base (unscaled) component
     * @param offset2 offset of second operand in its array
     * @param result array where result must be stored (it may be
     * one of the input arrays)
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void linearCombination(final T a1, final T[] c1, final int offset1, final T a2, final T[] c2, final int offset2, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute linear combination.
     * The derivative structure built will be a1 * ds1 + a2 * ds2
     * @param a1 first scale factor
     * @param c1 first base (unscaled) component
     * @param offset1 offset of first operand in its array
     * @param a2 second scale factor
     * @param c2 second base (unscaled) component
     * @param offset2 offset of second operand in its array
     * @param result array where result must be stored (it may be
     * one of the input arrays)
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void linearCombination(final double a1, final T[] c1, final int offset1, final double a2, final T[] c2, final int offset2, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute linear combination.
     * The derivative structure built will be a1 * ds1 + a2 * ds2 + a3 * ds3 + a4 * ds4
     * @param a1 first scale factor
     * @param c1 first base (unscaled) component
     * @param offset1 offset of first operand in its array
     * @param a2 second scale factor
     * @param c2 second base (unscaled) component
     * @param offset2 offset of second operand in its array
     * @param a3 third scale factor
     * @param c3 third base (unscaled) component
     * @param offset3 offset of third operand in its array
     * @param result array where result must be stored (it may be
     * one of the input arrays)
     * @param resultOffset offset of the result in its array
     */
    public void linearCombination(final double a1, final double[] c1, final int offset1, final double a2, final double[] c2, final int offset2, final double a3, final double[] c3, final int offset3, final double[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute linear combination.
     * The derivative structure built will be a1 * ds1 + a2 * ds2 + a3 * ds3 + a4 * ds4
     * @param a1 first scale factor
     * @param c1 first base (unscaled) component
     * @param offset1 offset of first operand in its array
     * @param a2 second scale factor
     * @param c2 second base (unscaled) component
     * @param offset2 offset of second operand in its array
     * @param a3 third scale factor
     * @param c3 third base (unscaled) component
     * @param offset3 offset of third operand in its array
     * @param result array where result must be stored (it may be
     * one of the input arrays)
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void linearCombination(final T a1, final T[] c1, final int offset1, final T a2, final T[] c2, final int offset2, final T a3, final T[] c3, final int offset3, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute linear combination.
     * The derivative structure built will be a1 * ds1 + a2 * ds2 + a3 * ds3 + a4 * ds4
     * @param a1 first scale factor
     * @param c1 first base (unscaled) component
     * @param offset1 offset of first operand in its array
     * @param a2 second scale factor
     * @param c2 second base (unscaled) component
     * @param offset2 offset of second operand in its array
     * @param a3 third scale factor
     * @param c3 third base (unscaled) component
     * @param offset3 offset of third operand in its array
     * @param result array where result must be stored (it may be
     * one of the input arrays)
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void linearCombination(final double a1, final T[] c1, final int offset1, final double a2, final T[] c2, final int offset2, final double a3, final T[] c3, final int offset3, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute linear combination.
     * The derivative structure built will be a1 * ds1 + a2 * ds2 + a3 * ds3 + a4 * ds4
     * @param a1 first scale factor
     * @param c1 first base (unscaled) component
     * @param offset1 offset of first operand in its array
     * @param a2 second scale factor
     * @param c2 second base (unscaled) component
     * @param offset2 offset of second operand in its array
     * @param a3 third scale factor
     * @param c3 third base (unscaled) component
     * @param offset3 offset of third operand in its array
     * @param a4 fourth scale factor
     * @param c4 fourth base (unscaled) component
     * @param offset4 offset of fourth operand in its array
     * @param result array where result must be stored (it may be
     * one of the input arrays)
     * @param resultOffset offset of the result in its array
     */
    public void linearCombination(final double a1, final double[] c1, final int offset1, final double a2, final double[] c2, final int offset2, final double a3, final double[] c3, final int offset3, final double a4, final double[] c4, final int offset4, final double[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute linear combination.
     * The derivative structure built will be a1 * ds1 + a2 * ds2 + a3 * ds3 + a4 * ds4
     * @param a1 first scale factor
     * @param c1 first base (unscaled) component
     * @param offset1 offset of first operand in its array
     * @param a2 second scale factor
     * @param c2 second base (unscaled) component
     * @param offset2 offset of second operand in its array
     * @param a3 third scale factor
     * @param c3 third base (unscaled) component
     * @param offset3 offset of third operand in its array
     * @param a4 fourth scale factor
     * @param c4 fourth base (unscaled) component
     * @param offset4 offset of fourth operand in its array
     * @param result array where result must be stored (it may be
     * one of the input arrays)
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void linearCombination(final T a1, final T[] c1, final int offset1, final T a2, final T[] c2, final int offset2, final T a3, final T[] c3, final int offset3, final T a4, final T[] c4, final int offset4, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute linear combination.
     * The derivative structure built will be a1 * ds1 + a2 * ds2 + a3 * ds3 + a4 * ds4
     * @param a1 first scale factor
     * @param c1 first base (unscaled) component
     * @param offset1 offset of first operand in its array
     * @param a2 second scale factor
     * @param c2 second base (unscaled) component
     * @param offset2 offset of second operand in its array
     * @param a3 third scale factor
     * @param c3 third base (unscaled) component
     * @param offset3 offset of third operand in its array
     * @param a4 fourth scale factor
     * @param c4 fourth base (unscaled) component
     * @param offset4 offset of fourth operand in its array
     * @param result array where result must be stored (it may be
     * one of the input arrays)
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void linearCombination(final double a1, final T[] c1, final int offset1, final double a2, final T[] c2, final int offset2, final double a3, final T[] c3, final int offset3, final double a4, final T[] c4, final int offset4, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Perform addition of two derivative structures.
     * @param lhs array holding left hand side of addition
     * @param lhsOffset offset of the left hand side in its array
     * @param rhs array right hand side of addition
     * @param rhsOffset offset of the right hand side in its array
     * @param result array where result must be stored (it may be
     * one of the input arrays)
     * @param resultOffset offset of the result in its array
     */
    public void add(final double[] lhs, final int lhsOffset, final double[] rhs, final int rhsOffset, final double[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Perform addition of two derivative structures.
     * @param lhs array holding left hand side of addition
     * @param lhsOffset offset of the left hand side in its array
     * @param rhs array right hand side of addition
     * @param rhsOffset offset of the right hand side in its array
     * @param result array where result must be stored (it may be
     * one of the input arrays)
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void add(final T[] lhs, final int lhsOffset, final T[] rhs, final int rhsOffset, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Perform subtraction of two derivative structures.
     * @param lhs array holding left hand side of subtraction
     * @param lhsOffset offset of the left hand side in its array
     * @param rhs array right hand side of subtraction
     * @param rhsOffset offset of the right hand side in its array
     * @param result array where result must be stored (it may be
     * one of the input arrays)
     * @param resultOffset offset of the result in its array
     */
    public void subtract(final double[] lhs, final int lhsOffset, final double[] rhs, final int rhsOffset, final double[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Perform subtraction of two derivative structures.
     * @param lhs array holding left hand side of subtraction
     * @param lhsOffset offset of the left hand side in its array
     * @param rhs array right hand side of subtraction
     * @param rhsOffset offset of the right hand side in its array
     * @param result array where result must be stored (it may be
     * one of the input arrays)
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void subtract(final T[] lhs, final int lhsOffset, final T[] rhs, final int rhsOffset, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Perform multiplication of two derivative structures.
     * @param lhs array holding left hand side of multiplication
     * @param lhsOffset offset of the left hand side in its array
     * @param rhs array right hand side of multiplication
     * @param rhsOffset offset of the right hand side in its array
     * @param result array where result must be stored (for
     * multiplication the result array <em>cannot</em> be one of
     * the input arrays)
     * @param resultOffset offset of the result in its array
     */
    public void multiply(final double[] lhs, final int lhsOffset, final double[] rhs, final int rhsOffset, final double[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Perform multiplication of two derivative structures.
     * @param lhs array holding left hand side of multiplication
     * @param lhsOffset offset of the left hand side in its array
     * @param rhs array right hand side of multiplication
     * @param rhsOffset offset of the right hand side in its array
     * @param result array where result must be stored (for
     * multiplication the result array <em>cannot</em> be one of
     * the input arrays)
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void multiply(final T[] lhs, final int lhsOffset, final T[] rhs, final int rhsOffset, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Perform division of two derivative structures. Based on the multiplication operator.
     * @param lhs array holding left hand side of division
     * @param lhsOffset offset of the left hand side in its array
     * @param rhs array right hand side of division
     * @param rhsOffset offset of the right hand side in its array
     * @param result array where result must be stored (for
     * division the result array <em>cannot</em> be one of
     * the input arrays)
     * @param resultOffset offset of the result in its array
     */
    public void divide(final double[] lhs, final int lhsOffset, final double[] rhs, final int rhsOffset, final double[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Perform division of two derivative structures. Based on the multiplication operator.
     * @param lhs array holding left hand side of division
     * @param lhsOffset offset of the left hand side in its array
     * @param rhs array right hand side of division
     * @param rhsOffset offset of the right hand side in its array
     * @param result array where result must be stored (for
     * division the result array <em>cannot</em> be one of
     * the input arrays)
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void divide(final T[] lhs, final int lhsOffset, final T[] rhs, final int rhsOffset, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute reciprocal of derivative structure. Based on the multiplication operator.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored
     * @param resultOffset offset of the result in its array
     */
    public void reciprocal(final double[] operand, final int operandOffset, final double[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute reciprocal of derivative structure. Based on the multiplication operator.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void reciprocal(final T[] operand, final int operandOffset, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Perform remainder of two derivative structures.
     * @param lhs array holding left hand side of remainder
     * @param lhsOffset offset of the left hand side in its array
     * @param rhs array right hand side of remainder
     * @param rhsOffset offset of the right hand side in its array
     * @param result array where result must be stored (it may be
     * one of the input arrays)
     * @param resultOffset offset of the result in its array
     */
    public void remainder(final double[] lhs, final int lhsOffset, final double[] rhs, final int rhsOffset, final double[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Perform remainder of two derivative structures.
     * @param lhs array holding left hand side of remainder
     * @param lhsOffset offset of the left hand side in its array
     * @param rhs array right hand side of remainder
     * @param rhsOffset offset of the right hand side in its array
     * @param result array where result must be stored (it may be
     * one of the input arrays)
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void remainder(final T[] lhs, final int lhsOffset, final T[] rhs, final int rhsOffset, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute power of a double to a derivative structure.
     * @param a number to exponentiate
     * @param operand array holding the power
     * @param operandOffset offset of the power in its array
     * @param result array where result must be stored (for
     * power the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     */
    public void pow(final double a, final double[] operand, final int operandOffset, final double[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute power of a double to a derivative structure.
     * @param a number to exponentiate
     * @param operand array holding the power
     * @param operandOffset offset of the power in its array
     * @param result array where result must be stored (for
     * power the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void pow(final double a, final T[] operand, final int operandOffset, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute power of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param p power to apply
     * @param result array where result must be stored (for
     * power the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     */
    public void pow(final double[] operand, final int operandOffset, final double p, final double[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute power of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param p power to apply
     * @param result array where result must be stored (for
     * power the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void pow(final T[] operand, final int operandOffset, final double p, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute integer power of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param n power to apply
     * @param result array where result must be stored (for
     * power the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     */
    public void pow(final double[] operand, final int operandOffset, final int n, final double[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute integer power of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param n power to apply
     * @param result array where result must be stored (for
     * power the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void pow(final T[] operand, final int operandOffset, final int n, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute power of a derivative structure.
     * @param x array holding the base
     * @param xOffset offset of the base in its array
     * @param y array holding the exponent
     * @param yOffset offset of the exponent in its array
     * @param result array where result must be stored (for
     * power the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     */
    public void pow(final double[] x, final int xOffset, final double[] y, final int yOffset, final double[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute power of a derivative structure.
     * @param x array holding the base
     * @param xOffset offset of the base in its array
     * @param y array holding the exponent
     * @param yOffset offset of the exponent in its array
     * @param result array where result must be stored (for
     * power the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void pow(final T[] x, final int xOffset, final T[] y, final int yOffset, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute square root of a derivative structure. Based on the multiplication operator.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored (for
     * square root the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     */
    public void sqrt(final double[] operand, final int operandOffset, final double[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute square root of a derivative structure. Based on the multiplication operator.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored (for
     * square root the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void sqrt(final T[] operand, final int operandOffset, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute n<sup>th</sup> root of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param n order of the root
     * @param result array where result must be stored (for
     * n<sup>th</sup> root the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     */
    public void rootN(final double[] operand, final int operandOffset, final int n, final double[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute n<sup>th</sup> root of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param n order of the root
     * @param result array where result must be stored (for
     * n<sup>th</sup> root the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void rootN(final T[] operand, final int operandOffset, final int n, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute exponential of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored (for
     * exponential the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     */
    public void exp(final double[] operand, final int operandOffset, final double[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute exponential of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored (for
     * exponential the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void exp(final T[] operand, final int operandOffset, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute exp(x) - 1 of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored (for
     * exponential the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     */
    public void expm1(final double[] operand, final int operandOffset, final double[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute exp(x) - 1 of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored (for
     * exponential the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void expm1(final T[] operand, final int operandOffset, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute natural logarithm of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored (for
     * logarithm the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     */
    public void log(final double[] operand, final int operandOffset, final double[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute natural logarithm of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored (for
     * logarithm the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void log(final T[] operand, final int operandOffset, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Computes shifted logarithm of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored (for
     * shifted logarithm the result array <em>cannot</em> be the input array)
     * @param resultOffset offset of the result in its array
     */
    public void log1p(final double[] operand, final int operandOffset, final double[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Computes shifted logarithm of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored (for
     * shifted logarithm the result array <em>cannot</em> be the input array)
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void log1p(final T[] operand, final int operandOffset, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Computes base 10 logarithm of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored (for
     * base 10 logarithm the result array <em>cannot</em> be the input array)
     * @param resultOffset offset of the result in its array
     */
    public void log10(final double[] operand, final int operandOffset, final double[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Computes base 10 logarithm of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored (for
     * base 10 logarithm the result array <em>cannot</em> be the input array)
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void log10(final T[] operand, final int operandOffset, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute cosine of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored (for
     * cosine the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     */
    public void cos(final double[] operand, final int operandOffset, final double[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute cosine of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored (for
     * cosine the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void cos(final T[] operand, final int operandOffset, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute sine of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored (for
     * sine the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     */
    public void sin(final double[] operand, final int operandOffset, final double[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute sine of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored (for
     * sine the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void sin(final T[] operand, final int operandOffset, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute combined sine and cosine of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param sin array where sine must be stored (for
     * sine the result array <em>cannot</em> be the input
     * array)
     * @param sinOffset offset of the result in its array
     * @param cos array where cosine must be stored (for
     * cosine the result array <em>cannot</em> be the input
     * array)
     * @param cosOffset offset of the result in its array
     * @since 1.4
     */
    public void sinCos(final double[] operand, final int operandOffset, final double[] sin, final int sinOffset, final double[] cos, final int cosOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute combined sine and cosine of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param sin array where sine must be stored (for
     * sine the result array <em>cannot</em> be the input
     * array)
     * @param sinOffset offset of the result in its array
     * @param cos array where cosine must be stored (for
     * cosine the result array <em>cannot</em> be the input
     * array)
     * @param cosOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     * @since 1.4
     */
    public <T extends CalculusFieldElement<T>> void sinCos(final T[] operand, final int operandOffset, final T[] sin, final int sinOffset, final T[] cos, final int cosOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute tangent of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored (for
     * tangent the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     */
    public void tan(final double[] operand, final int operandOffset, final double[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute tangent of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored (for
     * tangent the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void tan(final T[] operand, final int operandOffset, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute arc cosine of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored (for
     * arc cosine the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     */
    public void acos(final double[] operand, final int operandOffset, final double[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute arc cosine of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored (for
     * arc cosine the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void acos(final T[] operand, final int operandOffset, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute arc sine of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored (for
     * arc sine the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     */
    public void asin(final double[] operand, final int operandOffset, final double[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute arc sine of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored (for
     * arc sine the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void asin(final T[] operand, final int operandOffset, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute arc tangent of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored (for
     * arc tangent the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     */
    public void atan(final double[] operand, final int operandOffset, final double[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute arc tangent of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored (for
     * arc tangent the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void atan(final T[] operand, final int operandOffset, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute two arguments arc tangent of a derivative structure.
     * @param y array holding the first operand
     * @param yOffset offset of the first operand in its array
     * @param x array holding the second operand
     * @param xOffset offset of the second operand in its array
     * @param result array where result must be stored (for
     * two arguments arc tangent the result array <em>cannot</em>
     * be the input array)
     * @param resultOffset offset of the result in its array
     */
    public void atan2(final double[] y, final int yOffset, final double[] x, final int xOffset, final double[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute two arguments arc tangent of a derivative structure.
     * @param y array holding the first operand
     * @param yOffset offset of the first operand in its array
     * @param x array holding the second operand
     * @param xOffset offset of the second operand in its array
     * @param result array where result must be stored (for
     * two arguments arc tangent the result array <em>cannot</em>
     * be the input array)
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void atan2(final T[] y, final int yOffset, final T[] x, final int xOffset, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute hyperbolic cosine of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored (for
     * hyperbolic cosine the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     */
    public void cosh(final double[] operand, final int operandOffset, final double[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute hyperbolic cosine of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored (for
     * hyperbolic cosine the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void cosh(final T[] operand, final int operandOffset, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute hyperbolic sine of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored (for
     * hyperbolic sine the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     */
    public void sinh(final double[] operand, final int operandOffset, final double[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute hyperbolic sine of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored (for
     * hyperbolic sine the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void sinh(final T[] operand, final int operandOffset, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute combined hyperbolic sine and cosine of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param sinh array where hyperbolic sine must be stored (for
     * sine the result array <em>cannot</em> be the input
     * array)
     * @param sinhOffset offset of the result in its array
     * @param cosh array where hyperbolic <em>cannot</em> be the input
     * array)
     * @param coshOffset offset of the result in its array
     * @since 2.0
     */
    public void sinhCosh(final double[] operand, final int operandOffset, final double[] sinh, final int sinhOffset, final double[] cosh, final int coshOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute combined hyperbolic sine and cosine of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param sinh array where hyperbolic sine must be stored (for
     * sine the result array <em>cannot</em> be the input
     * array)
     * @param sinhOffset offset of the result in its array
     * @param cosh array where hyperbolic cosine must be stored (for
     * cosine the result array <em>cannot</em> be the input
     * array)
     * @param coshOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     * @since 1.4
     */
    public <T extends CalculusFieldElement<T>> void sinhCosh(final T[] operand, final int operandOffset, final T[] sinh, final int sinhOffset, final T[] cosh, final int coshOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute hyperbolic tangent of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored (for
     * hyperbolic tangent the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     */
    public void tanh(final double[] operand, final int operandOffset, final double[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute hyperbolic tangent of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored (for
     * hyperbolic tangent the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void tanh(final T[] operand, final int operandOffset, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute inverse hyperbolic cosine of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored (for
     * inverse hyperbolic cosine the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     */
    public void acosh(final double[] operand, final int operandOffset, final double[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute inverse hyperbolic cosine of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored (for
     * inverse hyperbolic cosine the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void acosh(final T[] operand, final int operandOffset, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute inverse hyperbolic sine of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored (for
     * inverse hyperbolic sine the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     */
    public void asinh(final double[] operand, final int operandOffset, final double[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute inverse hyperbolic sine of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored (for
     * inverse hyperbolic sine the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void asinh(final T[] operand, final int operandOffset, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute inverse hyperbolic tangent of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored (for
     * inverse hyperbolic tangent the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     */
    public void atanh(final double[] operand, final int operandOffset, final double[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute inverse hyperbolic tangent of a derivative structure.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param result array where result must be stored (for
     * inverse hyperbolic tangent the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void atanh(final T[] operand, final int operandOffset, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute composition of a derivative structure by a function.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param f array of value and derivatives of the function at
     * the current point (i.e. at {@code operand[operandOffset]}).
     * @param result array where result must be stored (for
     * composition the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     */
    public void compose(final double[] operand, final int operandOffset, final double[] f, final double[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute composition of a derivative structure by a function.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param f array of value and derivatives of the function at
     * the current point (i.e. at {@code operand[operandOffset]}).
     * @param result array where result must be stored (for
     * composition the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void compose(final T[] operand, final int operandOffset, final T[] f, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute composition of a derivative structure by a function.
     * @param operand array holding the operand
     * @param operandOffset offset of the operand in its array
     * @param f array of value and derivatives of the function at
     * the current point (i.e. at {@code operand[operandOffset]}).
     * @param result array where result must be stored (for
     * composition the result array <em>cannot</em> be the input
     * array)
     * @param resultOffset offset of the result in its array
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> void compose(final T[] operand, final int operandOffset, final double[] f, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Evaluate Taylor expansion of a derivative structure.
     * @param ds array holding the derivative structure
     * @param dsOffset offset of the derivative structure in its array
     * @param delta parameters offsets (&Delta;x, &Delta;y, ...)
     * @return value of the Taylor expansion at x + &Delta;x, y + &Delta;y, ...
     * @throws MathRuntimeException if factorials becomes too large
     */
    public double taylor(final double[] ds, final int dsOffset, final double... delta) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Evaluate Taylor expansion of a derivative structure.
     * @param ds array holding the derivative structure
     * @param dsOffset offset of the derivative structure in its array
     * @param delta parameters offsets (&Delta;x, &Delta;y, ...)
     * @return value of the Taylor expansion at x + &Delta;x, y + &Delta;y, ...
     * @throws MathRuntimeException if factorials becomes too large
     * @param <T> the type of the function parameters and value
     */
    @SafeVarargs
    public final <T extends CalculusFieldElement<T>> T taylor(final T[] ds, final int dsOffset, final T... delta) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Evaluate Taylor expansion of a derivative structure.
     * @param ds array holding the derivative structure
     * @param dsOffset offset of the derivative structure in its array
     * @param delta parameters offsets (&Delta;x, &Delta;y, ...)
     * @return value of the Taylor expansion at x + &Delta;x, y + &Delta;y, ...
     * @throws MathRuntimeException if factorials becomes too large
     * @param <T> the type of the function parameters and value
     */
    public <T extends CalculusFieldElement<T>> T taylor(final T[] ds, final int dsOffset, final double... delta) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Rebase derivative structure with respect to low level parameter functions.
     * @param ds array holding the derivative structure
     * @param dsOffset offset of the derivative structure in its array
     * @param baseCompiler compiler associated with the low level parameter functions
     * @param p array holding the low level parameter functions (one flat array)
     * @param result array where result must be stored (for
     * composition the result array <em>cannot</em> be the input
     * @param resultOffset offset of the result in its array
     * @since 2.2
     */
    public void rebase(final double[] ds, final int dsOffset, final DSCompiler baseCompiler, double[] p, final double[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Rebase derivative structure with respect to low level parameter functions.
     * @param <T> type of the field elements
     * @param ds array holding the derivative structure
     * @param dsOffset offset of the derivative structure in its array
     * @param baseCompiler compiler associated with the low level parameter functions
     * @param p array holding the low level parameter functions (one flat array)
     * @param result array where result must be stored (for
     * composition the result array <em>cannot</em> be the input
     * @param resultOffset offset of the result in its array
     * @since 2.2
     */
    public <T extends CalculusFieldElement<T>> void rebase(final T[] ds, final int dsOffset, final DSCompiler baseCompiler, T[] p, final T[] result, final int resultOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check rules set compatibility.
     * @param compiler other compiler to check against instance
     * @exception MathIllegalArgumentException if number of free parameters or orders are inconsistent
     */
    public void checkCompatibility(final DSCompiler compiler) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Combine terms with similar derivation orders.
     * @param <T> type of the field elements
     * @param terms list of terms
     * @return combined array
     */
    @SuppressWarnings("unchecked")
    private static <T extends AbstractMapper<T>> T[] combineSimilarTerms(final List<T> terms) {
        final List<T> combined = new ArrayList<>(terms.size());
        for (int j = 0; j < terms.size(); ++j) {
            final T termJ = terms.get(j);
            if (termJ.getCoeff() > 0) {
                for (int k = j + 1; k < terms.size(); ++k) {
                    final T termK = terms.get(k);
                    if (termJ.isSimilar(termK)) {
                        // combine terms
                        termJ.setCoeff(termJ.getCoeff() + termK.getCoeff());
                        // make sure we will skip other term later on in the outer loop
                        termK.setCoeff(0);
                    }
                }
                combined.add(termJ);
            }
        }
        return combined.toArray((T[]) Array.newInstance(terms.get(0).getClass(), combined.size()));
    }

    /**
     * Base mapper.
     * @param <T> type of the field elements
     * @since 2.2
     */
    private abstract static class AbstractMapper<T extends AbstractMapper<T>> {

        /**
         * Multiplication coefficient.
         */
        private int coeff;

        /**
         * Simple constructor.
         * @param coeff multiplication coefficient
         */
        AbstractMapper(final int coeff) {
            this.coeff = coeff;
        }

        /**
         * Set the multiplication coefficient.
         * @param coeff new coefficient
         */
        public void setCoeff(final int coeff) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Get the multiplication coefficient.
         * @return multiplication coefficient
         */
        public int getCoeff() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Check if another instance if correspond to term with similar derivation orders.
         * @param other other instance to check
         * @return true if instances are similar
         */
        protected abstract boolean isSimilar(T other);
    }

    /**
     * Multiplication mapper.
     * @since 2.2
     */
    private static class MultiplicationMapper extends AbstractMapper<MultiplicationMapper> {

        /**
         * Left hand side index.
         */
        private final int lhsIndex;

        /**
         * Right hand side index.
         */
        private final int rhsIndex;

        /**
         * Simple constructor.
         * @param coeff multiplication coefficient
         * @param lhsIndex left hand side index
         * @param rhsIndex right hand side index
         */
        MultiplicationMapper(final int coeff, final int lhsIndex, final int rhsIndex) {
            super(coeff);
            this.lhsIndex = lhsIndex;
            this.rhsIndex = rhsIndex;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isSimilar(final MultiplicationMapper other) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Univariate composition mapper.
     * @since 2.2
     */
    private static class UnivariateCompositionMapper extends AbstractMapper<UnivariateCompositionMapper> {

        /**
         * Univariate derivative index.
         */
        private final int fIndex;

        /**
         * Derivative structure indices.
         */
        private final int[] dsIndices;

        /**
         * Simple constructor.
         * @param coeff multiplication coefficient
         * @param fIndex univariate derivative index
         * @param dsIndices derivative structure indices
         */
        UnivariateCompositionMapper(final int coeff, final int fIndex, final int[] dsIndices) {
            super(coeff);
            this.fIndex = fIndex;
            this.dsIndices = dsIndices.clone();
        }

        /**
         * Sort the derivatives structures indices.
         */
        public void sort() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isSimilar(final UnivariateCompositionMapper other) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Multivariate composition mapper.
     * @since 2.2
     */
    private static class MultivariateCompositionMapper extends AbstractMapper<MultivariateCompositionMapper> {

        /**
         * Multivariate derivative index.
         */
        private final int dsIndex;

        /**
         * Indices of the intermediate variables derivatives products.
         */
        private final int[] productIndices;

        /**
         * Simple constructor.
         * @param coeff multiplication coefficient
         * @param dsIndex multivariate derivative index of ∂ₘf/∂pᵢ⋯∂pⱼ
         * @param productIndices indices of intermediate partial derivatives ∂pᵢ/∂qₘ⋯∂qₙ
         */
        MultivariateCompositionMapper(final int coeff, final int dsIndex, final int[] productIndices) {
            super(coeff);
            this.dsIndex = dsIndex;
            this.productIndices = productIndices.clone();
        }

        /**
         * Sort the indices of the intermediate variables derivatives products.
         */
        public void sort() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isSimilar(final MultivariateCompositionMapper other) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
