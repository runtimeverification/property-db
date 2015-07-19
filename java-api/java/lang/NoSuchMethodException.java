/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
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
     * Thrown when a particular method cannot be found.

     * {@description.close} *
 * @author     unascribed
 * @since      JDK1.0
 */
public
class NoSuchMethodException extends ReflectiveOperationException {
    private static final long serialVersionUID = 5034388446362600923L;

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs a <code>NoSuchMethodException</code> without a detail message.

     * {@description.close}     */
    public NoSuchMethodException() {
        super();
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs a <code>NoSuchMethodException</code> with a detail message.

     * {@description.close}     *
     * @param      s   the detail message.
     */
    public NoSuchMethodException(String s) {
        super(s);
    }
}
