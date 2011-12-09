/*
 * Copyright (c) 1994, 2006, Oracle and/or its affiliates. All rights reserved.
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
 * A <code>PushbackInputStream</code> adds
 * functionality to another input stream, namely
 * the  ability to "push back" or "unread"
 * one byte. This is useful in situations where
 * it is  convenient for a fragment of code
 * to read an indefinite number of data bytes
 * that  are delimited by a particular byte
 * value; after reading the terminating byte,
 * the  code fragment can "unread" it, so that
 * the next read operation on the input stream
 * will reread the byte that was pushed back.
 * For example, bytes representing the  characters
 * constituting an identifier might be terminated
 * by a byte representing an  operator character;
 * a method whose job is to read just an identifier
 * can read until it  sees the operator and
 * then push the operator back to be re-read.
 * {@description.close}
 *
 * @author  David Connelly
 * @author  Jonathan Payne
 * @since   JDK1.0
 */
public
class PushbackInputStream extends FilterInputStream {
    /** {@collect.stats}
     * {@description.open}
     * The pushback buffer.
     * {@description.close}
     * @since   JDK1.1
     */
    protected byte[] buf;

    /** {@collect.stats}
     * {@description.open}
     * The position within the pushback buffer from which the next byte will
     * be read.  When the buffer is empty, <code>pos</code> is equal to
     * <code>buf.length</code>; when the buffer is full, <code>pos</code> is
     * equal to zero.
     * {@description.close}
     *
     * @since   JDK1.1
     */
    protected int pos;

    /** {@collect.stats}
     * {@description.open}
     * Check to make sure that this stream has not been closed
     * {@description.close}
     */
    private void ensureOpen() throws IOException {
        if (in == null)
            throw new IOException("Stream closed");
    }

    /** {@collect.stats}
     * {@description.open}
     * Creates a <code>PushbackInputStream</code>
     * with a pushback buffer of the specified <code>size</code>,
     * and saves its  argument, the input stream
     * <code>in</code>, for later use. Initially,
     * there is no pushed-back byte  (the field
     * <code>pushBack</code> is initialized to
     * <code>-1</code>).
     * {@description.close}
     *
     * @param  in    the input stream from which bytes will be read.
     * @param  size  the size of the pushback buffer.
     * @exception IllegalArgumentException if size is <= 0
     * @since  JDK1.1
     */
    public PushbackInputStream(InputStream in, int size) {
        super(in);
        if (size <= 0) {
            throw new IllegalArgumentException("size <= 0");
        }
        this.buf = new byte[size];
        this.pos = size;
    }

    /** {@collect.stats}
     * {@description.open}
     * Creates a <code>PushbackInputStream</code>
     * and saves its  argument, the input stream
     * <code>in</code>, for later use. Initially,
     * there is no pushed-back byte  (the field
     * <code>pushBack</code> is initialized to
     * <code>-1</code>).
     * {@description.close}
     *
     * @param   in   the input stream from which bytes will be read.
     */
    public PushbackInputStream(InputStream in) {
        this(in, 1);
    }

    /** {@collect.stats}
     * {@description.open}
     * Reads the next byte of data from this input stream. The value
     * byte is returned as an <code>int</code> in the range
     * <code>0</code> to <code>255</code>. If no byte is available
     * because the end of the stream has been reached, the value
     * <code>-1</code> is returned.
     * {@description.close}
     * {@property.open blocking}
     * This method blocks until input data
     * is available, the end of the stream is detected, or an exception
     * is thrown.
     * {@property.close}
     *
     * {@description.open}
     * <p> This method returns the most recently pushed-back byte, if there is
     * one, and otherwise calls the <code>read</code> method of its underlying
     * input stream and returns whatever value that method returns.
     * {@description.close}
     *
     * @return     the next byte of data, or <code>-1</code> if the end of the
     *             stream has been reached.
     * @exception  IOException  if this input stream has been closed by
     *             invoking its {@link #close()} method,
     *             or an I/O error occurs.
     * @see        java.io.InputStream#read()
     */
    public int read() throws IOException {
        ensureOpen();
        if (pos < buf.length) {
            return buf[pos++] & 0xff;
        }
        return super.read();
    }

