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
 * {@description.open}
 * Thrown to indicate that there is an error in the underlying
 * protocol, such as a TCP error.
 * {@description.close}
 *
 * @author  Chris Warth
 * @since   JDK1.0
 */
public
class ProtocolException extends IOException {
    private static final long serialVersionUID = -6098449442062388080L;

    /** {@collect.stats} 
     * {@description.open}
     * Constructs a new {@code ProtocolException} with the
     * specified detail message.
     * {@description.close}
     *
     * @param   host   the detail message.
     */
    public ProtocolException(String host) {
        super(host);
    }

    /** {@collect.stats} 
     * {@description.open}
     * Constructs a new {@code ProtocolException} with no detail message.
     * {@description.close}
     */
    public ProtocolException() {
    }
}