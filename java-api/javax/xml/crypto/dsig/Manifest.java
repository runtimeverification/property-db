/*
 * Copyright (c) 2005, Oracle and/or its affiliates. All rights reserved.
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
/*
 * $Id: Manifest.java,v 1.7 2005/05/10 16:03:46 mullan Exp $
 */
package javax.xml.crypto.dsig;

import javax.xml.crypto.XMLStructure;
import java.util.List;

/** {@collect.stats}
 * A representation of the XML <code>Manifest</code> element as defined in
 * the <a href="http://www.w3.org/TR/xmldsig-core/">
 * W3C Recommendation for XML-Signature Syntax and Processing</a>.
 * The XML Schema Definition is defined as:
 * <pre><code>
 * &lt;element name="Manifest" type="ds:ManifestType"/&gt;
 *   &lt;complexType name="ManifestType"&gt;
 *     &lt;sequence>
 *       &lt;element ref="ds:Reference" maxOccurs="unbounded"/&gt;
 *     &lt;/sequence&gt;
 *     &lt;attribute name="Id" type="ID" use="optional"/&gt;
 *   &lt;/complexType&gt;
 * </code></pre>
 *
 * A <code>Manifest</code> instance may be created by invoking
 * one of the {@link XMLSignatureFactory#newManifest newManifest}
 * methods of the {@link XMLSignatureFactory} class; for example:
 *
 * <pre>
 *   XMLSignatureFactory factory = XMLSignatureFactory.getInstance("DOM");
 *   List references = Collections.singletonList(factory.newReference
 *       ("#reference-1", DigestMethod.SHA1));
 *   Manifest manifest = factory.newManifest(references, "manifest-1");
 * </pre>
 *
 * @author Sean Mullan
 * @author JSR 105 Expert Group
 * @since 1.6
 * @see XMLSignatureFactory#newManifest(List)
 * @see XMLSignatureFactory#newManifest(List, String)
 */
public interface Manifest extends XMLStructure {

    /** {@collect.stats}
     * URI that identifies the <code>Manifest</code> element (this can be
     * specified as the value of the <code>type</code> parameter of the
     * {@link Reference} class to identify the referent's type).
     */
    final static String TYPE = "http://www.w3.org/2000/09/xmldsig#Manifest";

    /** {@collect.stats}
     * Returns the Id of this <code>Manifest</code>.
     *
     * @return the Id  of this <code>Manifest</code> (or <code>null</code>
     *    if not specified)
     */
    String getId();

    /** {@collect.stats}
     * Returns an {@link java.util.Collections#unmodifiableList unmodifiable
     * list} of one or more {@link Reference}s that are contained in this
     * <code>Manifest</code>.
     *
     * @return an unmodifiable list of one or more <code>Reference</code>s
     */
    List getReferences();
}
