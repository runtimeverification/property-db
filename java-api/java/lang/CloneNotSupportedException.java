/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
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
     * Thrown to indicate that the <code>clone</code> method in class
 * <code>Object</code> has been called to clone an object, but that
 * the object's class does not implement the <code>Cloneable</code>
 * interface.
 * <p>
 * Applications that override the <code>clone</code> method can also
 * throw this exception to indicate that an object could not or
 * should not be cloned.

     * {@description.close} *
 * @author  unascribed
 * @see     java.lang.Cloneable
 * @see     java.lang.Object#clone()
 * @since   JDK1.0
 */

public
class CloneNotSupportedException extends Exception {
    private static final long serialVersionUID = 5195511250079656443L;

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs a <code>CloneNotSupportedException</code> with no
     * detail message.

     * {@description.close}     */
    public CloneNotSupportedException() {
        super();
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs a <code>CloneNotSupportedException</code> with the
     * specified detail message.

     * {@description.close}     *
     * @param   s   the detail message.
     */
    public CloneNotSupportedException(String s) {
        super(s);
    }
}
