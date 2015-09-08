/*
 * Copyright (c) 1994, 2008, Oracle and/or its affiliates. All rights reserved.
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

package java.lang;

/** {@collect.stats}
 *      
* {@description.open}
     * Thrown if an application tries to create an array with negative size.

     * {@description.close} *
 * @author  unascribed
 * @since   JDK1.0
 */
public
class NegativeArraySizeException extends RuntimeException {
    private static final long serialVersionUID = -8960118058596991861L;

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs a <code>NegativeArraySizeException</code> with no
     * detail message.

     * {@description.close}     */
    public NegativeArraySizeException() {
        super();
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs a <code>NegativeArraySizeException</code> with the
     * specified detail message.

     * {@description.close}     *
     * @param   s   the detail message.
     */
    public NegativeArraySizeException(String s) {
        super(s);
    }
}
