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
 * A helper interface with constants used by the serialization implementation.
 * {@description.close}
 */
public abstract interface ObjectStreamConstants {

    /** {@collect.stats}
     * {@description.open}
     * The stream header's magic number.
     * {@description.close}
     */
    public static final short STREAM_MAGIC = (short) 0xaced;

    /** {@collect.stats}
     * {@description.open}
     * The stream header's version number.
     * {@description.close}
     */
    public static final short STREAM_VERSION = 5;

    // These are tags to indicate the stream contents

    /** {@collect.stats}
     * {@description.open}
     * The minimum tag value.
     * {@description.close}
     */
    public static final byte TC_BASE = 0x70;

    /** {@collect.stats}
     * {@description.open}
     * Tag to mark a {@code null} object reference.
     * {@description.close}
     */
    public static final byte TC_NULL = (byte) 0x70;

    /** {@collect.stats}
     * {@description.open}
     * Tag to mark a reference to an object that has already been written to the
     * stream.
     * {@description.close}
     */
    public static final byte TC_REFERENCE = (byte) 0x71;

    /** {@collect.stats}
     * {@description.open}
     * Tag to mark a new class descriptor.
     * {@description.close}
     */
    public static final byte TC_CLASSDESC = (byte) 0x72;

    /** {@collect.stats}
     * {@description.open}
     * Tag to mark a new object.
     * {@description.close}
     */
    public static final byte TC_OBJECT = (byte) 0x73;

    /** {@collect.stats}
     * {@description.open}
     * Tag to mark a new string.
     * {@description.close}
     */
    public static final byte TC_STRING = (byte) 0x74;

    /** {@collect.stats}
     * {@description.open}
     * Tag to mark a new array.
     * {@description.close}
     */
    public static final byte TC_ARRAY = (byte) 0x75;

    /** {@collect.stats}
     * {@description.open}
     * Tag to mark a reference to a class.
     * {@description.close}
     */
    public static final byte TC_CLASS = (byte) 0x76;

    /** {@collect.stats}
     * {@description.open}
     * Tag to mark a block of optional data. The byte following this tag
     * indicates the size of the block.
     * {@description.close}
     */
    public static final byte TC_BLOCKDATA = (byte) 0x77;

    /** {@collect.stats}
     * {@description.open}
     * Tag to mark the end of block data blocks for an object.
     * {@description.close}
     */
    public static final byte TC_ENDBLOCKDATA = (byte) 0x78;

    /** {@collect.stats}
     * {@description.open}
     * Tag to mark a stream reset.
     * {@description.close}
     */
    public static final byte TC_RESET = (byte) 0x79;

    /** {@collect.stats}
     * {@description.open}
     * Tag to mark a long block of data. The long following this tag
     * indicates the size of the block.
     * {@description.close}
     */
    public static final byte TC_BLOCKDATALONG = (byte) 0x7A;

    /** {@collect.stats}
     * {@description.open}
     * Tag to mark an exception.
     * {@description.close}
     */
    public static final byte TC_EXCEPTION = (byte) 0x7B;

    /** {@collect.stats}
     * {@description.open}
     * Tag to mark a long string.
     * {@description.close}
     */
    public static final byte TC_LONGSTRING = (byte) 0x7C;

    /** {@collect.stats}
     * {@description.open}
     * Tag to mark a new proxy class descriptor.
     * {@description.close}
     */
    public static final byte TC_PROXYCLASSDESC = (byte) 0x7D;

    /** {@collect.stats}
     * {@description.open}
     * The maximum tag value.
     * {@description.close}
     */
    public static final byte TC_MAX = 0x7E;

    /** {@collect.stats}
     * {@description.open}
     * Handle for the first object that gets serialized.
     * {@description.close}
     */
    public static final int baseWireHandle = 0x007e0000;

    /** {@collect.stats}
     * {@description.open}
     * Stream protocol version 1.
     * {@description.close}
     */
    public static final int PROTOCOL_VERSION_1 = 1;

    /** {@collect.stats}
     * {@description.open}
     * Stream protocol version 2.
     * {@description.close}
     */
    public static final int PROTOCOL_VERSION_2 = 2;

    /** {@collect.stats}
     * {@description.open}
     * Permission constant to enable subclassing of ObjectInputStream and
     * ObjectOutputStream.
     * {@description.close}
     */
    public static final SerializablePermission SUBCLASS_IMPLEMENTATION_PERMISSION = new SerializablePermission(
            "enableSubclassImplementation");

    /** {@collect.stats}
     * {@description.open}
     * Permission constant to enable object substitution during serialization
     * and deserialization.
     * {@description.close}
     */
    public static final SerializablePermission SUBSTITUTION_PERMISSION = new SerializablePermission(
            "enableSubstitution");

    // Flags that indicate if the object was serializable, externalizable
    // and had a writeObject method when dumped.
    /** {@collect.stats}
     * {@description.open}
     * Bit mask for the {@code flag} field in ObjectStreamClass. Indicates
     * that a serializable class has its own {@code writeObject} method.
     * {@description.close}
     */
    public static final byte SC_WRITE_METHOD = 0x01; // If SC_SERIALIZABLE

    /** {@collect.stats}
     * {@description.open}
     * Bit mask for the {@code flag} field in ObjectStreamClass. Indicates
     * that a class is serializable.
     * {@description.close}
     */
    public static final byte SC_SERIALIZABLE = 0x02;

    /** {@collect.stats}
     * {@description.open}
     * Bit mask for the {@code flag} field in ObjectStreamClass. Indicates
     * that a class is externalizable.
     * {@description.close}
     */
    public static final byte SC_EXTERNALIZABLE = 0x04;

    /** {@collect.stats}
     * {@description.open}
     * Bit mask for the {@code flag} field in ObjectStreamClass. Indicates
     * that an externalizable class is written in block data mode.
     * {@description.close}
     */
    public static final byte SC_BLOCK_DATA = 0x08; // If SC_EXTERNALIZABLE

    /** {@collect.stats}
     * {@description.open}
     * Tag to mark a new enum.
     * {@description.close}
     */
    public static final byte TC_ENUM = 0x7E;

    /** {@collect.stats}
     * {@description.open}
     * Bit mask for the {@code flag} field in ObjectStreamClass. Indicates
     * that a class is an enum type.
     * {@description.close}
     */
    public static final byte SC_ENUM = 0x10;
}
