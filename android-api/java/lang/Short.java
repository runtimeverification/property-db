/*
 * Copyright (c) 1996, 2006, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package java.lang;

/** {@collect.stats} 
 * {@description.open}
 * The {@code Short} class wraps a value of primitive type {@code
 * short} in an object.  An object of type {@code Short} contains a
 * single field whose type is {@code short}.
 *
 * <p>In addition, this class provides several methods for converting
 * a {@code short} to a {@code String} and a {@code String} to a
 * {@code short}, as well as other constants and methods useful when
 * dealing with a {@code short}.
 * {@description.close}
 *
 * @author  Nakul Saraiya
 * @author  Joseph D. Darcy
 * @see     java.lang.Number
 * @since   JDK1.1
 */
public final class Short extends Number implements Comparable<Short> {

    /** {@collect.stats}
     * {@description.open} 
     * A constant holding the minimum value a {@code short} can
     * have, -2<sup>15</sup>.
     * {@description.close}
     */
    public static final short   MIN_VALUE = -32768;

    /** {@collect.stats} 
     * {@description.open}
     * A constant holding the maximum value a {@code short} can
     * have, 2<sup>15</sup>-1.
     * {@description.close}
     */
    public static final short   MAX_VALUE = 32767;

    /** {@collect.stats} 
     * {@description.open}
     * The {@code Class} instance representing the primitive type
     * {@code short}.
     * {@description.close}
     */
    public static final Class<Short>    TYPE = (Class<Short>) Class.getPrimitiveClass("short");

    /** {@collect.stats}
     * {@description.open} 
     * Returns a new {@code String} object representing the
     * specified {@code short}. The radix is assumed to be 10.
     * {@description.close}
     *
     * @param s the {@code short} to be converted
     * @return the string representation of the specified {@code short}
     * @see java.lang.Integer#toString(int)
     */
    public static String toString(short s) {
        return Integer.toString((int)s, 10);
    }

    /** {@collect.stats} 
     * {@description.open}
     * Parses the string argument as a signed {@code short} in the
     * radix specified by the second argument. 
     * {@description.close}
     * {@property.open runtime formal:java.lang.Short_BadParsingArgs}
     * The characters in the
     * string must all be digits, of the specified radix (as
     * determined by whether {@link java.lang.Character#digit(char,
     * int)} returns a nonnegative value) except that the first
     * character may be an ASCII minus sign {@code '-'}
     * (<code>'&#92;u002D'</code>) to indicate a negative value. 
     * {@property.close}
     * {@description.open}
     * The resulting {@code short} value is returned.
     * {@description.close}
     *
     * {@property.open runtime formal:java.lang.Short_BadParsingArgs}
     * <p>An exception of type {@code NumberFormatException} is
     * thrown if any of the following situations occurs:
     * <ul>
     * <li> The first argument is {@code null} or is a string of
     * length zero.
     *
     * <li> The radix is either smaller than {@link
     * java.lang.Character#MIN_RADIX} or larger than {@link
     * java.lang.Character#MAX_RADIX}.
     *
     * <li> Any character of the string is not a digit of the
     * specified radix, except that the first character may be a minus
     * sign {@code '-'} (<code>'&#92;u002D'</code>) provided that the
     * string is longer than length 1.
     *
     * <li> The value represented by the string is not a value of type
     * {@code short}.
     * </ul>
     * {@property.close}
     *
     * @param s         the {@code String} containing the
     *                  {@code short} representation to be parsed
     * @param radix     the radix to be used while parsing {@code s}
     * @return          the {@code short} represented by the string
     *                  argument in the specified radix.
     * @throws          NumberFormatException If the {@code String}
     *                  does not contain a parsable {@code short}.
     */
    public static short parseShort(String s, int radix)
        throws NumberFormatException {
        int i = Integer.parseInt(s, radix);
        if (i < MIN_VALUE || i > MAX_VALUE)
            throw new NumberFormatException(
                "Value out of range. Value:\"" + s + "\" Radix:" + radix);
        return (short)i;
    }

    /** {@collect.stats}
     * {@description.open} 
     * Parses the string argument as a signed decimal {@code
     * short}.
     * {@description.close}
     * {@property.open runtime formal:java.lang.Short_BadParsingArgs}
     * The characters in the string must all be decimal
     * digits, except that the first character may be an ASCII minus
     * sign {@code '-'} (<code>'&#92;u002D'</code>) to indicate a
     * negative value.
     * {@property.close}
     * {@description.open}
     * The resulting {@code short} value is returned,
     * exactly as if the argument and the radix 10 were given as
     * arguments to the {@link #parseShort(java.lang.String, int)}
     * method.
     * {@description.close}
     *
     * @param s a {@code String} containing the {@code short}
     *          representation to be parsed
     * @return  the {@code short} value represented by the
     *          argument in decimal.
     * @throws  NumberFormatException If the string does not
     *          contain a parsable {@code short}.
     */
    public static short parseShort(String s) throws NumberFormatException {
        return parseShort(s, 10);
    }

