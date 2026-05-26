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
package org.hipparchus.dfp;

/**
 * Subclass of {@link Dfp} which hides the radix-10000 artifacts of the superclass.
 * This should give outward appearances of being a decimal number with DIGITS*4-3
 * decimal digits. This class can be subclassed to appear to be an arbitrary number
 * of decimal digits less than DIGITS*4-3.
 */
public class DfpDec extends Dfp {

    /**
     * Makes an instance with a value of zero.
     * @param factory factory linked to this instance
     */
    protected DfpDec(final DfpField factory) {
        super(factory);
    }

    /**
     * Create an instance from a byte value.
     * @param factory factory linked to this instance
     * @param x value to convert to an instance
     */
    protected DfpDec(final DfpField factory, byte x) {
        super(factory, x);
    }

    /**
     * Create an instance from an int value.
     * @param factory factory linked to this instance
     * @param x value to convert to an instance
     */
    protected DfpDec(final DfpField factory, int x) {
        super(factory, x);
    }

    /**
     * Create an instance from a long value.
     * @param factory factory linked to this instance
     * @param x value to convert to an instance
     */
    protected DfpDec(final DfpField factory, long x) {
        super(factory, x);
    }

    /**
     * Create an instance from a double value.
     * @param factory factory linked to this instance
     * @param x value to convert to an instance
     */
    protected DfpDec(final DfpField factory, double x) {
        super(factory, x);
        round(0);
    }

    /**
     * Copy constructor.
     * @param d instance to copy
     */
    public DfpDec(final Dfp d) {
        super(d);
        round(0);
    }

    /**
     * Create an instance from a String representation.
     * @param factory factory linked to this instance
     * @param s string representation of the instance
     */
    protected DfpDec(final DfpField factory, final String s) {
        super(factory, s);
        round(0);
    }

    /**
     * Creates an instance with a non-finite value.
     * @param factory factory linked to this instance
     * @param sign sign of the Dfp to create
     * @param nans code of the value, must be one of {@link #INFINITE},
     * {@link #SNAN},  {@link #QNAN}
     */
    protected DfpDec(final DfpField factory, final byte sign, final byte nans) {
        super(factory, sign, nans);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp newInstance() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp newInstance(final byte x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp newInstance(final int x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp newInstance(final long x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp newInstance(final double x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp newInstance(final Dfp d) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp newInstance(final String s) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp newInstance(final byte sign, final byte nans) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the number of decimal digits this class is going to represent.
     * Default implementation returns {@link #getRadixDigits()}*4-3. Subclasses can
     * override this to return something less.
     * @return number of decimal digits this class is going to represent
     */
    protected int getDecimalDigits() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int round(int in) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp nextAfter(Dfp x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
