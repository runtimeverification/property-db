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
     * Thrown if an application tries to call a specified method of a
 * class (either static or instance), and that class no longer has a
 * definition of that method.
 * <p>
 * Normally, this error is caught by the compiler; this error can
 * only occur at run time if the definition of a class has
 * incompatibly changed.

     * {@description.close} *
 * @author  unascribed
 * @since   JDK1.0
 */
public
class NoSuchMethodError extends IncompatibleClassChangeError {
    private static final long serialVersionUID = -3765521442372831335L;

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs a <code>NoSuchMethodError</code> with no detail message.

     * {@description.close}     */
    public NoSuchMethodError() {
        super();
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs a <code>NoSuchMethodError</code> with the
     * specified detail message.

     * {@description.close}     *
     * @param   s   the detail message.
     */
    public NoSuchMethodError(String s) {
        super(s);
    }
}
