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

import java.util.Arrays;
import org.hipparchus.CalculusFieldElement;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.MathRuntimeException;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.FieldSinhCosh;
import org.hipparchus.util.MathUtils;

/**
 *  Decimal floating point library for Java
 *
 *  <p>Another floating point class.  This one is built using radix 10000
 *  which is 10<sup>4</sup>, so its almost decimal.</p>
 *
 *  <p>The design goals here are:</p>
 *  <ol>
 *    <li>Decimal math, or close to it</li>
 *    <li>Settable precision (but no mix between numbers using different settings)</li>
 *    <li>Portability.  Code should be kept as portable as possible.</li>
 *    <li>Performance</li>
 *    <li>Accuracy  - Results should always be +/- 1 ULP for basic
 *         algebraic operation</li>
 *    <li>Comply with IEEE 854-1987 as much as possible.
 *         (See IEEE 854-1987 notes below)</li>
 *  </ol>
 *
 *  <p>Trade offs:</p>
 *  <ol>
 *    <li>Memory foot print.  I'm using more memory than necessary to
 *         represent numbers to get better performance.</li>
 *    <li>Digits are bigger, so rounding is a greater loss.  So, if you
 *         really need 12 decimal digits, better use 4 base 10000 digits
 *         there can be one partially filled.</li>
 *  </ol>
 *
 *  <p>Numbers are represented  in the following form:
 *  \[
 *  n  =  \mathrm{sign} \times \mathrm{mant} \times \mathrm{radix}^\mathrm{exp}
 *  \]
 *  where sign is &plusmn;1, mantissa represents a fractional number between
 *  zero and one.  mant[0] is the least significant digit.
 *  exp is in the range of -32767 to 32768</p>
 *
 *  <p>IEEE 854-1987  Notes and differences</p>
 *
 *  <p>IEEE 854 requires the radix to be either 2 or 10.  The radix here is
 *  10000, so that requirement is not met, but  it is possible that a
 *  subclassed can be made to make it behave as a radix 10
 *  number.  It is my opinion that if it looks and behaves as a radix
 *  10 number then it is one and that requirement would be met.</p>
 *
 *  <p>The radix of 10000 was chosen because it should be faster to operate
 *  on 4 decimal digits at once instead of one at a time.  Radix 10 behavior
 *  can be realized by adding an additional rounding step to ensure that
 *  the number of decimal digits represented is constant.</p>
 *
 *  <p>The IEEE standard specifically leaves out internal data encoding,
 *  so it is reasonable to conclude that such a subclass of this radix
 *  10000 system is merely an encoding of a radix 10 system.</p>
 *
 *  <p>IEEE 854 also specifies the existence of "sub-normal" numbers.  This
 *  class does not contain any such entities.  The most significant radix
 *  10000 digit is always non-zero.  Instead, we support "gradual underflow"
 *  by raising the underflow flag for numbers less with exponent less than
 *  expMin, but don't flush to zero until the exponent reaches MIN_EXP-digits.
 *  Thus the smallest number we can represent would be:
 *  1E(-(MIN_EXP-digits-1)*4),  eg, for digits=5, MIN_EXP=-32767, that would
 *  be 1e-131092.</p>
 *
 *  <p>IEEE 854 defines that the implied radix point lies just to the right
 *  of the most significant digit and to the left of the remaining digits.
 *  This implementation puts the implied radix point to the left of all
 *  digits including the most significant one.  The most significant digit
 *  here is the one just to the right of the radix point.  This is a fine
 *  detail and is really only a matter of definition.  Any side effects of
 *  this can be rendered invisible by a subclass.</p>
 * @see DfpField
 */
public class Dfp implements CalculusFieldElement<Dfp> {

    /**
     * The radix, or base of this system.  Set to 10000
     */
    public static final int RADIX = 10000;

    /**
     * The minimum exponent before underflow is signaled.  Flush to zero
     *  occurs at minExp-DIGITS
     */
    public static final int MIN_EXP = -32767;

    /**
     * The maximum exponent before overflow is signaled and results flushed
     *  to infinity
     */
    public static final int MAX_EXP = 32768;

    /**
     * The amount under/overflows are scaled by before going to trap handler
     */
    public static final int ERR_SCALE = 32760;

    /**
     * Indicator value for normal finite numbers.
     */
    public static final byte FINITE = 0;

