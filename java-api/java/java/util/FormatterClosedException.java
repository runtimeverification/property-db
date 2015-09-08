/*
 * Copyright (c) 2003, Oracle and/or its affiliates. All rights reserved.
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

package java.util;

/** {@collect.stats}
 *      
* {@description.open}
     * Unchecked exception thrown when the formatter has been closed.
 *
 * <p> Unless otherwise specified, passing a <tt>null</tt> argument to any
 * method or constructor in this class will cause a {@link
 * NullPointerException} to be thrown.

     * {@description.close} *
 * @since 1.5
 */
public class FormatterClosedException extends IllegalStateException {

    private static final long serialVersionUID = 18111216L;

    /** {@collect.stats}
     * Constructs an instance of this class.
     */
    public FormatterClosedException() { }
}
