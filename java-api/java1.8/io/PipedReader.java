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
 * Piped character-input streams.
 * {@description.close}
 *
 * @author      Mark Reinhold
 * @since       JDK1.1
 */

public class PipedReader extends Reader {
    boolean closedByWriter = false;
    boolean closedByReader = false;
    boolean connected = false;

    /* REMIND: identification of the read and write sides needs to be
       more sophisticated.  Either using thread groups (but what about
       pipes within a thread?) or using finalization (but it may be a
       long time until the next GC). */
    Thread readSide;
    Thread writeSide;

   /** {@collect.stats}
    * {@description.open}
    * The size of the pipe's circular input buffer.
    * {@description.close}
    */
    private static final int DEFAULT_PIPE_SIZE = 1024;

    /** {@collect.stats}
     * {@description.open}
     * The circular buffer into which incoming data is placed.
     * {@description.close}
     */
    char buffer[];

    /** {@collect.stats}
     * {@description.open}
     * The index of the position in the circular buffer at which the
     * next character of data will be stored when received from the connected
     * piped writer. <code>in&lt;0</code> implies the buffer is empty,
     * <code>in==out</code> implies the buffer is full
     * {@description.close}
     */
    int in = -1;

    /** {@collect.stats}
     * {@description.open}
     * The index of the position in the circular buffer at which the next
     * character of data will be read by this piped reader.
     * {@description.close}
     */
    int out = 0;

    /** {@collect.stats}
     * {@description.open}
     * Creates a <code>PipedReader</code> so
     * that it is connected to the piped writer
     * <code>src</code>. Data written to <code>src</code>
     * will then be available as input from this stream.
     * {@description.close}
     *
     * @param      src   the stream to connect to.
     * @exception  IOException  if an I/O error occurs.
     */
    public PipedReader(PipedWriter src) throws IOException {
        this(src, DEFAULT_PIPE_SIZE);
    }

    /** {@collect.stats}
     * {@description.open}
     * Creates a <code>PipedReader</code> so that it is connected
     * to the piped writer <code>src</code> and uses the specified
     * pipe size for the pipe's buffer. Data written to <code>src</code>
     * will then be  available as input from this stream.
 {@description.close}

     * @param      src       the stream to connect to.
     * @param      pipeSize  the size of the pipe's buffer.
     * @exception  IOException  if an I/O error occurs.
     * @exception  IllegalArgumentException if {@code pipeSize <= 0}.
     * @since      1.6
     */
    public PipedReader(PipedWriter src, int pipeSize) throws IOException {
        initPipe(pipeSize);
        connect(src);
    }


    /** {@collect.stats}
     * {@description.open}
     * Creates a <code>PipedReader</code> so
     * that it is not yet {@linkplain #connect(java.io.PipedWriter)
     * connected}. It must be {@linkplain java.io.PipedWriter#connect(
     * java.io.PipedReader) connected} to a <code>PipedWriter</code>
     * before being used.
     * {@description.close}
     */
    public PipedReader() {
        initPipe(DEFAULT_PIPE_SIZE);
    }

    /** {@collect.stats}
     * {@description.open}
     * Creates a <code>PipedReader</code> so that it is not yet
     * {@link #connect(java.io.PipedWriter) connected} and uses
     * the specified pipe size for the pipe's buffer.
     * It must be  {@linkplain java.io.PipedWriter#connect(
     * java.io.PipedReader) connected} to a <code>PipedWriter</code>
     * before being used.
     * {@description.close}
     *
     * @param   pipeSize the size of the pipe's buffer.
     * @exception  IllegalArgumentException if {@code pipeSize <= 0}.
     * @since      1.6
     */
    public PipedReader(int pipeSize) {
        initPipe(pipeSize);
    }

    private void initPipe(int pipeSize) {
        if (pipeSize <= 0) {
            throw new IllegalArgumentException("Pipe size <= 0");
        }
        buffer = new char[pipeSize];
    }

    /** {@collect.stats}
     * {@description.open}
     * Causes this piped reader to be connected
     * to the piped  writer <code>src</code>.
     * If this object is already connected to some
     * other piped writer, an <code>IOException</code>
     * is thrown.
     * <p>
     * If <code>src</code> is an
     * unconnected piped writer and <code>snk</code>
     * is an unconnected piped reader, they
     * may be connected by either the call:
     *
     * <pre><code>snk.connect(src)</code> </pre>
     * <p>
     * or the call:
     *
     * <pre><code>src.connect(snk)</code> </pre>
     * <p>
     * The two calls have the same effect.
     * {@description.close}
     *
     * @param      src   The piped writer to connect to.
     * @exception  IOException  if an I/O error occurs.
     */
    public void connect(PipedWriter src) throws IOException {
        src.connect(this);
    }

    /** {@collect.stats}
     * {@description.open}
     * Receives a char of data.
     * {@description.close}
     * {@description.open blocking}
     * This method will block if no input is
     * available.
     * {@description.close}
     */
    synchronized void receive(int c) throws IOException {
        if (!connected) {
            throw new IOException("Pipe not connected");
        } else if (closedByWriter || closedByReader) {
            throw new IOException("Pipe closed");
        } else if (readSide != null && !readSide.isAlive()) {
            throw new IOException("Read end dead");
        }

        writeSide = Thread.currentThread();
        while (in == out) {
            if ((readSide != null) && !readSide.isAlive()) {
                throw new IOException("Pipe broken");
            }
            /* full: kick any waiting readers */
            notifyAll();
            try {
                wait(1000);
            } catch (InterruptedException ex) {
                throw new java.io.InterruptedIOException();
            }
        }
        if (in < 0) {
            in = 0;
            out = 0;
        }
        buffer[in++] = (char) c;
        if (in >= buffer.length) {
            in = 0;
        }
    }

