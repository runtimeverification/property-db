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
 *      
* {@description.open}
     * Indicates that one or more deserialized objects failed validation
 * tests.  The argument should provide the reason for the failure.

     * {@description.close} *
 * @see ObjectInputValidation
 * @since JDK1.1
 *
 * @author  unascribed
 * @since   JDK1.1
 */
public class InvalidObjectException extends ObjectStreamException {

    private static final long serialVersionUID = 3233174318281839583L;

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs an <code>InvalidObjectException</code>.

     * {@description.close}     * @param reason Detailed message explaining the reason for the failure.
     *
     * @see ObjectInputValidation
     */
    public  InvalidObjectException(String reason) {
        super(reason);
    }
}
