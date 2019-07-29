/*
 * Copyright (c) 1994, 2011, Oracle and/or its affiliates. All rights reserved.
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

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Vector;

/** {@collect.stats}
 * {@description.open}
 * A <code>SequenceInputStream</code> represents
 * the logical concatenation of other input
 * streams. It starts out with an ordered
 * collection of input streams and reads from
 * the first one until end of file is reached,
 * whereupon it reads from the second one,
 * and so on, until end of file is reached
 * on the last of the contained input streams.
 * {@description.close}
 *
 * @author  Author van Hoff
 * @since   JDK1.0
 */
public
class SequenceInputStream extends InputStream {
    Enumeration<? extends InputStream> e;
    InputStream in;

    /** {@collect.stats}
     * {@description.open}
     * Initializes a newly created <code>SequenceInputStream</code>
     * by remembering the argument, which must
     * be an <code>Enumeration</code>  that produces
     * objects whose run-time type is <code>InputStream</code>.
     * The input streams that are  produced by
     * the enumeration will be read, in order,
     * to provide the bytes to be read  from this
     * <code>SequenceInputStream</code>. After
     * each input stream from the enumeration
     * is exhausted, it is closed by calling its
     * <code>close</code> method.
     * {@description.close}
     *
     * @param   e   an enumeration of input streams.
     * @see     java.util.Enumeration
     */
    public SequenceInputStream(Enumeration<? extends InputStream> e) {
        this.e = e;
        try {
            nextStream();
        } catch (IOException ex) {
            // This should never happen
            throw new Error("panic");
        }
    }

    /** {@collect.stats}
     * {@description.open}
     * Initializes a newly
     * created <code>SequenceInputStream</code>
     * by remembering the two arguments, which
     * will be read in order, first <code>s1</code>
     * and then <code>s2</code>, to provide the
     * bytes to be read from this <code>SequenceInputStream</code>.
     * {@description.close}
     *
     * @param   s1   the first input stream to read.
     * @param   s2   the second input stream to read.
     */
    public SequenceInputStream(InputStream s1, InputStream s2) {
        Vector<InputStream> v = new Vector<>(2);

        v.addElement(s1);
        v.addElement(s2);
        e = v.elements();
        try {
            nextStream();
        } catch (IOException ex) {
            // This should never happen
            throw new Error("panic");
        }
    }

    /** {@collect.stats}
     * {@description.open}
     *  Continues reading in the next stream if an EOF is reached.
     * {@description.close}
     */
    final void nextStream() throws IOException {
        if (in != null) {
            in.close();
        }

        if (e.hasMoreElements()) {
            in = (InputStream) e.nextElement();
            if (in == null)
                throw new NullPointerException();
        }
        else in = null;

    }

    /** {@collect.stats}
     * {@description.open}
     * Returns an estimate of the number of bytes that can be read (or
     * skipped over) from the current underlying input stream without
     * blocking by the next invocation of a method for the current
     * underlying input stream. The next invocation might be
     * the same thread or another thread.  A single read or skip of this
     * many bytes will not block, but may read or skip fewer bytes.
     * <p>
     * This method simply calls {@code available} of the current underlying
     * input stream and returns the result.
     * {@description.close}
     *
     * @return an estimate of the number of bytes that can be read (or
     *         skipped over) from the current underlying input stream
     *         without blocking or {@code 0} if this input stream
     *         has been closed by invoking its {@link #close()} method
     * @exception  IOException  if an I/O error occurs.
     *
     * @since   JDK1.1
     */
    public int available() throws IOException {
        if(in == null) {
            return 0; // no way to signal EOF from available()
        }
        return in.available();
    }

    /** {@collect.stats}
     * {@description.open}
     * Reads the next byte of data from this input stream. The byte is
     * returned as an <code>int</code> in the range <code>0</code> to
     * <code>255</code>. If no byte is available because the end of the
     * stream has been reached, the value <code>-1</code> is returned.
     * {@description.close}
     * {@description.open blocking}
     * This method blocks until input data is available, the end of the
     * stream is detected, or an exception is thrown.
     * {@description.close}
     * {@description.open}
     * <p>
     * This method
     * tries to read one character from the current substream. If it
     * reaches the end of the stream, it calls the <code>close</code>
     * method of the current substream and begins reading from the next
     * substream.
     * {@description.close}
     *
     * @return     the next byte of data, or <code>-1</code> if the end of the
     *             stream is reached.
     * @exception  IOException  if an I/O error occurs.
     */
    public int read() throws IOException {
        if (in == null) {
            return -1;
        }
        int c = in.read();
        if (c == -1) {
            nextStream();
            return read();
        }
        return c;
    }

    /** {@collect.stats}
     * {@description.open}
     * Reads up to <code>len</code> bytes of data from this input stream
     * into an array of bytes.
     * {@description.close}
     * {@description.open blocking}
     *  If <code>len</code> is not zero, the method
     * blocks until at least 1 byte of input is available; otherwise, no
     * bytes are read and <code>0</code> is returned.
     * {@description.close}
     * {@description.open}
     * <p>
     * The <code>read</code> method of <code>SequenceInputStream</code>
     * tries to read the data from the current substream. If it fails to
     * read any characters because the substream has reached the end of
     * the stream, it calls the <code>close</code> method of the current
     * substream and begins reading from the next substream.
     * {@description.close}
     *
     * @param      b     the buffer into which the data is read.
     * @param      off   the start offset in array <code>b</code>
     *                   at which the data is written.
     * @param      len   the maximum number of bytes read.
     * @return     int   the number of bytes read.
     * @exception  NullPointerException If <code>b</code> is <code>null</code>.
     * @exception  IndexOutOfBoundsException If <code>off</code> is negative,
     * <code>len</code> is negative, or <code>len</code> is greater than
     * <code>b.length - off</code>
     * @exception  IOException  if an I/O error occurs.
     */
    public int read(byte b[], int off, int len) throws IOException {
        if (in == null) {
            return -1;
        } else if (b == null) {
            throw new NullPointerException();
        } else if (off < 0 || len < 0 || len > b.length - off) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return 0;
        }

        int n = in.read(b, off, len);
        if (n <= 0) {
            nextStream();
            return read(b, off, len);
        }
        return n;
    }

    /** {@collect.stats}
     * {@description.open}
     * Closes this input stream and releases any system resources
     * associated with the stream.
     * {@description.close}
     * {@property.open runtime formal:java.io.InputStream_ManipulateAfterClose}
     * A closed <code>SequenceInputStream</code>
     * cannot  perform input operations and cannot
     * be reopened.
     * <p>
     * {@property.close}
     * {@description.open}
     * If this stream was created
     * from an enumeration, all remaining elements
     * are requested from the enumeration and closed
     * before the <code>close</code> method returns.
     * {@description.close}
     *
     * @exception  IOException  if an I/O error occurs.
     */
    public void close() throws IOException {
        do {
            nextStream();
        } while (in != null);
    }
}
