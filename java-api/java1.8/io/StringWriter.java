/*
 * Copyright (c) 1996, 2004, Oracle and/or its affiliates. All rights reserved.
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


/** {@collect.stats}
 * {@description.open}
 * A character stream that collects its output in a string buffer, which can
 * then be used to construct a string.
 * <p>
 * {@description.close}
 * {@property.open runtime formal:java.io.Closeable_MeaninglessClose}
 * Closing a <tt>StringWriter</tt> has no effect. The methods in this class
 * can be called after the stream has been closed without generating an
 * <tt>IOException</tt>.
 * {@property.close}
 *
 * @author      Mark Reinhold
 * @since       JDK1.1
 */

public class StringWriter extends Writer {

    private StringBuffer buf;

    /** {@collect.stats}
     * {@description.open}
     * Create a new string writer using the default initial string-buffer
     * size.
     * {@description.close}
     */
    public StringWriter() {
        buf = new StringBuffer();
        lock = buf;
    }

    /** {@collect.stats}
     * {@description.open}
     * Create a new string writer using the specified initial string-buffer
     * size.
     * {@description.close}
     *
     * @param initialSize
     *        The number of <tt>char</tt> values that will fit into this buffer
     *        before it is automatically expanded
     *
     * @throws IllegalArgumentException
     *         If <tt>initialSize</tt> is negative
     */
    public StringWriter(int initialSize) {
        if (initialSize < 0) {
            throw new IllegalArgumentException("Negative buffer size");
        }
        buf = new StringBuffer(initialSize);
        lock = buf;
    }

    /** {@collect.stats}
     * {@description.open}
     * Write a single character.
     * {@description.close}
     */
    public void write(int c) {
        buf.append((char) c);
    }

    /** {@collect.stats}
     * {@description.open}
     * Write a portion of an array of characters.
     * {@description.close}
     *
     * @param  cbuf  Array of characters
     * @param  off   Offset from which to start writing characters
     * @param  len   Number of characters to write
     */
    public void write(char cbuf[], int off, int len) {
        if ((off < 0) || (off > cbuf.length) || (len < 0) ||
            ((off + len) > cbuf.length) || ((off + len) < 0)) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return;
        }
        buf.append(cbuf, off, len);
    }

    /** {@collect.stats}
     * {@description.open}
     * Write a string.
     * {@description.close}
     */
    public void write(String str) {
        buf.append(str);
    }

    /** {@collect.stats}
     * {@description.open}
     * Write a portion of a string.
     * {@description.close}
     *
     * @param  str  String to be written
     * @param  off  Offset from which to start writing characters
     * @param  len  Number of characters to write
     */
    public void write(String str, int off, int len)  {
        buf.append(str.substring(off, off + len));
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
    public StringWriter append(CharSequence csq) {
        if (csq == null)
            write("null");
        else
            write(csq.toString());
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
    public StringWriter append(CharSequence csq, int start, int end) {
        CharSequence cs = (csq == null ? "null" : csq);
        write(cs.subSequence(start, end).toString());
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
    public StringWriter append(char c) {
        write(c);
        return this;
    }

    /** {@collect.stats}
     * {@description.open}
     * Return the buffer's current value as a string.
     * {@description.close}
     */
    public String toString() {
        return buf.toString();
    }

    /** {@collect.stats}
     * {@description.open}
     * Return the string buffer itself.
     * {@description.close}
     *
     * @return StringBuffer holding the current buffer value.
     */
    public StringBuffer getBuffer() {
        return buf;
    }

    /** {@collect.stats}
     * {@description.open}
     * Flush the stream.
     * {@description.close}
     */
    public void flush() {
    }

    /** {@collect.stats}
     * {@property.open runtime formal:java.io.Closeable_MeaninglessClose}
     * Closing a <tt>StringWriter</tt> has no effect. The methods in this
     * class can be called after the stream has been closed without generating
     * an <tt>IOException</tt>.
     * {@property.close}
     */
    public void close() throws IOException {
    }

}
