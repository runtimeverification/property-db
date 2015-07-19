/*
 * Copyright (c) 2004, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.io.IOException;

/** {@collect.stats}
 *      
* {@description.open}
     * A <tt>Flushable</tt> is a destination of data that can be flushed.  The
 * flush method is invoked to write any buffered output to the underlying
 * stream.

     * {@description.close} *
 * @since 1.5
 */
public interface Flushable {

    /** {@collect.stats}
     *      
* {@description.open}
     * Flushes this stream by writing any buffered output to the underlying
     * stream.

     * {@description.close}     *
     * @throws IOException If an I/O error occurs
     */
    void flush() throws IOException;
}