    /**
     * Indicator value for Infinity.
     */
    public static final byte INFINITE = 1;

    /**
     * Indicator value for signaling NaN.
     */
    public static final byte SNAN = 2;

    /**
     * Indicator value for quiet NaN.
     */
    public static final byte QNAN = 3;

    /**
     * String for NaN representation.
     */
    private static final String NAN_STRING = "NaN";

    /**
     * String for positive infinity representation.
     */
    private static final String POS_INFINITY_STRING = "Infinity";

    /**
     * String for negative infinity representation.
     */
    private static final String NEG_INFINITY_STRING = "-Infinity";

    /**
     * Name for traps triggered by addition.
     */
    private static final String ADD_TRAP = "add";

    /**
     * Name for traps triggered by multiplication.
     */
    private static final String MULTIPLY_TRAP = "multiply";

    /**
     * Name for traps triggered by division.
     */
    private static final String DIVIDE_TRAP = "divide";

    /**
     * Name for traps triggered by square root.
     */
    private static final String SQRT_TRAP = "sqrt";

    /**
     * Name for traps triggered by alignment.
     */
    private static final String ALIGN_TRAP = "align";

    /**
     * Name for traps triggered by truncation.
     */
    private static final String TRUNC_TRAP = "trunc";

    /**
     * Name for traps triggered by nextAfter.
     */
    private static final String NEXT_AFTER_TRAP = "nextAfter";

    /**
     * Name for traps triggered by lessThan.
     */
    private static final String LESS_THAN_TRAP = "lessThan";

    /**
     * Name for traps triggered by greaterThan.
     */
    private static final String GREATER_THAN_TRAP = "greaterThan";

    /**
     * Name for traps triggered by newInstance.
     */
    private static final String NEW_INSTANCE_TRAP = "newInstance";

    /**
     * Multiplication factor for number of digits used to compute linear combinations.
     */
    private static final int LINEAR_COMBINATION_DIGITS_FACTOR = 2;

    /**
     * Mantissa.
     */
    protected int[] mant;

    /**
     * Sign bit: 1 for positive, -1 for negative.
     */
    protected byte sign;

    /**
     * Exponent.
     */
    protected int exp;

    /**
     * Indicator for non-finite / non-number values.
     */
    protected byte nans;

    /**
     * Factory building similar Dfp's.
     */
    private final DfpField field;

    /**
     * Makes an instance with a value of zero.
     * @param field field to which this instance belongs
     */
    protected Dfp(final DfpField field) {
        mant = new int[field.getRadixDigits()];
        sign = 1;
        exp = 0;
        nans = FINITE;
        this.field = field;
    }

    /**
     * Create an instance from a byte value.
     * @param field field to which this instance belongs
     * @param x value to convert to an instance
     */
    protected Dfp(final DfpField field, byte x) {
        this(field, (long) x);
    }

    /**
     * Create an instance from an int value.
     * @param field field to which this instance belongs
     * @param x value to convert to an instance
     */
    protected Dfp(final DfpField field, int x) {
        this(field, (long) x);
    }

    /**
     * Create an instance from a long value.
     * @param field field to which this instance belongs
     * @param x value to convert to an instance
     */
    protected Dfp(final DfpField field, long x) {
        // initialize as if 0
        mant = new int[field.getRadixDigits()];
        nans = FINITE;
        this.field = field;
        boolean isLongMin = false;
        if (x == Long.MIN_VALUE) {
            // special case for Long.MIN_VALUE (-9223372036854775808)
            // we must shift it before taking its absolute value
            isLongMin = true;
            ++x;
        }
        // set the sign
        if (x < 0) {
            sign = -1;
            x = -x;
        } else {
            sign = 1;
        }
        exp = 0;
        while (x != 0) {
            System.arraycopy(mant, mant.length - exp, mant, mant.length - 1 - exp, exp);
            mant[mant.length - 1] = (int) (x % RADIX);
            x /= RADIX;
            exp++;
        }
        if (isLongMin) {
            // remove the shift added for Long.MIN_VALUE
            // we know in this case that fixing the last digit is sufficient
            for (int i = 0; i < mant.length - 1; i++) {
                if (mant[i] != 0) {
                    mant[i]++;
                    break;
                }
            }
        }
    }

