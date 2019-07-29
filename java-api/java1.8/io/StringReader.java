/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * A character stream whose source is a string.
 * {@description.close}
 *
 * @author      Mark Reinhold
 * @since       JDK1.1
 */

public class StringReader extends Reader {

    private String str;
    private int length;
    private int next = 0;
    private int mark = 0;

    /** {@collect.stats}
     * {@description.open}
     * Creates a new string reader.
     * {@description.close}
     *
     * @param s  String providing the character stream.
     */
    public StringReader(String s) {
        this.str = s;
        this.length = s.length();
    }

    /** {@collect.stats}
    *  {@description.open}
    *  Check to make sure that the stream has not been closed
    * {@description.close}
    */
    private void ensureOpen() throws IOException {
        if (str == null)
            throw new IOException("Stream closed");
    }

    /** {@collect.stats}
     * {@description.open}
     * Reads a single character.
     * {@description.close}
     *
     * @return     The character read, or -1 if the end of the stream has been
     *             reached
     *
     * @exception  IOException  If an I/O error occurs
     */
    public int read() throws IOException {
        synchronized (lock) {
            ensureOpen();
            if (next >= length)
                return -1;
            return str.charAt(next++);
        }
    }

    /** {@collect.stats}
     * {@description.open}
     * Reads characters into a portion of an array.
     * {@description.close}
     *
     * @param      cbuf  Destination buffer
     * @param      off   Offset at which to start writing characters
     * @param      len   Maximum number of characters to read
     *
     * @return     The number of characters read, or -1 if the end of the
     *             stream has been reached
     *
     * @exception  IOException  If an I/O error occurs
     */
    public int read(char cbuf[], int off, int len) throws IOException {
        synchronized (lock) {
            ensureOpen();
            if ((off < 0) || (off > cbuf.length) || (len < 0) ||
                ((off + len) > cbuf.length) || ((off + len) < 0)) {
                throw new IndexOutOfBoundsException();
            } else if (len == 0) {
                return 0;
            }
            if (next >= length)
                return -1;
            int n = Math.min(length - next, len);
            str.getChars(next, next + n, cbuf, off);
            next += n;
            return n;
        }
    }

    /** {@collect.stats}
     * {@description.open}
     * Skips the specified number of characters in the stream. Returns
     * the number of characters that were skipped.
     *
     * <p>The <code>ns</code> parameter may be negative, even though the
     * <code>skip</code> method of the {@link Reader} superclass throws
     * an exception in this case. Negative values of <code>ns</code> cause the
     * stream to skip backwards. Negative return values indicate a skip
     * backwards. It is not possible to skip backwards past the beginning of
     * the string.
     *
     * <p>If the entire string has been read or skipped, then this method has
     * no effect and always returns 0.
     * {@description.close}
     *
     * @exception  IOException  If an I/O error occurs
     */
    public long skip(long ns) throws IOException {
        synchronized (lock) {
            ensureOpen();
            if (next >= length)
                return 0;
            // Bound skip by beginning and end of the source
            long n = Math.min(length - next, ns);
            n = Math.max(-next, n);
            next += n;
            return n;
        }
    }

    /** {@collect.stats}
     * {@description.open}
     * Tells whether this stream is ready to be read.
     * {@description.close}
     *
     * @return True if the next read() is guaranteed not to block for input
     *
     * @exception  IOException  If the stream is closed
     */
    public boolean ready() throws IOException {
        synchronized (lock) {
        ensureOpen();
        return true;
        }
    }

    /** {@collect.stats}
     * {@description.open}
     * Tells whether this stream supports the mark() operation, which it does.
     * {@description.close}
     */
    public boolean markSupported() {
        return true;
    }

    /** {@collect.stats}
     * {@description.open}
     * Marks the present position in the stream.
     * {@description.close}
     * {@property.open runtime formal:java.io.Reader_MarkReset}
     * Subsequent calls to reset()
     * will reposition the stream to this point.
     * {@property.close}
     *
     * @param  readAheadLimit  Limit on the number of characters that may be
     *                         read while still preserving the mark.  Because
     *                         the stream's input comes from a string, there
     *                         is no actual limit, so this argument must not
     *                         be negative, but is otherwise ignored.
     *
     * @exception  IllegalArgumentException  If {@code readAheadLimit < 0}
     * @exception  IOException  If an I/O error occurs
     */
    public void mark(int readAheadLimit) throws IOException {
        if (readAheadLimit < 0){
            throw new IllegalArgumentException("Read-ahead limit < 0");
        }
        synchronized (lock) {
            ensureOpen();
            mark = next;
        }
    }

    /** {@collect.stats}
     * {@property.open runtime formal:java.io.Reader_MarkReset formal:java.io.Reader_UnmarkedReset}
     * Resets the stream to the most recent mark, or to the beginning of the
     * string if it has never been marked.
     * {@property.close}
     *
     * @exception  IOException  If an I/O error occurs
     */
    public void reset() throws IOException {
        synchronized (lock) {
            ensureOpen();
            next = mark;
        }
    }

    /** {@collect.stats}
     * {@description.open}
     * Closes the stream and releases any system resources associated with
     * it.
     * {@description.close}
     * {@property.open runtime formal:java.io.Reader_ManipulateAfterClose}
     * Once the stream has been closed, further read(),
     * ready(), mark(), or reset() invocations will throw an IOException.
     * {@property.close}
     * {@property.open runtime formal:java.io.Closeable_MultipleClose}
     * Closing a previously closed stream has no effect.
     * {@property.close}
     */
    public void close() {
        str = null;
    }
}
