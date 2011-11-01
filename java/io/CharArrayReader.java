/*
 * Copyright (c) 1996, 2005, Oracle and/or its affiliates. All rights reserved.
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

package java.io;

/** {@collect.stats}
 * {@description.open}
 * This class implements a character buffer that can be used as a
 * character-input stream.
 * {@description.close}
 *
 * @author      Herb Jellinek
 * @since       JDK1.1
 */
public class CharArrayReader extends Reader {
    /** {@collect.stats}
     * {@description.open}
     * The character buffer.
     * {@description.close}
     */
    protected char buf[];

    /** {@collect.stats}
     * {@description.open}
     * The current buffer position.
     * {@description.close}
     */
    protected int pos;

    /** {@collect.stats}
     * {@description.open}
     * The position of mark in buffer.
     * {@description.close}
     */
    protected int markedPos = 0;

    /** {@collect.stats}
     * {@description.open}
     *  The index of the end of this buffer.  There is not valid
     *  data at or beyond this index.
     * {@description.close}
     */
    protected int count;

    /** {@collect.stats}
     * {@description.open}
     * Creates a CharArrayReader from the specified array of chars.
     * {@description.close}
     * @param buf       Input buffer (not copied)
     */
    public CharArrayReader(char buf[]) {
        this.buf = buf;
        this.pos = 0;
        this.count = buf.length;
    }

    /** {@collect.stats}
     * {@description.open}
     * Creates a CharArrayReader from the specified array of chars.
     *
     * <p> The resulting reader will start reading at the given
     * <tt>offset</tt>.  The total number of <tt>char</tt> values that can be
     * read from this reader will be either <tt>length</tt> or
     * <tt>buf.length-offset</tt>, whichever is smaller.
     * {@description.close}
     *
     * @throws IllegalArgumentException
     *         If <tt>offset</tt> is negative or greater than
     *         <tt>buf.length</tt>, or if <tt>length</tt> is negative, or if
     *         the sum of these two values is negative.
     *
     * @param buf       Input buffer (not copied)
     * @param offset    Offset of the first char to read
     * @param length    Number of chars to read
     */
    public CharArrayReader(char buf[], int offset, int length) {
        if ((offset < 0) || (offset > buf.length) || (length < 0) ||
            ((offset + length) < 0)) {
            throw new IllegalArgumentException();
        }
        this.buf = buf;
        this.pos = offset;
        this.count = Math.min(offset + length, buf.length);
        this.markedPos = offset;
    }

    /** {@collect.stats}
     * {@description.open}
     * Checks to make sure that the stream has not been closed
     * {@description.close}
     */
    private void ensureOpen() throws IOException {
        if (buf == null)
            throw new IOException("Stream closed");
    }

    /** {@collect.stats}
     * {@description.open}
     * Reads a single character.
     * {@description.close}
     *
     * @exception   IOException  If an I/O error occurs
     */
    public int read() throws IOException {
        synchronized (lock) {
            ensureOpen();
            if (pos >= count)
                return -1;
            else
                return buf[pos++];
        }
    }

    /** {@collect.stats}
     * {@description.open}
     * Reads characters into a portion of an array.
     * {@description.close}
     * @param b  Destination buffer
     * @param off  Offset at which to start storing characters
     * @param len   Maximum number of characters to read
     * @return  The actual number of characters read, or -1 if
     *          the end of the stream has been reached
     *
     * @exception   IOException  If an I/O error occurs
     */
    public int read(char b[], int off, int len) throws IOException {
        synchronized (lock) {
            ensureOpen();
            if ((off < 0) || (off > b.length) || (len < 0) ||
                ((off + len) > b.length) || ((off + len) < 0)) {
                throw new IndexOutOfBoundsException();
            } else if (len == 0) {
                return 0;
            }

            if (pos >= count) {
                return -1;
            }
            if (pos + len > count) {
                len = count - pos;
            }
            if (len <= 0) {
                return 0;
            }
            System.arraycopy(buf, pos, b, off, len);
            pos += len;
            return len;
        }
    }

    /** {@collect.stats}
     * {@description.open}
     * Skips characters.  Returns the number of characters that were skipped.
     *
     * <p>The <code>n</code> parameter may be negative, even though the
     * <code>skip</code> method of the {@link Reader} superclass throws
     * an exception in this case. If <code>n</code> is negative, then
     * this method does nothing and returns <code>0</code>.
     * {@description.close}
     *
     * @param n The number of characters to skip
     * @return       The number of characters actually skipped
     * @exception  IOException If the stream is closed, or an I/O error occurs
     */
    public long skip(long n) throws IOException {
        synchronized (lock) {
            ensureOpen();
            if (pos + n > count) {
                n = count - pos;
            }
            if (n < 0) {
                return 0;
            }
            pos += n;
            return n;
        }
    }

    /** {@collect.stats}
     * {@description.open}
     * Tells whether this stream is ready to be read. Character-array readers
     * are always ready to be read.
     * {@description.close}
     *
     * @exception  IOException  If an I/O error occurs
     */
    public boolean ready() throws IOException {
        synchronized (lock) {
            ensureOpen();
            return (count - pos) > 0;
        }
    }

    /** {@collect.stats}
     * {@property.open Property:java.io.Reader_MarkReset}
     * Tells whether this stream supports the mark() operation, which it does.
     * {@property.close}
     */
    public boolean markSupported() {
        return true;
    }

    /** {@collect.stats}
     * {@description.open}
     * Marks the present position in the stream.  Subsequent calls to reset()
     * will reposition the stream to this point.
     * {@description.close}
     *
     * @param  readAheadLimit  Limit on the number of characters that may be
     *                         read while still preserving the mark.  Because
     *                         the stream's input comes from a character array,
     *                         there is no actual limit; hence this argument is
     *                         ignored.
     *
     * @exception  IOException  If an I/O error occurs
     */
    public void mark(int readAheadLimit) throws IOException {
        synchronized (lock) {
            ensureOpen();
            markedPos = pos;
        }
    }

    /** {@collect.stats}
     * {@property.open Property:java.io.Reader_UnmarkedReset}
     * Resets the stream to the most recent mark, or to the beginning if it has
     * never been marked.
     * {@property.close}
     *
     * @exception  IOException  If an I/O error occurs
     */
    public void reset() throws IOException {
        synchronized (lock) {
            ensureOpen();
            pos = markedPos;
        }
    }

    /** {@collect.stats}
     * {@description.open}
     * Closes the stream and releases any system resources associated with
     * it.
     * {@description.close}
     * {@property.open Property:java.io.Reader_ManipulateAfterClose}
     * Once the stream has been closed, further read(), ready(),
     * mark(), reset(), or skip() invocations will throw an IOException.
     * {@property.close}
     * {@property.open Property:java.io.Closeable_MultipleClose}
     * Closing a previously closed stream has no effect.
     * {@property.close}
     */
    public void close() {
        buf = null;
    }
}
