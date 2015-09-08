/*
 * Copyright (c) 1997, 2008, Oracle and/or its affiliates. All rights reserved.
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

/** {@collect.stats}
 *      
* {@description.open}
     * Signals that an error of some sort has occurred while reading from
 * or writing to a JAR file.

     * {@description.close} *
 * @author  David Connelly
 * @since   1.2
 */
public
class JarException extends java.util.zip.ZipException {
    private static final long serialVersionUID = 7159778400963954473L;

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs a JarException with no detail message.

     * {@description.close}     */
    public JarException() {
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs a JarException with the specified detail message.

     * {@description.close}     * @param s the detail message
     */
    public JarException(String s) {
        super(s);
    }
}
