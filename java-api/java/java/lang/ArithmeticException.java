/*
 * Copyright (c) 1994, 2011, Oracle and/or its affiliates. All rights reserved.
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
 * {@description.open}
 * Thrown when an exceptional arithmetic condition has occurred. For
 * example, an integer "divide by zero" throws an
 * instance of this class.
 * {@description.close}
 *
 * {@code ArithmeticException} objects may be constructed by the
 * virtual machine as if {@linkplain Throwable#Throwable(String,
 * Throwable, boolean, boolean) suppression were disabled and/or the
 * stack trace was not writable}.
 *
 * @author  unascribed
 * @since   JDK1.0
 */
public class ArithmeticException extends RuntimeException {
    private static final long serialVersionUID = 2256477558314496007L;

    /** {@collect.stats}
     * Constructs an {@code ArithmeticException} with no detail
     * message.
     */
    public ArithmeticException() {
        super();
    }

    /** {@collect.stats}
     * Constructs an {@code ArithmeticException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public ArithmeticException(String s) {
        super(s);
    }
}