    /**
     * Create an instance from a double value.
     * @param field field to which this instance belongs
     * @param x value to convert to an instance
     */
    protected Dfp(final DfpField field, double x) {
        // initialize as if 0
        mant = new int[field.getRadixDigits()];
        this.field = field;
        long bits = Double.doubleToLongBits(x);
        long mantissa = bits & 0x000fffffffffffffL;
        int exponent = (int) ((bits & 0x7ff0000000000000L) >> 52) - 1023;
        if (exponent == -1023) {
            // Zero or sub-normal
            if (x == 0) {
                // make sure 0 has the right sign
                if ((bits & 0x8000000000000000L) != 0) {
                    sign = -1;
                } else {
                    sign = 1;
                }
                return;
            }
            exponent++;
            // Normalize the subnormal number
            while ((mantissa & 0x0010000000000000L) == 0) {
                exponent--;
                mantissa <<= 1;
            }
            mantissa &= 0x000fffffffffffffL;
        }
        if (exponent == 1024) {
            // infinity or NAN
            if (x != x) {
                sign = (byte) 1;
                nans = QNAN;
            } else if (x < 0) {
                sign = (byte) -1;
                nans = INFINITE;
            } else {
                sign = (byte) 1;
                nans = INFINITE;
            }
            return;
        }
        Dfp xdfp = new Dfp(field, mantissa);
        // Divide by 2^52, then add one
        xdfp = xdfp.divide(new Dfp(field, 4503599627370496L)).add(field.getOne());
        xdfp = xdfp.multiply(DfpMath.pow(field.getTwo(), exponent));
        if ((bits & 0x8000000000000000L) != 0) {
            xdfp = xdfp.negate();
        }
        System.arraycopy(xdfp.mant, 0, mant, 0, mant.length);
        sign = xdfp.sign;
        exp = xdfp.exp;
        nans = xdfp.nans;
    }

    /**
     * Copy constructor.
     * @param d instance to copy
     */
    public Dfp(final Dfp d) {
        mant = d.mant.clone();
        sign = d.sign;
        exp = d.exp;
        nans = d.nans;
        field = d.field;
    }

