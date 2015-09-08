/*
 * Copyright (c) 1996, 2010, Oracle and/or its affiliates. All rights reserved.
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
 *      
* {@description.open}
     * ObjectOutput extends the DataOutput interface to include writing of objects.
 * DataOutput includes methods for output of primitive types, ObjectOutput
 * extends that interface to include objects, arrays, and Strings.

     * {@description.close} *
 * @author  unascribed
 * @see java.io.InputStream
 * @see java.io.ObjectOutputStream
 * @see java.io.ObjectInputStream
 * @since   JDK1.1
 */
public interface ObjectOutput extends DataOutput, AutoCloseable {
    /** {@collect.stats}
     *      
* {@description.open}
     * Write an object to the underlying storage or stream.  The
     * class that implements this interface defines how the object is
     * written.

     * {@description.close}     *
     * @param obj the object to be written
     * @exception IOException Any of the usual Input/Output related exceptions.
     */
    public void writeObject(Object obj)
      throws IOException;

    /** {@collect.stats}
     * {@description.open}
     * Writes a byte. 
     * {@description.close}
     * {@description.open blocking}
     * This method will block until the byte is actually
     * written.
     * {@description.close}
     * @param b the byte
     * @exception IOException If an I/O error has occurred.
     */
    public void write(int b) throws IOException;

    /** {@collect.stats}
     * {@description.open}
     * Writes an array of bytes. 
     * {@description.close}
     * {@description.open blocking}
     * This method will block until the bytes
     * are actually written.
     * {@description.close}
     * @param b the data to be written
     * @exception IOException If an I/O error has occurred.
     */
    public void write(byte b[]) throws IOException;

    /** {@collect.stats}
     *      
* {@description.open}
     * Writes a sub array of bytes.

     * {@description.close}     * @param b the data to be written
     * @param off       the start offset in the data
     * @param len       the number of bytes that are written
     * @exception IOException If an I/O error has occurred.
     */
    public void write(byte b[], int off, int len) throws IOException;

    /** {@collect.stats}
     *      
* {@description.open}
     * Flushes the stream. This will write any buffered
     * output bytes.

     * {@description.close}     * @exception IOException If an I/O error has occurred.
     */
    public void flush() throws IOException;

    /** {@collect.stats}
     *      
* {@description.open}
     * Closes the stream.
     * {@description.close}      
* {@property.open runtime formal:java.io.ObjectOutput_Close}
     * This method must be called
     * to release any resources associated with the
     * stream.

     * {@property.close}     * @exception IOException If an I/O error has occurred.
     */
    public void close() throws IOException;
}
