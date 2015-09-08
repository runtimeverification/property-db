/*
 * Copyright (c) 1996, 1999, Oracle and/or its affiliates. All rights reserved.
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

import java.io.FilterOutputStream;
import java.io.OutputStream;
import java.io.IOException;

/** {@collect.stats}
 *      
* {@description.open}
     * An output stream that also maintains a checksum of the data being
 * written. The checksum can then be used to verify the integrity of
 * the output data.

     * {@description.close} *
 * @see         Checksum
 * @author      David Connelly
 */
public
class CheckedOutputStream extends FilterOutputStream {
    private Checksum cksum;

    /** {@collect.stats}
     *      
* {@description.open}
     * Creates an output stream with the specified Checksum.

     * {@description.close}     * @param out the output stream
     * @param cksum the checksum
     */
    public CheckedOutputStream(OutputStream out, Checksum cksum) {
        super(out);
        this.cksum = cksum;
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Writes a byte. Will block until the byte is actually written.

     * {@description.close}     * @param b the byte to be written
     * @exception IOException if an I/O error has occurred
     */
    public void write(int b) throws IOException {
        out.write(b);
        cksum.update(b);
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Writes an array of bytes. Will block until the bytes are
     * actually written.

     * {@description.close}     * @param b the data to be written
     * @param off the start offset of the data
     * @param len the number of bytes to be written
     * @exception IOException if an I/O error has occurred
     */
    public void write(byte[] b, int off, int len) throws IOException {
        out.write(b, off, len);
        cksum.update(b, off, len);
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Returns the Checksum for this output stream.

     * {@description.close}     * @return the Checksum
     */
    public Checksum getChecksum() {
        return cksum;
    }
}
