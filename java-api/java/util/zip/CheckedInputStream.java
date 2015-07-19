/*
 * Copyright (c) 1996, 2006, Oracle and/or its affiliates. All rights reserved.
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

package java.util.zip;

import java.io.FilterInputStream;
import java.io.InputStream;
import java.io.IOException;

/** {@collect.stats}
 *      
* {@description.open}
     * An input stream that also maintains a checksum of the data being read.
 * The checksum can then be used to verify the integrity of the input data.

     * {@description.close} *
 * @see         Checksum
 * @author      David Connelly
 */
public
class CheckedInputStream extends FilterInputStream {
    private Checksum cksum;

    /** {@collect.stats}
     *      
* {@description.open}
     * Creates an input stream using the specified Checksum.

     * {@description.close}     * @param in the input stream
     * @param cksum the Checksum
     */
    public CheckedInputStream(InputStream in, Checksum cksum) {
        super(in);
        this.cksum = cksum;
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Reads a byte. Will block if no input is available.

     * {@description.close}     * @return the byte read, or -1 if the end of the stream is reached.
     * @exception IOException if an I/O error has occurred
     */
    public int read() throws IOException {
        int b = in.read();
        if (b != -1) {
            cksum.update(b);
        }
        return b;
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Reads into an array of bytes. If <code>len</code> is not zero, the method
     * blocks until some input is available; otherwise, no
     * bytes are read and <code>0</code> is returned.

     * {@description.close}     * @param buf the buffer into which the data is read
     * @param off the start offset in the destination array <code>b</code>
     * @param len the maximum number of bytes read
     * @return    the actual number of bytes read, or -1 if the end
     *            of the stream is reached.
     * @exception  NullPointerException If <code>buf</code> is <code>null</code>.
     * @exception  IndexOutOfBoundsException If <code>off</code> is negative,
     * <code>len</code> is negative, or <code>len</code> is greater than
     * <code>buf.length - off</code>
     * @exception IOException if an I/O error has occurred
     */
    public int read(byte[] buf, int off, int len) throws IOException {
        len = in.read(buf, off, len);
        if (len != -1) {
            cksum.update(buf, off, len);
        }
        return len;
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Skips specified number of bytes of input.

     * {@description.close}     * @param n the number of bytes to skip
     * @return the actual number of bytes skipped
     * @exception IOException if an I/O error has occurred
     */
    public long skip(long n) throws IOException {
        byte[] buf = new byte[512];
        long total = 0;
        while (total < n) {
            long len = n - total;
            len = read(buf, 0, len < buf.length ? (int)len : buf.length);
            if (len == -1) {
                return total;
            }
            total += len;
        }
        return total;
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Returns the Checksum for this input stream.

     * {@description.close}     * @return the Checksum value
     */
    public Checksum getChecksum() {
        return cksum;
    }
}
