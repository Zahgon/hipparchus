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
package org.hipparchus.fraction;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.MathUtils;

/**
 * Formats a Fraction number in proper format.  The number format for each of
 * the whole number, numerator and, denominator can be configured.
 * <p>
 * Minus signs are only allowed in the whole number part - i.e.,
 * "-3 1/2" is legitimate and denotes -7/2, but "-3 -1/2" is invalid and
 * will result in a <code>ParseException</code>.
 */
public class ProperFractionFormat extends FractionFormat {

    /**
     * Serializable version identifier
     */
    private static final long serialVersionUID = 20160323L;

    /**
     * The format used for the whole number.
     */
    private final NumberFormat wholeFormat;

    /**
     * Create a proper formatting instance with the default number format for
     * the whole, numerator, and denominator.
     */
    public ProperFractionFormat() {
        this(getDefaultNumberFormat());
    }

    /**
     * Create a proper formatting instance with a custom number format for the
     * whole, numerator, and denominator.
     * @param format the custom format for the whole, numerator, and denominator.
     * @throws org.hipparchus.exception.NullArgumentException if the provided format is null.
     */
    public ProperFractionFormat(NumberFormat format) {
        this(format, (NumberFormat) format.clone(), (NumberFormat) format.clone());
    }

    /**
     * Create a proper formatting instance with a custom number format for each
     * of the whole, numerator, and denominator.
     * @param wholeFormat the custom format for the whole.
     * @param numeratorFormat the custom format for the numerator.
     * @param denominatorFormat the custom format for the denominator.
     * @throws org.hipparchus.exception.NullArgumentException if either provided format is null.
     */
    public ProperFractionFormat(NumberFormat wholeFormat, NumberFormat numeratorFormat, NumberFormat denominatorFormat) {
        super(numeratorFormat, denominatorFormat);
        MathUtils.checkNotNull(wholeFormat, LocalizedCoreFormats.WHOLE_FORMAT);
        this.wholeFormat = wholeFormat;
    }

    /**
     * Formats a {@link Fraction} object to produce a string.  The fraction
     * is output in proper format.
     *
     * @param fraction the object to format.
     * @param toAppendTo where the text is to be appended
     * @param pos On input: an alignment field, if desired. On output: the
     * offsets of the alignment field
     * @return the value passed in as toAppendTo.
     */
    @Override
    public StringBuffer format(Fraction fraction, StringBuffer toAppendTo, FieldPosition pos) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Access the whole format.
     * @return the whole format.
     */
    public NumberFormat getWholeFormat() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Parses a string to produce a {@link Fraction} object.  This method
     * expects the string to be formatted as a proper fraction.
     * <p>
     * Minus signs are only allowed in the whole number part - i.e.,
     * "-3 1/2" is legitimate and denotes -7/2, but "-3 -1/2" is invalid and
     * will result in a <code>ParseException</code>.</p>
     *
     * @param source the string to parse
     * @param pos input/ouput parsing parameter.
     * @return the parsed {@link Fraction} object.
     */
    @Override
    public Fraction parse(String source, ParsePosition pos) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
