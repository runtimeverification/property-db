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
     * Thrown to indicate that the IP address of a host could not be determined.

     * {@description.close} *
 * @author  Jonathan Payne
 * @since   JDK1.0
 */
public
class UnknownHostException extends IOException {
    private static final long serialVersionUID = -4639126076052875403L;

    /** {@collect.stats}
     * Constructs a new {@code UnknownHostException} with the
     * specified detail message.
     *
     * @param   host   the detail message.
     */
    public UnknownHostException(String host) {
        super(host);
    }

    /** {@collect.stats}
     * Constructs a new {@code UnknownHostException} with no detail
     * message.
     */
    public UnknownHostException() {
    }
}
