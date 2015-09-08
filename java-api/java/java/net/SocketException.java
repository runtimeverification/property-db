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

import java.io.IOException;

/** {@collect.stats}
 *      
* {@description.open}
     * Thrown to indicate that there is an error creating or accessing a Socket.

     * {@description.close} *
 * @author  Jonathan Payne
 * @since   JDK1.0
 */
public
class SocketException extends IOException {
    private static final long serialVersionUID = -5935874303556886934L;

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs a new {@code SocketException} with the
     * specified detail message.

     * {@description.close}     *
     * @param msg the detail message.
     */
    public SocketException(String msg) {
        super(msg);
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs a new {@code SocketException} with no detail message.

     * {@description.close}     */
    public SocketException() {
    }
}
