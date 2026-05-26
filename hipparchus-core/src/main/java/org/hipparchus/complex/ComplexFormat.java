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
package org.hipparchus.complex;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.MathIllegalStateException;
import org.hipparchus.exception.NullArgumentException;
import org.hipparchus.util.CompositeFormat;
import org.hipparchus.util.MathUtils;

/**
 * Formats a Complex number in cartesian format "Re(c) + Im(c)i".  'i' can
 * be replaced with 'j' (or anything else), and the number format for both real
 * and imaginary parts can be configured.
 */
public class ComplexFormat {

    /**
     * The default imaginary character.
     */
    private static final String DEFAULT_IMAGINARY_CHARACTER = "i";

    /**
     * The notation used to signify the imaginary part of the complex number.
     */
    private final String imaginaryCharacter;

    /**
     * The format used for the imaginary part.
     */
    private final NumberFormat imaginaryFormat;

    /**
     * The format used for the real part.
     */
    private final NumberFormat realFormat;

    /**
     * Create an instance with the default imaginary character, 'i', and the
     * default number format for both real and imaginary parts.
     */
    public ComplexFormat() {
        this.imaginaryCharacter = DEFAULT_IMAGINARY_CHARACTER;
        this.imaginaryFormat = CompositeFormat.getDefaultNumberFormat();
        this.realFormat = imaginaryFormat;
    }

    /**
     * Create an instance with a custom number format for both real and
     * imaginary parts.
     * @param format the custom format for both real and imaginary parts.
     * @throws NullArgumentException if {@code realFormat} is {@code null}.
     */
    public ComplexFormat(NumberFormat format) throws NullArgumentException {
        MathUtils.checkNotNull(format, LocalizedCoreFormats.IMAGINARY_FORMAT);
        this.imaginaryCharacter = DEFAULT_IMAGINARY_CHARACTER;
        this.imaginaryFormat = format;
        this.realFormat = format;
    }

    /**
     * Create an instance with a custom number format for the real part and a
     * custom number format for the imaginary part.
     * @param realFormat the custom format for the real part.
     * @param imaginaryFormat the custom format for the imaginary part.
     * @throws NullArgumentException if {@code imaginaryFormat} is {@code null}.
     * @throws NullArgumentException if {@code realFormat} is {@code null}.
     */
    public ComplexFormat(NumberFormat realFormat, NumberFormat imaginaryFormat) throws NullArgumentException {
        MathUtils.checkNotNull(imaginaryFormat, LocalizedCoreFormats.IMAGINARY_FORMAT);
        MathUtils.checkNotNull(realFormat, LocalizedCoreFormats.REAL_FORMAT);
        this.imaginaryCharacter = DEFAULT_IMAGINARY_CHARACTER;
        this.imaginaryFormat = imaginaryFormat;
        this.realFormat = realFormat;
    }

    /**
     * Create an instance with a custom imaginary character, and the default
     * number format for both real and imaginary parts.
     * @param imaginaryCharacter The custom imaginary character.
     * @throws NullArgumentException if {@code imaginaryCharacter} is
     * {@code null}.
     * @throws MathIllegalArgumentException if {@code imaginaryCharacter} is an
     * empty string.
     */
    public ComplexFormat(String imaginaryCharacter) throws MathIllegalArgumentException, NullArgumentException {
        this(imaginaryCharacter, CompositeFormat.getDefaultNumberFormat());
    }

    /**
     * Create an instance with a custom imaginary character, and a custom number
     * format for both real and imaginary parts.
     * @param imaginaryCharacter The custom imaginary character.
     * @param format the custom format for both real and imaginary parts.
     * @throws NullArgumentException if {@code imaginaryCharacter} is
     * {@code null}.
     * @throws MathIllegalArgumentException if {@code imaginaryCharacter} is an
     * empty string.
     * @throws NullArgumentException if {@code format} is {@code null}.
     */
    public ComplexFormat(String imaginaryCharacter, NumberFormat format) throws MathIllegalArgumentException, NullArgumentException {
        this(imaginaryCharacter, format, format);
    }

