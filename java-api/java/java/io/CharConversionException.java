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
     * Base class for character conversion exceptions.

     * {@description.close} *
 * @author      Asmus Freytag
 * @since       JDK1.1
 */
public class CharConversionException
    extends java.io.IOException
{
    private static final long serialVersionUID = -8680016352018427031L;

    /** {@collect.stats}
     *      
* {@description.open}
     * This provides no detailed message.

     * {@description.close}     */
    public CharConversionException() {
    }
    /** {@collect.stats}
     *      
* {@description.open}
     * This provides a detailed message.

     * {@description.close}     *
     * @param s the detailed message associated with the exception.
     */
    public CharConversionException(String s) {
        super(s);
    }
}
