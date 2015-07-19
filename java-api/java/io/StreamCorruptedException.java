/*
 * Copyright (c) 1996, 2005, Oracle and/or its affiliates. All rights reserved.
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
     * Thrown when control information that was read from an object stream
 * violates internal consistency checks.

     * {@description.close} *
 * @author  unascribed
 * @since   JDK1.1
 */
public class StreamCorruptedException extends ObjectStreamException {

    private static final long serialVersionUID = 8983558202217591746L;

    /** {@collect.stats}
     *      
* {@description.open}
     * Create a StreamCorruptedException and list a reason why thrown.

     * {@description.close}     *
     * @param reason  String describing the reason for the exception.
     */
    public StreamCorruptedException(String reason) {
        super(reason);
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Create a StreamCorruptedException and list no reason why thrown.

     * {@description.close}     */
    public StreamCorruptedException() {
        super();
    }
}
