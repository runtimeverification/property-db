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
     * Thrown when the Java Virtual Machine attempts to read a class
 * file
     * {@description.close} and determines that the file is malformed or otherwise cannot
 * be interpreted as a class file.
 *
 * @author  unascribed
 * @since   JDK1.0
 */
public
class ClassFormatError extends LinkageError {
    private static final long serialVersionUID = -8420114879011949195L;

    /** {@collect.stats}
     * Constructs a <code>ClassFormatError</code> with no detail message.
     */
    public ClassFormatError() {
        super();
    }

    /** {@collect.stats}
     * Constructs a <code>ClassFormatError</code> with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public ClassFormatError(String s) {
        super(s);
    }
}
