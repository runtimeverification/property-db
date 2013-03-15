/*
 * Copyright (c) 1996, 2006, Oracle and/or its affiliates. All rights reserved.
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

import java.lang.reflect.Field;

/** {@collect.stats}
 * {@description.open}
 * A description of a Serializable field from a Serializable class.  An array
 * of ObjectStreamFields is used to declare the Serializable fields of a class.
 * {@description.close}
 *
 * @author      Mike Warres
 * @author      Roger Riggs
 * @see ObjectStreamClass
 * @since 1.2
 */
public class ObjectStreamField
    implements Comparable<Object>
{

    /** {@collect.stats}
     * {@description.open}
     * field name
     * {@description.close}
     */
    private final String name;
    /** {@collect.stats}
     * {@description.open}
     * canonical JVM signature of field type
     * {@description.close}
     */
    private final String signature;
    /** {@collect.stats}
     * {@description.open}
     * field type (Object.class if unknown non-primitive type)
     * {@description.close}
     */
    private final Class type;
    /** {@collect.stats}
     * {@description.open}
     * whether or not to (de)serialize field values as unshared
     * {@description.close}
     */
    private final boolean unshared;
    /** {@collect.stats}
     * {@description.open}
     * corresponding reflective field object, if any
     * {@description.close}
     */
    private final Field field;
    /** {@collect.stats}
     * {@description.open}
     * offset of field value in enclosing field group
     * {@description.close}
     */
    private int offset = 0;

    /** {@collect.stats}
     * {@description.open}
     * Create a Serializable field with the specified type.  This field should
     * be documented with a <code>serialField</code> tag.
     * {@description.close}
     *
     * @param   name the name of the serializable field
     * @param   type the <code>Class</code> object of the serializable field
     */
    public ObjectStreamField(String name, Class<?> type) {
        this(name, type, false);
    }

    /** {@collect.stats}
     * {@description.open}
     * Creates an ObjectStreamField representing a serializable field with the
     * given name and type.  If unshared is false, values of the represented
     * field are serialized and deserialized in the default manner--if the
     * field is non-primitive, object values are serialized and deserialized as
     * if they had been written and read by calls to writeObject and
     * readObject.  If unshared is true, values of the represented field are
     * serialized and deserialized as if they had been written and read by
     * calls to writeUnshared and readUnshared.
     * {@description.close}
     *
     * @param   name field name
     * @param   type field type
     * @param   unshared if false, write/read field values in the same manner
     *          as writeObject/readObject; if true, write/read in the same
     *          manner as writeUnshared/readUnshared
     * @since   1.4
     */
    public ObjectStreamField(String name, Class<?> type, boolean unshared) {
        if (name == null) {
            throw new NullPointerException();
        }
        this.name = name;
        this.type = type;
        this.unshared = unshared;
        signature = ObjectStreamClass.getClassSignature(type).intern();
        field = null;
    }

    /** {@collect.stats}
     * {@description.open}
     * Creates an ObjectStreamField representing a field with the given name,
     * signature and unshared setting.
     * {@description.close}
     */
    ObjectStreamField(String name, String signature, boolean unshared) {
        if (name == null) {
            throw new NullPointerException();
        }
        this.name = name;
        this.signature = signature.intern();
        this.unshared = unshared;
        field = null;

        switch (signature.charAt(0)) {
            case 'Z': type = Boolean.TYPE; break;
            case 'B': type = Byte.TYPE; break;
            case 'C': type = Character.TYPE; break;
            case 'S': type = Short.TYPE; break;
            case 'I': type = Integer.TYPE; break;
            case 'J': type = Long.TYPE; break;
            case 'F': type = Float.TYPE; break;
            case 'D': type = Double.TYPE; break;
            case 'L':
            case '[': type = Object.class; break;
            default: throw new IllegalArgumentException("illegal signature");
        }
    }

    /** {@collect.stats}
     * {@description.open}
     * Creates an ObjectStreamField representing the given field with the
     * specified unshared setting.  For compatibility with the behavior of
     * earlier serialization implementations, a "showType" parameter is
     * necessary to govern whether or not a getType() call on this
     * ObjectStreamField (if non-primitive) will return Object.class (as
     * opposed to a more specific reference type).
     * {@description.close}
     */
    ObjectStreamField(Field field, boolean unshared, boolean showType) {
        this.field = field;
        this.unshared = unshared;
        name = field.getName();
        Class ftype = field.getType();
        type = (showType || ftype.isPrimitive()) ? ftype : Object.class;
        signature = ObjectStreamClass.getClassSignature(ftype).intern();
    }

    /** {@collect.stats}
     * {@description.open}
     * Get the name of this field.
     * {@description.close}
     *
     * @return  a <code>String</code> representing the name of the serializable
     *          field
     */
    public String getName() {
        return name;
    }

    /** {@collect.stats}
     * {@description.open}
     * Get the type of the field.  If the type is non-primitive and this
     * <code>ObjectStreamField</code> was obtained from a deserialized {@link
     * ObjectStreamClass} instance, then <code>Object.class</code> is returned.
     * Otherwise, the <code>Class</code> object for the type of the field is
     * returned.
     * {@description.close}
     *
     * @return  a <code>Class</code> object representing the type of the
     *          serializable field
     */
    public Class<?> getType() {
        return type;
    }

    /** {@collect.stats}
     * {@description.open}
     * Returns character encoding of field type.  The encoding is as follows:
     * <blockquote><pre>
     * B            byte
     * C            char
     * D            double
     * F            float
     * I            int
     * J            long
     * L            class or interface
     * S            short
     * Z            boolean
     * [            array
     * </pre></blockquote>
     * {@description.close}
     *
     * @return  the typecode of the serializable field
     */
    // REMIND: deprecate?
    public char getTypeCode() {
        return signature.charAt(0);
    }

    /** {@collect.stats}
     * {@description.open}
     * Return the JVM type signature.
     * {@description.close}
     *
     * @return  null if this field has a primitive type.
     */
    // REMIND: deprecate?
    public String getTypeString() {
        return isPrimitive() ? null : signature;
    }

    /** {@collect.stats}
     * {@description.open}
     * Offset of field within instance data.
     * {@description.close}
     *
     * @return  the offset of this field
     * @see #setOffset
     */
    // REMIND: deprecate?
    public int getOffset() {
        return offset;
    }

    /** {@collect.stats}
     * {@description.open}
     * Offset within instance data.
     * {@description.close}
     *
     * @param   offset the offset of the field
     * @see #getOffset
     */
    // REMIND: deprecate?
    protected void setOffset(int offset) {
        this.offset = offset;
    }

    /** {@collect.stats}
     * {@description.open}
     * Return true if this field has a primitive type.
     * {@description.close}
     *
     * @return  true if and only if this field corresponds to a primitive type
     */
    // REMIND: deprecate?
    public boolean isPrimitive() {
        char tcode = signature.charAt(0);
        return ((tcode != 'L') && (tcode != '['));
    }

    /** {@collect.stats}
     * {@description.open}
     * Returns boolean value indicating whether or not the serializable field
     * represented by this ObjectStreamField instance is unshared.
     * {@description.close}
     *
     * @since 1.4
     */
    public boolean isUnshared() {
        return unshared;
    }

    /** {@collect.stats}
     * {@description.open}
     * Compare this field with another <code>ObjectStreamField</code>.  Return
     * -1 if this is smaller, 0 if equal, 1 if greater.  Types that are
     * primitives are "smaller" than object types.  If equal, the field names
     * are compared.
     * {@description.close}
     */
    // REMIND: deprecate?
    public int compareTo(Object obj) {
        ObjectStreamField other = (ObjectStreamField) obj;
        boolean isPrim = isPrimitive();
        if (isPrim != other.isPrimitive()) {
            return isPrim ? -1 : 1;
        }
        return name.compareTo(other.name);
    }

    /** {@collect.stats}
     * {@description.open}
     * Return a string that describes this field.
     * {@description.close}
     */
    public String toString() {
        return signature + ' ' + name;
    }

    /** {@collect.stats}
     * {@description.open}
     * Returns field represented by this ObjectStreamField, or null if
     * ObjectStreamField is not associated with an actual field.
     * {@description.close}
     */
    Field getField() {
        return field;
    }

    /** {@collect.stats}
     * {@description.open}
     * Returns JVM type signature of field (similar to getTypeString, except
     * that signature strings are returned for primitive fields as well).
     * {@description.close}
     */
    String getSignature() {
        return signature;
    }
}
