/*
 * Copyright (c) 1996, 2005, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package java.io;

import java.util.Arrays;

/** {@collect.stats}
 * {@description.open}
 * This class implements a character buffer that can be used as an Writer.
 * The buffer automatically grows when data is written to the stream.  The data
 * can be retrieved using toCharArray() and toString().
 * {@description.close}
 * {@property.open runtime formal:java.io.Closeable_MeaninglessClose}
 * <P>
 * Note: Invoking close() on this class has no effect, and methods
 * of this class can be called after the stream has closed
 * without generating an IOException.
 * {@property.close}
 *
 * @author      Herb Jellinek
 * @since       JDK1.1
 */
public
class CharArrayWriter extends Writer {
    /** {@collect.stats}
     * {@description.open}
     * The buffer where data is stored.
     * {@description.close}
     */
    protected char buf[];

    /** {@collect.stats}
     * {@description.open}
     * The number of chars in the buffer.
     * {@description.close}
     */
    protected int count;

    /** {@collect.stats}
     * {@description.open}
     * Creates a new CharArrayWriter.
     * {@description.close}
     */
    public CharArrayWriter() {
        this(32);
    }

    /** {@collect.stats}
     * {@description.open}
     * Creates a new CharArrayWriter with the specified initial size.
     * {@description.close}
     *
     * @param initialSize  an int specifying the initial buffer size.
     * @exception IllegalArgumentException if initialSize is negative
     */
    public CharArrayWriter(int initialSize) {
        if (initialSize < 0) {
            throw new IllegalArgumentException("Negative initial size: "
                                               + initialSize);
        }
        buf = new char[initialSize];
    }

    /** {@collect.stats}
     * {@description.open}
     * Writes a character to the buffer.
     * {@description.close}
     */
    public void write(int c) {
        synchronized (lock) {
            int newcount = count + 1;
            if (newcount > buf.length) {
                buf = Arrays.copyOf(buf, Math.max(buf.length << 1, newcount));
            }
            buf[count] = (char)c;
            count = newcount;
        }
    }

