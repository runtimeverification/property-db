/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package java.io;

/** {@collect.stats}
 * {@description.open}
 * Defines an interface for classes that allow reading serialized objects.
 * {@description.close}
 *
 * @see ObjectInputStream
 * @see ObjectOutput
 */
public interface ObjectInput extends DataInput, AutoCloseable {
    /** {@collect.stats}
     * {@description.open}
     * Indicates the number of bytes of primitive data that can be read without
     * blocking.
     * {@description.close}
     *
     * @return the number of bytes available.
     * @throws IOException
     *             if an I/O error occurs.
     */
    public int available() throws IOException;

    /** {@collect.stats}
     * {@description.open}
     * Closes this stream. 
     * {@description.close}
     * {@property.open}
     * Implementations of this method should free any
     * resources used by the stream.
     * {@property.close}
     *
     * @throws IOException
     *             if an I/O error occurs while closing the input stream.
     */
    public void close() throws IOException;

    /** {@collect.stats}
     * {@description.open}
     * Reads a single byte from this stream and returns it as an integer in the
     * range from 0 to 255. Returns -1 if the end of this stream has been
     * reached. 
     * {@description.close}
     * {@description.open blocking}
     * Blocks if no input is available.
     * {@description.close}
     *
     * @return the byte read or -1 if the end of this stream has been reached.
     * @throws IOException
     *             if this stream is closed or another I/O error occurs.
     */
    public int read() throws IOException;

    /** {@collect.stats}
     * {@description.open}
     * Reads bytes from this stream into the byte array {@code buffer}. 
     * {@description.close}
     * {@description.open blocking}
     * Blocks
     * while waiting for input.
     * {@description.close}
     *
     * @param buffer
     *            the array in which to store the bytes read.
     * @return the number of bytes read or -1 if the end of this stream has been
     *         reached.
     * @throws IOException
     *             if this stream is closed or another I/O error occurs.
     */
    public int read(byte[] buffer) throws IOException;

    /** {@collect.stats}
     * {@description.open}
     * Reads at most {@code count} bytes from this stream and stores them in
     * byte array {@code buffer} starting at offset {@code count}. 
     * {@description.close}
     * {@description.open blocking}
     * Blocks while
     * waiting for input.
     * {@description.close}
     *
     * @param buffer
     *            the array in which to store the bytes read.
     * @param offset
     *            the initial position in {@code buffer} to store the bytes read
     *            from this stream.
     * @param count
     *            the maximum number of bytes to store in {@code buffer}.
     * @return the number of bytes read or -1 if the end of this stream has been
     *         reached.
     * @throws IOException
     *             if this stream is closed or another I/O error occurs.
     */
    public int read(byte[] buffer, int offset, int count) throws IOException;

    /** {@collect.stats}
     * {@description.open}
     * Reads the next object from this stream.
     * {@description.close}
     *
     * @return the object read.
     *
     * @throws ClassNotFoundException
     *             if the object's class cannot be found.
     * @throws IOException
     *             if this stream is closed or another I/O error occurs.
     */
    public Object readObject() throws ClassNotFoundException, IOException;

    /** {@collect.stats}
     * {@description.open}
     * Skips {@code byteCount} bytes on this stream. Less than {@code byteCount} byte are
     * skipped if the end of this stream is reached before the operation
     * completes.
     * {@description.close}
     *
     * @return the number of bytes actually skipped.
     * @throws IOException
     *             if this stream is closed or another I/O error occurs.
     */
    public long skip(long byteCount) throws IOException;
}
