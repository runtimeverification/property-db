/*
 * Copyright (c) 2005, 2006, Oracle and/or its affiliates. All rights reserved.
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

package java.io;

/** {@collect.stats}
 *      
* {@description.open}
     * Thrown when a serious I/O error has occurred.

     * {@description.close} *
 * @author  Xueming Shen
 * @since   1.6
 */
public class IOError extends Error {
    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs a new instance of IOError with the specified cause. The
     * IOError is created with the detail message of
     * <tt>(cause==null
     * {@description.close} ? null : cause.toString())</tt> (which typically
     * contains the class and detail message of cause).
     *
     * @param  cause
     *         The cause of this error, or <tt>null</tt> if the cause
     *         is not known
     */
    public IOError(Throwable cause) {
        super(cause);
    }

    private static final long serialVersionUID = 67100927991680413L;
}
