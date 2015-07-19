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
     * Thrown when an application tries to call an abstract method.
 * Normally, this error is caught by the compiler; this error can
 * only occur at run time if the definition of some class has
 * incompatibly changed since the currently executing method was last
 * compiled.

     * {@description.close} *
 * @author  unascribed
 * @since   JDK1.0
 */
public
class AbstractMethodError extends IncompatibleClassChangeError {
    private static final long serialVersionUID = -1654391082989018462L;

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs an <code>AbstractMethodError</code> with no detail  message.

     * {@description.close}     */
    public AbstractMethodError() {
        super();
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs an <code>AbstractMethodError</code> with the specified
     * detail message.

     * {@description.close}     *
     * @param   s   the detail message.
     */
    public AbstractMethodError(String s) {
        super(s);
    }
}
