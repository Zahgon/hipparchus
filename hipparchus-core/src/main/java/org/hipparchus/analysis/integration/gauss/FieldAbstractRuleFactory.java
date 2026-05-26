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
package org.hipparchus.analysis.integration.gauss;

import java.util.Arrays;
import java.util.SortedMap;
import java.util.TreeMap;
import org.hipparchus.CalculusFieldElement;
import org.hipparchus.Field;
import org.hipparchus.analysis.CalculusFieldUnivariateFunction;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.Incrementor;
import org.hipparchus.util.MathArrays;
import org.hipparchus.util.Pair;

/**
 * Base class for rules that determines the integration nodes and their
 * weights.
 * Subclasses must implement the {@link #computeRule(int) computeRule} method.
 *
 * @param <T> Type of the number used to represent the points and weights of
 * the quadrature rules.
 * @since 2.0
 */
public abstract class FieldAbstractRuleFactory<T extends CalculusFieldElement<T>> implements FieldRuleFactory<T> {

    /**
     * Field to which rule coefficients belong.
     */
    private final Field<T> field;

    /**
     * List of points and weights, indexed by the order of the rule.
     */
    private final SortedMap<Integer, Pair<T[], T[]>> pointsAndWeights;

    /**
     * Simple constructor
     * @param field field to which rule coefficients belong
     */
    protected FieldAbstractRuleFactory(final Field<T> field) {
        this.field = field;
        this.pointsAndWeights = new TreeMap<>();
    }

    /**
     * Get the field to which rule coefficients belong.
     * @return field to which rule coefficients belong
     */
    public Field<T> getField() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<T[], T[]> getRule(int numberOfPoints) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Computes the rule for the given order.
     *
     * @param numberOfPoints Order of the rule to be computed.
     * @return the computed rule.
     * @throws MathIllegalArgumentException if the elements of the pair do not
     * have the same length.
     */
    protected abstract Pair<T[], T[]> computeRule(int numberOfPoints) throws MathIllegalArgumentException;

    /**
     * Computes roots of the associated orthogonal polynomials.
     * <p>
     * The roots are found using the <a href="https://en.wikipedia.org/wiki/Aberth_method">Aberth method</a>.
     * The guess points for initializing search for degree n are fixed for degrees 1 and 2 and are
     * selected from n-1 roots of rule n-1 (the two extreme roots are used, plus the n-1 intermediate
     * points between all roots).
     * </p>
     * @param n number of roots to search for
     * @param ratioEvaluator function evaluating the ratio Pₙ(x)/Pₙ'(x)
     * @return sorted array of roots
     */
    protected T[] findRoots(final int n, final CalculusFieldUnivariateFunction<T> ratioEvaluator) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Enforce symmetry of roots.
     * @param roots roots to process in place
     */
    protected void enforceSymmetry(final T[] roots) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
