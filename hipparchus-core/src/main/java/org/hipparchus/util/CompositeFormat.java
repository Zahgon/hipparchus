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
package org.hipparchus.util;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;

/**
 * Base class for formatters of composite objects (complex numbers, vectors ...).
 */
public class CompositeFormat {

    /**
     * Class contains only static methods.
     */
    private CompositeFormat() {
    }

    /**
     * Create a default number format.  The default number format is based on
     * {@link NumberFormat#getInstance()} with the only customizing that the
     * maximum number of fraction digits is set to 10.
     * @return the default number format.
     */
    public static NumberFormat getDefaultNumberFormat() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Create a default number format.  The default number format is based on
     * {@link NumberFormat#getInstance(java.util.Locale)} with the only
     * customizing that the maximum number of fraction digits is set to 10.
     * @param locale the specific locale used by the format.
     * @return the default number format specific to the given locale.
     */
    public static NumberFormat getDefaultNumberFormat(final Locale locale) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Parses <code>source</code> until a non-whitespace character is found.
     *
     * @param source the string to parse
     * @param pos input/output parsing parameter.  On output, <code>pos</code>
     *        holds the index of the next non-whitespace character.
     */
    public static void parseAndIgnoreWhitespace(final String source, final ParsePosition pos) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Parses <code>source</code> until a non-whitespace character is found.
     *
     * @param source the string to parse
     * @param pos input/output parsing parameter.
     * @return the first non-whitespace character.
     */
    public static char parseNextCharacter(final String source, final ParsePosition pos) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Parses <code>source</code> for special double values.  These values
     * include Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY.
     *
     * @param source the string to parse
     * @param value the special value to parse.
     * @param pos input/output parsing parameter.
     * @return the special number.
     */
    private static Number parseNumber(final String source, final double value, final ParsePosition pos) {
        Number ret = null;
        StringBuilder sb = new StringBuilder();
        sb.append('(').append(value).append(')');
        final int n = sb.length();
        final int startIndex = pos.getIndex();
        final int endIndex = startIndex + n;
        if (endIndex < source.length() && source.substring(startIndex, endIndex).compareTo(sb.toString()) == 0) {
            ret = value;
            pos.setIndex(endIndex);
        }
        return ret;
    }

    /**
     * Parses <code>source</code> for a number.  This method can parse normal,
     * numeric values as well as special values.  These special values include
     * Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY.
     *
     * @param source the string to parse
     * @param format the number format used to parse normal, numeric values.
     * @param pos input/output parsing parameter.
     * @return the parsed number.
     */
    public static Number parseNumber(final String source, final NumberFormat format, final ParsePosition pos) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Parse <code>source</code> for an expected fixed string.
     * @param source the string to parse
     * @param expected expected string
     * @param pos input/output parsing parameter.
     * @return true if the expected string was there
     */
    public static boolean parseFixedstring(final String source, final String expected, final ParsePosition pos) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Formats a double value to produce a string.  In general, the value is
     * formatted using the formatting rules of <code>format</code>.  There are
     * three exceptions to this:
     * <ol>
     * <li>NaN is formatted as '(NaN)'</li>
     * <li>Positive infinity is formatted as '(Infinity)'</li>
     * <li>Negative infinity is formatted as '(-Infinity)'</li>
     * </ol>
     *
     * @param value the double to format.
     * @param format the format used.
     * @param toAppendTo where the text is to be appended
     * @param pos On input: an alignment field, if desired. On output: the
     *            offsets of the alignment field
     * @return the value passed in as toAppendTo.
     */
    public static StringBuffer formatDouble(final double value, final NumberFormat format, final StringBuffer toAppendTo, final FieldPosition pos) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