    /** {@collect.stats}
     * {@description.open} 
     * Returns a {@code Short} object holding the value
     * extracted from the specified {@code String} when parsed
     * with the radix given by the second argument. The first argument
     * is interpreted as representing a signed {@code short} in
     * the radix specified by the second argument, exactly as if the
     * argument were given to the {@link #parseShort(java.lang.String,
     * int)} method. The result is a {@code Short} object that
     * represents the {@code short} value specified by the string.
     *
     * <p>In other words, this method returns a {@code Short} object
     * equal to the value of:
     *
     * <blockquote>
     *  {@code new Short(Short.parseShort(s, radix))}
     * </blockquote>
     * {@description.close}
     *
     * @param s         the string to be parsed
     * @param radix     the radix to be used in interpreting {@code s}
     * @return          a {@code Short} object holding the value
     *                  represented by the string argument in the
     *                  specified radix.
     * @throws          NumberFormatException If the {@code String} does
     *                  not contain a parsable {@code short}.
     */
    public static Short valueOf(String s, int radix)
        throws NumberFormatException {
        return new Short(parseShort(s, radix));
    }

    /** {@collect.stats} 
     * {@description.open}
     * Returns a {@code Short} object holding the
     * value given by the specified {@code String}. The argument
     * is interpreted as representing a signed decimal
     * {@code short}, exactly as if the argument were given to
     * the {@link #parseShort(java.lang.String)} method. The result is
     * a {@code Short} object that represents the
     * {@code short} value specified by the string.
     *
     * <p>In other words, this method returns a {@code Short} object
     * equal to the value of:
     *
     * <blockquote>
     *  {@code new Short(Short.parseShort(s))}
     * </blockquote>
     * {@description.close}
     *
     * @param s the string to be parsed
     * @return  a {@code Short} object holding the value
     *          represented by the string argument
     * @throws  NumberFormatException If the {@code String} does
     *          not contain a parsable {@code short}.
     */
    public static Short valueOf(String s) throws NumberFormatException {
        return valueOf(s, 10);
    }

    private static class ShortCache {
        private ShortCache(){}

        static final Short cache[] = new Short[-(-128) + 127 + 1];

        static {
            for(int i = 0; i < cache.length; i++)
                cache[i] = new Short((short)(i - 128));
        }
    }

    /** {@collect.stats} 
     * {@description.open}
     * Returns a {@code Short} instance representing the specified
     * {@code short} value.
     * {@description.close}
     * {@property.open runtime performance formal:java.lang.Short_StaticFactory}
     * If a new {@code Short} instance is not required, this method
     * should generally be used in preference to the constructor
     * {@link #Short(short)}, as this method is likely to yield
     * significantly better space and time performance by caching
     * frequently requested values.
     * {@property.close}
     *
     * @param  s a short value.
     * @return a {@code Short} instance representing {@code s}.
     * @since  1.5
     */
    public static Short valueOf(short s) {
        final int offset = 128;
        int sAsInt = s;
        if (sAsInt >= -128 && sAsInt <= 127) { // must cache
            return ShortCache.cache[sAsInt + offset];
        }
        return new Short(s);
    }

    /** {@collect.stats}
     * {@description.open} 
     * Decodes a {@code String} into a {@code Short}.
     * Accepts decimal, hexadecimal, and octal numbers given by
     * the following grammar:
     *
     * <blockquote>
     * <dl>
     * <dt><i>DecodableString:</i>
     * <dd><i>Sign<sub>opt</sub> DecimalNumeral</i>
     * <dd><i>Sign<sub>opt</sub></i> {@code 0x} <i>HexDigits</i>
     * <dd><i>Sign<sub>opt</sub></i> {@code 0X} <i>HexDigits</i>
     * <dd><i>Sign<sub>opt</sub></i> {@code #} <i>HexDigits</i>
     * <dd><i>Sign<sub>opt</sub></i> {@code 0} <i>OctalDigits</i>
     * <p>
     * <dt><i>Sign:</i>
     * <dd>{@code -}
     * </dl>
     * </blockquote>
     *
     * <i>DecimalNumeral</i>, <i>HexDigits</i>, and <i>OctalDigits</i>
     * are defined in <a href="http://java.sun.com/docs/books/jls/second_edition/html/lexical.doc.html#48282">&sect;3.10.1</a>
     * of the <a href="http://java.sun.com/docs/books/jls/html/">Java
     * Language Specification</a>.
     *
     * <p>The sequence of characters following an (optional) negative
     * sign and/or radix specifier ("{@code 0x}", "{@code 0X}",
     * "{@code #}", or leading zero) is parsed as by the {@code
     * Short.parseShort} method with the indicated radix (10, 16, or
     * 8).
     * {@description.close}
     * {@property.open runtime formal:java.lang.Short_BadDecodeArg}
     * This sequence of characters must represent a positive
     * value or a {@link NumberFormatException} will be thrown.
     * {@property.close}
     * {@description.open}
     * The
     * result is negated if first character of the specified {@code
     * String} is the minus sign.
     * {@description.close}
     * {@property.open runtime formal:java.lang.Short_BadDecodeArg}
     * No whitespace characters are
     * permitted in the {@code String}.
     * {@property.close}
     *
     * @param     nm the {@code String} to decode.
     * @return    a {@code Short} object holding the {@code short}
     *            value represented by {@code nm}
     * @throws    NumberFormatException  if the {@code String} does not
     *            contain a parsable {@code short}.
     * @see java.lang.Short#parseShort(java.lang.String, int)
     */
    public static Short decode(String nm) throws NumberFormatException {
        int i = Integer.decode(nm);
        if (i < MIN_VALUE || i > MAX_VALUE)
            throw new NumberFormatException(
                    "Value " + i + " out of range from input " + nm);
        return (short)i;
    }

