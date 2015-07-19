/*
 * Copyright (c) 1997, 2006, Oracle and/or its affiliates. All rights reserved.
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
     * Thrown to indicate that the requested operation is not supported.<p>
 *
 * This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.

     * {@description.close} *
 * @author  Josh Bloch
 * @since   1.2
 */
public class UnsupportedOperationException extends RuntimeException {
    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs an UnsupportedOperationException with no detail message.

     * {@description.close}     */
    public UnsupportedOperationException() {
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs an UnsupportedOperationException with the specified
     * detail message.

     * {@description.close}     *
     * @param message the detail message
     */
    public UnsupportedOperationException(String message) {
        super(message);
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs a new exception with the specified detail message and
     * cause.
     *
     * <p>Note that the detail message associated with <code>cause</code> is
     * <i>not</i> automatically incorporated in this exception's detail
     * message.

     * {@description.close}     *
     * @param  message the detail message (which is saved for later retrieval
     *         by the {@link Throwable#getMessage()} method).
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link Throwable#getCause()} method).  (A <tt>null</tt> value
     *         is permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     * @since 1.5
     */
    public UnsupportedOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs a new exception with the specified cause and a detail
     * message of <tt>(cause==null ? null : cause.toString())</tt> (which
     * typically contains the class and detail message of <tt>cause</tt>).
     * This constructor is useful for exceptions that are little more than
     * wrappers for other throwables (for example, {@link
     * java.security.PrivilegedActionException}).

     * {@description.close}     *
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link Throwable#getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     * @since  1.5
     */
    public UnsupportedOperationException(Throwable cause) {
        super(cause);
    }

    static final long serialVersionUID = -1242599979055084673L;
}
