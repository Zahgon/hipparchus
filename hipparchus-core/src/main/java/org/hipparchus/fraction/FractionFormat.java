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
import java.util.Locale;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.MathIllegalStateException;

/**
 * Formats a Fraction number in proper format or improper format.
 * <p>
 * The number format for each of the whole number, numerator and,
 * denominator can be configured.
 */
public class FractionFormat extends AbstractFormat {

    /**
     * Serializable version identifier
     */
    private static final long serialVersionUID = 20160323L;

    /**
     * Create an improper formatting instance with the default number format
     * for the numerator and denominator.
     */
    public FractionFormat() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    /**
     * Create an improper formatting instance with a custom number format for
     * both the numerator and denominator.
     * @param format the custom format for both the numerator and denominator.
     * @throws org.hipparchus.exception.NullArgumentException if the provided format is null.
     */
    public FractionFormat(final NumberFormat format) {
        super(format);
    }

    /**
     * Create an improper formatting instance with a custom number format for
     * the numerator and a custom number format for the denominator.
     * @param numeratorFormat the custom format for the numerator.
     * @param denominatorFormat the custom format for the denominator.
     * @throws org.hipparchus.exception.NullArgumentException if either provided format is null.
     */
    public FractionFormat(final NumberFormat numeratorFormat, final NumberFormat denominatorFormat) {
        super(numeratorFormat, denominatorFormat);
    }

    /**
     * Get the set of locales for which complex formats are available.  This
     * is the same set as the {@link NumberFormat} set.
     * @return available complex format locales.
     */
    public static Locale[] getAvailableLocales() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * This static method calls formatFraction() on a default instance of
     * FractionFormat.
     *
     * @param f Fraction object to format
     * @return a formatted fraction in proper form.
     */
    public static String formatFraction(Fraction f) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the default complex format for the current locale.
     * @return the default complex format.
     */
    public static FractionFormat getImproperInstance() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the default complex format for the given locale.
     * @param locale the specific locale used by the format.
     * @return the complex format specific to the given locale.
     */
    public static FractionFormat getImproperInstance(final Locale locale) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the default complex format for the current locale.
     * @return the default complex format.
     */
    public static FractionFormat getProperInstance() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the default complex format for the given locale.
     * @param locale the specific locale used by the format.
     * @return the complex format specific to the given locale.
     */
    public static FractionFormat getProperInstance(final Locale locale) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Create a default number format.  The default number format is based on
     * {@link NumberFormat#getNumberInstance(java.util.Locale)} with the only
     * customizing is the maximum number of fraction digits, which is set to 0.
     * @return the default number format.
     */
    protected static NumberFormat getDefaultNumberFormat() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Formats a {@link Fraction} object to produce a string.  The fraction is
     * output in improper format.
     *
     * @param fraction the object to format.
     * @param toAppendTo where the text is to be appended
     * @param pos On input: an alignment field, if desired. On output: the
     * offsets of the alignment field
     * @return the value passed in as toAppendTo.
     */
    public // NOPMD - PMD false positive, we cannot have @Override here
    StringBuffer // NOPMD - PMD false positive, we cannot have @Override here
    format(// NOPMD - PMD false positive, we cannot have @Override here
    final Fraction fraction, final StringBuffer toAppendTo, final FieldPosition pos) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Formats an object and appends the result to a StringBuffer. <code>obj</code> must be either a
     * {@link Fraction} object or a {@link Number} object.  Any other type of
     * object will result in an {@link IllegalArgumentException} being thrown.
     *
     * @param obj the object to format.
     * @param toAppendTo where the text is to be appended
     * @param pos On input: an alignment field, if desired. On output: the
     * offsets of the alignment field
     * @return the value passed in as toAppendTo.
     * @see java.text.Format#format(java.lang.Object, java.lang.StringBuffer, java.text.FieldPosition)
     * @throws MathIllegalStateException if the number cannot be converted to a fraction
     * @throws MathIllegalArgumentException if <code>obj</code> is not a valid type.
     */
    @Override
    public StringBuffer format(final Object obj, final StringBuffer toAppendTo, final FieldPosition pos) throws MathIllegalArgumentException, MathIllegalStateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Parses a string to produce a {@link Fraction} object.
     * @param source the string to parse
     * @return the parsed {@link Fraction} object.
     * @exception MathIllegalStateException if the beginning of the specified string
     * cannot be parsed.
     */
    @Override
    public Fraction parse(final String source) throws MathIllegalStateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Parses a string to produce a {@link Fraction} object.  This method
     * expects the string to be formatted as an improper fraction.
     * @param source the string to parse
     * @param pos input/output parsing parameter.
     * @return the parsed {@link Fraction} object.
     */
    @Override
    public Fraction parse(final String source, final ParsePosition pos) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
