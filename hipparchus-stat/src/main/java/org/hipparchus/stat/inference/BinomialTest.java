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
package org.hipparchus.stat.inference;

import org.hipparchus.distribution.discrete.BinomialDistribution;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.MathRuntimeException;
import org.hipparchus.util.MathUtils;

/**
 * Implements binomial test statistics.
 * <p>
 * Exact test for the statistical significance of deviations from a
 * theoretically expected distribution of observations into two categories.
 *
 * @see <a href="http://en.wikipedia.org/wiki/Binomial_test">Binomial test (Wikipedia)</a>
 */
public class // NOPMD - this is not a Junit test class, PMD false positive here
BinomialTest {

    /**
     * Empty constructor.
     * <p>
     * This constructor is not strictly necessary, but it prevents spurious
     * javadoc warnings with JDK 18 and later.
     * </p>
     * @since 3.0
     */
    public BinomialTest() {
        // NOPMD - unnecessary constructor added intentionally to make javadoc happy
        // nothing to do
    }

    /**
     * Returns whether the null hypothesis can be rejected with the given confidence level.
     * <p>
     * <strong>Preconditions</strong>:
     * <ul>
     * <li>Number of trials must be &ge; 0.</li>
     * <li>Number of successes must be &ge; 0.</li>
     * <li>Number of successes must be &le; number of trials.</li>
     * <li>Probability must be &ge; 0 and &le; 1.</li>
     * </ul>
     *
     * @param numberOfTrials number of trials performed
     * @param numberOfSuccesses number of successes observed
     * @param probability assumed probability of a single trial under the null hypothesis
     * @param alternativeHypothesis type of hypothesis being evaluated (one- or two-sided)
     * @param alpha significance level of the test
     * @return true if the null hypothesis can be rejected with confidence {@code 1 - alpha}
     * @throws MathIllegalArgumentException if {@code numberOfTrials} or {@code numberOfSuccesses} is negative
     * @throws MathIllegalArgumentException if {@code probability} is not between 0 and 1
     * @throws MathIllegalArgumentException if {@code numberOfTrials} &lt; {@code numberOfSuccesses} or
     * if {@code alternateHypothesis} is null.
     * @see AlternativeHypothesis
     */
    public boolean binomialTest(int numberOfTrials, int numberOfSuccesses, double probability, AlternativeHypothesis alternativeHypothesis, double alpha) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the <i>observed significance level</i>, or
     * <a href="http://www.cas.lancs.ac.uk/glossary_v1.1/hyptest.html#pvalue">p-value</a>,
     * associated with a <a href="http://en.wikipedia.org/wiki/Binomial_test"> Binomial test</a>.
     * <p>
     * The number returned is the smallest significance level at which one can reject the null hypothesis.
     * The form of the hypothesis depends on {@code alternativeHypothesis}.</p>
     * <p>
     * The p-Value represents the likelihood of getting a result at least as extreme as the sample,
     * given the provided {@code probability} of success on a single trial. For single-sided tests,
     * this value can be directly derived from the Binomial distribution. For the two-sided test,
     * the implementation works as follows: we start by looking at the most extreme cases
     * (0 success and n success where n is the number of trials from the sample) and determine their likelihood.
     * The lower value is added to the p-Value (if both values are equal, both are added). Then we continue with
     * the next extreme value, until we added the value for the actual observed sample.</p>
     * <p>* <strong>Preconditions</strong>:</p>
     * <ul>
     * <li>Number of trials must be &ge; 0.</li>
     * <li>Number of successes must be &ge; 0.</li>
     * <li>Number of successes must be &le; number of trials.</li>
     * <li>Probability must be &ge; 0 and &le; 1.</li>
     * </ul>
     *
     * @param numberOfTrials number of trials performed
     * @param numberOfSuccesses number of successes observed
     * @param probability assumed probability of a single trial under the null hypothesis
     * @param alternativeHypothesis type of hypothesis being evaluated (one- or two-sided)
     * @return p-value
     * @throws MathIllegalArgumentException if {@code numberOfTrials} or {@code numberOfSuccesses} is negative
     * @throws MathIllegalArgumentException if {@code probability} is not between 0 and 1
     * @throws MathIllegalArgumentException if {@code numberOfTrials} &lt; {@code numberOfSuccesses} or
     * if {@code alternateHypothesis} is null.
     * @see AlternativeHypothesis
     */
    public double binomialTest(int numberOfTrials, int numberOfSuccesses, double probability, AlternativeHypothesis alternativeHypothesis) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
