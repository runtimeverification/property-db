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
     * The Character Encoding is not supported.

     * {@description.close} *
 * @author  Asmus Freytag
 * @since   JDK1.1
 */
public class UnsupportedEncodingException
    extends IOException
{
    private static final long serialVersionUID = -4274276298326136670L;

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs an UnsupportedEncodingException without a detail message.

     * {@description.close}     */
    public UnsupportedEncodingException() {
        super();
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs an UnsupportedEncodingException with a detail message.

     * {@description.close}     * @param s Describes the reason for the exception.
     */
    public UnsupportedEncodingException(String s) {
        super(s);
    }
}