    /**
     * Create an instance with a custom imaginary character, a custom number
     * format for the real part, and a custom number format for the imaginary
     * part.
     *
     * @param imaginaryCharacter The custom imaginary character.
     * @param realFormat the custom format for the real part.
     * @param imaginaryFormat the custom format for the imaginary part.
     * @throws NullArgumentException if {@code imaginaryCharacter} is
     * {@code null}.
     * @throws MathIllegalArgumentException if {@code imaginaryCharacter} is an
     * empty string.
     * @throws NullArgumentException if {@code imaginaryFormat} is {@code null}.
     * @throws NullArgumentException if {@code realFormat} is {@code null}.
     */
    public ComplexFormat(String imaginaryCharacter, NumberFormat realFormat, NumberFormat imaginaryFormat) throws MathIllegalArgumentException, NullArgumentException {
        if (imaginaryCharacter == null) {
            throw new NullArgumentException();
        }
        if (imaginaryCharacter.isEmpty()) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.NO_DATA);
        }
        MathUtils.checkNotNull(imaginaryFormat, LocalizedCoreFormats.IMAGINARY_FORMAT);
        MathUtils.checkNotNull(realFormat, LocalizedCoreFormats.REAL_FORMAT);
        this.imaginaryCharacter = imaginaryCharacter;
        this.imaginaryFormat = imaginaryFormat;
        this.realFormat = realFormat;
    }

    /**
     * Get the set of locales for which complex formats are available.
     * <p>This is the same set as the {@link NumberFormat} set.</p>
     * @return available complex format locales.
     */
    public static Locale[] getAvailableLocales() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * This method calls {@link #format(Object,StringBuffer,FieldPosition)}.
     *
     * @param c Complex object to format.
     * @return A formatted number in the form "Re(c) + Im(c)i".
     */
    public String format(Complex c) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * This method calls {@link #format(Object,StringBuffer,FieldPosition)}.
     *
     * @param c Double object to format.
     * @return A formatted number.
     */
    public String format(Double c) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Formats a {@link Complex} object to produce a string.
     *
     * @param complex the object to format.
     * @param toAppendTo where the text is to be appended
     * @param pos On input: an alignment field, if desired. On output: the
     *            offsets of the alignment field
     * @return the value passed in as toAppendTo.
     */
    public StringBuffer format(Complex complex, StringBuffer toAppendTo, FieldPosition pos) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Format the absolute value of the imaginary part.
     *
     * @param absIm Absolute value of the imaginary part of a complex number.
     * @param toAppendTo where the text is to be appended.
     * @param pos On input: an alignment field, if desired. On output: the
     * offsets of the alignment field.
     * @return the value passed in as toAppendTo.
     */
    private StringBuffer formatImaginary(double absIm, StringBuffer toAppendTo, FieldPosition pos) {
        pos.setBeginIndex(0);
        pos.setEndIndex(0);
        CompositeFormat.formatDouble(absIm, getImaginaryFormat(), toAppendTo, pos);
        if ("1".equals(toAppendTo.toString())) {
            // Remove the character "1" if it is the only one.
            toAppendTo.setLength(0);
        }
        return toAppendTo;
    }

    /**
     * Formats a object to produce a string.  {@code obj} must be either a
     * {@link Complex} object or a {@link Number} object.  Any other type of
     * object will result in an {@link IllegalArgumentException} being thrown.
     *
     * @param obj the object to format.
     * @param toAppendTo where the text is to be appended
     * @param pos On input: an alignment field, if desired. On output: the
     *            offsets of the alignment field
     * @return the value passed in as toAppendTo.
     * @see java.text.Format#format(java.lang.Object, java.lang.StringBuffer, java.text.FieldPosition)
     * @throws MathIllegalArgumentException is {@code obj} is not a valid type.
     */
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Access the imaginaryCharacter.
     * @return the imaginaryCharacter.
     */
    public String getImaginaryCharacter() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Access the imaginaryFormat.
     * @return the imaginaryFormat.
     */
    public NumberFormat getImaginaryFormat() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the default complex format for the current locale.
     * @return the default complex format.
     * @since 1.4
     */
    public static ComplexFormat getComplexFormat() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the default complex format for the given locale.
     * @param locale the specific locale used by the format.
     * @return the complex format specific to the given locale.
     * @since 1.4
     */
    public static ComplexFormat getComplexFormat(Locale locale) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the default complex format for the given locale.
     * @param locale the specific locale used by the format.
     * @param imaginaryCharacter Imaginary character.
     * @return the complex format specific to the given locale.
     * @throws NullArgumentException if {@code imaginaryCharacter} is
     * {@code null}.
     * @throws MathIllegalArgumentException if {@code imaginaryCharacter} is an
     * empty string.
     * @since 1.4
     */
    public static ComplexFormat getComplexFormat(String imaginaryCharacter, Locale locale) throws MathIllegalArgumentException, NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Access the realFormat.
     * @return the realFormat.
     */
    public NumberFormat getRealFormat() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Parses a string to produce a {@link Complex} object.
     *
     * @param source the string to parse.
     * @return the parsed {@link Complex} object.
     * @throws MathIllegalStateException if the beginning of the specified string
     * cannot be parsed.
     */
    public Complex parse(String source) throws MathIllegalStateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Parses a string to produce a {@link Complex} object.
     *
     * @param source the string to parse
     * @param pos input/ouput parsing parameter.
     * @return the parsed {@link Complex} object.
     */
    public Complex parse(String source, ParsePosition pos) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
