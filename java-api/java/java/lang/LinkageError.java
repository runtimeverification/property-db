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

package java.lang;

/** {@collect.stats}
 * {@description.open}
 * Subclasses of {@code LinkageError} indicate that a class has
 * some dependency on another class; however, the latter class has
 * incompatibly changed after the compilation of the former class.
 * {@description.close}
 *
 * @author  Frank Yellin
 * @since   JDK1.0
 */
public
class LinkageError extends Error {
    private static final long serialVersionUID = 3579600108157160122L;

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs a {@code LinkageError} with no detail message.

     * {@description.close}     */
    public LinkageError() {
        super();
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs a {@code LinkageError} with the specified detail
     * message.

     * {@description.close}     *
     * @param   s   the detail message.
     */
    public LinkageError(String s) {
        super(s);
    }

    /**
     * Constructs a {@code LinkageError} with the specified detail
     * message and cause.
     *
     * @param s     the detail message.
     * @param cause the cause, may be {@code null}
     * @since 1.7
     */
    public LinkageError(String s, Throwable cause) {
        super(s, cause);
    }
}
