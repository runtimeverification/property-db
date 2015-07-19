/*
 * Copyright (c) 2000, 2008, Oracle and/or its affiliates. All rights reserved.
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
 *      
* {@description.open}
     * Signals that a timeout has occurred on a socket read or accept.

     * {@description.close} *
 * @since   1.4
 */

public class SocketTimeoutException extends java.io.InterruptedIOException {
    private static final long serialVersionUID = -8846654841826352300L;

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs a new SocketTimeoutException with a detail
     * message.

     * {@description.close}     * @param msg the detail message
     */
    public SocketTimeoutException(String msg) {
        super(msg);
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Construct a new SocketTimeoutException with no detailed message.

     * {@description.close}     */
    public SocketTimeoutException() {}
}
