/*
 * Copyright (c) 2000, 2003, Oracle and/or its affiliates. All rights reserved.
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

package java.util.prefs;

import java.io.NotSerializableException;

/** {@collect.stats}
 *      
* {@description.open}
     * Thrown to indicate that a preferences operation could not complete because
 * of a failure in the backing store,
     * {@description.close} or a failure to contact the backing
 * store.
 *
 * @author  Josh Bloch
 * @since   1.4
 */
public class BackingStoreException extends Exception {
    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs a BackingStoreException with the specified detail message.

     * {@description.close}     *
     * @param s the detail message.
     */
    public BackingStoreException(String s) {
        super(s);
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs a BackingStoreException with the specified cause.

     * {@description.close}     *
     * @param cause the cause
     */
    public BackingStoreException(Throwable cause) {
        super(cause);
    }

    private static final long serialVersionUID = 859796500401108469L;
}