    /** {@collect.stats}
     * {@description.open}
     * Receives data into an array of characters.
     * {@description.close}
     * {@description.open blocking}
     * This method will
     * block until some input is available.
     * {@description.close}
     */
    synchronized void receive(char c[], int off, int len)  throws IOException {
        while (--len >= 0) {
            receive(c[off++]);
        }
    }

    /** {@collect.stats}
     * {@description.open}
     * Notifies all waiting threads that the last character of data has been
     * received.
     * {@description.close}
     */
    synchronized void receivedLast() {
        closedByWriter = true;
        notifyAll();
    }

    /** {@collect.stats}
     * {@description.open}
     * Reads the next character of data from this piped stream.
     * If no character is available because the end of the stream
     * has been reached, the value <code>-1</code> is returned.
     * {@description.close}
     * {@description.open blocking}
     * This method blocks until input data is available, the end of
     * the stream is detected, or an exception is thrown.
     * {@description.close}
     *
     * @return     the next character of data, or <code>-1</code> if the end of the
     *             stream is reached.
     * @exception  IOException  if the pipe is
     *          <a href=PipedInputStream.html#BROKEN> <code>broken</code></a>,
     *          {@link #connect(java.io.PipedWriter) unconnected}, closed,
     *          or an I/O error occurs.
     */
    public synchronized int read()  throws IOException {
        if (!connected) {
            throw new IOException("Pipe not connected");
        } else if (closedByReader) {
            throw new IOException("Pipe closed");
        } else if (writeSide != null && !writeSide.isAlive()
                   && !closedByWriter && (in < 0)) {
            throw new IOException("Write end dead");
        }

        readSide = Thread.currentThread();
        int trials = 2;
        while (in < 0) {
            if (closedByWriter) {
                /* closed by writer, return EOF */
                return -1;
            }
            if ((writeSide != null) && (!writeSide.isAlive()) && (--trials < 0)) {
                throw new IOException("Pipe broken");
            }
            /* might be a writer waiting */
            notifyAll();
            try {
                wait(1000);
            } catch (InterruptedException ex) {
                throw new java.io.InterruptedIOException();
            }
        }
        int ret = buffer[out++];
        if (out >= buffer.length) {
            out = 0;
        }
        if (in == out) {
            /* now empty */
            in = -1;
        }
        return ret;
    }

    /** {@collect.stats}
     * {@description.open}
     * Reads up to <code>len</code> characters of data from this piped
     * stream into an array of characters. Less than <code>len</code> characters
     * will be read if the end of the data stream is reached or if
     * <code>len</code> exceeds the pipe's buffer size.
     * {@description.close}
     * {@description.open blocking}
     * This method
     * blocks until at least one character of input is available.
     * {@description.close}
     *
     * @param      cbuf     the buffer into which the data is read.
     * @param      off   the start offset of the data.
     * @param      len   the maximum number of characters read.
     * @return     the total number of characters read into the buffer, or
     *             <code>-1</code> if there is no more data because the end of
     *             the stream has been reached.
     * @exception  IOException  if the pipe is
     *                  <a href=PipedInputStream.html#BROKEN> <code>broken</code></a>,
     *                  {@link #connect(java.io.PipedWriter) unconnected}, closed,
     *                  or an I/O error occurs.
     */
    public synchronized int read(char cbuf[], int off, int len)  throws IOException {
        if (!connected) {
            throw new IOException("Pipe not connected");
        } else if (closedByReader) {
            throw new IOException("Pipe closed");
        } else if (writeSide != null && !writeSide.isAlive()
                   && !closedByWriter && (in < 0)) {
            throw new IOException("Write end dead");
        }

        if ((off < 0) || (off > cbuf.length) || (len < 0) ||
            ((off + len) > cbuf.length) || ((off + len) < 0)) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return 0;
        }

        /* possibly wait on the first character */
        int c = read();
        if (c < 0) {
            return -1;
        }
        cbuf[off] =  (char)c;
        int rlen = 1;
        while ((in >= 0) && (--len > 0)) {
            cbuf[off + rlen] = buffer[out++];
            rlen++;
            if (out >= buffer.length) {
                out = 0;
            }
            if (in == out) {
                /* now empty */
                in = -1;
            }
        }
        return rlen;
    }

    /** {@collect.stats}
     * {@description.open}
     * Tell whether this stream is ready to be read.  A piped character
     * stream is ready if the circular buffer is not empty.
     * {@description.close}
     *
     * @exception  IOException  if the pipe is
     *                  <a href=PipedInputStream.html#BROKEN> <code>broken</code></a>,
     *                  {@link #connect(java.io.PipedWriter) unconnected}, or closed.
     */
    public synchronized boolean ready() throws IOException {
        if (!connected) {
            throw new IOException("Pipe not connected");
        } else if (closedByReader) {
            throw new IOException("Pipe closed");
        } else if (writeSide != null && !writeSide.isAlive()
                   && !closedByWriter && (in < 0)) {
            throw new IOException("Write end dead");
        }
        if (in < 0) {
            return false;
        } else {
            return true;
        }
    }

    /** {@collect.stats}
     * {@description.open}
     * Closes this piped stream and releases any system resources
     * associated with the stream.
     * {@description.close}
     *
     * @exception  IOException  if an I/O error occurs.
     */
    public void close()  throws IOException {
        in = -1;
        closedByReader = true;
    }
}