    /**
     * Create an instance from a String representation.
     * @param field field to which this instance belongs
     * @param s string representation of the instance
     */
    protected Dfp(final DfpField field, final String s) {
        // initialize as if 0
        mant = new int[field.getRadixDigits()];
        sign = 1;
        nans = FINITE;
        this.field = field;
        boolean decimalFound = false;
        // size of radix in decimal digits
        final int rsize = 4;
        // Starting offset into Striped
        final int offset = 4;
        final char[] striped = new char[getRadixDigits() * rsize + offset * 2];
        // Check some special cases
        if (POS_INFINITY_STRING.equals(s)) {
            sign = (byte) 1;
            nans = INFINITE;
            return;
        }
        if (NEG_INFINITY_STRING.equals(s)) {
            sign = (byte) -1;
            nans = INFINITE;
            return;
        }
        if (NAN_STRING.equals(s)) {
            sign = (byte) 1;
            nans = QNAN;
            return;
        }
        // Check for scientific notation
        int p = s.indexOf('e');
        if (p == -1) {
            // try upper case?
            p = s.indexOf('E');
        }
        final String fpdecimal;
        int sciexp = 0;
        if (p != -1) {
            // scientific notation
            fpdecimal = s.substring(0, p);
            String fpexp = s.substring(p + 1);
            boolean negative = false;
            for (int i = 0; i < fpexp.length(); i++) {
                if (fpexp.charAt(i) == '-') {
                    negative = true;
                    continue;
                }
                if (fpexp.charAt(i) >= '0' && fpexp.charAt(i) <= '9') {
                    sciexp = sciexp * 10 + fpexp.charAt(i) - '0';
                }
            }
            if (negative) {
                sciexp = -sciexp;
            }
        } else {
            // normal case
            fpdecimal = s;
        }
        // If there is a minus sign in the number then it is negative
        if (fpdecimal.indexOf('-') != -1) {
            sign = -1;
        }
        // First off, find all of the leading zeros, trailing zeros, and significant digits
        p = 0;
        // Move p to first significant digit
        int decimalPos = 0;
        for (; ; ) {
            if (fpdecimal.charAt(p) >= '1' && fpdecimal.charAt(p) <= '9') {
                break;
            }
            if (decimalFound && fpdecimal.charAt(p) == '0') {
                decimalPos--;
            }
            if (fpdecimal.charAt(p) == '.') {
                decimalFound = true;
            }
            p++;
            if (p == fpdecimal.length()) {
                break;
            }
        }
        // Copy the string onto Stripped
        int q = offset;
        striped[0] = '0';
        striped[1] = '0';
        striped[2] = '0';
        striped[3] = '0';
        int significantDigits = 0;
        for (; ; ) {
            if (p == (fpdecimal.length())) {
                break;
            }
            // Don't want to run pass the end of the array
            if (q == mant.length * rsize + offset + 1) {
                break;
            }
            if (fpdecimal.charAt(p) == '.') {
                decimalFound = true;
                decimalPos = significantDigits;
                p++;
                continue;
            }
            if (fpdecimal.charAt(p) < '0' || fpdecimal.charAt(p) > '9') {
                p++;
                continue;
            }
            striped[q] = fpdecimal.charAt(p);
            q++;
            p++;
            significantDigits++;
        }
        // If the decimal point has been found then get rid of trailing zeros.
        if (decimalFound && q != offset) {
            for (; ; ) {
                q--;
                if (q == offset) {
                    break;
                }
                if (striped[q] == '0') {
                    significantDigits--;
                } else {
                    break;
                }
            }
        }
        // special case of numbers like "0.00000"
        if (decimalFound && significantDigits == 0) {
            decimalPos = 0;
        }
        // Implicit decimal point at end of number if not present
        if (!decimalFound) {
            decimalPos = q - offset;
        }
        // Find the number of significant trailing zeros
        // set q to point to first sig digit
        q = offset;
        p = significantDigits - 1 + offset;
        while (p > q) {
            if (striped[p] != '0') {
                break;
            }
            p--;
        }
        // Make sure the decimal is on a mod 10000 boundary
        int i = ((rsize * 100) - decimalPos - sciexp % rsize) % rsize;
        q -= i;
        decimalPos += i;
        // Make the mantissa length right by adding zeros at the end if necessary
        while ((p - q) < (mant.length * rsize)) {
            for (i = 0; i < rsize; i++) {
                striped[++p] = '0';
            }
        }
        // Ok, now we know how many trailing zeros there are,
        // and where the least significant digit is
        for (i = mant.length - 1; i >= 0; i--) {
            mant[i] = (striped[q] - '0') * 1000 + (striped[q + 1] - '0') * 100 + (striped[q + 2] - '0') * 10 + (striped[q + 3] - '0');
            q += 4;
        }
        exp = (decimalPos + sciexp) / rsize;
        if (q < striped.length) {
            // Is there possible another digit?
            round((striped[q] - '0') * 1000);
        }
    }

    /**
     * Creates an instance with a non-finite value.
     * @param field field to which this instance belongs
     * @param sign sign of the Dfp to create
     * @param nans code of the value, must be one of {@link #INFINITE},
     * {@link #SNAN},  {@link #QNAN}
     */
    protected Dfp(final DfpField field, final byte sign, final byte nans) {
        this.field = field;
        this.mant = new int[field.getRadixDigits()];
        this.sign = sign;
        this.exp = 0;
        this.nans = nans;
    }

