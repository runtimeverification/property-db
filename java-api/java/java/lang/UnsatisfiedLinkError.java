/*
 * Copyright (c) 1994, 2008, Oracle and/or its affiliates. All rights reserved.
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
     * Thrown if the Java Virtual Machine cannot find an appropriate
 * native-language definition of a method declared <code>native</code>.

     * {@description.close} *
 * @author unascribed
 * @see     java.lang.Runtime
 * @since   JDK1.0
 */
public
class UnsatisfiedLinkError extends LinkageError {
    private static final long serialVersionUID = -4019343241616879428L;

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs an <code>UnsatisfiedLinkError</code> with no detail message.

     * {@description.close}     */
    public UnsatisfiedLinkError() {
        super();
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs an <code>UnsatisfiedLinkError</code> with the
     * specified detail message.

     * {@description.close}     *
     * @param   s   the detail message.
     */
    public UnsatisfiedLinkError(String s) {
        super(s);
    }
}