    /** {@collect.stats}
     * {@description.open}
     * Reads up to <code>len</code> bytes of data from this input stream into
     * an array of bytes.  This method first reads any pushed-back bytes; after
     * that, if fewer than <code>len</code> bytes have been read then it
     * reads from the underlying input stream.
     * {@description.close}
     * {@property.open blocking}
     * If <code>len</code> is not zero, the method
     * blocks until at least 1 byte of input is available; otherwise, no
     * bytes are read and <code>0</code> is returned.
     * {@property.close}
     *
     * @param      b     the buffer into which the data is read.
     * @param      off   the start offset in the destination array <code>b</code>
     * @param      len   the maximum number of bytes read.
     * @return     the total number of bytes read into the buffer, or
     *             <code>-1</code> if there is no more data because the end of
     *             the stream has been reached.
     * @exception  NullPointerException If <code>b</code> is <code>null</code>.
     * @exception  IndexOutOfBoundsException If <code>off</code> is negative,
     * <code>len</code> is negative, or <code>len</code> is greater than
     * <code>b.length - off</code>
     * @exception  IOException  if this input stream has been closed by
     *             invoking its {@link #close()} method,
     *             or an I/O error occurs.
     * @see        java.io.InputStream#read(byte[], int, int)
     */
    public int read(byte[] b, int off, int len) throws IOException {
        ensureOpen();
        if (b == null) {
            throw new NullPointerException();
        } else if (off < 0 || len < 0 || len > b.length - off) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return 0;
        }

