/*
 * Copyright (c) 1996, 1999, Oracle and/or its affiliates. All rights reserved.
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
 * ObjectInput extends the DataInput interface to include the reading of
 * objects. DataInput includes methods for the input of primitive types,
 * ObjectInput extends that interface to include objects, arrays, and Strings.
 * {@description.close}
 *
 * @author  unascribed
 * @see java.io.InputStream
 * @see java.io.ObjectOutputStream
 * @see java.io.ObjectInputStream
 * @since   JDK1.1
 */
public interface ObjectInput extends DataInput {
    /** {@collect.stats}
     * {@description.open}
     * Read and return an object. The class that implements this interface
     * defines where the object is "read" from.
     * {@description.close}
     *
     * @return the object read from the stream
     * @exception java.lang.ClassNotFoundException If the class of a serialized
     *      object cannot be found.
     * @exception IOException If any of the usual Input/Output
     * related exceptions occur.
     */
    public Object readObject()
        throws ClassNotFoundException, IOException;

    /** {@collect.stats}
     * {@description.open}
     * Reads a byte of data.
     * {@description.close}
     * {@description.open blocking}
     * This method will block if no input is
     * available.
     * {@description.close}
     * @return  the byte read, or -1 if the end of the
     *          stream is reached.
     * @exception IOException If an I/O error has occurred.
     */
    public int read() throws IOException;

    /** {@collect.stats}
     * {@description.open}
     * Reads into an array of bytes.
     * {@description.close}
     * {@description.open blocking}
     * This method will
     * block until some input is available.
     * {@description.close}
     * @param b the buffer into which the data is read
     * @return  the actual number of bytes read, -1 is
     *          returned when the end of the stream is reached.
     * @exception IOException If an I/O error has occurred.
     */
    public int read(byte b[]) throws IOException;

    /** {@collect.stats}
     * {@description.open}
     * Reads into an array of bytes.
     * {@description.close}
     * {@description.open blocking}
     * This method will
     * block until some input is available.
     * {@description.close}
     * @param b the buffer into which the data is read
     * @param off the start offset of the data
     * @param len the maximum number of bytes read
     * @return  the actual number of bytes read, -1 is
     *          returned when the end of the stream is reached.
     * @exception IOException If an I/O error has occurred.
     */
    public int read(byte b[], int off, int len) throws IOException;

    /** {@collect.stats}
     * {@description.open}
     * Skips n bytes of input.
     * {@description.close}
     * @param n the number of bytes to be skipped
     * @return  the actual number of bytes skipped.
     * @exception IOException If an I/O error has occurred.
     */
    public long skip(long n) throws IOException;

    /** {@collect.stats}
     * {@description.open}
     * Returns the number of bytes that can be read
     * without blocking.
     * {@description.close}
     * @return the number of available bytes.
     * @exception IOException If an I/O error has occurred.
     */
    public int available() throws IOException;

    /** {@collect.stats}
     * {@description.open}
     * Closes the input stream.
     * {@description.close}
     * {@property.open runtime formal:java.io.ObjectInput_Close}
     * Must be called
     * to release any resources associated with
     * the stream.
     * {@property.close}
     * @exception IOException If an I/O error has occurred.
     */
    public void close() throws IOException;
}
