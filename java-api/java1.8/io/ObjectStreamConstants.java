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
 * Constants written into the Object Serialization Stream.
 * {@description.close}
 *
 * @author  unascribed
 * @since JDK 1.1
 */
public interface ObjectStreamConstants {

    /** {@collect.stats}
     * {@description.open}
     * Magic number that is written to the stream header.
     * {@description.close}
     */
    final static short STREAM_MAGIC = (short)0xaced;

    /** {@collect.stats}
     * {@description.open}
     * Version number that is written to the stream header.
     * {@description.close}
     */
    final static short STREAM_VERSION = 5;

    /* Each item in the stream is preceded by a tag
     */

    /** {@collect.stats}
     * {@description.open}
     * First tag value.
     * {@description.close}
     */
    final static byte TC_BASE = 0x70;

    /** {@collect.stats}
     * {@description.open}
     * Null object reference.
     * {@description.close}
     */
    final static byte TC_NULL =         (byte)0x70;

    /** {@collect.stats}
     * {@description.open}
     * Reference to an object already written into the stream.
     * {@description.close}
     */
    final static byte TC_REFERENCE =    (byte)0x71;

    /** {@collect.stats}
     * {@description.open}
     * new Class Descriptor.
     * {@description.close}
     */
    final static byte TC_CLASSDESC =    (byte)0x72;

    /** {@collect.stats}
     * {@description.open}
     * new Object.
     * {@description.close}
     */
    final static byte TC_OBJECT =       (byte)0x73;

    /** {@collect.stats}
     * {@description.open}
     * new String.
     * {@description.close}
     */
    final static byte TC_STRING =       (byte)0x74;

    /** {@collect.stats}
     * {@description.open}
     * new Array.
     * {@description.close}
     */
    final static byte TC_ARRAY =        (byte)0x75;

    /** {@collect.stats}
     * {@description.open}
     * Reference to Class.
     * {@description.close}
     */
    final static byte TC_CLASS =        (byte)0x76;

    /** {@collect.stats}
     * {@description.open}
     * Block of optional data. Byte following tag indicates number
     * of bytes in this block data.
     * {@description.close}
     */
    final static byte TC_BLOCKDATA =    (byte)0x77;

    /** {@collect.stats}
     * {@description.open}
     * End of optional block data blocks for an object.
     * {@description.close}
     */
    final static byte TC_ENDBLOCKDATA = (byte)0x78;

    /** {@collect.stats}
     * {@description.open}
     * Reset stream context. All handles written into stream are reset.
     * {@description.close}
     */
    final static byte TC_RESET =        (byte)0x79;

    /** {@collect.stats}
     * {@description.open}
     * long Block data. The long following the tag indicates the
     * number of bytes in this block data.
     * {@description.close}
     */
    final static byte TC_BLOCKDATALONG= (byte)0x7A;

    /** {@collect.stats}
     * {@description.open}
     * Exception during write.
     * {@description.close}
     */
    final static byte TC_EXCEPTION =    (byte)0x7B;

    /** {@collect.stats}
     * {@description.open}
     * Long string.
     * {@description.close}
     */
    final static byte TC_LONGSTRING =   (byte)0x7C;

    /** {@collect.stats}
     * {@description.open}
     * new Proxy Class Descriptor.
     * {@description.close}
     */
    final static byte TC_PROXYCLASSDESC =       (byte)0x7D;

    /** {@collect.stats}
     * {@description.open}
     * new Enum constant.
     * {@description.close}
     * @since 1.5
     */
    final static byte TC_ENUM =         (byte)0x7E;

    /** {@collect.stats}
     * {@description.open}
     * Last tag value.
     * {@description.close}
     */
    final static byte TC_MAX =          (byte)0x7E;

    /** {@collect.stats}
     * {@description.open}
     * First wire handle to be assigned.
     * {@description.close}
     */
    final static int baseWireHandle = 0x7e0000;


    /** {@collect.stats}****************************************************/
    /* Bit masks for ObjectStreamClass flag.*/

    /** {@collect.stats}
     * {@description.open}
     * Bit mask for ObjectStreamClass flag. Indicates a Serializable class
     * defines its own writeObject method.
     * {@description.close}
     */
    final static byte SC_WRITE_METHOD = 0x01;

    /** {@collect.stats}
     * {@description.open}
     * Bit mask for ObjectStreamClass flag. Indicates Externalizable data
     * written in Block Data mode.
     * Added for PROTOCOL_VERSION_2.
     * {@description.close}
     *
     * @see #PROTOCOL_VERSION_2
     * @since 1.2
     */
    final static byte SC_BLOCK_DATA = 0x08;

    /** {@collect.stats}
     * {@description.open}
     * Bit mask for ObjectStreamClass flag. Indicates class is Serializable.
     * {@description.close}
     */
    final static byte SC_SERIALIZABLE = 0x02;

    /** {@collect.stats}
     * {@description.open}
     * Bit mask for ObjectStreamClass flag. Indicates class is Externalizable.
     * {@description.close}
     */
    final static byte SC_EXTERNALIZABLE = 0x04;

    /** {@collect.stats}
     * {@description.open}
     * Bit mask for ObjectStreamClass flag. Indicates class is an enum type.
     * {@description.close}
     * @since 1.5
     */
    final static byte SC_ENUM = 0x10;


    /* *******************************************************************/
    /* Security permissions */

    /** {@collect.stats}
     * {@description.open}
     * Enable substitution of one object for another during
     * serialization/deserialization.
     * {@description.close}
     *
     * @see java.io.ObjectOutputStream#enableReplaceObject(boolean)
     * @see java.io.ObjectInputStream#enableResolveObject(boolean)
     * @since 1.2
     */
    final static SerializablePermission SUBSTITUTION_PERMISSION =
                           new SerializablePermission("enableSubstitution");

    /** {@collect.stats}
     * {@description.open}
     * Enable overriding of readObject and writeObject.
     * {@description.close}
     *
     * @see java.io.ObjectOutputStream#writeObjectOverride(Object)
     * @see java.io.ObjectInputStream#readObjectOverride()
     * @since 1.2
     */
    final static SerializablePermission SUBCLASS_IMPLEMENTATION_PERMISSION =
                    new SerializablePermission("enableSubclassImplementation");
   /** {@collect.stats}
    * {@description.open}
    * A Stream Protocol Version. <p>
    *
    * All externalizable data is written in JDK 1.1 external data
    * format after calling this method. This version is needed to write
    * streams containing Externalizable data that can be read by
    * pre-JDK 1.1.6 JVMs.
    * {@description.close}
    *
    * @see java.io.ObjectOutputStream#useProtocolVersion(int)
    * @since 1.2
    */
    public final static int PROTOCOL_VERSION_1 = 1;


   /** {@collect.stats}
    * {@description.open}
    * A Stream Protocol Version. <p>
    *
    * This protocol is written by JVM 1.2.
    *
    * Externalizable data is written in block data mode and is
    * terminated with TC_ENDBLOCKDATA. Externalizable class descriptor
    * flags has SC_BLOCK_DATA enabled. JVM 1.1.6 and greater can
    * read this format change.
    *
    * Enables writing a nonSerializable class descriptor into the
    * stream. The serialVersionUID of a nonSerializable class is
    * set to 0L.
    * {@description.close}
    *
    * @see java.io.ObjectOutputStream#useProtocolVersion(int)
    * @see #SC_BLOCK_DATA
    * @since 1.2
    */
    public final static int PROTOCOL_VERSION_2 = 2;
}