    /** {@collect.stats}
     * {@description.open}
     * Writes characters to the buffer.
     * {@description.close}
     * @param c the data to be written
     * @param off       the start offset in the data
     * @param len       the number of chars that are written
     */
    public void write(char c[], int off, int len) {
        if ((off < 0) || (off > c.length) || (len < 0) ||
            ((off + len) > c.length) || ((off + len) < 0)) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return;
        }
        synchronized (lock) {
            int newcount = count + len;
            if (newcount > buf.length) {
                buf = Arrays.copyOf(buf, Math.max(buf.length << 1, newcount));
            }
            System.arraycopy(c, off, buf, count, len);
            count = newcount;
        }
    }

    /** {@collect.stats}
     * {@description.open}
     * Write a portion of a string to the buffer.
     * {@description.close}
     * @param  str  String to be written from
     * @param  off  Offset from which to start reading characters
     * @param  len  Number of characters to be written
     */
    public void write(String str, int off, int len) {
        synchronized (lock) {
            int newcount = count + len;
            if (newcount > buf.length) {
                buf = Arrays.copyOf(buf, Math.max(buf.length << 1, newcount));
            }
            str.getChars(off, off + len, buf, count);
            count = newcount;
        }
    }

    /** {@collect.stats}
     * {@description.open}
     * Writes the contents of the buffer to another character stream.
     * {@description.close}
     *
     * @param out       the output stream to write to
     * @throws IOException If an I/O error occurs.
     */
    public void writeTo(Writer out) throws IOException {
        synchronized (lock) {
            out.write(buf, 0, count);
        }
    }

    /** {@collect.stats}
     * {@description.open}
     * Appends the specified character sequence to this writer.
     *
     * <p> An invocation of this method of the form <tt>out.append(csq)</tt>
     * behaves in exactly the same way as the invocation
     *
     * <pre>
     *     out.write(csq.toString()) </pre>
     *
     * <p> Depending on the specification of <tt>toString</tt> for the
     * character sequence <tt>csq</tt>, the entire sequence may not be
     * appended. For instance, invoking the <tt>toString</tt> method of a
     * character buffer will return a subsequence whose content depends upon
     * the buffer's position and limit.
     * {@description.close}
     *
     * @param  csq
     *         The character sequence to append.  If <tt>csq</tt> is
     *         <tt>null</tt>, then the four characters <tt>"null"</tt> are
     *         appended to this writer.
     *
     * @return  This writer
     *
     * @since  1.5
     */
    public CharArrayWriter append(CharSequence csq) {
        String s = (csq == null ? "null" : csq.toString());
        write(s, 0, s.length());
        return this;
    }

    /** {@collect.stats}
     * {@description.open}
     * Appends a subsequence of the specified character sequence to this writer.
     *
     * <p> An invocation of this method of the form <tt>out.append(csq, start,
     * end)</tt> when <tt>csq</tt> is not <tt>null</tt>, behaves in
     * exactly the same way as the invocation
     *
     * <pre>
     *     out.write(csq.subSequence(start, end).toString()) </pre>
     * {@description.close}
     *
     * @param  csq
     *         The character sequence from which a subsequence will be
     *         appended.  If <tt>csq</tt> is <tt>null</tt>, then characters
     *         will be appended as if <tt>csq</tt> contained the four
     *         characters <tt>"null"</tt>.
     *
     * @param  start
     *         The index of the first character in the subsequence
     *
     * @param  end
     *         The index of the character following the last character in the
     *         subsequence
     *
     * @return  This writer
     *
     * @throws  IndexOutOfBoundsException
     *          If <tt>start</tt> or <tt>end</tt> are negative, <tt>start</tt>
     *          is greater than <tt>end</tt>, or <tt>end</tt> is greater than
     *          <tt>csq.length()</tt>
     *
     * @since  1.5
     */
    public CharArrayWriter append(CharSequence csq, int start, int end) {
        String s = (csq == null ? "null" : csq).subSequence(start, end).toString();
        write(s, 0, s.length());
        return this;
    }

    /** {@collect.stats}
     * {@description.open}
     * Appends the specified character to this writer.
     *
     * <p> An invocation of this method of the form <tt>out.append(c)</tt>
     * behaves in exactly the same way as the invocation
     *
     * <pre>
     *     out.write(c) </pre>
     * {@description.close}
     *
     * @param  c
     *         The 16-bit character to append
     *
     * @return  This writer
     *
     * @since 1.5
     */
    public CharArrayWriter append(char c) {
        write(c);
        return this;
    }

    /** {@collect.stats}
     * {@description.open}
     * Resets the buffer so that you can use it again without
     * throwing away the already allocated buffer.
     * {@description.close}
     */
    public void reset() {
        count = 0;
    }

    /** {@collect.stats}
     * {@description.open}
     * Returns a copy of the input data.
     * {@description.close}
     *
     * @return an array of chars copied from the input data.
     */
    public char toCharArray()[] {
        synchronized (lock) {
            return Arrays.copyOf(buf, count);
        }
    }

    /** {@collect.stats}
     * {@description.open}
     * Returns the current size of the buffer.
     * {@description.close}
     *
     * @return an int representing the current size of the buffer.
     */
    public int size() {
        return count;
    }

    /** {@collect.stats}
     * {@description.open}
     * Converts input data to a string.
     * {@description.close}
     * @return the string.
     */
    public String toString() {
        synchronized (lock) {
            return new String(buf, 0, count);
        }
    }

    /** {@collect.stats}
     * {@description.open}
     * Flush the stream.
     * {@description.close}
     */
    public void flush() { }

    /** {@collect.stats}
     * {@description.open}
     * Close the stream.
     * {@description.close}
     * {@property.open runtime formal:java.io.Closeable_MeaninglessClose}
     * This method does not release the buffer, since its
     * contents might still be required. Note: Invoking this method in this class
     * will have no effect.
     * {@property.close}
     */
    public void close() { }

}
