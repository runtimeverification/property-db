/*
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
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

package java.util.zip;

/** {@collect.stats}
 *      
* {@description.open}
     * Signals that an unrecoverable error has occurred.

     * {@description.close} *
 * @author  Dave Bristor
 * @since   1.6
 */
public class ZipError extends InternalError {
    private static final long serialVersionUID = 853973422266861979L;

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs a ZipError with the given detail message.

     * {@description.close}     * @param s the {@code String} containing a detail message
     */
    public ZipError(String s) {
        super(s);
    }
}
