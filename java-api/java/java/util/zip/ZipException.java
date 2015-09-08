/*
 * Copyright (c) 1995, 2010, Oracle and/or its affiliates. All rights reserved.
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

import java.io.IOException;

/** {@collect.stats}
 *      
* {@description.open}
     * Signals that a Zip exception of some sort has occurred.

     * {@description.close} *
 * @author  unascribed
 * @see     java.io.IOException
 * @since   JDK1.0
 */

public
class ZipException extends IOException {
    private static final long serialVersionUID = 8000196834066748623L;

    /** {@collect.stats} 
     * {@description.open}
     * Constructs a <code>ZipException</code> with <code>null</code>
     * as its error detail message.
     * {@description.close}
     */
    public ZipException() {
        super();
    }

    /** {@collect.stats} 
     * {@description.open}
     * Constructs a <code>ZipException</code> with the specified detail
     * message.
     * {@description.close}
     *
     * @param   s   the detail message.
     */

    public ZipException(String s) {
        super(s);
    }
}
