/*
 * Copyright (c) 1996, 2005, Oracle and/or its affiliates. All rights reserved.
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
 * {@description.open}
 * Superclass of all exceptions specific to Object Stream classes.
 * {@description.close}
 *
 * @author  unascribed
 * @since   JDK1.1
 */
public abstract class ObjectStreamException extends IOException {

    private static final long serialVersionUID = 7260898174833392607L;

    /** {@collect.stats}
     * {@description.open}
     * Create an ObjectStreamException with the specified argument.
     * {@description.close}
     *
     * @param classname the detailed message for the exception
     */
    protected ObjectStreamException(String classname) {
        super(classname);
    }

    /** {@collect.stats}
     * {@description.open}
     * Create an ObjectStreamException.
     * {@description.close}
     */
    protected ObjectStreamException() {
        super();
    }
}
