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
 *      
* {@description.open}
     * Signals that an error occurred while attempting to connect a
 * socket to a remote address and port.  Typically, the remote
 * host cannot be reached because of an intervening firewall, or
 * if an intermediate router is down.

     * {@description.close} *
 * @since   JDK1.1
 */
public class NoRouteToHostException extends SocketException {
    private static final long serialVersionUID = -1897550894873493790L;

    /** {@collect.stats}
     * Constructs a new NoRouteToHostException with the specified detail
     * message as to why the remote host cannot be reached.
     * A detail message is a String that gives a specific
     * description of this error.
     * @param msg the detail message
     */
    public NoRouteToHostException(String msg) {
        super(msg);
    }

    /** {@collect.stats}
     * Construct a new NoRouteToHostException with no detailed message.
     */
    public NoRouteToHostException() {}
}
