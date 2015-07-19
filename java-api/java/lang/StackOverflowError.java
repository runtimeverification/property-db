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
     * Thrown when a stack overflow occurs because an application
 * recurses too deeply.

     * {@description.close} *
 * @author unascribed
 * @since   JDK1.0
 */
public
class StackOverflowError extends VirtualMachineError {
    private static final long serialVersionUID = 8609175038441759607L;

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs a <code>StackOverflowError</code> with no detail message.

     * {@description.close}     */
    public StackOverflowError() {
        super();
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs a <code>StackOverflowError</code> with the specified
     * detail message.

     * {@description.close}     *
     * @param   s   the detail message.
     */
    public StackOverflowError(String s) {
        super(s);
    }
}
