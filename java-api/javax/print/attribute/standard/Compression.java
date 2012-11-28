/*
 * Copyright (c) 2000, 2004, Oracle and/or its affiliates. All rights reserved.
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
package javax.print.attribute.standard;

import javax.print.attribute.Attribute;
import javax.print.attribute.EnumSyntax;
import javax.print.attribute.DocAttribute;

/** {@collect.stats}
 * Class Compression is a printing attribute class, an enumeration, that
 * specifies how print data is compressed. Compression is an attribute of the
 * print data (the doc), not of the Print Job. If a Compression attribute is not
 * specified for a doc, the printer assumes the doc's print data is uncompressed
 * (i.e., the default Compression value is always {@link #NONE
 * <CODE>NONE</CODE>}).
 * <P>
 * <B>IPP Compatibility:</B> The category name returned by
 * <CODE>getName()</CODE> is the IPP attribute name.  The enumeration's
 * integer value is the IPP enum value.  The <code>toString()</code> method
 * returns the IPP string representation of the attribute value.
 * <P>
 *
 * @author  Alan Kaminsky
 */
public class Compression extends EnumSyntax implements DocAttribute {

    private static final long serialVersionUID = -5716748913324997674L;

    /** {@collect.stats}
     * No compression is used.
     */
    public static final Compression NONE = new Compression(0);

    /** {@collect.stats}
     * ZIP public domain inflate/deflate compression technology.
     */
    public static final Compression DEFLATE = new Compression(1);

    /** {@collect.stats}
     * GNU zip compression technology described in
     * <A HREF="http://www.ietf.org/rfc/rfc1952.txt">RFC 1952</A>.
     */
    public static final Compression GZIP = new Compression(2);

    /** {@collect.stats}
     * UNIX compression technology.
     */
    public static final Compression COMPRESS = new Compression(3);

    /** {@collect.stats}
     * Construct a new compression enumeration value with the given integer
     * value.
     *
     * @param  value  Integer value.
     */
    protected Compression(int value) {
        super(value);
    }


    private static final String[] myStringTable = {"none",
                                                   "deflate",
                                                   "gzip",
                                                   "compress"};

    private static final Compression[] myEnumValueTable = {NONE,
                                                           DEFLATE,
                                                           GZIP,
                                                           COMPRESS};

    /** {@collect.stats}
     * Returns the string table for class Compression.
     */
    protected String[] getStringTable() {
        return (String[])myStringTable.clone();
    }

    /** {@collect.stats}
     * Returns the enumeration value table for class Compression.
     */
    protected EnumSyntax[] getEnumValueTable() {
        return (EnumSyntax[])myEnumValueTable.clone();
    }

    /** {@collect.stats}
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class Compression and any vendor-defined subclasses, the category is
     * class Compression itself.
     *
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return Compression.class;
    }

    /** {@collect.stats}
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class Compression and any vendor-defined subclasses, the category
     * name is <CODE>"compression"</CODE>.
     *
     * @return  Attribute category name.
     */
    public final String getName() {
        return "compression";
    }

}
