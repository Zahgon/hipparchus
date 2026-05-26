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

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalStateException;
import org.hipparchus.util.CompositeFormat;

/**
 * Formats a vector in components list format "{v0; v1; ...; vk-1}".
 * <p>The prefix and suffix "{" and "}" and the separator "; " can be replaced by
 * any user-defined strings. The number format for components can be configured.</p>
 * <p>White space is ignored at parse time, even if it is in the prefix, suffix
 * or separator specifications. So even if the default separator does include a space
 * character that is used at format time, both input string "{1;1;1}" and
 * " { 1 ; 1 ; 1 } " will be parsed without error and the same vector will be
 * returned. In the second case, however, the parse position after parsing will be
 * just after the closing curly brace, i.e. just before the trailing space.</p>
 */
public class RealVectorFormat {

    /**
     * The default prefix: "{".
     */
    private static final String DEFAULT_PREFIX = "{";

    /**
     * The default suffix: "}".
     */
    private static final String DEFAULT_SUFFIX = "}";

    /**
     * The default separator: ", ".
     */
    private static final String DEFAULT_SEPARATOR = "; ";

    /**
     * Prefix.
     */
    private final String prefix;

    /**
     * Suffix.
     */
    private final String suffix;

    /**
     * Separator.
     */
    private final String separator;

    /**
     * Trimmed prefix.
     */
    private final String trimmedPrefix;

    /**
     * Trimmed suffix.
     */
    private final String trimmedSuffix;

    /**
     * Trimmed separator.
     */
    private final String trimmedSeparator;

    /**
     * The format used for components.
     */
    private final NumberFormat format;

    /**
     * Create an instance with default settings.
     * <p>The instance uses the default prefix, suffix and separator:
     * "{", "}", and "; " and the default number format for components.</p>
     */
    public RealVectorFormat() {
        this(DEFAULT_PREFIX, DEFAULT_SUFFIX, DEFAULT_SEPARATOR, CompositeFormat.getDefaultNumberFormat());
    }

    /**
     * Create an instance with a custom number format for components.
     * @param format the custom format for components.
     */
    public RealVectorFormat(final NumberFormat format) {
        this(DEFAULT_PREFIX, DEFAULT_SUFFIX, DEFAULT_SEPARATOR, format);
    }

    /**
     * Create an instance with custom prefix, suffix and separator.
     * @param prefix prefix to use instead of the default "{"
     * @param suffix suffix to use instead of the default "}"
     * @param separator separator to use instead of the default "; "
     */
    public RealVectorFormat(final String prefix, final String suffix, final String separator) {
        this(prefix, suffix, separator, CompositeFormat.getDefaultNumberFormat());
    }

    /**
     * Create an instance with custom prefix, suffix, separator and format
     * for components.
     * @param prefix prefix to use instead of the default "{"
     * @param suffix suffix to use instead of the default "}"
     * @param separator separator to use instead of the default "; "
     * @param format the custom format for components.
     */
    public RealVectorFormat(final String prefix, final String suffix, final String separator, final NumberFormat format) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.separator = separator;
        trimmedPrefix = prefix.trim();
        trimmedSuffix = suffix.trim();
        trimmedSeparator = separator.trim();
        this.format = format;
    }

    /**
     * Get the set of locales for which real vectors formats are available.
     * <p>This is the same set as the {@link NumberFormat} set.</p>
     * @return available real vector format locales.
     */
    public static Locale[] getAvailableLocales() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the format prefix.
     * @return format prefix.
     */
    public String getPrefix() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the format suffix.
     * @return format suffix.
     */
    public String getSuffix() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the format separator between components.
     * @return format separator.
     */
    public String getSeparator() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the components format.
     * @return components format.
     */
    public NumberFormat getFormat() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the default real vector format for the current locale.
     * @return the default real vector format.
     * @since 1.4
     */
    public static RealVectorFormat getRealVectorFormat() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the default real vector format for the given locale.
     * @param locale the specific locale used by the format.
     * @return the real vector format specific to the given locale.
     * @since 1.4
     */
    public static RealVectorFormat getRealVectorFormat(final Locale locale) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * This method calls {@link #format(RealVector,StringBuffer,FieldPosition)}.
     *
     * @param v RealVector object to format.
     * @return a formatted vector.
     */
    public String format(RealVector v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Formats a {@link RealVector} object to produce a string.
     * @param vector the object to format.
     * @param toAppendTo where the text is to be appended
     * @param pos On input: an alignment field, if desired. On output: the
     *            offsets of the alignment field
     * @return the value passed in as toAppendTo.
     */
    public StringBuffer format(RealVector vector, StringBuffer toAppendTo, FieldPosition pos) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Parse a string to produce a {@link RealVector} object.
     *
     * @param source String to parse.
     * @return the parsed {@link RealVector} object.
     * @throws MathIllegalStateException if the beginning of the specified string
     * cannot be parsed.
     */
    public ArrayRealVector parse(String source) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Parse a string to produce a {@link RealVector} object.
     *
     * @param source String to parse.
     * @param pos input/ouput parsing parameter.
     * @return the parsed {@link RealVector} object.
     */
    public ArrayRealVector parse(String source, ParsePosition pos) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
