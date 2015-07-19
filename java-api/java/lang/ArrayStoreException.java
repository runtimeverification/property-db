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

package java.lang;

/** {@collect.stats}
 *      
* {@description.open}
     * Thrown to indicate that an attempt has been made to store the
 * wrong type of object into an array of objects. For example, the
 * following code generates an <code>ArrayStoreException</code>:
 * <blockquote><pre>
 *     Object x[] = new String[3];
 *     x[0] = new Integer(0);

     * {@description.close} * </pre></blockquote>
 *
 * @author  unascribed
 * @since   JDK1.0
 */
public
class ArrayStoreException extends RuntimeException {
    private static final long serialVersionUID = -4522193890499838241L;

    /** {@collect.stats}
     * Constructs an <code>ArrayStoreException</code> with no detail message.
     */
    public ArrayStoreException() {
        super();
    }

    /** {@collect.stats}
     * Constructs an <code>ArrayStoreException</code> with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public ArrayStoreException(String s) {
        super(s);
    }
}
