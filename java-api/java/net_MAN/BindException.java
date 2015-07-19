/*
 * Copyright (c) 1996, 2008, Oracle and/or its affiliates. All rights reserved.
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

package java.net;

/** {@collect.stats} 
 * {@description.open}
 * Signals that an error occurred while attempting to bind a
 * socket to a local address and port.  Typically, the port is
 * in use, or the requested local address could not be assigned.
 * {@description.close}
 *
 * @since   JDK1.1
 */

public class BindException extends SocketException {
    private static final long serialVersionUID = -5945005768251722951L;

    /** {@collect.stats} 
     * {@description.open}
     * Constructs a new BindException with the specified detail
     * message as to why the bind error occurred.
     * A detail message is a String that gives a specific
     * description of this error.
     * {@description.close}
     * @param msg the detail message
     */
    public BindException(String msg) {
        super(msg);
    }

    /** {@collect.stats} 
     * {@description.open}
     * Construct a new BindException with no detailed message.
     * {@description.close}
     */
    public BindException() {}
}
