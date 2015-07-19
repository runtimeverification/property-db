/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.util.jar;

import java.io.IOException;
import java.util.zip.ZipEntry;
import java.security.CodeSigner;
import java.security.cert.Certificate;

/** {@collect.stats}
 *      
* {@description.open}
     * This class is used to represent a JAR file entry.

     * {@description.close} */
public
class JarEntry extends ZipEntry {
    Attributes attr;
    Certificate[] certs;
    CodeSigner[] signers;

    /** {@collect.stats}
     *      
* {@description.open}
     * Creates a new <code>JarEntry</code> for the specified JAR file
     * entry name.

     * {@description.close}     *
     * @param name the JAR file entry name
     * @exception NullPointerException if the entry name is <code>null</code>
     * @exception IllegalArgumentException if the entry name is longer than
     *            0xFFFF bytes.
     */
    public JarEntry(String name) {
        super(name);
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Creates a new <code>JarEntry</code> with fields taken from the
     * specified <code>ZipEntry</code> object.

     * {@description.close}     * @param ze the <code>ZipEntry</code> object to create the
     *           <code>JarEntry</code> from
     */
    public JarEntry(ZipEntry ze) {
        super(ze);
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Creates a new <code>JarEntry</code> with fields taken from the
     * specified <code>JarEntry</code> object.

     * {@description.close}     *
     * @param je the <code>JarEntry</code> to copy
     */
    public JarEntry(JarEntry je) {
        this((ZipEntry)je);
        this.attr = je.attr;
        this.certs = je.certs;
        this.signers = je.signers;
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Returns the <code>Manifest</code> <code>Attributes</code> for this
     * entry, or <code>null</code> if none.

     * {@description.close}     *
     * @return the <code>Manifest</code> <code>Attributes</code> for this
     * entry, or <code>null</code> if none
     * @throws IOException  if an I/O error has occurred
     */
    public Attributes getAttributes() throws IOException {
        return attr;
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Returns the <code>Certificate</code> objects for this entry, or
     * <code>null</code> if none.
     * {@description.close}      
* {@property.open formal:java.util.jar.JarEntry_GetCertificatesOnce}
     * This method can only be called once
     * the <code>JarEntry</code> has been completely verified by reading
     * from the entry input stream until the end of the stream has been
     * reached. Otherwise, this method will return <code>null</code>.

     * {@property.close}     *
     *      
* {@description.open}
     * <p>The returned certificate array comprises all the signer certificates
     * that were used to verify this entry. Each signer certificate is
     * followed by its supporting certificate chain (which may be empty).
     * Each signer certificate and its supporting certificate chain are ordered
     * bottom-to-top (i.e., with the signer certificate first and the (root)
     * certificate authority last).

     * {@description.close}     *
     * @return the <code>Certificate</code> objects for this entry, or
     * <code>null</code> if none.
     */
    public Certificate[] getCertificates() {
        return certs == null ? null : certs.clone();
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Returns the <code>CodeSigner</code> objects for this entry, or
     * <code>null</code> if none.
     * {@description.close}      
* {@property.open formal:java.util.jar.JarEntry_GetCodeSignersOnce}
     * This method can only be called once
     * the <code>JarEntry</code> has been completely verified by reading
     * from the entry input stream until the end of the stream has been
     * reached. Otherwise, this method will return <code>null</code>.

     * {@property.close}     *
     *      
* {@description.open}
     * <p>The returned array comprises all the code signers that have signed
     * this entry.

     * {@description.close}     *
     * @return the <code>CodeSigner</code> objects for this entry, or
     * <code>null</code> if none.
     *
     * @since 1.5
     */
    public CodeSigner[] getCodeSigners() {
        return signers == null ? null : signers.clone();
    }
}
