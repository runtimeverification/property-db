/*
 * Copyright (c) 1998, 2008, Oracle and/or its affiliates. All rights reserved.
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

package java.lang;

/** {@collect.stats}
 *      
* {@description.open}
     * Thrown when the Java Virtual Machine attempts to read a class
 * file and determines that the major and minor version numbers
 * in the file are not supported.

     * {@description.close} *
 * @since   1.2
 */
public
class UnsupportedClassVersionError extends ClassFormatError {
    private static final long serialVersionUID = -7123279212883497373L;

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs a <code>UnsupportedClassVersionError</code>
     * with no detail message.

     * {@description.close}     */
    public UnsupportedClassVersionError() {
        super();
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs a <code>UnsupportedClassVersionError</code> with
     * the specified detail message.

     * {@description.close}     *
     * @param   s   the detail message.
     */
    public UnsupportedClassVersionError(String s) {
        super(s);
    }
}
