/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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
     * This interface defines a factory for socket implementations. It
 * is used by the classes {@code Socket} and
 * {@code ServerSocket} to create actual socket
 * implementations.

     * {@description.close} *
 * @author  Arthur van Hoff
 * @see     java.net.Socket
 * @see     java.net.ServerSocket
 * @since   JDK1.0
 */
public
interface SocketImplFactory {
    /** {@collect.stats}
     *      
* {@description.open}
     * Creates a new {@code SocketImpl} instance.

     * {@description.close}     *
     * @return  a new instance of {@code SocketImpl}.
     * @see     java.net.SocketImpl
     */
    SocketImpl createSocketImpl();
}