    /** {@collect.stats}
     * {@description.open} 
     * The value of the {@code Short}.
     * {@description.close}
     *
     * @serial
     */
    private final short value;

    /** {@collect.stats} 
     * {@description.open}
     * Constructs a newly allocated {@code Short} object that
     * represents the specified {@code short} value.
     * {@description.close}
     *
     * @param value     the value to be represented by the
     *                  {@code Short}.
     */
    public Short(short value) {
        this.value = value;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Constructs a newly allocated {@code Short} object that
     * represents the {@code short} value indicated by the
     * {@code String} parameter. The string is converted to a
     * {@code short} value in exactly the manner used by the
     * {@code parseShort} method for radix 10.
     * {@description.close}
     *
     * @param s the {@code String} to be converted to a
     *          {@code Short}
     * @throws  NumberFormatException If the {@code String}
     *          does not contain a parsable {@code short}.
     * @see     java.lang.Short#parseShort(java.lang.String, int)
     */
    public Short(String s) throws NumberFormatException {
        this.value = parseShort(s, 10);
    }

    /** {@collect.stats} 
     * {@description.open}
     * Returns the value of this {@code Short} as a
     * {@code byte}.
     * {@description.close}
     */
    public byte byteValue() {
        return (byte)value;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Returns the value of this {@code Short} as a
     * {@code short}.
     * {@description.close}
     */
    public short shortValue() {
        return value;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Returns the value of this {@code Short} as an
     * {@code int}.
     * {@description.close}
     */
    public int intValue() {
        return (int)value;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Returns the value of this {@code Short} as a
     * {@code long}.
     * {@description.close}
     */
    public long longValue() {
        return (long)value;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Returns the value of this {@code Short} as a
     * {@code float}.
     * {@description.close}
     */
    public float floatValue() {
        return (float)value;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Returns the value of this {@code Short} as a
     * {@code double}.
     * {@description.close}
     */
    public double doubleValue() {
        return (double)value;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Returns a {@code String} object representing this
     * {@code Short}'s value.  The value is converted to signed
     * decimal representation and returned as a string, exactly as if
     * the {@code short} value were given as an argument to the
     * {@link java.lang.Short#toString(short)} method.
     * {@description.close}
     *
     * @return  a string representation of the value of this object in
     *          base&nbsp;10.
     */
    public String toString() {
        return String.valueOf((int)value);
    }

    /** {@collect.stats} 
     * {@description.open}
     * Returns a hash code for this {@code Short}.
     * {@description.close}
     */
    public int hashCode() {
        return (int)value;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Compares this object to the specified object.  The result is
     * {@code true} if and only if the argument is not
     * {@code null} and is a {@code Short} object that
     * contains the same {@code short} value as this object.
     * {@description.close}
     *
     * @param obj       the object to compare with
     * @return          {@code true} if the objects are the same;
     *                  {@code false} otherwise.
     */
    public boolean equals(Object obj) {
        if (obj instanceof Short) {
            return value == ((Short)obj).shortValue();
        }
        return false;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Compares two {@code Short} objects numerically.
     * {@description.close}
     *
     * @param   anotherShort   the {@code Short} to be compared.
     * @return  the value {@code 0} if this {@code Short} is
     *          equal to the argument {@code Short}; a value less than
     *          {@code 0} if this {@code Short} is numerically less
     *          than the argument {@code Short}; and a value greater than
     *           {@code 0} if this {@code Short} is numerically
     *           greater than the argument {@code Short} (signed
     *           comparison).
     * @since   1.2
     */
    public int compareTo(Short anotherShort) {
        return this.value - anotherShort.value;
    }

    /** {@collect.stats} 
     * {@description.open}
     * The number of bits used to represent a {@code short} value in two's
     * complement binary form.
     * {@description.close}
     * 
     * @since 1.5
     */
    public static final int SIZE = 16;

    /** {@collect.stats}
     * {@description.open} 
     * Returns the value obtained by reversing the order of the bytes in the
     * two's complement representation of the specified {@code short} value.
     * {@description.close}
     *
     * @return the value obtained by reversing (or, equivalently, swapping)
     *     the bytes in the specified {@code short} value.
     * @since 1.5
     */
    public static short reverseBytes(short i) {
        return (short) (((i & 0xFF00) >> 8) | (i << 8));
    }

    /** {@collect.stats}
     * {@description.open}
     * use serialVersionUID from JDK 1.1. for interoperability
     * {@description.close}
     */
    private static final long serialVersionUID = 7515723908773894738L;
}
