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
 * Thrown when serialization or deserialization is not active.
 * {@description.close}
 *
 * @author  unascribed
 * @since   JDK1.1
 */
public class NotActiveException extends ObjectStreamException {

    private static final long serialVersionUID = -3893467273049808895L;

    /** {@collect.stats}
     * {@description.open}
     * Constructor to create a new NotActiveException with the reason given.
     * {@description.close}
     *
     * @param reason  a String describing the reason for the exception.
     */
    public NotActiveException(String reason) {
        super(reason);
    }

    /** {@collect.stats}
     * {@description.open}
     * Constructor to create a new NotActiveException without a reason.
     * {@description.close}
     */
    public NotActiveException() {
        super();
    }
}
