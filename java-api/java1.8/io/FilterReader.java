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


/** {@collect.stats}
 * {@description.open}
 * Abstract class for reading filtered character streams.
 * The abstract class <code>FilterReader</code> itself
 * provides default methods that pass all requests to
 * the contained stream. Subclasses of <code>FilterReader</code>
 * should override some of these methods and may also provide
 * additional methods and fields.
 * {@description.close}
 *
 * @author      Mark Reinhold
 * @since       JDK1.1
 */

public abstract class FilterReader extends Reader {

    /** {@collect.stats}
     * {@description.open}
     * The underlying character-input stream.
     * {@description.close}
     */
    protected Reader in;

    /** {@collect.stats}
     * {@description.open}
     * Creates a new filtered reader.
     * {@description.close}
     *
     * @param in  a Reader object providing the underlying stream.
     * @throws NullPointerException if <code>in</code> is <code>null</code>
     */
    protected FilterReader(Reader in) {
        super(in);
        this.in = in;
    }

    /** {@collect.stats}
     * {@description.open}
     * Reads a single character.
     * {@description.close}
     *
     * @exception  IOException  If an I/O error occurs
     */
    public int read() throws IOException {
        return in.read();
    }

    /** {@collect.stats}
     * {@description.open}
     * Reads characters into a portion of an array.
     * {@description.close}
     *
     * @exception  IOException  If an I/O error occurs
     */
    public int read(char cbuf[], int off, int len) throws IOException {
        return in.read(cbuf, off, len);
    }

    /** {@collect.stats}
     * {@description.open}
     * Skips characters.
     * {@description.close}
     *
     * @exception  IOException  If an I/O error occurs
     */
    public long skip(long n) throws IOException {
        return in.skip(n);
    }

    /** {@collect.stats}
     * {@description.open}
     * Tells whether this stream is ready to be read.
     * {@description.close}
     *
     * @exception  IOException  If an I/O error occurs
     */
    public boolean ready() throws IOException {
        return in.ready();
    }

    /** {@collect.stats}
     * {@description.open}
     * Tells whether this stream supports the mark() operation.
     * {@description.close}
     */
    public boolean markSupported() {
        return in.markSupported();
    }

    /** {@collect.stats}
     * {@description.open}
     * Marks the present position in the stream.
     * {@description.close}
     *
     * @exception  IOException  If an I/O error occurs
     */
    public void mark(int readAheadLimit) throws IOException {
        in.mark(readAheadLimit);
    }

    /** {@collect.stats}
     * {@description.open}
     * Resets the stream.
     * {@description.close}
     *
     * @exception  IOException  If an I/O error occurs
     */
    public void reset() throws IOException {
        in.reset();
    }

    public void close() throws IOException {
        in.close();
    }

}
