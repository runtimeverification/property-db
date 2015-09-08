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

package java.io;

/** {@collect.stats}
 *      
* {@description.open}
     * Signals that a sync operation has failed.

     * {@description.close} *
 * @author  Ken Arnold
 * @see     java.io.FileDescriptor#sync
 * @see     java.io.IOException
 * @since   JDK1.1
 */
public class SyncFailedException extends IOException {
    private static final long serialVersionUID = -2353342684412443330L;

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs an SyncFailedException with a detail message.
     * A detail message is a String that describes this particular exception.

     * {@description.close}     *
     * @param desc  a String describing the exception.
     */
    public SyncFailedException(String desc) {
        super(desc);
    }
}
