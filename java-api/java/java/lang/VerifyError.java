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
     * Thrown when the "verifier" detects that a class file,
 * though well formed, contains some sort of internal inconsistency
 * or security problem.

     * {@description.close} *
 * @author  unascribed
 * @since   JDK1.0
 */
public
class VerifyError extends LinkageError {
    private static final long serialVersionUID = 7001962396098498785L;

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs an <code>VerifyError</code> with no detail message.

     * {@description.close}     */
    public VerifyError() {
        super();
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs an <code>VerifyError</code> with the specified detail message.

     * {@description.close}     *
     * @param   s   the detail message.
     */
    public VerifyError(String s) {
        super(s);
    }
}
