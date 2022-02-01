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
 * Abstract class for writing filtered character streams.
 * The abstract class <code>FilterWriter</code> itself
 * provides default methods that pass all requests to the
 * contained stream. Subclasses of <code>FilterWriter</code>
 * should override some of these methods and may also
 * provide additional methods and fields.
 * {@description.close}
 *
 * @author      Mark Reinhold
 * @since       JDK1.1
 */

public abstract class FilterWriter extends Writer {

    /** {@collect.stats}
     * {@description.open}
     * The underlying character-output stream.
     * {@description.close}
     */
    protected Writer out;

    /** {@collect.stats}
     * {@description.open}
     * Create a new filtered writer.
     * {@description.close}
     *
     * @param out  a Writer object to provide the underlying stream.
     * @throws NullPointerException if <code>out</code> is <code>null</code>
     */
    protected FilterWriter(Writer out) {
        super(out);
        this.out = out;
    }

    /** {@collect.stats}
     * {@description.open}
     * Writes a single character.
     * {@description.close}
     *
     * @exception  IOException  If an I/O error occurs
     */
    public void write(int c) throws IOException {
        out.write(c);
    }

    /** {@collect.stats}
     * {@description.open}
     * Writes a portion of an array of characters.
     * {@description.close}
     *
     * @param  cbuf  Buffer of characters to be written
     * @param  off   Offset from which to start reading characters
     * @param  len   Number of characters to be written
     *
     * @exception  IOException  If an I/O error occurs
     */
    public void write(char cbuf[], int off, int len) throws IOException {
        out.write(cbuf, off, len);
    }

    /** {@collect.stats}
     * {@description.open}
     * Writes a portion of a string.
     * {@description.close}
     *
     * @param  str  String to be written
     * @param  off  Offset from which to start reading characters
     * @param  len  Number of characters to be written
     *
     * @exception  IOException  If an I/O error occurs
     */
    public void write(String str, int off, int len) throws IOException {
        out.write(str, off, len);
    }

    /** {@collect.stats}
     * {@description.open}
     * Flushes the stream.
     * {@description.close}
     *
     * @exception  IOException  If an I/O error occurs
     */
    public void flush() throws IOException {
        out.flush();
    }

    public void close() throws IOException {
        out.close();
    }

}