    /**
     * Create an instance with a value of 0.
     * Use this internally in preference to constructors to facilitate subclasses
     * @return a new instance with a value of 0
     */
    public Dfp newInstance() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Create an instance from a byte value.
     * @param x value to convert to an instance
     * @return a new instance with value x
     */
    public Dfp newInstance(final byte x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Create an instance from an int value.
     * @param x value to convert to an instance
     * @return a new instance with value x
     */
    public Dfp newInstance(final int x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Create an instance from a long value.
     * @param x value to convert to an instance
     * @return a new instance with value x
     */
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
     * Create an instance by copying an existing one.
     * Use this internally in preference to constructors to facilitate subclasses.
     * @param d instance to copy
     * @return a new instance with the same value as d
     */
    public Dfp newInstance(final Dfp d) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Create an instance from a String representation.
     * Use this internally in preference to constructors to facilitate subclasses.
     * @param s string representation of the instance
     * @return a new instance parsed from specified string
     */
    public Dfp newInstance(final String s) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Creates an instance with a non-finite value.
     * @param sig sign of the Dfp to create
     * @param code code of the value, must be one of {@link #INFINITE},
     * {@link #SNAN},  {@link #QNAN}
     * @return a new instance with a non-finite value
     */
    public Dfp newInstance(final byte sig, final byte code) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Creates an instance by converting the instance to a different field (i.e. different accuracy).
     * <p>
     * If the target field as a greater number of digits, the extra least significant digits
     * will be set to zero.
     * </p>
     * @param targetField field to convert the instance to
     * @param rmode rounding mode to use if target field as less digits than the instance, can be null otherwise
     * @return converted instance (or the instance itself if it already has the required number of digits)
     * @see DfpField#getExtendedField(int, boolean)
     * @since 1.7
     */
    public Dfp newInstance(final DfpField targetField, final DfpField.RoundingMode rmode) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if mantissa of a truncated number must be incremented.
     * <p>
     * This method must be called <em>only</em> when some non-zero digits have been
     * discarded (i.e. when either {@code zeroLSB} is false or {@code lastDiscarded} is non-zero),
     * otherwise it would generate false positive
     * </p>
     * @param rmode rounding mode to use if target field as less digits than the instance, can be null otherwise
     * @param zeroLSB true is least significant discarded digits (except last) are all zero
     * @param lastDiscarded last discarded digit
     * @param firstNonDiscarded first non-discarded digit
     * @param sign of the number
     * @return true if the already truncated mantissa should be incremented to achieve correct rounding
     * @since 1.7
     */
    private static boolean shouldIncrement(final DfpField.RoundingMode rmode, final boolean zeroLSB, final int lastDiscarded, final int firstNonDiscarded, final int sign) {
        switch(rmode) {
            case ROUND_DOWN:
                return false;
            case ROUND_UP:
                return true;
            case ROUND_HALF_UP:
                return lastDiscarded >= 5000;
            case ROUND_HALF_DOWN:
                return isAboveHalfWay(zeroLSB, lastDiscarded);
            case ROUND_HALF_EVEN:
                return (isHalfWay(zeroLSB, lastDiscarded) && (firstNonDiscarded & 0x1) == 0x1) || isAboveHalfWay(zeroLSB, lastDiscarded);
            case ROUND_HALF_ODD:
                return (isHalfWay(zeroLSB, lastDiscarded) && (firstNonDiscarded & 0x1) == 0x0) || isAboveHalfWay(zeroLSB, lastDiscarded);
            case ROUND_CEIL:
                return sign > 0;
            case ROUND_FLOOR:
                return sign < 0;
            default:
                // this should never happen
                throw MathRuntimeException.createInternalError();
        }
    }

    /**
     * Increment the mantissa of the instance
     * @since 1.7
     */
    private void incrementMantissa() {
        boolean carry = true;
        for (int i = 0; carry && i < mant.length; ++i) {
            ++mant[i];
            if (mant[i] >= RADIX) {
                mant[i] -= RADIX;
            } else {
                carry = false;
            }
        }
        if (carry) {
            // we have exceeded capacity, we need to drop one digit
            for (int i = 0; i < mant.length - 1; i++) {
                mant[i] = mant[i + 1];
            }
            mant[mant.length - 1] = 1;
            exp++;
        }
    }

    /**
     * Check if discarded digits are exactly halfway between two rounder numbers.
     * @param zeroLSB true is least significant discarded digits (except last) are all zero
     * @param lastDiscarded last discarded digit
     * @return true if discarded digits correspond to a number exactly halfway between two rounded numbers
     * @since 1.7
     */
    private static boolean isHalfWay(final boolean zeroLSB, final int lastDiscarded) {
        return lastDiscarded == 5000 && zeroLSB;
    }

    /**
     * Check if discarded digits are strictly above halfway between two rounder numbers.
     * @param zeroLSB true is least significant discarded digits (except last) are all zero
     * @param lastDiscarded last discarded digit
     * @return true if discarded digits correspond to a number strictly above halfway between two rounded numbers
     * @since 1.7
     */
    private static boolean isAboveHalfWay(final boolean zeroLSB, final int lastDiscarded) {
        return (lastDiscarded > 5000) || (lastDiscarded == 5000 && !zeroLSB);
    }

    /**
     * Get the {@link org.hipparchus.Field Field} (really a {@link DfpField}) to which the instance belongs.
     * <p>
     * The field is linked to the number of digits and acts as a factory
     * for {@link Dfp} instances.
     * </p>
     * @return {@link org.hipparchus.Field Field} (really a {@link DfpField}) to which the instance belongs
     */
    @Override
    public DfpField getField() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the number of radix digits of the instance.
     * @return number of radix digits
     */
    public int getRadixDigits() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the constant 0.
     * @return a Dfp with value zero
     */
    public Dfp getZero() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the constant 1.
     * @return a Dfp with value one
     */
    public Dfp getOne() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the constant 2.
     * @return a Dfp with value two
     */
    public Dfp getTwo() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Shift the mantissa left, and adjust the exponent to compensate.
     */
    protected void shiftLeft() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* Note that shiftRight() does not call round() as that round() itself
     uses shiftRight() */
    /**
     * Shift the mantissa right, and adjust the exponent to compensate.
     */
    protected void shiftRight() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Make our exp equal to the supplied one, this may cause rounding.
     *  Also causes de-normalized numbers.  These numbers are generally
     *  dangerous because most routines assume normalized numbers.
     *  Align doesn't round, so it will return the last digit destroyed
     *  by shifting right.
     *  @param e desired exponent
     *  @return last digit destroyed by shifting right
     */
    protected int align(int e) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if instance is less than x.
     * @param x number to check instance against
     * @return true if instance is less than x and neither are NaN, false otherwise
     */
    public boolean lessThan(final Dfp x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if instance is greater than x.
     * @param x number to check instance against
     * @return true if instance is greater than x and neither are NaN, false otherwise
     */
    public boolean greaterThan(final Dfp x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if instance is less than or equal to 0.
     * @return true if instance is not NaN and less than or equal to 0, false otherwise
     */
    public boolean negativeOrNull() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if instance is strictly less than 0.
     * @return true if instance is not NaN and less than or equal to 0, false otherwise
     */
    public boolean strictlyNegative() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if instance is greater than or equal to 0.
     * @return true if instance is not NaN and greater than or equal to 0, false otherwise
     */
    public boolean positiveOrNull() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if instance is strictly greater than 0.
     * @return true if instance is not NaN and greater than or equal to 0, false otherwise
     */
    public boolean strictlyPositive() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp abs() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInfinite() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNaN() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if instance is equal to zero.
     * @return true if instance is equal to zero
     */
    @Override
    public boolean isZero() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if instance is equal to x.
     * @param other object to check instance against
     * @return true if instance is equal to x and neither are NaN, false otherwise
     */
    @Override
    public boolean equals(final Object other) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets a hashCode for the instance.
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if instance is not equal to x.
     * @param x number to check instance against
     * @return true if instance is not equal to x and neither are NaN, false otherwise
     */
    public boolean unequal(final Dfp x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compare two instances.
     * @param a first instance in comparison
     * @param b second instance in comparison
     * @return -1 if a<b, 1 if a>b and 0 if a==b
     *  Note this method does not properly handle NaNs or numbers with different precision.
     */
    private static int compare(final Dfp a, final Dfp b) {
        // Ignore the sign of zero
        if (a.mant[a.mant.length - 1] == 0 && b.mant[b.mant.length - 1] == 0 && a.nans == FINITE && b.nans == FINITE) {
            return 0;
        }
        if (a.sign != b.sign) {
            if (a.sign == -1) {
                return -1;
            } else {
                return 1;
            }
        }
        // deal with the infinities
        if (a.nans == INFINITE && b.nans == FINITE) {
            return a.sign;
        }
        if (a.nans == FINITE && b.nans == INFINITE) {
            return -b.sign;
        }
        if (a.nans == INFINITE && b.nans == INFINITE) {
            return 0;
        }
        // Handle special case when a or b is zero, by ignoring the exponents
        if (b.mant[b.mant.length - 1] != 0 && a.mant[b.mant.length - 1] != 0) {
            if (a.exp < b.exp) {
                return -a.sign;
            }
            if (a.exp > b.exp) {
                return a.sign;
            }
        }
        // compare the mantissas
        for (int i = a.mant.length - 1; i >= 0; i--) {
            if (a.mant[i] > b.mant[i]) {
                return a.sign;
            }
            if (a.mant[i] < b.mant[i]) {
                return -a.sign;
            }
        }
        return 0;
    }

    /**
     * Round to nearest integer using the round-half-even method.
     *  That is round to nearest integer unless both are equidistant.
     *  In which case round to the even one.
     *  @return rounded value
     */
    @Override
    public Dfp rint() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Round to an integer using the round floor mode.
     * That is, round toward -Infinity
     *  @return rounded value
     */
    @Override
    public Dfp floor() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Round to an integer using the round ceil mode.
     * That is, round toward +Infinity
     *  @return rounded value
     */
    @Override
    public Dfp ceil() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the IEEE remainder.
     * @param d divisor
     * @return this less n &times; d, where n is the integer closest to this/d
     */
    @Override
    public Dfp remainder(final Dfp d) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Does the integer conversions with the specified rounding.
     * @param rmode rounding mode to use
     * @return truncated value
     */
    protected Dfp trunc(final DfpField.RoundingMode rmode) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convert this to an integer.
     * If greater than 2147483647, it returns 2147483647. If less than -2147483648 it returns -2147483648.
     * @return converted number
     */
    public int intValue() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the exponent of the greatest power of 10000 that is
     *  less than or equal to the absolute value of this.  I.E.  if
     *  this is 10<sup>6</sup> then log10K would return 1.
     *  @return integer base 10000 logarithm
     */
    public int log10K() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the specified  power of 10000.
     * @param e desired power
     * @return 10000<sup>e</sup>
     */
    public Dfp power10K(final int e) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the exponent of the greatest power of 10 that is less than or equal to abs(this).
     *  @return integer base 10 logarithm
     */
    public int intLog10() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Return the specified  power of 10.
     * @param e desired power
     * @return 10<sup>e</sup>
     */
    public Dfp power10(final int e) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Negate the mantissa of this by computing the complement.
     *  Leaves the sign bit unchanged, used internally by add.
     *  Denormalized numbers are handled properly here.
     *  @param extra ???
     *  @return ???
     */
    protected int complement(int extra) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Add x to this.
     * @param x number to add
     * @return sum of this and x
     */
    @Override
    public Dfp add(final Dfp x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns a number that is this number with the sign bit reversed.
     * @return the opposite of this
     */
    @Override
    public Dfp negate() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Subtract x from this.
     * @param x number to subtract
     * @return difference of this and a
     */
    @Override
    public Dfp subtract(final Dfp x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Round this given the next digit n using the current rounding mode.
     * @param n ???
     * @return the IEEE flag if an exception occurred
     */
    protected int round(int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Multiply this by x.
     * @param x multiplicand
     * @return product of this and x
     */
    @Override
    public Dfp multiply(final Dfp x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Multiply this by a single digit x.
     * @param x multiplicand
     * @return product of this and x
     */
    @Override
    public Dfp multiply(final int x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Multiply this by a single digit 0&lt;=x&lt;radix.
     * There are speed advantages in this special case.
     * @param x multiplicand
     * @return product of this and x
     */
    private Dfp multiplyFast(final int x) {
        Dfp result = newInstance(this);
        /* handle special cases */
        if (nans != FINITE) {
            if (isNaN()) {
                return this;
            }
            if (nans == INFINITE && x != 0) {
                result = newInstance(this);
                return result;
            }
            if (nans == INFINITE && x == 0) {
                field.setIEEEFlagsBits(DfpField.FLAG_INVALID);
                result = newInstance(getZero());
                result.nans = QNAN;
                result = dotrap(DfpField.FLAG_INVALID, MULTIPLY_TRAP, newInstance(getZero()), result);
                return result;
            }
        }
        /* range check x */
        if (x < 0 || x >= RADIX) {
            field.setIEEEFlagsBits(DfpField.FLAG_INVALID);
            result = newInstance(getZero());
            result.nans = QNAN;
            result = dotrap(DfpField.FLAG_INVALID, MULTIPLY_TRAP, result, result);
            return result;
        }
        int rh = 0;
        for (int i = 0; i < mant.length; i++) {
            final int r = mant[i] * x + rh;
            rh = r / RADIX;
            result.mant[i] = r - rh * RADIX;
        }
        int lostdigit = 0;
        if (rh != 0) {
            lostdigit = result.mant[0];
            result.shiftRight();
            result.mant[mant.length - 1] = rh;
        }
        if (result.mant[mant.length - 1] == 0) {
            // if result is zero, set exp to zero
            result.exp = 0;
        }
        final int excp = result.round(lostdigit);
        if (excp != 0) {
            result = dotrap(excp, MULTIPLY_TRAP, result, result);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp square() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Divide this by divisor.
     * @param divisor divisor
     * @return quotient of this by divisor
     */
    @Override
    public Dfp divide(Dfp divisor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Divide by a single digit less than radix.
     *  Special case, so there are speed advantages. 0 &lt;= divisor &lt; radix
     * @param divisor divisor
     * @return quotient of this by divisor
     */
    public Dfp divide(int divisor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp reciprocal() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the square root.
     * @return square root of the instance
     */
    @Override
    public Dfp sqrt() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get a string representation of the instance.
     * @return string representation of the instance
     */
    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convert an instance to a string using scientific notation.
     * @return string representation of the instance in scientific notation
     */
    protected String dfp2sci() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convert an instance to a string using normal notation.
     * @return string representation of the instance in normal notation
     */
    protected String dfp2string() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Raises a trap.  This does not set the corresponding flag however.
     *  @param type the trap type
     *  @param what - name of routine trap occurred in
     *  @param oper - input operator to function
     *  @param result - the result computed prior to the trap
     *  @return The suggested return value from the trap handler
     */
    public Dfp dotrap(int type, String what, Dfp oper, Dfp result) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Trap handler.  Subclasses may override this to provide trap
     *  functionality per IEEE 854-1987.
     *
     *  @param type  The exception type - e.g. FLAG_OVERFLOW
     *  @param what  The name of the routine we were in e.g. divide()
     *  @param oper  An operand to this function if any
     *  @param def   The default return value if trap not enabled
     *  @param result    The result that is specified to be delivered per
     *                   IEEE 854, if any
     *  @return the value that should be return by the operation triggering the trap
     */
    protected Dfp trap(int type, String what, Dfp oper, Dfp def, Dfp result) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the type - one of FINITE, INFINITE, SNAN, QNAN.
     * @return type of the number
     */
    public int classify() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Creates an instance that is the same as x except that it has the sign of y.
     * abs(x) = dfp.copysign(x, dfp.one)
     * @param x number to get the value from
     * @param y number to get the sign from
     * @return a number with the value of x and the sign of y
     */
    public static Dfp copysign(final Dfp x, final Dfp y) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the next number greater than this one in the direction of x.
     * If this==x then simply returns this.
     * @param x direction where to look at
     * @return closest number next to instance in the direction of x
     */
    public Dfp nextAfter(final Dfp x) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convert the instance into a double.
     * @return a double approximating the instance
     * @see #toSplitDouble()
     */
    public double toDouble() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convert the instance into a split double.
     * @return an array of two doubles which sum represent the instance
     * @see #toDouble()
     */
    public double[] toSplitDouble() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getReal() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp getAddendum() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp remainder(final double a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp sign() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp copySign(final Dfp s) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp copySign(final double s) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getExponent() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp scalb(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp ulp() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp hypot(final Dfp y) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp rootN(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp pow(final double p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp pow(final int n) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp pow(final Dfp e) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp exp() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp expm1() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp log() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp log1p() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp log10() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp cos() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp sin() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp tan() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp acos() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp asin() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp atan() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp atan2(final Dfp x) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp cosh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp sinh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldSinhCosh<Dfp> sinhCosh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp tanh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp acosh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp asinh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp atanh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp toDegrees() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp toRadians() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp linearCombination(final Dfp[] a, final Dfp[] b) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp linearCombination(final double[] a, final Dfp[] b) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp linearCombination(final Dfp a1, final Dfp b1, final Dfp a2, final Dfp b2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp linearCombination(final double a1, final Dfp b1, final double a2, final Dfp b2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp linearCombination(final Dfp a1, final Dfp b1, final Dfp a2, final Dfp b2, final Dfp a3, final Dfp b3) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp linearCombination(final double a1, final Dfp b1, final double a2, final Dfp b2, final double a3, final Dfp b3) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp linearCombination(final Dfp a1, final Dfp b1, final Dfp a2, final Dfp b2, final Dfp a3, final Dfp b3, final Dfp a4, final Dfp b4) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp linearCombination(final double a1, final Dfp b1, final double a2, final Dfp b2, final double a3, final Dfp b3, final double a4, final Dfp b4) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dfp getPi() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