        int avail = buf.length - pos;
        if (avail > 0) {
            if (len < avail) {
                avail = len;
            }
            System.arraycopy(buf, pos, b, off, avail);
            pos += avail;
            off += avail;
            len -= avail;
        }
        if (len > 0) {
            len = super.read(b, off, len);
            if (len == -1) {
                return avail == 0 ? -1 : avail;
            }
            return avail + len;
        }
        return avail;
    }

    /** {@collect.stats}
     * {@description.open}
     * Pushes back a byte by copying it to the front of the pushback buffer.
     * After this method returns, the next byte to be read will have the value
     * <code>(byte)b</code>.
     * {@description.close}
     * {@property.open runtime formal:java.io.PushbackInputStream_UnreadAheadLimit}
     * {@new.open}
     * If the finite size of the internal pushback buffer is full, unread()
     * raises a runtime exception. The buffer size is specified when a
     * PushbackInputStream object is created.
     * {@new.close}
     * {@property.close}
     *
     * @param      b   the <code>int</code> value whose low-order
     *                  byte is to be pushed back.
     * @exception IOException If there is not enough room in the pushback
     *            buffer for the byte, or this input stream has been closed by
     *            invoking its {@link #close()} method.
     */
    public void unread(int b) throws IOException {
        ensureOpen();
        if (pos == 0) {
            throw new IOException("Push back buffer is full");
        }
        buf[--pos] = (byte)b;
    }

    /** {@collect.stats}
     * {@description.open}
     * Pushes back a portion of an array of bytes by copying it to the front
     * of the pushback buffer. After this method returns, the next byte to be
     * read will have the value <code>b[off]</code>, the byte after that will
     * have the value <code>b[off+1]</code>, and so forth.
     * {@description.close}
     * {@property.open runtime formal:java.io.PushbackInputStream_UnreadAheadLimit}
     * {@new.open}
     * If the finite size of the internal pushback buffer is full, unread()
     * raises a runtime exception. The buffer size is specified when a
     * PushbackInputStream object is created.
     * {@new.close}
     * {@property.close}
     *
     * @param b the byte array to push back.
     * @param off the start offset of the data.
     * @param len the number of bytes to push back.
     * @exception IOException If there is not enough room in the pushback
     *            buffer for the specified number of bytes,
     *            or this input stream has been closed by
     *            invoking its {@link #close()} method.
     * @since     JDK1.1
     */
    public void unread(byte[] b, int off, int len) throws IOException {
        ensureOpen();
        if (len > pos) {
            throw new IOException("Push back buffer is full");
        }
        pos -= len;
        System.arraycopy(b, off, buf, pos, len);
    }

    /** {@collect.stats}
     * {@description.open}
     * Pushes back an array of bytes by copying it to the front of the
     * pushback buffer. After this method returns, the next byte to be read
     * will have the value <code>b[0]</code>, the byte after that will have the
     * value <code>b[1]</code>, and so forth.
     * {@description.close}
     * {@property.open runtime formal:java.io.PushbackInputStream_UnreadAheadLimit}
     * {@new.open}
     * If the finite size of the internal pushback buffer is full, unread()
     * raises a runtime exception. The buffer size is specified when a
     * PushbackInputStream object is created.
     * {@new.close}
     * {@property.close}
     *
     * @param b the byte array to push back
     * @exception IOException If there is not enough room in the pushback
     *            buffer for the specified number of bytes,
     *            or this input stream has been closed by
     *            invoking its {@link #close()} method.
     * @since     JDK1.1
     */
    public void unread(byte[] b) throws IOException {
        unread(b, 0, b.length);
    }

    /** {@collect.stats}
     * {@description.open}
     * Returns an estimate of the number of bytes that can be read (or
     * skipped over) from this input stream without blocking by the next
     * invocation of a method for this input stream. The next invocation might be
     * the same thread or another thread.  A single read or skip of this
     * many bytes will not block, but may read or skip fewer bytes.
     *
     * <p> The method returns the sum of the number of bytes that have been
     * pushed back and the value returned by {@link
     * java.io.FilterInputStream#available available}.
     * {@description.close}
     *
     * @return     the number of bytes that can be read (or skipped over) from
     *             the input stream without blocking.
     * @exception  IOException  if this input stream has been closed by
     *             invoking its {@link #close()} method,
     *             or an I/O error occurs.
     * @see        java.io.FilterInputStream#in
     * @see        java.io.InputStream#available()
     */
    public int available() throws IOException {
        ensureOpen();
        return (buf.length - pos) + super.available();
    }

    /** {@collect.stats}
     * {@description.open}
     * Skips over and discards <code>n</code> bytes of data from this
     * input stream. The <code>skip</code> method may, for a variety of
     * reasons, end up skipping over some smaller number of bytes,
     * possibly zero.  If <code>n</code> is negative, no bytes are skipped.
     *
     * <p> The <code>skip</code> method of <code>PushbackInputStream</code>
     * first skips over the bytes in the pushback buffer, if any.  It then
     * calls the <code>skip</code> method of the underlying input stream if
     * more bytes need to be skipped.  The actual number of bytes skipped
     * is returned.
     * {@description.close}
     *
     * @param      n  {@inheritDoc}
     * @return     {@inheritDoc}
     * @exception  IOException  if the stream does not support seek,
     *            or the stream has been closed by
     *            invoking its {@link #close()} method,
     *            or an I/O error occurs.
     * @see        java.io.FilterInputStream#in
     * @see        java.io.InputStream#skip(long n)
     * @since      1.2
     */
    public long skip(long n) throws IOException {
        ensureOpen();
        if (n <= 0) {
            return 0;
        }

        long pskip = buf.length - pos;
        if (pskip > 0) {
            if (n < pskip) {
                pskip = n;
            }
            pos += pskip;
            n -= pskip;
        }
        if (n > 0) {
            pskip += super.skip(n);
        }
        return pskip;
    }

    /** {@collect.stats}
     * {@description.open}
     * Tests if this input stream supports the <code>mark</code> and
     * <code>reset</code> methods, which it does not.
     * {@description.close}
     *
     * @return   <code>false</code>, since this class does not support the
     *           <code>mark</code> and <code>reset</code> methods.
     * @see     java.io.InputStream#mark(int)
     * @see     java.io.InputStream#reset()
     */
    public boolean markSupported() {
        return false;
    }

    /** {@collect.stats}
     * {@description.open}
     * Marks the current position in this input stream.
     * {@description.close}
     *
     * {@property.open runtime formal:java.io.InputStream_MarkReset}
     * <p> The <code>mark</code> method of <code>PushbackInputStream</code>
     * does nothing.
     * {@new.open}
     * This function is supposed to raise an exception but does not.
     * {@new.close}
     * {@property.close}
     *
     * @param   readlimit   the maximum limit of bytes that can be read before
     *                      the mark position becomes invalid.
     * @see     java.io.InputStream#reset()
     */
    public synchronized void mark(int readlimit) {
    }

    /** {@collect.stats}
     * {@description.open}
     * Repositions this stream to the position at the time the
     * <code>mark</code> method was last called on this input stream.
     * {@description.close}
     *
     * {@property.open runtime formal:java.io.InputStream_MarkReset}
     * <p> The method <code>reset</code> for class
     * <code>PushbackInputStream</code> does nothing except throw an
     * <code>IOException</code>.
     * {@property.close}
     *
     * @exception  IOException  if this method is invoked.
     * @see     java.io.InputStream#mark(int)
     * @see     java.io.IOException
     */
    public synchronized void reset() throws IOException {
        throw new IOException("mark/reset not supported");
    }

    /** {@collect.stats}
     * {@description.open}
     * Closes this input stream and releases any system resources
     * associated with the stream.
     * {@description.close}
     * {@property.open runtime formal:java.io.InputStream_ManipulateAfterClose}
     * Once the stream has been closed, further read(), unread(),
     * available(), reset(), or skip() invocations will throw an IOException.
     * {@property.close}
     * {@property.open runtime formal:java.io.Closeable_MultipleClose}
     * Closing a previously closed stream has no effect.
     * {@property.close}
     *
     * @exception  IOException  if an I/O error occurs.
     */
    public synchronized void close() throws IOException {
        if (in == null)
            return;
        in.close();
        in = null;
        buf = null;
    }
}
