/*
 * Copyright (c) 1998, Oracle and/or its affiliates. All rights reserved.
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

package javax.swing.text.html.parser;

import java.util.Hashtable;
import java.util.BitSet;
import java.io.*;

/** {@collect.stats}
 * An element as described in a DTD using the ELEMENT construct.
 * This is essentiall the description of a tag. It describes the
 * type, content model, attributes, attribute types etc. It is used
 * to correctly parse a document by the Parser.
 *
 * @see DTD
 * @see AttributeList
 * @author Arthur van Hoff
 */
public final
class Element implements DTDConstants, Serializable {
    public int index;
    public String name;
    public boolean oStart;
    public boolean oEnd;
    public BitSet inclusions;
    public BitSet exclusions;
    public int type = ANY;
    public ContentModel content;
    public AttributeList atts;

    static int maxIndex = 0;

    /** {@collect.stats}
     * A field to store user data. Mostly used to store
     * style sheets.
     */
    public Object data;

    Element() {
    }

    /** {@collect.stats}
     * Create a new element.
     */
    Element(String name, int index) {
        this.name = name;
        this.index = index;
        maxIndex = Math.max(maxIndex, index);
    }

    /** {@collect.stats}
     * Get the name of the element.
     */
    public String getName() {
        return name;
    }

    /** {@collect.stats}
     * Return true if the start tag can be omitted.
     */
    public boolean omitStart() {
        return oStart;
    }

    /** {@collect.stats}
     * Return true if the end tag can be omitted.
     */
    public boolean omitEnd() {
        return oEnd;
    }

    /** {@collect.stats}
     * Get type.
     */
    public int getType() {
        return type;
    }

    /** {@collect.stats}
     * Get content model
     */
    public ContentModel getContent() {
        return content;
    }

    /** {@collect.stats}
     * Get the attributes.
     */
    public AttributeList getAttributes() {
        return atts;
    }

    /** {@collect.stats}
     * Get index.
     */
    public int getIndex() {
        return index;
    }

    /** {@collect.stats}
     * Check if empty
     */
    public boolean isEmpty() {
        return type == EMPTY;
    }

    /** {@collect.stats}
     * Convert to a string.
     */
    public String toString() {
        return name;
    }

    /** {@collect.stats}
     * Get an attribute by name.
     */
    public AttributeList getAttribute(String name) {
        for (AttributeList a = atts ; a != null ; a = a.next) {
            if (a.name.equals(name)) {
                return a;
            }
        }
        return null;
    }

    /** {@collect.stats}
     * Get an attribute by value.
     */
    public AttributeList getAttributeByValue(String name) {
        for (AttributeList a = atts ; a != null ; a = a.next) {
            if ((a.values != null) && a.values.contains(name)) {
                return a;
            }
        }
        return null;
    }


    static Hashtable contentTypes = new Hashtable();

    static {
        contentTypes.put("CDATA", new Integer(CDATA));
        contentTypes.put("RCDATA", new Integer(RCDATA));
        contentTypes.put("EMPTY", new Integer(EMPTY));
        contentTypes.put("ANY", new Integer(ANY));
    }

    public static int name2type(String nm) {
        Integer val = (Integer)contentTypes.get(nm);
        return (val != null) ? val.intValue() : 0;
    }
}
