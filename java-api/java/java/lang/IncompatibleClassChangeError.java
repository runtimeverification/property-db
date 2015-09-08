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
     * Thrown when an incompatible class change has occurred to some class
 * definition. The definition of some class, on which the currently
 * executing method depends, has since changed.

     * {@description.close} *
 * @author  unascribed
 * @since   JDK1.0
 */
public
class IncompatibleClassChangeError extends LinkageError {
    private static final long serialVersionUID = -4914975503642802119L;

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs an <code>IncompatibleClassChangeError</code> with no
     * detail message.

     * {@description.close}     */
    public IncompatibleClassChangeError () {
        super();
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs an <code>IncompatibleClassChangeError</code> with the
     * specified detail message.

     * {@description.close}     *
     * @param   s   the detail message.
     */
    public IncompatibleClassChangeError(String s) {
        super(s);
    }
}
