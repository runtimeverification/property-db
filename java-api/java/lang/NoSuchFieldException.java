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

package java.lang;

/** {@collect.stats}
 *      
* {@description.open}
     * Signals that the class doesn't have a field of a specified name.

     * {@description.close} *
 * @author  unascribed
 * @since   JDK1.1
 */
public class NoSuchFieldException extends ReflectiveOperationException {
    private static final long serialVersionUID = -6143714805279938260L;

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructor.

     * {@description.close}     */
    public NoSuchFieldException() {
        super();
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructor with a detail message.

     * {@description.close}     *
     * @param s the detail message
     */
    public NoSuchFieldException(String s) {
        super(s);
    }
}
