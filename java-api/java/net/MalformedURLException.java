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
 * Thrown to indicate that a malformed URL has occurred. Either no
 * legal protocol could be found in a specification string or the
 * string could not be parsed.
 * {@description.close}
 *
 * @author  Arthur van Hoff
 * @since   JDK1.0
 */
public class MalformedURLException extends IOException {
    private static final long serialVersionUID = -182787522200415866L;

    /** {@collect.stats} 
     * {@description.open}
     * Constructs a {@code MalformedURLException} with no detail message.
     * {@description.close}
     */
    public MalformedURLException() {
    }

    /** {@collect.stats} 
     * {@description.open}
     * Constructs a {@code MalformedURLException} with the
     * specified detail message.
     * {@description.close}
     *
     * @param   msg   the detail message.
     */
    public MalformedURLException(String msg) {
        super(msg);
    }
}